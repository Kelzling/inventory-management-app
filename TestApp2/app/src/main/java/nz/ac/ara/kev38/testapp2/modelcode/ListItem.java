package nz.ac.ara.kev38.testapp2.modelcode;

import java.math.BigDecimal;
import java.util.Objects;

public class ListItem {
	private Item theItem;
	private double quantity;
	private boolean inCart;
	private boolean purchased;
	private BigDecimal purchasePrice;
	
	public ListItem(Item theItem)
	{
		this.theItem = theItem;
		this.quantity = 0;
		this.inCart = false;
	}
	
	public ListItem(Item theItem, double theQuantity)
	{
		this.theItem = theItem;
		this.quantity = theQuantity;
		this.inCart = false;
	}
	
	public ListItem(Item theItem, double theQuantity, boolean isInCart)
	{
		this.theItem = theItem;
		this.quantity = theQuantity;
		this.inCart = isInCart;
	}
	
	public ListItem(Item theItem, double theQuantity, BigDecimal purchasePrice)
	{
		// if we are using this one we are presuming the item has already been purchased?
		this.theItem = theItem;
		this.quantity = theQuantity;
		this.inCart = false;
		this.purchased = true;
		this.purchasePrice = purchasePrice;
	}

	public Item getItem()
	{
		return this.theItem;
	}

	public double getQuantity()
	{
		return this.quantity;
	}

	public boolean isInCart()
	{
		return this.inCart;
	}
	
	public boolean isPurchased()
	{
		return this.purchased;
	}
	
	public BigDecimal getPurchasePrice()
	{
		return this.purchasePrice;
	}
	
	public void setQuantity(double newQuantity) {
		this.quantity = newQuantity;
	}

	public void setInCart(boolean newState) {
		this.inCart = newState;
	}
	
	public void setPurchased(boolean newState)
	{
		this.purchased = newState;
		if (this.purchased) {
			this.setInCart(false);
		}
	}
	
	public void setPurchasePrice(BigDecimal newPrice)
	{
		this.purchasePrice = newPrice;
	}
	
	public void modifyQuantity(double changeValue)
	{
		this.quantity += changeValue;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ListItem other = (ListItem) obj;
		if (inCart != other.inCart)
			return false;
		if (purchasePrice == null) {
			if (other.purchasePrice != null)
				return false;
		} else if (!purchasePrice.equals(other.purchasePrice))
			return false;
		if (purchased != other.purchased)
			return false;
		if (Double.doubleToLongBits(quantity) != Double.doubleToLongBits(other.quantity))
			return false;
		if (theItem == null) {
			if (other.theItem != null)
				return false;
		} else if (!theItem.equals(other.theItem))
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (inCart ? 1231 : 1237);
		result = prime * result + ((purchasePrice == null) ? 0 : purchasePrice.hashCode());
		result = prime * result + (purchased ? 1231 : 1237);
		long temp;
		temp = Double.doubleToLongBits(quantity);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((theItem == null) ? 0 : theItem.hashCode());
		return result;
	}
}
