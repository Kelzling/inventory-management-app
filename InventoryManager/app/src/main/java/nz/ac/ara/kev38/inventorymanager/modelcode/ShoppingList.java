package nz.ac.ara.kev38.inventorymanager.modelcode;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

public class ShoppingList {
	private ArrayList<ListItem> neededItems = new ArrayList<ListItem>();
	private ArrayList<ListItem> onSaleItems = new ArrayList<ListItem>();
	private ArrayList<ListItem> extraItems = new ArrayList<ListItem>();
	private Inventory myInventory;
	
	public ShoppingList(Inventory theInventory)
	{
		this.myInventory = theInventory;
		this.generateShoppingList();
	}
	
	public ShoppingList(Inventory theInventory, ArrayList<Tag> tagsList) {
		this.myInventory = theInventory;
		this.generateShoppingList(tagsList);
	}

	public ArrayList<ListItem> getNeededItems() {
		return this.neededItems;
	}

	public ArrayList<ListItem> getOnSaleItems() {
		return this.onSaleItems;
	}

	public ArrayList<ListItem> getExtraItems() {
		return this.extraItems;
	}
	
	public ArrayList<ListItem> getAllItems() {
		ArrayList<ListItem> allItems = new ArrayList<ListItem>();
		
		allItems.addAll(neededItems);
		allItems.addAll(onSaleItems);
		allItems.addAll(extraItems);
		
		return allItems;
	}
	
	private void generateShoppingList()
	{
		// needs testing
		ArrayList<Item> allItems = this.myInventory.getAllItems();
		
		for(Item anItem : allItems) {
			if (anItem.isBelowRestockThreshold()) {
				this.neededItems.add(new ListItem(anItem));
			} else if (anItem.isBelowOnSaleThreshold()) {
				this.onSaleItems.add(new ListItem(anItem));
			}
		}
	}
	
	private void generateShoppingList(ArrayList<Tag> tagsList)
	{
		// needs testing
		ArrayList<Item> matchingItems = new ArrayList<Item>();
		for (Tag aTag : tagsList) {
			Item[] matchingItemsForTag = this.myInventory.findItemsByTag(aTag);
			matchingItems.addAll(Arrays.asList(matchingItemsForTag));
		}
		
		for (Item anItem : matchingItems) {
			if (anItem.isBelowRestockThreshold()) {
				this.neededItems.add(new ListItem(anItem));
			} else if (anItem.isBelowOnSaleThreshold()) {
				this.onSaleItems.add(new ListItem(anItem));
			}
		}
	}
	
	public void addItemToList(Item newItem) throws InvalidItemException
	{
		if (this.myInventory.getItemByID(newItem.getUniqueID()) != null) {
			ListItem newListItem = new ListItem(newItem);
			this.extraItems.add(newListItem);
		} else {
			throw new InvalidItemException("Item must be from this inventory");
		}
	}
	
	public void addItemToList(Item newItem, double quantity) throws InvalidItemException
	{
		if (this.myInventory.getItemByID(newItem.getUniqueID()) != null) {
			ListItem newListItem = new ListItem(newItem, quantity);
			this.extraItems.add(newListItem);
		} else {
			throw new InvalidItemException("Item must be from this inventory");
		}
	}
	
	public void addItemToList(Item newItem, double quantity, boolean inCart) throws InvalidItemException
	{
		if (this.myInventory.getItemByID(newItem.getUniqueID()) != null) {
			ListItem newListItem = new ListItem(newItem, quantity, inCart);
			this.extraItems.add(newListItem);
		} else {
			throw new InvalidItemException("Item must be from this inventory");
		}
	}
	
	public void addItemToList(Item newItem, double quantity, BigDecimal purchasePrice) throws InvalidItemException, IOException
	{
		if (this.myInventory.getItemByID(newItem.getUniqueID()) != null) {
			newItem.purchaseItem(quantity, purchasePrice);
			ListItem newListItem = new ListItem(newItem, quantity, purchasePrice);
			this.extraItems.add(newListItem);
		} else {
			throw new InvalidItemException("Item must be from this inventory");
		}
	}
	
	public void removeItemFromNeeded(Item theItem)
	{
		this.neededItems.removeIf(aListItem -> aListItem.getItem().equals(theItem));
	}
	
	public void removeItemFromOnSale(Item theItem)
	{
		this.onSaleItems.removeIf(aListItem -> aListItem.getItem().equals(theItem));
	}
	
	public void removeItemFromExtra(Item theItem)
	{
		this.extraItems.removeIf(aListItem -> aListItem.getItem().equals(theItem));
	}
	
	public void checkout()
	{
		ArrayList<ListItem> allItems = this.getAllItems();
		for (ListItem aListItem : allItems) {
			if (aListItem.isInCart()) {
				try {
					aListItem.getItem().purchaseItem(aListItem.getQuantity(), aListItem.getPurchasePrice());
					aListItem.setPurchased(true);
				} catch (IOException e) {
					// skip item. Future to do when there is a full application: Display something to the user if some items were not successfully purchased somehow
				}
			}
		}
	}
}
