package prodcons;


/*  Keith Walsh
 *  CS471 Fall 2013
 *  Course Project Part 1
 *  
 *  This program contains five classes:
 *  ProdCons: Main driver. Sets parameters for the program.
 *  SharedBuffer: Contains the synchronized methods for pushing
 *  	and popping data from the buffer.
 *  Transaction: Data structure for each transaction.   
 *  Producer: Creates each transaction and adds it to the buffer
 *  	using the SharedBuffer.PushData method.
 *  Consumer: Consumes transactions from the shared buffer using
 *  	the SharedBuffer.PopData method.
 * 
 */

public class ProdCons {
	
	// Variables needed to run the main program.
	public static int numProducers = 0;
	public static int numConsumers = 0;
	static int bufferSize = 10;
	static int maxTransactions = 10000;
	static public long startTime;

	
 public static void main(String args[]) {

	 // The values for p and c come in from the line command
	 // i.e. >java -jar prodcons.jar 3 3
	 if (args.length == 2) {
	    try {
	    	numProducers = Integer.parseInt(args[0]);
	    	numConsumers = Integer.parseInt(args[1]);
	    } catch (NumberFormatException e) {
	        System.err.println("Argument" + " must be an integer");
	        System.exit(1);
	    }
	
		startTime = System.currentTimeMillis();
		SharedBuffer buffer = new SharedBuffer(bufferSize, maxTransactions);
	
	
		System.out.println("Running with the following parameters:");
		System.out.print("Producers (p): ");
		System.out.printf("%,d",numProducers);
		System.out.print("\nConsumers (c): ");
		System.out.printf("%,d",numConsumers);
		System.out.print("\nShared Buffer Size (b): ");
		System.out.printf("%,d",bufferSize);
		System.out.print("\nNumber of transactions: ");
		System.out.printf("%,d",maxTransactions);
		System.out.print("\n");
	
	  
		// Starts the consumer threads. Nothing will be read untile
		// the producer threads start producing.
		for (int k = 0; k < numConsumers; ++k) {
			//String s = "Consumer" + i;	// F
				Consumer c = new Consumer(k,buffer);
				c.start();
			}
	  
			// Starts the producer threads.
		for (int k = 0; k < numProducers; ++k) {
			//String s = "Producer" + k;
			Producer p = new Producer(k,buffer);
			p.start();
		}

	}
	else {
		 System.out.println("Must supply two integers as arguments for p and c. i.e. >java -jar prodcons.jar 3 3.");
	}
 }
 
 
 public static void generateTotals() {	 
	 // Function to print out the report.
	 
	 // Statistics for each consumer are displayed first.
	 System.out.println("\nLOCAL STATISTICS\n" +
	 		"Report by Consumer by Month\n" +
			"Consumer ID\tStore ID\tMonth\tTotal");
	 for (int i =0; i < numConsumers; ++i) {
		 for (int j = 0; j < numProducers; ++j) {
			 for (int k = 0; k < 12; ++k) {
				 // Print table with each consumer's sales statistics
				 System.out.print((i+1)+"\t"+(j+1)+"\t"+(k+1)+"\t$");
				 System.out.printf("%,.2f",(SharedBuffer.totalByStoreByMonth[i][j][k]/100.0));
				 System.out.print("\n");
				 
				 // While displaying them, also capture the statistics by store, month and total.
				 SharedBuffer.totalByStore[j] += SharedBuffer.totalByStoreByMonth[i][j][k];
 				 SharedBuffer.totalByMonth[k] += SharedBuffer.totalByStoreByMonth[i][j][k];
 				 SharedBuffer.totalSales += SharedBuffer.totalByStoreByMonth[i][j][k];
			 }
		 }
	 }

	 System.out.println("\nGLOBAL STATISTICS\n" +
		 		"Report by Store\n" +
		 		"Store\tTotal");

	 for (int i = 0; i < numProducers; ++i) {
			 System.out.print((i+1) + "\t$");
			 System.out.printf("%,.2f",(SharedBuffer.totalByStore[i]/100.0));
			 System.out.print("\n");
	 }

	 System.out.println("\nReport by Month\n" +
		 		"Month\tTotal");
	 for (int i = 0; i < 12; ++i) {
		 System.out.print((i+1) + "\t$");
		 System.out.printf("%,.2f",(SharedBuffer.totalByMonth[i]/100.0));
		 System.out.print("\n");
 }


	 System.out.print("\nTotal Aggregate Sales: $");
	 System.out.printf("%,.2f",(SharedBuffer.totalSales/100.0));	 
	 System.out.print("\n");
	 
	 System.out.print("\nTotal time for simulation: ");
	 System.out.printf("%,d",System.currentTimeMillis()-startTime);
	 System.out.print(" ms\n");
	 System.exit(1);
 }
 
 

}
