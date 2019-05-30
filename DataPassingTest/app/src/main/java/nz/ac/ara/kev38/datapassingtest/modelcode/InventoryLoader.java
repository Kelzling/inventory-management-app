package nz.ac.ara.kev38.datapassingtest.modelcode;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class InventoryLoader extends FileLoader {
	private SystemManager theSystem;
	
	public InventoryLoader(String fileID, SystemManager theSystem) throws FileNotFoundException
	{
		super(fileID);
		this.theSystem = theSystem;
	}
	
	@Override
	protected String getFilePath(String fileID) {
		// find file in inventories folder
		String path = ".\\Data\\Inventories\\" + fileID + this.fileExtension;
		
		return path;
	}

	@Override
	public Inventory createObjects() {
		Inventory theInventory = this.createInventory();
		this.createItems(theInventory);
		
		return theInventory;
	}
	
	private Inventory createInventory()
	{
		String[] inventoryData = this.rawData.get(0).split(",");
		
		String inventoryName = inventoryData[0];
		String otherInfo = inventoryData[1];
		
		Inventory theInventory = new Inventory(inventoryName, otherInfo);
		
		return theInventory;
	}
	
	private void createItems(Inventory theInventory)
	{
		for (int i = 1; i <= this.rawData.size() - 1; i++) {
			String[] itemData = this.rawData.get(i).split(",");
			String itemID = itemData[0];
			String itemName = itemData[1];
			String itemUnit = itemData[2];
			double itemQuantity = Double.parseDouble(itemData[3]);
			double restockThreshold = Double.parseDouble(itemData[4]);
			double onSaleThreshold = Double.parseDouble(itemData[5]);
			String otherInfo = itemData[6];
			
			ArrayList<Tag> tagsList = new ArrayList<Tag>();
			if (itemData.length == 8) {
				// if there is any tag data
				String[] tagNames = itemData[7].split("-");
				for (String aTagName : tagNames) {
					Tag aTag = theSystem.getTagByName(aTagName);
					tagsList.add(aTag);
				}
			}
			
			theInventory.addItem(itemID, itemName, itemUnit, itemQuantity, restockThreshold, onSaleThreshold, otherInfo, tagsList);
		}
	}
}
