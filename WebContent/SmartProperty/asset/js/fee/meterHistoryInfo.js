
var requestMeterMsg = {
	url:URL_QUERY_METER_INFO,
    callbackMethod : setMeterData,
    data:""
 };

   
 var requestBuildMsg = {
    url: URL_QUERY_BUILD,
    callbackMethod: setBuildSelect,
    data: ""
};

var requestUnitMsg = {
    url: URL_QUERY_UNIT,
    callbackMethod: setUnitSelect,
    data: ""
};

var requestRoomMsg = {
    url: URL_QUERY_ROOM,
    callbackMethod: setRoomSelect,
    data: ""
};


  var onePageSize=20;   //请求服务器每页数据的大小
  var currPage=1;       //当前第几页
  var totalPage;     //总页数
  var finishedTotalPage;     //总页数
  var totalcount;   //总条数
  var visiblepage=10;   //显示的页面按钮数
    
  var isinit=0;  //判断是否是初始化
 
  var tableID="#meter_history_table";
  var tableBodyID="#meter_history_tableBody";
  var pageid="#pagination";
  var currentSpanId="#sp"; 


    var MeterMsg={
      roomnumber:"" ,
      metertype:"" ,
      state:"" ,
      readdata:"" ,
      readingtime:"" ,
      consumedata:"" ,
     
    }

	//楼栋号 单元号 房间号
    var buildNo = 0;
    var uintNo = 0;
    var roomNo = 0;

	$(document).ready(function(){ 
			
			var datepicker="#meter_history_start";
	        initDatepicker(datepicker);
			var date="#meter_history_end";
	        initDatepicker(date);
		
			getBuildData();//初始化楼栋号


			$('#meter_history_build').change(function () {
			    buildNo = $(this).children('option:selected').val();
					
			    getUnitData();
				
			});
			$('#meter_history_unit').change(function () {
			    unitNo = $(this).children('option:selected').val();
			    getRoomData();
			});



		//initBuiltSelect();
		//initUnitSelect();
		//initRoomSelect(); // 初始化房号select
		initMeterType();
		initMeterState();
		// setDatePicker(); //初始化datepicker
			
        $("#meter_history_search").click(function(){ 

  
            if($("#meter_history_build").val()=="0")
            {
               //alert("查询所有水电信息");
				getAllMeterData();						//如果不选任何参数，获取所有的水电表信息
            }
			else 
			{
                 getMeterData();
				
            }			
        }); 	
  
    }); 

function getBuildData() 
{
		   
	var postData = { pageSize: onePageSize, pageNo: currPage };
	requestBuildMsg.data = postData;
	getData(requestBuildMsg);

}

function getUnitData() {
		    var postData = {
		        "buildNo": buildNo
		        };
			
		    requestUnitMsg.data = postData;
		    getData(requestUnitMsg);
			//alert("SSSSSS");
			
		}

function getRoomData() {
		    var postData = {
		        "buildNo": buildNo,
		        "unitNo": unitNo
                //调试使用，需要修改
		       // "buildNo":1,
                //"unitNo":101
		    };
		    requestRoomMsg.data = postData;
		    getData(requestRoomMsg);
		}


		function setBuildSelect(data) {
		   fillSelect(data,"#meter_history_build");
		}
	
		function setUnitSelect(data) {
				
				//alert("QQQQQ");

		    
			
		    $("#meter_history_unit").empty();
		    $("#meter_history_room").empty();
			$("#meter_history_room").append("<option value='" + 0 + "'>" + "---请选择---" + "</option>");
		    fillSelect(data,"#meter_history_unit");

		   
		}

		function setRoomSelect(data) {
		   
		    $("#meter_history_room").empty();
		     fillSelect(data,"#meter_history_room");
		}


function getMeterData()
        {

		$("#meter_history_table").dataTable().fnDestroy();//防止重复初始化表格

			var postData={pageSize:onePageSize};

			//var formData = new FormData($("#uploadForm")[0]);


			var build= $("#meter_history_build").val();
			var unit = $("#meter_history_unit").val();
			var room = $("#meter_history_room").val();
  
			//alert("查询具体的水电信息");
			var formData = {"buildNo":build,"unitNo":unit,"roomNo":room}
			requestMeterMsg.data=formData;

			requestMeterMsg.callbackMethod=setMeterData;

			//getDataWithFileUpload(requestMeterMsg);
			
			getData(requestMeterMsg);
			
        }
