<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add new service</title>
</head>
<body>
<form action="AddServiceServlet" method="post">
                Service ID: <input type="text" name="serviceid"><br>
                Service Name:  <input type="text" name="servicename"><br>
                Role:<input type="text" name="role"><br> 
                <input type="submit" value="Add">
        </form>
</body>
</html>