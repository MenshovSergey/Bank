package all;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AddAccount extends HttpServlet {
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
 	 	 	 	 	 	"action=/Bank/Add_account>" + 	 	 	 	 	 	 	 	 	 	 	 	
 	 	 	 	 	 	"<input type=submit value=\"Create\">" +
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
 	 	lastAccount = Bank.createAccount();
 		out.println( "<html>" +
 	 	 	 	"<head>" +
 	 	 	 	 	"<title>Add account</title>" +
 	 	 	 	"</head>" +
 	 	 	 	"<body bgcolor=#aaffee>" +
 	 	 	 	 	"<body bgcolor=#aabbff> " +
 	 	 	 	       "your account = "+ lastAccount +
 	 	 	 	 	 	"<form name=account method=POST " +
 	 	 	 	 	 	 	"action=/Bank/Add_account>" + 	 	 	 	 	 	 		 	 	 	 	 	 	
 	 	 	 	 	 	 	"<input type=submit value=\"Create\">" +
 	 	 	 	 	 	"</form>" + 	 	 	 	
 	 	 	 	"</body" +
 	 	 	 	"</html>"
 	 	 	); 	
 		
 		
 		out.close();  	
 	 	 	 	
 	}
	
}
