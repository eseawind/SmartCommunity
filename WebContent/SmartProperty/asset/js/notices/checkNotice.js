//请求楼宇对象
  var requestNoticeMsg ={
    url: URL_QUERY_NOTICE,
    callbackMethod : setNoticeData,
    data:""
 };
  
//请求楼宇对象
  var requestNoticeByKeywords ={
    url: URL_QUERY_NOTICE_KEYWORDS,
    callbackMethod : setNoticeData,
    data:""
 };

  


   var onePageSize=20;   //请求服务器每页数据的大小
  var currPage=1;       //当前第几页
  var totalPage;     //总页数
  var finishedTotalPage;     //总页数
  var totalcount;   //总条数
  var visiblepage=10;   //显示的页面按钮数
    
  var isinit=0;  //判断是否是初始化
 
  var currentSpanId="#sp"; 
 
  var tableID="#notice_table";
  var tableBodyID="#notice_tableBody";
  var pageid="#pagination";

 


    var NoticeMsg={
		
      title:"" ,
      content:"" ,
      time:"" ,
      author:""
	
    }



$(document).ready(function ()
  {
	var date="#notice_date";
	initDatepicker(date);
   
    getNoticeData();

	$("#notice_history_search").click(function(){ 
          
            if($("#notice_keywords").val()=='')
            {
                alert("关键字不能为空！");
            }
            else
            {
              getDataByKeyword();
             
            }  
        }); 
   
  });






    function getNoticeData()
    {
		$("#notice_table").dataTable().fnDestroy();//防止重复初始化表格
			//var notice_keywords = $("#notice_keywords").val();

			var postData={pageSize:onePageSize,pageNo:currPage};

			requestNoticeMsg.callbackMethod=setNoticeData;

			requestNoticeMsg.data=postData;
			
			//getDataWithFileUpload(requestNoticeMsg);
			getData(requestNoticeMsg);
    }

function getDataByKeyword()
    {

		$("#notice_table").dataTable().fnDestroy();//防止重复初始化表格


			var notice_keywords = $("#notice_keywords").val();

			var postData={keywords: notice_keywords};

			requestNoticeByKeywords.callbackMethod=setNoticeData;

			requestNoticeByKeywords.data=postData;
			
			//getDataWithFileUpload(requestNoticeMsg);
			getData(requestNoticeByKeywords);
    }

  
    function setNoticeData(data)
    {
		

      var dataArray=new Array(); //数据对象数组
		
		
      for(var i=0;i<data.result.length;i++)
      {
        NoticeMsg=new Object();

		 //NoticeMsg.id =data.result[i].id;
        NoticeMsg.title =data.result[i].title;
        NoticeMsg.content=data.result[i].content;
        NoticeMsg.date=data.result[i].date;
        NoticeMsg.author=data.result.length;

		//ElectricityMsg.state="<input type='button' class='nopay'  style='color:red;' value='未交' />";

        //NoticeMsg.edit="<a href='#' onclick='editClick("+data.result[i].id+")'>" + "修改" + "</a>";
       // NoticeMsg.dele="<a href='#' onclick='deleClick("+data.result[i].id+")'>" + "删除" + "</a>";
       

        dataArray.push(NoticeMsg);
      }
   
      var sequenceArray=new Array();//序列数组
      
	 //sequenceArray[0]=$("<input type='checkbox' >");
	  sequenceArray[0]="title";
      sequenceArray[1]="content";
      sequenceArray[2]="date";
      sequenceArray[3]="author";
     

      totalPage=data.totalpage; 

		

      $("#notice_tableBody tr").not("tr:first").remove(); //清空除第一行之外的表内容
	
      addTableBody("notice_table",dataArray,sequenceArray); //调用公用添加table方法
      
	  
    //  if (!isinit) {
       // pageChange(requestNoticeMsg,pageid,currentSpanId,totalPage,totalcount,visiblepage,onePageSize,currPage);
       // isinit=1;
     //}


$("#notice_table").dataTable({
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

	function initBuiltSelect() {

	// 房号select数据
	var buildid = new Array("0","1","2","3","4","5");

	for (var i = 0; i < buildid.length; i++) {
		$("#notice_build").append(
				"<option  value='" + buildid[i] + "'>" + buildid[i] + "</option>");
	}

}

    function updateRequestObj(num)
    {
      var postDataString={pageSize:onePageSize,pageNo:num};
      requestNoticeMsg.data=postDataString;
      return requestNoticeMsg;
    }


     
    function updatePostString(num)
    {
      var postDataString="{pageSize:"+onePageSize+",pageNo:"+num+"}";

      return postDataString;
    }


   //修改
    function editClick(id)
    { 
      alert(id); 
    }

    //删除
    function deleClick(id)
    {
      if(confirm("是否确认删除？"))
        {
          alert(id);
        } 
    }
