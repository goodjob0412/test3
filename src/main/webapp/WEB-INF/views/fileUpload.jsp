<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>File Uploader</title>
</head>
<body>
    <form action="<c:url value='/upload'/>" method="post" enctype="multipart/form-data">
        <input type="file" name="f_file"  multiple="multiple">
        <input type="submit" value="사진올리기">
    </form>
</body>
</html>
