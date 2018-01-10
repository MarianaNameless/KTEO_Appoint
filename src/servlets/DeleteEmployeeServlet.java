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

@WebServlet("/DeleteEmployeeServlet")
public class DeleteEmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 8345605719292191551L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String employeeid = request.getParameter("employeeid");
		String errorMsg = null;
		if (employeeid == null || employeeid.equals("")) {
			errorMsg = "Employee ID can't be null or empty";
		}
		if (errorMsg != null) {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/deleteEmployee.jsp");
			PrintWriter out = response.getWriter();
			out.println("<font color=red>" + errorMsg + "</font><br>");
			rd.include(request, response);
		} else {
			Connection con = (Connection) getServletContext().getAttribute("DBConnection");
			PreparedStatement ps = null;
			
			try {

				ps = con.prepareStatement("delete from Employees where EmployeeID=?");
				ps.setString(1, employeeid);
				ps.execute();

				System.out.println("Employee deleted with Employee ID=" + employeeid);

				RequestDispatcher rd = getServletContext().getRequestDispatcher("/admin.jsp");
				PrintWriter out = response.getWriter();
				out.println("<font color=green>Delete successful, do whatever else you want.</font>");
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