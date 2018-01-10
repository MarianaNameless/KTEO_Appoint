<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Delete a service</title>
</head>
<body>
Provide Service Name of the Service that you want to delete: <br>
<form action="DeleteServiceServlet" method="post">
                Service Name: <input type="text" name="servicename"> <br> 
                <input type="submit" value="Delete">
        </form>
</body>
</html>