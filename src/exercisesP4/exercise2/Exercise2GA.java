package exercisesP4.exercise2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import us.lsi.ag.AuxiliaryAg;
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
		
		penalty += AuxiliaryAg.distanceToGeZero(Double.valueOf((totalRating / value.size()) - 3));
		
		for(Integer totalSpent : totalSpentPerCat) {
			penalty += AuxiliaryAg.distanceToLeZero(Double.valueOf(totalSpent - Exercise2LP.getBudgetPerCategory()));
		}
		
		penalty += AuxiliaryAg.distanceToGeZero(Double.valueOf(categoriesVisited.size() - Exercise2LP.getNCategories()));
		
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

}
