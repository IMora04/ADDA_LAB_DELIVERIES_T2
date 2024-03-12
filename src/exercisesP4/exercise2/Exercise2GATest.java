package exercisesP4.exercise2;

import java.util.List;
import java.util.Locale;

import exercisesP4.exercise3.Exercise3GA;
import exercisesP4.exercise3.SolutionEx3GA;
import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agstopping.StoppingConditionFactory;

public class Exercise2GATest {
	
	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));
		
		AlgoritmoAG.ELITISM_RATE  = 0.10;
		AlgoritmoAG.CROSSOVER_RATE = 0.95;
		AlgoritmoAG.MUTATION_RATE = 0.8;
		AlgoritmoAG.POPULATION_SIZE = 1000;
		
		StoppingConditionFactory.NUM_GENERATIONS = 1000;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionFactory.StoppingConditionType.GenerationCount;
		
		Exercise2GA p = new Exercise2GA("files/Ejercicio2DatosEntrada3.txt");
		
		
		AlgoritmoAG<List<Integer>,SolutionEx2GA> ap = AlgoritmoAG.of(p);
		ap.ejecuta();
		

		System.out.println("================================");
		System.out.println(ap.bestSolution());
		System.out.println("================================");
	}

}
