package nz.ac.ara.kev38.datapassingtest.modelcode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class MonthlyInventoryReportGenerator extends ReportGenerator {
	private HashMap<Item, ArrayList<ItemPurchase>> inventoryData;
	
	public MonthlyInventoryReportGenerator(HashMap<Item, ArrayList<ItemPurchase>> theInventoryData)
	{
		this.inventoryData = theInventoryData;
	}
	
	@Override
	protected TreeMap<LocalDate, Double> collateData() {
		// iterate over each set of data and add the total purchase price for that purchase to the appropriate month
		TreeMap<LocalDate, Double> totalSpendPerMonth = new TreeMap<LocalDate, Double>();
		
		for (ArrayList<ItemPurchase> anItemData : this.inventoryData.values()) {
			for (ItemPurchase aPurchase : anItemData) {
				// maybe this maintains some precision? idk
				double totalPrice = aPurchase.getCostPerUnit().multiply(new BigDecimal(aPurchase.getQuantity())).doubleValue();
				totalSpendPerMonth.compute(aPurchase.getDate().withDayOfMonth(1), (key, currentValue) -> (currentValue == null) ? totalPrice : currentValue + totalPrice);
			}
			
		}
		
		return totalSpendPerMonth;
	}

}
