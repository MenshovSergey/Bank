package tests.main.java.ru.ifmo.pp.fgb;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import all.Bank;
import exceptions.AccountNotFoundException;
import exceptions.NotMuchMoneyException;
import exceptions.OverflowAccountException;

public class BankImpl implements tests.main.java.ru.ifmo.pp.fgb.Bank  {
    private int size = 0;
    private final LoggingAccount[] accounts;
	public BankImpl(int N) {
		System.out.println("Constructing...");
		accounts = new LoggingAccount[N];
		
		for (int i = 0; i < N; i++) {
			Bank.createAccount();
			accounts[i] = new LoggingAccount(i);
		}
	}
	@Override
	public int getNumberOfAccounts() {
		return accounts.length;
	}

	@Override
	public long getAmount(int index) {
		long result = 0;
		accounts[index].lock.lock();
		try {
			result =  Bank.getAmount(index + 1);
		} catch (AccountNotFoundException e) {			
			e.printStackTrace();
		}		
        long res = accounts[index].amount;        
        if (res != result) {
        	System.out.println("[" + Thread.currentThread().getId() + "] Error: expected: " + res + ", found: " + result 
        			+" account = "+index);
        } else {
        	//System.out.println("[" + Thread.currentThread().getId() + "] getAmount" +" index = "+
        		//	index + " "+ "result = " + result);
        }
        accounts[index].lock.unlock();
		return result;
	}

	@Override
	public long getTotalAmount() {
		long sum = 0;
        for (LoggingAccount account : accounts) {
            account.lock.lock();
            sum += account.amount;
        }
        for (LoggingAccount account : accounts) {
            account.lock.unlock();
        }
		return sum;
	}

	@Override
	public long deposit(int index, long amount) {
		
		if (amount <= 0)
            throw new IllegalArgumentException("Invalid amount: " + amount);
		long result = 0;
		 LoggingAccount account = accounts[index];
	        long res = 0;
	        account.lock.lock();
		     try {
				result = Bank.addMoneyToAccount(index + 1, amount);
			} catch (AccountNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (OverflowAccountException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		        try {
		            if (amount > MAX_AMOUNT || account.amount + amount > MAX_AMOUNT) {
		                throw new IllegalStateException("Overflow");
		            }
		            //System.out.println("Deposit before : account.amount = "+account.amount+" " + "index="+index);
		            account.amount += amount;
		            //account.setAmount(account.amount + amount);
		            //System.out.println("account.amount = " + account.amount +" number = "+ (index + 1));
		            res = account.amount;		        
		
		        //System.out.println("Deposit " +"account = " + index + " amount = "+ amount );
	    } finally {
            account.lock.unlock();
        }
		return result;
	}

	@Override
	public long withdraw(int index, long amount) {
		long result = 0;
		boolean res1 = false;
		if (amount <= 0)
            throw new IllegalArgumentException("Invalid amount: " + amount);
		 LoggingAccount account = accounts[index];
	        long res = 0;
	        account.lock.lock();
			try {
				result = Bank.withdrawAccount(index + 1, amount);
			} catch (AccountNotFoundException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			} catch (NotMuchMoneyException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}		
	        try {
	            if (account.amount - amount < 0){
	            	
	            	throw new IllegalStateException("Underflow");
	            }	     
	            //System.out.println("Withdraw before : DB = "+ account.amount+" " + "index="+index);
	            account.amount -= amount;
	            //account.setAmount(account.amount - amount);
	            res = account.amount;
	            
	            	System.out.println("withdraw account ="+ index
	            			+ "DB= "+ result +" array = "+ res);
	            
	            //System.out.println("Withdraw " +"account = " + index + " amount = "+ amount );
	        }  finally {
	        	account.lock.unlock();
	        }
	  return result;
	}

	@Override
	public void transfer(int fromIndex, int toIndex, long amount) {
		boolean res = false;
		if (amount <= 0)
            throw new IllegalArgumentException("Invalid amount: " + amount);
        if (fromIndex == toIndex)
            throw new IllegalArgumentException("fromIndex == toIndex");
    	LoggingAccount from = accounts[fromIndex];
        LoggingAccount to = accounts[toIndex];
        if (fromIndex > toIndex) {
            to.lock.lock();
            from.lock.lock();
        } else {
            from.lock.lock();
            to.lock.lock();
        }
			try {
				//System.out.println("["+Thread.currentThread().getId()+"] Transfer fromIndex= "+ fromIndex + " toIndex = "+ toIndex + "count = "+ amount);
				Bank.transfer(fromIndex + 1, toIndex + 1, amount);
			} catch (AccountNotFoundException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			} catch (NotMuchMoneyException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			} catch (OverflowAccountException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		
	
        try {
            if (amount > from.amount) {            	
                throw new IllegalStateException("Underflow");            	
            }
            else if (amount > MAX_AMOUNT || to.amount + amount > MAX_AMOUNT)
                throw new IllegalStateException("Overflow");
            from.amount -= amount;
            to.amount += amount;            
        } finally {
            from.lock.unlock();
            to.lock.unlock();
        }
		// TODO Auto-generated method stub
		
	}
	/**
     * Private account data structure.
     */
    private static class Account {
        /**
         * Amount of funds in this account.
         */
        long amount;
        Lock lock = new ReentrantLock();

    }
    
	
    private static class LoggingAccount {
    	int id;
    	long amount;
    	Lock lock = new ReentrantLock();
    	
    	public LoggingAccount(int id) {
    		this.id = id;
    	}
    	
    	void setAmount(long amount) {
    		//System.out.println("[" + Thread.currentThread().getId() + "] Account " + id + " changed from " + this.amount + " to " + amount + " (" + this.hashCode() + ")");
    		this.amount = amount;
    	}
    }
	

}
