    function initDatepicker(datepicker)
	{
		$(datepicker).datepicker({
            changeMonth: true, //可以选择月份
            changeYear: true, //可以选择年份
            showButtonPanel: true, //显示按钮面板
            prevText: '<上月',  
            nextText: '下月>',
            currentText: '今天', //当前日期按钮上显示的文字
            closeText: '关闭', //关闭按钮上显示的文本
            yearRange: 'c-60:c+20',
 		     /* 区域化周名为中文 */
            dayNamesMin : ["日", "一", "二", "三", "四", "五", "六"], 
            /* 每周从周一开始 */
            //firstDay : 1,
            /* 区域化月名为中文习惯 */
            monthNamesShort : ["1月", "2月", "3月", "4月", "5月", "6月",
            "7月", "8月", "9月", "10月", "11月", "12月"],
             /* 月份显示在年后面 */
             showMonthAfterYear : true,
            /* 格式化中文日期 */
            //dateFormat : "yy年MMdd日" 
            dateFormat: 'yy-mm-dd'

        });
	}