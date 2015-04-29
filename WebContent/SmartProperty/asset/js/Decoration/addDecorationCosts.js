
//提交装修费用信息
var requestSubmitDecorationCostMsg = {
    url: URL_SUBMIT_DECORATE_FEE,
    callbackMethod: submitInfoResult,
    data: "",
}



//联动下拉列表楼栋，单元，房间号
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
var buildNo = "0";
var unitNo = "0";
var roomNo = "0";




$(document).ready(function () {

    getBuildData();//初始化楼栋号 
    setDatePicker();

    $('#search_build').change(function () {
			
                buildNo = $("#search_build").children('option:selected').val();
                $("#search_unit").empty();
		        $("#search_room").empty();
                $("#search_unit").append("<option value='0'>" + "---请选择---" + "</option>");
                $("#search_room").append("<option value='0'>" + "---请选择---" + "</option>");
                if(buildNo != "0")
                {
			        getUnitData();
                }
			});

	$('#search_unit').change(function () {
			 
                buildNo = $("#search_build").children('option:selected').val();
				unitNo = $("#search_unit").children('option:selected').val();
                $("#search_room").empty();
                $("#search_room").append("<option value='0'>" + "---请选择---" + "</option>");
                if(unitNo !="0") 
                {
                    getRoomData();
                }
			    
			});

            $("#button_submmit").click(function (){

				if($("#search_build").val()=='' ||$("#decoratefee_ownername").val()==''){
					alert("房间号和业主姓名内容不能为空！");
            }
            else{
				if(confirm("是否确认提交装修费用信息？"))
				{
					submitDecorationsCostData();
				}
			}  
                
             });
});




//提交装修费用信息
function submitDecorationsCostData() {
    //var formData = new FormData($("#uploadForm"));//是否正确
  //  var formobj =  document.getElementById("uploadForm");
  //  var formData = new FormData(formobj);
  // var formData ={"buildNo":"1","unitNo":"1","roomNo":"101","ownername":"cdx"};


		

		var ownername = $("#decoratefee_ownername").val();
		var deposit = $("#decoratefee_deposit").val();


		//var formData = {"notice.title":notice_title,"notice.author":notice_author,
			//"notice.content":notice_content,"buildNo":notice_build,"unitNo":notice_unit,"roomNo":notice_room}

		var formData ={"decoratefee.roomnumber":"1-1-001","decoratefee.ownername":ownername,"decoratefee.deposit":deposit};

		requestSubmitDecorationCostMsg.data = formData;
		getData(requestSubmitDecorationCostMsg);
}

function submitInfoResult(result) {
    if (result.success == true) {
        alert("提交信息成功");
    }
    else {
        alert("提交信息失败  " + result.type);
    }

}


////////日期设置函数。
    function setDatePicker()
    {  
		
            var myDate = new Date();
            var year = myDate.getFullYear();    //获取完整的年份(4位,1970-)
            var month = myDate.getMonth();       //获取当前月份(0-11,0代表1月)
            var data = myDate.getDate();        //获取当前日(1-31)
            document.getElementById("decorDate").value = year + "-" + (month + 1) + "-" + data;

            var ownerIntimeDatePicker = "#decorDate";  //家庭成员入住时间
            initDatepicker(ownerIntimeDatePicker);
    }

////////楼栋，单元，房间号部分设置函数。
        function getBuildData() {
		    var postData = {};
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
//联级下拉列表的回调函数
		function setBuildSelect(data) {
		   fillSelect(data,"#search_build");
		}
	
		function setUnitSelect(data) {
		    $("#search_unit").empty();
		    fillSelect(data,"#search_unit");
		}

		function setRoomSelect(data) {
		    $("#search_room").empty();
		    fillSelect(data,"#search_room");
		}


//以上为联动下拉列表楼栋，单元，房间号部分