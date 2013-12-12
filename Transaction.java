package prodcons;

/*
 *  
 *  Transaction class contains the variables for each transaction.
 * 
 */

public class Transaction {
	
	// Each transation contains the following information:
	// Sales Date (DD/MM/YY), store ID (integer), register# (integer), 
	// sale amount (float).
	// I'm using type int for sale amount to increase precision on operations.
	
	private int storeID;
	private int saleAmount;
	private String saleDate;
	private int registerNumber;
	
	
	public Transaction(int si, int rn, String sd, int sa) {
		storeID = si;
		saleAmount = sa;
		saleDate = sd;
		registerNumber = rn;
	}
	
	public void printTransaction() {

		System.out.println("Store ID: " + storeID +
				"\nRegister number: " + registerNumber +
				"\nDate: " + saleDate +
				"\nSale amount: " + saleAmount);
	}
	
	public int getStoreID() {
		return storeID;
	}
	
	public float getSaleAmount() {
		return saleAmount;
	}
	
	public int getMonth() {
		return Integer.parseInt(saleDate.substring(0, 2));
	}

}
