
        var requestBuildMsg = {
            url: URL_QUERY_BUILD,
            callbackMethod: setBuildSelect,
            data: ""
        };

        var requestUnitMsg = {
            url: URL_QUERY_UNIT,
            callbackMethod: setUnitSelect,
            data: ""
        };

        var requestRoomMsg = {
            url: URL_QUERY_ROOM,
            callbackMethod: setRoomSelect,
            data: ""
        };


        var requestComplaintMsg = {
            url: URL_SUBMIT_COMPLAINT,
            callbackMethod: submitComplaint,
            data: ""
        };

        var buildNo = 0;
        var uintNo = 0;
        var roomNo = 0;

        $(document).ready(function () {

            var datepicker = "#demand_time";
            initDatepicker(datepicker);
          
            getBuildData();//��ʼ��¥����

            $("#button_submmit").click(function () {
            	getDemandsData() ;
            });
            $("#button_reset").click(function () {
                //$("#preview").text("");
                $("#button_submmit").removeAttr("disabled");
            });
            $('#search_build').change(function () {
                buildNo = $(this).children('option:selected').val();
                getUnitData();

            });
            $('#search_unit').change(function () {
                buildNo = $('#search_build').children('option:selected').val();
                unitNo = $(this).children('option:selected').val();
                getRoomData();


            });
            $('#search_room').change(function () {
                roomNo = $(this).children('option:selected').val();
            });

        });

        function getBuildData() {
            var postData = {};
            requestBuildMsg.data = postData;
            getData(requestBuildMsg);
        }
        function getUnitData() {
            var postData = {
                "buildNo": buildNo
            };
            requestUnitMsg.data = postData;
            getData(requestUnitMsg);
        }
        function getRoomData() {
            var postData = {
                "buildNo": buildNo,
                "unitNo": unitNo

            };
            requestRoomMsg.data = postData;
            getData(requestRoomMsg);
        }

        function setBuildSelect(data) {
            fillSelect(data, "#search_build");
        }

        function setUnitSelect(data) {
            $("#search_unit").empty();
            $("#search_room").empty();
            fillSelect(data, "#search_unit");

        }

        function setRoomSelect(data) {
            $("#search_room").empty();

            fillSelect(data, "#search_room");

        }

        function getDemandsData() {
            var build = $("#search_build").val();
            var unit = $("#search_unit").val();
            var room = $("#search_room").val();
            var roomnumber = build + "-" + unit + "-" + room;
   
            var customerName = $("#name").val();
            var customerPhone = $("#phone").val();
            var demandTime = $("#demand_time").val();
            var remarks = $("#remarks").val();
            var demandContent = $("#demand_content").val();
            var images = $("#imagesid").val();
            var formData = {
                "complaints.roomnumber": roomnumber,
                "complaints.name": customerName,
                "complaints.phone": customerPhone,
                "complaints.time": demandTime,
                "complaints.remarks": remarks,
                "complaints.content": demandContent,
                "images":images
            };
         
            requestComplaintMsg.data = formData;
            getData(requestComplaintMsg);

        }
        function submitComplaint(result) {
            if (result.success == true) {
                alert("sucess");
               
            }
            else {
                alert("false" + result.type);
            }
        }
