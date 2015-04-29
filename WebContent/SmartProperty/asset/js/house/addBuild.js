//请求楼宇对象
  var requestBuildingMsg ={
    url: URL_ADD_BUILDING,
    callbackMethod : submitInfoResult,
    data:""
  };
 
  
  var onePageSize=10;   //请求服务器每页数据的大小
  var currPage=1;       //当前第几页
  var totalPage;     //总页数
  var finishedTotalPage;     //总页数
  var totalcount;   //总条数
  var visiblepage=10;   //显示的页面按钮数
    
  var isinit=0;  //判断是否是初始化
 
  var tableID="#notice_table";
  var tableBodyID="#notice_tableBody";
  var pageid="#pagination";
  var currentSpanId="#sp"; 
  
  //已录入楼宇对象
    var BuildingMsg={
      buildnum:"" ,
      buildtype:"" ,
      buildname:"" ,
      locate:"" ,
      manage:"" ,
      structure:"" ,
      direction:"" ,
      complatetime:"",
      demo:"",
      edit:"",
      dele:""

    }

function setDatePicker()
    {
      
		var myDate = new Date();
        var year = myDate.getFullYear();    //获取完整的年份(4位,1970-)
        var month = myDate.getMonth();       //获取当前月份(0-11,0代表1月)
        var data =  myDate.getDate();        //获取当前日(1-31)
        document.getElementById("complatetime").value = year+"-"+(month+1)+"-"+data;

        var ownerIntimeDatePicker="#complatetime";  //家庭成员入住时间
        initDatepicker(ownerIntimeDatePicker);
    }


$(document).ready(function ()
  {
    setDatePicker(); //初始化datepicker
    initorientationSelect();
    //getbuildingData();
    //tableHandle(notice_table);
    
    $("#button_submmit").click(function ()
    {
     
	  
	  if($("#buildnum").val()=='')
		{
			alert("楼宇编号不能为空");
		}
		else
		{
			if(confirm("是否确认提交信息？"))
			{
              submitBuildData(); 
			}
		}  


    });
	
	
  });

function initorientationSelect() {

  // 房号select数据
  var direction = new Array("东","南","西","北","东南","东北","西南","西北");

  for (var i = 0; i < direction.length; i++) {
    $("#direction").append(
        "<option value='" + direction[i] + "'>" + direction[i] + "</option>");
  }

}

//提交小区信息
function submitBuildData() {

  var buildnum = $("#buildnum").val();
	alert(buildnum);

  var buildtype = $("#buildtype").val();
  var buildname = $("#buildname").val();
  var locate = $("#location").val();

  var manage = $("#manage").val();
  var structure = $("#structure").val();
  var direction = $("#direction").val();
  var complatetime = $("#complatetime").val();

  var demo = $("#demo").val();

  //var formData = new FormData($("#uploadForm")[0]);
  //http://localhost:8080/MyBatis/building/submitBuildingInfo.action?building.number=122


  var formData = {"building.number":buildnum,"building.type":buildtype,
	  "building.name":buildname,"building.address":locate,"building.manager":manage,
	  "building.struct":structure,"building.orientation":direction,"building.remarks":demo,"building.complatetime":complatetime}


 //var formData = {"building.number":buildnum};
  requestBuildingMsg.data=formData;

 // alert(buildnum+ " " +buildtype+ " " + buildname + " " + location+ " " + manage + " " + structure+ " " + direction + " " + complatetime+ " " + demo);

  getData(requestBuildingMsg);
  
}


function submitInfoResult(result)
{
  if(result.success == true)
  {
    alert("提交信息成功");
    
  } 
  else
  {
    alert("提交信息失败" + result.type);
  }
  
}

/*

//获取已录入的楼宇信息
    function getbuildingData()
    {
      var postData={pageSize:onePageSize,pageNo:currPage};
      requestBuildingMsg1.callbackMethod=setBuildingData;
      requestBuildingMsg1.data=postData;
     // requestBuildingMsg.url="../../building/listBuildingInfoByPage.action";
      
      getDataWithFileUpload(requestBuildingMsg1);
    }
  //显示已录入的楼宇信息
    function setBuildingData(data)
    {
      
      var dataArray=new Array(); //数据对象数组
      for(var i=0;i<data.result.length;i++)
      {
        BuildingMsg=new Object();

        BuildingMsg.buildnum =data.result[i].number;
        BuildingMsg.buildtype=data.result[i].type;
        BuildingMsg.buildname=data.result[i].name;
        BuildingMsg.location=data.result[i].address;
        BuildingMsg.manage=data.result[i].manager;
        BuildingMsg.structure=data.result[i].struct;
        BuildingMsg.direction=data.result[i].orientation;
        BuildingMsg.complatetime=data.result[i].completiondate;
        BuildingMsg.demo=data.result[i].remarks;

        BuildingMsg.edit="<a href='#' onclick='editClick("+data.result[i].id+")'>" + "修改" + "</a>";
        BuildingMsg.dele="<a href='#' onclick='deleClick("+data.result[i].id+")'>" + "删除" + "</a>";

        dataArray.push(BuildingMsg);
      }
      

      var sequenceArray=new Array();//序列数组
      sequenceArray[0]="buildnum";
      sequenceArray[1]="buildtype";
      sequenceArray[2]="buildname";
      sequenceArray[3]="location";
      sequenceArray[4]="manage";
      sequenceArray[5]="structure";
      sequenceArray[6]="direction";
      sequenceArray[7]="complatetime";
      sequenceArray[8]="demo";
      sequenceArray[9]="edit";
      sequenceArray[10]="dele";

      totalPage=data.totalPage; 
      $("#notice_tableBody tr").not("tr:first").remove(); //清空除第一行之外的表内容

      addTableBody("notice_table",dataArray,sequenceArray); //调用公用添加table方法
      
      if (!isinit) {
        pageChange(requestBuildingMsg1,pageid,currentSpanId,totalPage,totalcount,visiblepage,onePageSize,currPage);
        isinit=1;
      }

    }

    function updateRequestObj(num)
    {
      var postDataString={pageSize:onePageSize,pageNo:num};
      requestBuildingMsg1.data=postDataString;
      return requestBuildingMsg1;
    }


     
    function updatePostString(num)
    {
      var postDataString="{pageSize:"+onePageSize+",pageNo:"+num+"}";

      return postDataString;
    }
	*/

   /* function edit(id,data)
    {

      for(var i=0;i<data.result.length;i++)
      {
        if(data.result[i].id==id)
        {
          document.getElementById("buildnum").value=data.result[i].number;
          document.getElementById("buildtype").value=data.result[i].type;
          document.getElementById("buildname").value=data.result[i].name;
          document.getElementById("location").value=data.result[i].address;
          document.getElementById("manage").value=data.result[i].manager;
          document.getElementById("structure").value=data.result[i].struct;
          document.getElementById("direction").value=data.result[i].orientation;
          document.getElementById("complatetime").value=data.result[i].completiondate;
          document.getElementById("demo").value=data.result[i].remarks;
        }
      }
        
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
*/