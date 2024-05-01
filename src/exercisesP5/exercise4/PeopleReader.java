package exercisesP5.exercise4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PeopleReader {
	
	public static void read(String file) {
		PeopleData.affinities = new HashMap<>();
		PeopleData.languages = new HashMap<>();
		PeopleData.ages = new ArrayList<>();
		PeopleData.nationalities = new ArrayList<>();

		List<String> lines = null;
		
		try {
			lines = Files.lines(Path.of(file)).skip(1).collect(Collectors.toList());
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		for(String s:lines) {
			
			String[] splittedLine = s.split(";");
			String[] firstField = splittedLine[0].split("-");
			Integer key = Integer.valueOf(
					firstField[0].trim().replace("P", ""));
			
			// Add age
			PeopleData.ages.add(Integer.valueOf(firstField[1].split("=")[1].trim()));
			
			// Add languages
			String[] languageSet = splittedLine[1].replace("(", "").replace(")", "")
					.split("=")[1].split(",");
			List<String> languageList = new ArrayList<>();
			for(String l:languageSet) {
				languageList.add(l.trim());
			}
			
			PeopleData.languages.put(key, new HashSet<>(languageList));
			
			// Add nationality
			PeopleData.nationalities.add(splittedLine[2].split("=")[1].trim());
			
			//Add affinities
			Map<Integer, Integer> m = new HashMap<>();
			for(String aff : splittedLine[3].replace("(", "").replace(")", "").split("=")[1].split(",")) {
				String[] pair = aff.split(":");
				m.put(Integer.valueOf(pair[0]), Integer.valueOf(pair[1]));
			}
			PeopleData.affinities.put(key, m);
		}
		
		PeopleData.nPeople = PeopleData.ages.size();
		
	}


}
