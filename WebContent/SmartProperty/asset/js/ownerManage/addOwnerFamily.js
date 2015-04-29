$(document).ready(function(){
        
        initSelect(); //初始化select列表
        setDatePicker(); //初始化datepicker

		$("#submit_familyinfo_button").mousedown(function(){ 
			getFamilyData();

		});  
    }); 
    
    function setDatePicker()
    {
      
        var ownerIntimeDatePicker="#family_intime";  //家庭成员入住时间
        initDatepicker(ownerIntimeDatePicker);

        var ownerIntimeDatePicker="#family_outtime";  //家庭成员迁出时间
        initDatepicker(ownerIntimeDatePicker);

    }

	//初始化select
    function initSelect()
    {
    	//与户主关系select数据
    	var relationship=new Array("户主","父亲","母亲","儿子","女儿","妻子","丈夫","配偶","孙子","孙女","爷爷","奶奶","外公","外婆","哥哥","姐姐","弟弟","妹妹","弟媳妇","姐夫","妹夫","姑姑","姨","亲戚","朋友","同事");

    	for(var i=0;i<relationship.length;i++)
    	{
    		$("#relationselect").append("<option value='" + relationship[i] + "'>" + relationship[i] + "</option>");
    	}

    }


    //获得所填家庭信息的数据
    function getFamilyData()
    {
    	

    	//以下为家庭成员信息
    	var familyName =$("#family_name").val(); 
    	var relationSelect =$("#relationselect").find("option:selected").text();
    	var familyId =$("#family_id").val();
        var familyLiveplace =$("#family_liveplace").val(); 
    	var familySex =$("#family_sex").val(); 
    	var familyNation =$("#family_nation").val(); 
    	var familyPolitical =$("#family_political").val();
    	var familyMarrystate=$("#family_marrystate").val(); 
    	var familyNativeplace =$("#family_nativeplace").val(); 
    	var familyBirthplace =$("#family_birthplace").val(); 
    	var familyPhone =$("#family_phone").val(); 
    	var familyQq =$("#family_qq").val(); 
    	var familyEducation =$("#family_education").val(); 
    	var familyWorkplace =$("#family_workplace").val(); 
    	var isInsured=$("#isInsured").find("option:selected").text();
    	var isLowInsured =$("#isLowInsured").find("option:selected").text();
    	var isDisability =$("#isDisability").find("option:selected").text();
    	var isJunLie =$("#isJunLie").find("option:selected").text();
    	
    	var whetherFamilyphoto;
		//判断是否勾选“是否纸质照片”
		if($("#whether_familyphoto").attr("checked"))
		{
			whetherFamilyphoto=true
		}
		else
		{
			whetherFamilyphoto=false
		}
        
    	var familyInterest =$("#family_interest").val(); 
    	var familyIntime =$("#family_intime").val();
    	var familyOuttime =$("#family_outtime").val();
    	var familyRemark =$("#family_remark").val();
    	
    	alert(familyName+" "+relationSelect+" "+" "+familyLiveplace+familyId+" "+familySex+" "+familyNation+" "+familyPolitical+" "+familyMarrystate+" "+familyNativeplace+" "+familyBirthplace+" "+familyPhone+" "+familyQq+" "+familyEducation+" "+familyWorkplace+" "+isInsured+" "+isLowInsured+isDisability+" "+isJunLie+" "+familyInterest+" "+familyIntime+" "+familyOuttime+" "+familyRemark+" "+whetherFamilyphoto);
	
    }

	function   remove()   
	{   
      result="移除该家庭成员？？"   
      if(confirm(result))   
      {   
			alter("删除记录成功！");
      }   
      else   
      {   
		   alter("取消删除。");
        //  window.location.href="#";
       }   
	}  