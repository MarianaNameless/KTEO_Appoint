package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CheckCarServlet
 */
@WebServlet("/CheckCarServlet")
public class CheckCarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String licensenumber = request.getParameter("licensenumber");
		String errorMsg = null;
		if (licensenumber == null || licensenumber.equals("")) {
			errorMsg = "Licence Number can't be null or empty";
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
			String owner = new String();
			String model = new String();
			String type = new String();
			Date insurance = new Date();
			Date lastcheck = new Date();
			try {
				
			ps = con.prepareStatement("select Owner, Model, Type, InsuranceExpires, LastCheck from Vehicles where LicenseNumber=?");
			ps.setString(1, licensenumber);
			rs = ps.executeQuery();
			if (rs != null && rs.next()){
				owner = (rs.getString("Owner"));
				model = (rs.getString("Model"));
				type = (rs.getString("Type"));
				insurance = (rs.getDate("InsuranceExpires"));
				lastcheck = (rs.getDate("LastCheck"));
				PrintWriter out = response.getWriter();
				out.println("Owner: " + owner + "<br>Model: " + model 
						+ "<br>Type: " + type + "<br>Insurance valid until: " + insurance + "<br>Last check: " + lastcheck);
			}else{
				PrintWriter out = response.getWriter();
				out.println("<font color=red> This license number is not valid, try again!</font><br>");
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

