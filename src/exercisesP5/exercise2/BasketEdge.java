package exercisesP5.exercise2;

import us.lsi.graphs.virtual.SimpleEdgeAction;

public record BasketEdge(BasketVertex source, BasketVertex target, Integer action, Double weight) implements SimpleEdgeAction<BasketVertex, Integer> {

	public static BasketEdge of(BasketVertex source, BasketVertex target, Integer action) {
		return new BasketEdge(source, target, action, 1.*BasketData.prices.get(action));
	}
	
}
