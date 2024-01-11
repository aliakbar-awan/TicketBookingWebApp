import java.io.*;
import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class BookTicket extends HttpServlet
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
				String journey = request.getParameter("journey");
				String arr = request.getParameter("ticketNum");
				
				String []seatsInString = arr.split(",",40);
				int totalSeats = seatsInString.length;
				
				
				try
				{
					Class.forName("com.mysql.jdbc.Driver");
					String url = "jdbc:mysql://127.0.0.1/test";
			
					Connection con = DriverManager.getConnection(url,"root","root");
					Statement st = con.createStatement();
			
					String query = "select * from schedule1 where JourneyID = '"+ journey +"';";
					ResultSet rs = st.executeQuery(query);
					rs.next();
					
					
					out.print("<html> <head> <title> Booking tickets </title> ");
			
					//styling the output table:
					out.print("<style>");
			
					out.print("table {border-collapse: collapse;width: 30%;border:2px green solid;}");
					out.print("th, td {text-align: center;padding: 8px;}");
					out.print("tr:nth-child(odd){background-color: #f2f2f2}");
					out.print("th {background-color: #4CAF50;color: white;}");
					out.print("input[type=submit]{background-color: #0080ff; color: white; padding: 5px 10px; border: none; cursor: pointer;font-size:100%;}");
			
					//Styling of page:
					out.print("font{letter-spacing: -7px;}");
					out.print("ul{list-style-type:none; background-color:#333; margin:0; overflow:hidden; font-size:125%; font-family:sans-serif;}");
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
					out.print("<ul><li class='active'> <a href='Book.html'> Book ticket </a></li>");
					out.print("<li> <a href='Cancel.html'> Cancel ticket </a> </li>");
					out.print("</ul><br> ");
					
					String dest = rs.getString("Destination");
					String dep = rs.getString("Departure");
					
					int []seat = new int[totalSeats];
					boolean properFormat = true;
					boolean alreadyBooked = false;
					
					for(int i=0;i<totalSeats;i++)
					{
						seat[i] = Integer.parseInt(seatsInString[i]);						
						String seatNumber = "Seat"+seat[i]+"";
						if(rs.getBoolean(seatNumber)==true)
						{
							alreadyBooked = true;  //Some of the entered seat number is alredy booked.
							break;
						}
					}
					
					if(alreadyBooked==true)  
					{
						out.println("<h3 style='color:red;'> Booking failed, Some of the seat numbers you entered are already booked! </h3>");
					}
					else   // Input seat numbers were true, so insert data
					{
						
						int fare = rs.getInt("Fare")*totalSeats;   //Calculating fare
						
						int count =0;
						String query3 = "insert into tickets(JourneyID,Username,Departure,Destination,Departure_date,Departure_time,Total_seats,Seat_num,Booking_date,Booking_time,Bus_number,Total_Fare)values('"+journey+"','"+username+"','"+rs.getString("Departure")+"','"+rs.getString("Destination")+"','"+rs.getString("Departure_date")+"','"+rs.getString("Departure_time")+"','"+totalSeats+"','"+arr+"',CAST(CURDATE() AS CHAR),CAST(CURTIME() AS CHAR),'"+rs.getString("Bus_number")+"','"+fare+"')";
						int s = st.executeUpdate(query3);
						count = count + s;
						
						
						for(int k=0;k<totalSeats;k++)
						{
							String query2 = "update schedule1 set Seat"+seatsInString[k]+"='1' where JourneyID ='"+ journey +"'";
							if(st.executeUpdate(query2)==1)
							{
								count = count + 1;
							}
							
						}
						
						if(count==totalSeats+1)
						{
							
							out.println("<h3 style='color:#4CAF50;'> Your seats are booked successfully </h3><br><br>");
							out.print("<table>");
							out.println("<tr><td><b>Username:</b>   "+username+"</td></tr>");
							out.println("<tr><td><b>Journey:</b>   "+dep+" - "+dest+"</td></tr>");
							out.println("<tr><td><b>Total seats:</b> "+totalSeats+"</td></tr>");
							out.println("<tr><td><b>Seat numbers:</b>   "+arr+"</td></tr>");
							out.println("<tr><td><b>Total Fare:</b>   "+fare+"</td></tr>");
							out.print("</table>");
						}
						else
						{
							out.println("<h3 style='color:red'> Something went wrong! </h3>");
						}
						
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
				response.sendRedirect("Login.html");
			}
		}
		
		
	}
	
	
}