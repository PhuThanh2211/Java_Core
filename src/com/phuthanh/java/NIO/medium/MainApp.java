package com.phuthanh.java.NIO.medium;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.Pipe;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class MainApp {

	public static void main(String[] args) {
//		writeAndReadTextFile();
		
//		writeBinaryFileV1();
//		writeBinaryFileV2();
//		processSeekableByteChannelInterface();
		
		pipeWithThreads();
	}
	
	private static void writeAndReadTextFile() {
		String dirFile = "src/com/phuthanh/java/NIO/medium/data.txt";
		
		Path dataPath = FileSystems.getDefault().getPath(dirFile);
		
		try {
			Files.write(dataPath, "\nLine 5".getBytes("UTF-8"), StandardOpenOption.APPEND);
			List<String> lines = Files.readAllLines(dataPath);
			for (String line : lines) {
				System.out.println(line);
			}
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void writeBinaryFileV1() {
		String dirFile = "src/com/phuthanh/java/NIO/medium/data.dat";
		try (FileOutputStream binFile = new FileOutputStream(dirFile)) {
			FileChannel binChannel = binFile.getChannel();
			
			byte[] outputBytes = "Hello World".getBytes();
//			ByteBuffer buffer = ByteBuffer.wrap(outputBytes);
			
			ByteBuffer buffer = ByteBuffer.allocate(outputBytes.length);
			buffer.put(outputBytes);
			buffer.flip();
			
			int numBytes = binChannel.write(buffer);
			System.out.println("numBytes written was: " + numBytes);
			
			ByteBuffer intBuffer = ByteBuffer.allocate(Integer.BYTES);
			intBuffer.putInt(245);
			intBuffer.flip();
			numBytes = binChannel.write(intBuffer);
			System.out.println("numBytes written was: " + numBytes);
			
			intBuffer.flip();
			intBuffer.putInt(-98765);
			intBuffer.flip();
			
			numBytes = binChannel.write(intBuffer);
			System.out.println("numBytes written was: " + numBytes);
			
			RandomAccessFile ra = new RandomAccessFile(dirFile, "rwd");
			FileChannel channel = ra.getChannel();
			outputBytes[0] = 'a';
			outputBytes[1] = 'b';
			buffer.flip();
			long numBytesRead = channel.read(buffer);
			System.out.println("outputBytes = " + new String(outputBytes));
			
			// Absolute Read
			intBuffer.flip();
			numBytesRead = channel.read(intBuffer);
			System.out.println(intBuffer.getInt(0));
			intBuffer.flip();
			numBytesRead = channel.read(intBuffer);
			System.out.println(intBuffer.getInt(0));
			
			// Relative Read
//			intBuffer.flip();
//			numBytesRead = channel.read(intBuffer);
//			intBuffer.flip();
//			System.out.println(intBuffer.getInt());
//			intBuffer.flip();
//			numBytesRead = channel.read(intBuffer);
//			intBuffer.flip();
//			System.out.println(intBuffer.getInt());
			
			channel.close();
			ra.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void writeBinaryFileV2() {
		String dirFile = "src/com/phuthanh/java/NIO/medium/data.dat";
		
		try (FileOutputStream binFile = new FileOutputStream(dirFile);
				FileChannel channel = binFile.getChannel()) {
			ByteBuffer buffer = ByteBuffer.allocate(100);
			byte[] outputBytes = "Hello World!".getBytes();
			buffer.put(outputBytes);
			buffer.putInt(245);
			buffer.putInt(-98765);
			
			byte[] outputBytes2 = "Nice to meet you".getBytes();
			buffer.put(outputBytes2);
			buffer.putInt(1000);
			buffer.flip();
			channel.write(buffer);
			
			RandomAccessFile ra = new RandomAccessFile(dirFile, "rwd");
			FileChannel raChannel = ra.getChannel();
			
			ByteBuffer readBuffer = ByteBuffer.allocate(100);
			raChannel.read(readBuffer);
			readBuffer.flip();
			
			byte[] inputString = new byte[outputBytes.length];
			readBuffer.get(inputString);
			
			System.out.println("inputString = " + new String(inputString));
			System.out.println("int1 = " + readBuffer.getInt());
			System.out.println("int2 = " + readBuffer.getInt());
			
			byte[] inputString2 = new byte[outputBytes2.length];
			readBuffer.get(inputString2);
			
			System.out.println("inputString2 = " + new String(inputString2));
			System.out.println("int3 = " + readBuffer.getInt());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*	New Feature in Java 7: Seekable Byte Channel Interface
		Some Methods:
		1. read(ByteBuffer) - read bytes beginning at the channel's current position, and after the read,
							updates the position accordingly.
							Note that now we're talking the channel's position, not the byte buffer's position.
							Of course, the bytes will be placed into the buffer starting at its current position.
							
		2. write(ByteBuffer) - the same as read, except it writes. There's one exception.
							If a datasource is opened in APPEND mode, the the first write will take place starting
							at the end of the datasource, rather than at position 0. After the write,
							the position will be updated accordingly.
							
		3. position() 		- returns the channel's position
		4. position(long) 	- sets the channel's position to the passed value
		5. truncate(long)	- truncates the size of the attached datasource to the passed value
		6. size() 			- returns the size of the attached datasource
	
	*/
	private static void processSeekableByteChannelInterface() {
		String dirFile = "src/com/phuthanh/java/NIO/medium/data.dat";
		
		try (FileOutputStream binFile = new FileOutputStream(dirFile);
				FileChannel channel = binFile.getChannel()) {
			
			ByteBuffer buffer = ByteBuffer.allocate(100);
			byte[] outputBytes = "Hello World!".getBytes();
			buffer.put(outputBytes);
			int int1Pos = outputBytes.length;
			buffer.putInt(245);
			int int2Pos = int1Pos + Integer.BYTES;
			buffer.putInt(-98765);
			
			byte[] outputBytes2 = "Nice to meet you".getBytes();
			buffer.put(outputBytes2);
			int int3Pos = int2Pos + Integer.BYTES + outputBytes2.length;
			buffer.putInt(1000);
			buffer.flip();
			channel.write(buffer);
			
			RandomAccessFile ra = new RandomAccessFile(dirFile, "rwd");
			FileChannel raChannel = ra.getChannel();
			
			ByteBuffer readBuffer = ByteBuffer.allocate(Integer.BYTES);
			raChannel.position(int3Pos);
			raChannel.read(readBuffer);
			readBuffer.flip();
			
			System.out.println("int3 = " + readBuffer.getInt());
			readBuffer.flip();
			raChannel.position(int2Pos);
			raChannel.read(readBuffer);
			readBuffer.flip();
			
			System.out.println("int2 = " + readBuffer.getInt());
			readBuffer.flip();
			raChannel.position(int1Pos);
			raChannel.read(readBuffer);
			readBuffer.flip();
			
			System.out.println("int1 = " + readBuffer.getInt());
			
			byte[] outputString = "Hello, World".getBytes();
			long str1Pos = 0;
			long newInt1Pos = outputString.length;
			long newInt2Pos = newInt1Pos + Integer.BYTES;
			byte[] outputString2 = "Nice to meet you".getBytes();
			long str2Pos = newInt2Pos + Integer.BYTES;
			long newInt3Pos = str2Pos + outputString2.length;
			
			ByteBuffer intBuffer = ByteBuffer.allocate(Integer.BYTES);
			intBuffer.putInt(245);
			intBuffer.flip();
			channel.position(newInt1Pos);
			channel.write(intBuffer);
			
			intBuffer.flip();
			intBuffer.putInt(-98765);
			intBuffer.flip();
			channel.position(newInt2Pos);
			channel.write(intBuffer);
			
			intBuffer.flip();
			intBuffer.putInt(1000);
			intBuffer.flip();
			channel.position(newInt3Pos);
			channel.write(intBuffer);
			
			channel.position(str1Pos);
			channel.write(ByteBuffer.wrap(outputString));
			channel.position(str2Pos);
			channel.write(ByteBuffer.wrap(outputString2));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void pipeWithThreads() {
		try {
			Pipe pipe = Pipe.open();
			
			Runnable writer = new Runnable() {
				
				@Override
				public void run() {
					try {
						Pipe.SinkChannel sinkChannel = pipe.sink();
						ByteBuffer buffer = ByteBuffer.allocate(56);
						
						for (int i = 0; i < 10; i++) {
							String currentTime = "The time is " + System.currentTimeMillis();
							
							buffer.put(currentTime.getBytes());
							buffer.flip();
							
							while (buffer.hasRemaining()) {
								sinkChannel.write(buffer);
							}
							
							buffer.flip();
							Thread.sleep(100);
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			
			Runnable reader = new Runnable() {
				
				@Override
				public void run() {
					try {
						Pipe.SourceChannel sourceChannel = pipe.source();
						ByteBuffer buffer = ByteBuffer.allocate(56);
						
						for (int i = 0; i < 10; i++) {
							int bytesRead = sourceChannel.read(buffer);
							byte[] timeString = new byte[bytesRead];
							buffer.flip();
							buffer.get(timeString);
							System.out.println("Reader Thread: " + new String(timeString));
							buffer.flip();
							Thread.sleep(100);
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			
			new Thread(writer).start();
			new Thread(reader).start();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}




















