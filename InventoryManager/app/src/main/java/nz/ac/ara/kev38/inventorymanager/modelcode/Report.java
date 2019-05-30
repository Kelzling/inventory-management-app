package nz.ac.ara.kev38.inventorymanager.modelcode;

import java.time.LocalDate;
import java.util.TreeMap;

public class Report {
	private TreeMap<LocalDate, Double> rawValues;
	private TreeMap<LocalDate, Double> percentagePriceChangeOverTime;
	private TreeMap<LocalDate, Double> absolutePriceChangeOverTime;
	private Double cheapestPurchasePrice;
	private Double mostExpensivePurchasePrice;
	private Double averagePurchasePrice;
	
	public Report(TreeMap<LocalDate, Double> rawValues, TreeMap<LocalDate, Double> percentageChange, TreeMap<LocalDate, Double> absoluteChange, Double cheapestPrice, Double expensivePrice, Double averagePrice)
	{
		this.rawValues = rawValues;
		this.percentagePriceChangeOverTime = percentageChange;
		this.absolutePriceChangeOverTime = absoluteChange;
		this.cheapestPurchasePrice = cheapestPrice;
		this.mostExpensivePurchasePrice = expensivePrice;
		this.averagePurchasePrice = averagePrice;
	}
	
	public TreeMap<LocalDate, Double> getRawValues()
	{
		return this.rawValues;
	}
	
	public TreeMap<LocalDate, Double> getPercentagePriceChangeOverTime()
	{
		return this.percentagePriceChangeOverTime;
	}
	
	public TreeMap<LocalDate, Double> getAbsolutePriceChangeOverTime() {
		return this.absolutePriceChangeOverTime;
	}

	public Double getCheapestPurchasePrice() {
		return this.cheapestPurchasePrice;
	}

	public Double getMostExpensivePurchasePrice() {
		return this.mostExpensivePurchasePrice;
	}

	public Double getAveragePurchasePrice() {
		return this.averagePurchasePrice;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((absolutePriceChangeOverTime == null) ? 0 : absolutePriceChangeOverTime.hashCode());
		result = prime * result + ((averagePurchasePrice == null) ? 0 : averagePurchasePrice.hashCode());
		result = prime * result + ((cheapestPurchasePrice == null) ? 0 : cheapestPurchasePrice.hashCode());
		result = prime * result + ((mostExpensivePurchasePrice == null) ? 0 : mostExpensivePurchasePrice.hashCode());
		result = prime * result
				+ ((percentagePriceChangeOverTime == null) ? 0 : percentagePriceChangeOverTime.hashCode());
		result = prime * result + ((rawValues == null) ? 0 : rawValues.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Report other = (Report) obj;
		if (absolutePriceChangeOverTime == null) {
			if (other.absolutePriceChangeOverTime != null)
				return false;
		} else if (!absolutePriceChangeOverTime.equals(other.absolutePriceChangeOverTime))
			return false;
		if (averagePurchasePrice == null) {
			if (other.averagePurchasePrice != null)
				return false;
		} else if (!averagePurchasePrice.equals(other.averagePurchasePrice))
			return false;
		if (cheapestPurchasePrice == null) {
			if (other.cheapestPurchasePrice != null)
				return false;
		} else if (!cheapestPurchasePrice.equals(other.cheapestPurchasePrice))
			return false;
		if (mostExpensivePurchasePrice == null) {
			if (other.mostExpensivePurchasePrice != null)
				return false;
		} else if (!mostExpensivePurchasePrice.equals(other.mostExpensivePurchasePrice))
			return false;
		if (percentagePriceChangeOverTime == null) {
			if (other.percentagePriceChangeOverTime != null)
				return false;
		} else if (!percentagePriceChangeOverTime.equals(other.percentagePriceChangeOverTime))
			return false;
		if (rawValues == null) {
			if (other.rawValues != null)
				return false;
		} else if (!rawValues.equals(other.rawValues))
			return false;
		return true;
	}
}
