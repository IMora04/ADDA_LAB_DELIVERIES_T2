package exercisesP4.exercise2;

import java.util.List;

public class SolutionEx2GA {
	
	private List<Integer> productsSelected;
	private Integer totalCost;
	
	public static SolutionEx2GA of_sublist(List<Integer> value) {
		return new SolutionEx2GA(value);
	}
	
	public SolutionEx2GA(List<Integer> value) {
		totalCost = 0;
		productsSelected = value;
		
		for(Integer p:productsSelected) {
			totalCost += Exercise2LP.getProductPrice(p);
		}
	}
	
	public String toString() {
		String res = "LIST OF SELECTED PRODUCTS: \n";
		
		for(Integer p:productsSelected) {
			res += "Product " + p + "\n";
		}
		
		res += "\nTOTAL COST: " + totalCost;
				
		return res;
	}

}
