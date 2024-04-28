package exercisesP5.exercise1;

import us.lsi.graphs.virtual.SimpleEdgeAction;

public record PlantEdge(PlantVertex source, PlantVertex target, Integer action, Double weight) implements SimpleEdgeAction<PlantVertex, Integer> {

	public static PlantEdge of(PlantVertex source, PlantVertex target, Integer action) {
		return new PlantEdge(source, target, action, action.equals(-1) ? 0. : 1.);
	}

}
