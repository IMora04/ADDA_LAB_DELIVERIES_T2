package exercisesP5.exercise1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import org.jgrapht.GraphPath;

import us.lsi.graphs.virtual.VirtualVertex;

public record PlantVertex(Integer index, List<List<Integer>> varietiesPlanted, List<Integer> spaceLeft)
	implements VirtualVertex<PlantVertex, PlantEdge, Integer>{
	
	public static Integer nVarieties = PlantData.nVarieties;
	public static Integer nOrchards = PlantData.nOrchards;
	
	public static PlantVertex of(int index, List<List<Integer>> varietiesPlanted, List<Integer> spaceLeft) {
		return new PlantVertex(index, varietiesPlanted, spaceLeft);
	}

	public List<Integer> actions() {
		if(this.index == nVarieties) {
			return new ArrayList<>();
		}
		if(this.index == nVarieties - 1) {
			for(int i = 0; i < nOrchards; i++) {
				if(this.spaceLeft.get(i) < PlantData.reqSpace.get(this.index)) {
					continue;
				}
				boolean anyInc = false;
				for(Integer v : varietiesPlanted.get(i)) {
					if(PlantData.incompatibles.get(v).contains(this.index)) {
						anyInc = true;
						break;
					}
				}
				if(!anyInc) {
					return new ArrayList<>(Arrays.asList(i));
				}
			}
			return new ArrayList<>(Arrays.asList(-1));
		}
		List<Integer> ac = new ArrayList<>(Arrays.asList(-1));
		for(int i = 0; i < nOrchards; i++) {
			if(this.spaceLeft.get(i) < PlantData.reqSpace.get(this.index)) {
				continue;
			}
			boolean anyInc = false;
			for(Integer v : varietiesPlanted.get(i)) {
				if(PlantData.incompatibles.get(v).contains(this.index)) {
					anyInc = true;
					break;
				}
			}
			if(!anyInc) {
				ac.add(i);
			}
		}
		ac.sort(Comparator.reverseOrder());
		return ac;
	}

	public PlantVertex neighbor(Integer a) {
		if(a.equals(-1)) {
			return of(this.index + 1,
					new ArrayList<List<Integer>>(this.varietiesPlanted),
					new ArrayList<Integer>(this.spaceLeft));
		}
		List<List<Integer>> vP = new ArrayList<>();
		for(int i = 0; i < this.varietiesPlanted.size(); i++) {
			List<Integer> ls = new ArrayList<>(this.varietiesPlanted.get(i));
			if(i == a) {
				ls.add(this.index);
			}
			vP.add(ls);
		}
		List<Integer> sL = new ArrayList<>(this.spaceLeft);
		sL.set(a, sL.get(a) - PlantData.reqSpace.get(this.index));
		return of(this.index + 1, vP, sL);
	}

	public PlantEdge edge(Integer a) {
		return PlantEdge.of(this, this.neighbor(a), a);
	} 
	
	public static PlantVertex initial() {
		List<List<Integer>> vP = new ArrayList<List<Integer>>();
		for(int i = 0; i < nOrchards; i++) {
			vP.add(new ArrayList<>());
		}
		List<Integer> sL = new ArrayList<Integer>();
		for(int i:PlantData.orchardSize) {
			sL.add(i);
		}
		return of(0, vP, sL);
	}
	
	public static Predicate<PlantVertex> goal() {
		return v -> v.index().equals(nVarieties);
	}
	
	public static PlantSolution getSolution(GraphPath<PlantVertex, PlantEdge> g) {
		return PlantSolution.of(g);
	}

}
