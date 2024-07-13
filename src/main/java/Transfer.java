
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Transfer")
public class Transfer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public Transfer() 
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
			long targetAcc=Long.parseLong(request.getParameter("tnum"));
			
			double transamount=Double.parseDouble(request.getParameter("amt"));
		
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","srikanth","srikanth");
			
			PreparedStatement ps5=con.prepareStatement("select  *from sdfcbank  where accountno=?");
			ps5.setLong(1, accountno);
			
			ResultSet rs3=ps5.executeQuery();
			String st="";
			
			if(rs3.next())
			{
				st=rs3.getString("status");
				
			}
			if(st.equals("deactive"))
			{
				out.println("<h1 style='color:red'>Your Account is currenlty Deactivated</h1>");
				out.println("<h1 style='color:red'>Need to Activate Your Account</h1>");
			}
			
			else
			{
			
			PreparedStatement ps1=con.prepareStatement("select  * from sdfcbank  where name=? and password=? and accountno=?");
			ps1.setString(1, name);
			ps1.setString(2, password);
			ps1.setLong(3, accountno);
			
			ResultSet rs1=ps1.executeQuery();
			
			double amt1=0.0;
			if(rs1.next())
			{
				amt1=rs1.getDouble("amount");
			}
			
			
			amt1=amt1-transamount;
			
			PreparedStatement ps2=con.prepareStatement("update sdfcbank set amount=? where accountno=?");
			ps2.setDouble(1, amt1);
			ps2.setLong(2, accountno);
			
			int i=ps2.executeUpdate();
			
			
			
			PreparedStatement ps3=con.prepareStatement("select  * from sdfcbank  where accountno=?");
			ps3.setLong(1, targetAcc);
			
			ResultSet rs2=ps3.executeQuery();
			
			double amt2=0.0;
			if(rs2.next())
			{
				amt2=rs2.getDouble("amount");
			}
			
			amt2=amt2+transamount;
			
			
			PreparedStatement ps4=con.prepareStatement("update sdfcbank set amount=? where accountno=?");
			ps4.setDouble(1, amt2);
			ps4.setLong(2, targetAcc);
			
			int j=ps4.executeUpdate();
			
			if(j>0)
			{
				out.print("<h1 style='color:green'> Amount Transfered Successfully</h1>");
			}
			
			con.close();
			}
		}
		catch(Exception e)
		{
			out.println(e);
		}
	}
}
