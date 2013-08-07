var SERVER="http://localhost:8080";
var SERVICE_PATH = "/services/api/"


	dbutil={
		db:null,//���ݿ���
  	dbSize: 5 * 1024 * 1024, // 5MB
		init:function(){
			this.db = openDatabase("taxm", "1.0", "taxm manager", this.dbSize);
			return this;
		},
		/*����*/
		createTable:function(){
			this.db.transaction(function(tx) {
		  	//tx.executeSql("drop table if exists corp_jbxx",[]);
		    tx.executeSql("CREATE TABLE IF NOT EXISTS corp_jbxx(ID INTEGER PRIMARY KEY ASC, name text unique, content TEXT, update_time DATETIME)", [],dbutil.onSuccess);
		    tx.executeSql("CREATE TABLE IF NOT EXISTS corp_hd(ID INTEGER PRIMARY KEY ASC, name text unique, content TEXT, update_time DATETIME)", [],dbutil.onSuccess);
		    tx.executeSql("CREATE TABLE IF NOT EXISTS corp_ns(ID INTEGER PRIMARY KEY ASC, name text unique, content TEXT, update_time DATETIME)", [],dbutil.onSuccess);
		    tx.executeSql("CREATE TABLE IF NOT EXISTS corp_sb(ID INTEGER PRIMARY KEY ASC, name text unique, content TEXT, update_time DATETIME)", [],dbutil.onSuccess);
		    tx.executeSql("CREATE TABLE IF NOT EXISTS corp_fpgz(ID INTEGER PRIMARY KEY ASC, name text unique, content TEXT, update_time DATETIME)", [],dbutil.onSuccess);
		    tx.executeSql("CREATE TABLE IF NOT EXISTS corp_sbfjf(ID INTEGER PRIMARY KEY ASC, name text unique, content TEXT, update_time DATETIME)", [],dbutil.onSuccess);
		  });
		},
		/*д��*/
		writeRow: function( tablename, row , callback){
			var succ = callback? callback: dbutil.onSuccess;
			this.db.transaction(function (tx){
				tx.executeSql('replace into ' + tablename + '(name, content, update_time) values(?, ?,?)', [ row.name,row.content, new Date().toISOString()],
					dbutil.onSuccess,
		      dbutil.onError);        
			});
		},
		/*ȱʡ���쳣�ص�����*/
		onError: function(tx, e) {
		  alert("There has been an error: " + e.message);
		},
		/*ȱʡ�Ļص�����*/
		onSuccess: function(tx, r) {
			console.log("success");
			console.log("result:"+r.rows.length);
		},
		/*�����ƹؼ��ֲ�ѯ*/
		findByName: function( tablename, keyword, callback) {
			this.db.transaction(function (tx){
				tx.executeSql("select id,name,content,update_time from " + tablename + " where name like "+"'%"+ keyword + "%' order by update_time desc", [],
					callback,
		      dbutil.onError);        
			});	
		}//-find by Name


	}
