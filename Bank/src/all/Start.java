package all;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
public class Start extends HttpServlet
{
 	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
 	{
 	 	req.setCharacterEncoding("Cp1251");
 	 	resp.setContentType("text/html;charset=windows-1251");  	 	
 	 	PrintWriter out = resp.getWriter();
 	 	
 	 	out.println( "<html>" +
 	 	 	"<head>" +
 	 	 	 	"<title>Start</title>" +
 	 	 	"</head>" +
 	 	 	"<body bgcolor=#aaffee>" + 	 	 	 	 	
 	 	 	 	 "<br>" +
 	 	 	 	 "<a href=\"/Bank/Add_account\">Add_account</a>" +
 	 	 	 	 "<br>" +
 	 	 	 	 "<a href=\"/Bank/Delete_account\">Delete_account</a>" +
 	 	 	 	 "<br>" +
 	 	 	 	 "<a href=\"/Bank/List_accounts\">List_accounts</a>" +
 	 	 	 	 "<br>" +
 	 	 	 	 "<a href=\"/Bank/Get_account_amount\">Get_account_amount</a>" +
 	 	 	 	 "<br>" +
 	 	 	 	 "<a href=\"/Bank/Withdraw_account\">Withdraw_account</a>" +
 	 	 	   	 "<br>" +	 	 	 	 
 	 	 	   	 "<a href=\"/Bank/Add_money_to_account\">Add_money_to_account</a>" +
 	 	 	 	 "<br>" +
 	 	 	 	 "<a href=\"/Bank/Transfer\">Transfer</a>" +
 	 	 	"</body" +
 	 	 	"</html>"
 	 	);
 	 	
 	 	out.close();
 	 	
 	}
}