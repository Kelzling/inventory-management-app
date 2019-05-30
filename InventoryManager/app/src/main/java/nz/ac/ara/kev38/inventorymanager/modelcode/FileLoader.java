package nz.ac.ara.kev38.inventorymanager.modelcode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class FileLoader {
	protected ArrayList<String> rawData = new ArrayList<String>();
	protected String fileExtension = ".txt";
	protected String filesDir;
	
	public FileLoader(String filesDir, String fileID) throws FileNotFoundException
	{
		this.filesDir = filesDir;
		this.readFromFile(fileID);
	}

	// used to get the file path for each type of loader
	protected abstract String getFilePath(String fileID);
	
	// finds file in appropriate folder using the fileID, splits on new line, and inserts into rawData
	protected void readFromFile(String fileID) throws FileNotFoundException
	{
		String path = this.getFilePath(fileID);
		File theFile = new File(path);
		
		Scanner fileScanner = new Scanner(theFile);
		
		while (fileScanner.hasNextLine()) {
			this.rawData.add(fileScanner.nextLine());
		}
		
		fileScanner.close();
	}
	
	// creates all of the objects for the appropriate type of file
	public abstract <T> T createObjects();
}
