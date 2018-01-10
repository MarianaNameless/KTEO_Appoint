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

@WebServlet("/SetNewDateServlet")
public class SetNewDateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String licensenumber = request.getParameter("licensenumber");
		String newcheck = request.getParameter("newcheck");
		String errorMsg = null;
		if (licensenumber == null || licensenumber.equals("")) {
			errorMsg = "Licence Number can't be null or empty";
		}
		if (newcheck == null || newcheck.equals("")) {
			errorMsg = "New check date can't be null or empty";
		}
		if (errorMsg != null) {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/technician.jsp");
			PrintWriter out = response.getWriter();
			out.println("<font color=red>" + errorMsg + "</font><br>");
			rd.include(request, response);
		} else {
			Connection con = (Connection) getServletContext().getAttribute("DBConnection");
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = con.prepareStatement("update Vehicles set NextCheck=? where LicenseNumber=?");
				ps.setString(1, newcheck);
				ps.setString(2, licensenumber);
				rs = ps.executeQuery();
				if (rs != null && rs.next()){
					PrintWriter out = response.getWriter();
					out.println("<font color=green> New date has been set</font>");
				}else{ 
					PrintWriter out = response.getWriter();
					out.println("<font color=red> No such vehicle</font>");
				}
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/technician.jsp");
				System.out.println("See you again soon");
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
