$(document).ready(function(){ 

			initData();
			
		}); 

		//获取信息
		function initData()
		{
			var html="";

			html += "<tbody>"; 
			for(var i=0;i<50;i++){
				html+="<tr>";   
					html+="<td>"+"Wu"+i+"</td>";
					html+="<td>"+"20"+i+"</td>";   
					html+="<td>"+"50.3"+"</td>";   
					html+="<td>"+"已缴"+"</td>";    
				html+="</tr>"; 
			}		
			html += "</tbody>"; 
			$("#waterinfo_table").append(html); 

			$("#waterinfo_table").dataTable({
				"aLengthMenu": [[8, 16, 32, 64], [8,16, 32, 64]],   
				"bFilter":true,   
				"bSort":true,  
				"bJQueryUI": false, 
				"iDisplayLength":8,   
				"bPaginate" : true,   
				"bLengthChange" :true,
				"aaSorting": [[ 1, "desc" ]],//设置第2个元素为默认排序     
				"aoColumnDefs" : [{  
			        "bSearchable" : false,   
			           "aTargets" : [2]  
			    },{  
			        "bSortable" : false, //指定不支持排序的列  
			           "aTargets" : [3] 
			    }], 
				"oLanguage":{    
					"sZeroRecords": "对不起，查询不到任何相关数据", 
   					"sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",    
					"sInfoEmpty" : "记录数为0",
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