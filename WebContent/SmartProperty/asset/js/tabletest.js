	$(document).ready(function(){ 
		
		addTableHead();

		addTableBody();

		addTableHandle();
	}); 


	var tableID="#testTable";

	function addTableHead()
	{
		var tableHeadData = new Array("姓名","性别","年龄","年级","学校","国家");
		tableHead(tableID,tableHeadData);

	}


	function addTableBody()
	{
		//var tableID="#testTable";

		var dataArray = new Array();   
		for(var i=0;i<20;i++)
		{ 
 			dataArray[i]=new Array(); 
			for(var j=0;j<6;j++)
			{ 
 				 dataArray[i][j]=i.toString() +j.toString(); 
 			}
		}

		tableBody(tableID,dataArray);
	}

	function addTableHandle()
	{
		//var tableID="#testTable";
		tableHandle(tableID);
	}
