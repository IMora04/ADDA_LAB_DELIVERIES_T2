package exercisesP5.exercise4;

import us.lsi.graphs.virtual.SimpleEdgeAction;

public record PeopleEdge(PeopleVertex source, PeopleVertex target, Integer action, Double weight)
	implements SimpleEdgeAction<PeopleVertex, Integer> {

	public static PeopleEdge of(PeopleVertex source, PeopleVertex target, Integer a) {
		Double w = 0.;
		if(source.index()%2!=0) {
			w = PeopleVertex.getAffinity(a, source.lastChosen()) * 1.;
		}
		return new PeopleEdge(source, target, a, w);
	}
		
}
