package exercisesP5.exercise3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.GraphPath;

public record DeliverySolution(Map<Integer, List<Integer>> sentProducts, Double totalCost) {

	public static DeliverySolution of(GraphPath<DeliveryVertex, DeliveryEdge> g) {
		Map<Integer, List<Integer>> sP = new HashMap<>();
		Double tC = 0.;
		for(DeliveryEdge e:g.getEdgeList()) {
			DeliveryVertex s = e.source();
			Integer prod = s.index()%DeliveryData.nProducts;
			List<Integer> toStore;
			if(sP.containsKey(prod)) {
				toStore = sP.get(prod);
			} else {
				toStore = new ArrayList<>();
			}
			toStore.add(e.action());
			sP.put(prod, toStore);
			tC += e.weight();
		}
		return new DeliverySolution(sP, tC);
	}
	
}
