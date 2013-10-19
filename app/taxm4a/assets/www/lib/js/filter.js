function showChart(d){
		chartUtil.init("#chart");
		chartUtil.add(d, "sbsjf");

}

//-----------------------已经废弃--begin
/*seriesExtra 从数据中抽取时间序列
	data 数据
	exp_entity 	取单个元素的表达式，eg: "data[i].id" || "data[i]" 
	exp_time 		取单个元素中时间的表达式，eg:"entity.ssq";
	exp_quality 取单个元素中数据的表达式，eg:"entity.sum"
	返回：时间 ， 计数， 完整时间
*/
function seriesExtra(data, exp_entity, exp_time, exp_quality){
	var cdata = [];

		//eg: [["2013-06-14",123],["2013-07-14",32]]
		var d=[]
		
		for(var i=0 ; i<data.length ; i++){
			var entity = eval(exp_entity);// exp_entity eg: "data[i].id" || "data[i]" //

			// "2013-06-14","1234"
			var time = eval(exp_time); //exp_time eg:"entity.ssq";
			time = time.substring(0,7)
			var fulltime = time;
			
			time = time.replace("-","");
			//var sum = Math.round(parseFloat (eval(exp_quality)) );//exp_quality eg:"entity.sum"
			var sum = 1;
			//合并同一次交纳的
			if(d.length>0 && time == d[0]){
				d[1] = d[1] + sum;
			}else{
				if(d.length >0 ) cdata[cdata.length] = d;
				d = [time, sum, fulltime];
			}

		};//-for
		if(d.length >0 ) cdata[cdata.length] = d;//last element;
		
		return cdata;	
}
/*
addFilter添加过滤条
data 原始数据
info_type 信息类型。主要用来从数据中提取信息
*/
function addFilter(data,info_type){
	var cdata = [];

	if(info_type=="sbfjf") cdata = seriesExtra(data, "data[i].id", "entity.ssq", "entity.sum" );
	if(info_type=="ns") cdata = seriesExtra(data, "data[i].id", "entity.sfssqZzrq", "entity.yzsfJe" );
	if(info_type=="fpgz") cdata = seriesExtra(data, "data[i]", "entity.xsRq", "entity.sl" );
	if(info_type=="sb") cdata = seriesExtra(data, "data[i]", "entity.id.sfssqZzrq", "1" );
	
	
	if(cdata.length ==0) return;

	console.log(info_type +" filter data:" + cdata.toString());
	filterUtil.add(info_type, cdata) ;

}

FilterUtil={
	lastClick:[],
	instance_:null,
	init:function(){
		instance_=this;
		return this;
	},
	//添加过滤按钮条
	add:function(info_type, data){
		var id="f_"+ info_type,
				s = "<div id='" + id + "' class='myfilter'>";
		$.each(data,function(i, d){
			var a_id = info_type + "_a_" + i;
			s = s + "<a id='"+ a_id + "' href='#'  title='" + d[2]+ "'>" + d[0] + "<span class='btntip'>"+ d[1]+ "" + "</span></a>";
//			s = s + "<a id='"+ a_id + "' href='#' title='" + d[2]+ "'>" + d[0] +   "</span></a>";
		});
		s = s + "</div>";
		$("#dn_"+info_type +" .mytitle").append(s);
		$( "#"+id + " a" ).buttonMarkup({ theme: "c",mini: true });
		
		//按下时，过滤数据
		$( "#"+id + " a" ).click(function(){
			//互斥按钮
			var lastclick= $("#dn_"+info_type).attr("lastFilter");
			if(lastclick && this.id != lastclick) $("#"+ lastclick).click();
			
			$("#t_" + info_type + " tr:contains('" + this.title +"')").toggle();
			$(this).toggleClass("ui-btn-up-e");
			
			$("#dn_"+info_type).attr("lastFilter", this.id);
		});
		$( "#" +id ).controlgroup({ type: "horizontal",mini:true});
		
		//缺省按第一个按钮
		for(var i=0 ;i<data.length; i++){
			$("#t_" + info_type + " tr:gt(0)").hide();
		}
		$("#"+info_type + "_a_0" ).click();
		
		/*
		//缺省过滤数据，在15条以内
		for(var i=0, counter=0, overflow=false ;i<data.length; i++){
			if(!overflow && (counter=counter + data[i][1]) > 15){
				overflow = true;
			}
			if(overflow) {
				console.log("default filtered:"+ info_type + ":"+ data[i][0] + "-count="+ data[i][1]+ ";like:"+ data[i][2]);
				$("#"+info_type + "_a_" + i).click();
			}
		}//-for
		*/	
		
	}//-add
}//-FilterUtil


//-----------------------已经废弃--end