function getAllMeterData()
{
			$("#meter_history_table").dataTable().fnDestroy();//防止重复初始化表格

			//var postData={pageSize:onePageSize};

			//var formData = new FormData($("#uploadForm")[0]);
			var build= $("#meter_history_build").val();
			var unit = $("#meter_history_unit").val();
			var room = $("#meter_history_room").val();
  
			//alert(room);
			 
			//var formData = {"build":"","unit":"","room":""}

			var postData={pageSize:onePageSize,pageNo:currPage};
			requestMeterMsg.data=postData;


			requestMeterMsg.callbackMethod=setMeterData;

		
			getData(requestMeterMsg);
}



	function setMeterData(data)
    {
      
      var dataArray=new Array(); //数据对象数组
      
	  for(var i=0;i<data.result.length;i++)
      {
        MeterMsg=new Object();

        MeterMsg.roomnumber=data.result[i].roomnumber;
        //MeterMsg.metertype=data.result[i].metertype;
		if(data.result[i].metertype==0)
		{
			MeterMsg.metertype="水表";
		}
		else
		  {
			MeterMsg.metertype="电表";
		}

        //MeterMsg.state=data.result[i].state;
		if(data.result[i].state==false)
		{
			MeterMsg.state="未审核";
		}
		else
		  {
			MeterMsg.state="已审核";
		}
        MeterMsg.readingtime=data.result[i].readingtime;
		MeterMsg.readdata=data.result[i].readdata;
        MeterMsg.consumedata=data.result[i].consumedata;
       // MeterMsg.immigratedate=data.result[i].immigratedate;
       
		

        dataArray.push(MeterMsg);
      }

      var sequenceArray=new Array();//序列数组
      sequenceArray[0]="roomnumber";
      sequenceArray[1]="metertype";
      sequenceArray[2]="state";
      sequenceArray[3]="readingtime";
      sequenceArray[4]="readdata";
      sequenceArray[5]="consumedata";
     

      $("#meter_history_tableBody tr").not("tr:first").remove(); //清空除第一行之外的表内容

      addTableBody("meter_history_table",dataArray,sequenceArray); //调用公用添加table方法
     
	 /* 
      if (!isinit) {
        pageChange(requestOwnerMsg,pageid,currentSpanId,totalPage,totalcount,visiblepage,onePageSize,currPage);
        isinit=1;
      }
	  */
	    $("#meter_history_table").dataTable({
				"aLengthMenu": [[8, 16, 32, 64], [8,16, 32, 64]],   
				"bFilter":true,   
				"bSort":true,  
				"bJQueryUI": false, 
				"iDisplayLength":8,   
				"bPaginate" : true,          //翻页功能
				"bLengthChange" :true,       //改变每页显示数据数量
				"aaSorting": [[ 3, "desc" ]],//设置第2个元素为默认排序     
				"aoColumnDefs" : [{  
			        "bSortable" : false, //指定不支持排序的列  
			           "aTargets" : [0]
			    }], 
				 
				"oLanguage":{    
					"sZeroRecords": "对不起，查询不到任何相关数据", 
   					"sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",    
					"sInfoEmpty" : "记录数为0",
					"sSearch": "搜索:", 
					"sInfoFiltered": "  ，共搜索 _MAX_ 条记录",
					"sLengthMenu": "每页显示 _MENU_ 条记录",   
					"oPaginate":{      
						"sNext":"下一页",     
						"sPrevious":"上一页"
					} 
  
				} 
			});
			

    }







 function setDatePicker()
    {
        var ownerIntimeDatePicker="#meter_history_start";  //业主入伙时间
        initDatepicker(ownerIntimeDatePicker);
    }


function initBuiltSelect() {

	// 房号select数据
	var buildid = new Array("0","1","2","3","4","5");

	for (var i = 0; i < buildid.length; i++) {
		$("#meter_history_build").append(
				"<option  value='" + buildid[i] + "'>" + buildid[i] + "</option>");
	}

}
    // 初始化select
function initUnitSelect() {

	// 房号select数据
	var unitid = new Array("0","1","2","3");

	for (var i = 0; i < unitid.length; i++) {
		$("#meter_history_unit").append(
				"<option value='" + unitid[i] + "'>" + unitid[i] + "</option>");
	
	}

}




 // 初始化select
