package exercisesP5.exercise3;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.jgrapht.GraphPath;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.BT;
import us.lsi.graphs.alg.PDR;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class DeliveryTest {

	public static void main(String[] args) {
		
		Locale.setDefault(Locale.of("en", "US"));
		Integer testFile = 3;
		String route = "filesPI5/Ejercicio3DatosEntrada" + testFile + ".txt";
		DeliveryReader.read(route);
		
		DeliveryVertex initial = DeliveryVertex.initial();
		List<Integer> cheapestProducts = DeliveryVertex.cheapestProduct();
		
		EGraph<DeliveryVertex, DeliveryEdge> graph = EGraph.virtual(initial, DeliveryVertex.goal(), PathType.Sum, Type.Min)
				.edgeWeight(e -> e.weight())
				.goalHasSolution(DeliveryVertex.goal())
				.heuristic((a,b,c) -> {
					if(a.index().equals(DeliveryData.nProducts*DeliveryData.nDestinations)) {
						return 0.;
					}
					Double ac = 0.;
					for(Integer i = 0; i < a.demandLeft().size(); i++) {
						ac += cheapestProducts.get(i) * a.demandLeft().get(i);
					}
					return ac;
				})
				.build();
		
		System.out.println("\n----- TESTS FOR FILE " + route + " -----\n");
		System.out.println("\n----- BT TEST -----\n");
		
		BT<DeliveryVertex, DeliveryEdge, DeliverySolution> bt = BT.of(graph, DeliveryVertex::getSolution, null, null, true);
		Optional<GraphPath<DeliveryVertex, DeliveryEdge>> gpBT = bt.search();
		
		if (gpBT.isPresent()) {
			DeliverySolution s = DeliveryVertex.getSolution(gpBT.get());
			System.out.println(s);
			GraphColors.toDot(
					bt.outGraph(),
					"graphSolutions/graphTestBTEx3." + testFile + ".gv",
					v -> "p: " + v.index()%DeliveryData.nProducts + ", d: " + v.index()/DeliveryData.nProducts + ", " + v.demandLeft() + ", " + v.remainingUnits() + v.actions(),
					e -> "units: " + e.action().toString() + ", cost: " + e.weight(),
					v -> gpBT.get().getVertexList().contains(v) ? GraphColors.color(Color.blue) : GraphColors.color(Color.black),
					e -> gpBT.get().getEdgeList().contains(e) ? GraphColors.color(Color.blue) : GraphColors.color(Color.black));
		} else
			System.out.println("No solution");
		
		System.out.println("\n----- DP TEST -----\n");
		
		PDR<DeliveryVertex, DeliveryEdge, ?> dp = PDR.of(graph, DeliveryVertex::getSolution, null, null, true);
		Optional<GraphPath<DeliveryVertex, DeliveryEdge>> gpDP = dp.search();
		
		if (gpDP.isPresent()) {
			DeliverySolution s = DeliveryVertex.getSolution(gpDP.get());
			System.out.println(s);
			GraphColors.toDot(
					dp.outGraph(),
					"graphSolutions/graphTestDPEx3." + testFile + ".gv",
					v -> "p: " + v.index()%DeliveryData.nProducts + ", d: " + v.index()/DeliveryData.nProducts + ", " + v.demandLeft() + ", " + v.remainingUnits(),
					e -> "units: " + e.action().toString() + ", cost: " + e.weight(),
					v -> gpDP.get().getVertexList().contains(v) ? GraphColors.color(Color.blue) : GraphColors.color(Color.black),
					e -> gpDP.get().getEdgeList().contains(e) ? GraphColors.color(Color.blue) : GraphColors.color(Color.black));
		} else
			System.out.println("No solution");
		
		System.out.println("\n----- A* TEST -----\n");
		
		AStar<DeliveryVertex, DeliveryEdge, DeliverySolution> aS = AStar.of(graph, DeliveryVertex::getSolution, null, null);
		Optional<GraphPath<DeliveryVertex, DeliveryEdge>> gpAS = aS.search();

		if (gpAS.isPresent()) {
			DeliverySolution s = DeliveryVertex.getSolution(gpAS.get());
			System.out.println(s);
			GraphColors.toDot(
					aS.outGraph(),
					"graphSolutions/graphTestASEx3." + testFile + ".gv",
					v -> "p: " + v.index()%DeliveryData.nProducts + ", d: " + v.index()/DeliveryData.nProducts + ", " + v.demandLeft() + ", " + v.remainingUnits(),
					e -> "units: " + e.action().toString() + ", cost: " + e.weight(),
					v -> gpAS.get().getVertexList().contains(v) ? GraphColors.color(Color.blue) : GraphColors.color(Color.black),
					e -> gpAS.get().getEdgeList().contains(e) ? GraphColors.color(Color.blue) : GraphColors.color(Color.black));
		} else
			System.out.println("No solution");
		
	}
	
}
