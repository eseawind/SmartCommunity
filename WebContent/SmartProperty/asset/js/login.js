
	$(document).ready(function(){//这个就是jQuery ready ，它就像C语言的main 所有操作包含在它里面 
		getCookie();			
		$("#button_login").mousedown(function(){ 	
			login();
		}); 
			
	}); 

	    function getCookie()
	    {
	        	
	        var userId=$.cookie("userID");
	        //var pass=$.cookie("usePass");
	        if(userId!=null)
	        {
	        	$("#username").val(userId);
	        }
		/*
	        if(pass!=null)
	        {
	        	$("#userpass").val(pass);
	        }
		*/
	    }


		function login()
		{
			//alert($.cookie("name"));
			var name=$("#username").val();
			var pass=$("#userpass").val();

			if(name=='' || pass=='')
			{
			    //alert("not null");
			}
			else
			{
			
				doLogin(name,pass);	
			}  
		}

		
		//表单的提交
		function doLogin(userName,passWord)
		{
			
		      //使用Ajax技术与服务器交互
			$.ajax({ //一个Ajax过程 
				type: "post", //以post方式与后台沟通 
				url : "../userManage/login.action",
				dataType:'json',//从服务器端返回的值以 JSON方式 解释 
				data: {name:userName,password:passWord} ,//提交参数
				success: function(json,text){//如果成功 表示返回值为200
					//alert("name-->"+json.name); 
					if( json.success == true){ //根据返回值中的success的值来判断是否登录成功
					//	$.cookie("USERNAME",json.name);
					//	$.cookie("USERID",userName);
						//alert(document.cookie);
						//$.cookie("example", "foo",{expires:1},{path:"/"});  //保存10天
						setCookie(userName,passWord);
		    				window.location.href="index.html";
	
					}else{					//验证失败
						alert("用户名或者密码错误"); 
					}
				},
				error: function (XmlHttpRequest, textStatus, errorThrown) {  
                	//alert(textStatus);  
            	    }
		});

/*

			$.post( 
				"../webs/loginUser.action", 
				{ 
					username:userName, 
					password:passWord
				}, 
				function (data) 
				{ 
					var myjson=''; 
					eval('myjson=' + data + ';');  
					//alert(myjson.success);
					//var sessionid=$.cookie("JSESSIONID");
					var sessionid=getSessionId();
					if( myjson.success == true){
						alert(sessionid);
						window.location.href="index.html";
					}
				} 
			);  
*/

		 }

/*
		function getSessionId(){
			var c_name = 'JSESSIONID';
			if(document.cookie.length>0){
			    c_start=document.cookie.indexOf(c_name + "=")
			    if(c_start!=-1){ 
				  c_start=c_start + c_name.length+1 
				  c_end=document.cookie.indexOf(";",c_start)
				  if(c_end==-1) c_end=document.cookie.length
				  return unescape(document.cookie.substring(c_start,c_end));
			    }
			}
		}
*/
		function setCookie(userName,passWord)
		{
			
/*
			var userId=$.cookie("userID");
			//判断是否已经保存了name cookie
			if(userId==null)
			{
				$.cookie("userID",userName,{expires:1});
				alert("cookie null");
				
			}
			else
			{
				alert("not null:"+$.cookie("userID"));
				
				if(userId!=userName)
				{
					$.cookie("userID",userName,{expires:1});

					alert("new username"+$.cookie("userID"));
				}
				
			}
*/
			var isRememberChecked;
			//判断是否选择记住密码
			if($("#remember_pass").attr("checked"))
			{
				isRememberChecked=true
			}
			else
			{
				isRememberChecked=false
			}
		//	alert(isRememberChecked);
			
			if(isRememberChecked==true)
		    	{
		    	/*
				var pass=$.cookie("usePass");
			    	if(pass==null)
				{  
					$.cookie("usePass", passWord,{expires:1}); 
					alert("pass no cookie"); 
				}
				else
				{
					if(pass!=passWord)
					{
						$.cookie("usePass", passWord,{expires:1}); 
						alert("new pass:"+$.cookie("usePass"))
					}
				}
			*/

				var userId=$.cookie("userID");
				//判断是否已经保存了name cookie
				if(userId==null)
				{
					$.cookie("userID",userName,{expires:1});
					alert("cookie null");
				
				}
				else
				{
					alert("not null:"+$.cookie("userID"));
				
					if(userId!=userName)
					{
						$.cookie("userID",userName,{expires:1});
						//alert("new username"+$.cookie("userID"));
					}
				
				}

		    	}
			
		}

       
