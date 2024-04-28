package exercisesP5.exercise2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BasketReader {
	
	public static void read(String file) {
		BasketData.budget = 0;
		BasketData.nProducts = 0;
		BasketData.prices = new ArrayList<>();;
		BasketData.categories = new ArrayList<>();;
		BasketData.ratings = new ArrayList<>();
		
		List<String> lines = null;
		try {
			lines = Files.lines(Path.of(file)).collect(Collectors.toList());
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		BasketData.budget = Integer.valueOf(lines.get(0).split("=")[1].trim());
		lines = lines.subList(2, lines.size());
		
		for(String s:lines) {
			String[] splittedLine = s.split(":");
			BasketData.nProducts += 1;
			BasketData.prices.add(Integer.valueOf(splittedLine[1]));
			BasketData.categories.add(Integer.valueOf(splittedLine[2]));
			BasketData.ratings.add(Integer.valueOf(splittedLine[3]));
		}
		BasketData.nCategories = BasketData.categories.stream().collect(Collectors.toSet()).size();
	}
	
}
