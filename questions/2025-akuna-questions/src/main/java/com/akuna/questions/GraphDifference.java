package com.akuna.questions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.TreeMap;

/**
 * A Graph is provided with multiple nodes and edges.
 * Find the maximum difference between node id's in any connected component of
 * an undirected graph.
 * Given a number of nodes and edges, construct an undirected graph.
 * A connected component is any group of connected nodes.
 * For each connected component, determine its maximum and minimum node value.
 * Return the maximum difference
 */

/*
 * - Build adjacency list of undirected graph
 * - Traverse graph using DFS or BFS to find connected components
 * - For each component track min and max node id
 * - return max difference
 */
public class GraphDifference {

	public static int maximumDifference(int gNodes, List<Integer> gFrom, List<Integer> gTo) {
		// Build graph adjacency list
		Map<Integer, List<Integer>> graph = new TreeMap();
		for (int i = 1; i <= gNodes; i++) {
			graph.put(i, new ArrayList<>());
		}
		for (int i = 0; i < gFrom.size(); i++) {
			int from = gFrom.get(i);
			int to = gTo.get(i);
			graph.get(from).add(to);
			graph.get(to).add(from);
		}
		// Debug
		for (int node : graph.keySet()) {
			System.out.println(String.format("%d -> %s", node, graph.get(node)));
		}

		boolean[] visited = new boolean[gNodes + 1];
		int maxDiff = 0;
		for (int i = 1; i <= gNodes; i++) {
			if (!visited[i]) {
				// int[] minMax = dfs(i, graph, visited);
				int[] minMax = bfs(i, graph, visited);
				int diff = minMax[1] - minMax[0];
				maxDiff = Math.max(maxDiff, diff);
			}
		}

		return maxDiff;

	}

	private static int[] dfs(int node, Map<Integer, List<Integer>> graph, boolean[] visited) {
		Stack<Integer> stack = new Stack();
		stack.push(node);
		visited[node] = true;

		int min = node;
		int max = node;

		while (!stack.isEmpty()) {
			int current = stack.pop();
			min = Math.min(min, current);
			max = Math.max(max, current);

			for (int neighbor : graph.get(current)) {
				if (!visited[neighbor]) {
					visited[neighbor] = true;
					stack.push(neighbor);
				}
			}
		}

		return new int[] { min, max };
	}

	private static int[] bfs(int node, Map<Integer, List<Integer>> graph, boolean[] visited) {
		Queue<Integer> queue = new LinkedList<>();
		queue.offer(node);
		visited[node] = true;

		int min = node;
		int max = node;

		while (!queue.isEmpty()) {
			int current = queue.poll();
			min = Math.min(min, current);
			max = Math.max(max, current);

			for (int neighbor : graph.get(current)) {
				if (!visited[neighbor]) {
					visited[neighbor] = true;
					queue.offer(neighbor);
				}
			}
		}

		return new int[] { min, max };
	}

	public static void main(String[] args) {
		System.out.println("Maximum difference in connected graph");
		GraphDifference gd = new GraphDifference();

		int gNodes = 5;
		List<Integer> gFrom = Arrays.asList(1, 1, 2, 2, 3, 4);
		List<Integer> gTo = Arrays.asList(2, 3, 3, 4, 4, 5);

		int result = gd.maximumDifference(gNodes, gFrom, gTo);
		System.out.println(result);

	}
}
