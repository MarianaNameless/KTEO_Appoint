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
 * Servlet implementation class ServeCustomerServlet
 */
@WebServlet("/ServeCustomerServlet")
public class ServeCustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String licensenumber = request.getParameter("licensenumber");
		String errorMsg = null;
		if (licensenumber == null || licensenumber.equals("")) {
			errorMsg = "Licence Number can't be null or empty";
		}
		if (errorMsg != null) {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/secretary.jsp");
			PrintWriter out = response.getWriter();
			out.println("<font color=red>" + errorMsg + "</font><br>");
			rd.include(request, response);
		} else {
			Connection con = (Connection) getServletContext().getAttribute("DBConnection");
			PreparedStatement ps = null;
			ResultSet rs = null;
			Date date = new Date();
   			Date insurance  = new Date();
   			Date NextCheck  = new Date();
   			int fine = 0;
			
			try {
				

				ps = con.prepareStatement("select Fine, InsuranceExpires, NextCheck from Vehicles where LicenseNumber=?");
				ps.setString(1, licensenumber);
				rs = ps.executeQuery();
				if (rs != null && rs.next()){
					fine = (rs.getInt("Fine"));
					insurance = (rs.getDate("InsuranceExpires"));
					NextCheck = (rs.getDate("NextCheck"));
					if (insurance.before(date)){
						PrintWriter out = response.getWriter();
						out.println("<font color=red> Insurance has expired, customer has to pay 200 euros</font><br>"); 
					}else {
						PrintWriter out = response.getWriter();
						out.println("<font color=green> Insurance is ok, go on</font><br>");
					}if (NextCheck.before(date)){
						PrintWriter out = response.getWriter();
						out.println("<font color=red> Customer is late and has to pay " +fine+"euros</font><br>"); 
					}else {
						PrintWriter out = response.getWriter();
						out.println("<font color=green> Customer may go to technian</font>");
					}
				}else {
					PrintWriter out = response.getWriter();
					out.println("<font color=red> This license number is not valid, try again!</font><br>");
				}

				RequestDispatcher rd = getServletContext().getRequestDispatcher("/secretary.jsp");
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
