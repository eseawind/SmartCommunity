//此方法适用于获得table的数据
function getTableData(urlString, postDataString, tableid, tablebodyid) {

	 $.ajax({ 
	  	type: "post", 
	  	url: urlString, 
	  	dataType: "json", 
	  	data:postDataString,
	  	success: function(data){ 
	  		if(data.success == true){
	 			initTableData(tableid,tablebodyid,data);
	    	}else{ //验证失败 
	    		alert("获取信息失败");
	    	} 
	    }, 
	    error: function(XmlHttpRequest, textStatus, errorThrown) 
	    { 
	    	alert(textStatus); 
	    } 
	});
	 
	//initTableData(tableid, tablebodyid, null);

}


function getData(requestMsg) {

	var url = requestMsg.url;
	var sendMsg = requestMsg.data;
	//alert(sendMsg.pageNo);
	var callBackName = requestMsg.callbackMethod;

	$.ajax({
		type : "post",
		url : url,
		dataType : "json",
		data: sendMsg,
		success : function(data) {
			
			callBackName(data);
		},
		error : function(XmlHttpRequest, textStatus, errorThrown) {
			alert(textStatus);
		}
	});
}

function getData2(requestMsg) {

    var url = requestMsg.url;
    var sendMsg = requestMsg.data;
    //alert(sendMsg.pageNo);
    var callBackName = requestMsg.callbackMethod;

    $.ajax({
        type: "post",
        url: url,
        dataType: "json",
        data: sendMsg,
        async: false,    // 异步为false
        success: function (data) {

            callBackName(data);
        },
        error: function (XmlHttpRequest, textStatus, errorThrown) {
            alert(textStatus);
        }
    });
}

function getDataWithFileUpload(requestMsg) {

	var url = requestMsg.url;
	var sendMsg = requestMsg.data;
	// var sendMsg=$.toJSON(requestMsg.data);
	var callBackName = requestMsg.callbackMethod;

	  ;

	$.ajax({
		type : "post",
		url : url,
		processData : false,
		contentType : false,
		dataType : "json",
		data : sendMsg,
		success : function(data) {

		//alert("请求查询:"+sendMsg.pageSize+"   后台返回多少条:"+data.result.length);

			callBackName(data);
			

		},
		error : function(XmlHttpRequest, textStatus, errorThrown) {
			alert(textStatus);
		}
	});

}

function fillSelect(data,selectID) {
    var dataArray = new Array(); //数据对象数组
    for (var i = 0; i < data.result.length; i++) {
        Msg = new Object();
        Msg.name = data.result[i].name;
        Msg.number = data.result[i].number;
        dataArray.push(Msg);
    }
    $(selectID).empty();
    $(selectID).append(
        "<option value='" + 0 + "'>" + "---请选择---" + "</option>");
    for (var i = 0; i < dataArray.length; i++) {
        $(selectID).append(
                "<option value='" + dataArray[i].number + "'>" + dataArray[i].name + "</option>");
    }
    
    
}


/** 把table的所有行打包成formdata数组  */
function tableRow2FormData(startData, tableid, formData) {
    var formDatas = [];
    var t = document.getElementById(tableid);
    var rl = t.rows.length;
    for (var i = 1; i < rl; i++) {
        var cl = t.rows[i].cells.length;
        var q = startData;
        for (var j in formData) {     
            formData[j] = t.rows[i].cells[q].innerText;
            q++;
        }
        formDatas.push(formData);
    }
    return formDatas;
}

/** 
获取form的每个值，添加到表格的新建一行    p为新建行的id,全局变量
*/
function form2TableNewRow(formDataArry,tableId,p)
{
    var Msg = {
        name: "",
        number: ""
    };
    var table = document.getElementById(tableId);
    var tdLength=formDataArry.length;
    var td=new Array();
    var row1 = $("<tr id="+p+"></tr>");
    for(var i=0;i<tdLength;i++)
    {
        td[i]="<td>"+formDataArry[i]+"</td>";
        row1.append(td[i]); 
    }
    var table1 = $('#table');
    table1.append(row1); 
    p++;
    return p;
}
/** 从表格中获取数据，添加到同一页面form的input项。  */
function tableRow2Form(Msg, id, startData) {
    for (var i = 0; i < Msg.length; i++) {
        var j = i + startData;
        document.getElementById(Msg[i]).value = $("tr[id=" + id + "]").children("td:eq(" + j + ")").text();
    }
}
/** 获取form的每个值，添加到表格的双击制定的一行 （修改数据）   */
function changeForm2Table(Msg,p,startData) {
    for (var i = 0; i < Msg.length; i++)
    $("tr[id=" + p + "]").children("td:eq("+(i+startData)+")").html(document.getElementById(Msg[i]).value);
}

/** 根据formdata数组循环提交到服务器。    */
function submitFormDatas(requestMsg,formDatas) {
    for (var i = 0; i < formDatas.length; i++) {
        requestMsg.data = formDatas[i];
        getData(requestMsg);
    }

}

//修改样式 跟随输入框选择框变化
function changeCss() {
    var size = $(".input_font").css("font-size");
    if (size == "14px")
        $("input").removeClass().addClass("input-sm form-control input_font");
    else if (size = "17px")
        $("input").removeClass().addClass("input-md form-control input_font");
    else
        $("input").removeClass().addClass("input-lg form-control input_font");

    var size_select = $(".select_font").css("font-size");
    if (size_select == "14px")
        $("select").removeClass().addClass("form-control input-sm select_font");
    else if (size_select = "17px")
        $("select").removeClass().addClass("form-control input-lg select_font");
    else
        $("select").removeClass().addClass("form-control input-lg select_font");
}

