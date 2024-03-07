package contest1;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import us.lsi.common.Files2;
import us.lsi.common.List2;
import us.lsi.common.Set2;
import us.lsi.common.String2;

public class SubsetsData {
	
	public static record Subset(Integer id, Set<Integer> elements, Double weight) {
		public static int cont;
		public static Subset create(String s) {
			String[] v = s.split(":");
			return new Subset(cont++, Set2.parse(v[0].trim(), "{,}" , Integer::parseInt),
				Double.parseDouble(v[1].trim()));
		}
		
		@Override
		public String toString() {
			return String.format("S%d = %s; Weight = %.1f", id, elements, weight);
		}	
	}

	private static List<Integer> universe;
	private static List<Subset> subsets;

	public static void iniDatos(String file) {
		Subset.cont=0;
		subsets = List2.empty();
		Set<Integer> elems = new TreeSet<>();
		for(String linea: Files2.linesFromFile(file)) {
			Subset s = Subset.create(linea);
			subsets.add(s);
			elems.addAll(s.elements());
		}
		universe =  List2.ofCollection(elems);
		toConsole();
	}
		
	public static Subset getSubSet(int subset_index){
		return subsets.get(subset_index);
	}
	public static Set<Integer> getElementos(int element_index){
		return subsets.get(element_index).elements();
	}
	public static List<Subset> getSubsets(){
		return subsets;
	}
	public static List<Integer> getUniverse(){
		return universe;
	}
	
	public static void toConsole() {
		String2.toConsole("Universo: %s", universe);
		String2.toConsole(subsets,"Subconjuntos");
		String2.toConsole(String2.linea());		
	}
	
	// Test de la lectura del fichero
	public static void main(String[] args) {
		iniDatos("files/testForCompetition.txt");
	}
	
	/******************************
	 * Static method using indices
	 ******************************/
	
	public static Integer getNumSubsets() {
		return subsets.size();
	}
	public static Integer getNumElements() {
		return universe.size();
	}
	public static Double getWeight(Integer subset_index) {
		return subsets.get(subset_index).weight();
	}
	public static Integer containsElement(Integer subset_index, Integer element_index) {
		return subsets.get(subset_index).elements().contains(universe.get(element_index))? 1: 0;
	}



}