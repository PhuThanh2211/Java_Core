package com.phuthanh.java.multipleThreads;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumer {

	public static final String EOF = "EOF";
	
	public static void main(String[] args) {
		List<String> buffer = new ArrayList<>();
		ReentrantLock bufferLock = new ReentrantLock();
		
		ExecutorService executorService = Executors.newFixedThreadPool(3);
		
		MyProducer producer = new MyProducer(buffer, ThreadColor.ANSI_YELLOW, bufferLock);
		MyConsumer consumer1 = new MyConsumer(buffer, ThreadColor.ANSI_PURPLE, bufferLock);
		MyConsumer consumer2 = new MyConsumer(buffer, ThreadColor.ANSI_CYAN, bufferLock);
		
		executorService.execute(producer);
		executorService.execute(consumer1);
		executorService.execute(consumer2);
		
		/* If the thread pool is set to 3, future will wait the the 3 threads run completely tasks and run
		 	If the thread pool is set to 5, future will run with 3 thread (producer, consumer1, consumer2) together
		 */
		Future<String> future = executorService.submit(new Callable<String>() {

			@Override
			public String call() throws Exception {
				System.out.println(ThreadColor.ANSI_WHITE + "I'm being printed for the Callable class");
				return "This is the callable result";
			}
		});

		try {
			System.out.println(future.get());
		} catch (ExecutionException e) {
			System.out.println("Something went wrong");
		} catch (InterruptedException e) {
			System.out.println("Thread running the task was interrupt");
		}
		
		executorService.shutdown();
	}

}

class MyProducer implements Runnable {
	private List<String> buffer;
	private String color;
	private ReentrantLock bufferLock;
	
	public MyProducer(List<String> buffer, String color, ReentrantLock bufferLock) {
		this.buffer = buffer;
		this.color = color;
		this.bufferLock = bufferLock;
	}
	
	public void run() {
		Random random = new Random();
		String[] nums = {"1", "2", "3", "4", "5"};
		
		for (String num : nums) {
			try {
				System.out.println(color + "Adding..." + num);
				
				bufferLock.lock();
				try {
					buffer.add(num);
				} finally {
					bufferLock.unlock();
				}
				
				Thread.sleep(random.nextInt(1000));
				
			} catch (InterruptedException e) {
				System.out.println("Producer was interrupted");
			}
		}
		
		System.out.println(color + "Adding EOF and exiting...");
		
		bufferLock.lock();
		try {
			buffer.add("EOF");
		} finally {
			bufferLock.unlock();
		}
	}
}

class MyConsumer implements Runnable {
	private List<String> buffer;
	private String color;
	private ReentrantLock bufferLock;
	
	public MyConsumer(List<String> buffer, String color, ReentrantLock bufferLock) {
		this.buffer = buffer;
		this.color = color;
		this.bufferLock = bufferLock;
	}
	
	public void run() {
		while (true) {
			if (bufferLock.tryLock()) {
				try {
					if (buffer.isEmpty()) {
						continue;
					}
					
					if (buffer.get(0).equals(ProducerConsumer.EOF)) {
						System.out.println(color + "Exiting");
						break;
					} else {
						System.out.println(color + "Removed " + buffer.remove(0));
					}
				} finally {
					bufferLock.unlock();
				}
			}
		}
	}
}



















