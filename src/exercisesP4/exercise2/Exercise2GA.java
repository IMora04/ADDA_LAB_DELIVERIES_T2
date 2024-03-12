package exercisesP4.exercise2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import exercisesP4.exercise3.Exercise3Reader;
import us.lsi.ag.SeqNormalData;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;

public class Exercise2GA implements SeqNormalData<SolutionEx2GA>{
	
	public Exercise2GA(String file) {
		Exercise2Reader.read(file);
	}

	public ChromosomeType type() {
		return ChromosomeType.SubList;
	}

	public Double fitnessFunction(List<Integer> value) {
		Double of = Double.valueOf(value.stream().mapToInt(p -> Exercise2LP.getProductPrice(p)).sum());
		Double penalty = 0.;
		Double k = 1000.;
		
		Double totalRating = 0.;
		List<Integer> totalSpentPerCat = new ArrayList<Integer>();
		for(Integer i = 0; i < Exercise2LP.getNCategories(); i++) {
			totalSpentPerCat.add(0);
		}
		Set<Integer> categoriesVisited = new HashSet<Integer>();
		
		for(Integer product:value) {
			// Avg rating penalty
			totalRating += Exercise2LP.getProductRating(product);
			
			// Budget penalty
			Integer index = Exercise2LP.getProductCategory(product);
			totalSpentPerCat.set(index,
					totalSpentPerCat.get(index) + Exercise2LP.getProductPrice(product));
			
			// One per category penalty
			categoriesVisited.add(index);
		}
		
		if(totalRating / value.size() < 3) {
			penalty += 1;
		}
		
		Long count = totalSpentPerCat.stream().filter(c -> c > Exercise2LP.getBudgetPerCategory()).count();
		penalty += count;
		
		if(categoriesVisited.size() < Exercise2LP.getNCategories()) {
			penalty += 1;
		}
		
		return - of - k * penalty;
	}

	public SolutionEx2GA solucion(List<Integer> value) {
		return SolutionEx2GA.of_sublist(value);
	}

	public Integer itemsNumber() {
		return Exercise2LP.getNProducts();
	}

	public Integer maxMultiplicity(int index) {
		return 1;
	}
	
	/*
	 * 
	 * If valuesInRange
	 * 	bin? YES. size products, selected as 1
	 * 		avg >= 3 NO
	 * 		price < budget NO
	 * 		1 per cat NO
	 * 		Just once YES
	 * 
	 * If seqNormalData
	 * 	subList? YES. Objects taken
	 * 		avg >= 3 NO
	 * 		price < budget NO
	 * 		1 per cat NO
	 * 		Just once YES
	 * 
	 * 
	 */

}
