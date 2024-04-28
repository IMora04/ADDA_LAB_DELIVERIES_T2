package exercisesP5.exercise2;

import java.util.List;

import org.jgrapht.GraphPath;

public record BasketSolution(List<Integer> chosenProducts, Integer totalPrice) {

	public static BasketSolution of(GraphPath<BasketVertex, BasketEdge> g) {
		List<Integer> cP = g.getEdgeList().stream().map(e -> e.action()).toList();
		Integer tP = (int) g.getEdgeList().stream().mapToDouble(e -> e.weight()).sum();
		return new BasketSolution(cP, tP);
	}
	
	public String toString() {
		String res = "Solution: \n";
		res += "\tChosen products: ";
		for(Integer i:chosenProducts) {
			res += "Product " + i + ", ";
		}
		res = res.trim().substring(0, res.length()-1);
		res += "\n\tTotal price of the basket: " + totalPrice;
		return res;
	}
	
}
