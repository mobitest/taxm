function showChart(d){
		chartUtil.init("#chart");
		chartUtil.add(d, "sbsjf");

}

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
			$("#t_" + info_type + " tr").hide();
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


// Source: http://stackoverflow.com/questions/497790
var dates = {
    convert:function(d) {
        // Converts the date in d to a date-object. The input can be:
        //   a date object: returned without modification
        //  an array      : Interpreted as [year,month,day]. NOTE: month is 0-11.
        //   a number     : Interpreted as number of milliseconds
        //                  since 1 Jan 1970 (a timestamp) 
        //   a string     : Any format supported by the javascript engine, like
        //                  "YYYY/MM/DD", "MM/DD/YYYY", "Jan 31 2009" etc.
        //  an object     : Interpreted as an object with year, month and date
        //                  attributes.  **NOTE** month is 0-11.
        return (
            d.constructor === Date ? d :
            d.constructor === Array ? new Date(d[0],d[1],d[2]) :
            d.constructor === Number ? new Date(d) :
            d.constructor === String ? new Date(d) :
            typeof d === "object" ? new Date(d.year,d.month,d.date) :
            NaN
        );
    },
    compare:function(a,b) {
        // Compare two dates (could be of any type supported by the convert
        // function above) and returns:
        //  -1 : if a < b
        //   0 : if a = b
        //   1 : if a > b
        // NaN : if a or b is an illegal date
        // NOTE: The code inside isFinite does an assignment (=).
        return (
            isFinite(a=this.convert(a).valueOf()) &&
            isFinite(b=this.convert(b).valueOf()) ?
            (a>b)-(a<b) :
            NaN
        );
    },
    inRange:function(d,start,end) {
        // Checks if date in d is between dates in start and end.
        // Returns a boolean or NaN:
        //    true  : if d is between start and end (inclusive)
        //    false : if d is before start or after end
        //    NaN   : if one or more of the dates is illegal.
        // NOTE: The code inside isFinite does an assignment (=).
       return (
            isFinite(d=this.convert(d).valueOf()) &&
            isFinite(start=this.convert(start).valueOf()) &&
            isFinite(end=this.convert(end).valueOf()) ?
            start <= d && d <= end :
            NaN
        );
    }
}