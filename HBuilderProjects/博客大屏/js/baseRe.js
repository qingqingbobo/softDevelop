function fnW(str) {
    var num;
    str >= 10 ? num = str : num = "0" + str;
    return num;
}

//中国地图开始
//var china_map =echarts.init(document.getElementById("china_map"),'macarons'); 
var china_map =echarts.init(document.getElementById("china_map"),'infographic'); 
//var china_map =echarts.init(document.getElementById("china_map"),'shine'); 


function randomData() {
		return Math.round(Math.random()*500);
	}
 
	var mydata = [
		{name: '北京',value: randomData() },{name: '天津',value: randomData() },
		{name: '上海',value: randomData() },{name: '重庆',value: randomData() },
		{name: '河北',value: randomData() },{name: '河南',value: randomData() },
		{name: '云南',value: randomData() },{name: '辽宁',value: randomData() },
		{name: '黑龙江',value: randomData() },{name: '湖南',value: randomData()},
		{name: '安徽',value: randomData() },{name: '山东',value: randomData() },
		{name: '新疆',value: randomData() },{name: '江苏',value: randomData() },
		{name: '浙江',value: randomData() },{name: '江西',value: randomData() },
		{name: '湖北',value: randomData() },{name: '广西',value: randomData() },
		{name: '甘肃',value: randomData() },{name: '山西',value: randomData() },
		{name: '内蒙古',value: randomData() },{name: '陕西',value: randomData()},
		{name: '吉林',value: randomData() },{name: '福建',value: randomData() },
		{name: '贵州',value: randomData() },{name: '广东',value: randomData() },
		{name: '青海',value: randomData() },{name: '西藏',value: randomData() },
		{name: '四川',value: randomData() },{name: '宁夏',value: randomData() },
		{name: '海南',value: randomData() },{name: '澳门',value: randomData() },
		{name: '香港',value: randomData() },{name: '台湾',value: randomData() }
	];
 
	var option = {
		//backgroundColor: '#FFFFFF',
		
		title: {
			text: '博客作者所在地分析',
			textStyle:{color:'#fff'},
			//subtext: '纯属虚构',
			x:'center'
		},
		tooltip : {
			trigger: 'item'
		},
		visualMap: {
			show : false,
			x: 'left',
			y: 'bottom',
			//layoutCenter:['30%','30%'],
			splitList: [ 
				{start: 500, end:600},{start: 400, end: 500},
				{start: 300, end: 400},{start: 200, end: 300},
				{start: 100, end: 200},{start: 0, end: 100},
			],
			color: ['#ff0', '#ffff00', '#0E94EB','#6FBCF0', '#F0F06F', '#00CC00']
		},
		series: [{
			name: '博客作者所在地分析',
			type: 'map',
			mapType: 'china', 
			roam: true,
			label: {
				normal: {
					show: false
				},
				emphasis: {
					show: false
				}
			},
			// data:mydata
		}]
	};

china_map.setOption(option);

// 请求网络,获取数据
axios.get("http://localhost:8080/getauthorbyprovince")
.then(function (response) {
	// console.log(response);
	// 设置图表的数据
	if (response.data.code * 1 == 200) {
		let optionData={
			// 设置数据集
			dataset: {
				source:response.data.result
			}
		}
		// 重新设置新的数据
		china_map.setOption(optionData);
	}
});
//中国地图结束



//获取当前时间
var timer = setInterval(function () {
    var date = new Date();
    var year = date.getFullYear(); //当前年份
    var month = date.getMonth(); //当前月份
    var data = date.getDate(); //天
    var hours = date.getHours(); //小时
    var minute = date.getMinutes(); //分
    var second = date.getSeconds(); //秒
    var day = date.getDay(); //获取当前星期几 
    var ampm = hours < 12 ? 'am' : 'pm';
    $('#time').html(fnW(hours) + ":" + fnW(minute) + ":" + fnW(second));
    $('#date').html('<span>' + year + '/' + (month + 1) + '/' + data + '</span><span>' + ampm + '</span><span>周六' + '</span>')

}, 1000)


//博客类别分析占比，带边框效果的饼图
//var pie_fanzui =echarts.init(document.getElementById("pie_fanzui"),'macarons'); 
var pie_fanzui =echarts.init(document.getElementById("pie_fanzui"),'infographic'); 
option = {
    title : {
        x:'center'
    },
    tooltip : {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {d}%"
    },
    legend: {
        orient: 'vertical',
        left: 'left',
        data: ['资讯','python','服务器','数学','其他类别'],
        textStyle: {color: '#fff'}
    },
    
	label: {
	     normal: {
	          textStyle: {
	                color: 'red'  // 改变标示文字的颜色
	          }
	     }
	},
    series : [
        {
            name: '博客类别分析占比',
            type: 'pie',
            radius : '55%',
            center: ['50%', '60%'],
            // data:[
            //     {value:335, name:'资讯'},
            //     {value:310, name:'编程语言'},
            //     {value:234, name:'python'},
            //     {value:135, name:'服务器'},
            //     {value:1548, name:'数据结构与算法'},
            //     {value:1548, name:'数据库'},
            //     {value:1548, name:'其他类别'}
            // ],
          
            itemStyle: {
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
            }
            
        }
    ]
};

