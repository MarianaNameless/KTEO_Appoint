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
 * Servlet implementation class DeleteServiceServlet
 */
@WebServlet("/DeleteServiceServlet")
public class DeleteServiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String servicename = request.getParameter("servicename");
		String errorMsg = null;
		if (servicename == null || servicename.equals("")) {
			errorMsg = "Service Name can't be null or empty";
		}
		if (errorMsg != null) {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/deleteService.jsp");
			PrintWriter out = response.getWriter();
			out.println("<font color=red>" + errorMsg + "</font><br>");
			rd.include(request, response);
		} else {
			Connection con = (Connection) getServletContext().getAttribute("DBConnection");
			PreparedStatement ps = null;

			try {
				

				ps = con.prepareStatement("delete from Services where ServiceName=?");
				ps.setString(1, servicename);
				ps.execute();
				System.out.println("Service deleted with Service Name = " + servicename);

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
