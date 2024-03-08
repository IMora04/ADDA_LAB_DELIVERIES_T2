package exercisesP4.exercise1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SolutionEx1GA {
	
	private Map<Integer, List<Integer>> m;
	private Integer totalVarieties;


	public static SolutionEx1GA of_range(List<Integer> ls) {
		return new SolutionEx1GA(ls);
	}
	
	private SolutionEx1GA(List<Integer> ls) {
		m = new HashMap<Integer, List<Integer>>();
		Integer var = 0;
		for(Integer orch:ls) {
			m = check(orch, var, m);
			var += 1;
		}
		totalVarieties = getTotalVarietiesSelected(m);
	}
	
	public static Map<Integer, List<Integer>> check(Integer orch, Integer var, Map<Integer, List<Integer>> m) {
		if(orch.compareTo(0) < 0) {
			return m;
		}
		if(m.containsKey(orch)) {
			m.get(orch).add(var);
		} else {
			List<Integer> l = new ArrayList<Integer>();
			l.add(var);
			m.put(orch, l);
		}
		return m;
	}
	
	public static Integer getTotalVarietiesSelected(Map<Integer, List<Integer>> m) {
		return m.values().stream().flatMap(t -> t.stream()).toList().size();
	}

	public String toString() {
		String res = "MAP: \n";
		for(Entry<Integer, List<Integer>> e:m.entrySet()) {
			
			String list = "[";
			for(Integer i:e.getValue()) {
				list += i + ", ";
			}
			list = list.substring(0, list.length()-2);
			list += "]";
			
			res += e.getKey() + ": " + list + "\n";
			
		}
		
		res += "TOTAL VARIETIES USED: " + totalVarieties;
		
		return res;
	}
	
}
