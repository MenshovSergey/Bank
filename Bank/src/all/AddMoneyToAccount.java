package all;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exceptions.AccountNotFoundException;
import exceptions.NotMuchMoneyException;
import exceptions.OverflowAccountException;


public class AddMoneyToAccount extends HttpServlet {
	long lastAccount = 0;
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
 	 	req.setCharacterEncoding("Cp1251");
 	 	resp.setContentType("text/html;charset=windows-1251"); 	 	 	 	
 	 	PrintWriter out = resp.getWriter();
 	 	out.println( "<html>" +
 	 	 	"<head>" +
 	 	 	 	"<title>Add money</title>" +
 	 	 	"</head>" +
 	 	 	"<body bgcolor=#aaffee>" +
 	 	 	 	"<body bgcolor=#aabbff> " +
 	 	 	 	 	"<form name=account method=POST " +
 	 	 	 	 	 	"action=/Bank/Add_money_to_account>" + 
 	 	 	 	 	     "Account number = " +  	 	 	 	 	 	 
 	 	 	 	 	     "<input type = text name=number>"+ "<br>" +
 	 	 	 	 	     "Money = " +
 	 	 	 	 	     "<input type = text name=money>" + "<br>" +
 	 	 	 	 	 	"<input type=submit value=\"Add\">" +
 	 	 	 	 	"</form>" + 	 	 	 	
 	 	 	"</body" +
 	 	 	"</html>"
 	 	);
 	 	out.close(); 	 	
 	}
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
 	 	req.setCharacterEncoding("Cp1251");
 	 	resp.setContentType("text/html;charset=windows-1251"); 	 	
 	 	PrintWriter out = resp.getWriter();
 	 	long number = 0;
 	 	long count = 0;
 	 	String response = "";
 	 	try {
			number = Long.parseLong(req.getParameter("number"));
	 	 	count = Long.parseLong(req.getParameter("money"));
	 	 	if (count <= 0) {
	 	 		throw new NumberFormatException();
	 	 	}
			Bank.addMoneyToAccount(number, count);
			response = "Operation done succesfully";
		} catch (AccountNotFoundException e) {
			response = "Account not found";			
		} catch (OverflowAccountException e) {
			response = "You have too much money. You wanted by the FBI";
		}
 	 	
		catch (NumberFormatException e) {
			response = "Incorrect format";
		}
		
 	 	out.println( "<html>" +
 	 	 	 	"<head>" +
 	 	 	 	 	"<title>Add money</title>" +
 	 	 	 	"</head>" +
 	 	 	 	"<body bgcolor=#aaffee>" +
 	 	 	 	 	"<body bgcolor=#aabbff> " +
 	 	 	 	 	 	"<form name=account method=POST " +
 	 	 	 	 	 	 	"action=/Bank/Add_money_to_account>" + 
 	 	 	 	 	 	     "Account number = " +  	 	 	 	 	 	 
 	 	 	 	 	 	     "<input type = text name=number>"+ "<br>" +
 	 	 	 	 	 	     "Money = " +
 	 	 	 	 	 	     "<input type = text name=money>" + "<br>" +
 	 	 	 	 	 	 	"<input type =submit value=\"Add\">" + "<br>"+
 	 	 	 	 	 	     response + 	 	 	 	 	 	     
 	 	 	 	 	 	"</form>" + 	 	 	 	
 	 	 	 	"</body" +
 	 	 	 	"</html>"
 	 	 	);
 		out.close();
 	
 	 	
 	 	 	 	
 	}
}
