package exercisesP5.exercise1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PlantDP {
	
	private PlantVertex initialProblem;
	private Double bestOF;
	private PlantSolution bestSol;
	private Map<PlantVertex, PlantPartialSol> mem;
	
	public static PlantDP of() {
		return new PlantDP();
	}
	
	private PlantDP() {
		
	}
	
	public void dpm(PlantVertex initialProblem, Double bestOF, PlantSolution bestSol) {
		this.initialProblem = initialProblem;
		this.bestOF = bestOF;
		this.bestSol = bestSol;
		this.mem = new HashMap<>();
		dpm(initialProblem, mem);
	}
	
	public PlantPartialSol dpm(PlantVertex v, Map<PlantVertex, PlantPartialSol> mem) {
		PlantPartialSol sol = null;
		if(mem.containsKey(v)) {
			
			sol = mem.get(v);
			
		} else if(v.index().equals(PlantData.nVarieties)) {
			
			sol = PlantPartialSol.of(null, 0.);
			mem.put(v, sol);
			Double of = 1.*v.varietiesPlanted().stream().flatMap(l -> l.stream()).distinct().toList().size();
			if(bestOF == null || bestOF < of) {
				bestOF = of;
			}
			
		} else {
			
			List<PlantPartialSol> solutions = new ArrayList<>();
			Double of = 1.*v.varietiesPlanted().stream().flatMap(l -> l.stream()).distinct().toList().size();
			for(Integer a:v.actions()) {
				Double h = (PlantData.nVarieties - (v.index() + 1)) + (a.equals(-1)?0.:1.);
				if(bestOF >= of + h) {
					continue;
				}
				PlantPartialSol nextSol = dpm(v.neighbor(a), mem);
				if(nextSol != null) {
					solutions.add(PlantPartialSol.of(a, nextSol.weight() + (a.equals(-1)?0.:1.)));
				}
			}
			if(!solutions.isEmpty()) {
				sol = solutions.stream().max(Comparator.naturalOrder()).orElse(null);
				mem.put(v, sol);
			}
		
		}
		
		return sol;
	}
	
	public PlantSolution solution() {
		PlantVertex v = this.initialProblem;
		PlantPartialSol s = this.mem.get(v);
		if(s == null) {
			return this.bestSol;
		}
		while(s.action() != null) {
			v = v.neighbor(s.action());
			s = this.mem.get(v);
		}
		return PlantSolution.of(v);
	}
	
	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		Integer testFile = 1;
		String route = "files/Ejercicio1DatosEntrada" + testFile + ".txt";
		Exercise1Reader.read(route);

		PlantDP dp = PlantDP.of();
		dp.dpm(PlantVertex.initial(), 0., null);
		System.out.println(dp.solution());
	}
}
