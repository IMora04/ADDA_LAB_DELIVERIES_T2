package exercisesP5.exercise3;

import us.lsi.graphs.virtual.SimpleEdgeAction;

public record DeliveryEdge(DeliveryVertex source, DeliveryVertex target, Integer action, Double weight)
	implements SimpleEdgeAction<DeliveryVertex, Integer> {
	
	public static DeliveryEdge of(DeliveryVertex source, DeliveryVertex target, Integer action) {
		Integer dest = source.index()/DeliveryData.nProducts;
		Integer prod = source.index()%DeliveryData.nProducts;
		return new DeliveryEdge(source, target, action, 1.*action*DeliveryData.storingCosts.get(prod).get(dest));
	}
	
}
