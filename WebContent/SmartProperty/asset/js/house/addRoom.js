var	requestRoomMsg ={
	url: URL_ADD_ROOM,
	callbackMethod : submitInfoResult,
	data:""
};

  var requestBuildMsg = {
    url: URL_QUERY_BUILD,
    callbackMethod: setBuildSelect,
    data: ""
};


  var onePageSize=10;   //请求服务器每页数据的大小
  var currPage=1;       //当前第几页
  var totalPage;     //总页数
  var finishedTotalPage;     //总页数
  var totalcount;   //总条数
  var visiblepage=10;   //显示的页面按钮数
    
  var isinit=0;  //判断是否是初始化
 
  
  //已录入楼宇对象
    var RoomMsg={
      buildnumber:"" ,
      floor:"" ,
      number:"" ,
      buildingarea:"" ,
      innerarea:"" ,
      publicarea:"" ,
      type:"" ,
      targetgroup:"",
      startcharge:"",
      state:""

    }




$(document).ready(function() {

	getBuildData();//初始化楼栋号
	setDatePicker(); //初始化datepicker
    initstateSelect();
   
	
	$("#button_submmit").click(function() {
		
		if($("#build").val()=='' ||$("#unit").val()==''||$("#room").val()=='')
		{
			alert("房屋信息不能为空");
		}
		else
		{
			if(confirm("是否确认提交信息？"))
			{
                  submitRoomData();
			}
		}  

	});	

  });

function getBuildData() 
{
		   
	var postData = { pageSize: onePageSize, pageNo: currPage };
	requestBuildMsg.data = postData;
	getData(requestBuildMsg);

}

function setBuildSelect(data) {
		   fillSelect(data,"#build");
		}


function setDatePicker()
	{
        var myDate = new Date();
        var year = myDate.getFullYear();    //获取完整的年份(4位,1970-)
        var month = myDate.getMonth();       //获取当前月份(0-11,0代表1月)
        var data =  myDate.getDate();        //获取当前日(1-31)
        document.getElementById("startcharge").value = year+"-"+(month+1)+"-"+data;

        var ownerIntimeDatePicker="#startcharge";  //家庭成员入住时间
        initDatepicker(ownerIntimeDatePicker);
    }


function initstateSelect() {
	// 状态select数据
	var state = new Array("已售","空闲", "已租" );

	for (var i = 0; i < state.length; i++) {
		$("#state").append(
				"<option value='" + state[i] + "'>" + state[i] + "</option>");
	}

}

//提交房屋信息
function submitRoomData() {

	var build = $("#build").val();
	var nuit = $("#unit").val();
	var room = $("#room").val();
	alert(build);

	var number=build+"-"+nuit+"-"+room;
	alert(number);

	var buildingarea = $("#buildingarea").val();
	var innerarea = $("#innerarea").val();
	var publicarea = $("#publicarea").val();

	var type = $("#type").val();
	var targetgroup = $("#targetgroup").val();
	var startcharge = $("#startcharge").val();
	var state = $("#state").val();


	//	http://localhost:8080/MyBatis/building/submitRoomInfo.action?room.buildingnumber=1&&room.number=7-7-101

	//var formData = new FormData($("#uploadForm")[0]);


 
    var formData={"room.buildingnumber":build,"room.number":number,
		"room.buildingarea":buildingarea,"room.innerarea":innerarea,"room.publicarea":publicarea,
		"room.type":type,"room.startcharge":startcharge,"room.state":state}

	requestRoomMsg.data=formData;
	//getData1(requestRrepairMsg);

	getData(requestRoomMsg);
}


function submitInfoResult(result)
{
	if(result.success == true)
	{
	alert("提交信息成功");
	} 
	else
	{
		alert("提交信息失败  " + result.type);
	}
}

/*
//获取已录入的楼宇信息
    function getroomData()
    {
      var postData={pageSize:onePageSize,pageNo:currPage};
      requestRoomMsg1.callbackMethod=setRoomData;
      requestRoomMsg1.data=postData;
     // requestBuildingMsg.url="../../building/listBuildingInfoByPage.action";
      
      getDataWithFileUpload(requestRoomMsg1);
    }
  //显示已录入的楼宇信息
    function setRoomData(data)
    {
      
      var dataArray=new Array(); //数据对象数组
      for(var i=0;i<data.result.length;i++)
      {
        RoomMsg=new Object();
        RoomMsg.buildingnumber=data.result[i].buildingnumber;
        RoomMsg.floor=data.result[i].floor;
        RoomMsg.number=data.result[i].number;
        RoomMsg.buildingarea=data.result[i].buildingarea;
        RoomMsg.innerarea=data.result[i].innerarea;
        RoomMsg.publicarea=data.result[i].publicarea;
        RoomMsg.type=data.result[i].type;
        RoomMsg.targetgroup=data.result[i].targetgroup;
        RoomMsg.startcharge=data.result[i].startcharge;
        RoomMsg.state=data.result[i].state;

        dataArray.push(RoomMsg);
      }

      var sequenceArray=new Array();//序列数组
      sequenceArray[0]="buildingnumber";
      sequenceArray[1]="floor";
      sequenceArray[2]="number";
      sequenceArray[3]="buildingarea";
      sequenceArray[4]="innerarea";
      sequenceArray[5]="publicarea";
      sequenceArray[6]="type";
      sequenceArray[7]="targetgroup";
      sequenceArray[8]="startcharge";
      sequenceArray[9]="state";

      totalPage=data.totalPage; 
      $("#notice_tableBody tr").not("tr:first").remove(); //清空除第一行之外的表内容

      addTableBody("notice_table",dataArray,sequenceArray); //调用公用添加table方法
      
      if (!isinit) {
        pageChange(requestRoomMsg1,pageid,currentSpanId,totalPage,totalcount,visiblepage,onePageSize,currPage);
        isinit=1;
      }

    }

    function updateRequestObj(num)
    {
      var postDataString={pageSize:onePageSize,pageNo:num};
      requestRoomMsg1.data=postDataString;
      return requestRoomMsg1;
    }


     
    function updatePostString(num)
    {
      var postDataString="{pageSize:"+onePageSize+",pageNo:"+num+"}";

      return postDataString;
    }

*/