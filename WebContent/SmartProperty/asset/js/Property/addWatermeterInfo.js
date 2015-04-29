
  var requestMeterMsg ={
    url: URL_SUBMIT_METER_INFO,
    callbackMethod :submitMeterInfoResult,
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


//条件查询的楼栋号 单元号 房间号
    var buildNo = "0";
    var uintNo = "0";
    var roomNo = "0";
 

  var onePageSize=20;   //请求服务器每页数据的大小
  var currPage=1;       //当前第几页
  var totalPage;     //总页数
  var finishedTotalPage;     //总页数
  var totalcount;   //总条数
  var visiblepage=10;   //显示的页面按钮数
    
  var isinit=0;  //判断是否是初始化
 

   $(document).ready(function(){

		//initBuiltSelect();
		//initUnitSelect();
		//initRoomSelect(); // 初始化房号select


		getBuildData();//初始化楼栋号

		$('#meter_build').change(function () {
			    buildNo = $(this).children('option:selected').val();
			    getUnitData();
			});

	$('#meter_unit').change(function () {
			    unitNo = $(this).children('option:selected').val();
			    getRoomData();
			});
	$('#meter_room').change(function () {
			    roomNo = $(this).children('option:selected').val();
			   
			});
		
		setDatePicker(); //初始化datepicker
	
	//var userid=$.cookie("USERID");
        //alert(userid);
														
        $("#meter_button").click(function(){ 
          
            if($("#meter_water").val()==''||$("meter_electric").val()=='')//||$("meter_man").val()==''
            {
				
                alert("内容不能为空！");
            }
            else
            {
				
              if(confirm("是否确认信息录入？"))
              {
				  
                  submit_MeterInfo();
              }
            }  
          
        }); 
    }); 
    

	function getBuildData() {
		    var postData = { pageSize: onePageSize, pageNo: currPage };
		    //requestBuildMsg.callbackMethod = setBuildData;
		    requestBuildMsg.data = postData;
		    getData(requestBuildMsg);
		}

		function getUnitData() {
		    var postData = {
		        "buildNo": buildNo
		        };
		    requestUnitMsg.data = postData;
		    getData(requestUnitMsg);
		}

		function getRoomData() {
		    var postData = {
		        "buildNo": buildNo,
		        "unitNo": unitNo
                
		    };
		    requestRoomMsg.data = postData;
		    getData(requestRoomMsg);
			}

			//下拉列表的回调函数
		function setBuildSelect(data) {
		   fillSelect(data,"#meter_build");
		}
	
		function setUnitSelect(data) {
			//alert("单元号具体的");
		    $("#meter_unit").empty();
		    $("#meter_room").empty();
			$("#meter_room").append("<option value='" + 0 + "'>" + "---请选择---" + "</option>");
		    fillSelect(data,"#meter_unit");
		}

		function setRoomSelect(data) {
		    $("#meter_room").empty();
		    fillSelect(data,"#meter_room");
		}


 function setDatePicker()
    {
        var ownerIntimeDatePicker="#meter_date";  
        initDatepicker(ownerIntimeDatePicker);
    }





     function submit_MeterInfo()
		 {
			 
			 
		//var formData = new FormData($("#uploadForm")[0]);
		//requestMeterMsg.data=formData;
		
		//getDataWithFileUpload(requestMeterMsg);

		

		var meter_build = $("#meter_build").val();
		var meter_unit = $("#meter_unit").val();
		var meter_room = $("#meter_room").val();
			
		var meter_water = $("#meter_water").val();

		var meter_electric = $("#meter_electric").val();
 
			

		var formData = {"buildNo":meter_build,"unitNo":meter_unit,
			"roomNo":meter_room,"waterconsum":meter_water,"elecconsum":meter_electric}

		var urlString=requestMeterMsg.url;

		var callBackName=requestMeterMsg.callbackMethod;

		requestMeterMsg.data=formData;

			alert(meter_water);
			alert(meter_electric);
			alert(meter_room);

		getData(requestMeterMsg);
		//alert("AAAAAASSSSS");
    }

function submitMeterInfoResult(result)
{
  if(result.success == true)
  {
    alert("水电表提交信息成功");
  
  } 
  else
  {
    alert("水电表提交信息失败" + result.type);
  }
  
}



// 初始化select
function initBuiltSelect() {

	// 房号select数据
	var buildid = new Array("0","1","2","3","4","5");

	for (var i = 0; i < buildid.length; i++) {
		$("#meter_build").append(
				"<option value='" + buildid[i] + "'>" + buildid[i] + "</option>");
	}

}
    // 初始化select
function initUnitSelect() {

	// 房号select数据
	var unitid = new Array("00","01","02","03");

	for (var i = 0; i < unitid.length; i++) {
		$("#meter_unit").append(
				"<option value='" + unitid[i] + "'>" + unitid[i] + "</option>");
	
	}

}

 // 初始化select
function initRoomSelect() {

	// 房号select数据
	var roomid = new Array("000","301", "302", "303", "304");

	for (var i = 0; i < roomid.length; i++) {
		$("#meter_room").append(
				"<option value='" + roomid[i] + "'>" + roomid[i] + "</option>");
	}

}