<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
Provide EmployeeID of the employee that you want to modify: <br>
<form action="ModifyEmployeeServlet" method="post">
                Employee ID: <input type="text" name="employeeid"> <br><br>
              What do you want to change?<br>
              Employee ID:<input type="text" name="newemployeeid"> <br>
              First Name:<input type="text" name="newfirstname"> <br>
              Last Name:<input type="text" name="newlastname"> <br>
              Role:<input type="text" name="newrole"> <br>
              Password:<input type="text" name="newpassword"> <br>
            
                <input type="submit" value="Submit changes">
        </form>
</body>
</html>