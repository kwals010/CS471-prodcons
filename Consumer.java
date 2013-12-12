package prodcons;

import java.util.Random;

/*
 *  
 *  Consumer class pulls data out of the shared buffer.
 * 
 */
public class Consumer extends Thread {
	private SharedBuffer buffer;
	private int consumerID;	
	 
 public Consumer(int n, SharedBuffer buffer) {
	  this.buffer = buffer;
	  this.consumerID = n;
 }
 
 public void run() {
	 // Uses the canTake thread to see if there's anything to read in the buffer.
	 while (buffer.canTake()) {		 
		 buffer.popData(consumerID);	
		 // All the work on the data has to happen while holding the lock, so nothing happens here. 
		 try {
			 Thread.sleep(generateRandomSleep());
		 } 
		 catch (InterruptedException e) {
		 }
	 }	 
 }
 
 public long generateRandomSleep() {
		// Randomly sleep for 5-40 milliseconds.
		Random rn = new Random();
		double r = 5 + rn.nextDouble() * 35;
		//System.out.println("Random sleep: " + r);
		return (long) r;
		
	}
}
