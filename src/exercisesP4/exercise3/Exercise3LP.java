package exercisesP4.exercise3;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;

public class Exercise3LP {
		
	public static List<Integer> minDemands;
	public static List<Integer> productsAvailable;
	public static Map<Integer, List<Integer>> cost;
	
	public static Integer getNProducts() {
		return productsAvailable.size();
	}
	public static Integer getNDestinations() {
		return minDemands.size();
	}
	public static Integer getMinDemand(Integer i) {
		return minDemands.get(i);
	}
	public static Integer getProductsAvailable(Integer i) { 
		return productsAvailable.get(i);
	}
	public static Integer getCost(Integer p, Integer d) {
		return cost.get(p).get(d);
	}
	
	public static void main(String[] args) {	
		
		String file = "files/Ejercicio3DatosEntrada1.txt";
		Exercise3Reader.read(file);
		System.out.println(getNProducts());
		
		try {
			AuxGrammar.generate(Exercise3LP.class,"LSI files/productsDestination.lsi","LP files/productsDestination.lp");
		} catch (IOException e) {
			e.printStackTrace();
		}
		GurobiSolution solution = GurobiLp.gurobi("LP files/productsDestination.lp");
		System.out.println(solution.toString((s, d) -> d > 0.));

	}

}
