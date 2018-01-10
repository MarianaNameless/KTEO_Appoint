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

/**
 * Servlet implementation class AddServiceServlet
 */
@WebServlet("/AddServiceServlet")
public class AddServiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String serviceid = request.getParameter("serviceid");
		String servicename = request.getParameter("servicename");
		String role = request.getParameter("role");
		String errorMsg = null;
		if (role.equals("secretary") || role.equals("technician")) {
			errorMsg = null;
		} else {
			errorMsg = "Role must be secretary or technician.";
		}
		if (serviceid == null || serviceid.equals("")) {
			errorMsg = "Service ID can't be null or empty.";
		}
		if (servicename == null || servicename.equals("")) {
			errorMsg = "Service name can't be null or empty.";
		}
		if (role == null || role.equals("")) {
			errorMsg = "Role can't be null or empty.";
		}
		if (errorMsg != null) {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/addService.jsp");
			PrintWriter out = response.getWriter();
			out.println("<font color=red>" + errorMsg + "</font>");
			rd.include(request, response);
		} else {
			Connection con = (Connection) getServletContext().getAttribute("DBConnection");
			PreparedStatement ps = null;
			try {
				ps = con.prepareStatement(
						"insert into Services(ServiceID, ServiceName, Role) values (?,?,?)");
				ps.setString(1, serviceid);
				ps.setString(2, servicename);
				ps.setString(3, role);
				ps.execute();
				System.out.println("Service registered with Service ID =" + serviceid);
				// forward to login page to login
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/admin.jsp");
				PrintWriter out = response.getWriter();
				out.println("<font color=green>Add successful, do whatever you want.</font>");
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