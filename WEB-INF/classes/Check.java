import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class Check extends HttpServlet
{
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		response.setContentType("txt/html");
		
		PrintWriter out = response.getWriter();
		
		String username = request.getParameter("usrname");
		String password = request.getParameter("usrpwd");
		
		
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			
			String url="jdbc:mysql://127.0.0.1/test";
			
			Connection con = DriverManager.getConnection(url,"root","root");
			Statement st = con.createStatement();
			
			String query = "select * from users where Username = '"+ username +"'";
			
			ResultSet rs = st.executeQuery(query);
			if(rs.first()==false)
			{
				
				response.sendRedirect("Login.html");
			}
			else
			{
				String usr = rs.getString("Username");
				String pwd = rs.getString("Pasword");
				String type = rs.getString("UserType");
				
				if(password.equals(pwd))
				{
					HttpSession sess = request.getSession(true);
					sess.setAttribute("username",usr);
					sess.setAttribute("sessID", sess.getId());
					sess.setAttribute("type", type);
					
					if(type.equals("customer"))
					{
						response.sendRedirect("Book.html");
					}
					else if(type.equals("admin"))
					{
						response.sendRedirect("Schedule.html");
					}	
				}
				else
				{
					response.sendRedirect("Login.html");
				}
				
			}
		}
		catch(Exception e)
		{
			out.print("<html> <head> <title> Login </title> </head>");
				
			out.print("<body> <center><h3> This username doesn't exist <h3> ");
			out.print("<a href = Login.html> Click to re-enter </a> </center>");
				out.print("<h>" + e + "/h");
			out.print("</body> </html>");
			
		}
		
		
		
		out.close();
	}
}