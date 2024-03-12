package exercisesP4.exercise4;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Ex4Data {
	
	public static Map<Integer, Map<Integer, Integer>> affinities;
	public static Map<Integer, Set<String>> languages;
	public static List<Integer> ages;
	public static List<String> nationalities;
	
	public static Integer getNPeople() {
		return ages.size();
	}
	
	public static Integer getAffinity(Integer i, Integer j) {
		return affinities.get(i).get(j);
	}
	
	public static Set<String> getLanguages(Integer i) {
		return languages.get(i);
	}
	
	public static Integer getAge(Integer i) {
		return ages.get(i);
	}
	
	public static String getNationality(Integer i) {
		return nationalities.get(i);
	}

}
