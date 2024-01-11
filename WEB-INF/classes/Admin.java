import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class Admin extends HttpServlet
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
			String usertype = (String) session.getAttribute("type");
			if(usertype.equals("admin"))
			{
				String uname = request.getParameter("usrname");
				String fname = request.getParameter("fname");
				String lname = request.getParameter("lname");
				String email = request.getParameter("email");
				String pwd = request.getParameter("usrpwd");
			
				try
				{
					Class.forName("com.mysql.jdbc.Driver");
					String url = "jdbc:mysql://127.0.0.1/test";
					
					Connection con = DriverManager.getConnection(url,"root","root");
					Statement st = con.createStatement();
					
					out.print("<html> <head> <title> Create new Admin </title> ");
					
					//Styling of page:
					out.print("<style>");
					out.print("font{letter-spacing: -7px;}");
					out.print("ul{list-style-type:none;background-color:#333;margin:0;overflow:hidden;font-size:125%;font-family:sans-serif;}");
					out.print("li{float:left;}");
					out.print("li a{display:block;color:white;padding-right:70px;text-align:center;padding: 14px 10px;text-decoration:none;}");
					out.print(".active{background-color:grey;}");
					out.print("li:hover:not(.active){background-color:#111;}");
				
					out.print("</style>");
					out.print("</head>"); // end of head
					out.print("<body style='font-family:sans-serif'>");
			
					out.print("<center>");
				
					out.print("<p style='text-align:center; font-size:350%;'> <font color='#0080ff'> C </font>"); 
					out.print("<font color='#ff0000'> h </font> <font color='#ffbf00'> a </font>");
					out.print("<font color='#0080ff'> l </font> <font color='green'> o </font> ");
					out.print("<font style='font-size:40%; letter-spacing: 0px;'>.com </font></p>");
					
			
					//List of all other pages for admin, access bar:
					out.print("<ul><li> <a href='Schedule.html'> Change Schedule </a></li>");
					out.print("<li> <a href='addNewBus.html'> Add new bus </a> </li>");
					out.print("<li class='active'> <a href='newAdmin.html'> Add new admin </a> </li>");
					out.print("<li> <a href='updateRates.html'> Update rates </a> </li>");
					out.print("<li> <a href='checkSeats.html'> Check seats </a> </li>");
					out.print("<li> <a href='viewTickets.html'> View tickets </a> </li>");
					out.print("</ul><br><br> ");
			
					String query1 = "select Username from users";
					
					ResultSet rs = st.executeQuery(query1);
			
					boolean userNameFound = false;
					while(rs.next())
					{
						if(rs.getString("Username").equals(uname))
						{
							userNameFound = true;
							break;
						}
					}
				
					if(!userNameFound)
					{
						String query = "insert into users(Username,First_name,Last_name,Email,Pasword,UserType) values('"+uname+"','"+fname+"','"+lname+"','"+email+"','"+pwd+"','admin');";
						int r = st.executeUpdate(query);
						
						out.println("<p style='color:#4CAF50; font-size:130%;'> New Admin has been successfully created! </p>");
					}
					else
					{
						out.print("<p style='color:red; font-size:130%;'> Username you entered for new admin is already taken by someone! </p>");
						out.println("<p style='color:#4CAF50; font-size:130%;'> Please, try another one! </p>");
					}
			
			
			
			
					out.print("</center></body> </html>");   //end of body
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