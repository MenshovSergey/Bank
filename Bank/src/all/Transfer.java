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


public class Transfer extends HttpServlet {
	long lastAccount = 0;
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
 	 	req.setCharacterEncoding("Cp1251");
 	 	resp.setContentType("text/html;charset=windows-1251"); 	  	 	
 	 	PrintWriter out = resp.getWriter();
 	 	out.println( "<html>" +
 	 	 	"<head>" +
 	 	 	 	"<title>Transfer</title>" +
 	 	 	"</head>" +
 	 	 	"<body bgcolor=#aaffee>" +
 	 	 	 	"<body bgcolor=#aabbff> " +
 	 	 	 	 	"<form name=account method=POST " +
 	 	 	 	 	 	"action=/Bank/Transfer>" +
 	 	 	 	 	    "Account from + " +
 	 	 	 	 	    "<input type = text name = from>"+ "<br>"+
 	 	 	 	 	    "Account to + " +
	 	 	 	 	    "<input type = text name = to>"+ "<br>"+
 	 	 	 	 	    "Money = "+ 
 	 	 	 	 	    "<input type = text name = money>"+ "<br>"+
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
 	 	PrintWriter out = resp.getWriter(); 	
 	 	String response = "";
 	 	long from = 0;
 	 	long to = 0;
 	 	long money = 0;
 	 	try {
 	 		 from = Long.parseLong(req.getParameter("from"));
 	 	 	 to = Long.parseLong(req.getParameter("to"));
 	 	 	 money = Long.parseLong(req.getParameter("money"));
 	 	 	if (money <= 0) {
 	 	 		throw new NumberFormatException();
 	 	 	}
			Bank.transfer(from, to, money);
			response = "Operation done successfully";
		} catch (AccountNotFoundException e) {
			response = "Account not found";
		} catch (NotMuchMoneyException e) {
			response = "Account " + from +" doesn't have enough money";
		} catch (OverflowAccountException e) {
			response = "Account "+to+ " have too much money. You wanted by the FBI";
		} catch (NumberFormatException e) {
			response = "Incorrect format";
		}
 	 	out.println( "<html>" +
 	 	 	 	"<head>" +
 	 	 	 	 	"<title>Transfer</title>" +
 	 	 	 	"</head>" +
 	 	 	 	"<body bgcolor=#aaffee>" +
 	 	 	 	 	"<body bgcolor=#aabbff> " +
 	 	 	 	 	 	"<form name=account method=POST " +
 	 	 	 	 	 	 	"action=/Bank/Transfer>" +
 	 	 	 	 	 	    "Account from + " +
 	 	 	 	 	 	    "<input type = text name = from>"+ "<br>"+
 	 	 	 	 	 	    "Account to + " +
 		 	 	 	 	    "<input type = text name = to>"+ "<br>"+
 	 	 	 	 	 	    "Money = "+ 
 	 	 	 	 	 	    "<input type = text name = money>"+ "<br>"+
 	 	 	 	 	 	 	"<input type=submit value=\"Do\">" + "<br>"+
 	 	 	 	 	 	    response +
 	 	 	 	 	 	"</form>" + 	 	 	 	
 	 	 	 	"</body" +
 	 	 	 	"</html>"
 	 	 	);
 		out.close();
 	
 	 	
 	 	 	 	
 	}
}
