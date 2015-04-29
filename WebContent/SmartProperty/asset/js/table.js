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
	//html += "<tbody>"; 
	for(var i=0;i<trLength;i++){
		var tdLength=tableBodyData[i].length;

		html+="<tr>";
		for(var j=0;j<tdLength;j++)
		{
			html+="<td>"+tableBodyData[i][j]+"</td>";   
		}
		html+="</tr>"; 
	}		
	//html += "</tbody>"; 
	$(tableId).append(html); 
}

/*
function addTableBody(tableId,tableBodyData)
{
	var trLength=tableBodyData.length;

	var table = document.getElementById(tableId);
	
	for(var i=0;i<trLength;i++){
		var tdLength=tableBodyData[i].length;

		for(var j=0;j<tdLength;j++)
		{
		    table.rows [i+1].cells[j].innerHTML=tableBodyData[i][j];
		}
		if(i<trLength-1)
		{
			var newRow = $("#" + tableId + " tr:last").clone();
        	newRow.appendTo("#" + tableId + "");	
		}
	}		
}
*/

/*
function addTableBody(tableId,tableBodyData,sequenceArray)
{
	
	var trLength=tableBodyData.length;
	var table = document.getElementById(tableId);
	
	for(var i=0;i<trLength;i++){
		var tdLength=sequenceArray.length;
		for(var j=0;j<tdLength;j++)
		{
			var data=tableBodyData[i][sequenceArray[j]];
		    table.rows [i+1].cells[j].innerHTML=data;
		}
		if(i<trLength-1)
		{
			var newRow = $("#" + tableId + " tr:last").clone();
        	newRow.appendTo("#" + tableId + "");	
		}
	}	
		
}
*/
function addTableBody(tableId,tableBodyData,sequenceArray)
{
	
	var trLength=tableBodyData.length;
	var tdLength=sequenceArray.length;
	var table = document.getElementById(tableId);
	for(var j=0;j<tdLength;j++)
		{
		    table.rows [1].cells[j].innerHTML="";
		}
	
	for(var i=0;i<trLength;i++){
	//	var tdLength=sequenceArray.length;
		for(j=0;j<tdLength;j++)
		{
			var data=tableBodyData[i][sequenceArray[j]];
		    table.rows [i+1].cells[j].innerHTML=data;
		}
		if(i<trLength-1)
		{
			var newRow = $("#" + tableId + " tr:last").clone();
        	newRow.appendTo("#" + tableId + "");	
		}
	}	
		
}

function tdclick() {  
// .parent("tr")表示这个td的父节点 
// .prevAll()表示这个tr前面有多少个tr 
var hang = $(this).parent("tr").prevAll().length; 
var lie = $(this).prevAll().length;  
alert("第"+hang+"行"+"第"+lie+"列"); 
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

function pageChange(requsetObject,page_ID,currentSpanId,total_Page,total_count,visible_page,page_size,current_page)
{
	
	$.jqPaginator(page_ID, {
		totalPages: total_Page,
		//totalCounts: total_count,
		visiblePages: visible_page,
		//pageSize: page_size,
		currentPage: current_page,
		prev: '<li class="prev"><a href="javascript:;">上一页</a></li>',
		next: '<li class="next"><a href="javascript:;">下一页</a></li>',
		first: '<li class="first"><a href="javascript:;">第一页</a></li>',
		last: '<li class="last"><a href="javascript:;">最后一页</a></li>',
		page: '<li class="page"><a href="javascript:;">{{page}}</a></li>',
		    onPageChange: function (num, type) {
		        $(currentSpanId).text('当前第' + '：' + num+' 页，共'+totalPage+'页,  每页'+page_size+'条记录');

		       if(type=="change")
		       {
		       		var requsetObject=updateRequestObj(num);
		        	//alert(requsetObject.data);
		       		getData(requsetObject);
		       }
		        

		    }
	});
}
