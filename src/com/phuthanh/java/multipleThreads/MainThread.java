package com.phuthanh.java.multipleThreads;

/*
	- A process is a unit of execution that has its own memory space. Each instance of a Java Virtual Machine (JVM)
	runs as a process. When we run a Java console application, we're kicking off a process.
	
	- If one Java app is running and we run another one, each app has its own memory space of HEAP. The first java app can't
	access the HEAP that belongs to the second Java app. The HEAP isn't shared between them. They each have their own.
	
	- A thread is unit of execution within a process. Each process can have multiple threads. In Java, every process has at least
	one thread (main thread). In fact, just about every Java process also has multiple system threads that handle tasks like
	memory management and I/O. Developers don't explicitly create and code those threads. Our code runs on the main thread,
	or in other threads that we explicitly create.
	
	- Creating a thread doesn't require as many resources as creating a process. Every thread created by a process shares
	the process's memory and files.
	
	- Concurrency, which refers to an app doing more than one thing at a time. Now, that doesn't necessarily mean that the app
	 is doing more than one thing at the same time. It means that progress can be made on more than one task.
	 
	- If it's a concurrency app, it can download a bit of data, then switch to drawing part of the shape, then switch back
	to downloading some more data, then switch back to drawing more of the shape. Concurrency means one task doesn't have to 
	complete before another can start. Java provides thread-related classes so that we can create Java concurrent app.
*/
public class MainThread {

	public static void main(String[] args) {
		System.out.println(ThreadColor.ANSI_PURPLE + "Hello from the main thread");
		
		AnotherThread anotherThread = new AnotherThread();
		anotherThread.setName("== Another Thread ==");
		anotherThread.start();
		
		new Thread() {
			public void run() {
				System.out.println(ThreadColor.ANSI_YELLOW + "Hello from the anonymous class thread");
			}
		}.start();
		
		Thread myRunnableThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println(ThreadColor.ANSI_RED + "Hello from the anonymous class's implementation of run()");
				
				try {
					anotherThread.join(2000);
					System.out.println(ThreadColor.ANSI_RED + "AnotherThread terminated, or timed out, So I'm running again");
					
				} catch (InterruptedException e) {
					System.out.println(ThreadColor.ANSI_RED + "I couldn't wait after all. I was interrupt");
				}
			}
		});
		myRunnableThread.start();
		
		System.out.println(ThreadColor.ANSI_PURPLE + "Hello again from the main thread");
	}

}




















