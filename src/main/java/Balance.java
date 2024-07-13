import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Balance")
public class Balance extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
   
    public Balance() 
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
			
			
			
				PreparedStatement ps6=con.prepareStatement("select  *from sdfcbank  where name=? and password=? and accountno=?");
				ps6.setString(1, name);
				ps6.setString(2, password);
				ps6.setLong(3, accountno);
				
				ResultSet rs1=ps6.executeQuery();

				PreparedStatement ps=con.prepareStatement("select   *from sdfcbank  where name=? and password=? and accountno=?");
				ps.setString(1, name);
				ps.setString(2, password);
				ps.setLong(3, accountno);
				
				ResultSet rs=ps.executeQuery();
				
				ResultSetMetaData rsmd=rs.getMetaData();
				int n=rsmd.getColumnCount();
				
			
			out.println("<table border='1'style='border-collapse: collapse';>");
			for(int i=1;i<=n;i++)
			{
				out.println("<th>"+rsmd.getColumnName(i)+"</th>");
			}
			
			while(rs.next())
			{
				out.println("<tr>");
				for(int i=1;i<=n;i++)
				{
					out.println("<td>"+rs.getString(i)+"</td>");
				}
				out.println("</tr>");
			}
			out.println("</table>");

			}
			con.close();
		}
		catch(Exception e)
		{
			out.println(e);
		}
	}

}
