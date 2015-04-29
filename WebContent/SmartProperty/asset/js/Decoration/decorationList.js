var requestDecorateListMsg = {
    url: URL_QUERY_DECORATE ,
    callbackMethod: setDecorationData,
    data: ""
};



var onePageSize = 10;   //请求服务器每页数据的大小
var currPage = 1;       //当前第几页
var totalPage;     //总页数
var finishedTotalPage;     //总页数
var totalcount;   //总条数
var visiblepage = 10;   //显示的页面按钮数

var isinit = 0;  //判断是否是初始化



//装修列表对象
var decorationMsg = {
    roomnumber: "",
    username: "",
    telephone: "",
    isSimple: "",
    hasProtocol: "",
    hasForm: "",
    hasClicence: "",
    isComplete: "",
    remark: ""
}



$(document).ready(function () {
    getDecorationData();
});


function getDecorationData() {

	$("#decoration_table").dataTable().fnDestroy();//防止重复初始化表格
    var postData = {pageSize:onePageSize,pageNo:currPage};
   
    requestDecorateListMsg.data = postData;

    getData(requestDecorateListMsg);
}

//显示未完成的报修信息
function setDecorationData(data) {

    var dataArray = new Array(); //数据对象数组
	//alert(data.result.length);
    for (var i = 0; i < data.result.length; i++) {

        decorationMsg = new Object();
		//alert(data.result[i].phone);
        decorationMsg.roomnumber =data.result[i].roomnumber;
        decorationMsg.username = data.result[i].ownername;
        decorationMsg.telephone = data.result[i].phone;
        decorationMsg.isSimple = data.result[i].isjianzhuang;
        decorationMsg.hasProtocol = data.result[i].hasdecprotocol;
        decorationMsg.hasForm = data.result[i].hasdecform;
        decorationMsg.hasClicence = data.result[i].hasdeclicence;
        decorationMsg.isComplete = data.result[i].iscomplete;
        decorationMsg.remark = data.result[i].remarks;

        dataArray.push(decorationMsg);
    }
    var sequenceArray = new Array();//序列数组

    sequenceArray[0] = "roomnumber";
    sequenceArray[1] = "username";
    sequenceArray[2] = "telephone";
    sequenceArray[3] = "isSimple";
    sequenceArray[4] = "hasProtocol";
    sequenceArray[5] = "hasForm";
    sequenceArray[6] = "hasClicence";
    sequenceArray[7] = "isComplete";
    sequenceArray[8] = "remark";

 

    $("#decoration_tableBody tr").not("tr:first").remove(); //清空除第一行之外的表内容

    addTableBody("decoration_table", dataArray, sequenceArray); //调用公用添加table方法

	$("#decoration_table").dataTable({
				"aLengthMenu": [[8, 16, 32, 64], [8,16, 32, 64]],   
				"bFilter":true,   
				"bSort":true,  
				"bJQueryUI": false, 
				"iDisplayLength":8,   
				"bPaginate" : true,          //翻页功能
				"bLengthChange" :true,       //改变每页显示数据数量
				"aaSorting": [[ 1, "desc" ]],//设置第2个元素为默认排序     
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


