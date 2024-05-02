package exercisesP5.exercise1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.GraphPath;

public record PlantSolution(Map<Integer, List<Integer>> m, Integer totalVarieties) {
	
	public static PlantSolution of(GraphPath<PlantVertex, PlantEdge> g) {
		Integer totalVarieties = 0;
		Map<Integer, List<Integer>> m = new HashMap<>();
		PlantVertex last = g.getEndVertex();
		for(int i = 0; i < last.varietiesPlanted().size(); i++) {
			m.put(i, last.varietiesPlanted().get(i));
			totalVarieties += last.varietiesPlanted().get(i).size();
		}
		return new PlantSolution(m, totalVarieties);
	}
	
	public static PlantSolution of(PlantVertex finalVertex) {
		Integer totalVarieties = 0;
		Map<Integer, List<Integer>> m = new HashMap<>();
		for(int i = 0; i < finalVertex.varietiesPlanted().size(); i++) {
			m.put(i, finalVertex.varietiesPlanted().get(i));
			totalVarieties += finalVertex.varietiesPlanted().get(i).size();
		}
		return new PlantSolution(m, totalVarieties);
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
