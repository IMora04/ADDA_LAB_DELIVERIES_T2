package exercisesP4.exercise1;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;

public class Exercise1LP {
		
	public static Integer nOrchards;
	public static Integer nVarieties;
	public static List<Integer> orchardSize;
	public static List<Integer> reqSpace;
	public static Map<Integer, List<Integer>> incompatibles;
		
	public static Integer getNOrchards() {
		return nOrchards;
	}

	public static Integer getNVarieties() {
		return nVarieties;
	}
	
	public static Integer getOrchardSize(Integer i) {
		return orchardSize.get(i);
	}
	
	public static Integer getReqSpace(Integer i) {
		return reqSpace.get(i);
	}
	
	public static Boolean isIncompatible(Integer i, Integer j) {
		return incompatibles.get(i).contains(j) || incompatibles.get(j).contains(i);
	}

	public static void main(String[] args) {	
		
		String file = "files/Ejercicio1DatosEntrada1.txt";
		Exercise1Reader.read(file);
		System.out.println(getNOrchards());
		
		try {
			AuxGrammar.generate(Exercise1LP.class,"LSI files/orchards.lsi","LP files/orchards.lp");
		} catch (IOException e) {
			e.printStackTrace();
		}
		GurobiSolution solution = GurobiLp.gurobi("LP files/orchards.lp");
		System.out.println(solution.toString((s, d) -> d > 0.));

	}
	
}
