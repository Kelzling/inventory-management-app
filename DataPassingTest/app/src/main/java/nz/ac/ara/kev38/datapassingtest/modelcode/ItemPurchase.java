package nz.ac.ara.kev38.datapassingtest.modelcode;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ItemPurchase {
	private LocalDate date;
	private double quantity;
	private BigDecimal costPerUnit;
	
	public ItemPurchase(LocalDate purchaseDate, double quantity, BigDecimal costPerUnit)
	{
		this.date = purchaseDate;
		this.quantity = quantity;
		this.costPerUnit = costPerUnit;
	}
	
	public ItemPurchase(String purchaseDate, double quantity, String costPerUnit)
	{
		this.date = LocalDate.parse(purchaseDate);
		this.quantity = quantity;
		this.costPerUnit = new BigDecimal(costPerUnit);
	}
	
	public LocalDate getDate()
	{
		return this.date;
	}
	
	public double getQuantity()
	{
		return this.quantity;
	}
	
	public BigDecimal getCostPerUnit()
	{
		return this.costPerUnit;
	}
	
	public BigDecimal getTotalCost()
	{
		return BigDecimal.valueOf(this.quantity).multiply(this.costPerUnit);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((costPerUnit == null) ? 0 : costPerUnit.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		long temp;
		temp = Double.doubleToLongBits(quantity);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		ItemPurchase other = (ItemPurchase) obj;
		if (costPerUnit == null) {
			if (other.costPerUnit != null)
				return false;
		} else if (!costPerUnit.equals(other.costPerUnit))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (Double.doubleToLongBits(quantity) != Double.doubleToLongBits(other.quantity))
			return false;
		return true;
	}
}
