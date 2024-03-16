package exercisesP4.exercise3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SolutionEx3GA {

	private Map<Integer, List<Integer>> m;
	private Integer totalCost;
	
	public static SolutionEx3GA of_range(List<Integer> value) {
		return new SolutionEx3GA(value);
	}
	
	private SolutionEx3GA(List<Integer> ls) {
		totalCost = 0;
		m = new HashMap<Integer, List<Integer>>();
		
		for(Integer prod = 0; prod < Exercise3LP.getNProducts(); prod++) {
			// Initialize list of number of units sent to each destination. One per product type
			m.put(prod, new ArrayList<Integer>());
			
			// Iterate over the chromosome (list) as a matrix
			for(Integer dest = 0; dest < Exercise3LP.getNDestinations(); dest++) {
				m.get(prod).add(ls.get(dest*Exercise3LP.getNProducts()+prod));
				totalCost += Exercise3LP.getCost(prod, dest) * 
						ls.get(dest*Exercise3LP.getNProducts()+prod);
				
			}
		}
	}
	
	public String toString() {
		String res = "MAP: Each destination has a list" + "\n" +
				"of the number of products sent," + "\n" + "for each product" + "\n\n";
		for(Entry<Integer, List<Integer>> e:m.entrySet()) {
			
			String list = "[";
			for(Integer i:e.getValue()) {
				list += i + ", ";
			}
			list = list.substring(0, list.length()-2);
			list += "]";
			
			res += e.getKey() + ": " + list + "\n";
			
		}
		
		res += "\n" + "TOTAL COST: " + totalCost;
		
		return res;
	}

}
