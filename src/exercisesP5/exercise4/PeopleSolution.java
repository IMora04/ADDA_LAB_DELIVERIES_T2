package exercisesP5.exercise4;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.GraphPath;

public record PeopleSolution(List<Integer> pairs, Integer totalAffinity) {

	public static PeopleSolution of(GraphPath<PeopleVertex, PeopleEdge> g) {
		List<Integer> pairs = g.getEdgeList().stream().map(e -> e.action()).toList();
		Integer totalAffinity = (int)g.getEdgeList().stream().mapToDouble(e -> e.weight()).sum();
		return new PeopleSolution(pairs, totalAffinity);
	}
	
	public static PeopleSolution of(PeopleState state) {
		List<Integer> pairs = state.getPastActions();
		Integer totalAffinity = 0;
		for(Integer i = 0; i < pairs.size(); i++) {
			if(i%2==0) {
				continue;
			}
			totalAffinity += PeopleData.affinities.get(pairs.get(i)).get(pairs.get(i-1));
		}
		return new PeopleSolution(new ArrayList<>(pairs), totalAffinity);
	}

	public String toString() {
		String s = "Solution: \n";
		for(Integer i = 0; i < pairs.size() / 2; i++) {
			s += "\tPair " + i + ": " + pairs.get(2*i) + ", " + pairs.get(2*i+1) + "\n";
		}
		s += "\tTotal affinity: " + totalAffinity;
		return s;
	}
	
}
