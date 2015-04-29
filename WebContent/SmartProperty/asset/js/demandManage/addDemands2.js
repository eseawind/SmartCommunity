
var requestComplaintMsg = {
    url: URL_SUBMIT_COMPLAINT,
    callbackMethod: submitComplaint,
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

var buildNo = 0;
var uintNo = 0;
var roomNo = 0;

$(document).ready(function () {
	var myDate = new Date();
    var year = myDate.getFullYear();    //获取完整的年份(4位,1970-)
    var month = myDate.getMonth();       //获取当前月份(0-11,0代表1月)
    var data =  myDate.getDate();        //获取当前日(1-31)
    document.getElementById("time").value = year+"-"+(month+1)+"-"+data;
    var datepicker = "#time";
    initDatepicker(datepicker);

    getBuildData();

    $('#search_build').change(function () {
        buildNo = $(this).children('option:selected').val();
        getUnitData();

    });
    $('#search_unit').change(function () {
        buildNo = $('#search_build').children('option:selected').val();
        unitNo = $(this).children('option:selected').val();
        getRoomData();


    });
    $('#search_room').change(function () {
        roomNo = $(this).children('option:selected').val();
    });
    $("#button_submmit").click(function () {
        getDemandsData();
    });

    $("#button_reset").click(function () {
        $("#button_submmit").removeAttr("disabled");
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
    fillSelect(data, "#search_unit");

}

function setRoomSelect(data) {
    $("#search_room").empty();

    fillSelect(data, "#search_room");

}



//�ύ������Ϣ
function submitRepairData() {
    var roomid = $("#roomid").val();
    var userName = $("#username").val();
    var phonenumber = $("#phonenumber").val();
    var deviceName = $("#devicename").val();
    var description = $("#deviceproblem").val();
    var images = $("#imagesid").val();

    //alert(images);
    if (deviceName == "����") {
        deviceName = $("#otherDeviceName").val();
    }

    if (description == "����") {
        description = $("#otherDeviceProblem").val();
    }

    var uploadForm = $("#uploadForm");

    var urlString = requestRrepairMsg.url;
    var callBackName = requestRrepairMsg.callbackMethod;

    var formData = new FormData($("#uploadForm")[0]);
    requestRrepairMsg.data = formData;

    getDataWithFileUpload(requestRrepairMsg);

}

function submitInfoResult(result) {
    if (result.success == true) {
        alert("�ύ��Ϣ�ɹ�");
    }
    else {
        alert("�ύ��Ϣʧ��  " + result.type);
    }

}

function getDemandsData() {
    //var build = $("#search_build").val();
    //var unit = $("#search_unit").val();
    //var room = $("#search_room").val();
    //var roomnumber = build + "-" + unit + "-" + room;

    //alert(roomnumber);

    //var customerName = $("#name").val();
    //var customerPhone = $("#phone").val();
    //var demandTime = $("#time").val();
    //var remarks = $("#remarks").val();
    //var demandContent = $("#content").val();
    //var images = $("#imagesid").val();
    //var formData = {
    //    "roomnumber": roomnumber,
    //    "name": customerName,
    //    "phone": customerPhone,
    //    "time": demandTime,
    //    "remarks": remarks,
    //    "content": demandContent,
    //    "images": images
    //};
    //alert(formData['content']);
    var uploadForm = $("#uploadForm");

    var formData = new FormData($("#uploadForm")[0]);

    requestComplaintMsg.data = formData;
    getDataWithFileUpload(requestComplaintMsg);

}
function submitComplaint(result) {
    if (result.success == true) {
        alert("sucess");

    }
    else {
        alert("faulse" + result.type);
    }
}