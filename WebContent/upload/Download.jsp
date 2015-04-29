<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>下载</title>
</head>
<body>
<img alt="显示图片" src="<s:url action='downloadImage.action'></s:url>"></img>
<a href='<s:url value="downloadImage.action"/>'>download</a>
</body>
</html>