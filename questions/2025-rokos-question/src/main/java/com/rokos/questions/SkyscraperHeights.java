package com.rokos.questions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Question 1
 * 
 * You are given an array of N integers, representing the maximum heights of N
 * skyscrappers to be built.
 * Your task is to specify the actual heights if the skyscrappers, given that:
 * - the height of the kTh skyscrapper should be not bigger than A[k]
 * - no two skyscrappers should be of the same height
 * - the total sum of the skyscrappers height should be the maximum possible
 */
public class SkyscraperHeights {

	static class HeightIndex implements Comparable<HeightIndex> {
		public int height;
		public int index;

		public HeightIndex(int height, int index) {
			this.height = height;
			this.index = index;
		}

		@Override
		public int compareTo(HeightIndex other) {
			int value = other.height - this.height;
//			if (value == 0) {
//				return other.index - this.index;
//			}
			return value;
		}

		@Override
		public String toString() {
			return String.format("(%d,%d)", height, index);
		}
	}

	public static int[] getMaxSkyscraperHeights(int[] A) {
		int[] heights = new int[A.length];

		// Sort max building height and its index
		List<HeightIndex> maxHeightIndexOrderedList = new ArrayList<>();
		for (int i = 0; i < A.length; i++) {
			maxHeightIndexOrderedList.add(new HeightIndex(A[i], i));
		}
		Collections.sort(maxHeightIndexOrderedList);
		System.out.println(maxHeightIndexOrderedList.toString());

		int currentHeight = Integer.MAX_VALUE;
		for (int i = 0; i < maxHeightIndexOrderedList.size(); i++) {
			int maxHeight = maxHeightIndexOrderedList.get(i).height;
			int index = maxHeightIndexOrderedList.get(i).index;

			currentHeight = Math.min(currentHeight - 1, maxHeight);

			heights[index] = currentHeight;
		}

		return heights;

	}

	public static void main(String[] args) {
		System.out.println(SkyscraperHeights.class.getName());

		int[] input = null;
		// int[] expectedOutput = null;
		int[] answer = null;

		input = new int[] { 1, 2, 3 };
		// expectedOutput = new int[] { 1, 2, 3 };
		answer = SkyscraperHeights.getMaxSkyscraperHeights(input);
		System.out.println(String.format("%s : %s", Arrays.toString(input), Arrays.toString(answer)));

		input = new int[] { 9, 4, 3, 7, 7 };
		// expectedOutput = new int[] { 9, 4, 3, 7, 6};
		answer = SkyscraperHeights.getMaxSkyscraperHeights(input);
		System.out.println(String.format("%s : %s", Arrays.toString(input), Arrays.toString(answer)));

		input = new int[] { 2, 5, 4, 5, 5 };
		// expectedOutput = new int[] { 1, 2, 3, 4, 5};
		answer = SkyscraperHeights.getMaxSkyscraperHeights(input);
		System.out.println(String.format("%s : %s", Arrays.toString(input), Arrays.toString(answer)));

	}
}
