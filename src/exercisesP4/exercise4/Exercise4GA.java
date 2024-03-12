package exercisesP4.exercise4;

import java.util.List;
import java.util.Set;

import us.lsi.ag.SeqNormalData;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;

public class Exercise4GA implements SeqNormalData<SolutionEx4GA>{

	public Exercise4GA(String file) {
		Exercise4Reader.read(file);
	}

	public ChromosomeType type() {
		return ChromosomeType.Permutation;
	}

	public Double fitnessFunction(List<Integer> value) {
		
		Double of = 0.;
		Double penalty = 0.;
		Double k = 10000.;
		
		for(Integer i = 0; i < value.size() / 2; i++) {
			Integer first = value.get(i*2);
			Integer second = value.get(i*2+1);
			
			of += Ex4Data.getAffinity(first, second);
			
			
			Set<String> firstLanguages = Ex4Data.getLanguages(first);
			if(firstLanguages.stream().filter(l -> Ex4Data.getLanguages(second).contains(l)).count() == 0) {
				penalty += 1;
			}
			
			Integer dif = Ex4Data.getAge(first) - Ex4Data.getAge(second);
			dif = dif >= 0 ? dif:-dif;
			if(dif > 5) {
				penalty += 1;
			}
			
			if(Ex4Data.getNationality(first).equals(Ex4Data.getNationality(second))) {
				penalty += 1;
			}
		}
		
		return of - k * penalty;
	}
	
	public SolutionEx4GA solucion(List<Integer> value) {
		return SolutionEx4GA.of_permutation(value);
	}

	public Integer itemsNumber() {
		return Ex4Data.getNPeople();
	}
		
}
