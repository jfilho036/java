package com.pimco.questions;

/**
 * What is this coding doing?
 * What would it print?
 * 
 * Any problems?
 * - race condition. increase loops from 4 to make it easier to catch.
 * 
 * How to fix it?
 * - synchronized incr/dec/get and join thread
 * - use atomic integer
 * What if counter is volatile? Not enough because ++/-- is not atomic: read,
 * update, write.
 * 
 */
public class Counters implements Runnable {
	private int counter;
	// private volatile int counter;

	// public void increment() {
	public synchronized void increment() {
		this.counter++;
	}

	// public void decrement() {
	public synchronized void decrement() {
		this.counter--;
	}

	// public int get() {
	public synchronized int get() {
		return this.counter;
	}

	@Override
	public void run() {
		for (int i = 0; i < 4000; i++) {
			increment();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Counters counters = new Counters();
		// new Thread(counters).start();
		Thread t = new Thread(counters);
		t.start();

		for (int i = 0; i < 4000; i++) {
			counters.decrement();
		}

		// TODO this was added as part of solution
		t.join();

		System.out.println(counters.get());
	}

}
