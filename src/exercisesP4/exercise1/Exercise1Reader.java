package exercisesP4.exercise1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Exercise1Reader {
	
	public static void read(String file) {
		
		Exercise1LP.orchardSize = new ArrayList<>();
		Exercise1LP.reqSpace = new ArrayList<>();
		Exercise1LP.incompatibles = new HashMap<Integer, List<Integer>>();
		firstRead(file, Exercise1LP.orchardSize);
		
	}
	
	public static void firstRead(String file, List<Integer> oSize) {
		List<String> lines = null;
		
		try {
			lines = new ArrayList<>(Files.lines(Path.of(file)).skip(1).toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		String delimiter = "// VARIEDADES";
		
		for(String s:lines) {
			if(s.equals(delimiter)) {
				Exercise1LP.nOrchards = Exercise1LP.orchardSize.size();
				secondRead(lines.subList(
						lines.indexOf(delimiter) + 1, lines.size()),
						Exercise1LP.reqSpace,
						Exercise1LP.incompatibles
						);
				break;
			}
			
			oSize.add(
				Integer.valueOf(s.substring(s.indexOf("H")+1, s.indexOf(":"))),
				Integer.valueOf(s.substring(s.indexOf("=")+1, s.indexOf(";")))
			);
		}

	}
	
	public static void secondRead(List<String> lines, List<Integer> reqSpace, Map<Integer, List<Integer>> map) {
		for(String s:lines) {
			Integer index = 0;
			String[] sep = s.split(";", 2);
			String space = sep[0];
			String inc = sep[1];
			
			index = Integer.valueOf(space.substring(space.indexOf("V")+1, space.indexOf("-")).trim());
			
			reqSpace.add(
				index,
				Integer.valueOf(space.substring(space.indexOf("=")+1, space.length()))
			);
			
			inc = inc.replaceAll(" incomp=", "");
			inc = inc.replaceAll(";", "");
			inc = inc.replaceAll("V", "");
			List<Integer> toAdd = Arrays.asList(inc.split(",")).stream().map(i -> Integer.valueOf(i)).toList();
			
			if(map.containsKey(index)) {
				map.get(index).addAll(toAdd);
			} else {
				map.put(index, toAdd);
			}
		}
		
		Exercise1LP.nVarieties = reqSpace.size();
	}
	
	public static void main(String[] args) {
		
		read("files/Ejercicio1DatosEntrada3.txt");
		System.out.println(Exercise1LP.orchardSize);
		System.out.println(Exercise1LP.reqSpace);
		System.out.println(Exercise1LP.incompatibles);
		
	}
	
}