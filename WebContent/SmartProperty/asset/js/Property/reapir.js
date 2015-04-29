
//请求设备名称对象
var	requestDevicesMsg ={
	url: "../../repair/listDevices.action",
	callbackMethod:"",
	data:""
};
//设备名称请求结果对象
var responseDevicesMsg={
	result:""
	
};
//请求设备状况对象
var	requestDevicesProblemMsg ={
	url: "../../repair/listDeviceFault.action",
	callbackMethod: "",

	data:{
	   	id:""
	}
};

//设备状况结果对象
var responseDevicesProblemMsg={
	result:""
};
var requestRrepairMsg={
	url: "../../repair/submitRepairInfo.action",
	callbackMethod:submitInfoResult,
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

var buildNo = 0;
var uintNo = 0;
var roomNo = 0;

$(document).ready(function() {

	//initRoomSelect(); // 初始化房号select
	getDevicesData();
	hideOrShowInput();

	getBuildData();


	$('#search_build').change(function () {

	    buildNo = $(this).children('option:selected').val();

	    getUnitData();

	});

	$('#search_unit').change(function () {
	    unitNo = $(this).children('option:selected').val();
	    getRoomData();
	});

	$("#button_submmit").click(function() {
		submitRepairData();
	});

	$("#button_reset").click(function() {
		//$("#preview").text("");
		$("#button_submmit").removeAttr("disabled");
	});

	$("#devicename").change(function() {
		//var deviceName = $("#devicename").val();
	    hideOrShowInput();

	    $("#deviceproblem").empty();
	    $("#deviceproblem").append("<option value='" + 0 + "'>" + "---请选择---" + "</option>");

		var deviceId = $("#devicename option:selected").attr("id");
		if(deviceId!="pleaseselect" )
		{
			if(deviceId!="other")
			{
				getDeviceProblemData(deviceId);
			}
			else {
			    $("#deviceproblem").append(
               "<option value='其他'>" + "其他" + "</option>");
			}
		}
		
	});

	$("#deviceproblem").change(function() {
		//var deviceName = $("#devicename").val();
		hideOrShowInput();

	});

});

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

function setBuildSelect(data) {
    fillSelect(data, "#search_build");
}

function setUnitSelect(data) {


    $("#search_unit").empty();
    $("#search_room").empty();

    $("#search_room").append("<option value='" + 0 + "'>" + "---请选择---" + "</option>");
    fillSelect(data, "#search_unit");
}

function setRoomSelect(data) {
    $("#search_room").empty();
    fillSelect(data, "#search_room");
}



//显示或隐藏input
function hideOrShowInput()
{
	var deviceName = $("#devicename").val();
	if(deviceName=="其他")
	{
		$("#otherDeviceDiv").show();
	}
	else
	{
		$("#otherDeviceDiv").hide();
	}

	var description = $("#deviceproblem").val();
	if(description=="其他")
	{
		$("#otherProblemDiv").show();
	}
	else
	{
		$("#otherProblemDiv").hide();
	}

}

//设置请求得到的设备名称信息
function setResponseDeviceMsg(data)
{
	responseDevicesMsg.result=data;

}

//设置请求得到的设备问题信息
function setResponseDevicesProblemMsg(data)
{
	responseDevicesProblemMsg.data=data;
}


// 获取设备名称数据
function getDevicesData() {

	requestDevicesMsg.callbackMethod=setDeviceData;
	
	getData(requestDevicesMsg);
}

//设置显示设备名称select
function setDeviceData(data)
{
	setResponseDeviceMsg(data);
	if(data.success==true)
	{
		for (var i = 0; i < data.result.length; i++) {
			$("#devicename").append(
				"<option id='" + data.result[i].id + "' value='"+ data.result[i].devicename + "'>"+ data.result[i].devicename + "</option>");
		}
		$("#devicename").append("<option id='other' value='其他'>其他</option>");
		
	}
	else
	{
		alert("获取信息失败！");
	}
	
}


// 获取设备问题描述select数据
function getDeviceProblemData(deviceId) {
 
	requestDevicesProblemMsg.callbackMethod=setDeviceProblemData;
	//requestDevicesProblemMsg.data.id=deviceId;   //这里可能有问题
	var sendData={id:deviceId};
	requestDevicesProblemMsg.data=sendData;
	
//	var sendMsg=$.toJSON(requestDevicesProblemMsg.data);

	getData(requestDevicesProblemMsg);   
}

//设置显示设备问题描述select
function setDeviceProblemData(data)
{

	setResponseDevicesProblemMsg(data);

	if(data.success==true)
	{
		
		$("#deviceproblem").empty();
		$("#deviceproblem").append(
				"<option value='pleaseSelect'>---请选择---</option>");
		for (var i = 0; i < data.result.length; i++) {
			$("#deviceproblem").append(
					"<option id='" + data.result[i].id + "' value='"
							+ data.result[i].devicestate + "'>"
							+ data.result[i].devicestate + "</option>");
		}
		$("#deviceproblem").append("<option id='other' value='其他'>其他</option>");
	}
	else
	{
		alert("获取信息失败！");
	}

}


//提交报修信息
function submitRepairData() {

	var deviceName = $("#devicename").val();
	var description = $("#deviceproblem").val();


	//alert(images);
	if(deviceName=="其他")
	{
	    var sl = $("#devicename");
	    var ops = sl.find("option");
	    ops.eq(0).val($("#otherDeviceName").val()).text($("#otherDeviceName").val()).prop("selected", true);
	    //alert($("#devicename").val());

	}

	if(description=="其他")
	{
	    var s2 = $("#deviceproblem");
	    var ops2 = s2.find("option");
	    ops2.eq(0).val($("#otherDeviceProblem").val()).text($("#otherDeviceProblem").val()).prop("selected", true);
	}


	//alert(roomid + " " + userName + " " + phonenumber + " " + deviceName + " "+ description);
	//setRequestSubmitRepairMsg();

	var uploadForm = $("#uploadForm");
  
    //var sendMsg = uploadForm.serializeArray();
    //var formData={"repair.username":userName,"repair.roomnumber":roomid,"repair.devicename":deviceName,"repair.phone":phonenumber,"repair.description":description};
	//var sendMsg=$.toJSON(repairMsg.data);
    
	var urlString=requestRrepairMsg.url;
	var callBackName=requestRrepairMsg.callbackMethod;

	var formData = new FormData($("#uploadForm")[0]);

	//alert(formData['repair.devicename']+"ppppp");

	requestRrepairMsg.data=formData;

	getDataWithFileUpload(requestRrepairMsg);
}

function submitInfoResult(result)
{
	if(result.success == true)
	{
		alert("提交信息成功");
	} 
	else
	{
		alert("提交信息失败  " + result.type);
	}
	
}