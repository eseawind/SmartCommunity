/*
		window.onload=function(){ 
			
	        var username=getvalue("username");
	        alert(USERNAME);

		} 
    */
	var flag=true;

        $(document).ready(function(){ 
		//var username=getvalue("username");

		var winHeight = parseInt(document.documentElement.clientHeight)-60;// 客户端浏览器高度
		document.getElementById("menuframe").height = winHeight + "px";
		document.getElementById("mainframe").height = winHeight + "px";
	        if (document.cookie.length>0){
	        	
	        	var name=$.cookie("USERNAME");
			//alert(name);
			$("#user_name").html(name);
	        }
	        else{
	        	//alert("no cookie");
	        }

		$("#hide_menu").click(function(){ 

			menuManage();

		}); 
	        
	}); 

	function menuManage()
        {
        	if(flag==true)
	       	{
	       			//alert(flag);
	       		document.getElementById("menuframe").width="1%";
				document.getElementById("mainframe").width="97%";
				
				document.getElementById("hide_menu").src="images/showmenu.JPG";
					//document.getElementById("hide_menu").id="show_menu";
				flag=false;
	       	}
	       	else
	       	{
	       		document.getElementById("menuframe").width="13%";
				document.getElementById("mainframe").width="85%";
				
				document.getElementById("hide_menu").src="images/hideimage.jpg";
				flag=true;
	       	}
        }

/*
		function getvalue(name)
		{
			var str=window.location.search;
			if (str.indexOf(name)!=-1)
			{
				var pos_start=str.indexOf(name)+name.length+1;
				var pos_end=str.indexOf("&",pos_start);
				if (pos_end==-1)
				{
					return str.substring(pos_start);
				}
				else
				{
					return str.substring(pos_start,pos_end)
				}
			}
			else
			{
				return "没有这个"+name+"值";
			}
		}
*/
