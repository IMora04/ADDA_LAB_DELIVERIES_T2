package exercisesP4.exercise2;

import java.io.IOException;
import java.util.List;

import exercisesP4.exercise1.Exercise1LP;
import exercisesP4.exercise1.Exercise1Reader;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;

public class Exercise2LP {
	
	public static Integer budgetPerCategory;
	public static Integer nProducts;
	public static Integer nCategories;
	public static List<Integer> productPrices;
	public static List<Integer> productCategories;
	public static List<Integer> productRatings;
	
	public static Integer getNProducts() {
		return nProducts;
	}
	public static Integer getNCategories() {
		return nCategories;
	}
	public static Integer getBudgetPerCategory() {
		return budgetPerCategory;
	}
	public static Integer getProductPrice(Integer i) {
		return productPrices.get(i);
	}
	public static Integer getProductCategory(Integer i) {
		return productCategories.get(i);
	}
	public static Integer getProductRating(Integer i) {
		return productRatings.get(i);
	}
	
	public static void main(String[] args) {	
		
		String file = "files/Ejercicio2DatosEntrada1.txt";
		Exercise1Reader.read(file);
		System.out.println(getNProducts());
		
		try {
			AuxGrammar.generate(Exercise1LP.class,"LSI files/christmasBasket.lsi","LP files/christmasBasket.lp");
		} catch (IOException e) {
			e.printStackTrace();
		}
		GurobiSolution solution = GurobiLp.gurobi("LP files/orchards.lp");
		System.out.println(solution.toString((s, d) -> d > 0.));

	}
	
}
