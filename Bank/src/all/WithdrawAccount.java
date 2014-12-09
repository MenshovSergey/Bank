package all;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exceptions.AccountNotFoundException;
import exceptions.NotMuchMoneyException;


public class WithdrawAccount extends HttpServlet {
	long lastAccount = 0;
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
 	 	req.setCharacterEncoding("Cp1251");
 	 	resp.setContentType("text/html;charset=windows-1251"); 	 	 	 	
 	 	PrintWriter out = resp.getWriter();
 	 	out.println( "<html>" +
 	 	 	 	"<head>" +
 	 	 	 	 	"<title>Add account</title>" +
 	 	 	 	"</head>" +
 	 	 	 	"<body bgcolor=#aaffee>" +
 	 	 	 	 	"<body bgcolor=#aabbff> " +
 	 	 	 	      
 	 	 	 	 	 	"<form name=account method=POST " +
 	 	 	 	 	 	 	"action=/Bank/Withdraw_account>" + "<br>"+
 	 	 	 	 	 	     "Enter account "+ "<input type = text name=number>"+ "<br>"+
 	 	 	 	 	 	 	 "Enter withdraw amount "+ "<input type = text name=withdraw>"+ "<br>"+
 	 	 	 	 	 	 	"<input type=submit value=\"Do\">" +
 	 	 	 	 	 	"</form>" + 	 	 	 	
 	 	 	 	"</body" +
 	 	 	 	"</html>"
 	 	 	); 	
 	 	out.close(); 	 	
 	}
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
 	 	req.setCharacterEncoding("Cp1251");
 	 	resp.setContentType("text/html;charset=windows-1251"); 	 	
 	 	long number = 0;
 	 	long withdraw = 0;
 	 	String response = ""; 
 	 	
		try {
			number = Long.parseLong(req.getParameter("number"));
	 	 	withdraw = Long.parseLong(req.getParameter("withdraw"));
	 	 	if (withdraw < 0) {
	 	 		throw new NumberFormatException();
	 	 	}
			Bank.withdrawAccount(number, withdraw);
			response = "Operation done succesfully";
		} catch (AccountNotFoundException e) {
			response = "Account not found";
			
		} catch (NotMuchMoneyException e) {
			response = "You have a little money";
		}
		catch (NumberFormatException e) {
			response = "Incorrect format";
		}
		
 	 	PrintWriter out = resp.getWriter(); 	 	 	 		 
 		out.println( "<html>" +
 	 	 	 	"<head>" +
 	 	 	 	 	"<title>Add account</title>" +
 	 	 	 	"</head>" +
 	 	 	 	"<body bgcolor=#aaffee>" +
 	 	 	 	 	"<body bgcolor=#aabbff> " + 	 	 	 	      
 	 	 	 	 	 	"<form name=account method=POST " +
 	 	 	 	 	 	 	"action=/Bank/Withdraw_account>" + "<br>"+
 	 	 	 	 	 	     "Enter account "+ "<input type = text name=number>"+ "<br>"+
 	 	 	 	 	 	 	 "Enter withdraw amount "+ "<input type = text name=withdraw>"+ "<br>"+
 	 	 	 	 	 	 	"<input type=submit value=\"Do\">" +"<br>" + response +
 	 	 	 	 	 	"</form>" + 	 	 	 	
 	 	 	 	"</body" +
 	 	 	 	"</html>"
 	 	 	); 	
 		out.close();
 	
 	 	
 	 	 	 	
 	}
}
