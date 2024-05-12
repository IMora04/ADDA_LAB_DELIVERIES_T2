package exercisesP5.exercise3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.GraphPath;

public record DeliverySolution(Map<Integer, List<Integer>> sentProducts, Double totalCost) {

	public static DeliverySolution of(GraphPath<DeliveryVertex, DeliveryEdge> g) {
		Map<Integer, List<Integer>> sentProducts = new HashMap<>();
		Double totalCost = 0.;
		for(DeliveryEdge e:g.getEdgeList()) {
			Integer prod = e.source().index()%DeliveryData.nProducts;
			List<Integer> toStore = new ArrayList<>();
			if(sentProducts.containsKey(prod)) {
				toStore = sentProducts.get(prod);
			}
			toStore.add(e.action());
			sentProducts.put(prod, toStore);
			totalCost += e.weight();
		}
		return new DeliverySolution(sentProducts, totalCost);
	}
	
	public static DeliverySolution of(DeliveryVertex initial, List<Integer> pastActions) {
		Map<Integer, List<Integer>> sentProducts = new HashMap<>();
		Double totalCost = 0.;
		for(int i = 0; i < pastActions.size(); i++) {
			Integer prod = i%DeliveryData.nProducts;
			Integer dest = i/DeliveryData.nProducts;
			List<Integer> toStore = new ArrayList<>();
			if(sentProducts.containsKey(prod)) {
				toStore = sentProducts.get(prod);
			}
			toStore.add(pastActions.get(i));
			sentProducts.put(prod, toStore);
			totalCost += 1.*pastActions.get(i)*DeliveryData.storingCosts.get(prod).get(dest);
		}
		return new DeliverySolution(sentProducts, totalCost);
	}

	public String toString() {
		String s = "Solution: \n";
		for(Map.Entry<Integer, List<Integer>> e:sentProducts.entrySet()) {
			s += ("\tProduct " + e.getKey() +": " + e.getValue() + "\n");
		}
		s += "\tTotal storing costs: " + totalCost;
		return s;
	}
	
}
