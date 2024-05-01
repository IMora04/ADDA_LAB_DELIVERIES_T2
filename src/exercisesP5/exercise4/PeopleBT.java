package exercisesP5.exercise4;

import java.util.List;

public class PeopleBT {
	
	private PeopleState currentState;
	private Double bestOF;
	private PeopleVertex startProblem;
	private PeopleSolution solution;
	
	public static PeopleBT of() {
		return new PeopleBT();
	}
	
	public void btm(PeopleVertex startProblem) {
		this.startProblem = startProblem;
		this.currentState = PeopleState.of(startProblem);
		this.bestOF = null;
		this.solution = null;
		btm();
	}
	
	public void btm() {
		if(currentState.getProblem().index().equals(PeopleData.nPeople)) {
			if(bestOF == null || bestOF < currentState.getAcumOF()) {
				bestOF = currentState.getAcumOF();
				solution = PeopleSolution.of(currentState);
			}
		} else {
			List<Integer> actions = currentState.getProblem().actions();
			for(Integer a:actions) {
				if(bestOF != null && bestOF <= currentState.getAcumOF() + heuristic(currentState)) {
					continue;
				}
				currentState.forward(a);
				btm();
				currentState.back(a);
			}
		}
	}
	
	public Double heuristic(PeopleState state) {
		Integer pairsLeft = (PeopleData.nPeople - state.getProblem().index());
		pairsLeft = pairsLeft%2==0 ? pairsLeft/2 : (pairsLeft/2)+1;
		return pairsLeft*5.;
	}
	
	public static void main(String[] args) {
		Integer testFile = 3;
		String route = "files/Ejercicio4DatosEntrada" + testFile + ".txt";
		PeopleReader.read(route);
		PeopleBT bt = PeopleBT.of();
		bt.btm(PeopleVertex.initial());
		System.out.println(bt.solution);
	}

}
