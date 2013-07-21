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
		  	//tx.executeSql("drop table if exists corp_jbxx",[]);
		    tx.executeSql("CREATE TABLE IF NOT EXISTS corp_jbxx(ID INTEGER PRIMARY KEY ASC, name text unique, content TEXT, update_time DATETIME)", []
		    ,dbutil.onSuccess);
		  });
		},
		/*写行*/
		writeRow: function( row , callback){
			var succ = callback? callback: dbutil.onSuccess;
			this.db.transaction(function (tx){
				tx.executeSql('replace into corp_jbxx(name, content, update_time) values(?, ?,?)', [ row.name,row.content, new Date().toISOString()],
					dbutil.onSuccess,
		      dbutil.onError);        
			});
		},
		/*缺省的异常回调函数*/
		onError: function(tx, e) {
		  alert("There has been an error: " + e.message);
		},
		/*缺省的回调函数*/
		onSuccess: function(tx, r) {
			console.log("success");
			console.log("result:"+r.rows.length);
		},
		/*用名称关键字查询*/
		findByName: function( keyword, callback) {
			this.db.transaction(function (tx){
				tx.executeSql("select id,name,content,update_time from corp_jbxx where name like "+"'%"+ keyword + "%'", [],
					callback,
		      dbutil.onError);        
			});
			
		}//-find by Name


	}
