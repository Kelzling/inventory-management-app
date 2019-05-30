package nz.ac.ara.kev38.inventorymanager.modelcode;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
public class PurchaseHistoryLoader extends FileLoader {
	public PurchaseHistoryLoader(String filesDir, String fileID) throws FileNotFoundException
	{
		super(filesDir, fileID);
	}
	
	@Override
	protected String getFilePath(String fileID) {
		// find file in purchase history folder
		String path = this.filesDir + "\\Data\\PurchaseHistory\\" + fileID + this.fileExtension;
		
		return path;
	}

	@Override
	public ArrayList<ItemPurchase> createObjects() {
		ArrayList<ItemPurchase> purchaseHistory = new ArrayList<ItemPurchase>();
		
		for (String csvString : this.rawData) {
			String[] itemPurchaseData = csvString.split(",");
			
			LocalDate purchaseDate = LocalDate.parse(itemPurchaseData[0]);
			double quantity = Double.parseDouble(itemPurchaseData[1]);
			BigDecimal costPerUnit = new BigDecimal(itemPurchaseData[2]);
			
			ItemPurchase newItemPurchase = new ItemPurchase(purchaseDate, quantity, costPerUnit);
			
			purchaseHistory.add(newItemPurchase);
		}

		return purchaseHistory;
	}

}
