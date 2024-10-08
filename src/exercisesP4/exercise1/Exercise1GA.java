package exercisesP4.exercise1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import us.lsi.ag.AuxiliaryAg;
import us.lsi.ag.ValuesInRangeData;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;

public class Exercise1GA implements ValuesInRangeData<Integer, SolutionEx1GA>{

	public Exercise1GA(String file) {
		Exercise1Reader.read(file);
	}
	
	public Integer size() {
		return Exercise1LP.getNVarieties();
	}

	public ChromosomeType type() {
		return ChromosomeType.Range;
	}

	public Double fitnessFunction(List<Integer> value) {
		Double penaltySizes = 0.;
		Double penaltyIncompatible = 0.;
		Integer k = 10000;
		Map<Integer, List<Integer>> m = new HashMap<Integer, List<Integer>>();
		
		Integer var = 0;
		for(Integer orch:value) {
			m = SolutionEx1GA.check(orch, var, m);
			var += 1;
		}
		
		for(Entry<Integer, List<Integer>> e:m.entrySet()) {
			List<Integer> varieties = e.getValue();
			
			// Size penalty
			Integer availableSpace = Exercise1LP.getOrchardSize(e.getKey());
			Integer occupiedSpace = varieties.stream().mapToInt(v -> Exercise1LP.getReqSpace(v)).sum();
			penaltySizes += AuxiliaryAg.distanceToLeZero(Double.valueOf(occupiedSpace - availableSpace));
			
			// Incompatibilities penalty
			for(Integer a = 0; a < varieties.size()-1; a++) {
				for(Integer b = a+1; b < varieties.size(); b++) {
					if(Exercise1LP.isIncompatible(varieties.get(a), varieties.get(b))) {
						penaltyIncompatible += 1;
					}
				}
			}

		}
		
		Integer of = SolutionEx1GA.getTotalVarietiesSelected(m);
		
		return Double.valueOf(of - k * (penaltySizes + penaltyIncompatible));
	}

	public SolutionEx1GA solucion(List<Integer> value) {
		return SolutionEx1GA.of_range(value);
	}

	public Integer max(Integer i) {
		return Exercise1LP.getNOrchards();
	}

	public Integer min(Integer i) {
		return -2;
	}
	
}