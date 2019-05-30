package nz.ac.ara.kev38.datapassingtest.modelcode;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public class Item implements Parcelable {
	private UUID uniqueID;
	private String name;
	private String unit;
	private double quantity;
	private double restockThreshold;
	private double onSaleThreshold;
	private String otherInfo;
	private ArrayList<Tag> myTags = new ArrayList<Tag>();

	public Item(String newName, String newUnit, Double initialQuantity, double newRestockThreshold, double newOnSaleThreshold, String newOtherInfo)
	{
		this.uniqueID = UUID.randomUUID();
		this.name = newName;
		this.unit = newUnit;
		this.quantity = initialQuantity;
		this.restockThreshold = newRestockThreshold;
		this.onSaleThreshold = newOnSaleThreshold;
		this.otherInfo = newOtherInfo;
	}

	public Item(String newName, String newUnit, Double initialQuantity, double newRestockThreshold, double newOnSaleThreshold, String newOtherInfo, ArrayList<Tag> initialTags)
	{
		this.uniqueID = UUID.randomUUID();
		this.name = newName;
		this.unit = newUnit;
		this.quantity = initialQuantity;
		this.restockThreshold = newRestockThreshold;
		this.onSaleThreshold = newOnSaleThreshold;
		this.otherInfo = newOtherInfo;
		this.myTags.addAll(initialTags);
	}

	public Item(String itemID, String newName, String newUnit, Double initialQuantity, double newRestockThreshold, double newOnSaleThreshold, String newOtherInfo, ArrayList<Tag> initialTags)
	{
		this.uniqueID = UUID.fromString(itemID);
		this.name = newName;
		this.unit = newUnit;
		this.quantity = initialQuantity;
		this.restockThreshold = newRestockThreshold;
		this.onSaleThreshold = newOnSaleThreshold;
		this.otherInfo = newOtherInfo;
		this.myTags.addAll(initialTags);
	}

	protected Item(Parcel in) {
		name = in.readString();
		unit = in.readString();
		quantity = in.readDouble();
		restockThreshold = in.readDouble();
		onSaleThreshold = in.readDouble();
		otherInfo = in.readString();
		myTags = in.createTypedArrayList(Tag.CREATOR);
	}

	public static final Creator<Item> CREATOR = new Creator<Item>() {
		@Override
		public Item createFromParcel(Parcel in) {
			return new Item(in);
		}

		@Override
		public Item[] newArray(int size) {
			return new Item[size];
		}
	};

	public void setName(String newName)
	{
		this.name = newName;
	}

	public void setUnit(String newUnit)
	{
		this.unit = newUnit;
	}

	public void setQuantity(double newQuantity)
	{
		this.quantity = newQuantity;
	}

	public void setRestockThreshold(double newRestockThreshold)
	{
		this.restockThreshold = newRestockThreshold;
	}

	public void setOnSaleThreshold(double newOnSaleThreshold)
	{
		this.onSaleThreshold = newOnSaleThreshold;
	}

	public void setOtherInfo(String newOtherInfo)
	{
		this.otherInfo = newOtherInfo;
	}

	public void addTag(Tag aTag)
	{
		this.myTags.add(aTag);
	}

	public void addTags(ArrayList<Tag> newTags)
	{
		this.myTags.addAll(newTags);
	}

	public void removeTag(Tag oldTag)
	{
		this.myTags.remove(oldTag);
	}

	public void modifyQuantity(double changeValue)
	{
		this.quantity += changeValue;
	}

	public String getUniqueID()
	{
		return this.uniqueID.toString();
	}

	public String getName()
	{
		return this.name;
	}

	public String getUnit()
	{
		return this.unit;
	}

	public ArrayList<Tag> getTags()
	{
		return this.myTags;
	}

	public Double getQuantity()
	{
		return this.quantity;
	}

	public Double getRestockThreshold()
	{
		return this.restockThreshold;
	}

	public Double getOnSaleThreshold()
	{
		return this.onSaleThreshold;
	}

	public String getOtherInfo()
	{
		return this.otherInfo;
	}

	public ArrayList<ItemPurchase> getPurchaseHistory() throws FileNotFoundException
	{
		PurchaseHistory historyManager = new PurchaseHistory(this);
		ArrayList<ItemPurchase> myHistory = historyManager.getPurchaseHistory();

		return myHistory;
	}

	public ArrayList<ItemPurchase> getPurchaseHistory(LocalDate newStartDate, LocalDate newEndDate) throws FileNotFoundException
	{
		PurchaseHistory historyManager = new PurchaseHistory(this);
		ArrayList<ItemPurchase> myHistory = historyManager.getPurchaseHistory(newStartDate, newEndDate);

		return myHistory;
	}

	public boolean isBelowRestockThreshold()
	{
		return this.quantity < this.restockThreshold;
	}

	public boolean isBelowOnSaleThreshold()
	{
		return this.quantity < this.onSaleThreshold;
	}

	public boolean hasTag(Tag aTag)
	{
		return this.myTags.contains(aTag);
	}
	/*
	public void purchaseItem(double quantity, BigDecimal cost) throws IOException
	{
		PurchaseHistory.logPurchase(this.getUniqueID(), LocalDate.now(), quantity, cost);
		this.modifyQuantity(quantity);
	} */

	public String toString()
	{
		String myString = this.uniqueID + "," + this.name + "," + this.unit + "," + this.quantity + "," + this.restockThreshold + "," + this.onSaleThreshold + "," + this.otherInfo + ",";

		for (Tag aTag : myTags) {
			myString += aTag.getName() + "-";
		}

		return myString;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		long temp;
		temp = Double.doubleToLongBits(onSaleThreshold);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((otherInfo == null) ? 0 : otherInfo.hashCode());
		temp = Double.doubleToLongBits(quantity);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(restockThreshold);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((uniqueID == null) ? 0 : uniqueID.hashCode());
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
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
		Item other = (Item) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Double.doubleToLongBits(onSaleThreshold) != Double.doubleToLongBits(other.onSaleThreshold))
			return false;
		if (otherInfo == null) {
			if (other.otherInfo != null)
				return false;
		} else if (!otherInfo.equals(other.otherInfo))
			return false;
		if (Double.doubleToLongBits(quantity) != Double.doubleToLongBits(other.quantity))
			return false;
		if (Double.doubleToLongBits(restockThreshold) != Double.doubleToLongBits(other.restockThreshold))
			return false;
		if (uniqueID == null) {
			if (other.uniqueID != null)
				return false;
		} else if (!uniqueID.equals(other.uniqueID))
			return false;
		if (unit == null) {
			if (other.unit != null)
				return false;
		} else if (!unit.equals(other.unit))
			return false;
		return true;
	}

	@Override
	public int describeContents() {
		return hashCode();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeString(unit);
		dest.writeDouble(quantity);
		dest.writeDouble(restockThreshold);
		dest.writeDouble(onSaleThreshold);
		dest.writeString(otherInfo);
		dest.writeTypedList(myTags);
	}
}