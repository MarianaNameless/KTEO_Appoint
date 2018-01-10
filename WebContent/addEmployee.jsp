<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add new employee</title>
</head>
<body>
 <form action="AddEmployeeServlet" method="post">
                Employee ID: <input type="text" name="employeeid"><br>
                First Name:  <input type="text" name="firstname"><br>
               Last Name <input type="text" name="lastname"><br> 
               Role: <input type="text" name="role"><br>
               Password: <input type="password" name="password"> <br> 
                <input type="submit" value="Add">
        </form>
</body>
</html>