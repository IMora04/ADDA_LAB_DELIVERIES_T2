package exercisesP4.exercise4;

import java.util.List;

public class SolutionEx4GA {
	
	private List<Integer> pairs;
	private Integer totalAffinity;
	
	private SolutionEx4GA(List<Integer> value) {
		pairs = value;
		totalAffinity = 0;
		for(Integer i = 0; i < value.size() / 2; i++) {
			totalAffinity += Ex4Data.getAffinity(value.get(i*2), value.get(i*2+1));
		}
	}
	
	public static SolutionEx4GA of_permutation(List<Integer> value) {
		return new SolutionEx4GA(value);
	}
	
	public String toString() {
		String res = "LIST OF PAIRS:\n\n";
		
		for(Integer i = 0; i < pairs.size() / 2; i++) {
			res += "Person " + pairs.get(i*2) + " with person " + pairs.get(i*2+1) + "\n";
		}
		
		res += "TOTAL AFFINITY: " + totalAffinity;
				
		return res;
	}

}
