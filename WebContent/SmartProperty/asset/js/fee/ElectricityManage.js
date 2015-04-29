 var type;//费用类别为：电费
  var requestElectricityMsg ={
    url:URL_QUERY_METER_INFO,
    callbackMethod : setElectricityData,
    data:""
  };

  var requestAccountMsg ={
	 url: URL_QUERY_METER_ACCOUNT,
	 callbackMethod : setAccountData,
     data:""
  };
 
//缴费对象
  var requestPaymentMsg ={
    url: URL_SUBMIT_METER_FEE,
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

 
  var Data;		//对应requestElectricityMsg的data
  var flag;  //check函数检测输入
  var payid; //缴费id
  var paynum;//缴费金额
  var td;//缴费按钮项

//条件查询的楼栋号 单元号 房间号
    var buildNo = "0";
    var unitNo = "0";
    var roomNo = "0";
 

  var onePageSize=20;   //请求服务器每页数据的大小
  var currPage=1;       //当前第几页
  var totalPage;     //总页数
  var finishedTotalPage;     //总页数
  var totalcount;   //总条数
  var visiblepage=10;   //显示的页面按钮数
    
  var isinit=0;  //判断是否是初始化
 
  var tableID="#electricityinfo_table";
  var tableBodyID="#electricityinfo_tableBody";
  var pageid="#pagination";
  var currentSpanId="#sp"; 
   var ElectricityMsg={
	  id:"",
	  Type:"",
      roomnumber:"" ,
      econsume:"" ,
      unitprice:"" ,
      totalprice:"" ,
      readingtime:"" ,
      state:"" ,
    }

var AccountMsg={
      roomnumber:"" ,
      remain:"" ,
    }




$(document).ready(function ()
  {
	$("#search_type").append("<option value='" + 0 + "'>" + "水费" + "</option>");
	$("#search_type").append("<option value='" + 1 + "'>" + "电费" + "</option>");

	var	postData={"pageSize":onePageSize,"pageNo":currPage};
    getElectricityData(postData);
	var	postData1={"pageSize":onePageSize,"pageNo":currPage};
	getAccountData(postData1);
	$('#electricityinfo_table tr').find('td:eq(0)').hide();//隐藏第一列


	getBuildData();//初始化楼栋号

	
	$('#search_build').change(function () {
			/*    buildNo = $("#search_build").children('option:selected').val();
				unitNo = $("#search_unit").children('option:selected').val();
				roomNo = $("#search_room").children('option:selected').val();
			*/
			    getUnitData();
			});

	$('#search_unit').change(function () {
			 /*   buildNo = $("#search_build").children('option:selected').val();
				unitNo = $("#search_unit").children('option:selected').val();
				roomNo = $("#search_room").children('option:selected').val();
				*/
			    getRoomData();
			});
/*	$('#search_room').change(function () {
				buildNo = $("#search_build").children('option:selected').val();
				unitNo = $("#search_unit").children('option:selected').val();
				roomNo = $("#search_room").children('option:selected').val();
			   
			});
			*/

	//使用查询按钮查询信息
	$("#button_query").click(function()
		{
			var sel=document.getElementById('search_type');    
		//	alert(sel.options[sel.selectedIndex].text);
		//	alert(sel.options[sel.selectedIndex].value);
			type=sel.options[sel.selectedIndex].value;
			buildNo = $("#search_build").children('option:selected').val();
			unitNo = $("#search_unit").children('option:selected').val();
			roomNo = $("#search_room").children('option:selected').val();
		//	alert(buildNo+unitNo+roomNo);
			
			var	postData;
			if(type==0 || type==1){
				postData={"buildNo":buildNo,"unitNo":unitNo,"roomNo":roomNo,"metertype":type,"pageSize":onePageSize,"pageNo":currPage};
			
			}
			else
			{
				
				postData={"buildNo":buildNo,"unitNo":unitNo,"roomNo":roomNo,"pageSize":onePageSize,"pageNo":currPage};
			
			}
			getElectricityData(postData);
			
		});	


	$(document).on("click",".nopay",function(){
		td=$(this).parents("tr").find('td:last');
		
		payid = $(this).parents("tr").find('td:first').text();
	//  var payid = $(this).parents("tr").children("td:eq(0)").text();
	//	alert(payid);
		var roomnum = $(this).parents("tr").children("td:eq(1)").text();
		var owenum = $(this).parents("tr").children("td:eq(5)").text();
		$("#roomnum").attr("value",roomnum);//房间号填充内容
		$("#roomnum").attr('disabled','true');//设为不可更改
		$("#shouldpay").attr("value",owenum);//应交金额填充内容
		$("#shouldpay").attr('disabled','true');//设为不可更改
		$("#paynum").val("");//清空内容
		
		
	//弹出浮窗
		$('#sign_up').lightbox_me({
				centered: true,
				closeClick:false,
				onLoad: function() { 
					$('#sign_up').find('input:last').focus();
				}
		});	
	}); 

	
		//提交缴费信息
	$("#button_submmit").click(function(){
			flag = 1;
		    check();
		    if (flag == 0)
		        return;
			paynum = $("#paynum").val();
		//	alert(paynum);
			submitPaymentData(); 
			$('#sign_up').trigger('close');
		//	td.html("");//清空内容
		//	td.text("已交");
		
		
	});

		//取消缴费
	$('#cancel').click(function(){ 		
			$('#sign_up').trigger('close');    
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
		   fillSelect(data,"#search_build");
		}
	
		function setUnitSelect(data) {
		    $("#search_unit").empty();
		    $("#search_room").empty();
			$("#search_room").append("<option value='" + 0 + "'>" + "---请选择---" + "</option>");
		    fillSelect(data,"#search_unit");
		}

		function setRoomSelect(data) {
		    $("#search_room").empty();
		    fillSelect(data,"#search_room");
		}

//提交缴费的回调函数
function submitInfoResult(result)
	{
	
	if(result.success == true)
	{
		alert("缴费成功");

	//局部刷新
		td.html("");//清空内容
		td.text("已交");
	} 
	 else
	{
		alert("缴费失败" + result.type);
	}
  
}

//提交缴费信息
function submitPaymentData(/*payid,paynum*/) {
//  var usernum = $("#userid").val();
//  var paynum = $("#paynum").val();  
//  var formData = new FormData($("#uploadForm")[0]);
//	alert(payid);
  //  alert(paynum);
  var formData = {"id":payid,"amount":paynum};
  requestPaymentMsg.data=formData;
  getData(requestPaymentMsg);
  
}

//检测输入
function check()
{
	if($("#paynum").val() == "")
		{
            alert("缴费金额不能为空！请重新输入！");
		//	$("#paynum").val("");//清空内容
		    $('#paynum').focus();
            flag = 0;
            return;
        }
	if(parseFloat($("#paynum").val()) < parseFloat($("#shouldpay").val()))//将string转换为float再比较大小
		{
            alert("缴费金额不足！请重新输入！");
			$("#paynum").val("");//清空内容
			$('#paynum').focus();
            flag = 0;
            return;
        }
        
}
  






//获取电费信息
    function  getElectricityData(postData)
    {
		$("#electricityinfo_table").dataTable().fnDestroy();//防止重复初始化表格
//      var postData={"metertype":type,"pageSize":onePageSize,"pageNo":currPage};
//	  var postData={"pageSize":onePageSize,"pageNo":currPage};	
//    requestElectricityMsg.callbackMethod=setElectricityData;
      requestElectricityMsg.data=postData;
    
      
      getData(requestElectricityMsg);
    }


  //获取电费信息的回调函数
    function setElectricityData(data)
    {
      
      var dataArray=new Array(); //数据对象数组
      for(var i=0;i<data.result.length;i++)
      {
        ElectricityMsg=new Object();
		ElectricityMsg.id =data.result[i].id;
        ElectricityMsg.roomnumber =data.result[i].roomnumber;
        ElectricityMsg.econsume=data.result[i].consumedata;
        ElectricityMsg.unitprice=data.result[i].uniteprice;
        ElectricityMsg.totalprice=data.result[i].totalprice;
        ElectricityMsg.readingtime=data.result[i].readingtime;
		if(data.result[i].metertype==0)
		{
			ElectricityMsg.Type="水费";
		}
		else
			ElectricityMsg.Type="电费";

	    if(data.result[i].state==true)
		{
			ElectricityMsg.state="已交";
		}
		else
			ElectricityMsg.state="<input type='button' class='nopay'  style='color:red;' value='未交' />";
	
        dataArray.push(ElectricityMsg);
      }

      var sequenceArray=new Array();//序列数组
	  sequenceArray[0]="id";
	  sequenceArray[1]="roomnumber";
      sequenceArray[2]="Type";
      sequenceArray[3]="econsume";
      sequenceArray[4]="unitprice";
      sequenceArray[5]="totalprice";
      sequenceArray[6]="readingtime";
      sequenceArray[7]="state";

 //     totalPage=data.totalPage; 
      $("#electricityinfo_tableBody tr").not("tr:first").remove(); //清空除第一行之外的表内容
	//	$("#electricityinfo_table tr").not("tr:first").remove(); //清空除第一行之外的表内容
	
      addTableBody("electricityinfo_table",dataArray,sequenceArray); //调用公用添加table方法
      /*
      if (!isinit) {
		 pageChange(requestElectricityMsg,pageid,currentSpanId,totalPage,totalcount,visiblepage,onePageSize,currPage);
         isinit=1;
	  
      }
	  */
	//初始化表格  
	  $("#electricityinfo_table").dataTable({
                "bRetrieve": true,
                "bDestroy":true,
				"aLengthMenu": [[8, 16, 32, 64], [8,16, 32, 64]],  
			//	"bStateSave": true, //返回时，保留在同一页上
				"bFilter":true,   
				"bSort":true,  
				"bJQueryUI": false, 
				"iDisplayLength":8,   
				"bPaginate" : true,          //翻页功能
				"bLengthChange" :true,       //改变每页显示数据数量
				"aaSorting": [[ 0, "asc"]],//设置第1个元素为默认排序     
				"aoColumnDefs" : [{  
			        "bSortable" : false, //指定不支持排序的列  
			           "aTargets" : [1]
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

//获取账户余额信息
    function  getAccountData(postData)
    {
		$("#accountinfo_table").dataTable().fnDestroy();//防止重复初始化表格
//      var postData={"metertype":type,"pageSize":onePageSize,"pageNo":currPage};
//	  var postData={"pageSize":onePageSize,"pageNo":currPage};	
//    requestElectricityMsg.callbackMethod=setElectricityData;
      requestAccountMsg.data=postData;
    
      
      getData(requestAccountMsg);
    }
	//获取账户余额信息的回调函数
    function setAccountData(data)
    {
      
      var dataArray=new Array(); //数据对象数组
      for(var i=0;i<data.result.length;i++)
      {
        AccountMsg=new Object();
	//	AccountMsg.id =data.result[i].id;
         AccountMsg.roomnumber =data.result[i].roomnumber;
         AccountMsg.remain=data.result[i].remain;
       
	
        dataArray.push(AccountMsg);
      }

      var sequenceArray=new Array();//序列数组
//	  sequenceArray[0]="id";
	  sequenceArray[0]="roomnumber";
      sequenceArray[1]="remain";
      

 //     totalPage=data.totalPage; 
      $("#accountinfo_tableBody tr").not("tr:first").remove(); //清空除第一行之外的表内容
	
	
      addTableBody("accountinfo_table",dataArray,sequenceArray); //调用公用添加table方法
     
	//初始化表格  
	  $("#accountinfo_table").dataTable({
				"aLengthMenu": [[8, 16, 32, 64], [8,16, 32, 64]],  
			//	"bStateSave": true, //返回时，保留在同一页上
				"bFilter":true,   
				"bSort":true,  
				"bJQueryUI": false, 
				"iDisplayLength":8,   
				"bPaginate" : true,          //翻页功能
				"bLengthChange" :true,       //改变每页显示数据数量
				"aaSorting": [[ 1, "asc"]],//设置第1个元素为默认排序     
			/*	"aoColumnDefs" : [{  
			        "bSortable" : false, //指定不支持排序的列  
			           "aTargets" : [0]
					}], */
				 
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






    function updateRequestObj(num)
    {
      var postDataString={pageSize:onePageSize,pageNo:num};
      requestElectricityMsg.data=postDataString;
      return requestElectricityMsg;
    }


     
    function updatePostString(num)
    {
      var postDataString="{pageSize:"+onePageSize+",pageNo:"+num+"}";

      return postDataString;
    }


/*
	function addToSelect(selectId,optData)
	{
		var sel = $("#"+selectId);
		sel.empty();
		for(var i=0;i<optData.length;i++)
		{
			var option = $("<option>").text(optData[i].name).val(optData[i].number);
			sel.append(option);
		}
	}
*/

