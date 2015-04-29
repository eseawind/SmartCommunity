var onePageSize = 10;   //请求服务器每页数据的大小
var currPage = 1;       //当前第几页
var totalPage;     //总页数
var finishedTotalPage;     //总页数
var totalcount;   //总条数
var visiblepage = 10;   //显示的页面按钮数

var isinit = 0;  //判断是否是初始化
var isinitFinished = 0;
var tableID1 = "#decorationCost_table";
var tableIDA = "decorationCost_table";
var tableBodyID1 = "#decorationCost_tableBody";
var pageid1 = "#pagination1";
var currentSpanId1 = "#sp1";

//装修费用对象
var decorationCostMsg = {
    roomnumber: "",
    username: "",
    date: "",
    pledge: "",//押金
    trashCost: "",
    eleCost: "",
    waterCost: "",
    keycardCost: "",
    conment: ""
}



$(document).ready(function () {
    getDecorationCostData(); 
});


function getDecorationCostData() {
    var postData = { roomnumber:"1-1-301",pageSize: onePageSize, pageNo: currPage };
    requestDecorationCostMsg.callbackMethod = setDecorationCostData;
    requestDecorationCostMsg.data = postData;
    getData(requestDecorationCostMsg);
}

//显示未完成的报修信息
function setDecorationCostData(data) {
    var dataArray = new Array(); //数据对象数组
    for (var i = 0; i < data.result.length; i++) {
        decorationCostMsg = new Object();
        decorationCostMsg.roomnumber = requestDecorationCostMsg.data.roomnumber;
        decorationCostMsg.username = data.result[i].ownername;
        decorationCostMsg.trashCost = data.result[i].rabishfee;
        decorationCostMsg.pledge = data.result[i].deposit;
        decorationCostMsg.eleCost = data.result[i].elecfee;
        decorationCostMsg.date = data.result[i].date;
        decorationCostMsg.waterCost = data.result[i].waterfee;
        decorationCostMsg.keycardCost = data.result[i].passfee;
        decorationCostMsg.conment = data.result[i].remarks;
        dataArray.push(decorationCostMsg);
    }
    var sequenceArray = new Array();//序列数组
    sequenceArray[0] = "roomnumber";
    sequenceArray[1] = "username";
    sequenceArray[2] = "data";
    sequenceArray[3] = "pledge";
    sequenceArray[4] = "trashCost";
    sequenceArray[5] = "eleCost";
    sequenceArray[6] = "waterCost";
    sequenceArray[7] = "keycardCost";
    sequenceArray[8] = "conment";
    totalPage = data.totalPage;
    $("#decorationCost_table tr").not("tr:first").remove(); //清空除第一行之外的表内容
    addTableBody("decorationCost_table", dataArray, sequenceArray); //调用公用添加table方法
}


 function updateRequestObj(num) {
    var postDataString = { pageSize: onePageSize, pageNo: num };
    requestUndoRepairMsg.data = postDataString;
    return requestUndoRepairMsg;
} 
