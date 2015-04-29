
var requestAddRemoveMsg={
    //url:URL_SUBMIT_ROOMOWNER,
    callbackMethod:submitRemoveResult,
    data:""
};

$(document).ready(function(){
        
    setDatePicker(); //初始化datepicker
    var str = $.getUrlParam('roomnumber'); //这是一字符串 
    var strs = new Array(); //定义一数组 
    strs = str.split("-"); //字符分割
    document.getElementById("name").value = strs[3];
    document.getElementById("roomnumber").value = strs[0] + "-" + strs[1] + "-" + strs[2];

		$("#remove_button").click(function (){ 
		removeOwnerData();
		});  
    }); 

	 function setDatePicker()
    {
        var ownerRemoveDatePicker="#removeDate";  //住户迁出时间
        initDatepicker(ownerRemoveDatePicker);
	}

	 function removeOwnerData()
    {
	    var waterCharge =$("#waterCharge").val(); 
    	var electricCharge =$("#electricCharge").val();
    	var propertyCharge =$("#propertyCharge").val();
    	var removeDate =$("#removeDate").val(); 
    	var operator =$("#operator").val(); 
    
        var urlString=requestAddRemoveMsg.url;
        var callBackName=requestRoomownerMsg.callbackMethod;
        var formData = new FormData($("#uploadForm")[0]);
        requestAddRemoveMsg.data=formData;
    
        getDataWithFileUpload(requestAddRemoveMsg);
    }

	 function submitRemoveResult(result)
	{
		if(result.success == true)
		{
			alert("迁出业主成功");
		} 
		else
		{
			alert("迁出操作失败,请重新提交" + result.type);
		}
		
	 }

	 (function ($) {
	     $.getUrlParam = function (name) {
	         var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	         var r = window.location.search.substr(1).match(reg);
	         if (r != null)
	             return unescape(r[2]);
	         return null;
	     }
	 })(jQuery);