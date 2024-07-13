import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/NewAccount")
public class NewAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public NewAccount()
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
			String confirm_password=request.getParameter("cpsw");
			double amount=Double.parseDouble(request.getParameter("amt"));
			String address=request.getParameter("address");
			long mobile=Long.parseLong(request.getParameter("mobile"));
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","srikanth","srikanth");
			
			PreparedStatement ps=con.prepareStatement("insert into sdfcbank values(?,?,?,?,?,?,?,?,?)");
			ps.setLong(1, accountno);
			ps.setString(2, name);
			ps.setString(3, password);
			ps.setString(4, confirm_password);
			ps.setDouble(5, amount);
			ps.setString(6, address);
			ps.setLong(7, mobile);
			ps.setString(8, "active");
			ps.setString(9, "No Feedback");
			
			int i=ps.executeUpdate();
			if(i>0)
			{
				out.println("<h1 style='color:green'>New Account Created Successfully</h1>");
				response.sendRedirect("home.html");
			}
			else
			{
				out.println("<h1 style='color:red'> Account Not Created</h1>");
			}
			con.close();
		}
		catch(Exception e)
		{
			out.println(e);
			//"<h1 style='color:red'>Duplicate Account Number!</h1>"
		}
	}

}