pie_fanzui.setOption(option);

// 请求网络,获取数据
axios.get("http://localhost:8080/categories")
.then(function (response) {
	console.log(response);
	// 设置图表的数据
	if (response.data.code * 1 == 200) {
		let optionData={
			// 设置数据集
			dataset: {
				source:response.data.result
			}
		}
		// 重新设置新的数据
		pie_fanzui.setOption(optionData);
	}
});

//----------------------违法犯罪人员分析占比end---------------



//博客作者创龄分析占比，带边框效果的饼图
//var pie_age =echarts.init(document.getElementById("pie_age"),'macarons'); 
var pie_age =echarts.init(document.getElementById("pie_age"),'infographic'); 
//var pie_age =echarts.init(document.getElementById("pie_age"),'shine'); 
option = {
	title: {
		text: '作者创龄占比',
		left: 'center',
		top: 'center'
	},
    tooltip: {
        trigger: 'item',
        formatter: "{b}:{d}%",

    },
    legend: {
        orient: 'vertical',
        x: 'left',
        data:['0-9年','10-19年','20-29年','30-39年','40-49年'],
        textStyle: {color: '#fff'}
    },
    series: [
        {
            name:'博客作者创龄分布',
            type:'pie',
            radius: ['50%', '75%'],
            avoidLabelOverlap: true,
            label: {
                normal: {
                    show: false,
                },
                emphasis: {
                    show: true,
                    textStyle: {
                        fontSize: '20',
                        fontWeight: 'bold'
                    }
                }
            },
            labelLine: {
                normal: {
                    show: false
                }
            },
            // data:[
            //     {value:335, name:'0-9年'},
            //     {value:310, name:'10-19年'},
            //     {value:234, name:'20-29年'},
            //     {value:135, name:'30-39年'},
            //     {value:135, name:'40-49年'}
            // ]
        }
    ]
};
pie_age.setOption(option);

// 请求网络，获取数据
axios.get("http://localhost:8080/CreationAge")
.then(function (response) {
	// console.log(response);
	// 设置图表的数据
	if (response.data.code * 1 == 200) {
		let optionData={
			// 设置数据集
			dataset: {
				source:response.data.result
			}
		}
		// 重新设置新的数据
		pie_age.setOption(optionData);
	}
});

//----------------------违法犯罪人员年龄分析占比end---------------



//===================近七天发布博客统计=======================
//var line_time =echarts.init(document.getElementById("line_time"),'shine'); 
var line_time =echarts.init(document.getElementById("line_time"),'macarons'); 
//var line_time =echarts.init(document.getElementById("line_time"),'infographic'); 

var dataGenerator = function (dataArray) {
	// console.log("dataGenerator");
  var xAxisData = [];
  var seriesData = [];
  
  for (var i = 0; i < dataArray.length; i++) {
    var item = dataArray[i];
    xAxisData.push(item.date);
    seriesData.push(item.num);
  }
  
  var option = {
          // 给echarts图设置背景色
          //backgroundColor: '#FBFBFB',  // -----------> // 给echarts图设置背景色
          color: ['#7FFF00'],
          tooltip: {
              trigger: 'axis'
          },
         
  		grid:{
                      x:40,
                      y:30,
                      x2:5,
                      y2:20
                      
                  },
          calculable: true,
  
  
          xAxis: [{
              type: 'category',
  			data: xAxisData,
  			axisLabel: {
  			color: "#7FFF00" //刻度线标签颜色
  			}
          }],
          yAxis: [{
              type: 'value',
              axisLabel: {
  			color: "#7FFF00" //刻度线标签颜色
  			}
          }],
          series: [{
              name: '人次',
              type: 'line',
              data: seriesData
              
          }]
      };
  
  return option;
};

var json = [
  // {"date": "2023-08-19T16:00:00.000+00:00", "num": 2},
  // {"date": "2023-08-20T16:00:00.000+00:00", "num": 2},
  // {"date": "2023-08-21T16:00:00.000+00:00", "num": 1},
  // {"date": "2023-08-22T16:00:00.000+00:00", "num": 2},
  // {"date": "2023-08-23T16:00:00.000+00:00", "num": 1},
  // {"date": "2023-08-24T16:00:00.000+00:00", "num": 3},
  // {"date": "2023-08-25T16:00:00.000+00:00", "num": 1}
];

