package nz.ac.ara.kev38.inventorymanager.modelcode;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class FileSaver {
	public static final String FILE_EXTENSION = ".txt";
	
	public static void writeToFile(String filesDir, ISaveable contentToSave) throws IOException
	{
		String directoryPath = "/" + contentToSave.getDirectory() + "/";
		/*File theDirectory = new File(directoryPath);
		 if (!theDirectory.exists()) {
			theDirectory.mkdirs();
		} */
		String filePath = directoryPath + contentToSave.getFileID() + FileSaver.FILE_EXTENSION;
		// Path filePath = FileSystems.getDefault().getPath(directoryPath, contentToSave.getFileID() + FileSaver.FILE_EXTENSION);
		File theFile = new File(filesDir + filePath);
		String fileContent = contentToSave.convertToSaveable();

		if (!theFile.getParentFile().exists()) {
			theFile.getParentFile().mkdirs();
		}
		theFile.createNewFile();
		BufferedWriter writer = new BufferedWriter(new FileWriter(theFile));
		writer.write(fileContent);
		writer.close();
	}
	
	public static void appendToFile(String filePath, String fileName, String contentToWrite) throws IOException
	{
		File theDirectory = new File(filePath);
		if (!theDirectory.exists()) {
			theDirectory.mkdirs();
		}
		BufferedWriter appendingWriter = new BufferedWriter(new FileWriter(filePath + fileName, true));
		appendingWriter.append(contentToWrite);
		appendingWriter.newLine();
		appendingWriter.close();
	}
}
