var requestDemandsMsg = {
    url: URL_QUERY_COMPLAINT,
    callbackMethod: setDemands,
    data: ""
};
var requestDemandsMsg2 = {
    url: URL_QUERY_COMPLAINT,
    callbackMethod: setDemands2,
    data: ""
};

var onePageSize = 20;   //请求服务器每页数据的大小
var currPage = 1;       //当前第几页
var totalPage;     //总页数
var finishedTotalPage;     //总页数
var totalcount;   //总条数
var visiblepage = 10;   //显示的页面按钮数

var isinit = 0;  //判断是否是初始化
var isinitFinished = 0;
var tableID1 = "#undodemandtable";
var tableBodyID1 = "#undodemandtableBody";
var pageid1 = "#pagination1";
var currentSpanId1 = "#sp1";

var tableID2 = "#finishedRepair_table";
var tableBodyID2 = "#finisheReapir_tableBody";
var pageid2 = "#pagination2";
var currentSpanId2 = "#sp2";


//楼栋号 单元号 房间号


//获取信息
var demandsMsg = {
    roomnumber: "",
    name: "",
    phone: "",
    time: "",
    content: "",
    requiredresult: "",
    remarks: "",
    more: "",
    signHandled: "",
    id: ""
};
$(document).ready(function () {
    $('#undodemandtable tr').find('td:last').hide();//隐藏第一列

    //getDemandsData();
    getDemandsData2();

    $(document).on("click", ".reviewImage", function () {

        var UnFinishedRepairId = $(this).parents("tr").find('td:last').text();
        ///获得点击的行号
        var clickRow = $(this).parents("tr")[0].rowIndex - 1;

        ///加载图片
        loadPicture(UnFinishedRepairId);

        $('#sign_up').lightbox_me({
            centered: true,
            closeClick: false,
            onLoad: function () {
                $('.zoom').zoom();
                ///图片滚动显示slideBox
                $('#demo2').slideBox({});

                //$('.zoom').zoom({ on:'click' });
            }
        });
    });


});


var picUrl = new Array();	///全局图片路径

function loadPicture(UnFinishedRepairId) {
    $(ulID).empty();
    for (var i = 0; i < picUrl[UnFinishedRepairId].length; i++) {
        var url = picUrl[UnFinishedRepairId][i];

        $(ulID).append("<li><a  title=''><span class='zoom' ><img class='zoomBig' width='555' height='320' src='" + url + "'/></span></a></li>");

        ///旧方法
        //document.getElementById("ulID").innerHTML="<li><a href='' title=''><img src='"+picUrl[clickRow]+"'/></a></li>"+"<li><a href='' title=''><img src='"+picUrl[clickRow+1]+"'/></a></li>";
    }

    if (picUrl[UnFinishedRepairId].length == 0) {
        $(ulID).append("<label>图片不存在</label>");
    }


}


function getDemandsData() {
    var postData = {
        pageSize: onePageSize,
        pageNo: currPage,
        Finished: false
    };
    requestDemandsMsg.callbackMethod = setDemands;
    requestDemandsMsg.data = postData;
    getData(requestDemandsMsg);
}
function setDemands(data) {
    var dataArray = new Array(); //数据对象数组
    for (var i = 0; i < data.result.length; i++) {
        demandsMsg = new Object();

        demandsMsg.roomnumber = data.result[i].roomnumber;
        demandsMsg.name = data.result[i].name;
        demandsMsg.phone = data.result[i].phone;
        demandsMsg.time = data.result[i].time;
        demandsMsg.content = data.result[i].content;
        demandsMsg.requiredresult = data.result[i].requiredresult;
        demandsMsg.remarks = data.result[i].remarks;
        demandsMsg.id = data.result[i].id;
        demandsMsg.more = "<input type='button' class='reviewImage'  style='color:red;' value='查看图片' />";
        demandsMsg.signHandled = "<a href='#' onclick='signHandledClick(" + data.result[i].id + ")'>" + "标记已处理" + "</a>";

        ///获取图片的url
        picUrl[demandsMsg.id] = new Array();    //声明二维，每一个一维数组里面的一个元素都是一个数组；

        for (var j = 0; j < data.result[i].complaintsimages.length; j++) {
            picUrl[demandsMsg.id][j] = "../../" + data.result[i].complaintsimages[j].url;
        }

        dataArray.push(demandsMsg);

    }

    var sequenceArray = new Array();//序列数组

    sequenceArray[0] = "roomnumber";
    sequenceArray[1] = "name";
    sequenceArray[2] = "phone";
    sequenceArray[3] = "time";
    sequenceArray[4] = "content";
    sequenceArray[5] = "requiredresult";
    sequenceArray[6] = "remarks";
    sequenceArray[7] = "more";
    sequenceArray[8] = "signHandled";
    sequenceArray[9] = "id";

    totalPage = data.totalPage;
    $("#undodemandtableBody tr").not("tr:first").remove(); //清空除第一行之外的表内容
    addTableBody("undodemandtable", dataArray, sequenceArray); //调用公用添加table方法

    $("#undodemandtable").dataTable({
        "aLengthMenu": [[8, 16, 32, 64], [8, 16, 32, 64]],
        "bFilter": true,
        "bInfo": false,
        "bRetrieve": true,
        "bSort": true,
        "bJQueryUI": false,
        "iDisplayLength": 8,
        "bPaginate": true,          //翻页功能
        "bLengthChange": true,       //改变每页显示数据数量
        "aaSorting": [[1, "desc"]],//设置第2个元素为默认排序     
        "aoColumnDefs": [{
            "bSortable": false, //指定不支持排序的列  
            "aTargets": [0]
        }],

        "oLanguage": {
            "sZeroRecords": "对不起，查询不到任何相关数据",
            "sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
            "sInfoEmpty": "记录数为0",
            "sSearch": "搜索:",
            "sInfoFiltered": "  ，共搜索 _MAX_ 条记录",
            "sLengthMenu": "每页显示 _MENU_ 条记录",
            "oPaginate": {
                "sNext": "下一页",
                "sPrevious": "上一页"
            }

        }
    });
}
/////////
function signHandledClick(id) {

    var postData = {
        id: id,
        processedstate: 1
    };

    requestChangeRepairStateMsg.data = postData;

    getData(requestChangeRepairStateMsg);

}

