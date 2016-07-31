<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
	"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>MyPower</title>

</head>
<body>
<p align="right">
<img alt="" src="images/admin_graphic_01.png">
</p>
<p align="right"><a href="LogoutServlet">Logout</a></p>

	<p align="left">
		<form method="post" action="UploadServlet" enctype="multipart/form-data">
		<h3>Please upload a file in PNG/JPEG format, having a resolution of 1242 x 1539 pixels and not more than 700 Kilobytes in size.</h3>
			<h3>Select file to upload: <input type="file" accept="image/*" name="uploadFile" /></h3>
			<br/>
			<input type="submit" value="Upload" />
		</form>
		<p align="left">
			<h3>${requestScope.message}</h3>
		</p>
	</p>
</body>

</html>