var requestDecorateMsg = {
    url: URL_SUBMIT_DECORATE,
    callbackMethod: submitInfoResult,
    data: ""
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



var onePageSize = 20;   //请求服务器每页数据的大小
var currPage = 1;       //当前第几页
var totalPage;     //总页数
var finishedTotalPage;     //总页数
var totalcount;   //总条数
var visiblepage = 10;   //显示的页面按钮数

var isinit = 0;  //判断是否是初始化


	//楼栋号 单元号 房间号
    var buildNo = 0;
    var uintNo = 0;
    var roomNo = 0;



$(document).ready(function () {

   
   getBuildData();//初始化楼栋号

		$('#decoration_build').change(function () {

			    buildNo = $(this).children('option:selected').val();
					
			    getUnitData();
				
			});

	$('#decoration_unit').change(function () {
			    unitNo = $(this).children('option:selected').val();
			    getRoomData();
			});



    $("#decorate_submmit").click(function () {

        if ($("#decorate_ownername").val() == '' || $("#decorate_phone").val() == ''|| $("#decoration_build").val() == '0') {
            alert("业主姓名和电话不能为空");
        }
        else {
			if(confirm("是否确认提交装修信息？"))
              {
                submitDecorateData();
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
		   fillSelect(data,"#decoration_build");
		}
	
		function setUnitSelect(data) {
				
			
		    $("#decoration_unit").empty();
		    $("#decoration_room").empty();

			$("#decoration_room").append("<option value='" + 0 + "'>" + "---请选择---" + "</option>");
		  
			fillSelect(data,"#decoration_unit");
		}

		function setRoomSelect(data) {
		     $("#decoration_room").empty();
		    fillSelect(data,"#decoration_room");
		}



function submitDecorateData() {
    
    //提交表单



    var owner = $("#decorate_ownername").val();
    var telephone = $("#decorate_phone").val();
	

	//////房间号这一部分的后台还没改好	//////
	//var build = $("#decoration_build").val();
	//var unit = $("#decoration_unit").val();
	//var room = $("#decoration_room").val();
	
	var isSimple = $("#isSimple").val();
	var hasProtocol = $("#hasProtocol").val();
    var hasForm = $("#hasForm").val();
    var hasClicence = $("#hasClicence").val();
    var isComplete= $("#isComplete").val();

    var remark = $("#remarks").val();
	
		
		var formData ={"decorate.roomnumber":"1-1-401","decorate.ownername":owner,"decorate.phone":telephone,
			"decorate.isjianzhuang":isSimple,"decorate.hasdecprotocol":hasProtocol,"decorate.hasdecform":hasForm,
			"decorate.hasdeclicence":hasClicence,"decorate.iscomplete":isComplete,"decorate.remarks":remark};

    requestDecorateMsg.data = formData;
  // requestDecorateMsg.data = loadformdata($("#uploadForm")[0]);
    requestDecorateMsg.callbackMethod = submitInfoResult;
    getData(requestDecorateMsg);
}





function submit_DecorateData() {

    var notice_title = $("#notice_title").val();

    var notice_build = $("#notice_build").val();
    var notice_unit = $("#notice_unit").val();
    var notice_room = $("#notice_room").val();

    var notice_author = $("#notice_author").val();

    var notice_content = $("#notice_content").val();

    //var formData = new FormData($("#uploadForm")[0]);

    //alert(document.getElementById("uploadForm").value);

    var formData = { "notice.title": notice_title, "notice.author": notice_author,
        "notice.content": notice_content, "buildNo": notice_build, "unitNo": notice_unit, "roomNo": notice_room
    }

    var urlString = requestNoticeMsg.url;

    var callBackName = requestNoticeMsg.callbackMethod;

    requestDecorateMsg.data = formData;



    getData(requestDecorateMsg);
}

function submitInfoResult(result) {
    if (result.success == true) {
        alert("提交信息成功");

    }
    else {
        alert("提交信息失败" + result.type);
    }

}

function loadformdata(formid) {//自动生成键值对对象
    var formData = {};
    var i = $("#" + formid + " [name]").length;
    for (var a = 0; a < i; a++) {
        var name = $("#" + formid + " [name]").eq(a).attr("name");
        var value = $("#" + formid + " [name]").eq(a).val();
        formData[name] = value;
        //console.log(formData);
    }
    return formData;
};

