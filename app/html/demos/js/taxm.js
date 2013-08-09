var SERVICE_PATH="http://192.168.137.1:8080/core/services/api/"
var FILE_PATH = "../data/"
var DROP_TABLE = false; //重建缓存表
var DEBUG_LOCAL = true; //利用本地文件调试


	dbutil={
		db:null,//数据库句柄
  	dbSize: 5 * 1024 * 1024, // 5MB
		init:function(){
			this.db = openDatabase("taxm", "1.0", "taxm manager", this.dbSize);
			return this;
		},
		/*建表*/
		createTable:function(){
			this.db.transaction(function(tx) {
		  	if(DROP_TABLE) {
		  		tx.executeSql("drop table if exists corp_jbxx",[]);
		  		tx.executeSql("drop table if exists corp_hd",[]);
		  		tx.executeSql("drop table if exists corp_ns",[]);
		  		tx.executeSql("drop table if exists corp_sb",[]);
		  		tx.executeSql("drop table if exists corp_fpgz",[]);
		  		tx.executeSql("drop table if exists corp_sbfjf",[]);	  		
		  	}
		    tx.executeSql("CREATE TABLE IF NOT EXISTS corp_jbxx(ID INTEGER PRIMARY KEY ASC, name text unique, content TEXT, update_time DATETIME)", [],dbutil.onSuccess);
		    tx.executeSql("CREATE TABLE IF NOT EXISTS corp_hd(ID INTEGER PRIMARY KEY ASC, name text unique, content TEXT, update_time DATETIME)", [],dbutil.onSuccess);
		    tx.executeSql("CREATE TABLE IF NOT EXISTS corp_ns(ID INTEGER PRIMARY KEY ASC, name text unique, content TEXT, update_time DATETIME)", [],dbutil.onSuccess);
		    tx.executeSql("CREATE TABLE IF NOT EXISTS corp_sb(ID INTEGER PRIMARY KEY ASC, name text unique, content TEXT, update_time DATETIME)", [],dbutil.onSuccess);
		    tx.executeSql("CREATE TABLE IF NOT EXISTS corp_fpgz(ID INTEGER PRIMARY KEY ASC, name text unique, content TEXT, update_time DATETIME)", [],dbutil.onSuccess);
		    tx.executeSql("CREATE TABLE IF NOT EXISTS corp_sbfjf(ID INTEGER PRIMARY KEY ASC, name text unique, content TEXT, update_time DATETIME)", [],dbutil.onSuccess);
		  });
		},
		/*写行*/
		writeRow: function( tablename, row , callback){
			var succ = callback? callback: dbutil.onSuccess;
			this.db.transaction(function (tx){
				tx.executeSql('replace into ' + tablename + '(name, content, update_time) values(?, ?,?)', [ row.name,row.content, new Date().toISOString()],
					callback,
		      dbutil.onError);
			});
		},
		/*写行成功时，只保留最新50条*/
		cleanCache:function(){
			console.log("now clear the cache rows over 50");
			this.db.transaction(function (tx){
				console.log("clear jbxx");
	    	tx.executeSql('delete from corp_jbxx where id not in(select id from corp_jbxx b order by update_time desc limit 50) ',[],	dbutil.onSuccess,dbutil.onError);
				console.log("clear corp_hd");
	    	tx.executeSql('delete from corp_hd where name not in(select name as nsrbm from corp_jbxx b order by update_time desc limit 50) ',[],	dbutil.onSuccess,dbutil.onError);
				console.log("clear corp_ns");
	    	tx.executeSql('delete from corp_ns where name not in(select name as nsrbm   from corp_jbxx b order by update_time desc limit 50) ',[],	dbutil.onSuccess,dbutil.onError);
				console.log("clear corp_sb");
	    	tx.executeSql('delete from corp_sb where name not in(select name as nsrbm   from corp_jbxx b order by update_time desc limit 50) ',[],	dbutil.onSuccess,dbutil.onError);
				console.log("clear corp_fpgz");
	    	tx.executeSql('delete from corp_fpgz where name not in(select name as nsrbm   from corp_jbxx b order by update_time desc limit 50) ',[],	dbutil.onSuccess,dbutil.onError);
				console.log("clear corp_sbfjf");
	    	tx.executeSql('delete from corp_sbfjf where name not in(select name as nsrbm   from corp_jbxx b order by update_time desc limit 50) ',[],	dbutil.onSuccess,dbutil.onError);
   		});
		},
		
		/*缺省的异常回调函数*/
		onError: function(tx, e) {
		  alert("There has been an error: " + e.message);
		},
		/*缺省的回调函数*/
		onSuccess: function(tx, r) {
			console.log("success");
			console.log("result count:"+r.rows.length);
		},
		/*用名称关键字查询*/
		findByName: function( tablename, keyword, callback) {
			this.db.transaction(function (tx){
				tx.executeSql("select id,name,content,update_time from " + tablename + " where name like '%"+ keyword + "%' order by update_time desc", [],
					callback,
		      dbutil.onError);        
			});	
		}//-find by Name


	}

		
				/*页面生成器 BY takeashower*/
			PageBuilder={
				/*
				load flow:
			//1先从本地取，调页面生成3
			//2本地没有，联网取getRemote;联网取到后，写本地，并调页面生成3
			//3生成页面 render
				
				1.load	从本地取数；
				info_type:信息类型（ns纳税、hd核定、jbxx基本信息、sb申报、sbfjf社保、fpgz发票购置）
				id:标识值
				remoteNow:立即远程取
				*/
				load:function(info_type, id, remoteNow){
					var path = SERVICE_PATH + info_type +"/" + id + ".json";					
					if(DEBUG_LOCAL) path = FILE_PATH + info_type + ".json";//用本地文件调试
					 
					if(remoteNow) return PageBuilder.getRemote(info_type, id, path);
					
					dbutil.findByName("corp_"+ info_type, id, function(tx, r){
						//a 本地无数据
						if(r.rows.length ==0 || r.rows.item(0).content.length ==0){
							PageBuilder.getRemote(info_type, id, path);
							return;
						}
						//b 本地有数据
						PageBuilder.render(info_type, JSON.parse(r.rows.item(0).content));
					});

				},
				/*
				2.getRemote联网取数
				info_type:信息类型（ns纳税、hd核定、jbxx基本信息、sb申报、sbfjf社保、fpgz发票购置）
				id: 标识值
				path:路径 ，如/services/api/ns/
				*/
				getRemote:function(info_type, id, path){
					$.ajax({url: path,					
							dataType:DEBUG_LOCAL? "json":"jsonp",
							success:function(entity){
								if(entity.length>0){
									//缓存数据
									var o = new Object();
									o.name = id; 
									if(info_type=="jbxx") o.name = entity[0].nsrMc; //基本信息
									o.content = JSON.stringify(entity);
									if(o.content.length >"[]".length){
										dbutil.writeRow("corp_"+ info_type, o, function(tx, r){console.log("write a row, name=" + id);} );
									}
								}
								PageBuilder.render(info_type, entity);
							
							},
							error: function(XMLHttpRequest, textStatus, errorThrown){ 
		            alert('error:'+errorThrown); 
		       		}
	
						});
					
				},
				//3.调用子页生成函数
				render:function(info_type, data){
							$("#"+info_type+" span").text(data.length);
							eval("PageBuilder.pop_"+ info_type +"(data)")
							
							/*生成函数：
								pop_jbxx	基本信息
								pop_hd	核定
								pop_sb	申报
								pop_ns	纳税
								pop_fpgz发票购置
								pop_sbfjf社保
							*/
				},
				
				//3.---sub of render
				pop_jbxx:function(d){
      	  $("#dn_jbxx").remove();
      	  if(d.nsrMc) d = [d];
	        var head= '<div id="dn_jbxx" class="datanode gen"> <ul data-role="listview" data-inset="true">'+
  	      '<li data-role="list-divider">基本信息</li>'+
    	   
      	  '</ul></div>';
        	$('.ui-content').append(head);
        	var lv = $("#dn_jbxx.gen>ul");
					//遍历该节点下的记录
					$.each(d,function(i, entity){
					//处理单条纳税记录的生成
	
						var t =
						"<li data-role='fieldcontain'><label for='name2'>纳税人名称：</label><span type='text'>"+ entity.nsrMc	+"</span></li>"+
						"<li data-role='fieldcontain'><label for='name2'>纳税人编码：</label><span type='text'>"+ entity.nsrbm	+"</span></li>"+
						"<li data-role='fieldcontain'><label for='name2'>注册地址：</label><span type='text'>"+ entity.zcDz	+"</span></li>"+
						"<li data-role='fieldcontain'><label for='name2'>生产经营地址：</label><span type='text'>"+ entity.sjjyDz	+"</span></li>"+
						"<li data-role='fieldcontain'><label for='name2'>注册类型：</label><span type='text'>"+ entity.zclx	+"</span></li>"+
						"<li data-role='fieldcontain'><label for='name2'>所处街道：</label><span type='text'>"+ entity.xzjd	+"</span></li>"+
						"<li data-role='fieldcontain'><label for='name2'>信誉等级：</label><span type='text'>"+ entity.xydjjb	+"</span></li>"+
						"<li data-role='fieldcontain'><label for='name2'>法定代表人：</label><span type='text'>"+ entity.fddbrMc	+"</span></li>"+
						"<li data-role='fieldcontain'><label for='name2'>联系电话：</label><span type='text'>"+ entity.lxdhDh	+"</span></li>"+
						"<li data-role='fieldcontain'><label for='name2'>法人手机号码：</label><span type='text'>"+ entity.frsjhm	+"</span></li>"+
						"<li data-role='fieldcontain'><label for='name2'>财务负责人：</label><span type='text'>"+ entity.cwfzrMc	+"</span></li>"+
						"<li data-role='fieldcontain'><label for='name2'>办税员姓名：</label><span type='text'>"+ entity.bsyMc	+"</span></li>"+
						"<li data-role='fieldcontain'><label for='name2'>控股类型：</label><span type='text'>"+ entity.kglx	+"</span></li>"+
						"<li data-role='fieldcontain'><label for='name2'>税务登记：</label><span type='text'>"+ entity.swdjlb	+"</span></li>"+
						"<li data-role='fieldcontain'><label for='name2'>税务登记证号：</label><span type='text'>"+ entity.swdjzh	+"</span></li>"+
						"<li data-role='fieldcontain'><label for='name2'>登记状态：</label><span type='text'>"+ entity.djzt	+"</span></li>"+
						"<li data-role='fieldcontain'><label for='name2'>隶属关系：</label><span type='text'>"+ entity.lsgx	+"</span></li>"+
						"<li data-role='fieldcontain'><label for='name2'>行业：</label><span type='text'>"+ entity.hy	+"</span></li>"+
						"<li data-role='fieldcontain'><label for='name2'>管理机关：</label><span type='text'>"+ entity.gljg	+"</span></li>"+
						"<li data-role='fieldcontain'><label for='name2'>登记机关：</label><span type='text'>"+ entity.djjg	+"</span></li>"+
						"<li data-role='fieldcontain'><label for='name2'>专管员：</label><span type='text'>"+ entity.zgy	+"</span></li>"+
						"<li data-role='fieldcontain'><label for='name2'>税务登记日期：</label><span type='text'>"+ entity.djRq	+"</span></li>"+
						"<li data-role='fieldcontain'><label for='name2'>工商开业时间：</label><span type='text'>"+ entity.yxQsrq	+"</span></li>"+
						"<li data-role='fieldcontain'><label for='name2'>经营范围：</label><span type='text'>"+ entity.jyfwzy+"</span></li>"+

						'</fieldset></li>';

						lv.append(t)
						$("#mytitle").text(entity.nsrMc + "("+entity.nsrbm+")");
					});//-- each

					lv.listview({ theme: "c" });//.listview("refresh");

				},// -jbxx
				//3.---sub of render
				pop_hd:function(d){
					$("#dn_hd").remove();	
	        var head= '<div id="dn_hd" class="datanode gen" style="display:none"> <ul data-role="listview" data-inset="true">'+
  	      '<li data-role="list-divider">核定信息</li>'+
    	   
      	  '</ul></div>'
        	$('.ui-content').append(head);
        	var lv = $("#dn_hd.gen>ul");
					//遍历该节点下的记录
					$.each(d,function(i, entity){
					//处理单条纳税记录的生成
						var date1=entity.id.hdQsrq;
						var t =
						' <li> <fieldset class="ui-grid-b ui-responsive"> '+
						"<li class='ui-block-a'><label>税    种：</label><span type='text'>"+ entity.id.sz	+"</span>"+
						"<li class='ui-block-b'><label>品    目：</label><span type='text'>"+ entity.id.pm +"</span>"+
						"<li class='ui-block-c'><label>申报期限：</label><span type='text'>"+ entity.sbqx +"</span>"+
						"<li class='ui-block-a'><label>计税金额：</label><span type='text'>"+ entity.jsje +"</span><label>元</label>"+
						"<li class='ui-block-b'><label>税    率：</label><span type='text'>"+ entity.sl +"</span><label>%</label>"+
						"<li class='ui-block-c'><label>纳税期限：</label><span type='text'>"+ entity.nsqx +"</span>"+
						"<li class='ui-block-a'><label>速算扣除数：</label><span type='text'>"+ entity.sskcs +"</span>"+
						"<li class='ui-block-b'><label>应纳税额：</label><span type='text'>"+ entity.ynse +"</span>"+
						"<li class='ui-block-c'><label>征收方式：</label><span type='text'>"+ entity.zsfs +"</span>"+
						"<li class='ui-block-a'><label>计税依据：</label><span type='text'>"+ entity.jsyj +"</span>"+
						"<li class='ui-block-b'><label>起始日期：</label><span type='text'>"+ date1 + "</span>"+
						"<li class='ui-block-c'><label>终止日期：</label><span type='text'>"+ entity.hdZzrq +"</span>"+
						"<li class='ui-block-a'><label>缴款名称：</label><span type='text'>"+ entity.jkmc +"</span>"+
						'</fieldset></li>';

						lv.append(t)
					});//-- each

					lv.listview({ theme: "c" });//.listview("refresh");

				},//-- hd

				//3.---sub of render
				pop_sb:function(d){
					$("#dn_sb").remove();	
       	   var head= '<div id="dn_sb" class="mytitle datanode gen" style="display:none"> '+
       	   '申报情况'+
       	   '</div>';
       	  
       	   $('.ui-content').append(head);
						var tb_1=
						'<table data-role="table" id="t_sb" data-mode="columntoggle" class="ui-responsive table-stroke ui-body-d">'+
     				'<thead><tr class="ui-bar-b">'+
         		'<th data-priority="6">申报项目</th>'+
         		'<th data-priority="1">税费所属期起</th>'+
         		'<th data-priority="1" style="white-space:nowrap">税费所属期止</th>'+
         		'<th data-priority="2">申报情况</th>'+
         		'<th data-priority="2">申报状态 </th>'+

       			'</tr></thead><tbody>' ;
       				
       		var tb_2=	""	
       		var date1=new Date();
       		var date2=new Date();
					//遍历该节点下的记录
					$.each(d,function(i, entity){
						 //处理单条
						date1=entity.id.sfssqQsrq;
						date2=entity.id.sfssqZzrq;	
						tb_2=	tb_2 +
       		 	'<tr>'+
         		'<th>'+ entity.id.zsxm +'</th>'+
         		'<td>'+ date1 +'</td>'+
         		'<td>'+ date2 +'</td>'+
         		'<td>'+ entity.sbqk +'</td>'+
         		'<td>'+ entity.sbzt +'</td>'+
     		         		
       			'</tr>';

					});//-- each

					var tb_3="</tbody></table>";
					//一次性添加内容（每添加一次都是对象，而非字符串，所以元素位置会错乱；同时注意性能
					$('#dn_sb').append(tb_1 + tb_2 + tb_3);	

					//初始化表格，设按钮文字
					$("#t_sb").table({ columnBtnText: "显示列" });
					
				},	//-- sb

				//3.---sub of render
				pop_ns:function(d){
					$("#dn_ns").remove();	
       	   var head= '<div id="dn_ns" class="mytitle datanode gen" style="display:none"> '+
       	   '缴税情况'+
       	   '</div>';
       	  
       	   $('.ui-content').append(head);

						var tb_1=
						'<table data-role="table" id="t_ns" data-mode="columntoggle" class="ui-responsive table-stroke ui-body-d">'+
     				'<thead><tr class="ui-bar-b">'+
         		'<th data-priority="6">缴款期限</th>'+
         		'<th data-priority="1">纳税人编码</th>'+
         		'<th data-priority="1" style="white-space:nowrap">纳税人内部码</th>'+
         		'<th data-priority="2">税费所属期起</th>'+
         		'<th data-priority="2">税费所属期止</th>'+
         		'<th data-priority="1">征收项目</th>'+
         		'<th data-priority="3" width="120px">应征税费金额</th>'+
       			'</tr></thead><tbody>' ;

       		var tb_2=	""	
					//遍历该节点下的记录
					$.each(d,function(i, entity){
						 //处理单条发票购置记录的生成
						 entity = entity.id;

						tb_2=	tb_2 +
       		 	'<tr>'+
         		'<th>'+ entity.jkQx +'</th>'+
         		'<td>'+ entity.nsrbm +'</td>'+
         		'<td>'+ entity.nsrnbm +'</td>'+
         		'<td>'+ entity.sfssqQsrq +'</td>'+
         		'<td>'+ entity.sfssqZzrq +'</td>'+
         		'<td>'+ entity.sz +'</td>' +
         		'<td>'+ entity.yzsfJe +'</td>'+         		         		
       			'</tr>';

					});//-- each

					var tb_3="</tbody></table>";
					//一次性添加内容（每添加一次都是对象，而非字符串，所以元素位置会错乱；同时注意性能
					$('#dn_ns').append(tb_1 + tb_2 + tb_3);	

					//初始化表格，设按钮文字
					$("#t_ns").table({ columnBtnText: "显示列" });
					
				},//--ns
				
				//3.---sub of render
				pop_fpgz:function(d){
					$("#dn_fpgz").remove();	
       	   var head= '<div id="dn_fpgz" class="mytitle datanode gen" style="display:none"> '+
       	   '发票购置情况'+
       	   '</div>';
       	  
       	   $('.ui-content').append(head);
						var tb_1=
						'<table data-role="table" id="t_fpgz" data-mode="columntoggle" class="ui-responsive table-stroke ui-body-d">'+
     				'<thead><tr class="ui-bar-b">'+
         		'<th data-priority="6">发票种类</th>'+
         		'<th data-priority="1">发票批次</th>'+
         		'<th data-priority="1" style="white-space:nowrap">计量单位</th>'+
         		'<th data-priority="2">发票号码起</th>'+
         		'<th data-priority="2">发票号码止</th>'+
         		'<th data-priority="1">数量</th>'+
         		'<th data-priority="3" width="120px">销售日期</th>'+
       			'</tr></thead><tbody>' ;
       			
       			var date1=new Date();
       		var tb_2=	""	
					//遍历该节点下的记录
					$.each(d,function(i, entity){
						 //处理单条发票购置记录的生成
						date1=entity.xsRq;
						tb_2=	tb_2 +
       		 	'<tr>'+
         		'<th>'+ entity.id.fpzl +'</th>'+
         		'<td>'+ entity.fppc +'</td>'+
         		'<td>'+ entity.jldw +'</td>'+
         		'<td>'+ entity.id.fpQshm +'</td>'+
         		'<td>'+ entity.fpZzhm +'</td>'+
         		'<td>'+ entity.sl +'</td>' +
         		'<td>'+ date1 +'</td>'+    		
       			'</tr>';

					});//-- each

					var tb_3="</tbody></table>";
					//一次性添加内容（每添加一次都是对象，而非字符串，所以元素位置会错乱；同时注意性能
					$('#dn_fpgz').append(tb_1 + tb_2 + tb_3);	

					//初始化表格，设按钮文字
					$("#t_fpgz").table({ columnBtnText: "显示列" });

					
				},//--fpgz
				
				//3.---sub of render
				pop_sbfjf:function(d){
					$("#dn_sbfjf").remove();	
       	   var head= '<div id="dn_sbfjf" class= "mytitle datanode gen" style="display:none"> '+
       	   '社保缴纳情况'+
       	   '</div>';
       	  
       	   $('.ui-content').append(head);
						var tb_1=
						'<table data-role="table" id="t_sbfjf" data-mode="columntoggle" class="ui-responsive table-stroke ui-body-d">'+
     				'<thead><tr class="ui-bar-b">'+
         		'<th data-priority="6">征收项目</th>'+
         		'<th data-priority="1">征收品目</th>'+
         		'<th data-priority="1" style="white-space:nowrap">申报方式</th>'+
         		'<th data-priority="2">税款属性1</th>'+
         		'<th data-priority="2">税款属性2</th>'+
         		'<th data-priority="1">税费属性</th>'+
         		'<th data-priority="3" width="120px">所属期</th>'+
         		'<th data-priority="3">总金额</th>'+
       			'</tr></thead><tbody>' ;
       			
       		var tb_2=	""	
					//遍历该节点下的记录
					$.each(d,function(i, entity){
						 //处理单条记录的生成
						entity=entity.id;
						tb_2=	tb_2 +
       		 	'<tr>'+
         		'<th>'+ entity.zsxm +'</th>'+
         		'<td>'+ entity.zspm +'</td>'+
         		'<td>'+ entity.sbfs +'</td>'+
         		'<td>'+ entity.sksx1 +'</td>'+
         		'<td>'+ entity.sksx2 +'</td>'+
         		'<td>'+ entity.sfsx +'</td>' +
         		'<td>'+ entity.ssq +'</td>'+         		         		
         		'<td>'+ entity.sum +'</td>'+    
       			'</tr>';

					});//-- each

					var tb_3="</tbody></table>";
					//一次性添加内容（每添加一次都是对象，而非字符串，所以元素位置会错乱；同时注意性能
					$('#dn_sbfjf').append(tb_1 + tb_2 + tb_3);	

					//初始化表格，设按钮文字
					$("#t_sbfjf").table({ columnBtnText: "显示列" });
					
				} //--sbfjf
				
			};
		