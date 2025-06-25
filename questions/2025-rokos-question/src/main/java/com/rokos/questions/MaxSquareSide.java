package com.rokos.questions;

/**
 * Question 2
 * 
 * There are two wooden sticks of lengths A and B respectively. Each of them can
 * be cut into shorter sticks of integer lengths.
 * Our goal is to construct the largest possible square.
 * In order to do this, we want to cut the sticks in such a way as to achieve
 * four sticks of the same length.
 * What is the longest side of the square that can be achieved?
 * 
 */

public class MaxSquareSide {

	public static int getMaxSquareSide(int A, int B) {
		if (A + B < 4) {
			return 0;
		}

		int left = 0;
		int right = Math.max(A, B);

		int maxSide = 0;
		// Use binary search to find maxSide that meets condition
		// A/l + B/l >= 4
		while (left <= right) {
			int mid = (left + right) / 2;

			int sticks = A / mid + B / mid;

			if (sticks >= 4) {
				maxSide = mid;
				left = mid + 1;
			} else {
				right = mid - 1;
			}
		}

		return maxSide;
	}

	public static void main(String[] args) {
		System.out.println(MaxSquareSide.class.getName());

		int A = 0;
		int B = 0;
		int answer = 0;

		A = 10;
		B = 21;
		// expectedOutput = 7
		answer = MaxSquareSide.getMaxSquareSide(A, B);
		System.out.println(String.format("%d , %d : %d", A, B, answer));

		A = 13;
		B = 11;
		// expectedOutput = 5
		answer = MaxSquareSide.getMaxSquareSide(A, B);
		System.out.println(String.format("%d , %d : %d", A, B, answer));

		A = 2;
		B = 1;
		// expectedOutput = 0
		answer = MaxSquareSide.getMaxSquareSide(A, B);
		System.out.println(String.format("%d , %d : %d", A, B, answer));

		A = 1;
		B = 8;
		// expectedOutput = 4
		answer = MaxSquareSide.getMaxSquareSide(A, B);
		System.out.println(String.format("%d , %d : %d", A, B, answer));
	}
}
