package com.akuna.questions;

import java.util.Map;
import java.util.TreeMap;

public class PortfolioRebalancer {

	interface IPortfolio {
		Map<String, Integer> getAllocations();
	}

	static class SimplePortfolio implements IPortfolio {
		private final Map<String, Integer> allocations;

		public SimplePortfolio(Map<String, Integer> allocations) {
			this.allocations = allocations;
		}

		@Override
		public Map<String, Integer> getAllocations() {
			return this.allocations;
		}
	}

	static class PortfolioManager {

		public static Map<String, Integer> rebalancePortfolio(IPortfolio currentPortfolio, IPortfolio targetPortfolio) {
			// Using tree map to key assets sorted by name
			Map<String, Integer> rebalancedAllocations = new TreeMap<>();
			// Map<String, Integer> rebalancedAllocations = new LinkedHashMap();
			for (String asset : targetPortfolio.getAllocations().keySet()) {
				int currentAllocation = currentPortfolio.getAllocations().get(asset);
				int targetAllocation = targetPortfolio.getAllocations().get(asset);

				rebalancedAllocations.put(asset, targetAllocation - currentAllocation);
			}
			return rebalancedAllocations;
		}
	}

	public static void main(String[] args) {
		IPortfolio currentPortfolio = new SimplePortfolio(Map.of("Stock", 60, "Bonds", 30, "Curencies", 10));
		IPortfolio targetPortfolio = new SimplePortfolio(Map.of("Stock", 50, "Bonds", 40, "Curencies", 10));

		Map<String, Integer> rebalancedAllocations = PortfolioManager.rebalancePortfolio(currentPortfolio,
				targetPortfolio);

		System.out.println(rebalancedAllocations);
	}

}
