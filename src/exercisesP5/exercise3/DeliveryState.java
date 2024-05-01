package exercisesP5.exercise3;

import java.util.ArrayList;
import java.util.List;

public class DeliveryState {
	
	private DeliveryVertex problem;
	private List<Integer> pastActions;
	private List<DeliveryVertex> pastProblems;
	private Double acumOF;
	
	private DeliveryState(DeliveryVertex problem, List<Integer> pastActions, List<DeliveryVertex> pastProblems, Double acumOF) {
		this.problem = problem;
		this.pastActions = pastActions;
		this.pastProblems = pastProblems;
		this.acumOF = acumOF;
	}
	
	public static DeliveryState of(DeliveryVertex problem) {
		return new DeliveryState(problem, new ArrayList<>(), new ArrayList<>(List.of(problem)), 0.);
	}
	
	public void forward(Integer action) {
		DeliveryVertex n = this.problem.neighbor(action);
		this.pastActions.add(action);
		this.pastProblems.add(n);
		this.acumOF += this.problem.edge(action).weight();
		this.problem = n;
	}
	
	public void back(Integer action) {
		this.pastActions.remove(this.pastActions.size() - 1);
		this.pastProblems.remove(this.pastProblems.size() - 1);
		this.problem = this.pastProblems.get(this.pastProblems.size() - 1);
		this.acumOF -= this.problem.edge(action).weight();
	}

	public DeliveryVertex getProblem() {
		return problem;
	}

	public List<Integer> getPastActions() {
		return pastActions;
	}

	public List<DeliveryVertex> getPastProblems() {
		return pastProblems;
	}

	public Double getAcumOF() {
		return acumOF;
	}

}
