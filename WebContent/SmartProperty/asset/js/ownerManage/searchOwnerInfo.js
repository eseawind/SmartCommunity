var requestOwnerMsg = {
	url:URL_QUERY_ROOMOWNER,
    callbackMethod : setOwnerData,
    data:""
};

var requestQueryMsg = {
    url: URL_QUERY_ROOMOWNER,
    callbackMethod: setQueryData,
    data: ""
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

  var onePageSize=10;   //请求服务器每页数据的大小
  var currPage=1;       //当前第几页
  var totalPage;     //总页数
  var finishedTotalPage;     //总页数
  var totalcount;   //总条数
  var visiblepage=10;   //显示的页面按钮数
    
  var isinit=0;  //判断是否是初始化
 
  var tableID="#owner_table";
  var tableBodyID="#owner_tableBody";
  var pageid="#pagination";
  var currentSpanId = "#sp";

  
  //已录入楼宇对象
  var ownerMsg = {
      name: "",
      sex: "",
      identity: "",
      roomnumber: "",
      telephone: "",
      mail: "",
      immigratedate: "",
      politicalaffiliation: "",
      propertyowner: "",
      propertyowneridentity: ""

  };
    var Msg = {
        name: "",
        number: ""
    };

//楼栋号 单元号 房间号
    var type = 1;
    var buildNo = 0;
    var uintNo = 0;
    var roomNo = 0;

    $(document).ready(function () {
            getOwnerData();//初始化表格数据
            getBuildData();//初始化楼栋号
			
			$("#owner_table tbody").on("dblclick", "tr", function (event) {
			    var content = $(this).children("td:eq(3)").text(); 
			    //window.open('detailPeopleInfo.html?roomnumber='+content);
			    window.location.href = 'detailPeopleInfo.html?roomnumber=' + content;
			});
			$('#search_client').change(function () {
			    type = $(this).children('option:selected').val();
			});
			$('#search_build').change(function () {
			    buildNo = $(this).children('option:selected').val();
			    getUnitData();
			   
			});
			$('#search_unit').change(function () {
			    buildNo = $('#search_build').children('option:selected').val();
			    unitNo = $(this).children('option:selected').val();
			    getRoomData();
			    
				   
			});
			$('#search_room').change(function () {		   
			    roomNo = $(this).children('option:selected').val();			
			});

			$("#query_button").click(function () {
			    type = $('#search_client').children('option:selected').val();
			    buildNo = $('#search_build').children('option:selected').val();
			    unitNo = $('#search_unit').children('option:selected').val();
			    roomNo = $('#search_room').children('option:selected').val();
			    getQueryData();
			});
            
        }); 
		function getOwnerData()
        {
		    var postData={};
			requestOwnerMsg.callbackMethod=setOwnerData;
			requestOwnerMsg.data=postData;
			getData(requestOwnerMsg);
		}
		function getQueryData() {
		    var postData = {
		        "buildNo":buildNo,
		        "unitNo":unitNo,
		        "roomNo": roomNo,
                "type": type
		    };
		    requestQueryMsg.data = postData;
		    getData(requestQueryMsg);
		}

		function getBuildData() {
		    var postData = { };
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

		function setBuildSelect(data) {
		   fillSelect(data,"#search_build");
		}
	
		function setUnitSelect(data) {
		    $("#search_unit").empty();
		    $("#search_room").empty();
		    //$("#search_room").append("<option value='" + 0 + "'>" + "---请选择---" + "</option>");
		   // $("#search_unit").append("<option value='" + 0 + "'>" + "---请选择---" + "</option>");
		    fillSelect(data, "#search_unit");
            /*
		    var dataArray = new Array(); //数据对象数组
		    for (var i = 0; i < data.result.length; i++) {
		        Msg = new Object();
		        Msg.name = data.result[i].name;
		        Msg.number = data.result[i].number;
		        dataArray.push(Msg);
		    }
		   
		    $("#search_unit").append(
               "<option value='" + 0 + "'>" + "不限" + "</option>");
		    for (var i = 0; i < dataArray.length; i++) {
		        $("#search_unit").append(
                        "<option value='" + "未定义值" + "'>" + "未定义名" + "</option>");
		    }
            */
		}

		function setRoomSelect(data) {
		    $("#search_room").empty();
		   // $("#search_room").append("<option value='" + 0 + "'>" + "---请选择---" + "</option>");
		    fillSelect(data, "#search_room");
            /*
		    var dataArray = new Array(); //数据对象数组
		    for (var i = 0; i < data.result.length; i++) {
		        Msg = new Object();
		        Msg.name = data.result[i].name;
		        Msg.number = data.result[i].number;
		        dataArray.push(Msg);
		    }
		   
		    $("#search_room").append(
               "<option value='" + 0 + "'>" + "不限" + "</option>");
		    for (var i = 0; i < dataArray.length; i++) {
		        $("#search_room").append(
                        "<option value='" + "未定义值" + "'>" + "未定义名" + "</option>");
		    }
            */
		}

	    function setOwnerData(data)
        {
      
          var dataArray=new Array(); //数据对象数组
          for(var i=0;i<data.result.length;i++)
          {
            ownerMsg=new Object();

            ownerMsg.name =data.result[i].name;
            ownerMsg.sex=data.result[i].sex;
            ownerMsg.identity=data.result[i].identity;
            ownerMsg.roomnumber=data.result[i].roomnumber;
            ownerMsg.telephone=data.result[i].telephone;
            ownerMsg.mail=data.result[i].mail;
            ownerMsg.immigratedate=data.result[i].immigratedate;
            ownerMsg.politicalaffiliation = data.result[i].politicalaffiliation;
            ownerMsg.propertyowner = data.result[i].propertyowner;
            ownerMsg.propertyowneridentity = data.result[i].propertyowneridentity;
            dataArray.push(ownerMsg);
          }

          var sequenceArray=new Array();//序列数组
          sequenceArray[0]="name";
          sequenceArray[1]="sex";
          sequenceArray[2]="identity";
          sequenceArray[3]="roomnumber";
          sequenceArray[4]="telephone";
          sequenceArray[5]="mail";
          sequenceArray[6]="immigratedate";
          sequenceArray[7] = "politicalaffiliation";
          sequenceArray[8] = "propertyowner";
          sequenceArray[9] = "propertyowneridentity";

          totalPage=data.totalPage; 
          $("#owner_tableBody tr").not("tr:first").remove(); //清空除第一行之外的表内容
          addTableBody("owner_table",dataArray,sequenceArray); //调用公用添加table方法
      
          if (!isinit) {
           // pageChange(requestOwnerMsg,pageid,currentSpanId,totalPage,totalcount,visiblepage,onePageSize,currPage);
           // isinit=1;
          }
	       $("#owner_table").dataTable({
				    "aLengthMenu": [[8, 16, 32, 64], [8,16, 32, 64]],   
				    "bFilter": true,
               "bInfo": false,
				    "bRetrieve": true,
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

	    function setQueryData(data) {
	        var dataArray = new Array(); //数据对象数组
	        for (var i = 0; i < data.result.length; i++) {
	            ownerMsg = new Object();

	            ownerMsg.name = data.result[i].name;
	            ownerMsg.sex = data.result[i].sex;
	            ownerMsg.identity = data.result[i].identity;
	            ownerMsg.roomnumber = data.result[i].roomnumber;
	            ownerMsg.telephone = data.result[i].telephone;
	            ownerMsg.mail = data.result[i].mail;
	            ownerMsg.immigratedate = data.result[i].immigratedate;
	            ownerMsg.politicalaffiliation = data.result[i].politicalaffiliation;
	            ownerMsg.propertyowner = data.result[i].propertyowner;
	            ownerMsg.propertyowneridentity = data.result[i].propertyowneridentity;
	            dataArray.push(ownerMsg);
	        }
	        var sequenceArray = new Array();//序列数组
	        sequenceArray[0] = "name";
	        sequenceArray[1] = "sex";
	        sequenceArray[2] = "identity";
	        sequenceArray[3] = "roomnumber";
	        sequenceArray[4] = "telephone";
	        sequenceArray[5] = "mail";
	        sequenceArray[6] = "immigratedate";
	        sequenceArray[7] = "politicalaffiliation";
	        sequenceArray[8] = "propertyowner";
	        sequenceArray[9] = "propertyowneridentity";

	        totalPage = data.totalPage;
	        $("#owner_tableBody tr").not("tr:first").remove();//清空除第一行之外的表内容
	        addTableBody("owner_table", dataArray, sequenceArray); //调用公用添加table方法

	    }

    function updateRequestObj(num)
    {
      var postDataString={pageSize:onePageSize,pageNo:num};
      requestOwnerMsg.data=postDataString;
      return requestOwnerMsg;
    }


     
    function updatePostString(num)
    {
      var postDataString="{pageSize:"+onePageSize+",pageNo:"+num+"}";

      return postDataString;
    }