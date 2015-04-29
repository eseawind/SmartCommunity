package com.smartcommunity.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
 

public class JSONUtil {

	public static final String successString = "success";
	public static final String typeString = "type";
	public static final String resultString = "result";

    private static final String SPACE = "    ";  
    /**
     * 返回 成功的 json 对象
     * @param flag
     * @return
     */
	public static JSONObject getJsonObject(Boolean flag) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(successString, flag.booleanValue());
		return jsonObject;
	}
	public static JSONObject putAccountRemain(JSONObject jsonObject, float remain) {
		if (jsonObject == null) {
			jsonObject = new JSONObject();
		}
		jsonObject.put("accountremain", remain);
		return jsonObject;
	}
	
	public static JSONObject putSuccess(JSONObject jsonObject , Boolean flag) {
		if (flag == null) {
			flag = false;
		}
		jsonObject.put(successString, flag.booleanValue());
		return jsonObject;
	}
	/**
	 * 获取成功与失败的状态，返回true成功，返回 false 失败
	 * @param jsonObject
	 * @return
	 */
	public static boolean getJsonStatus(JSONObject jsonObject) {
		if (jsonObject == null) {
			return false;
		}
		
		Boolean status = (Boolean) jsonObject.get(successString);
		if (status == null) {
			return false;
		}

		return status.booleanValue();
	}
	/**
	 * 将结果放入 json 对象中
	 * @param jsonObject
	 * @param jsonArray
	 * @return
	 */
	public static JSONObject putResult(JSONObject jsonObject,JSONArray jsonArray) {
		if (jsonArray == null || jsonObject == null) {
			return jsonObject;
		}

		jsonObject.put(resultString, jsonArray);

		return jsonObject;
	}
	/**
	 * 将总页数放入 json 对象中
	 * @param jsonObject
	 * @param totalPage
	 * @return
	 */
	public static JSONObject putTotalPage(JSONObject jsonObject,Integer totalPage) {
		if (jsonObject == null) {
			return null;
		}
		jsonObject.put("totalPage", totalPage);
		return jsonObject;
	}
	/**
	 * json 对象中存入异常信息
	 * @param jsonObject
	 * @param e
	 * @return
	 */
	public static JSONObject putCause(JSONObject jsonObject ,Exception e) {
		if (jsonObject == null) {
			jsonObject = JSONUtil.getJsonObject(false);
		}
		if (e.getCause() == null)
			jsonObject.put(typeString, "unknow exception");
		else {
			jsonObject.put(typeString, e.getCause().getMessage());
		}
		return jsonObject;
	}
	/**
	 * 直接将错误信息放入 json 对象中
	 * @param jsonObject
	 * @param errorString
	 * @return
	 */
	public static JSONObject putCause(JSONObject jsonObject , String errorString) {
		if (jsonObject == null) {
				return null;
		}
			jsonObject.put(typeString, errorString);
		return jsonObject;
	}
    /** 
     * 返回指定次数的缩进字符串。每一次缩进三个空格，即SPACE。 
     *  
     * @param number 缩进次数。 
     * @return 指定缩进次数的字符串。 
     */  
    private static String indent(int number)  
    {  
        StringBuffer result = new StringBuffer();  
        for(int i = 0; i < number; i++)  
        {  
            result.append(SPACE);  
        }  
        return result.toString();  
    }  
	public static String readableFormat(JSONObject jsonObject) {
		/** 
		 * 该类提供格式化JSON字符串的方法。 
		 * 该类的方法formatJson将JSON字符串格式化，方便查看JSON数据。 
		 * <p>例如： 
		 * <p>JSON字符串：["yht","xzj","zwy"] 
		 * <p>格式化为： 
		 * <p>[ 
		 * <p>     "yht", 
		 * <p>     "xzj", 
		 * <p>     "zwy" 
		 * <p>] 
		 *  
		 * <p>使用算法如下： 
		 * <p>对输入字符串，追个字符的遍历 
		 * <p>1、获取当前字符。 
		 * <p>2、如果当前字符是前方括号、前花括号做如下处理： 
		 * <p>（1）如果前面还有字符，并且字符为“：”，打印：换行和缩进字符字符串。 
		 * <p>（2）打印：当前字符。 
		 * <p>（3）前方括号、前花括号，的后面必须换行。打印：换行。 
		 * <p>（4）每出现一次前方括号、前花括号；缩进次数增加一次。打印：新行缩进。 
		 * <p>（5）进行下一次循环。 
		 * <p>3、如果当前字符是后方括号、后花括号做如下处理： 
		 * <p>（1）后方括号、后花括号，的前面必须换行。打印：换行。 
		 * <p>（2）每出现一次后方括号、后花括号；缩进次数减少一次。打印：缩进。 
		 * <p>（3）打印：当前字符。 
		 * <p>（4）如果当前字符后面还有字符，并且字符不为“，”，打印：换行。 
		 * <p>（5）继续下一次循环。 
		 * <p>4、如果当前字符是逗号。逗号后面换行，并缩进，不改变缩进次数。 
		 * <p>5、打印：当前字符。 
		 *  
		 * @author  yanghaitao 
		 * @version  [版本号, 2014年9月29日] 
		 */  
		      
		    /** 
		     * 返回格式化JSON字符串。 
		     *  
		     * @param json 未格式化的JSON字符串。 
		     * @return 格式化的JSON字符串。 
		     */  

		    String json = jsonObject.toString();
		        StringBuffer result = new StringBuffer();  
		          
		        int length = json.length();  
		        int number = 0;  
		        char key = 0;  
		          
		        //遍历输入字符串。  
		        for (int i = 0; i < length; i++)  
		        {  
		            //1、获取当前字符。  
		            key = json.charAt(i);  
		              
		            //2、如果当前字符是前方括号、前花括号做如下处理：  
		            if((key == '[') || (key == '{') )  
		            {  
		                //（1）如果前面还有字符，并且字符为“：”，打印：换行和缩进字符字符串。  
		                if((i - 1 > 0) && (json.charAt(i - 1) == ':'))  
		                {  
		                    result.append('\n');  
		                    result.append(indent(number));  
		                }  
		                  
		                //（2）打印：当前字符。  
		                result.append(key);  
		                  
		                //（3）前方括号、前花括号，的后面必须换行。打印：换行。  
		                result.append('\n');  
		                  
		                //（4）每出现一次前方括号、前花括号；缩进次数增加一次。打印：新行缩进。  
		                number++;  
		                result.append(JSONUtil.indent(number));  
		                  
		                //（5）进行下一次循环。  
		                continue;  
		            }  
		              
		            //3、如果当前字符是后方括号、后花括号做如下处理：  
		            if((key == ']') || (key == '}') )  
		            {  
		                //（1）后方括号、后花括号，的前面必须换行。打印：换行。  
		                result.append('\n');  
		                  
		                //（2）每出现一次后方括号、后花括号；缩进次数减少一次。打印：缩进。  
		                number--;  
		                result.append(indent(number));  
		                  
		                //（3）打印：当前字符。  
		                result.append(key);  
		                  
		                //（4）如果当前字符后面还有字符，并且字符不为“，”，打印：换行。  
		                if(((i + 1) < length) && (json.charAt(i + 1) != ','))  
		                {  
		                    result.append('\n');  
		                }  
		                  
		                //（5）继续下一次循环。  
		                continue;  
		            }  
		              
		            //4、如果当前字符是逗号。逗号后面换行，并缩进，不改变缩进次数。  
		            if((key == ','))  
		            {  
		                result.append(key);  
		                result.append('\n');  
		                result.append(indent(number));  
		                continue;  
		            }  
		              
		            //5、打印：当前字符。  
		            result.append(key);  
		        }  
		          
		        return result.toString();  
	}
		      

}
