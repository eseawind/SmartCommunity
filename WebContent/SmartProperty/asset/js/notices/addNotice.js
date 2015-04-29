var requestNoticeMsg ={
    url: URL_SUBMIT_NOTICE,
    callbackMethod : submitInfoResult,
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


	//楼栋号 单元号 房间号
    var buildNo = 0;
    var uintNo = 0;
    var roomNo = 0;


	$(document).ready(function(){

		//initBuiltSelect();
		//initUnitSelect();
		//initRoomSelect(); // 初始化房号select
	
	
		getBuildData();//初始化楼栋号

		$('#notice_build').change(function () {
			    buildNo = $(this).children('option:selected').val();
					
			    getUnitData();
				
			});

	$('#notice_unit').change(function () {
			    unitNo = $(this).children('option:selected').val();
			    getRoomData();
			});


        $("#notice_button_submmit").click(function(){ 
          
            if($("#notice_title").val()=='' ||$("#notice_content").val()=='')
            {
                alert("内容不能为空！");
            }
            else
            {
              if(confirm("是否确认发表通告？"))
              {
                  submit_NoticeInfo();
              }
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
		        //"buildNo":1,
               // "unitNo":101
		    };
		    requestRoomMsg.data = postData;
		    getData(requestRoomMsg);
		}


		function setBuildSelect(data) {
		   fillSelect(data,"#notice_build");
		}
	
		function setUnitSelect(data) {
				
				//alert("QQQQQ");

		    $("#notice_unit").empty();
		    $("#notice_room").empty();
			$("#notice_room").append("<option value='" + 0 + "'>" + "---请选择---" + "</option>");
		    fillSelect(data,"#notice_unit");
		}

		function setRoomSelect(data) {
		     $("#notice_room").empty();
		    fillSelect(data,"#notice_room");
		}



function setDatePicker()
    {
        var copydata="#complatetime";  //业主入伙时间
        initDatepicker(complatetime);
    }

	// 初始化select



    function submit_NoticeInfo(){

		var notice_title = $("#notice_title").val();

		var notice_build = $("#notice_build").val();
		var notice_unit = $("#notice_unit").val();
		var notice_room = $("#notice_room").val();

		var notice_author = $("#notice_author").val();

		var notice_content = $("#notice_content").val();
 
		//var formData = new FormData($("#uploadForm")[0]);

		//alert(document.getElementById("uploadForm").value);

		var formData = {"notice.title":notice_title,"notice.author":notice_author,
			"notice.content":notice_content,"buildNo":notice_build,"unitNo":notice_unit,"roomNo":notice_room}

		var urlString=requestNoticeMsg.url;

		var callBackName=requestNoticeMsg.callbackMethod;

		requestNoticeMsg.data=formData;



		getData(requestNoticeMsg);
    }

function submitInfoResult(result)
{
  if(result.success == true)
  {
    alert("提交信息成功");
  
  } 
  else
  {
    alert("提交信息失败" + result.type);
  }
  
}





function initBuiltSelect() {

	// 房号select数据
	var buildid = new Array("0","1","2","3","4","5");

	for (var i = 0; i < buildid.length; i++) {
		$("#notice_build").append(
				"<option value='" + buildid[i] + "'>" + buildid[i] + "</option>");
	}

}
    // 初始化select
function initUnitSelect() {

	// 房号select数据
	var unitid = new Array("00","01","02","03");

	for (var i = 0; i < unitid.length; i++) {
		$("#notice_unit").append(
				"<option value='" + unitid[i] + "'>" + unitid[i] + "</option>");
	
	}

}

 // 初始化select
function initRoomSelect() {

	// 房号select数据
	var roomid = new Array("000","301", "302", "303", "304");

	for (var i = 0; i < roomid.length; i++) {
		$("#notice_room").append(
				"<option value='" + roomid[i] + "'>" + roomid[i] + "</option>");
	}

}