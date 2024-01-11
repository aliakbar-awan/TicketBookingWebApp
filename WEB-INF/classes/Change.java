import java.io.*;
import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Change extends HttpServlet
{
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException,ServletException
	{
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		
		HttpSession session = request.getSession(false);
		if(session==null)
		{
			response.sendRedirect("Login.html");
		}
		else
		{
			String username = (String)session.getAttribute("username");
			String sessID = (String)session.getAttribute("sessID");
			String type = (String)session.getAttribute("type");
			
			if(type.equals("admin") && sessID.equals(session.getId()))
			{
				String journey = request.getParameter("journey");
				String depDate = request.getParameter("depDate");
				String depTime = request.getParameter("depTime");
				
			
					out.print("<html> <head> <title> Change schedule </title> ");
			
					//styling the output table:
					out.print("<style>");
			
					out.print("table {border-collapse: collapse;width: 70%;}");
					out.print("th, td {text-align: center;padding: 8px;}");
					out.print("tr:nth-child(even){background-color: #f2f2f2}");
					out.print("th {background-color: #4CAF50;color: white;}");
					out.print("input[type=submit]{background-color: #0080ff;font-size:120%; color: white; padding: 5px 10px; border: none; cursor: pointer;}");
			
					//Styling of page:
					out.print("font{letter-spacing: -7px;}");
					out.print("ul{list-style-type:none;background-color:#333;margin:0;overflow:hidden;font-size:125%;font-family:sans-serif;}");
					out.print("li{float:left;}");
					out.print("li a{display:block;color:white;text-align:center;padding: 14px 10px;text-decoration:none;padding-right:70px;}");
					out.print(".active{background-color:grey;}");
					out.print("li:hover:not(.active){background-color:#111;}");
			
					out.print("</style>");
					out.print("</head>");
					out.print("<body style='font-family:sans-serif'>");
					out.print("<center>");
					out.print("<p style='text-align:center; font-size:350%;'> <font color='#0080ff'> C </font>"); 
					out.print("<font color='#ff0000'> h </font> <font color='#ffbf00'> a </font>");
					out.print("<font color='#0080ff'> l </font> <font color='green'> o </font> ");
					out.print("<font style='font-size:40%; letter-spacing: 0px;'>.com </font></p>");
					
			
					//List of all other pages for admin, access bar:
					out.print("<ul><li class='active'> <a href='Schedule.html'> Change Schedule </a></li>");
					out.print("<li> <a href='addNewBus.html'> Add new bus </a> </li>");
					out.print("<li> <a href='newAdmin.html'> Add new admin </a> </li>");
					out.print("<li> <a href='updateRates.html'> Update rates </a> </li>");
					out.print("<li> <a href='checkSeats.html'> Check seats </a> </li>");
					out.print("<li> <a href='viewTickets.html'> View tickets </a> </li>");
					out.print("</ul><br> ");
			
			
					out.print("<br>");
					try
					{
						Class.forName("com.mysql.jdbc.Driver");
						String url = "jdbc:mysql://127.0.0.1/test";
					
						Connection con = DriverManager.getConnection(url,"root","root");
						Statement st = con.createStatement();
						
						String query = "update schedule1 set Departure_date='"+depDate+"' Departure_time='"+depTime+"' where JourneyID='"+journey+"'";
						String query1 = "update tickets set Departure_date='"+depDate+"' Departure_time='"+depTime+"' where JourneyID='"+journey+"'";
						if(st.executeUpdate(query)==1 && st.executeUpdate(query1)==1)
						{
							out.println("<p style=' font-size:150%; color:green;font-family:sans-serif;'> Bus timing updated successfully1 </p>");
						}
						else
						{
							out.println("<p style=' font-size:150%; color:red;font-family:sans-serif;'> Something went wrong, Updation failed! </p>");
						}
					}
					catch(Exception e)
					{
						out.print(e);
					}
					
					
					
					
					
					out.print("</form>");
			
				out.print("</center></body> </html>");
		
			}
			else
			{
				response.sendRedirect("	Login.html");
			}			
			
		}
		
		
	}
	
	
}