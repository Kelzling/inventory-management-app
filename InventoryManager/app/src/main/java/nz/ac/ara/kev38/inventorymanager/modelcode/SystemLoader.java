package nz.ac.ara.kev38.inventorymanager.modelcode;

import java.io.FileNotFoundException;

public class SystemLoader extends FileLoader {	
	public SystemLoader(String filesDir, String fileID) throws FileNotFoundException
	{
		super(filesDir, fileID);
	}

	@Override
	public SystemManager createObjects() {
		SystemManager theSystem = this.createSystem();
		this.createTags(theSystem);
		
		return theSystem;
	}
	
	private SystemManager createSystem()
	{
		String[] inventoryNames = this.rawData.get(0).split(",");
		
		SystemManager theSystem = new SystemManager(inventoryNames);
		
		return theSystem;
	}
	
	private void createTags(SystemManager theSystem)
	{
		for (int i = 1; i <= this.rawData.size() - 1; i++) {
			String[] tagData = this.rawData.get(i).split(",");
			String tagName = tagData[0];
			TagType tagType = TagType.valueOf(tagData[1]);
			String otherInfo = tagData[2];
			
			theSystem.addTag(tagName, tagType, otherInfo);
		}
	}

	@Override
	protected String getFilePath(String fileID) {
		return this.filesDir + "/Data/" + fileID + this.fileExtension;
	}
}
