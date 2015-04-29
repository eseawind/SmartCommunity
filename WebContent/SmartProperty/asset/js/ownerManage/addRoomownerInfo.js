
var requestRoomownerMsg={
    url:URL_SUBMIT_ROOMOWNER,
    callbackMethod:submitroomownerInfoResult,
    data:""
};

//全局变量
var cur_submit_id;
var cur_room;
var state_string = "";
var p=0;  //用于自增table 的tr id
var insert_id = "";  //存储当前选中的tr id
var flag;  //check函数检测是否输入不规范
var sequenceArray = new Array();//form数组


var Msg = new Array(
            "roomnumber",
            "name",
            "sex",
            "identity",
            "registeredresidence",
            "telephone",
            "propertyowner",
            "propertyowneridentity",
            "immigratedate",
            "type",
            "mail",
            "qq",
            "company",
            "hobbies",
            "nativeplace",
            "nation",
            "educationlevel",
            "maritalstatus",
            "politicalaffiliation"
            )


$(document).ready(function(){
        changeCss();
		ownerMsg=new Object();
        setDatePicker();
		//下一个
        $("#next_button").click(function () {
            flag = 1;
		    check();
		    if (flag == 0)
		        return;
		    add_table_array();
		    p = form2TableNewRow(sequenceArray, "table", p);
            //设置隐藏列
		    for (var i = 10; i < 20;i++)
		        $('#table tr').find('td:eq(' + i + ')').hide();
		    setTable();
			
		}); 
		//修改保存
		
		$("#save_change_button").click(function(){
			save_change();
		});
		

		//双击行显示信息
		$("#table tbody").on("dblclick","tr", function (event) {
	
		    insert_id = $(this).attr("id");
		    tableRow2Form(Msg, insert_id,1);
		}); 

		$("#delete_button").click(function (){ 
		del();
		});
		
		$("#exit_button").click(function () {
		    window.location.href = 'addRoomownerInfo.html';
		});
        $(document).on("click", "#submit_button", function (event) {
            getroomownerData();
            var checked = $("input:checked");
            $(checked).each(function () {
                $(this).parent().parent().remove();
            });
            alert(state_string);
            state_string = "";
		});
		
		$("th.hide").hide();
    }); 


    //设置添加表行的数组值
    function add_table_array() {
        sequenceArray[0] = "<input type='checkbox' name='owner' value='New'>";
        for (var i = 0; i < Msg.length; i++) {
            sequenceArray[i + 1] = document.getElementById(Msg[i]).value;
      }
    }
    function setTable() {
        $("#table").dataTable({
            "aLengthMenu": [[8, 16, 32, 64], [8, 16, 32, 64]],
            "bFilter": false,
            "bRetrieve": true,
            "bSort": true,
            "bInfo": false,
            "bJQueryUI": false,
            "iDisplayLength": 8,
            "bPaginate": false,          //翻页功能
            "bLengthChange":false,       //改变每页显示数据数量
            "aaSorting": [[1, "desc"]],//设置第2个元素为默认排序     
            "aoColumnDefs": [{
                "bSortable": false, //指定不支持排序的列  
                "aTargets": [0]
            }],
            "oLanguage": {
                "sZeroRecords": "对不起，查询不到任何相关数据",
                "sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
                "sInfoEmpty": "记录数为0",
                "sSearch": "搜索:",
                "sInfoFiltered": "  ，共搜索 _MAX_ 条记录",
                "sLengthMenu": "每页显示 _MENU_ 条记录",
                "oPaginate": {
                    "sNext": "下一页",
                    "sPrevious": "上一页"
                }

            }
        });
    }

	///修改按钮点击事件 ，改变表格中值
	function save_change()
{
	    flag = 1;
	    if (insert_id == "") {
	        alert("未选中表格数据，请双击选择下方表格记录行");
	        return;
	    }
	    check();
	    if (flag == 0)
	        return;
	    changeForm2Table(Msg, insert_id,1);
		alert("修改数据成功！");
	}
	////////日期设置函数。
    function setDatePicker()
	{
        var myDate = new Date();
        var year = myDate.getFullYear();    //获取完整的年份(4位,1970-)
        var month = myDate.getMonth();       //获取当前月份(0-11,0代表1月)
        var data =  myDate.getDate();        //获取当前日(1-31)
        document.getElementById("immigratedate").value = year+"-"+(month+1)+"-"+data;

        var ownerIntimeDatePicker="#immigratedate";  //家庭成员入住时间
        initDatepicker(ownerIntimeDatePicker);
    }
/////////增加表格一条记录
    function check()
    {
        if ($("#roomnumber").val() == "") {
            alert("房间号不能为空！");
            flag = 0;
            return;
        }
        if ($("#name").val() == "") {
            alert("姓名不能为空！");
            flag = 0;
            return;
        }
        if ($("#identity").val() == "") {
            alert("身份证号不能为空");
            flag = 0;
            return;
        }
        if ($("#telephone").val() == "") {
            alert("联系电话不能为空");
            flag = 0;
            return;
        }
        if ($("#registeredresidence").val() == "") {
            alert("户口所在地不能为空");
            flag = 0;
            return;
        }
        if ($("#propertyowner").val() == "") {
            alert("产权人不能为空！");
            flag = 0;
            return;
        }
        if ($("#propertyowneridentity").val() == "") {
            alert("产权人身份证号不能为空！");
            flag = 0;
            return;
        }

    }
		  //删除打钩函数
			function del() 
			{ 
			 var checked =$("input:checked");
			  if(checked.size()==0){
					alert("需选中要删除的行！");
					return;
				}
			 $(checked).each(function()
				 {	
					$(this).parent().parent().remove();
			});
			} 

	
