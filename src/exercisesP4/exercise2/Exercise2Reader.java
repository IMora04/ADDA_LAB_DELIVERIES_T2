package exercisesP4.exercise2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Exercise2Reader {
	
	public static void read(String file) {
		Exercise2LP.budgetPerCategory = 0;
		Exercise2LP.nProducts = 0;
		Exercise2LP.productPrices = new ArrayList<>();;
		Exercise2LP.productCategories = new ArrayList<>();;
		Exercise2LP.productRatings = new ArrayList<>();
		
		List<String> lines = null;
		try {
			lines = Files.lines(Path.of(file)).collect(Collectors.toList());
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		Exercise2LP.budgetPerCategory = Integer.valueOf(lines.get(0).split("=")[1].trim());
		lines = lines.subList(2, lines.size());
		
		for(String s:lines) {
			String[] splittedLine = s.split(":");
			Exercise2LP.nProducts += 1;
			Exercise2LP.productPrices.add(Integer.valueOf(splittedLine[1]));
			Exercise2LP.productCategories.add(Integer.valueOf(splittedLine[2]));
			Exercise2LP.productRatings.add(Integer.valueOf(splittedLine[3]));
		}
		
	}
	
	public static void main(String[] args) {
		read("files/Ejercicio2DatosEntrada1.txt");
	}

}
