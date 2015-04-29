package com.smartcommunity.util;

public class ConstantPool {

	/** 状态 */
	public static final Integer STATE_COMPLETE = 1;
	public static final Integer STATE_PROCESSING = 0;
	public static final Integer STATE_UNCOMPLETE = -1;
	/** 公告 */
	public static final String NOTICE_ROOM = "room";
	
	/** 报修 */

	public static final String REPAIR_PROCESSEDSTATE = "processedstate";
	
	/** 分页查询 */
	public static final String PAGE_SEARCH_PATTERN = "[\\s]+(left|right|inner)?[\\s]*(outer)?[\\s]*(join).*[\\s]+(where)";
	public static final String PAGE_REPLACE_PATTERN = " where";
	
	/** 抄表 */
	public static final String FIELD_WATER_CONSUME = "waterconsume";
	public static final String FIELD_WATER_UNIT_PRICE = "waterunitprice";
	public static final String FIELD_WATER_TOTAL_PRICE = "watertotalprice";

	public static final String FIELD_ELEC_CONSUME = "elecconsume";
	public static final String FIELD_ELEC_UNIT_PRICE = "elecunitprice";
	public static final String FIELD_ELEC_TOTAL_PRICE = "electotalprice";
	

	public static final Integer TYPE_WATER_METER = 0;
	public static final Integer TYPE_ELEC_METER = 1;
}
