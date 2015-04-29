//请求楼宇对象
  var requestBuildList ={
    url: URL_QUERY_BUILDING,
    callbackMethod : setBuildListData,
    data:""
 };
  
//按关键字请求楼宇对象
/*
  var requestBuildListByKeywords ={
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
 


    var BuildListMsg={
		
      number:"" ,
      manager:"" ,
      name:"" ,
      address:"",
		type:"" ,
      struct:"" ,
      orientation:"" ,
      completiondate:"",
	   remarks:"",
		  dele:""
	
    }



$(document).ready(function ()
  {

    
    getBuildListData();

	$("#builList_search").click(function(){ 
          
            if($("#builList_keywords").val()=='')
            {
                alert("关键字不能为空！");
            }
            else
            {
              getDataByKeyword();
             
            }  
        }); 
   
  });



    function getBuildListData()
    {
		$("#builList_table").dataTable().fnDestroy();//防止重复初始化表格
			//var notice_keywords = $("#notice_keywords").val();

			var postData={pageSize:onePageSize,pageNo:currPage};

			 requestBuildList.callbackMethod= setBuildListData;

			 requestBuildList.data=postData;
			
			//getDataWithFileUpload(requestNoticeMsg);
			getData(requestBuildList);
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

  
    function setBuildListData(data)
    {
		

      var dataArray=new Array(); //数据对象数组
		
		
      for(var i=0;i<data.result.length;i++)
      {
        BuildListMsg=new Object();

        BuildListMsg.number =data.result[i].number;
        BuildListMsg.manager=data.result[i].manager;
        BuildListMsg.name=data.result[i].name;
        BuildListMsg.address=data.result.address;

		BuildListMsg.type =data.result[i].type;
        BuildListMsg.struct=data.result[i].struct;
        BuildListMsg.orientation=data.result[i].orientation;

		BuildListMsg.completiondate=data.result[i].completiondate;

		
        BuildListMsg.remarks=data.result[i].remarks;

		//BuildingMsg.edit="<a href='#' onclick='editClick("+data.result[i].id+")'>" + "修改" + "</a>";
        BuildListMsg.dele="<a href='#' onclick='deleClick("+data.result[i].id+")'>" + "删除" + "</a>";

        dataArray.push( BuildListMsg);
      }
   
      var sequenceArray=new Array();//序列数组
      
	
	  sequenceArray[0]="number";
      sequenceArray[1]="manager";
      sequenceArray[2]="name";
      sequenceArray[3]="address";
	  
	  sequenceArray[4]="type";
	  sequenceArray[5]="struct";
      sequenceArray[6]="orientation";
      sequenceArray[7]="completiondate";

      sequenceArray[8]="remarks";
	  sequenceArray[9]="dele";

	  
      
     

      totalPage=data.totalpage; 

		

      $("#builList_tableBody tr").not("tr:first").remove(); //清空除第一行之外的表内容
	
      addTableBody("builList_table",dataArray,sequenceArray); //调用公用添加table方法
      
	  
    //  if (!isinit) {
       // pageChange(requestNoticeMsg,pageid,currentSpanId,totalPage,totalcount,visiblepage,onePageSize,currPage);
       // isinit=1;
     //}


$("#builList_table").dataTable({
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

    
