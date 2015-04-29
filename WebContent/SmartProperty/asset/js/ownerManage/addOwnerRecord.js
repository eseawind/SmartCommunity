$(document).ready(function(){
        
        initSelect(); //初始化select列表
        setDatePicker(); //初始化datepicker

        $("#submit_Ownerinfo_button").mousedown(function(){ 
			getOwnerData();
			
		});

    }); 
    
    function setDatePicker()
    {
        var ownerIntimeDatePicker="#owner_intime";  //业主入伙时间
        initDatepicker(ownerIntimeDatePicker);
    }

	//初始化select
    function initSelect()
    {

    	//原始装修select数据
    	var firstFitment=new Array("简装","毛坯");

    	for(var i=0;i<firstFitment.length;i++)
    	{
    		$("#first_fitment").append("<option value='" + firstFitment[i] + "'>" + firstFitment[i] + "</option>");
    	}
    }

	//获得所填业主信息的数据
    function getOwnerData()
    {
    	//以下为业主信息
    	var ownerRoom=$("#owner_buliding").val();
    	var ownerName=$("#owner_name").val();
    	var ownerId=$("#owner_id").val();  
    	var ownerRoomsize=$("#owner_roomsize").val();   
    	var ownerIntime =$("#owner_intime").val();  
    	var firstFitment =$("#first_fitment").find("option:selected").text(); //原始装修select

    	var whetherRental;
		//判断是否勾选“是否出租”
		if($("#whether_rental").attr("checked"))
		{
			whetherRental=true
		}
		else
		{
			whetherRental=false
		}

    	var comments=$("#comments").val();

    	alert(ownerRoom+" "+ownerName+" "+ownerId+" "+ownerRoomsize+" "+ownerIntime+" "+firstFitment+" "+whetherRental+" "+comments);
    }

    