function getDemandsData2() {
    var postData = {
        pageSize: onePageSize,
        pageNo: currPage,
        Finished: true
    };
    requestDemandsMsg2.callbackMethod = setDemands2;
    requestDemandsMsg2.data = postData;
    getData(requestDemandsMsg2);
}
function setDemands2(data) {
    var dataArray = new Array(); //数据对象数组
    for (var i = 0; i < data.result.length; i++) {
        demandsMsg = new Object();

        demandsMsg.roomnumber = data.result[i].roomnumber;
        demandsMsg.name = data.result[i].name;
        demandsMsg.phone = data.result[i].phone;
        demandsMsg.time = data.result[i].time;
        demandsMsg.content = data.result[i].content;
        demandsMsg.requiredresult = data.result[i].requiredresult;
        //demandsMsg.remarks = data.result[i].processedsituation;
        demandsMsg.processedsituation = "良好"
        dataArray.push(demandsMsg);

    }

    var sequenceArray = new Array();//序列数组

    sequenceArray[0] = "roomnumber";
    sequenceArray[1] = "name";
    sequenceArray[2] = "phone";
    sequenceArray[3] = "time";
    sequenceArray[4] = "content";
    sequenceArray[5] = "requiredresult";
    sequenceArray[6] = "processedsituation";


    totalPage = data.totalPage;
    $("#finishdemandtableBody tr").not("tr:first").remove(); //清空除第一行之外的表内容
    addTableBody("finishdemandtable", dataArray, sequenceArray); //调用公用添加table方法

    $("#finishdemandtable").dataTable({
        "aLengthMenu": [[8, 16, 32, 64], [8, 16, 32, 64]],
        "bFilter": true,
        "bInfo": true,
        "bRetrieve": true,
        "bSort": true,
        "bJQueryUI": false,
        "iDisplayLength": 8,
        "bPaginate": true,          //翻页功能
        "bLengthChange": true,       //改变每页显示数据数量
        "aaSorting": [[3, "desc"]],//设置第2个元素为默认排序     
        "aoColumnDefs": [{
            "bSortable": false, //指定不支持排序的列  
            "aTargets": [0]
        }],

        "oLanguage": {
            "sZeroRecords": "对不起，查询不到任何相关数据",
            "sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
            "sInfoEmpty": "记录数为0",
            "sSearch": "搜索:",
            "sInfoFiltered": "  ，共搜索 _MAX_ 条记录",
            "sLengthMenu": "每页显示 _MENU_ 条记录",
            "oPaginate": {
                "sNext": "下一页",
                "sPrevious": "上一页"
            }

        }
    });
}

//function initSelect()
//{
//	var myDate= new Date();
//	var startYear=myDate.getFullYear()-10;//起始年份
//	var endYear=myDate.getFullYear();//结束年份
//	var obj=document.getElementById('year')
//	for (var i=startYear;i<=endYear;i++)
//	{
//	    obj.options.add(new Option(i,i));
//	}

//    var month=new Array("1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月");
//    for(var i=0;i<month.length;i++)
//    {		
//    	$("#month").append("<option value='" + (i+1) + "'>" + month[i] + "</option>");
//    }
//} 


