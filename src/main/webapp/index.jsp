<%--
  Created by IntelliJ IDEA.
  User: zfx
  Date: 2018/6/18
  Time: 13:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
hello world!
<a href="/user/login.do/?username=a&&password=y">aa</a>

springMvc上传文件
<form name="form1" action="/manage/product/upload.do" method="post" enctype="multipart/form-data">
    <input type="file" name="upload_file">
    <input type="submit" name="springMvc上传文件">
</form>


富文本上传
<form name="form1" action="/manage/product/richtext_img_upload.do" method="post" enctype="multipart/form-data">
    <input type="file" name="upload_file">
    <input type="submit" name="富文本上传文件">
</form>
</body>
</html>
