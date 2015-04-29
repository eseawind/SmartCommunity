//请求房屋对象

var requestRoomListMsg = {
    url: URL_QUERY_ROOMLIST,
    callbackMethod: setRoomListData,
    data: ""
};
  
//按关键字请求楼宇对象
/*
  var requestRoomListByKeywords ={
    url: URL_QUERY_NOTICE_KEYWORDS,
    callbackMethod : setNoticeData,
    data:""
 };
*/
  


   var onePageSize=20;   //请求服务器每页数据的大小
  var currPage=1;       //当前第几页
  var totalPage;     //总页数
  var finishedTotalPage;     //总页数
  var totalcount;   //总条数
  var visiblepage=10;   //显示的页面按钮数
    
  var isinit=0;  //判断是否是初始化
 


    var RoomListMsg={
		
      buildingnumber:"" ,
      number:"" ,
      user:"" ,
      buildingarea:"",
		innerarea:"" ,
      publicarea:"" ,
      state:"" ,
      type:"",
	  // floor:"",
		  targetgroup:"",
	  startcharge:""
	
    }



$(document).ready(function ()
  {

    
    getRoomListData();

	$("#roomList_search").click(function(){ 
          
            if($("#roomList_keywords").val()=='')
            {
                alert("关键字不能为空！");
            }
            else
            {
              getDataByKeyword();
             
            }  
        }); 
   
  });



    function getRoomListData()
    {
		$("#roomList_table").dataTable().fnDestroy();//防止重复初始化表格
			//var notice_keywords = $("#notice_keywords").val();

			var postData={pageSize:onePageSize,pageNo:currPage};

			 requestRoomListMsg.data=postData;

			 requestRoomListMsg.callbackMethod=setRoomListData;
			
			//getDataWithFileUpload(requestNoticeMsg);
			getData(requestRoomListMsg);
    }

function getDataByKeyword()
    {
		/*
		$("#notice_table").dataTable().fnDestroy();//防止重复初始化表格


			var notice_keywords = $("#notice_keywords").val();

			var postData={keywords: notice_keywords};

			requestNoticeByKeywords.callbackMethod=setNoticeData;

			requestNoticeByKeywords.data=postData;
			
			//getDataWithFileUpload(requestNoticeMsg);
			getData(requestNoticeByKeywords);
			*/
    }

  
    function setRoomListData(data)
    {
		

      var dataArray=new Array(); //数据对象数组
		
		//alert(data.result.length);
      for(var i=0;i<data.result.length;i++)
      {
        RoomListMsg=new Object();

        RoomListMsg.buildingnumber =data.result[i].buildingnumber;
        RoomListMsg.number=data.result[i].number;

        RoomListMsg.user=data.result[i].user;

        RoomListMsg.buildingarea=data.result.buildingarea;
		RoomListMsg.innerarea =data.result[i].innerarea;
        RoomListMsg.publicarea=data.result[i].publicarea;
        RoomListMsg.state=data.result[i].state;
		RoomListMsg.type=data.result[i].type;

        //RoomListMsg.floor=data.result.floor;
		RoomListMsg.targetgroup=data.result[i].targetgroup;
		RoomListMsg.startcharge=data.result[i].startcharge;

		//RoomingMsg.edit="<a href='#' onclick='editClick("+data.result[i].id+")'>" + "修改" + "</a>";
       // RoomListMsg.dele="<a href='#' onclick='deleClick("+data.result[i].id+")'>" + "删除" + "</a>";

        dataArray.push( RoomListMsg);
      }
   
      var sequenceArray=new Array();//序列数组
      

	  sequenceArray[0]="buildingnumber";
      sequenceArray[1]="number";
      sequenceArray[2]="user";
      sequenceArray[3]="buildingarea";
	  
	  sequenceArray[4]="innerarea";
	  sequenceArray[5]="publicarea";
      sequenceArray[6]="state";
      sequenceArray[7]="type";

      //sequenceArray[8]="floor";
	  sequenceArray[8]="targetgroup";
	  sequenceArray[9]="startcharge";
	 

	  
      
     

      totalPage=data.totalpage; 

		

      $("#roomList_tableBody tr").not("tr:first").remove(); //清空除第一行之外的表内容
	
      addTableBody("roomList_table",dataArray,sequenceArray); //调用公用添加table方法
      
	  
    //  if (!isinit) {
       // pageChange(requestNoticeMsg,pageid,currentSpanId,totalPage,totalcount,visiblepage,onePageSize,currPage);
       // isinit=1;
     //}


$("#roomList_table").dataTable({
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

	

    //删除
    function deleClick(id)
    {
      if(confirm("是否确认删除？"))
        {
          alert("功能还未实现");
        } 
    }

    
