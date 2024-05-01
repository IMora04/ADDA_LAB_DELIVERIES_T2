package exercisesP5.exercise4;

import java.util.ArrayList;
import java.util.List;

public class PeopleState {

	private PeopleVertex problem;
	private List<PeopleVertex> pastProblems;
	private List<Integer> pastActions;
	private Double acumOF;
	
	private PeopleState(PeopleVertex problem, ArrayList<PeopleVertex> pastProblems, ArrayList<Integer> pastActions,
			Double acumOF) {
		this.problem = problem;
		this.pastActions = pastActions;
		this.pastProblems = pastProblems;
		this.acumOF = acumOF;
	}

	public void forward(Integer action) {
		acumOF += problem.edge(action).weight();
		problem = problem.neighbor(action);
		pastActions.add(action);
		pastProblems.add(problem);
	}
	
	public void back(Integer action) {
		pastProblems.remove(pastProblems.size()-1);
		pastActions.remove(pastActions.size()-1);
		problem = pastProblems.get(pastProblems.size()-1);
		acumOF -= problem.edge(action).weight();
	}

	public static PeopleState of(PeopleVertex startProblem) {
		return new PeopleState(startProblem, new ArrayList<PeopleVertex>(List.of(startProblem)), new ArrayList<Integer>(), 0.);
	}

	public PeopleVertex getProblem() {
		return problem;
	}

	public List<PeopleVertex> getPastProblems() {
		return pastProblems;
	}

	public List<Integer> getPastActions() {
		return pastActions;
	}

	public Double getAcumOF() {
		return acumOF;
	}
	
}
