package exercisesP4.exercise2;

import java.util.List;

import us.lsi.ag.SeqNormalData;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;

public class Exercise2GA implements SeqNormalData<SolutionEx2GA>{

	public ChromosomeType type() {
		return ChromosomeType.SubList;
	}

	public Double fitnessFunction(List<Integer> value) {
		Double of = Double.valueOf(value.stream().mapToInt(p -> Exercise2LP.getProductPrice(p)).sum());
		Double penalty = 0.;
		
		Double totalRating = 0.;
		for(Integer product:value) {
			totalRating += Exercise2LP.getProductRating(product);
		}
		if(totalRating / value.size() < 3) {
			penalty += 1;
		}
		
		return null;
	}

	public SolutionEx2GA solucion(List<Integer> value) {
		return null;
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
