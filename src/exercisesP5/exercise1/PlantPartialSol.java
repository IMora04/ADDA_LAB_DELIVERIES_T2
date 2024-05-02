package exercisesP5.exercise1;

public record PlantPartialSol(Integer action, Double weight) implements Comparable<PlantPartialSol> {

	public int compareTo(PlantPartialSol o) {
		return this.weight.compareTo(o.weight);
	}
	
	public static PlantPartialSol of(Integer action, Double weight) {
		return new PlantPartialSol(action, weight);
	}


}
