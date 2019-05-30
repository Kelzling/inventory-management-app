package nz.ac.ara.kev38.datapassingtest.modelcode;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class WeeklyInventoryReportGenerator extends ReportGenerator {
	private HashMap<Item, ArrayList<ItemPurchase>> inventoryData;
	
	public WeeklyInventoryReportGenerator(HashMap<Item, ArrayList<ItemPurchase>> theInventoryData)
	{
		this.inventoryData = theInventoryData;
	}
	
	@Override
	protected TreeMap<LocalDate, Double> collateData() {
		TreeMap<LocalDate, Double> totalSpendPerWeek = new TreeMap<LocalDate, Double>();
		
		for (ArrayList<ItemPurchase> anItemData : this.inventoryData.values()) {
			for (ItemPurchase aPurchase : anItemData) {
				double totalPrice = aPurchase.getCostPerUnit().multiply(new BigDecimal(aPurchase.getQuantity())).doubleValue();
				LocalDate firstDayOfWeek = aPurchase.getDate().with(DayOfWeek.MONDAY);
				totalSpendPerWeek.compute(firstDayOfWeek, (key, currentValue) -> (currentValue == null) ? totalPrice : currentValue + totalPrice);
			}
		}

		return totalSpendPerWeek;
	}

}
