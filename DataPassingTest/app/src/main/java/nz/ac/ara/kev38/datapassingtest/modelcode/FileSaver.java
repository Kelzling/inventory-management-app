package nz.ac.ara.kev38.datapassingtest.modelcode;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileSaver {
	public static final String FILE_EXTENSION = ".txt";
	
	public static void writeToFile(ISaveable contentToSave) throws IOException
	{
		String directoryPath = "./" + contentToSave.getDirectory() + "/";	
		File theDirectory = new File(directoryPath);
		if (!theDirectory.exists()) {
			theDirectory.mkdirs();
		}
		String filePath = directoryPath + contentToSave.getFileID() + FileSaver.FILE_EXTENSION;
		String fileContent = contentToSave.convertToSaveable();

		BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
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
