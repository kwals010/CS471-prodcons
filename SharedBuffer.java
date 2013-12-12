package prodcons;

/*
 * 
 *  The SharedBuffer uses the Java keyword synchronized to provide locking
 *  and ensure mutually exclusive access to critical variables. When threads
 *  wait, the notifyAll() commands from other threads will wake them up.
 * 
 */

public class SharedBuffer {
	
	// Global variables that must be written to in critical sections
	static public int put;	// Pointer to next location for Producer to put data in the array.
	static public int take;	// Pointer to current location where Consumer is waiting for data.
	static public Transaction[] sharedBuffer;	// Shared buffer as an array.
	static public int bufSize;	// Size of the shared buffer.
	static public int totalMade;	// Keeps track of transactions added to the buffer.
	static public int totalTaken;	// Keeps track of transactions read from the buffer.
	static public int maxNumber;	// The maximum number of transactions that will be created.
	static public int[][][] totalByStoreByMonth;	// Global statistics for report.
	static public int[] totalByStore;
	static public int[] totalByMonth;
	static public int totalSales;

 
 public SharedBuffer(int size, int max) {
	 
	 put = 1;	
	 take = 0;	
	 bufSize = size;	
	 sharedBuffer = new Transaction[size];	
	 totalMade = 0;
	 totalTaken = 0;
	 maxNumber = max;
	 totalByStoreByMonth = new int[ProdCons.numConsumers][ProdCons.numProducers][12];
	 totalByStore = new int[ProdCons.numProducers];
	 totalByMonth = new int[12];	// Twelve months in a year.
	 totalSales = 0;
 }
 
 public synchronized void pushData(int n, Transaction t) {
	 while(put == take) {	// If put == take, there's no room to put.
		 try {
			 //System.out.println(n + " waiting");
			 wait();
		 } 
		 catch(InterruptedException ex){}
	 }
	 if (totalMade < maxNumber){	
		 ++totalMade;
		 sharedBuffer[put] = t;		// Put the transaction into the buffer
		 put = (put + 1) % bufSize;	// Increment pointer for producer. With array, goes back to the start if at the end.
		 //System.out.println("Total made so far: " + totalMade);
		 notifyAll();
	 }
 }
 
 public synchronized void popData(int n) {
	 while((take + 1) % bufSize == put) {	// If the put pointer is one ahead, there's nothing to read.
		try {
			 //System.out.println(n + " waiting");
			 wait();
		}
		catch(InterruptedException ex){}
	 }	 
	 take = (take + 1) % bufSize; // Move the pointer for consumer
	 ++totalTaken;	//	Increment the number taken so far
	 // Add the transaction to the appropriate item in the global array for each consumer
	 totalByStoreByMonth[n][sharedBuffer[take].getStoreID()][sharedBuffer[take].getMonth()-1] += sharedBuffer[take].getSaleAmount();
	 notifyAll();
 }
 
 public synchronized boolean canMake() {
	 // Thread that checks to ensure the maximum number of transactions hasn't been reached.
	 if (totalMade < maxNumber) {
		 notifyAll();
		 return true;
	 } 
	 else {
		 notifyAll();
		 return false;
	 } 
 }
 
 public synchronized boolean canTake() {
	 // Thread that checks if all the transactions have been read. If they have, then
	 // goes back to main program to generate totals.
	 if (totalTaken < maxNumber) {
		 notifyAll();
		 return true;
	 } 
	 else {
		 //System.out.println("Nothing left to take");
		 ProdCons.generateTotals();
		 return false;
	 }
	 
 }
 
}
