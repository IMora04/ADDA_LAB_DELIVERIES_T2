package exercisesP5.exercise3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.jgrapht.GraphPath;

import us.lsi.graphs.virtual.VirtualVertex;

public record DeliveryVertex(Integer index, List<Integer> remainingUnits, List<Integer> demandLeft) 
	implements VirtualVertex<DeliveryVertex, DeliveryEdge, Integer> {
	
	public List<Integer> actions() {
		Integer prod = this.index%DeliveryData.nProducts;
		Integer dest = this.index/DeliveryData.nProducts;
		List<Integer> actions = new ArrayList<>();
		
		if(prod.equals(DeliveryData.nProducts-1)) {
			if(demandLeft.get(dest) <= remainingUnits.get(prod) && demandLeft.get(dest) >= 0) {
				actions.add(demandLeft.get(dest));
			}
		} else if(!(this.index.equals(DeliveryData.nProducts*DeliveryData.nDestinations))){
			actions = IntStream.range(0, DeliveryData.unitsAvailable.get(prod)+1)
					.boxed().collect(Collectors.toList());
		}
		actions.sort(Comparator.naturalOrder());
		return actions;
	}

	public DeliveryVertex neighbor(Integer a) {
		List<Integer> remainingUnits = new ArrayList<>(this.remainingUnits);
		List<Integer> demandLeft = new ArrayList<>(this.demandLeft);
		Integer dest = this.index/DeliveryData.nProducts;
		Integer prod = this.index%DeliveryData.nProducts;
		remainingUnits.set(prod, remainingUnits.get(prod) - a);
		demandLeft.set(dest, demandLeft.get(dest) - a);
		return new DeliveryVertex(this.index+1, remainingUnits, demandLeft);
	}

	public DeliveryEdge edge(Integer a) {
		return DeliveryEdge.of(this, this.neighbor(a), a);
	}
	
	public static DeliveryVertex initial() {
		return new DeliveryVertex(0, new ArrayList<>(DeliveryData.unitsAvailable), new ArrayList<>(DeliveryData.minDemands));
	}
	
	public static Predicate<DeliveryVertex> goal() {
		return v -> v.index.equals(DeliveryData.nDestinations*DeliveryData.nProducts);
	}
	
	public static DeliverySolution getSolution(GraphPath<DeliveryVertex, DeliveryEdge> g) {
		return DeliverySolution.of(g);
	}
	
	public static List<Integer> cheapestProduct() {
		List<Integer> cheapestProducts = new ArrayList<>();
		for(int d = 0; d < DeliveryData.nDestinations; d++) {
			Integer min = Integer.MAX_VALUE;
			for(int p = 0; p < DeliveryData.nProducts; p++) {
				Integer storingCosts = DeliveryData.storingCosts.get(p).get(d);
				if(min < storingCosts) {
					min = storingCosts;
				}
			}
			cheapestProducts.add(min);
		}
		return cheapestProducts;
	}
	
}
