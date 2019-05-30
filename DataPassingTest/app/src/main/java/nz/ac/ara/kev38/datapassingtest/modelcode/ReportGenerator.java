package nz.ac.ara.kev38.datapassingtest.modelcode;

import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

public abstract class ReportGenerator {
	protected TreeMap<LocalDate, Double> collatedData = new TreeMap<LocalDate, Double>();
	
	public Report createReport()
	{
		this.collatedData = this.collateData();
		
		TreeMap<LocalDate, Double> percentageChange = this.calculatePercentageChange();
		TreeMap<LocalDate, Double> absoluteChange = this.calculateAbsoluteChange();
		Double cheapestPrice = this.calculateCheapestPrice();
		Double mostExpensivePrice = this.calculateMostExpensivePrice();
		Double meanPrice = this.calculateMeanPrice();
		
		Report theReport = new Report(collatedData, percentageChange, absoluteChange, cheapestPrice, mostExpensivePrice, meanPrice);
		
		return theReport;
	}

	protected abstract TreeMap<LocalDate, Double> collateData();
	
	private Double[] getAllPurchasePrices()
	{
		Double[] values = this.collatedData.values().toArray(new Double[collatedData.size()]);
		
		return values;
	}
	
	protected TreeMap<LocalDate, Double> calculatePercentageChange()
	{
		// compare each value to the one before it to get the percentage change
		TreeMap<LocalDate, Double> percentageChangePerTimePeriod = new TreeMap<LocalDate, Double>();
		
		for (Map.Entry<LocalDate, Double> aMapEntry : this.collatedData.entrySet()) {
			Map.Entry<LocalDate, Double> previousEntry = this.collatedData.lowerEntry(aMapEntry.getKey());
			
			if (previousEntry != null) {
				double percentageChange = Calculator.calculatePercentageChange(previousEntry.getValue(), aMapEntry.getValue());
				percentageChangePerTimePeriod.put(aMapEntry.getKey(), percentageChange);
			}
		}
		
		return percentageChangePerTimePeriod;
	}
	
	protected TreeMap<LocalDate, Double> calculateAbsoluteChange()
	{
		// compare each value to the one before it to get the absolute change
		TreeMap<LocalDate, Double> absoluteChangePerTimePeriod = new TreeMap<LocalDate, Double>();
		
		for(Map.Entry<LocalDate, Double> aMapEntry : this.collatedData.entrySet()) {
			Map.Entry<LocalDate, Double> previousEntry = this.collatedData.lowerEntry(aMapEntry.getKey());
			
			if (previousEntry != null) {
				double absoluteChange = Calculator.calculateAbsoluteChange(previousEntry.getValue(), aMapEntry.getValue());
				absoluteChangePerTimePeriod.put(aMapEntry.getKey(), absoluteChange);
			}
		}
		
		return absoluteChangePerTimePeriod;
	}
	
	protected Double calculateCheapestPrice()
	{
		Double cheapestPrice = Calculator.calculateMinimum(this.getAllPurchasePrices());
		
		return cheapestPrice;
	}
	
	protected Double calculateMostExpensivePrice()
	{
		Double mostExpensivePrice = Calculator.calculateMaximum(this.getAllPurchasePrices());
		
		return mostExpensivePrice;
	}
	
	protected Double calculateMeanPrice()
	{
		Double meanPrice = Calculator.calculateMean(this.getAllPurchasePrices());
		
		return meanPrice;
	}
}
