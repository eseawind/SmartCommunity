/**
本文件中所有常量为全局使用
只要在使用的html页面引用本文件，就可以直接调用
*/
	/** 服务器IP */
	var HTTP = "";		//IP地址
	/** 服务器IP */
	var IP = "";		//IP地址
	/** 服务器端口 */
	var PORT = "";				//端口
	/** 程序名 */
	var NAME = "../../";		//目录名
	/** 用户登录 {request{name,password} ,reponse{{success,telephone,room,name,id}}} */
	var LOGIN = HTTP+IP+PORT+NAME+"userManage/login.action";		//登录地址
	/** 用户注销 {} */
	var LOGOUT = HTTP+IP+PORT+NAME+"userManage/logout.action";	//注销地址
	
	
	
	///////////////////////////////用户报修管理////////////////////////////////////////////////////////////////////////
	/** 用户报修 获取报修设备列表  {
		request:{null},
		response:{
			id,
			devicename}} 
	*/
	var URL_REPAIR_DEVICE = HTTP+IP+PORT+NAME+"repair/listDevices.action";//普通用户获得报修设备
	
	/** 用户报修 获取报修设备故障列表  {
		request:{
			id(设备名的id)
			},
		response:{
			id,
			deviceid,
			devicestate}} 
		*/
	var URL_REPAIR_DEVICEFAULT = HTTP+IP+PORT+NAME+"repair/listDeviceFault.action";//普通用户获取设备故障
	
	/** 用户报修 提交报修  {
		request:{
			roomnumber,
			username(报修人姓名),
			phone,
			devicename, 
			description, 
			images(上传的图片)(参数名前加 repair.)},
		response:{}} */
	var URL_REPAIR_ADD = HTTP+IP+PORT+NAME+"repair/submitRepairInfo.action";//普通用户添加报修地址
	
	/** 用户报修 提交反馈  {
		request:{
			id(报修信息的 id),
			feedback(反馈的信息)(参数前加 repair.)},
		response:{}} */
	var URL_REPAIR_FEEDBACK = HTTP+IP+PORT+NAME+"repair/submitFeedback.action";//普通用户反馈报修
	
	/** 用户报修 撤销报修  {
		request:{
			id(报修信息的 id)},
		response:{}} */
	var URL_REPAIR_CANCEL = HTTP+IP+PORT+NAME+"repair/cancelRepair.action";//普通用户撤销报修
	
	/** 用户报修 查看报修结果  {
		request:{
			pageSize(每页大小),
			pageNo(页号),
			finished(是否已完成)},
		response:{
			id,
			roomnumber,
			username(报修人姓名),
			phone,
			devicename, 
			description, 
			date(报修日期),
			processedsituation, 
			processedstate(-1:未完成,0:正在处理,1:已完成), 
			serviceman,
			processedtime(完成处理时间), 
			feedback(用户反馈), 
			repairimages(图片地址)[id, repairid, url(图片地址)}} */
	var URL_REPAIR_VIEW = HTTP+IP+PORT+NAME+"repair/listRepairInfoByPage.action";//普通用户查看报修地址
	///////////////////////////////用户报修管理////////////////////////////////////////////////////////////////////////
	


	/** 更改用户报修状态  {
		request:{
			id,
			processedstate=1
			},
		response:{
			true
			}} */
	var URL_REPAIR_STATE = HTTP+IP+PORT+NAME+"repair/setState.action";//更改用户报修状态
	///////////////////////////////更改用户报修状态////////////////////////////////////////////////////////////////////////

	
	/** 管理员添加设备，及其照片  {
		request{
			deviceName,
			deviceProblem,
			deviceImage
			}
		reponse{
			success
			}
		}
		*/
	var URL_DEVICE_ADD = HTTP+IP+PORT+NAME+"repair/submitDeviceInfo.action";//管理员添加设备
	
	
	//////////////楼宇房间信息管理/////////////////////////////////////////////
	/** 添加房间信息  {
		request:{buildingnumber(* 所属楼宇),
		number(* 房间号),
		buildingarea(建筑面积),
		innerarea(房内面积),
		publicarea(公共区域面积),
		state(房屋状态),
		type(房屋类型),
		floor(所属楼层),
		targetgroup(服务对象),
		startcharge(开始收费日期)(参数名前面加 room.)}
		response:{null}} */
	var URL_ADD_ROOM = HTTP+IP+PORT+NAME+"building/submitRoomInfo.action";	
	
	/**
		查询房间信息{
		request{
			buildingNumber(楼宇)
		},
		reponse{
			success,
			result:[ 
				buildingnumber(* 所属楼宇id) 
				number(* 房间号) 
				user 
				buildingarea 
				innerarea
				publicarea 
				state 
				type 
				floor 
				targetgroup 
				startcharge}}
	*/
	var URL_QUERY_ROOMLIST = HTTP+IP+PORT+NAME+"building/listRoomInfoByPage.action";	
	
	
	/**
		添加楼宇信息{
		request{
			number(* 楼宇编号),
			manager(管理处),
			name(楼宇名称),
			address(楼宇地址),
			struct(楼宇结构),
			orientation(楼宇结构),
			type(楼宇类型),
			remarks(备注)(参数名前加 building.)
			}
		reponse{
			success}
		}
	*/
	var URL_ADD_BUILDING = HTTP+IP+PORT+NAME+"building/submitBuildingInfo.action";	
	
	
	/**
		查询楼宇信息楼宇信息{
		request{
			pageSize(每页大小),
			pageNo(页号)
			}
		reponse{
			success,
			result[
				id,
				number(楼宇编号),
				manager(管理处),
				name(楼宇名称),
				address(楼宇地址),
				struct(楼宇结构),
				orientation(楼宇结构),
				type(楼宇类型),
				remarks(备注)
				]}
		}
	*/
	var URL_QUERY_BUILDING = HTTP+IP+PORT+NAME+"building/listBuildingInfoByPage.action";	
	//////////////楼宇房间信息管理/////////////////////////////////////////////
	
	
	//////////////抄表管理/////////////////////////////////////////////
	/**
		提交水电表信息{
		request{
			roomnumber(* 房间号),
			waterconsum(* 水量),
			elecconsum(* 电量),
			readman(抄表人),
			readtime(抄表时间)
			注 : 水费和电费至少填写一项
			}
		reponse{
			success,
			}
		}
	*/
	var URL_SUBMIT_METER = HTTP+IP+PORT+NAME+"meter/submitMeterInfo.action";	
	//////////////抄表管理/////////////////////////////////////////////
	
	
	
	/**
		查询投诉信息{
		request{
			pageSize(每页大小),
			pageNo(页号),
			isFinished(是否已完成)
			}
		reponse{
			success,
			result:[
				id,
				roomnumber,
				name,
				phone,
				content, 
				requiredresult,
				keywords, 
				processedsituation, 
				processedstate, 
				requiredresult,
				time, 
				complaintsimages:[
					id,
					complaintsid,
					url]]} 
					(参数名前加 complaints.)
			}
		}
	*/
	var URL_QUERY_COMPLAINT = HTTP+IP+PORT+NAME+"complaints/listComplaints.action";	
	//////////////查询投诉信息/////////////////////////////////////////////

	
	/**
		提交投诉信息{
		request{
			roomnumber(* ),
			name,
			phone(* ),
			content(*),
			requiredresult,
			remarks (参数名前加 complaints.)
			images(图片)
			}
		reponse{
			success,
			}
		}
	*/
	var URL_SUBMIT_COMPLAINT = HTTP+IP+PORT+NAME+"complaints/submitComplaints.action";	
	//////////////提交投诉信息/////////////////////////////////////////////
	
	//////////////客户管理/////////////////////////////////////////////
	/**
		提交客户信息{
		request{
			roomnumber(*),
			name(*),
			sex,
			identity(* 身份证号),
			nativeplace(籍贯),
			registeredresidence(户口所在地),
			nation(民族),
			politicalaffiliation(政治面貌),
			educationlevel,
			company(工作的公司),
			maritalstatus(婚姻状况),
			hobbies,
			telephone,
			qq,
			mail,
			immigratedate(迁入日期),
			quitdate(迁出日期),
			propertyowner(* 产权人),
			propertyowneridentity(* 产权人身份证号)(参数前加 roomowner.)
			}
		reponse{
			success,
			}
		}
	*/
	var URL_SUBMIT_ROOMOWNER = HTTP+IP+PORT+NAME+"userManage/submitRoomownerInfo.action";	
	
	
	/**
		查询客户信息{
		request{
			roomnumber(* ),
			name,
			phone(* ),
			content(*),
			requiredresult,
			remarks (参数名前加 complaints.)
			images(图片)
			}
		reponse{
			success,
			}
		}
	*/
	var URL_QUERY_ROOMOWNER = HTTP+IP+PORT+NAME+"userManage/list.action";	
	//////////////客户管理/////////////////////////////////////////////
	
	
	/**
		提交装修信息{
		request{
			roomnumber(*)
			ownername
			phone 
			isjianzhuang 
			hasdecprotocol
			hasdecform
			hasdeclicence 
			remarks 
			iscomplete
			(参数名前加 decorate.)
			}
		reponse{
			success,
			}
		}
	*/
	var URL_SUBMIT_DECORATE = HTTP+IP+PORT+NAME+"decorate/submitDecorate.action";	
	//////////////提交装修信息/////////////////////////////////////////////



	/**
		提交装修费用信息{
		request{
			 roomnumber(*)
			 ownername
			 date 
			 deposit(押金) 
			 rabishfee(垃圾费用) 
			 elecfee(电费) 
			 waterfee(水费)
			 passfee(出入证费) 
			 remarks
			 (参数名前加 decoratefee.)
			}
		reponse{
			success,
			}
		}
	*/
	var URL_SUBMIT_DECORATE_FEE = HTTP+IP+PORT+NAME+"decorate/submitDecoratefee.action";	
	//////////////提交装修费用信息/////////////////////////////////////////////



	/**
		查询装修信息{
		request{
			 roomnumber(*) 
			 ownername 
			 phone 
			 isjianzhuang 
			 hasdecprotocol
			 hasdecform 
			 hasdeclicence 
			 remarks
			 iscomplete
			}
		reponse{
			success, 
			result:[ 
				roomnumber,
				ownername,
				phone, 
				isjianzhuang, 
				hasdecprotocol,
				hasdecform, 
				hasdeclicence, 
				remarks, 
				iscomplete]}
			}
		}
	*/
	var URL_QUERY_DECORATE = HTTP+IP+PORT+NAME+"decorate/listDecorate.action";	
	//////////////查询装修信息/////////////////////////////////////////////
	
	/**
		查询装修费用信息{
		request{
			roomnumber
			}
		reponse{
			success, 
			result:[ 
				ownername, 
				date, 
				deposit(押金),
				rabishfee(垃圾费用),
				elecfee(电费),
				waterfee(水费), 
				passfee(出入证费), 
				remarks
			}
		}
	*/
	var URL_QUERY_DECORATE_FEE = HTTP+IP+PORT+NAME+"decorate/listDecoratefee.action";	
	//////////////查询装修费用信息/////////////////////////////////////////////
	
	
		/**
		查询楼栋信息{
		request{
		
			}
		reponse{
			success, 
			result:[ 
				date
			}
		}
	*/
	var URL_QUERY_BUILD= HTTP+IP+PORT+NAME+"building/listBuildingNumber.action";
	//////////////查询楼栋信息/////////////////////////////////////////////
	
	/**
		查询单元信息{
		request{
			unitNo
			}
		reponse{
			success, 
			result:[ 
				date
			}
		}
	*/
	var URL_QUERY_UNIT= HTTP+IP+PORT+NAME+"building/listUnitNumber.action";
	//////////////查询单元信息/////////////////////////////////////////////

	/**
		查询房间信息{
		request{
			builNo,
			unitNo
			}
		reponse{
			success, 
			result:[ 
				date
			}
		}
	*/
	var URL_QUERY_ROOM= HTTP+IP+PORT+NAME+"building/listRoomNumber.action";
	//////////////查询房间信息/////////////////////////////////////////////

	
	/**
	查询水电表历史信息
	
	request{
			buildNO,
			unitNO,
			roomNO
		}
		reponse{
			success, 
			result:[ 
				date
			}
		}

	*/

	var URL_QUERY_METER_INFO= HTTP+IP+PORT+NAME+"meter/listMeterInfo.action";
	/////////////查询水电表历史信息//////////////////////////////////


	/**
	提交水电表信息
	
	request{
		buildNo,
		unitNo,
		roomNo,
		waterconsum,
		elecconsum,
		}
		reponse{
			success
		}

	*/
	var URL_SUBMIT_METER_INFO= HTTP+IP+PORT+NAME+"meter/submitMeterInfo.action";
	/////////////提交水电表信息//////////////////////////////////

	/**
	设置水费梯度

		request{
	
		}
		reponse{
			success
		}


	*/

	var URL_SUBMIT_WATER_PRICE= HTTP+IP+PORT+NAME+"meter/changeWaterPrice.action";
	/////////////设置水费梯度//////////////////////////////////

	

	/**
	查询所有公告信息{
								
	requert{
		keywords,
		pageNo,
		pageSize
		}
		reponse{
			
		}
			}
	*/
	var URL_QUERY_NOTICE= HTTP+IP+PORT+NAME+"notice/listNoticeByPage.action";
	/////////////查询所有公告信息//////////////////////////////////



	/**
	依据关键字查询公告信息{
								
	requert{
		keywords,
		pageNo,
		pageSize
		}
		reponse{
			
		}
			}
	*/
	var URL_QUERY_NOTICE_KEYWORDS= HTTP+IP+PORT+NAME+"notice/listNoticeByKeyWords.action";
	/////////////依据关键字查询公告信息//////////////////////////////////


	/**
	提交公告信息{
		request{
			 notice.title
			 notice.author
			 notice.content
			}
		reponse{
			success,
			}
		}
		
	*/
	var URL_SUBMIT_NOTICE= HTTP+IP+PORT+NAME+"notice/publishNotice.action";
	/////////////提交公告信息//////////////////////////////////


	/**
	查询账户余额{
		request{
			 
			}
		reponse{
			
			}
		}
		
	*/
	var	URL_QUERY_METER_ACCOUNT= HTTP+IP+PORT+NAME+"meter/getAccount.action";//查询账户余额

	/**
	交水电费{
		request{
			 
			}
		reponse{
			
			}
		}
		
	*/
	var URL_SUBMIT_METER_FEE= HTTP+IP+PORT+NAME+"meter/paying.action";//交水电费
	

	/**
	提交水电费公式{
		request{
			 formular(cd>3?cd*2:cd*4)
			}
		reponse{
			success,
			}
		}
		
	*/
	var URL_SUBMIT_FORMULA= HTTP+IP+PORT+NAME+"meter/changeWaterPrice.action";
	/////////////提交水电费公式//////////////////////////////////








