
import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Activate")
public class Activate extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    
    public Activate() 
    {
        super();
        
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		try
		{
			long accountno=Long.parseLong(request.getParameter("anum"));
			String name=request.getParameter("name");
			String password=request.getParameter("psw");
			
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","srikanth","srikanth");
			
			PreparedStatement ps1=con.prepareStatement("update sdfcbank set status=? where name=? and password=? and accountno=?");
		    ps1.setString(1, "active");
		    ps1.setString(2, name);
			ps1.setString(3, password);
			ps1.setLong(4, accountno);
			
			int i=ps1.executeUpdate();
			
			if(i>0)
			{
				out.println("<h1 style='color:green'>Your Account is Activated</h1>");
			}
		}
		catch(Exception e)
		{
			out.println(e);
		}
	}

}
