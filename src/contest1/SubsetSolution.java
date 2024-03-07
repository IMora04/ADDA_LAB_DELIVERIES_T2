package contest1;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import contest1.SubsetsData.Subset;
import us.lsi.common.List2;
import us.lsi.common.String2;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve_test.AuxGrammar2;

public class SubsetSolution {


	public static SubsetSolution createFromBinary(List<Integer> ls) {		
		return new SubsetSolution(ls);
	}
	public static SubsetSolution createFromSublist(List<Integer> ls) {
		List<Integer> binaryLs = List2.nCopies(0, SubsetsData.getNumSubsets());
		for (int subset : ls) {
			binaryLs.set(subset, 1);
		}
		return new SubsetSolution(ls);
	}
	
	Double total;
	List<Subset> subconjuntos;	

	/**
	 * @param ls A binary list stating if the subset at position i is selected or not.
	 */
	private SubsetSolution(List<Integer> ls) {
		total = 0.;
		subconjuntos = List2.empty();
		for(int i=0; i<ls.size(); i++) {
			if(ls.get(i)>0) {
				total += SubsetsData.getWeight(i);
				subconjuntos.add(SubsetsData.getSubSet(i));
			}
		}
	}

	@Override
	public String toString() {
		String s = subconjuntos.stream().map(e -> "S"+e.id())
		.collect(Collectors.joining(", ", "Cursos elegidos: {", "}\n"));
		return String.format("%sCoste Total: %.1f", s, total);	
	}

	
	public static void main(String[] args) {		
		
		SubsetsData.iniDatos("files/testForCompetition.txt");
		try {
			AuxGrammar2.generate(SubsetsData.class,"LSI files/contest1.lsi","LP files/contest1.lp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GurobiSolution s = GurobiLp.solveSolution("lsi_files/subsets.lp");
		String2.toConsole(s.toString((k,v)->v>0));

	}

}
