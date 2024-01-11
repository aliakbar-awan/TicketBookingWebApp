import java.io.*;
import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ShowUserTickets extends HttpServlet
{
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException,ServletException,NumberFormatException
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
			if(type.equals("customer") && sessID.equals(session.getId()))
			{
				try
				{
					Class.forName("com.mysql.jdbc.Driver");
					String url = "jdbc:mysql://127.0.0.1/test";
					
					Connection con = DriverManager.getConnection(url,"root","root");
					Statement st = con.createStatement();
			
					String query = "select *,CAST(CURTIME() AS CHAR) AS curtime1 from tickets where Username='"+ username +"' ";
			
					ResultSet rs = st.executeQuery(query); //For tickets
			
					out.print("<html> <head> <title> Canceling tickets </title> ");
			
					//styling the output table:
					out.print("<style>");
			
					out.print("table {border-collapse: collapse;width: 80%;}");
					out.print("th, td {text-align: center;padding: 8px;}");
					out.print("tr:nth-child(even){background-color: #f2f2f2}");
					out.print("th {background-color: #4CAF50;color: white;}");
					out.print("input[type=submit]{background-color: #0080ff; color: white; padding: 5px 10px; border: none; cursor: pointer;}");
			
					//Styling of page:
					out.print("font{letter-spacing: -7px;}");
					out.print("ul{list-style-type:none;background-color:#333;margin:0;overflow:hidden;font-size:125%;font-family:sans-serif;}");
					out.print("li{float:left;}");
					out.print("li a{display:block; color:white; text-align:center; padding: 14px 10px;text-decoration:none; padding-left:280px; padding-right:260px;}");
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
					
					
					//List of all other pages for user, access bar:
					out.print("<ul><li> <a href='Book.html'> Book ticket </a></li>");
					out.print("<li class='active'> <a href='Cancel.html'> Cancel ticket </a> </li>");
					out.print("</ul><br> ");
			
			
					out.print("<br><table >");
					out.print("<tr>");
			
					out.print("<th> Ticket ID </th>");
					out.print("<th> JourneyID </th>");
					out.print("<th> Username </th>");
					out.print("<th> From </th>");
					out.print("<th> To </th>");
					out.print("<th> Departure date </th>");
					out.print("<th> Departure time </th>");
					out.print("<th> Seats </th>");
					out.print("<th> Fare </th>");
					out.print("<th> Click here! </th>");
			
					out.print("</tr>");
			
					if(rs.first()==false)
					{
				
						out.print("<h3> You don't have any bookings. </h3><br>");
					}
					else
					{
						do
						{
							out.print("<tr>");
					
							out.print("<td>"+ rs.getString("Ticket_ID") +"</td>");
							out.print("<td>"+ rs.getString("JourneyID") +"</td>");
							out.print("<td>"+ rs.getString("Username") +"</td>");
							out.print("<td>"+ rs.getString("Departure") +"</td>");
							out.print("<td>"+ rs.getString("Destination") +"</td>");
							out.print("<td>"+ rs.getString("Departure_date") +"</td>");
							out.print("<td>"+ rs.getString("Departure_time") +"</td>");
							out.print("<td>"+ rs.getString("seat_num") +"</td>");
							out.print("<td>"+ rs.getString("Total_Fare") +"</td>");
							
							//SimpleDateFormat sd = new SimpleDateFormat();
							java.util.Date departureDate = rs.getDate("Departure_date");
							java.util.Date curDate = java.sql.Date.valueOf(java.time.LocalDate.now());
							
							if(departureDate.after(curDate))
							{
								//Form to cancel that ticket
								out.print("<form name= cancel action='Cancel' method= post>");
								int journey = rs.getInt("JourneyID");
								String seats = rs.getString("Seat_num");
								out.print("<input type=hidden name='journey' value='"+journey+"'>");
								out.print("<input type=hidden name='seats' value='"+seats+"'>");
								out.print("<td><input type=submit value='Cancel'></td>");
							
								out.print("</form>");
								out.print("</tr>");
							}
							else if(departureDate.equals(curDate))
							{
								//Then check times:
								String depTime = rs.getString("Departure_time");
								String curTime = rs.getString("curtime1");
								
								String []dep = depTime.split(":",3); //String containing hours, minutes and seconds on separate indexes
								String []cur = curTime.split(":",3);
								
								
								
								int []depTimeInt = new int [3];
								int []curTimeInt = new int [3];
								
								
								for(int i=0;i<3;i++)
								{
									depTimeInt[i] = Integer.parseInt(dep[i]);
									curTimeInt[i] = Integer.parseInt(cur[i]);
								}
								
								if(depTimeInt[0]>curTimeInt[0])
								{
									out.print("<form name= cancel action='Cancel' method= post>");
									
									int journey = rs.getInt("JourneyID");
									String seats = rs.getString("Seat_num");
									out.print("<input type=hidden name='journey' value='"+journey+"'>");
									out.print("<input type=hidden name='seats' value='"+seats+"'>");
									out.print("<td><input type=submit value='Cancel'></td>");
							
									out.print("</form>");
									out.print("</tr>");
								}
								else if(depTimeInt[0]==curTimeInt[0])
								{
									if(depTimeInt[1]>curTimeInt[1])
									{
										out.print("<form name= cancel action='Cancel' method= post>");
									
										int journey = rs.getInt("JourneyID");
										String seats = rs.getString("Seat_num");
										out.print("<input type=hidden name='journey' value='"+journey+"'>");
										out.print("<input type=hidden name='seats' value='"+seats+"'>");
										out.print("<td><input type=submit value='Cancel'></td>");
							
										out.print("</form>");
										out.print("</tr>");
									}
									else
									{
										out.print("<td>*****</td>");
									}
								}
								else
								{
									out.print("<td>*****</td>");
								}
							}
							else
							{
								out.print("<td>*****</td>");
							}
								
							
							
							
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