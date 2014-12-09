package all;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exceptions.AccountNotFoundException;


public class DeleteAccount extends HttpServlet {
	long lastAccount = 0;
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
 	 	req.setCharacterEncoding("Cp1251");
 	 	resp.setContentType("text/html;charset=windows-1251"); 	 	 	
 	 	PrintWriter out = resp.getWriter();
 	 	out.println( "<html>" +
 	 	 	"<head>" +
 	 	 	 	"<title>Delete account</title>" +
 	 	 	"</head>" +
 	 	 	"<body bgcolor=#aaffee>" +
 	 	 	 	"<body bgcolor=#aabbff> " +
 	 	 	 	 	"<form name=account method=POST " +
 	 	 	 	 	 	"action=/Bank/Delete_account>" + "Number = "+ 
 	 	 	 	 	    "<input type = text name=number>"+
 	 	 	 	 	 	"<input type=submit value=\"Delete\">" +
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
 	 	String response = "";
 	 	try {
 	 		number = Long.parseLong(req.getParameter("number"));
			Bank.deleteAccount(number);
			response = "Account " + number +" was successfully delete";
		} catch (AccountNotFoundException e) {
			// TODO Auto-generated catch block
		    response = "Account " + number + " not found";
		}
 	 	catch (NumberFormatException e) {
 	 		response = "Incorrect format";
 	 	}
 	 	
 	 	out.println( "<html>" +
 	 	 	 	"<head>" +
 	 	 	 	 	"<title>Delete account</title>" +
 	 	 	 	"</head>" +
 	 	 	 	"<body bgcolor=#aaffee>" +
 	 	 	 	 	"<body bgcolor=#aabbff> " +
 	 	 	 	 	 	"<form name=account method=POST " +
 	 	 	 	 	 	 	"action=/Bank/Delete_account>" + response + "<br>" +  
 	 	 	 	 	 	    "<input type = text name=number>"+
 	 	 	 	 	 	 	"<input type=submit value=\"Delete\">" +
 	 	 	 	 	 	"</form>" + 	 	 	 	
 	 	 	 	"</body" +
 	 	 	 	"</html>"
 	 	 	);	
 		out.close();
 	
 	 	
 	 	 	 	
 	}
}
