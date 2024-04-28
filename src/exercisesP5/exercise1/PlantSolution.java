package exercisesP5.exercise1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.GraphPath;

public record PlantSolution(Map<Integer, List<Integer>> m, Integer totalVarieties) {
	
	public static PlantSolution of(GraphPath<PlantVertex, PlantEdge> g) {
		Integer tV = 0;
		Map<Integer, List<Integer>> m = new HashMap<>();
		PlantVertex last = g.getEndVertex();
		for(int i = 0; i < last.varietiesPlanted().size(); i++) {
			m.put(i, last.varietiesPlanted().get(i));
			tV += last.varietiesPlanted().get(i).size();
		}
		return new PlantSolution(m, tV);
	}

	public String toString() {
		String s = "Solution: \n";
		for(Map.Entry<Integer, List<Integer>> e:m.entrySet()) {
			s += ("\tOrchard " + e.getKey() +": " + e.getValue() + "\n");
		}
		s += "\tTotal varieties planted: " + totalVarieties;
		return s;
	}
	
	
	
}
