package com.phuthanh.java.multipleThreads;

public class MoreOnDeadlocks {

	public static void main(String[] args) {
		final PolitePerson jane = new PolitePerson("Jane");
		final PolitePerson john = new PolitePerson("John");
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				jane.sayHello(john);
			}
		}).start();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				john.sayHello(jane);// TODO Auto-generated method stub
			}
		}).start();
		
		
	}
	
	/*
		Thread1 acquires the lock on the Jane object and enters the sayHello() method. It prints to the console, then suspends.
		Thread2 acquires the lock on the John object and enters the sayHello() method. It prints to the console, then suspends.
		
		Thread1 runs again and wants to say hello back to the John object. It tries to call sayHelloBack() method 
			using the John object that was passed into the sayHello(), but Thread2 is holding the John object, so Thread1 suspends.
		Thread2 runs again and wants to say hello back to the John object. It tries to call sayHelloBack() method 
			using the Jane object that was passed into the sayHello(), but Thread1 is holding the Jane object, so Thread2 suspends.
			
		=> Deadlocks
	*/
	static class PolitePerson {
		private final String name;
		
		public PolitePerson(String name) {
			this.name = name;
		}
		
		public String getName() {
			return this.name;
		}
		
		public synchronized void sayHello(PolitePerson person) {
			System.out.format("%s: %s" + " has said hello to me!%n", this.name, person.getName());
			person.sayHelloBack(this);
		}
		
		public synchronized void sayHelloBack(PolitePerson person) {
			System.out.format("%s: %s" + " has said hello back to me!%n", this.name, person.getName());
		}
	}
}
