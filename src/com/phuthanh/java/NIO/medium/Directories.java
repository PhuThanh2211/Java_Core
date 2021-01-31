package com.phuthanh.java.NIO.medium;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Directories {
	
	public static final String SEPERATOR = File.separator;

	public static void main(String[] args) {
//		getChildElementOfDirectory();
//		displayFileStore();
//		walkFileTree();
//		copyFileTree();
		
		mappingIoAndNio();
	}

	private static void getChildElementOfDirectory() {
		DirectoryStream.Filter<Path> filter = new DirectoryStream.Filter<Path>() {
			public boolean accept(Path path) throws IOException {
				return (Files.isRegularFile(path));
			}
		};
		
		StringBuilder path = new StringBuilder();
		path.append("src" + SEPERATOR + "com" + SEPERATOR + "phuthanh" + SEPERATOR + "java" + SEPERATOR);
		path.append("NIO" + SEPERATOR + "medium" + SEPERATOR);
		
		
		Path directory = FileSystems.getDefault().getPath(path.toString());
		
		try (DirectoryStream<Path> contents = Files.newDirectoryStream(directory, filter)) {
			for (Path file : contents) {
				System.out.println(file.getFileName());
			}
			
		} catch (IOException | DirectoryIteratorException e) {
			e.printStackTrace();
		}
	}
	
	private static void createTempFile() {
		try {
			Path tempFile = Files.createTempFile("mvapp", ".appext");
			System.out.println("Temporary file path = " + tempFile.toAbsolutePath());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void displayFileStore() {
		Iterable<FileStore> stores = FileSystems.getDefault().getFileStores();
		
		for (FileStore store : stores) {	
			System.out.println("Volume name/Drive letter = " + store);
			System.out.println("file store = " + store.name());
		}
	}
	
	private static void walkFileTree() {
		System.out.println("--Walking Tree for folder NIO Medium--");
		
		StringBuilder path = new StringBuilder();
		path.append("src" + SEPERATOR + "com" + SEPERATOR + "phuthanh" + SEPERATOR + "java" + SEPERATOR);
		path.append("NIO" + SEPERATOR + "medium" + SEPERATOR);
		
		Path dirPath = FileSystems.getDefault().getPath(path.toString());
		
		try {
			Files.walkFileTree(dirPath, new PrintNames());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void copyFileTree() {
		System.out.println("--Copy folder files to folder walkFile--");
		
		Path sourcePath = FileSystems.getDefault().getPath("files");
		Path copyPath = FileSystems.getDefault().getPath("walkFile");
		
		try {
			Files.walkFileTree(sourcePath, new CopyFiles(sourcePath, copyPath));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void mappingIoAndNio() {
		File file = new File("/Examples/file.txt");
		Path convertedPath = file.toPath();
		System.out.println("convertedPath = " + convertedPath);
		
		File parent = new File("/Examples");
		File resolvedFile = new File(parent, "dir/file.txt");
		System.out.println("resolvedFile = " + resolvedFile.toPath());
		
		resolvedFile = new File("/Examples", "dir/file.txt");
		System.out.println(resolvedFile.toPath());
		
		Path parentPath = Paths.get("/Examples");
		Path childRelativePath = Paths.get("dir/file.txt");
		System.out.println(parentPath.resolve(childRelativePath));
		
		System.out.println("-------------------------------------");
		File workingDirectory = new File("").getAbsoluteFile();
		System.out.println("Working Directory = " + workingDirectory.getAbsolutePath() + "\n");
		
		System.out.println("---print folder files contents using list()---");
		File files = new File(workingDirectory, "/files");
		String[] filesContent = files.list();
		for (int i = 0; i < filesContent.length; i++) {
			System.out.println("i = " + i + ": " + filesContent[i]);
		}
		
		System.out.println("\n---print folder files contents using listFiles()---");
		File[] listFiles = files.listFiles();
		for (int i = 0; i < listFiles.length; i++) {
			System.out.println("i = " + i + ": " + listFiles[i].getName());
		}
	}
}








