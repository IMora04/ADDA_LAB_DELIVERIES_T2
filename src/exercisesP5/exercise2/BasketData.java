package exercisesP5.exercise2;

import java.util.List;

public class BasketData {
	
	/*
2. Se desea componer una cesta de navidad que incluya diversos productos. De cada
producto se conoce su categoría, su precio, y su valoración (entero entre 1 y 5). Determine
la composición de la cesta de forma que:
• entre los productos seleccionados deben cubrir todas las categorías,
• es necesario que la media de las valoraciones de todos los productos
seleccionados sea mayor o igual que 3,
• el precio total de los productos seleccionados de una misma categoría no puede
superar un presupuesto dado (este presupuesto es un dato común a todas las
categorías), y
• se desea minimizar el precio total de la cesta.
	 */

	public static List<Integer> categories;
	public static List<Integer> prices;
	public static List<Integer> ratings;
	public static Integer nCategories;
	public static Integer budget;
	public static Integer nProducts;

}
