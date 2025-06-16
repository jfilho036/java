package com.rokos.questions;

import java.util.ArrayList;
import java.util.List;

/**
 * Question 3
 *
 * You are given a rooted tree.
 * A node is balanced if all of its subtrees are of the same size.
 * The size of the subtree is the number if nodes it contains.
 * Write a function that given a tree returns the number of balanced nodes it
 * contains
 * 
 * Assume the following declaration is given
 * class Node {
 * public Node[] subtrees;
 * }
 * 
 */

public class BalancedNodes {

	static class Node {
		public Node[] subtrees;

		public Node(Node[] subtrees) {
			this.subtrees = subtrees;
		}
	}

	static class NodesCount {
		public int nodeCount;
		public int balancedNodeCount;

		public NodesCount(int nodeCount, int balancedNodeCount) {
			this.nodeCount = nodeCount;
			this.balancedNodeCount = balancedNodeCount;
		}
	}

	public static int countBalancedNodes(Node tree) {
		NodesCount nodesCount = countNodes(tree);
		return nodesCount.balancedNodeCount;

	}

	public static NodesCount countNodes(Node tree) {
		if (tree == null) {
			return new NodesCount(0, 0);
		}

		// Leaf node
		if (tree.subtrees.length == 0) {
			return new NodesCount(1, 1);
		}

		NodesCount result = new NodesCount(1, 0);

		List<Integer> childCount = new ArrayList<>();
		for (Node child : tree.subtrees) {
			NodesCount nodesCount = countNodes(child);
			childCount.add(nodesCount.nodeCount);
			result.nodeCount += nodesCount.nodeCount;
			result.balancedNodeCount += nodesCount.balancedNodeCount;
		}

		boolean isBalanced = true;
		for (int i = 1; i < childCount.size(); i++) {
			// Not balanced
			if (childCount.get(i - 1) != childCount.get(i)) {
				isBalanced = false;
				break;
			}
		}

		if (isBalanced) {
			result.balancedNodeCount += 1;
		}

		return result;

	}

	public static void main(String[] args) {
		System.out.println(BalancedNodes.class.getName());

		Node tree_1 = tree_1();
		System.out.println("Balanced nodes count: " + countBalancedNodes(tree_1));

		Node tree_2 = tree_2();
		System.out.println("Balanced nodes count: " + countBalancedNodes(tree_2));

	}

	private static Node createLeafNode() {
		return new Node(new Node[] {});
	}

	private static Node tree_1() {
		Node leaf1 = createLeafNode();
		Node leaf2 = createLeafNode();
		Node leaf3 = createLeafNode();

		Node mid1 = new Node(new Node[] { leaf1, leaf2 });
		Node mid2 = new Node(new Node[] { leaf3 });

		Node root = new Node(new Node[] { mid1, mid2 });
		return root;
	}

	private static Node tree_2() {
		Node leaf1 = createLeafNode();
		Node leaf2 = createLeafNode();
		Node leaf3 = createLeafNode();
		Node leaf4 = createLeafNode();

		Node mid1 = new Node(new Node[] { leaf1, leaf2 });
		Node mid2 = new Node(new Node[] { leaf4 });
		Node mid3 = new Node(new Node[] { leaf3, mid2 });

		Node root = new Node(new Node[] { mid1, mid3 });
		return root;
	}

}
