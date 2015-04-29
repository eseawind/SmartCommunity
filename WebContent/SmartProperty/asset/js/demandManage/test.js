		$(document).ready(function(){ 
			initFinishedDemandsData();
			//initUnFinishedDemandsData();

			initSelect();

		}); 

		//初始化已经完成的诉求信息
		function initFinishedDemandsData()
		{
			var html="";

			html += "<tbody>"; 
			for(var i=0;i<10;i++){
				html+="<tr>";   
					html+="<td>"+"2014-4-12"+"</td>";
					html+="<td>"+"1-1-401"+"</td>";   
					html+="<td>"+"张三"+"</td>";   
					html+="<td>"+"18290899090"+"</td>"; 
					html+="<td>"+"业主反映入户门开启不完整，入户左侧开关面板松动。"+"</td>";
					html+="<td>"+"联系厂家没有技术人员到场维修，需要等到2#楼安装入户门时（预计6月份）"+"</td>";   
					html+="<td>"+"李雪"+"</td>";   
					html+="<td>"+"5月14日已更换解决"+"</td>"; 
					html+="<td>"+"<input id='remember_pass' type='checkbox'>"+"</td>"; 
					//html+="<td>"+"<a href='#'>删除</a>"+"</td>";    
				html+="</tr>"; 
			}		
			html += "</tbody>"; 
			$("#demandsFinished_table").append(html);

	/*		$("#demandsFinished_table").dataTable({
				"aLengthMenu": [[8, 16, 32, 64], [8,16, 32, 64]],   
				"bFilter":true,   
				"bSort":true,  
				"bJQueryUI": false, 
				"iDisplayLength":8,   
				"bPaginate" : true,   
				"bLengthChange" :true,
				//"sScrollX": "300%",  
				"sScrollXInner": "300%", 
				"aaSorting": [[ 0, "desc" ]],//设置第1个元素为默认排序     
				"aoColumnDefs" : [{  
			        "bSearchable" : false,   
			           "aTargets" : [8]  
			    },{  
			        "bSortable" : false, //指定不支持排序的列  
			           "aTargets" : [3,4,5,6,7,8] 
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
*/
		}


		function initSelect()
		{
			//房号select数据
    		var roomid=new Array("1-1-301","1-1-302","1-1-303","1-1-304","1-1-305","1-1-401","1-1-402","1-1-403","1-1-404","1-1-405","1-1-501","1-1-502","1-1-503","1-1-504","1-1-505","1-1-601","1-1-602","1-1-603","1-1-604","1-1-605","1-1-701","1-1-702","1-1-703","1-1-704","1-1-705","1-1-801","1-1-802","1-1-803","1-1-804","1-1-805","1-1-901","1-1-902","1-1-903","1-1-904","1-1-905","1-1-1001","1-1-1002","1-1-1003","1-1-1004","1-1-1005","1-1-1101","1-1-1102","1-1-1103","1-1-1104","1-1-1105","1-1-1202","1-1-1203","1-1-1204","1-1-1205","1-1-1301","1-1-1302","1-1-1303","1-1-1304","1-1-1305","1-1-1401","1-1-1402","1-1-1403","1-1-1404","1-1-1405","1-1-1501","1-1-1502","1-1-1503","1-1-1504","1-1-1505","1-1-1601","1-1-1602","1-1-1603","1-1-1604","1-1-1605","1-1-1701","1-1-1702","1-1-1703","1-1-1704","1-1-1705");

    		for(var i=0;i<roomid.length;i++)
    		{
    			$("#roomId").append("<option value='" + roomid[i] + "'>" + roomid[i] + "</option>");
    			//$("#unfinished_roomId").append("<option value='" + roomid[i] + "'>" + roomid[i] + "</option>");
    		}


    		var years=new Array("2014","2013","2012","2011","2010","2009","2008","2007","2006","2005","2004","2003","2002","2001","2000","1999","1998","1997","1996","1995","1994","1993","1992","1991");
    		for(var i=0;i<years.length;i++)
    		{
    			$("#years").append("<option value='" + years[i] + "'>" + years[i] + "</option>");
    			//$("#unfinished_years").append("<option value='" + years[i] + "'>" + years[i] + "</option>");
    		}

		}