//获得表格数据，并提交
//通用写法（调试）
/*
			function getroomownerData() {
			    //var formDatas = new Array();
			    var formDatas = [];
			    var formData = {
			        "roomowner.roomnumber":"" ,
			        "roomowner.name":"",
			        "roomowner.sex": "",
			        "roomowner.identity": "",
			        "roomowner.registeredresidence": "",
			        "roomowner.telephone": "", 
			        "roomowner.propertyowner": "",
			        "roomowner.propertyowneridentity": "",
			        "roomowner.immigratedate": "",
                    "type":"",
			        "roomowner.mail": "",
			        "roomowner.qq": "",
			        "roomowner.company": "",
			        "roomowner.hobbies": "",
			        "roomowner.nativeplace": "",
			        "roomowner.nation": "",
			        "roomowner.educationlevel": "",
		      	    "roomowner.maritalstatus": "",			     
			        "roomowner.politicalaffiliation": ""
			    };

			    var tableStartData = 1;//////表格有效数据获取的起始位置（有些表格起始列为checkbox，获取数据时不用该列数据。下标从0开始）
			    var tableid = "table";
			    formDatas = tableRow2FormData(tableStartData, tableid, formData);
			    //console.log(requestRoomownerMsg.data['roomowner.number'] + "jjjjj");
			    //alert(formData['roomowner.number']);
			    //alert(requestRoomownerMsg.data['roomowner.number'] + "qqqqqqqqq");
			    //alert(formDatas[1]);
			    var callBackName = requestRoomownerMsg.callbackMethod;
			    submitFormDatas(requestRoomownerMsg, formDatas);
			    //getData(requestRoomownerMsg);  
			}
           */
            
			function getroomownerData()
			{
				var urlString=requestRoomownerMsg.url;
				var callBackName=requestRoomownerMsg.callbackMethod;
				var rows = $("#table tr").length;
				for (var i = 2; i <= rows; i++) {
					var formData = {
					    "roomowner.roomnumber": $("table tr:eq("+i+") td:eq(1)").text(),
					    "roomowner.name": $("table tr:eq("+i+") td:eq(2)").text(),
					    "roomowner.sex": $("table tr:eq("+i+") td:eq(3)").text(),
					    "roomowner.identity": $("table tr:eq("+i+") td:eq(4)").text(),
					    "roomowner.registeredresidence":$("table tr:eq("+i+") td:eq(5)").text(),
					    "roomowner.telephone": $("table tr:eq("+i+") td:eq(6)").text(),
					    "roomowner.propertyowner": $("table tr:eq("+i+") td:eq(7)").text(),
					    "roomowner.propertyowneridentity": $("table tr:eq("+i+") td:eq(8)").text(),
					    "roomowner.immigratedate": $("table tr:eq("+i+") td:eq(9)").text(),
					    "roomowner.type":$("table tr:eq("+i+") td:eq(10)").text(),
					    "roomowner.mail": $("table tr:eq("+i+") td:eq(11)").text(),
					    "roomowner.qq": $("table tr:eq("+i+") td:eq(12)").text(),
					    "roomowner.company": $("table tr:eq("+i+") td:eq(13)").text(),
					    "roomowner.hobbies": $("table tr:eq("+i+") td:eq(14)").text(),
					    "roomowner.nativeplace":$("table tr:eq("+i+") td:eq(15)").text(),
					    "roomowner.nation": $("table tr:eq("+i+") td:eq(16)").text(),
					    "roomowner.educationlevel": $("table tr:eq("+i+") td:eq(17)").text(),
					    "roomowner.maritalstatus": $("table tr:eq("+i+") td:eq(18)").text(),
					    "roomowner.politicalaffiliation": $("table tr:eq("+i+") td:eq(19)").text(),				   
					}
					cur_submit_id = i;
					cur_room = $("table tr:eq(" + i + ") td:eq(1)").text();
				requestRoomownerMsg.data=formData;
				getData2(requestRoomownerMsg);
				}
                
             }
             
////////回调函数
		function submitroomownerInfoResult(result)
		{
		    if (result.success == true) {
		        state_string += "房间号" + cur_room + "表项提交成功\n";
		        var rows = $("#table tr").length;
		        for (var i = cur_submit_id; i <= rows; i++) {
		            if ($("table tr:eq(" + i + ") td:eq(1)").text() == cur_room) {
		                $("#table tr:eq(" + (i - 1) + ")").find('input[type=checkbox]').attr("checked", true);
		                break;
		            }
		        }
		    }
		    else {
		        state_string += "房间号" + cur_room + "表项提交有误：" + result.type + "\n";
		    }	    
		
		}
 
