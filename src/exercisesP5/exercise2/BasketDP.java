package exercisesP5.exercise2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import exercisesP5.exercise1.Exercise1Reader;
import exercisesP5.exercise1.PlantDP;
import exercisesP5.exercise1.PlantPartialSol;
import exercisesP5.exercise1.PlantSolution;
import exercisesP5.exercise1.PlantVertex;

public class BasketDP {
	
	private BasketVertex initialProblem;
	private Double bestOF;
	private BasketSolution bestSol;
	private Map<BasketVertex, BasketPartialSol> mem;
	
	public static BasketDP of() {
		return new BasketDP();
	}
	
	private BasketDP() {
		
	}
	
	public void dpm(BasketVertex initialProblem, Double bestOF, BasketSolution bestSol) {
		this.initialProblem = initialProblem;
		this.bestOF = bestOF;
		this.bestSol = bestSol;
		this.mem = new HashMap<>();
		dpmAux(initialProblem, 0., mem);
	}
	
	public Double heuristic(BasketVertex v, Integer a) {
		return 0.;
	}
		
	public BasketPartialSol dpmAux(BasketVertex v, Double acumPrice, Map<BasketVertex, BasketPartialSol> mem) {
		BasketPartialSol sol = null;
		if(mem.containsKey(v)) {
			sol = mem.get(v);
		} else if(v.index().equals(BasketData.nCategories)) {
			sol = BasketPartialSol.of(null, 0.);
			mem.put(v, sol);
			if(bestOF == null || bestOF > acumPrice) {
				bestOF = acumPrice*1.;
			}
		} else {
			List<BasketPartialSol> solutions = new ArrayList<>();
			for(Integer action:v.actions()) {
				if(bestOF != null && bestOF <= acumPrice + heuristic(v, action)) {
					continue;
				}
				BasketPartialSol nextSol = dpmAux(v.neighbor(action), acumPrice + action * BasketData.prices.get(v.index()), mem);
				if(nextSol != null) {
					solutions.add(BasketPartialSol.of(action, acumPrice + BasketData.prices.get(action)));
				}
			}
			if(!solutions.isEmpty()) {
				sol = solutions.stream().min(Comparator.naturalOrder()).orElse(null);
				mem.put(v, sol);
			}
		}
		return sol;
	}
	
	public BasketSolution solution() {
		List<Integer> actions = new ArrayList<>();
		Integer of = 0;
		BasketVertex v = this.initialProblem;
		BasketPartialSol s = this.mem.get(v);
		if(s == null) {
			return this.bestSol;
		}
		while(s.action() != null) {
			actions.add(s.action());
			of += BasketData.prices.get(s.action());
			v = v.neighbor(s.action());
			s = this.mem.get(v);
		}
		return BasketSolution.of(actions, of);
	}
	
	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		Integer testFile = 3;
		String route = "files/Ejercicio2DatosEntrada" + testFile + ".txt";
		BasketReader.read(route);
		BasketDP dp = BasketDP.of();
		dp.dpm(BasketVertex.initial(), null, null);
		System.out.println(dp.solution());
	}
	
}
