		var onePageSize=10;   //请求服务器每页数据的大小
		var currPage=1;       //当前第几页
		var totalPage;     //总页数
		var finishedTotalPage;     //总页数
		var totalcount;   //总条数
		var visiblepage=5;   //显示的页面按钮数
		
		var isinit=0;  //判断是否是初始化
		var isinitFinished=0;
		var tableID1="#UnFinishedRepair_table";
		var tableIDA="UnFinishedRepair_table";
		var tableBodyID1="#UnFinishrepair_tableBody";
		var pageid1="#pagination1";
		var currentSpanId1="#sp1";

		var tableID2="#finishedRepair_table";
		var tableBodyID2="#finisheReapir_tableBody";
		var pageid2="#pagination2";
		var currentSpanId2="#sp2";

		//查看报修信息
	var requestUndoRepairMsg={
		url:URL_REPAIR_VIEW,
		callbackMethod:"", 
		data:""
	}

	//查看已处理的报修信息
	var requestFinishedRepairMsg={
		url:URL_REPAIR_VIEW,
		callbackMethod:"", 
		data:""
	}

	var requestChangeRepairStateMsg={
		url:URL_REPAIR_STATE,
		callbackMethod:ChangeRepairState, 
		data:""
	}



		//为处理报修对象
		var repairUndoMsg={
			roomnumber:"",
			username:"",
			phone:"",
			devicename:"",
			date:"",
			description:"",
			repairState:"",
			checkDetail:"",
			signHandled:"",
			id:""
		}

		
		var repairFinishedMsg={
			roomnumber:"",
			username:"",
			phone:"",
			devicename:"",
			date:"",
			description:"",
			processedtime:""

		}

	

	$(document).ready(function(){ 

		getUndoRepairData(); //获取未完成报修信息
		$('#UnFinishedRepair_table tr').find('td:last').hide();//隐藏第一列

		getFinishedRepairData();  //获取已完成报修信息

		$(document).on("click",".reviewImage",function(){

				var UnFinishedRepairId = $(this).parents("tr").find('td:last').text();
			///获得点击的行号
			var clickRow=$(this).parents("tr")[0].rowIndex-1;
			
			///加载图片
			loadPicture(UnFinishedRepairId); 

			$('#sign_up').lightbox_me({
					centered: true,
					closeClick:false,
					onLoad: function() { 
						$('.zoom').zoom();
						///图片滚动显示slideBox
						$('#demo2').slideBox({});
						
							//$('.zoom').zoom({ on:'click' });
					}
				});	
			});

	}); 

	//标记处理方法
		function signHandledClick(id)
		{
			
               // alert("要处理的报修ID："+id);
           	

			var postData={id:id,processedstate:1};
			
			requestChangeRepairStateMsg.data=postData;
			
			getData(requestChangeRepairStateMsg);
			


		}

		function ChangeRepairState()
		{
			getUndoRepairData(); //获取未完成报修信息
		
			getFinishedRepairData();  //获取已完成报修信息
		}


	var picUrl=new Array();	///全局图片路径

	function loadPicture(UnFinishedRepairId)
	{
		$(ulID).empty();

		//alert(UnFinishedRepairId);
		//alert("显示图片路径"+picUrl[UnFinishedRepairId]);

		
		
		
		//alert("图片URL个数："+picUrl[UnFinishedRepairId].length);
		for (var i = 0; i < picUrl[UnFinishedRepairId].length; i++) 
		{
			var url=picUrl[UnFinishedRepairId][i];

			$(ulID).append("<li><a  title=''><span class='zoom' ><img class='zoomBig' width='555' height='320' src='"+url+"'/></span></a></li>");

			///旧方法
		//document.getElementById("ulID").innerHTML="<li><a href='' title=''><img src='"+picUrl[clickRow]+"'/></a></li>"+"<li><a href='' title=''><img src='"+picUrl[clickRow+1]+"'/></a></li>";
		}

		if(picUrl[UnFinishedRepairId].length==0)
		{
			$(ulID).append("<label>图片不存在</label>");
		}
		

	}

		//获取未完成的报修信息
		function getUndoRepairData()
		{
			var postData={pageSize:onePageSize,pageNo:currPage,finished:false};
			requestUndoRepairMsg.callbackMethod=setUndoRepairData;
			requestUndoRepairMsg.data=postData;
			
			getData(requestUndoRepairMsg);
		}
		
		//显示未完成的报修信息
		function setUndoRepairData(data)
		{
	
			
			
			var dataArray=new Array(); //数据对象数组
			for(var i=0;i<data.result.length;i++)
			{
				repairUndoMsg=new Object();
				repairUndoMsg.roomnumber=data.result[i].roomnumber;
				repairUndoMsg.username=data.result[i].username;
				repairUndoMsg.phone=data.result[i].phone;
				repairUndoMsg.devicename=data.result[i].devicename;
				repairUndoMsg.date=data.result[i].date;
				repairUndoMsg.description=data.result[i].description;
				
				
				///获取图片的url

				//picUrl[i]="../../"+data.result[i].repairimages[0].url;

				repairUndoMsg.id=data.result[i].id;
				
				///获取图片的url
				picUrl[repairUndoMsg.id]=new Array();    //声明二维，每一个一维数组里面的一个元素都是一个数组；

				for(var j=0;j<data.result[i].repairimages.length;j++)
				{
				//	picUrl[id][j]="../"+data.result[i].repairimages[j].url;
					picUrl[repairUndoMsg.id][j]="../../"+data.result[i].repairimages[j].url;
				}

					
					
				var repairstate;
		 		if(data.result[i].processedstate==-1)
		 		{
		 			repairstate="<span style='color: #FF7744'>未处理</span>";
		 		}

		 		if(data.result[i].processedstate==0)
		 		{
		 			repairstate="<span style='color: #E38EFF'>处理中</span>";
		 		}

				repairUndoMsg.repairState=repairstate;
 
				repairUndoMsg.checkDetail="<input type='button' class='reviewImage'  style='color:red;' value='查看图片' />"
		 		repairUndoMsg.signHandled="<a href='#' onclick='signHandledClick("+data.result[i].id+")'>" + "标记已处理" + "</a>";

				dataArray.push(repairUndoMsg);
			}
		
			
			//var p=pic[0].url
			//alert(dataArray[0].repairState);

			var sequenceArray=new Array();//序列数组
			sequenceArray[0]="roomnumber";
			sequenceArray[1]="username";
			sequenceArray[2]="phone";
			sequenceArray[3]="devicename";
			sequenceArray[4]="date";
			sequenceArray[5]="description";
			sequenceArray[6]="repairState";
			sequenceArray[7]="checkDetail";
			sequenceArray[8]="signHandled";
			sequenceArray[9]="id";

			totalPage=data.totalPage;	
			$("#UnFinishrepair_tableBody tr").not("tr:first").remove();

			addTableBody("UnFinishedRepair_table",dataArray,sequenceArray); //调用公用添加table方法
			
			if (!isinit) {
				pageChange(requestUndoRepairMsg,pageid1,currentSpanId1,totalPage,totalcount,visiblepage,onePageSize,currPage);
				isinit=1;
			}

		}

		//获取已经完成的报修信息
		function getFinishedRepairData()
		{
			var postData={pageSize:onePageSize,pageNo:currPage,finished:true};
			requestFinishedRepairMsg.callbackMethod=setFinishedRepairData;
			requestFinishedRepairMsg.data=postData;
			
			getData(requestFinishedRepairMsg);
		}

		//显示已经完成的报修信息
		function setFinishedRepairData(data)
		{
			var dataArray = new Array(); 
			for(var i=0;i<data.result.length;i++)
			{ 
		 		dataArray[i]=new Array(); 

		 		dataArray[i][0]=data.result[i].roomnumber;
		 		dataArray[i][1]=data.result[i].username;
		 		dataArray[i][2]=data.result[i].phone;
		 		dataArray[i][3]=data.result[i].devicename;
		 		dataArray[i][4]=data.result[i].date;
		 		dataArray[i][5]=data.result[i].description;
		 		dataArray[i][6]=data.result[i].processedtime;
			}
			finishedTotalPage=data.totalPage;

			$(tableBodyID2).text(""); 
			tableBody(tableID2,dataArray);

			if (!isinitFinished) {

				pageChange(requestFinishedRepairMsg,pageid2,currentSpanId2,finishedTotalPage,totalcount,visiblepage,onePageSize,currPage);
				isinitFinished=1;
			}
		}
		

		function updateRequestObj(num)
		{
			var postDataString={pageSize:onePageSize,pageNo:num,finished:false};
			requestUndoRepairMsg.data=postDataString;
			return requestUndoRepairMsg;
		}

		//查看详细方法
		function checkClick(id)
		{
			
             alert(id);   
		}

		

