package nz.ac.ara.kev38.inventorymanager.modelcode;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class WeeklyItemReportGenerator extends ReportGenerator {
	private ArrayList<ItemPurchase> itemData;
	
	public WeeklyItemReportGenerator(ArrayList<ItemPurchase> theItemData)
	{
		this.itemData = theItemData;
	}
	
	@Override
	protected TreeMap<LocalDate, Double> collateData() {
		HashMap<LocalDate, ArrayList<Double>> allPricesPerWeek = new HashMap<LocalDate, ArrayList<Double>>();
		
		for (ItemPurchase aPurchase : this.itemData) {
			LocalDate purchaseWeek = aPurchase.getDate().with(DayOfWeek.MONDAY);
			
			ArrayList<Double> purchasePrices = allPricesPerWeek.getOrDefault(purchaseWeek, new ArrayList<Double>());
			purchasePrices.add(aPurchase.getCostPerUnit().doubleValue());
			
			allPricesPerWeek.put(purchaseWeek, purchasePrices);
		}
		
		TreeMap<LocalDate, Double> averagePricePerWeek = new TreeMap<LocalDate, Double>();
		
		for(Map.Entry<LocalDate, ArrayList<Double>> weekData : allPricesPerWeek.entrySet()) {
			double weeklyAverage = Calculator.calculateMean(weekData.getValue().toArray(new Double[weekData.getValue().size()]));
			averagePricePerWeek.put(weekData.getKey(), weeklyAverage);
		}
		
		return averagePricePerWeek;
	}

}
