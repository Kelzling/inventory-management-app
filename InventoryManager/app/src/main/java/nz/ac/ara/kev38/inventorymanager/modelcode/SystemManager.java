package nz.ac.ara.kev38.inventorymanager.modelcode;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class SystemManager implements ISaveable, Parcelable {
	public final static String SYSTEM_ID = "THE_SYSTEM";
	private String filesDir;
	private ArrayList<Tag> allMyTags = new ArrayList<>();
	private ArrayList<String> allMyInventoryNames = new ArrayList<>();
	private Inventory currentInventory;
	
	public SystemManager() {};
	
	public SystemManager(String[] inventoryNames)
	{
		this.allMyInventoryNames.addAll(Arrays.asList(inventoryNames));
	}

	protected SystemManager(Parcel in) {
		allMyTags = in.createTypedArrayList(Tag.CREATOR);
		allMyInventoryNames = in.createStringArrayList();
		filesDir = in.readString();
	}

	public static final Creator<SystemManager> CREATOR = new Creator<SystemManager>() {
		@Override
		public SystemManager createFromParcel(Parcel in) {
			return new SystemManager(in);
		}

		@Override
		public SystemManager[] newArray(int size) {
			return new SystemManager[size];
		}
	};

	public void saveState()
	{
		try {
			this.saveCurrentInventory();
			FileSaver.writeToFile(this.filesDir, this);
		} catch (IOException e) {
			// something really screwed up and I'm not sure how best to handle that right now besides eventually displaying something to the user
			e.printStackTrace();
		}
	}
	
	public void saveCurrentInventory()
	{
		try {
			if (this.currentInventory != null) {
				FileSaver.writeToFile(this.filesDir, this.currentInventory);
			}
		} catch (IOException e) {
			// // something really screwed up and I'm not sure how best to handle that right now besides eventually displaying something to the user
			e.printStackTrace();
		}
	}

	public void setFilesDir(String newFilesDir) {
		filesDir = newFilesDir;
	}

	public String getFilesDir() {
		return filesDir;
	}
	
	public Inventory getInventory(String inventoryName) throws InventoryNotFoundException
	{
		if (this.currentInventory == null || !this.currentInventory.getName().equals(inventoryName)) {
			this.saveCurrentInventory();
			this.currentInventory = this.loadInventory(inventoryName);
		}
		return this.currentInventory;
	}
	
	private Inventory loadInventory(String inventoryName) throws InventoryNotFoundException
	{
		if (this.hasInventory(inventoryName)) {
			try {
				InventoryLoader theLoader = new InventoryLoader(this.filesDir, inventoryName, this);
				Inventory theInventory = theLoader.createObjects();
				return theInventory;
			} catch (FileNotFoundException ex) {
				throw new InventoryNotFoundException();
			}
		} else {
			// some form of I don't have that inventory action? throw an exception?
			throw new InventoryNotFoundException();
		}
	}
	
	public ArrayList<String> getInventoryNames()
	{
		return this.allMyInventoryNames;
	}
	
	public void addInventory(String name, String otherInfo) {
		Inventory newInventory = new Inventory(name, otherInfo);
		try {
			FileSaver.writeToFile(this.filesDir, newInventory); // test this to see what happens when it writes to file with no items etc, curious
			this.allMyInventoryNames.add(name);
			this.saveState();
		} catch (IOException e) {
			// something really screwed up and I'm not sure how best to handle that right now besides eventually displaying something to the user
			e.printStackTrace();
		}
	}
	
	public boolean hasInventory(String inventoryName)
	{
		boolean hasInventory = this.allMyInventoryNames.contains(inventoryName);
		
		return hasInventory;
	}
	
	public void addTag(String tagName, TagType type, String otherInfo)
	{
		Tag newTag = new Tag(tagName, type, otherInfo);
		this.allMyTags.add(newTag);
	}
	
	public Tag getTagByName(String tagName)
	{
		Tag theTag = this.allMyTags.stream()
				.filter(aTag -> aTag.getName().equalsIgnoreCase(tagName))
				.findAny()
				.get();
				
		
		return theTag;
	}
	
	public ArrayList<Tag> getTagsByType(TagType tagType)
	{
		Tag[] matchingTags = this.allMyTags.stream()
				.filter(aTag -> aTag.getType() == tagType)
				.toArray(Tag[]::new);
		
		ArrayList<Tag> tagsList = new ArrayList<Tag>(Arrays.asList(matchingTags));
		
		return tagsList;		
	}
	
	public void removeTag(Tag theTag)
	{
		for (String anInventoryName : this.allMyInventoryNames) {
			try {
				Inventory anInventory = this.loadInventory(anInventoryName);
				anInventory.removeTagFromItems(theTag);
				FileSaver.writeToFile(this.filesDir, anInventory);
			} catch (InventoryNotFoundException e) {
				// no need to remove the tag then
			} catch (IOException e) {
				// something really screwed up and I'm not sure how best to handle that right now besides eventually displaying something to the user
				e.printStackTrace();
			}
		}
		this.allMyTags.remove(theTag);
	}
	
	public void removeInventory(String inventoryName)
	{
		// to do - decide whether you remove the file somehow??
		this.allMyInventoryNames.remove(inventoryName);
		this.saveState();
	}

	@Override
	public String getDirectory() {
		return "Data";
	}

	@Override
	public String getFileID() {
		return SystemManager.SYSTEM_ID;
	}

	@Override
	public String convertToSaveable() {
		String myString = "";
		final String NEW_LINE = "\r\n";
		
		for (String name : this.allMyInventoryNames) {
			myString += name + ",";
		}
		
		for (Tag aTag : this.allMyTags) {
			myString += NEW_LINE + aTag.toString();
		}
		
		return myString;
	}

	@Override
	public int describeContents() {
		return hashCode();
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeParcelableArray(allMyTags.toArray(new Tag[allMyTags.size()]), i);
		parcel.writeStringList(allMyInventoryNames);
		parcel.writeString(filesDir);
	}
}
