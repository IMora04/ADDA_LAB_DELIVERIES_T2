package contest1;

import java.util.List;

import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.ag.agstopping.StoppingConditionFactory.StoppingConditionType;

public class SubsetsGenetics {
	public static void main(String[] args) {
		AlgoritmoAG.ELITISM_RATE  = 0.20;
		AlgoritmoAG.CROSSOVER_RATE = 0.8;
		AlgoritmoAG.MUTATION_RATE = 0.8;
		AlgoritmoAG.POPULATION_SIZE = 40;
		
		StoppingConditionFactory.NUM_GENERATIONS = 600;
		StoppingConditionFactory.SOLUTIONS_NUMBER_MIN = 1;
		StoppingConditionFactory.FITNESS_MIN = 0.;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionType.GenerationCount;
		
		//1. Initialice SusbsetsData and creates the AlgoritmoAG
		SubsetsData.iniDatos("files/testForCompetition.txt");
		AlgoritmoAG<List<Integer>, SubsetSolution> alg = AlgoritmoAG.of(new ChromosomeSubsetData());
		//2. Execute the algorithm
		alg.ejecuta();
		//3. Print the bestSolution of the algorithm
		System.out.println(alg.bestSolution().toString());
	}
}