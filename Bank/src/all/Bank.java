package all;

import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import exceptions.AccountNotFoundException;
import exceptions.NotMuchMoneyException;
import exceptions.OverflowAccountException;

public class Bank {
	private static long last = 0;
	private static String user = "nauru";
	private static String password = "17981798";
	private static String url = "jdbc:mysql://localhost:3306/bank";
	private static String driver = "com.mysql.jdbc.Driver";
	private static Connection c = null;// 
	private static Statement st;
	private static String lock = "lock";
	static {
		try {
			Class.forName(driver);// Регистрируем драйвер
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			c = DriverManager.getConnection(url, user, password);
			st = c.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static long createAccount() {
		long res = 0;
		Statement st = null;
		try {
			st = c.createStatement();
			st.execute("Lock tables accounts WRITE");
			ResultSet rs = st.executeQuery("select MAX(account) from accounts");
			while (rs.next()) {
				res = Long.parseLong(rs.getString("max(account)"));
			}
			res++;
			st.execute("INSERT INTO ACCOUNTS VALUES (" + res + "," + 0 + ")");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				st.execute("Unlock tables");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return res;
	}

	public static long getAmount(long number) throws AccountNotFoundException {
		long result = -1;
		ResultSet rs = null;
		String res = "";
		Statement st = null;
		try {
			st = c.createStatement();
			Bank.lock(st, number);
			rs = st.executeQuery("SELECT * from accounts WHERE (account = "
					+ number + ")");
			if (rs.isClosed()) {
				// System.out.println("nimber " + number + "isCLosed");
			} else {
				if (rs.next()) {
					result = rs.getLong("amount");
				} else {
					throw new AccountNotFoundException();
				}
			}
			st.executeQuery("SELECT RELEASE_LOCK(\"" + lock + number + "\""
					+ ")");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		}
		return result;
	}

	public static void deleteAccount(long account)
			throws AccountNotFoundException {
		Statement st = null;
		try {
			st = c.createStatement();
			Bank.lock(st, account);
			int count = st.executeUpdate("DELETE from accounts where(account="
					+ account + ");");
			if (count == 0) {
				throw new AccountNotFoundException();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			Bank.unlock(st, account);
		}
	}

	public static long withdrawAccount(long account, long count)
			throws AccountNotFoundException, NotMuchMoneyException {
		long amount = 0;
		ResultSet rs = null;
		String res = "";
		Statement st = null;
		try {
			st = c.createStatement();
			Bank.lock(st, account);
			// System.out.println("withdraw account = "+ account +
			// "count = "+count);
			rs = st.executeQuery("select * from accounts where (account = "
					+ account + ")");
			if (rs.next()) {
				amount = rs.getLong("amount");
			} else {
				throw new AccountNotFoundException();
			}
			if (amount < count) {
				throw new NotMuchMoneyException();
			}
			// System.out.println("["+Thread.currentThread().getId()+"]Withdraw before : DB = "+
			// amount+" " + "index="+(account - 1));
			amount = amount - count;

			st.executeUpdate("UPDATE accounts set amount = " + amount
					+ " where (account = " + account + ")");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			Bank.unlock(st, account);
		}
		return (amount);

	}

	public static long addMoneyToAccount(long account, long count)
			throws AccountNotFoundException, OverflowAccountException {
		long result = 0;
		ResultSet rs = null;
		String res = "";
		Statement st = null;
		try {
			st = c.createStatement();
			// System.out.println("addMoneyToAccount");

			Bank.lock(st, account);
			rs = st.executeQuery("SELECT * from accounts WHERE (account = "
					+ account + ")");
			if (rs.next()) {
				result = rs.getLong("amount");
				int r = st
						.executeUpdate("UPDATE accounts SET amount = amount +"
								+ count + " WHERE " + "(account=" + account
								+ ")");

				result += count;
				// System.out.println(result);

			} else {
				throw new AccountNotFoundException();
			}
		} catch (SQLException e) {
			if (e.getMessage().contains(
					"Data truncation: Out of range value for column")) {
				throw new OverflowAccountException();
			}
			e.printStackTrace();
		} finally {
			Bank.unlock(st, account);
		}
		return result;
	}

	public static ArrayList<Long> getListAccount() {
		ArrayList<Long> result = new ArrayList<Long>();
		Statement st = null;
		try {
			st = c.createStatement();
			ResultSet rs = st
					.executeQuery("select * from accounts where (account > 0)");
			while (rs.next()) {
				result.add(rs.getLong("account"));
				Bank.lock(st, result.get(result.size() - 1));
				result.add(rs.getLong("amount"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (long i : result) {
			Bank.unlock(st, i);
		}
		return result;
	}

	public static void transfer(long from, long to, long money)
			throws AccountNotFoundException, NotMuchMoneyException,
			OverflowAccountException {
		long amountFrom = 0;
		long amountTo = 0;
		Statement st = null;
		try {
			st = c.createStatement();
			// System.out.println("transfer");
			if (from < to) {
				Bank.lock(st, from);
				Bank.lock(st, to);
			} else {
				Bank.lock(st, to);
				Bank.lock(st, from);
			}
			amountFrom = Bank.getLockAccount(st, from);
			amountTo = Bank.getLockAccount(st, to);
			if (amountFrom < money) {
				throw new NotMuchMoneyException();
			}
			st.executeUpdate("Update accounts set amount = amount + " + money
					+ " where (account = " + to + ")");
			st.executeUpdate("Update accounts set amount = amount - " + money
					+ " where (account = " + from + ")");

		} catch (SQLException e) {
			if (e.getMessage().contains(
					"Data truncation: Out of range value for column")) {
				throw new OverflowAccountException();
			}
			e.printStackTrace();
		} finally {
			Bank.unlock(st, from);
			Bank.unlock(st, to);

		}

	}

	private static long getLockAccount(Statement st, long account)
			throws SQLException, AccountNotFoundException {
		long result = 0;
		ResultSet rs = st
				.executeQuery("select * from accounts where(account = "
						+ account + ")");
		if (rs.next()) {
			result = rs.getLong("amount");
		} else {
			throw new AccountNotFoundException();
		}
		return result;
	}

	private static void lock(Statement st, long account) {
		while (true) {
			ResultSet rs;
			try {
				rs = st.executeQuery("SELECT GET_LOCK(\"" + lock + account
						+ "\"" + ", 10" + ")");
				if (rs.next()) {
					if (!rs.wasNull()) {
						String res = rs.getString(1);
						// System.out.println("GET_LOCK = " + res +
						// " account = " + account);
						if ("1".equals(res)) {
							break;
						}
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private static void unlock(Statement st, long account) {
		try {
			st.executeQuery("SELECT RELEASE_LOCK(\"" + lock + account + "\""
					+ ")");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void finalize() {
		try {

			c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
