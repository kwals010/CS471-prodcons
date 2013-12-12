package prodcons;

import java.util.Random;

/*
 *  
 *  Producer class generates the random values needed for each transaction.
 * 
 */

public class Producer extends Thread {
	private SharedBuffer buffer;
	private int storeID;

 
 public Producer(int n, SharedBuffer buffer) {
	 this.storeID = n;
	 this.buffer = buffer;
 }
 
 public void run() {
	 // Uses the canMake thread to see if there's room in the shared buffer.
	 while (buffer.canMake()) {	
		// Month and day are generated randomly
		int month = generateRandomMonth();
	    int day = generateRandomDay(month);
	    
	 // Create a new transaction
	    Transaction t = new Transaction(storeID, generateRandomRegister(),dateToString(month,day),generateRandomCents());
		 
	  buffer.pushData(storeID, t);
	  try {
		  Thread.sleep(generateRandomSleep());
	  } 
	  catch (InterruptedException e) {
	  }
	 }
 }
 public int generateRandomRegister() {
	  // The register numbers range from 1-10 for any store.	
	 Random rn = new Random();
		int r = rn.nextInt(10) + 1;
		//System.out.println("Random register: " + r);
		return r; 
	}
	
	public int generateRandomCents() {
		// The sale amount in each item can range between 0.50 and 999.99.
		// That is, between 50 and 99,999 cents.
		Random rn = new Random();
		int r = rn.nextInt(99999) + 50;
		return r; 
	}
	
	public int generateRandomMonth() {
		// Assume that the DD field is 1-30, MM is 01-12, and YY is always 06.
		Random rn = new Random();
		int r = rn.nextInt(12) + 1;
		//System.out.println("Random month: " + r);
		return r; 
	}
	
	public int generateRandomDay(int month)	{
		int maxdays = 31;
		if (month == 2) {
			maxdays = 28;
		}
		else if (month == 4 || month == 6 || month == 9 || month == 11) {
			maxdays = 30;
		}
		Random rn = new Random();
		int r = rn.nextInt(maxdays) + 1;
		//System.out.println("Random day: " + r);
		return r; 
	}
	
	static String dateToString(int month, int day) {
	    StringBuffer s = new StringBuffer(8);
	    if (month < 10) {
	    	s.append(0);
	    }
	    s.append(month);
	    s.append("-");
	    if (day < 10) {
	    	s.append(0);
	    }
	    s.append(day);
	    s.append("-06");
	    return s.toString();
	}
	
	public long generateRandomSleep() {
		// Randomly sleep for 5-40 milliseconds
		Random rn = new Random();
		double r = 5 + rn.nextDouble() * 35;
		//System.out.println("Random sleep: " + r);
		return (long) r;
	}
}
