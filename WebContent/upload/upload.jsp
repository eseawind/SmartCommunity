<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <title>文件上传</title>

        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <meta http-equiv="expires" content="0">
    </head>

    <body>
        <!-- ${pageContext.request.contextPath}/upload/execute_upload.do -->
        <!-- ${pageContext.request.contextPath}/upload2/upload2.do -->
        <form action="${pageContext.request.contextPath}/updownload/uploadImage.action" 
              enctype="multipart/form-data" method="post">
            文件:<input type="file" name="image">
                <input type="submit" value="上传" />
        </form>
        <br/>
        <s:fielderror />
    </body>
</html>