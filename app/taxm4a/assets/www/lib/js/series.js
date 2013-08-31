/*时间线
依赖 highchartstock.js --> jquery.js
1.init the chart container
  var chart = chartUtil.init("#contain");
2. add series
	var name = "chart1",
	data = [[Date.parse("2012-01-13"),23],[Date.parse("2012-01-13"),23]];
  chart.addSeries(name,data);
  
*/

/*seriesExtra 从数据中抽取时间序列
	data 数据
	exp_entity 	取单个元素的表达式，eg: "data[i].id" || "data[i]" 
	exp_time 		取单个元素中时间的表达式，eg:"entity.ssq";
	exp_quality 取单个元素中数据的表达式，eg:"entity.sum"
*/
function seriesExtra(data, exp_entity, exp_time, exp_quality){
	var cdata = [];

		//eg: [["2013-06-14",123],["2013-07-14",32]]
		var d=[]
		
		for(var i=0 ; i<data.length ; i++){
			var entity = eval(exp_entity);// exp_entity eg: "data[i].id" || "data[i]" //

			// "2013-06-14","1234"
			var time = Date.parse(eval(exp_time)); //exp_time eg:"entity.ssq";
			var sum = Math.round(parseFloat (eval(exp_quality)));//exp_quality eg:"entity.sum"
			//合并同一个时间的数量
			if(d.length>0 && time == d[0]){
				d[1] = d[1] + sum;
			}else{
				if(d.length >0 ) cdata[cdata.length] = d;
				d = [time, sum];
			}

		};//-for
		if(d.length >0 ) cdata[cdata.length] = d;//last element;
		
		return cdata;	
}
function addSeries(data,info_type){
	var cdata = [];
	/*
	if(info_type=="sbfjf"){
		//eg: [["2013-06-14",123],["2013-07-14",32]]
		var d=[]
		for(var i=0;i<data.length; i++){
			entity = data[i].id;//

			// "2013-06-14","1234"
			var time = Date.parse(entity.ssq);
			var sum = Math.round(parseFloat (entity.sum));
			//合并同一次交纳的
			if(d.length>0 && time == d[0]){
				d[1] = d[1] + sum;
			}else{
				if(d.length >0 ) cdata[cdata.length] = d;
				d = [time, sum];
			}

		};//-for
		if(d.length >0 ) cdata[cdata.length] = d;//last element;
		

	}
	
	if(info_type=="ns"){
		//eg: [["2013-06-14",123],["2013-07-14",32]]
		var d=[]
		for(var i=0;i<data.length; i++){
			entity = data[i].id;//

			// "2013-06-14","1234"
			var time = Date.parse(entity.sfssqZzrq);
			var sum = Math.round(parseFloat (entity.yzsfJe));
			//合并同一次交纳的
			if(d.length>0 && time == d[0]){
				d[1] = d[1] + sum;
			}else{
				if(d.length >0 ) cdata[cdata.length] = d;
				d = [time, sum];
			}

		};//-for
		if(d.length >0 ) cdata[cdata.length] = d;//last element;
		

	}*/
	if(info_type=="sbfjf") cdata = seriesExtra(data, "data[i].id", "entity.ssq", "entity.sum" );
	if(info_type=="ns") cdata = seriesExtra(data, "data[i].id", "entity.sfssqZzrq", "entity.yzsfJe" );
	if(info_type=="fpgz") cdata = seriesExtra(data, "data[i]", "entity.xsRq", "entity.sl" );
	if(info_type=="sb") cdata = seriesExtra(data, "data[i]", "entity.id.sfssqZzrq", "entity.sl" );
	
	
	if(cdata.length ==0) return;

	console.log(info_type +":" + cdata.toString());
	chartutil.add(info_type, cdata) ;

}

//图表工具
var chartUtil =  {
		isCreated:false,//已创建
		container:null,   
		  
	//初始化 
		init:function(c){                   
		Highcharts.setOptions({
			lang: {
				months: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
				shortMonths:['1','2','3','4','5','6','7','8','9','10','11','12'],
				rangeSelectorFrom:'从',rangeSelectorTo:'至',rangeSelectorZoom:'选项',resetZoom:'重置',
				weekdays:['周1','周2','周3','周4','周5','周6','周7']
			}
		});
		this.container = $(c);
		return this;
	},
	
	//排序
	sortme:function(a,b){
		return a[0]-b[0];
	},
	
	//添加
	add:function(name, data){
		data.sort(this.sortme);

		//已经存在图表，添加线
		if(this.isCreated){
			 this.container.highcharts().addSeries({name:name, data:data});
			 return ;
			}
		// Create the chart
		this.container.highcharts('StockChart', {
			credits : {text:"时间线",href:"#"},
			chart : {
				height:200,
				type:'line'
			},

			rangeSelector : {
				selected : 1,inputDateFormat: '%Y-%m-%d ',
				buttons: [{
					type: 'month',
					count: 1,
					text: '1月'
				}, {
					type: 'month',
					count: 6,
					text: '半年'
				}, {
					type: 'ytd',
					text: '当年'
				}, {
					type: 'year',
					count: 1,
					text: '1年'
				}, {
					type: 'all',
					text: '全部'
				}]
			},

			title : {
				text : ""
			},
			
			xAxis :{
				dateTimeLabelFormats:
				{
				millisecond: '%H:%M:%S.%L',
				second: '%H:%M:%S',
				minute: '%H:%M',
				hour: '%H:%M',
				day: '%m-%d',
				week: '%m-%d',
				month: '%Y-%m-%d',
				year: '%Y'
				}
			},

				
			series : [{
				name : name,
				data : data,
				marker : {
					enabled : true,
					radius : 3
				},
				shadow : true,
				tooltip : {
					valueDecimals : 2
				}
			}]
		});
		this.isCreated= true;
	}//- add
}; //- hicharts



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