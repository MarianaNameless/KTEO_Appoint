package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import beans.Employee;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 8345605719292191551L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String employeeid = request.getParameter("employeeid");
		String password = request.getParameter("pwd");
		String errorMsg = null;
		if (employeeid == null || employeeid.equals("")) {
			errorMsg = "Employee ID can't be null or empty";
		}
		if (password == null || password.equals("")) {
			errorMsg = "Password can't be null or empty";
		}
		if (errorMsg != null) {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
			PrintWriter out = response.getWriter();
			out.println("<font color=red>" + errorMsg + "</font>");
			rd.include(request, response);
		} else {
			Connection con = (Connection) getServletContext().getAttribute("DBConnection");
			PreparedStatement ps = null;
			ResultSet rs = null;

			try {

				ps = con.prepareStatement(
						"select EmployeeID, FirstName, LastName, Role, Password from Employees where EmployeeID=? and Password=?");

				ps.setString(1, employeeid);
				ps.setString(2, password);

				rs = ps.executeQuery();

				if (rs != null && rs.next()) {
					Employee employee = new Employee(rs.getString("EmployeeID"), rs.getString("FirstName"),
							rs.getString("LastName"), rs.getString("Role"), rs.getString("Password"));

					HttpSession session = request.getSession();

					session.setAttribute("Employee", employee);
					String role = employee.getRole();
					if (role.contains("secretary")) {
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/secretary.jsp");
						rd.include(request, response);
						return;
					} else if (role.contains("technician")) {
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/technician.jsp");
						rd.forward(request, response);
						return;
					} else if (role.contains("admin")) {
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/admin.jsp");
						rd.forward(request, response);
						return;
					}
				} else {
					RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
					PrintWriter out = response.getWriter();
					System.out.println("User not found with Employee ID=" + employeeid);
					out.println("<font color=red>No user found with given employee id, please register first.</font>");
					rd.include(request, response);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Database connection problem");
				throw new ServletException("DB Connection problem.");
			} finally {
				try {

					rs.close();
					ps.close();
				} catch (SQLException e) {
					System.out.println("SQLException in closing PreparedStatement or ResultSet");
				}
			}
		}

	}
}
