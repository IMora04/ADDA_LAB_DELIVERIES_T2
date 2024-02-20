package workshopLP;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;

public class Head {
	
	public static Integer nProc;
	public static Integer nTasks;
	public static List<Integer> durations;
	
	public static Integer getNProc() {
		return nProc;
	}
	
	public static Integer getNTasks() {
		return nTasks;
	}
	
	public static Integer getDuration(Integer j) {
		return durations.get(j);
	}
	
	public static void main(String[] args) {
		
		nTasks = 6;
		nProc = 4;
		durations = new ArrayList<Integer>(List.of(10, 5, 12, 15, 3, 1));
				
		Locale.setDefault(new Locale("en", "US"));
		try {
			AuxGrammar.generate(Head.class,"files/workshop1.lsi","files/workshop1.lp");
		} catch (IOException e) {
			e.printStackTrace();
		}
		GurobiSolution solution = GurobiLp.gurobi("files/workshop1.lp");
		System.out.println(solution.toString());

	}
	
}
