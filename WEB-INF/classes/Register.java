import java.io.*;
import java.util.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.sql.*;

public class Register extends HttpServlet
{
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		
		String uname = request.getParameter("uname");
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String type = request.getParameter("type");
		String pwd = request.getParameter("pwd");
		String email = request.getParameter("email");
		
		out.print("<html> <head> <title> Booking tickets </title> ");
		
		
		
		//Styling of page:
		out.print("<style>");
		out.print("font{letter-spacing: -7px;}");
		out.print("input[type=submit]{width: 15%;font-size:110%; background-color: #0080ff;color: white;padding: 8px 10px;border: none;border-radius: 4px;cursor: pointer;}");
		
		out.print("</style>");
		out.print("</head>");
		out.print("<body style='font-family:sans-serif'>");
		out.print("<center>");
		out.print("<p style='text-align:center; font-size:350%;'> <font color='#0080ff'> C </font>"); 
		out.print("<font color='#ff0000'> h </font> <font color='#ffbf00'> a </font>");
		out.print("<font color='#0080ff'> l </font> <font color='green'> o </font> ");
		out.print("<font style='font-size:40%; letter-spacing: 0px;'>.com </font></p>");
		
		try
		{			
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1/test";
			Connection con=DriverManager.getConnection(url, "root", "root");
			Statement st = con.createStatement();
			
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
				String query = "insert into users(Username,First_name,Last_name,Email,Pasword,UserType) values('"+uname+"','"+fname+"','"+lname+"','"+email+"','"+pwd+"','"+type+"');";
				int r = st.executeUpdate(query);
				out.println("<p style='color:#4CAF50; font-size:150%;'> Your account has been successfully created! </p>");
				out.print("<form action='Login.html'>");
				out.print("<input type=submit value='Login to your account'>");
				out.print("</form>");
			}
			else
			{
				out.print("<form action='SignUp.html'>");
				out.print("<p style='color:red; font-size:150%;'> Username you entered is already taken! </p>");
				out.print("<input type=submit value='Try another one'>");
				out.print("</form>");
			}
			
			
			
			
			out.print("</center></body> </html>");
		}
		catch(Exception e)
		{
			out.println(e);
		}
		
		
		
		out.close();
	}
}