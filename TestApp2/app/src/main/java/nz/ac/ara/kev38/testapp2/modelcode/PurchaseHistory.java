package nz.ac.ara.kev38.testapp2.modelcode;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

public class PurchaseHistory {
	private Item theItem;
	private final static String LOG_DIRECTORY = "Data\\PurchaseHistory";
	private ArrayList<ItemPurchase> myPurchases = new ArrayList<ItemPurchase>();
	
	public PurchaseHistory(Item anItem) throws FileNotFoundException
	{
		this.theItem = anItem;
		this.loadPurchaseHistory();
	}
	
	public static void logPurchase(String itemID, LocalDate purchaseDate, double quantity, BigDecimal cost) throws IOException
	{
		String filePath = ".\\" + PurchaseHistory.LOG_DIRECTORY + "\\";
		String fileName = itemID + FileSaver.FILE_EXTENSION;
		// create CSV string for purchase
		String csvString = purchaseDate.toString() + "," +  quantity + "," + (cost != null ? cost.toPlainString() : "0.0");
		FileSaver.appendToFile(filePath, fileName, csvString);
	}
	
	private void loadPurchaseHistory() throws FileNotFoundException
	{
		PurchaseHistoryLoader myLoader = new PurchaseHistoryLoader(theItem.getUniqueID());
		this.myPurchases = myLoader.createObjects();
	}
		
	public ArrayList<ItemPurchase> getPurchaseHistory()
	{
		return this.myPurchases;
	}
	
	public ArrayList<ItemPurchase> getPurchaseHistory(LocalDate newStartDate, LocalDate newEndDate)
	{
		// filter this.myPurchases
		ArrayList<ItemPurchase> filteredPurchases = (ArrayList<ItemPurchase>) this.myPurchases.clone(); // see why it insists on casting to object when using clone and if this is preventable
		LocalDate startDate = newStartDate != null ? newStartDate : LocalDate.MIN; // far in the past date
		LocalDate endDate = newEndDate != null ? newEndDate : LocalDate.now();
		
		filteredPurchases.removeIf(anItemPurchase -> {
			LocalDate purchaseDate = anItemPurchase.getDate();
			return purchaseDate.isBefore(startDate) || purchaseDate.isAfter(endDate);
		});
		
		return filteredPurchases;
	}
}
