

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Withdraw")
public class Withdraw extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public Withdraw()
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
			double wamount=Double.parseDouble(request.getParameter("amt"));
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","srikanth","srikanth");
			
			PreparedStatement ps5=con.prepareStatement("select  *from sdfcbank  where accountno=?");
			ps5.setLong(1, accountno);
			
			ResultSet rs2=ps5.executeQuery();
			String st="";
			
			if(rs2.next())
			{
				st=rs2.getString("status");
				
			}
			if(st.equals("deactive"))
			{
				out.println("<h1 style='color:red'>Your Account is currenlty Deactivated</h1>");
				out.println("<h1 style='color:red'>Need to Activate Your Account</h1>");
			}
			
			else
			{
			
			PreparedStatement ps=con.prepareStatement("select amount from sdfcbank  where name=? and password=? and accountno=?");
			ps.setString(1, name);
			ps.setString(2, password);
			ps.setLong(3, accountno);
			
			ResultSet rs=ps.executeQuery();
			//ResultSetMetaData rsmd=rs.getMetaData();
			//int n=rsmd.getColumnCount();
			
			double amount=0.0;
			
			if(rs.next())
			{
				amount=rs.getDouble(1);
			}
			
			
			
			out.println("<h1 style='color:green'>Your Current Amount is </h1>"+amount+"<br>");
			if(amount>wamount)
			{
				
			amount=amount-wamount;
			
			PreparedStatement ps1=con.prepareStatement("update sdfcbank set amount=? where name=? and password=? and accountno=?");
		    ps1.setDouble(1, amount);
		    ps1.setString(2, name);
			ps1.setString(3, password);
			ps1.setLong(4, accountno);
	
			int i=ps1.executeUpdate();
			
			out.println("<h3 style='color:red'>After Withdraw Your Balance is </h3>"+amount);
			}
			else
			{
				out.print("Insufficient Balance");
			}
			con.close();
			}
		}
		catch(Exception e)
		{
			out.println(e)	;
		}
	}

}