axios.get("http://localhost:8080/liveness")
.then(function (response) {
	// console.log(response);
	// 设置图表的数据
	if (response.data.code * 1 == 200) {
		// console.log("存 json 数据");
		json = response.data.result;
		// console.log(json);
		// console.log(json[0].date);
		
		var option = dataGenerator(json);
		// console.log(option);
		
		line_time.setOption(option);
		
	}
});


var option = {
        // 给echarts图设置背景色
        //backgroundColor: '#FBFBFB',  // -----------> // 给echarts图设置背景色
        color: ['#7FFF00'],
        tooltip: {
            trigger: 'axis'
        },
       
		grid:{
                    x:40,
                    y:30,
                    x2:5,
                    y2:20
                    
                },
        calculable: true,


        xAxis: [{
            type: 'date',
			data: result.xAxisData,
			axisLabel: {
			color: "#7FFF00" //刻度线标签颜色
			}
        }],
        yAxis: [{
            type: 'value',
            axisLabel: {
			color: "#7FFF00" //刻度线标签颜色
			}
        }],
        series: [{
            name: '人次',
            type: 'line',
            data: result.seriesData
            
        }]
    };


line_time.setOption(option);


//=========违法犯罪人员地区分布开始=======================
//var qufenbu_data =echarts.init(document.getElementById("qufenbu_data"),'shine'); 
//var qufenbu_data =echarts.init(document.getElementById("qufenbu_data"),'macarons'); 
var qufenbu_data =echarts.init(document.getElementById("qufenbu_data"),'infographic');
option = {
    color: ['#FADB71'],
    tooltip : {
        trigger: 'axis',
        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        }
    },
    grid: {
        x:30,
        y:10,
        x2:15,
        y2:20
    },
    xAxis : [
        {
            type : 'category',
            data : ['河北', '天津', '北京', '新疆', '内蒙', '宁夏', '海南'],
            axisTick: {
                alignWithLabel: true
            },
            axisLabel: {
			color: "#FADB71" //刻度线标签颜色
			}
        }
    ],
    yAxis : [
        {
            type : 'value',
            axisLabel: {
			color: "#FADB71" //刻度线标签颜色
			}
        }
    ],
    series : [
        {
            name:'地区分布',
            type:'bar',
            barWidth: '60%',
            data:[10, 52, 200, 334, 390, 330, 220]
        }
    ]
};

qufenbu_data.setOption(option);
//=========违法犯罪人员地区分布结束=======================


//时间选择器
var startV = '';
var endV = '';
laydate.skin('danlan');
var startTime = {
    elem: '#startTime',
    format: 'YYYY-MM-DD',
    min: '1997-01-01', //设定最小日期为当前日期
    max: laydate.now(), //最大日期
    istime: true,
    istoday: true,
    fixed: false,
    choose: function (datas) {
        startV = datas;
        endTime.min = datas; //开始日选好后，重置结束日的最小日期
    }
};
var endTime = {
    elem: '#endTime',
    format: 'YYYY-MM-DD',
    min: laydate.now(),
    max: laydate.now(),
    istime: true,
    istoday: true,
    fixed: false,
    choose: function (datas) {
        //        startTime.max = datas; //结束日选好后，重置开始日的最大日期
        endV = datas;
    }
};

laydate(startTime);
laydate(endTime);

//时间选择器
var startVs = '';
var endVs = '';
laydate.skin('danlan');
var startTimes = {
    elem: '#startTimes',
    format: 'YYYY-MM-DD',
    min: '1997-01-01', //设定最小日期为当前日期
    max: '2099-06-16', //最大日期
    istime: true,
    istoday: true,
    fixed: false,
    choose: function (datas) {
        startVs = datas;
        endTimes.min = datas; //开始日选好后，重置结束日的最小日期
        setQgData($('#barTypes').parent().parent(), 1);
    }
};
var endTimes = {
    elem: '#endTimes',
    format: 'YYYY-MM-DD',
    min: laydate.now(),
    max: laydate.now(),
    istime: true,
    istoday: true,
    fixed: false,
    choose: function (datas) {
        //        startTime.max = datas; //结束日选好后，重置开始日的最大日期
        endVs = datas;
        setQgData($('#barTypes').parent().parent(), 1);
    }
};

laydate(startTimes);
laydate(endTimes);




//更改日期插件的样式
function dateCss() {
    var arr = $('#laydate_box').attr('style').split(';');
    var cssStr =
        'position:absolute;right:0;';
    for (var i = 0; i < arr.length; i++) {
        if (arr[i].indexOf('top') != -1) {
            cssStr += arr[i];
        }
    }

    $('#laydate_box').attr('style', cssStr);
}


var workDate;
var time = {
    elem: '#times',
    format: 'YYYY-MM-DD',
    min: laydate.now(),
    max: laydate.now() + 30,
    istime: true,
    istoday: true,
    fixed: false,
    choose: function (datas) {
        //        startTime.max = datas; //结束日选好后，重置开始日的最大日期
        workDate = datas;
    }
};

laydate(time);



