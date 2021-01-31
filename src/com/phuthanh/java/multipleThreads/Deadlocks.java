package com.phuthanh.java.multipleThreads;

public class Deadlocks {
	
	private static Object lock1 = new Object();
	private static Object lock2 = new Object();

	public static void main(String[] args) {
		new Thread1().start();
		new Thread2().start();
	}
	
	private static class Thread1 extends Thread {
		public void run() {
			synchronized (lock1) {
				System.out.println("Thread 1: Has lock1");
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					
				}
				System.out.println("Thread 1: Waiting for lock2");
				synchronized (lock2) {
					System.out.println("Thread 1: Has lock1 and lock2");
				}
				
				System.out.println("Thread 1: Released lock2");
			}
			
			System.out.println("Thread 1: Released lock1. Exiting...");
		}
	}
	
	private static class Thread2 extends Thread {
		/* Deadlock because 2 thread not process locks with same order 
		public void run() {
			synchronized (lock2) {
				System.out.println("Thread 2: Has lock2");
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					
				}
				System.out.println("Thread 2: Waiting for lock1");
				synchronized (lock1) {
					System.out.println("Thread 2: Has lock1 and lock2");
				}
				
				System.out.println("Thread 2: Released lock1");
			}
			
			System.out.println("Thread 2: Released lock2. Exiting...");
		}
		*/
		
		/* Solution: Same order to get locks */
		public void run() {
			synchronized (lock1) {
				System.out.println("Thread 2: Has lock1");
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					
				}
				System.out.println("Thread 2: Waiting for lock2");
				synchronized (lock2) {
					System.out.println("Thread 2: Has lock1 and lock2");
				}
				
				System.out.println("Thread 2: Released lock2");
			}
			
			System.out.println("Thread 2: Released lock1. Exiting...");
		}
	}
	
}
