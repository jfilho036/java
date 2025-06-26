package com.pimco.questions;

/**
 * What will this code print?
 */
public class Exceptions {
	public static void main(String[] args) {
		try {
			int value = 1 / 0;
			System.out.println("1");
		} catch (ArithmeticException ae) {
			System.out.println("2");
			throw new RuntimeException();
		} catch (Exception e) {
			System.out.println("3");
		} finally {
			System.out.println("4");
		}
		System.out.println("5");
	}
}
