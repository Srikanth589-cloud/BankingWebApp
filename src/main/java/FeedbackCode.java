import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/FeedbackCode")
public class FeedbackCode extends HttpServlet
{
	private static final long serialVersionUID = 1L;
       
   
    public FeedbackCode() 
    {
        super();
       
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		try
		{
			String feed=request.getParameter("feed");
			String details=request.getParameter("details");
			String name=request.getParameter("name");
			String email=request.getParameter("mail");
			String mobile=request.getParameter("mobile");
			String comment=request.getParameter("com");
			String customer=request.getParameter("op");
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","srikanth","srikanth");
			
			PreparedStatement ps=con.prepareStatement("insert into feedback values(?,?,?,?,?,?,?)");
			ps.setString(1,feed);
			ps.setString(2,details);
			ps.setString(3,name);
			ps.setString(4,email);
			ps.setString(5,mobile);
			ps.setString(6,comment);
			ps.setString(7,customer);
			
			int i=ps.executeUpdate();
			if(i==1)
			{
				out.println("Thanks for Feedback Or Complaints");
			}
			else
			{
				out.println("please enter rigth feedback or comments");
			}
			con.close();
		}
		catch(Exception e)
		{
			out.println(e);
		}
		
	}

}
