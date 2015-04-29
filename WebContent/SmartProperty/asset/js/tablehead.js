function tableHead(tableId,tableHeadData)
{
	var length=tableHeadData.length;
    //alert(tableHeadData);
	var html = "";
    html += "<thead>";
    html += "<tr class='active'>";
    for (var i = 0; i <length; i++) {       
	    html += "<th>" + tableHeadData[i] + "</th>"; 

    }
    html += "</tr>";
    html += "</thead>";
    // alert(html)
    $(tableId).append(html);

}

function tableBody(tableId,tableBodyData)
{
	var trLength=tableBodyData.length;
	
	var html = "";
	html += "<tbody>"; 
	for(var i=0;i<trLength;i++){
		var tdLength=tableBodyData[i].length;

		html+="<tr>";
		for(var j=0;j<tdLength;j++)
		{
			html+="<td>"+tableBodyData[i][j]+"</td>";   
		}
		html+="</tr>"; 
	}		
	html += "</tbody>"; 
	$(tableId).append(html); 

}

function tableHandle(tableId)
{
	$(tableId).dataTable({   
			"bFilter":true, 
			"aLengthMenu": [[8, 16, 32, 64], [8,16, 32, 64]],  
			"bSort":true,  
			"bJQueryUI": false, 
			"iDisplayLength":8,   
			"bPaginate" : true,   
			"bLengthChange" :true,
			"sScrollX": "",
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