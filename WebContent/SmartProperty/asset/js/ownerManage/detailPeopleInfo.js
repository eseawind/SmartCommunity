var requestOwnerMsg = {
    url: URL_QUERY_ROOMOWNER,
    callbackMethod: setOwnerData,
    data: ""
};
var requestChangeMsg = {
    url: URL_CHANGE_ROOMOWNER,
    callbackMethod: changeOwnerData,
    data: ""
};
var onePageSize = 10;   //���������ÿҳ��ݵĴ�С
var currPage = 1;       //��ǰ�ڼ�ҳ
var totalPage;     //��ҳ��
var finishedTotalPage;     //��ҳ��
var totalcount;   //������
var visiblepage = 10;   //��ʾ��ҳ�水ť��

var isinit = 0;  //�ж��Ƿ��ǳ�ʼ��

var tableID = "#owner_table";
var tableBodyID = "#owner_tableBody";
var pageid = "#pagination";
var currentSpanId = "#sp";
//��¼��¥�����
var ownerMsg = {
   roomownerid:"",
   roomnumber: "",
   name: "",
   sex: "",
   type:"",
   identity: "",
   registeredresidence: "",
   telephone: "",
   propertyowner: "",
   propertyowneridentity: "",
   immigratedate: "",
   mail: "",
   qq: "",
   company: "",
   hobbies: "",
   nativeplace: "",
   nation: "",
   educationlevel: "",
   maritalstatus: "",
   quitdate:"",
   politicalaffiliation: ""
}

$(document).ready(function () {

    setDatePicker();
    var str = $.getUrlParam('roomnumber'); //����һ�ַ� 
    var strs = new Array(); //����һ���� 
    strs = str.split("-"); //�ַ�ָ� 
    getOwnerData(strs);
    //����޸İ�ť
    $("#save_change").click(function () {
        changeData();
    });
    $("#exit_button").click(function () {
        //window.location.href = 'searchOwnerInfo.html';
        window.history.back();
    });

});
////////�������ú���
function setDatePicker() {
    var ownerIntimeDatePicker = "#immigratedate";  //��ͥ��Ա��סʱ��
    initDatepicker(ownerIntimeDatePicker);
    var ownerOuttimeDatePicker = "#quitdate";  //��ͥ��Ա��סʱ��
    initDatepicker(ownerOuttimeDatePicker);
}
function getOwnerData(strs) {
    var postData = {
        buildNo:strs[0],
        unitNo:strs[1],
        roomNo:strs[2]
    };
    requestOwnerMsg.callbackMethod = setOwnerData;
    requestOwnerMsg.data = postData;
    getData(requestOwnerMsg);
}

		function setOwnerData(data) {
		        var dataArray = new Array(); //��ݶ�������
		        for (var i = 0; i < data.result.length; i++) {
		        ownerMsg = new Object();
		        ownerMsg.roomownerid = data.result[i].id;
		        ownerMsg.name = data.result[i].name;
		        ownerMsg.sex = data.result[i].sex;
		        ownerMsg.type = data.result[i].type;
		        ownerMsg.identity = data.result[i].identity;
		        ownerMsg.roomnumber = data.result[i].roomnumber;
		        ownerMsg.telephone = data.result[i].telephone;
		        ownerMsg.mail = data.result[i].mail;
		        ownerMsg.immigratedate = data.result[i].immigratedate;
		        ownerMsg.politicalaffiliation = data.result[i].politicalaffiliation;
		        ownerMsg.propertyowner = data.result[i].propertyowner;
		        ownerMsg.propertyowneridentity = data.result[i].propertyowneridentity;
		        ownerMsg.registeredresidence = data.result[i].registeredresidence;
		        ownerMsg.qq = data.result[i].qq;
		        ownerMsg.company = data.result[i].company;
		        ownerMsg.hobbies = data.result[i].hobbies;
		        ownerMsg.nativeplace = data.result[i].nativeplace;
		        ownerMsg.nation = data.result[i].nation;
		        ownerMsg.educationlevel = data.result[i].educationlevel;
		        ownerMsg.maritalstatus = data.result[i].maritalstatus;
		        ownerMsg.quitdate = data.result[i].quitdate;
                
		        dataArray.push(ownerMsg);
		        }
		        document.getElementById("roomownerid").value = dataArray[0].roomownerid;
		        document.getElementById("name").value = dataArray[0].name;
		        document.getElementById("sex").value = dataArray[0].sex;
		        document.getElementById("type").value = dataArray[0].type;
		        document.getElementById("identity").value =dataArray[0].identity;
		        document.getElementById("roomnumber").value = dataArray[0].roomnumber;
		        document.getElementById("telephone").value = dataArray[0].telephone;
		        document.getElementById("mail").value = dataArray[0].mail;
		        document.getElementById("immigratedate").value =dataArray[0].immigratedate;
		        document.getElementById("nativeplace").value = dataArray[0].nativeplace;
		        document.getElementById("registeredresidence").value = dataArray[0].registeredresidence;
		        document.getElementById("politicalaffiliation").value = dataArray[0].politicalaffiliation;
		        document.getElementById("nation").value = dataArray[0].nation;
		        document.getElementById("educationlevel").value = dataArray[0].educationlevel;
		        document.getElementById("company").value = dataArray[0].company;
		        document.getElementById("maritalstatus").value = dataArray[0].maritalstatus;
		        document.getElementById("hobbies").value =dataArray[0].hobbies;
		        document.getElementById("qq").value = dataArray[0].qq;
		        document.getElementById("quitdate").value = dataArray[0].quitdate;
		        document.getElementById("propertyowner").value = dataArray[0].propertyowner;
		        document.getElementById("propertyowneridentity").value = dataArray[0].propertyowneridentity;
		}
		function changeData()
		{
		    var formData = new FormData($("#uploadForm")[0]);
		    //alert(userID);
		    //formData.append("roomowner.id", userID);
		    //alert(formData["roomowner.id"]);
		    requestChangeMsg.data = formData;
		    getDataWithFileUpload(requestChangeMsg);
		}

		function updateRequestObj(num) {
		    var postDataString = { pageSize: onePageSize, pageNo: num };
		    requestOwnerMsg.data = postDataString;
		    return requestOwnerMsg;
		}



		function updatePostString(num) {
		    var postDataString = "{pageSize:" + onePageSize + ",pageNo:" + num + "}";

		    return postDataString;
		}

		function changeOwnerData(result) {
		    if (result.success == true) {
		        alert("修改成功");
		    }
		    else {
		        alert("修改失败\n" + result.type);
		    }

		}


(function($){ $.getUrlParam = function(name) 
        {
            var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if (r != null)
                return unescape(r[2]);
            return null;
        }
    })(jQuery);