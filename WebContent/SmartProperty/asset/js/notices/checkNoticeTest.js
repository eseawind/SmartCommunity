		var onePageSize=10;
		var pageNumber=1;
		var totalPage;
		var isinit=0;

		$(document).ready(function(){ 

			getData(onePageSize,pageNumber);
			
		}); 

		function initData(data)
		{
			var html="";
	
			totalPage=data.totalpage;
			for(var i=0;i<data.result.length;i++){
				html+="<tr>";   
				html+="<td>"+data.result[i].title+"</td>";   
				html+="<td>"+data.result[i].content+"</td>";   
				html+="<td>"+data.result[i].date+"</td>";   
				html+="<td>"+data.result[i].author+"</td>";   
				html+="</tr>"; 
			}		
					
			$("#data_body").text(""); 
			$("#notice_table").append(html); 
			if (!isinit) {

				pageChange();
				isinit=1;
			}
/*
			$("#notice_table").dataTable({
				"aLengthMenu": [[8, 16, 32, 64], [8,16, 32, 64]],   
				"bFilter":true,   
				"bSort":true,  
				"bJQueryUI": false, 
				"iDisplayLength":8,   
				"bPaginate" : true,   
				"bLengthChange" :true,
				"aaSorting": [[ 2, "desc" ]],//设置第2个元素为默认排序     

				"aoColumnDefs" : [{  
			        "bSearchable" : false,   
			           "aTargets" : [1]  
			    },{  
			        "bSortable" : false, //指定不支持排序的列  
			           "aTargets" : [1] 
			    }], 
				"oLanguage":{    
					"sZeroRecords": "对不起，查询不到任何相关数据", 
   					"sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条公告",    
					"sInfoEmpty" : "记录数为0",
					"sSearch": "搜索:", 
					"sInfoFiltered": "  ，共搜索 _MAX_ 条公告",
					"sLengthMenu": "每页显示 _MENU_ 条公告",   
					"oPaginate":{      
						"sNext":"下一页",     
						"sPrevious":"上一页"
					} 
  
				} 
			});
		*/
		}


		function pageChange()
		{
		    $.jqPaginator('#pagination2', {
		        totalPages: totalPage,
		       // totalCounts: 100,
		        visiblePages: 10,
		        pageSize: onePageSize,
		        currentPage: 1,
		        prev: '<li class="prev"><a href="javascript:;">上一页</a></li>',
		        next: '<li class="next"><a href="javascript:;">下一页</a></li>',
		        first: '<li class="first"><a href="javascript:;">第一页</a></li>',
		        last: '<li class="last"><a href="javascript:;">最后一页</a></li>',
		        page: '<li class="page"><a href="javascript:;">{{page}}</a></li>',
		        onPageChange: function (num, type) {
		            $('#sp').text('当前第' + '：' + num+' 页，共'+totalPage+'页');

		            getData(onePageSize,num);

		        }
		     });
		}

		function getData(onePageSize,pageNumber)
		{
			$.ajax({
	             type: "post",
	             url: "../../notice/listNoticeByPage.action",
	             dataType: "json",
	             data: {pageSize:onePageSize,pageNo:pageNumber},
	             success: function(data){    
	                    if(data.success == true){ //根据返回值中的success的值来判断是否登录成功
	                      //alert("提交报修信息成功！"); 
	                      	initData(data);

	                    }else{          //验证失败
	                      //alert("提交报修信息失败！"); 
	                    }            
	              },
	              error: function (XmlHttpRequest, textStatus, errorThrown) {  
	                  alert(textStatus);  
	              } 
         	});
		}
