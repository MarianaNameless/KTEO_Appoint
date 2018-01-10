<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Administrator</title>
</head>
<body>
<%@ include file="hi.jsp" %>
		What do you want to do?<br><br>
		Employees:
		 <form action="addEmployee.jsp" method="post">
                <input type="submit" value="Add">
        </form><form action="modifyEmployee.jsp" method="post">
                <input type="submit" value="Modify">
        </form><form action="deleteEmployee.jsp" method="post">
                <input type="submit" value="Delete">
        </form>
        <br>
        Services:
        <form action="addService.jsp" method="post">
                <input type="submit" value="Add">
        </form><form action="deleteService.jsp" method="post">
                <input type="submit" value="Delete">
        </form><br>
        
<% %>
<%@ include file="logout.jsp" %>



</body>
</html>