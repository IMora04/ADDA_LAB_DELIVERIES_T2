package exercisesP5.exercise1;

import java.util.ArrayList;
import java.util.List;

import us.lsi.graphs.virtual.VirtualVertex;

public record PlantVertex(Integer index, List<Integer> plantedVarieties, List<Integer> spaceLeft) implements VirtualVertex<PlantVertex, PlantEdge, Integer>{

	public static PlantVertex of(Integer i, List<Integer> pV, List<Integer> sL) {
		return new PlantVertex(i, pV, sL);
	}
	
	public List<Integer> actions() {
		List<Integer> actions = new ArrayList<>();
		actions.add(-1);
		for(Integer i = 0; i < PlantData.nOrchards; i++) {
			if(PlantData.reqSpace.get(this.index) <= spaceLeft.get(i)) {
				actions.add(i);
			}
		}
		return actions;
	}

	public PlantVertex neighbor(Integer a) {
		List<Integer> pV = new ArrayList<>(plantedVarieties);
		List<Integer> sL = new ArrayList<>(spaceLeft);
		if(!a.equals(-1)) {
			pV.set(a, pV.get(a) + 1);
			sL.set(a, sL.get(a) - PlantData.reqSpace.get(this.index));
		}
		return PlantVertex.of(index+1, pV, sL);
	}

	public PlantEdge edge(Integer a) {
		// TODO Auto-generated method stub
		return PlantEdge.of(this, this.neighbor(a), );
	}

}
