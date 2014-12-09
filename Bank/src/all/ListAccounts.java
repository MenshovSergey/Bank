package all;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ListAccounts extends HttpServlet {
	long lastAccount = 0;
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
 	 	req.setCharacterEncoding("Cp1251");
 	 	resp.setContentType("text/html;charset=windows-1251"); 	 	 	 	
 	 	PrintWriter out = resp.getWriter();
 	 	out.println( "<html>" +
 	 	 	"<head>" +
 	 	 	 	"<title>List accounts</title>" +
 	 	 	"</head>" +
 	 	 	"<body bgcolor=#aaffee>" +
 	 	 	 	"<body bgcolor=#aabbff> " +
 	 	 	 	 	"<form name=account method=POST " +
 	 	 	 	 	 	"action=/Bank/List_accounts>" + 	 	 	 	 	 	 	 	 	 	 	 	
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
 		out.println( "<html>" +
 	 	 	 	"<head>" +
 	 	 	 	 	"<title>List accounts</title>" +
 	 	 	 	"</head>" +
 	 	 	 	"<body bgcolor=#aaffee>" +
 	 	 	 	 	"<body bgcolor=#aabbff> " + 	 	 	 	      
 	 	 	 	 	 	"<form name=account method=POST " +
 	 	 	 	 	 	 	"action=/Bank/List_accounts>" + 	 	 	 	 	 	 		 	 	 	 	 	 	
 	 	 	 	 	 	 	"<input type=submit value=\"Get\">" + "<br>" +
 	 	 	 	 	 	 	print(Bank.getListAccount())+ 
 	 	 	 	 	 	"</form>" + 	 	 	 	
 	 	 	 	"</body" +
 	 	 	 	"</html>"
 	 	 	); 	
 		out.close();	 	
 	 	 	 	
 	}
	private String print(ArrayList<Long> accounts) {
		String res = "";
		for (int i = 0; i < accounts.size(); i += 2) {
			res += accounts.get(i) + " "+ accounts.get(i + 1) + "<br>";
		}
		return res;
	}
}
