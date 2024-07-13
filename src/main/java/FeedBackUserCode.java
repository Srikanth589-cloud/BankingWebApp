

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/FeedBackUserCode")
public class FeedBackUserCode extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    
    public FeedBackUserCode() 
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
			String feedback=request.getParameter("feed");
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","srikanth","srikanth");
			
			PreparedStatement ps=con.prepareStatement("update sdfcbank set feedback=?  where name=? and password=? and accountno=?");
			ps.setString(1, feedback);
			ps.setString(2, name);
			ps.setString(3, password);
			ps.setLong(4, accountno);
			
			int i=ps.executeUpdate();
			
			if(i==1)
			{
				out.print("<h1 style='color:green'> Thanks for Feedback</h1>");
			}
			else
			{
				out.println("<h1 style='color:red'>Invalid Accno or name or passwrod</h1>");
			}
		}
		catch(Exception e)
		{
			out.println(e);
		}
	}

}