function initRoomSelect() {

	// 房号select数据
	var roomid = new Array("101","102", "103");

	for (var i = 0; i < roomid.length; i++) {
		$("#meter_history_room").append(
				"<option value='" + roomid[i] + "'>" + roomid[i] + "</option>");
	}

}

function initMeterType() {

	// 房号select数据
	var metertype = new Array("水表","电表");

	for (var i = 0; i < metertype.length; i++) {
		$("#meter_history_type").append(
				"<option  value='" + metertype[i] + "'>" + metertype[i] + "</option>");
	}
}

function initMeterState() {

	// 房号select数据
	var meterstate = new Array("已审核","已录入","已收费");

	for (var i = 0; i < meterstate.length; i++) {
		$("#meter_history_state").append(
				"<option value='" + meterstate[i] + "'>" + meterstate[i] + "</option>");
	}
}


    function updateRequestObj(num)
    {
      var postDataString={pageSize:onePageSize,pageNo:num};
      requestElectricityMsg.data=postDataString;
      return requestElectricityMsg;
    }


     
    function updatePostString(num)
    {
      var postDataString="{pageSize:"+onePageSize+",pageNo:"+num+"}";

      return postDataString;
    }


/*

   function initMeterHistorySearch()
   {
            var html="";
            html += "<tbody>"; 
            for(var i=0;i<100;i++){
                html+="<tr>";   
                    html+="<td>"+"1栋2单元005房间"+"</td>";
                    html+="<td>"+"小明"+"</td>";   
                    html+="<td>"+"水表"+"</td>";   
                    html+="<td>"+"001"+"</td>"; 
                    html+="<td>"+"已收费"+"</td>";
                    html+="<td>"+"2015-01-01"+"</td>";   
                    html+="<td>"+"2015-01-28"+"</td>";   
                    html+="<td>"+"1"+"</td>"; 
                    html+="<td>"+"10"+"</td>";
                    html+="<td>"+"9"+"</td>";   
                    html+="<td>"+"2015-01-25"+"</td>";   
                    html+="<td>"+"小红"+"</td>"; 
                html+="</tr>"; 
            }       
            html += "</tbody>"; 
            $("#meter_history").append(html);

            $("#meter_history").dataTable({
                "aLengthMenu": [[6,12,18,24], [6,12,18,24]],   
                "bFilter":true,   
                "bSort":true,  
                "bJQueryUI": false, 
                "iDisplayLength":6,   
                "bPaginate" : true,   
                "bLengthChange" :true,
                "aaSorting": [[ 2, "desc" ]],//设置第2个元素为默认排序     

                "aoColumnDefs" : [{  
                    "bSearchable" : false,   
                       "aTargets" : [7,8]  
                },{  
                    "bSortable" : false, //指定不支持排序的列  
                       "aTargets" : [0,5,6,7,8] 
                }], 
                "oLanguage":{    
                    "sZeroRecords": "对不起，查询不到任何相关数据", 
                    "sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",    
                    "sInfoEmpty" : "记录数为0",
                    "sSearch": "搜索:", 
                    "sInfoFiltered": "  ，共搜索 _MAX_ 条记录",
                    "sLengthMenu": "每页显示 _MENU_ 条记录",   
                    "oPaginate":{      
                        "sNext":"下一页",     
                        "sPrevious":"上一页"
                    } 
                } 
            });
    
        }


    function submit_noticeInfo(){
      var meter_water = $("#meter_water").val();
	  var meter_electric = $("#meter_electric").val();
	  var meter_man = $("#meter_man").val();
	var meter_built = $("#meter_built").val();
	var meter_unit= $("#meter_unit").val();
	  var meter_room = $("#meter_room").val();

      $.ajax({
             type: "post",
             url: "../../notification/publishNotice.action",
             dataType: "json",
             data: {title:notice_title, object:notice_object, content:notice_content},
             success: function(data){
		    alert(data.success);
                    
		    if(data.success == true){ //根据返回值中的success的值来判断是否登录成功
				alert("水电信息录入！"); 
		    }else{          //验证失败
				alert("水电信息录入！"); 
		    }           
              },
              error: function (XmlHttpRequest, textStatus, errorThrown) {  
                  alert(textStatus);  
              } 

         });

	
    }
	*/