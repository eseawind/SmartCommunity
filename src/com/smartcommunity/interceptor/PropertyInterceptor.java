package com.smartcommunity.interceptor;

import java.util.Set;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.ValueStack;
import com.smartcommunity.util.UTIL;

public class PropertyInterceptor extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		javax.servlet.http.HttpSession session = org.apache.struts2.ServletActionContext
				.getRequest().getSession(false);
		if (session != null) {
			java.util.Set<Integer> roleSet = (Set<Integer>) session
					.getAttribute(com.smartcommunity.util.UTIL.sessionRoles);
			if (roleSet != null && roleSet.contains(UTIL.roleuser)) {

				String roomNumber = (String) session
						.getAttribute(UTIL.sessionRoomNumber);
				ActionContext ac = invocation.getInvocationContext();
				ValueStack stack = ac.getValueStack();
				String[] splitRoomNumber = roomNumber.split(UTIL.ROOM_SPLIT);

				stack.setValue(UTIL.ROOM_BUILDNO, splitRoomNumber[0]);
				stack.setValue(UTIL.ROOM_UNITNO, splitRoomNumber[1]);
				stack.setValue(UTIL.ROOM_ROOMNO, splitRoomNumber[2]);
			}

		}


		return invocation.invoke();
		// ActionConfig config = invocation.getProxy().getConfig();
		// Map<String, String> parameters = config.getParams();
		// if (parameters.containsKey(pathKey))
		// {
		// String path = parameters.get(pathKey);
		// String encoding = parameters.get(encodingKey);
		// String separator = parameters.get(separatorKey);
		// if (encoding == null)
		// encoding = "UTF-8";
		// if (separator == null)
		// separator = "";
		// path = invocation.getAction().getClass().getResource(path)
		// .getPath();
		// Properties properties = new Properties();
		// InputStream is = new FileInputStream(path);
		// java.io.Reader reader = new java.io.InputStreamReader(is, encoding);
		//
		// properties.load(reader);
		// System.out.println(stack.hashCode());
		// Enumeration names = properties.propertyNames();
		// while (names.hasMoreElements())
		// {
		// // 下面会使用setValue方法修改ValueStack对象中的相应属性值
		// String name = names.nextElement().toString();
		// if (!name.contains("."))
		// stack.setValue(name, properties.get(name));
		// String newName = null;
		// newName = parameters.get(name.replaceAll("\\.", ""));
		// if (newName != null)
		// stack.setValue(newName, properties.get(name));
		// if (!separator.equals(""))
		// {
		// newName = name.replaceAll("\\.", "");
		// stack.setValue(newName, properties.get(name));
		// }
		// newName = name.replaceAll("\\.", separator);
		// }
		// }
	}

}
