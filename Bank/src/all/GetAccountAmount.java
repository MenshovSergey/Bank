package all;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exceptions.AccountNotFoundException;

//TODO: Table for text
public class GetAccountAmount extends HttpServlet {
	long lastAccount = 0;
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
 	 	req.setCharacterEncoding("Cp1251");
 	 	resp.setContentType("text/html;charset=windows-1251");  	 	
 	 	PrintWriter out = resp.getWriter();
 	 	out.println( "<html>" +
 	 	 	"<head>" +
 	 	 	 	"<title>Get account amount</title>" +
 	 	 	"</head>" +
 	 	 	"<body bgcolor=#aaffee>" +
 	 	 	 	"<body bgcolor=#aabbff> " +
 	 	 	 	 	"<form name=account method=POST " +
 	 	 	 	 	 	"action=/Bank/Get_account_amount>" + 
 	 	 	 	 	    "Number Account = "+ 
 	 	 	 	 	     "<input type = text name = number>  " + "<br>"+ 	 	 	 	 	    
 	 	 	 	 	 	"<input type=submit value=\"Get\">" +
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
 	 	try {
 	 		long number = Long.parseLong(req.getParameter("number"));
 	 		try {
 				long result = Bank.getAmount(number);
 				response = "Account  "+number + " have " + result + "<br>"; 			
 			} catch (AccountNotFoundException e) {
 				response = "Account " + number +" not found" + "<br>"; 				
 			} 	 	 	
 	 	} catch (NumberFormatException e) {
 	 		response = "Incorrect format account, try again " + "<br>" ; 	 		
 	 	}
 	 	out.println( "<html>" +
 	 	 	 	"<head>" +
 	 	 	 	 	"<title>Get account amount</title>" +
 	 	 	 	"</head>" +
 	 	 	 	"<body bgcolor=#aaffee>" +
 	 	 	 	 	"<body bgcolor=#aabbff> " +
 	 	 	 	 	 	"<form name=account method=POST " +
 	 	 	 	 	 	 	"action=/Bank/Get_account_amount>" + 
 	 	 	 	 	 	    response + 
 	 	 	 	 	 	    "Account number = "+ 
 	 	 	 	 	 	     "<input type = text name = number>  " + "<br>"+ 	 	 	 	 	 	    
 	 	 	 	 	 	 	"<input type=submit value=\"Get\">" + "<br>"+ 	 	 	 	 	 	     
 	 	 	 	 	 	"</form>" + 	 	 	 	
 	 	 	 	"</body" +
 	 	 	 	"</html>"
 	 	 	);
 	 	
 		out.close();
 	
 	 	
 	 	 	 	
 	}
}
