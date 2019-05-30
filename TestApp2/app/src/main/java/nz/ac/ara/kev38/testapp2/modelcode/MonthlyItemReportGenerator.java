package nz.ac.ara.kev38.testapp2.modelcode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MonthlyItemReportGenerator extends ReportGenerator {
	private ArrayList<ItemPurchase> itemData;
	
	public MonthlyItemReportGenerator(ArrayList<ItemPurchase> theItemData)
	{
		this.itemData = theItemData;
	}
	
	@Override
	protected TreeMap<LocalDate, Double> collateData() {
		HashMap<LocalDate, ArrayList<Double>> allPricesPerMonth = new HashMap<LocalDate, ArrayList<Double>>();
		
		for(ItemPurchase aPurchase : this.itemData) {
			LocalDate purchaseMonth = aPurchase.getDate().withDayOfMonth(1);
			
			ArrayList<Double> purchasePrices = allPricesPerMonth.getOrDefault(purchaseMonth, new ArrayList<Double>());
			purchasePrices.add(aPurchase.getCostPerUnit().doubleValue());
			
			allPricesPerMonth.put(purchaseMonth, purchasePrices);
		}

		TreeMap<LocalDate, Double> averagePricePerMonth = new TreeMap<LocalDate, Double>();
		
		for(Map.Entry<LocalDate, ArrayList<Double>> monthData : allPricesPerMonth.entrySet()) {
			double monthlyAverage = Calculator.calculateMean(monthData.getValue().toArray(new Double[monthData.getValue().size()]));
			averagePricePerMonth.put(monthData.getKey(), monthlyAverage);
		}
		
		return averagePricePerMonth;
	}

}
