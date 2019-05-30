package nz.ac.ara.kev38.testapp2.modelcode;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Inventory implements ISaveable {
	private String name;
	private String otherInfo;
	private ArrayList<Item> allMyItems = new ArrayList<Item>();
	
	public Inventory(String newName, String newOtherInfo)
	{
		this.name = newName;
		this.otherInfo = newOtherInfo;
	}
	
	public void setName(String newName)
	{
		this.name = newName;
	}
	
	public void setOtherInfo(String newOtherInfo)
	{
		this.otherInfo = newOtherInfo;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public String getOtherInfo()
	{
		return this.otherInfo;
	}
	
	public ArrayList<Item> getAllItems()
	{
		return this.allMyItems;
	}
	
	public Item getItemByID(String itemID)
	{
		Item theItem = this.allMyItems.stream()
				.filter(anItem -> anItem.getUniqueID().equals(itemID))
				.findAny()
				.orElse(null);
		
		return theItem;
	}
	
	public int getItemCount()
	{
		return this.allMyItems.size();
	}
	
	public void addItem(String newName, String newUnit, Double initialQuantity, double newRestockThreshold, double newOnSaleThreshold, String newOtherInfo)
	{
		Item newItem = new Item(newName, newUnit, initialQuantity, newRestockThreshold, newOnSaleThreshold, newOtherInfo);
		this.allMyItems.add(newItem);
	}
	
	public void addItem(String newName, String newUnit, Double initialQuantity, double newRestockThreshold, double newOnSaleThreshold, String newOtherInfo, ArrayList<Tag> initialTags)
	{
		Item newItem = new Item(newName, newUnit, initialQuantity, newRestockThreshold, newOnSaleThreshold, newOtherInfo, initialTags);
		this.allMyItems.add(newItem);
	}
	
	public void addItem(String itemID, String newName, String newUnit, Double initialQuantity, double newRestockThreshold, double newOnSaleThreshold, String newOtherInfo, ArrayList<Tag> initialTags)
	{
		Item newItem = new Item(itemID, newName, newUnit, initialQuantity, newRestockThreshold, newOnSaleThreshold, newOtherInfo, initialTags);
		this.allMyItems.add(newItem);
	}
	
	public boolean hasItem(String itemName)
	{
		// checks to see if an item with the same name already exists. It is allowable for two items with the same name to exist, but it should prompt the user to be sure that they want to do that
		Item theItem = this.allMyItems.stream()
				.filter(anItem -> anItem.getName().equalsIgnoreCase(itemName))
				.findAny()
				.orElse(null);
		
		return theItem != null;
	}
	
	public void deleteItem(String itemID) throws IOException
	{
		Item theItem = this.getItemByID(itemID);
		this.allMyItems.remove(theItem);
	}
	
	public Item[] findItemsByName(String nameSearchString)
	{
		Item[] matchingItems = this.allMyItems.stream()
				.filter(anItem -> anItem.getName().contains(nameSearchString))
				.toArray(Item[]::new);
		return matchingItems;
	}
	
	public Item[] findItemsByTag(Tag aTag)
	{
		Item[] matchingItems = this.allMyItems.stream()
				.filter(anItem -> anItem.hasTag(aTag))
				.toArray(Item[]::new);
		return matchingItems;
	}
	
	public Item[] findItemsByTags(ArrayList<Tag> tagList)
	{
		Item[] matchingItems = this.allMyItems.stream()
				.filter(anItem -> {
					boolean hasTag = true;
					for (Tag aTag : tagList) {
						if (!anItem.hasTag(aTag)) {
							hasTag = false;
							break;
						}
					}
					return hasTag;
				})
				.toArray(Item[]::new);
		
		return matchingItems;
	}
	
	public Item[] findItemsByNameAndTags(String nameSearchString, ArrayList<Tag> tagsList)
	{
		Item[] matchingItems = this.allMyItems.stream()
				.filter(anItem -> {
					// needs testing
					boolean matches = false;
					if (anItem.getName().contains(nameSearchString)) {
						matches = true;
						for (Tag aTag : tagsList) {
							if (!anItem.hasTag(aTag)) {
								matches = false;
								break;
							}
						}
					}
					return matches;
				})
				.toArray(Item[]::new);
		
		return matchingItems;
	}
	
	public Map<Tag, ArrayList<Item>> getItemsByTagType(TagType searchType)
	{
		// generates a list of each tag of that type present in the inventory, mapped to a list of items that have that tag
		HashMap<Tag, ArrayList<Item>> itemsByTagType = new HashMap<Tag, ArrayList<Item>>();
		
		for (Item anItem : allMyItems) {
			// items could appear in the lists of multiple tags, but that's ok
			ArrayList<Tag> itemTags = anItem.getTags();
			
			for (Tag aTag : itemTags) {
				if (aTag.getType() == searchType) {
					ArrayList<Item> tagItems = itemsByTagType.getOrDefault(aTag, new ArrayList<Item>());
					
					tagItems.add(anItem);
					
					itemsByTagType.put(aTag, tagItems);
				}
			}
		}
		
		return itemsByTagType;
	}
	
	public ShoppingList generateShoppingList()
	{
		ShoppingList theList = new ShoppingList(this);
		return theList;
	}
	
	public ShoppingList generateShoppingList(ArrayList<Tag> tagsList)
	{
		ShoppingList theList = new ShoppingList(this, tagsList);
		return theList;
	}
	
	public void removeTagFromItems(Tag aTag)
	{
		for (Item anItem : allMyItems) {
			if (anItem.hasTag(aTag)) {
				anItem.removeTag(aTag);
			}
		}
	}
	
	public Report generateWeeklyItemReport(String itemID) throws FileNotFoundException, InvalidItemException
	{
		Item theItem = this.getItemByID(itemID);
		
		if (theItem == null) {
			throw new InvalidItemException("Item does not exist");
		}
		
		ArrayList<ItemPurchase> purchaseData = theItem.getPurchaseHistory();
		WeeklyItemReportGenerator theReportGenerator = new WeeklyItemReportGenerator(purchaseData);
		Report theReport = theReportGenerator.createReport();
		
		return theReport;
	}
	
	public Report generateWeeklyItemReport(String itemID, LocalDate startDate, LocalDate endDate) throws FileNotFoundException, InvalidItemException
	{
		Item theItem = this.getItemByID(itemID);
		
		if (theItem == null) {
			throw new InvalidItemException("Item does not exist");
		}
		
		ArrayList<ItemPurchase> purchaseData = theItem.getPurchaseHistory(startDate, endDate);
		WeeklyItemReportGenerator theReportGenerator = new WeeklyItemReportGenerator(purchaseData);
		Report theReport = theReportGenerator.createReport();
		
		return theReport;
	}
	
	public Report generateMonthlyItemReport(String itemID) throws FileNotFoundException, InvalidItemException
	{
		Item theItem = this.getItemByID(itemID);
		
		if (theItem == null) {
			throw new InvalidItemException("Item does not exist");
		}
		
		ArrayList<ItemPurchase> purchaseData = theItem.getPurchaseHistory();
		MonthlyItemReportGenerator theReportGenerator = new MonthlyItemReportGenerator(purchaseData);
		Report theReport = theReportGenerator.createReport();
		
		return theReport;
	}
	
	public Report generateMonthlyItemReport(String itemID, LocalDate startDate, LocalDate endDate) throws FileNotFoundException, InvalidItemException
	{
		Item theItem = this.getItemByID(itemID);
		
		if (theItem == null) {
			throw new InvalidItemException("Item does not exist");
		}
		
		ArrayList<ItemPurchase> purchaseData = theItem.getPurchaseHistory(startDate, endDate);
		MonthlyItemReportGenerator theReportGenerator = new MonthlyItemReportGenerator(purchaseData);
		Report theReport = theReportGenerator.createReport();
		
		return theReport;
	}
	
	public Report generateWeeklyInventoryReport()
	{
		HashMap<Item, ArrayList<ItemPurchase>> purchaseData = new HashMap<Item, ArrayList<ItemPurchase>>();
		
		for (Item anItem : this.allMyItems) {
			ArrayList<ItemPurchase> itemData;
			try {
				itemData = anItem.getPurchaseHistory();
				purchaseData.put(anItem, itemData);
			} catch (FileNotFoundException e) {
				// skip item
			}
		}
		
		WeeklyInventoryReportGenerator theReportGenerator = new WeeklyInventoryReportGenerator(purchaseData);
		Report theReport = theReportGenerator.createReport();
		
		return theReport;
	}
	
	public Report generateWeeklyInventoryReport(LocalDate startDate, LocalDate endDate)
	{
		HashMap<Item, ArrayList<ItemPurchase>> purchaseData = new HashMap<Item, ArrayList<ItemPurchase>>();
		
		for (Item anItem : this.allMyItems) {
			ArrayList<ItemPurchase> itemData;
			try {
				itemData = anItem.getPurchaseHistory(startDate, endDate);
				purchaseData.put(anItem, itemData);
			} catch (FileNotFoundException e) {
				// skip item
			}
		}
		
		WeeklyInventoryReportGenerator theReportGenerator = new WeeklyInventoryReportGenerator(purchaseData);
		Report theReport = theReportGenerator.createReport();
		
		return theReport;
	}
	
	public Report generateMonthlyInventoryReport()
	{
		HashMap<Item, ArrayList<ItemPurchase>> purchaseData = new HashMap<Item, ArrayList<ItemPurchase>>();
		
		for (Item anItem : this.allMyItems) {
			ArrayList<ItemPurchase> itemData;
			try {
				itemData = anItem.getPurchaseHistory();
				purchaseData.put(anItem, itemData);
			} catch (FileNotFoundException e) {
				// skip item
			}
		}
		
		MonthlyInventoryReportGenerator theReportGenerator = new MonthlyInventoryReportGenerator(purchaseData);
		Report theReport = theReportGenerator.createReport();
		
		return theReport;
	}
	
	public Report generateMonthlyInventoryReport(LocalDate startDate, LocalDate endDate)
	{
		HashMap<Item, ArrayList<ItemPurchase>> purchaseData = new HashMap<Item, ArrayList<ItemPurchase>>();
		
		for (Item anItem : this.allMyItems) {
			ArrayList<ItemPurchase> itemData;
			try {
				itemData = anItem.getPurchaseHistory(startDate, endDate);
				purchaseData.put(anItem, itemData);
			} catch (FileNotFoundException e) {
				// skip item
			}
		}
		
		MonthlyInventoryReportGenerator theReportGenerator = new MonthlyInventoryReportGenerator(purchaseData);
		Report theReport = theReportGenerator.createReport();
		
		return theReport;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((allMyItems == null) ? 0 : allMyItems.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((otherInfo == null) ? 0 : otherInfo.hashCode());
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
		Inventory other = (Inventory) obj;
		if (allMyItems == null) {
			if (other.allMyItems != null)
				return false;
		} else if (!allMyItems.equals(other.allMyItems))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (otherInfo == null) {
			if (other.otherInfo != null)
				return false;
		} else if (!otherInfo.equals(other.otherInfo))
			return false;
		return true;
	}
	

	@Override
	public String getFileID() {
		return this.getName();
	}

	@Override
	public String convertToSaveable() {
		String myString = this.getName() + "," + this.getOtherInfo();
		final String NEW_LINE = "\r\n";
		
		for (Item anItem : this.allMyItems) {
			myString += NEW_LINE + anItem.toString();
		}
		
		return myString;
	}

	@Override
	public String getDirectory() {
		return "Data/Inventories";
	}

}
