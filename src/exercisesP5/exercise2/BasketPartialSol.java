package exercisesP5.exercise2;

public record BasketPartialSol(Integer action, Double weight) implements Comparable<BasketPartialSol> {

	public int compareTo(BasketPartialSol o) {
		return this.weight.compareTo(o.weight);
	}
	
	public static BasketPartialSol of(Integer action, Double weight) {
		return new BasketPartialSol(action, weight);
	}

}
