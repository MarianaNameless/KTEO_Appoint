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

@WebServlet("/AddEmployeeServlet")
public class AddEmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddEmployeeServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String employeeid = request.getParameter("employeeid");
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String role = request.getParameter("role");
		String password = request.getParameter("password");
		String errorMsg = null;

		if (role.equals("secretary") || role.equals("technician")) {
			errorMsg = null;
		} else {
			errorMsg = "Role must be secretary or technician.";
		}
		if (password == null || password.equals("")) {
			errorMsg = "Password can't be null or empty.";
		}
		if (employeeid == null || employeeid.equals("")) {
			errorMsg = "Employee ID can't be null or empty.";
		}

		if (firstname == null || firstname.equals("")) {
			errorMsg = "First name can't be null or empty.";
		}
		if (lastname == null || lastname.equals("")) {
			errorMsg = "Last name can't be null or empty.";
		}
		if (role == null || role.equals("")) {
			errorMsg = "Role can't be null or empty.";
		}
		if (errorMsg != null) {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/addEmployee.jsp");
			PrintWriter out = response.getWriter();
			out.println("<font color=red>" + errorMsg + "</font>");
			rd.include(request, response);
		} else {
			Connection con = (Connection) getServletContext().getAttribute("DBConnection");
			PreparedStatement ps = null;
			try {
				ps = con.prepareStatement(
						"insert into Employees(EmployeeID, FirstName, LastName, Role, Password) values (?,?,?,?,?)");
				ps.setString(1, employeeid);
				ps.setString(2, firstname);
				ps.setString(3, lastname);
				ps.setString(4, role);
				ps.setString(5, password);
				ps.execute();
				System.out.println("Employee registered with employeeid=" + employeeid);
				// forward to login page to login
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
				PrintWriter out = response.getWriter();
				out.println("<font color=green>Add successful, new employee may login below.</font>");
				rd.include(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Database connection problem");
				throw new ServletException("DB Connection problem.");
			} finally {
				try {
					ps.close();
				} catch (SQLException e) {
					System.out.println("SQLException in closing PreparedStatement");
				}
			}
		}
	}

}
