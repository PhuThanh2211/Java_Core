package com.phuthanh.java.NIO.medium;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;

public class PathIO {

	public static void main(String[] args) {
		processFileAttribute();
		
	}

	private static void printFile(Path path) {
		try (BufferedReader fileReader = Files.newBufferedReader(path)) {
			String line;
			
			while ((line = fileReader.readLine()) != null) {
				System.out.println(line);
			}
			
			System.out.println("-----------------");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void processPath() {
		Path path = FileSystems.getDefault().getPath("src/com/phuthanh/java/NIO/medium/WorkingDirectoryFile.txt");
		printFile(path);
		
		Path filePath = FileSystems.getDefault().getPath("files/SubdirectoryFile.txt");
		printFile(filePath);
		
		filePath = Paths.get("/home/ttran291/PhuThanh/Java/OutThere.txt");
		printFile(filePath);
	}
	
	private static void processMoreOnPath() {
		
		// Print current absolute path
		Path filePath = Paths.get(".");
		System.out.println(filePath.toAbsolutePath());
		
		// Create a path that does not exist
		Path path2 = FileSystems.getDefault().getPath("tinkerNotExist.txt");
		System.out.println(path2.toAbsolutePath());
		
		filePath = FileSystems.getDefault().getPath("files");
		System.out.println("Exist = " + Files.exists(filePath));
		
		System.out.println("Exist = " + Files.exists(path2));
	}
	
	private static void copyFile() {
		try {
			Path sourceFile = FileSystems.getDefault().getPath("files", "SubdirectoryFile.txt");
			Path copyFile = FileSystems.getDefault().getPath("tinkerNotExist.txt");
			
			Files.copy(sourceFile, copyFile, StandardCopyOption.REPLACE_EXISTING);
			
			// Copy Folder
			sourceFile = FileSystems.getDefault().getPath("files", ".");
			copyFile = FileSystems.getDefault().getPath("files", "subfiles");
			
			Files.copy(sourceFile, copyFile, StandardCopyOption.REPLACE_EXISTING);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void moveFile() {
		try {
			Path fileToMove = FileSystems.getDefault().getPath("tinkerNotExist.txt");
			Path destination = FileSystems.getDefault().getPath("files", "tinkerNotExist.txt");
			
			Files.move(fileToMove, destination);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void renameFile() {
		try {
			// Rename: Source directory and destination must be same
			
			Path fileToRename = FileSystems.getDefault().getPath("files", "tinkerNotExist.txt");
			Path destination = FileSystems.getDefault().getPath("files", "RikimaruNotExist.txt");
			
			Files.move(fileToRename, destination);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void deleteFile() {
		try {
			
			Path fileToDelete = FileSystems.getDefault().getPath("files", "RikimaruNotExist.txt");
			
			Files.deleteIfExists(fileToDelete);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void createFile() {
		try {
			// Create File
			Path fileToCreate = FileSystems.getDefault().getPath("files", "SadPacific.txt");
			Files.createFile(fileToCreate);
			
			// Create Directory
			Path dirToCreate = FileSystems.getDefault().getPath("files", "heroes");
			Files.createDirectory(dirToCreate);
			
			// Create Directory that does not exist
			dirToCreate = FileSystems.getDefault().getPath("files/heroes/Troll/Void/Pa/AntiMage");
			Files.createDirectories(dirToCreate);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void processFileAttribute() {
		try {
			Path filePath = FileSystems.getDefault().getPath("files", "SubdirectoryFile.txt");
			long size = Files.size(filePath);
			System.out.println("Size = " + size);
			System.out.println("Last modified = " + Files.getLastModifiedTime(filePath));
			
			// Another way to process
			BasicFileAttributes attrs = Files.readAttributes(filePath, BasicFileAttributes.class);
			System.out.println("-----------------------------");
			System.out.println("Size = " + attrs.size());
			System.out.println("Last modified = " + attrs.lastModifiedTime());
			System.out.println("Created = " + attrs.creationTime());
			System.out.println("Is directory = " + attrs.isDirectory());
			System.out.println("Is regular file = " + attrs.isRegularFile());
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}









