import java.io.*;
import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Ticket extends HttpServlet
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
				try
				{
					Class.forName("com.mysql.jdbc.Driver");
					String url = "jdbc:mysql://127.0.0.1/test";
					
					Connection con = DriverManager.getConnection(url,"root","root");
					Statement st = con.createStatement();
			
					String query = "select * from tickets;";
			
					ResultSet rs = st.executeQuery(query);
			
					out.print("<html> <head> <title> Booked tickets </title> ");
			
					//styling the output table:
					out.print("<style>");
			
					out.print("table {border-collapse: collapse;width: 70%;}");
					out.print("th, td {text-align: center;padding: 8px;}");
					out.print("tr:nth-child(even){background-color: #f2f2f2}");
					out.print("th {background-color: #4CAF50;color: white;}");
			
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
					out.print("<ul><li> <a href='Schedule.html'> Change Schedule </a></li>");
					out.print("<li> <a href='addNewBus.html'> Add new bus </a> </li>");
					out.print("<li> <a href='newAdmin.html'> Add new admin </a> </li>");
					out.print("<li> <a href='updateRates.html'> Update rates </a> </li>");
					out.print("<li> <a href='checkSeats.html'> Check seats </a> </li>");
					out.print("<li class='active'> <a href='viewTickets.html'> View tickets </a> </li>");
					out.print("</ul><br> ");
			
			
					out.print("<br><table >");
					out.print("<tr>");
			
					out.print("<th> Ticket ID </th>");
					out.print("<th> Username </th>");
					out.print("<th> From </th>");
					out.print("<th> To </th>");
					out.print("<th> Departure date </th>");
					out.print("<th> Departure time </th>");
					out.print("<th> Seats </th>");
					out.print("<th> Fare </th>");
					
					out.print("</tr>");
			
					if(rs.first()==false)
					{
				
						out.print("<h3> There are no tickets booked for this root yet. </h3><br>");
					}
					else
					{
						do
						{
							out.print("<tr>");
					
							out.print("<td>"+ rs.getString("Ticket_ID") +"</td>");
							out.print("<td>"+ rs.getString("Username") +"</td>");
							out.print("<td>"+ rs.getString("Departure") +"</td>");
							out.print("<td>"+ rs.getString("Destination") +"</td>");
							out.print("<td>"+ rs.getString("Departure_date") +"</td>");
							out.print("<td>"+ rs.getString("Departure_time") +"</td>");
							out.print("<td>"+ rs.getString("Seat_num") +"</td>");
							out.print("<td>"+ rs.getString("Total_Fare") +"</td>");		
							out.print("</tr>");
						}while(rs.next());
						out.print("</table>");
					}
			
			
			out.print("</center></body> </html>");
		}
		catch(Exception e)
		{
			out.print(e);
		}
			}
			else
			{
				response.sendRedirect("	Login.html");
			}			
			
		}
		
		
	}
	
	
}