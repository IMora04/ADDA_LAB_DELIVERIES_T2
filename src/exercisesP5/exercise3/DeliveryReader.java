package exercisesP5.exercise3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class DeliveryReader {
	
	public static void read(String file) {
		DeliveryData.minDemands = new ArrayList<>();
		DeliveryData.unitsAvailable = new ArrayList<>();
		DeliveryData.storingCosts = new HashMap<Integer, List<Integer>>();

		List<String> lines = null;
		try {
			lines = Files.lines(Path.of(file)).skip(1).collect(Collectors.toList());
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		String delimiter = "// PRODUCTOS Id_producto -> unidades=integer;coste_almacenamiento=(destino:coste)";

		for(String s:lines) {
			if(s.equals(delimiter)) {
				break;
			}
			s = s.replace(";", "");
			DeliveryData.minDemands.add(Integer.valueOf(s.split("=")[1]));
		}
		lines = lines.subList(lines.indexOf(delimiter) + 1, lines.size());
		
		for(String s:lines) {
			
			String[] splittedLine = s.split(";");
			String [] firstField = splittedLine[0].split("=");
			
			DeliveryData.unitsAvailable.add(
					Integer.valueOf(firstField[1]));
			
			Integer key = Integer.valueOf(
					firstField[0].replace("P", "").split("-")[0].trim());
			
			if(DeliveryData.storingCosts.containsKey(key)) {
				System.out.println("Check input data: Repeated product index");
			} else {
				String[] storingCostsPerDst = splittedLine[1].split("=")[1].split(",");
				DeliveryData.storingCosts.put(key, new ArrayList<Integer>());
				for(String c:storingCostsPerDst) {
					DeliveryData.storingCosts.get(key).add(
							Integer.valueOf(
									c.replace(")", "").split(":")[1]));
				}
			}
		}
		System.out.println(DeliveryData.storingCosts);
		DeliveryData.nDestinations = DeliveryData.minDemands.size();
		DeliveryData.nProducts = DeliveryData.unitsAvailable.size();
		System.out.println(DeliveryData.nDestinations);

	}
	
	public static void main(String[] args) {
		read("files/Ejercicio3DatosEntrada1.txt");
	}

}
