package exercisesP4.exercise1;

import java.util.List;
import java.util.Locale;

import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agstopping.StoppingConditionFactory;

public class Exercise1AGTest {
	
	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		
		AlgoritmoAG.ELITISM_RATE  = 0.10;
		AlgoritmoAG.CROSSOVER_RATE = 0.95;
		AlgoritmoAG.MUTATION_RATE = 0.8;
		AlgoritmoAG.POPULATION_SIZE = 1000;
		
		StoppingConditionFactory.NUM_GENERATIONS = 1000;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionFactory.StoppingConditionType.GenerationCount;
		
		Exercise1AG p = new Exercise1AG("files/Ejercicio1DatosEntrada1.txt");
		
		
		AlgoritmoAG<List<Integer>,SolutionEx1AG> ap = AlgoritmoAG.of(p);
		ap.ejecuta();
		

		System.out.println("================================");
		System.out.println(ap.bestSolution());
		System.out.println("================================");
	}


}
