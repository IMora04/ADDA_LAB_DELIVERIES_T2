package exercisesP5.exercise3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
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
		List<Integer> ac = new ArrayList<>();
		
		if(this.index.equals(DeliveryData.nProducts*DeliveryData.nDestinations)) {
			
		} else if(prod.equals(DeliveryData.nProducts-1)) {
			if(demandLeft.get(dest) <= remainingUnits.get(prod) && demandLeft.get(dest) >= 0) {
				ac.add(demandLeft.get(dest));
			}
		} else {
			ac = IntStream.range(0, DeliveryData.unitsAvailable.get(prod)+1).boxed().collect(Collectors.toList());
		}
		ac.sort(Comparator.naturalOrder());
		return ac;
	}

	public DeliveryVertex neighbor(Integer a) {
		List<Integer> rU = new ArrayList<>(this.remainingUnits);
		List<Integer> dL = new ArrayList<>(this.demandLeft);
		Integer dest = this.index/DeliveryData.nProducts;
		Integer prod = this.index%DeliveryData.nProducts;
		rU.set(prod, rU.get(prod) - a);
		dL.set(dest, dL.get(dest) - a);
		return new DeliveryVertex(this.index+1, rU, dL);
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
		List<Integer> cP = new ArrayList<>();
		for(int d = 0; d < DeliveryData.nDestinations; d++) {
			Integer min = Integer.MAX_VALUE/2;
			for(int p = 0; p < DeliveryData.nProducts; p++) {
				if(DeliveryData.storingCosts.get(p).get(d) < min) {
					min = DeliveryData.storingCosts.get(p).get(d);
				}
			}
			cP.add(min);
		}
		return cP;
	}
	
}
