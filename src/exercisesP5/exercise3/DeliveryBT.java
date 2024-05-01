package exercisesP5.exercise3;

import java.util.List;

public class DeliveryBT {
	
	private DeliveryVertex startProblem;
	private DeliveryState state;
	private DeliverySolution solution;
	private Double bestOF;
	
	public void btm(DeliveryVertex startProblem) {
		this.startProblem = startProblem;
		this.state = DeliveryState.of(startProblem);
		this.bestOF = null;
		this.solution = null;
		btm();
	}
	
	public static DeliveryBT of() {
		return new DeliveryBT();
	}
	
	public void btm() {
		if(state.getProblem().index().equals(DeliveryData.nDestinations*DeliveryData.nProducts)) {
			if(bestOF == null || bestOF > state.getAcumOF()) {
				bestOF = state.getAcumOF();
				solution = DeliverySolution.of(startProblem, state.getPastActions());
			}
		} else {
			List<Integer> actions = state.getProblem().actions();
			for(Integer a:actions) {
				if(bestOF != null && this.state.getAcumOF() + heuristic(this.state.getProblem(), a) >= bestOF) {
					continue;
				}
				state.forward(a);
				btm();
				state.back(a);
			}
		}
	}
		
	public Double heuristic(DeliveryVertex v, Integer a) {
		if(v.index().equals(DeliveryData.nProducts*DeliveryData.nDestinations)) {
			return 0.;
		}
		Double h = 0.;
		for(Integer i = 0; i < v.demandLeft().size(); i++) {
			h += DeliveryVertex.cheapestProduct().get(i) * v.demandLeft().get(i);
		}
		return h;
	}
	
	public DeliverySolution getSolution() {
		return solution;
	}
	
	public static void main(String[] args) {
		Integer testFile = 3;
		String route = "files/Ejercicio3DatosEntrada" + testFile + ".txt";
		DeliveryReader.read(route);
		DeliveryBT bt = DeliveryBT.of();
		bt.btm(DeliveryVertex.initial());
		System.out.println("---");
		System.out.println(bt.getSolution());
	}
	
}