function addFilter2(data, info_type){
	FilterUtil2.add(info_type, data) ;
}
//新过滤！
FilterUtil2 ={
		//添加过滤输入
	add:function(info_type, data){
		var ops = FilterUtil2.getFilterOptions(info_type, data);
		if(ops.label_from==null) return;
		
		//加输入区域
		var id="f2_"+ info_type,
				s = "<div id='" + id + "' class='myfilter'> ";
		var a_id0 = info_type + "_f_0",
				a_id1 = info_type + "_f_1";
		var label0 = ops.label_from,
				label1 = ops.label_to
		;
		//"<input id='jbxx' value='2013-01-01'/>
		var d0= ops.default_date_from,d1=ops.date_max;
		s = s + "<label>" + label0 + "</label>" + "<span class='holo'><input id='"+ a_id0 + "' name='"+ a_id0 +"' type=text style='width:90px' value='" + d0 + "' class='" + info_type + "_filter ui-body-c' readonly /></span>";
		s = s + "<label>" + label1 + "</label>" + "<span class='holo'><input id='"+ a_id1 + "' name='"+ a_id1 +"' type=text style='width:90px' value='" + d1 + "' class='" + info_type + "_filter  ui-body-c' readonly /></span>";
			
		s = s + "<label class='pal'>（已显示<span id='" + info_type + "_total_rows' class='rowscount'></span>中的<span id='" + info_type + "_filter_rows' class='rowscount'></span>条）</label></div>";
		$(s).appendTo("#dn_"+info_type +" .mytitle");//.trigger("create");
		//$("."+ info_type +"_filter").textinput();
		
		//处理输入交互
		var options = { theme: 'android-ics', mode: 'mixed', display: 'bubble', lang: 'zh',headerText: '{value}'};
      //$("#"+a_id0).scroller($.extend({preset : 'date'}, options ));		
      //$("#"+a_id1).scroller($.extend({preset : 'date'}, options ));		
      $("."+ info_type + "_filter").click(function(event) {
          var currentField = $(this);
          var myNewDate = Date.parse(currentField.val()) || new Date();
          if(typeof myNewDate === "number"){ myNewDate = new Date (myNewDate); }

          // Same handling for iPhone and Android
          window.plugins.datePicker.show({
              date : myNewDate,
              mode : 'date', // date or time or blank for both
              allowOldDates : true
          }, function(returnDate) {
              var newDate = new Date(returnDate);
              currentField.val(returnDate).trigger('change');

              // This fixes the problem you mention at the bottom of this script with it not working a second/third time around, because it is in focus.
              currentField.blur();
          });
      });	
      
		//输入改变值后
		$("."+ info_type + "_filter").change(function(){
			var min_dt = $("#"+a_id0).val(),
					max_dt = $("#"+ a_id1).val();
			
				var objs = $("#t_"+ info_type + " tr td:nth-child(" + ops.column_index + ")");
				var i,filtered=0;
				for(i=0;i<objs.length; i++){
					var o = objs[i];
					var v = $(o).text();
				
					if(v>max_dt || v< min_dt){
						 $(o).parent().hide();
					}else{
						 $(o).parent().show();
						 filtered++;
					}
				}//-for
			$("#" + info_type + "_filter_rows").text(filtered);
			$("#" + info_type + "_total_rows").text(objs.length);

			//$("#t_" + info_type + " tr:contains('" + this.value +"')").toggle();
			//$("#t_sbfjf tr td:nth-child(7)");
		});//- change
		
		//触发过滤
		$("."+ info_type + "_filter").trigger("change");
		
		
	},//- add
	/*根据信息类型与实际数据，取得过滤的设置*/
	getFilterOptions:function(info_type, data){
		/*
		date_max:最大日期
		date_min:最小日期
		column_index:日期列所在的位置（便于应用过滤）,eg:7
		label_from:
		label_to:
		default_date_from:缺省开始的日期
		exp:entity
		exp:date_field
		*/
		var ops = {date_max:"2012-01-01",date_min:"2012-03-01", column_index:7,label_from:null,label_to:"至"};
		var cdata = [];
	
		var exp = {entity:"data[i].id",// 	取单个元素的表达式，eg: "data[i].id" || "data[i]" 
						date_field:"entity.ssq"};// 		取单个元素中时间的表达式，eg:"entity.ssq";
		if(info_type=="sbfjf") {exp = {entity:"data[i].id", date_field:"entity.ssq"}; ops.label_from="所属期";ops.column_index=7;}
		if(info_type=="ns") {exp = {entity:"data[i].id",  date_field:"entity.sfssqZzrq"};ops.label_from="税费所属期"; ops.column_index=5;}
		if(info_type=="fpgz"){exp = {entity:"data[i]",  date_field:"entity.xsRq"};ops.label_from="销售日期";ops.column_index=7;}
		if(info_type=="sb") { exp = {entity:"data[i]",  date_field:"entity.id.sfssqZzrq"};ops.label_from="税费所属期";ops.column_index=3;}
		
		for(var i=0; i<data.length ; i++){
			var entity = eval(exp.entity);// exp_entity eg: "data[i].id" || "data[i]" //

			// "2013-06-14","1234"
			var date1 = eval(exp.date_field); //exp_time eg:"entity.ssq";
			if(ops.date_min> date1) ops.date_min= date1;
			if(ops.date_max< date1) ops.date_max= date1;
			
			//缺省至多显示10行（可能超出一些）
			if(i<10) ops.default_date_from = date1;
		}// -for

		return ops;
	
	}// - getFilterOptions
	
				
		
}//- FilterUtil2