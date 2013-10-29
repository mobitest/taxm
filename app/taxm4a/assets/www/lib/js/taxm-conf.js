var SERVER_ROOT="http://192.168.137.1:8080/core/" 
//jh
var SERVER_ROOT="http://192.168.0.102:8080/core/" 
//product
var  SERVER_ROOT="http://192.168.6.137/core/" 
//dev
//var SERVER_ROOT="http://localhost:8080/" 
//test
//var SERVER_ROOT="http://192.168.1.5:8080/"

var SERVICE_PATH= SERVER_ROOT + "services/api/" 
var JSP_PATH = SERVER_ROOT + "ajax/" 
	
var FILE_PATH = "../data/"
var DROP_TABLE = false; //重建缓存表
var DEBUG_LOCAL = false; //利用本地文件调试
var update_time = new Date().toISOString();
var fav = 0;//收藏
var TIMEOUT = 5000//超时上限
var UNKNOW_SCRATCH = "EOU*&))(&^*&*&";