import java.io.*;
import java.util.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Book extends HttpServlet
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
			if(type.equals("customer") && sessID.equals(session.getId()))
			{
				String journey = request.getParameter("journey");
				
				try
				{
					Class.forName("com.mysql.jdbc.Driver");
					String url = "jdbc:mysql://127.0.0.1/test";
			
					Connection con = DriverManager.getConnection(url,"root","root");
					Statement st = con.createStatement();
			
					String query = "select * from schedule1 where JourneyID = '"+ journey +"';";
					ResultSet rs = st.executeQuery(query);
					rs.next();
					boolean []seat = new boolean[40];
					
					for(int i=1;i<=40;i++)
					{
						String var = "Seat"+ i +"";
						seat[i-1] = rs.getBoolean(var);
					}
					
					out.print("<html> <head> <title> Booking tickets </title> ");
			
					//styling the output table:
					out.print("<style>");
			
					out.print("table {border-collapse: collapse;width: 70%;}");
					out.print("th, td {text-align: center;padding: 8px;}");
					out.print("tr:nth-child(even){background-color: #f2f2f2}");
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
			
			
					
			
					if(rs.first()==false)
					{
				
						out.print("<h3> Sorry! There is some Problem. </h3><br>");
					}
					else
					{
						
						for(int j=1;j<=40;j++)
						{
							if(seat[j-1]==false)
							{
								out.println("<b><font style='color:#33cc33;letter-spacing:0px; size=8;'>"+j+"</font></b>");
							}
							else
							{
								out.println("<b><font style='color:#ff0000;letter-spacing:1px; size=8;'>"+j+"</font></b>");
							}
							
							if(j%4==0)
							{
								out.print("<br><br>");
							}
							else if(j%2==0)
							{
								out.print("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
							}
							else if(j<=8)
							{
								out.print("&nbsp;&nbsp;&nbsp;");
							}
							
						}
						out.println("<form name='selectTicket' method='post' action='BookTicket'>");
						out.println("Enter comma-separated seats numbers, if more then one (only GREEN!) <br><br>");
						out.print("<input style='font-size:100%;' type='text' size=15 name='ticketNum' placeholder='Enter seat numbers'>");
						out.print("<input type= 'hidden' name='journey' value='"+journey+"'>");
						out.println("<input  type='submit' value='Book seats'> ");
						out.println("</form>");
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