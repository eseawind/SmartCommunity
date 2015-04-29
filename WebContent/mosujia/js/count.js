// JavaScript Document
$(document).ready(function(){
	$(".add").click(function(){ 
		var t=$(this).parent().find('em');
		t.text(parseInt(t.text())+1); 
		setTotal();	    
	}) 
	$(".min").click(function(){ 
		var t=$(this).parent().find('em');
		t.text(parseInt(t.text())-1); 
		if(parseInt(t.text())<0){ 
			t.text(0); 
		}
		setTotal();	 
	
	})

	function setTotal(){ 
		var s=0; 
		$("#menu .ui-li-aside").each(function(){ 
			var a=parseInt($(this).find('em').text());
			var b=parseFloat($(this).find('b').text());
			s+=a*b; 
		}); 
		$("#count").html(s.toFixed(2)); 
		$("#jiesuan").text("去结算").css({"text-align":"center","color":"red"})
	} 
})

