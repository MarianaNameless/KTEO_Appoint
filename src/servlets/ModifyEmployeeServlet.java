package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ModifyEmployeeServlet")
public class ModifyEmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String employeeid = request.getParameter("employeeid");
		String newemployeeid = request.getParameter("newemployeeid");
		String newfirstname = request.getParameter("newfirstname");
		String newlastname = request.getParameter("newlastname");
		String newrole = request.getParameter("newrole");
		String newpassword = request.getParameter("newpassword");
		String errorMsg = null;
		int i = 0;
		if (employeeid == null || employeeid.equals("")) {
			errorMsg = "Employee ID can't be null or empty";
		}
		if (newrole == null || newrole.equals("") || newrole.equals("secretary") || newrole.equals("technician")) {
			errorMsg = null;
	    }else{
	    	errorMsg = "Role must be secretary or technician.";
	    }
		if (errorMsg != null) {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/modifyEmployee.jsp");
			PrintWriter out = response.getWriter();
			out.println("<font color=red>" + errorMsg + "</font><br>");
			rd.include(request, response);
		} else {
			Connection con = (Connection) getServletContext().getAttribute("DBConnection");
			try {
				if (newemployeeid == null || newemployeeid.equals("")) {
					i = i + 1;
				} else {
					PreparedStatement ps = null;
					ps = con.prepareStatement("update Employees set EmployeeID=? where EmployeeID=?");
					ps.setString(1, newemployeeid);
					ps.setString(2, employeeid);
					ps.execute();
					System.out.println(newemployeeid);
					ps.close();
				}
				if (newfirstname == null || newfirstname.equals("")) {
					i = i + 1;
				} else {
					PreparedStatement ps = null;
					ps = con.prepareStatement("update Employees set FirstName=? where EmployeeID=?");
					ps.setString(1, newfirstname);
					ps.setString(2, employeeid);
					ps.execute();
					System.out.println(newfirstname);
					ps.close();
				}
				if (newlastname == null || newlastname.equals("")) {
					i = 1;
				} else {
					PreparedStatement ps = null;
					ps = con.prepareStatement("update Employees set LastName=? where EmployeeID=?");
					ps.setString(1, newlastname);
					ps.setString(2, employeeid);
					ps.execute();
					System.out.println(newlastname);
					ps.close();
				}
				if (newrole == null || newrole.equals("")) {
					i = 1;
				} else  {
					PreparedStatement ps = null;
					errorMsg = null;
					ps = con.prepareStatement("update Employees set Role=? where EmployeeID=?");
					ps.setString(1, newrole);
					ps.setString(2, employeeid);
					ps.execute();
					System.out.println(newrole);
					ps.close();
				} 
				if (newpassword == null || newpassword.equals("")) {
					i = 1;
				} else {
					PreparedStatement ps = null;
					ps = con.prepareStatement("update Employees set Password=? where EmployeeID=?");
					ps.setString(1, newpassword);
					ps.setString(2, employeeid);
					ps.execute();
					ps.close();
					System.out.println(newpassword);
					ps.close();
				}
				System.out.println("Employee modified with employeeid=" + employeeid);
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/admin.jsp");
				PrintWriter out = response.getWriter();
				out.println("<font color=green>Changes successfully submitted!</font>");
				rd.include(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Database connection problem");
				throw new ServletException("DB Connection problem.");
			}
			if (i == 1)
				System.out.println(":)");
		}
	}

}

