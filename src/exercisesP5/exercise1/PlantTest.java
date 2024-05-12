package exercisesP5.exercise1;

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

public class PlantTest {
	
	public static void main(String[] args) {
		
		Locale.setDefault(Locale.of("en", "US"));
		Integer testFile = 3;
		String route = "filesPI5/Ejercicio1DatosEntrada" + testFile + ".txt";
		Exercise1Reader.read(route);
		
		PlantVertex initial = PlantVertex.initial();
		
		EGraph<PlantVertex, PlantEdge> graph = EGraph.virtual(initial, PlantVertex.goal(), PathType.Sum, Type.Max)
				.edgeWeight(e -> e.weight())
				.goalHasSolution(PlantVertex.goal())
				.heuristic((a,b,c) -> {
					return (PlantData.nVarieties - a.index())*1.
				;})
				.build();
		
		System.out.println("\n----- TESTS FOR FILE " + route + " -----\n");
		System.out.println("\n----- BT TEST -----\n");
		
		BT<PlantVertex, PlantEdge, PlantSolution> bt = BT.of(graph, PlantVertex::getSolution, null, null, true);
		Optional<GraphPath<PlantVertex, PlantEdge>> gpBT = bt.search();
		
		if (gpBT.isPresent()) {
			PlantSolution s = PlantVertex.getSolution(gpBT.get());
			System.out.println(s);
			GraphColors.toDot(
					bt.outGraph(),
					"graphSolutions/graphTestBTEx1." + testFile + ".gv",
					v -> v.index() + ", " + v.varietiesPlanted() + ", " + v.spaceLeft(),
					e -> e.action().toString(),
					v -> gpBT.get().getVertexList().contains(v) ? GraphColors.color(Color.blue) : GraphColors.color(Color.black),
					e -> gpBT.get().getEdgeList().contains(e) ? GraphColors.color(Color.blue) : GraphColors.color(Color.black));
		} else
			System.out.println("No solution");
		
		System.out.println("\n----- DP TEST -----\n");
		
		PDR<PlantVertex, PlantEdge, ?> dp = PDR.of(graph, PlantVertex::getSolution, null, null, true);
		Optional<GraphPath<PlantVertex, PlantEdge>> gpDP = dp.search();
		
		if (gpDP.isPresent()) {
			PlantSolution s = PlantVertex.getSolution(gpDP.get());
			System.out.println(s);
			GraphColors.toDot(
					dp.outGraph(),
					"graphSolutions/graphTestDPEx1." + testFile + ".gv",
					v -> v.index() + ", " + v.varietiesPlanted() + ", " + v.spaceLeft(),
					e -> e.action().toString(),
					v -> gpDP.get().getVertexList().contains(v) ? GraphColors.color(Color.blue) : GraphColors.color(Color.black),
					e -> gpDP.get().getEdgeList().contains(e) ? GraphColors.color(Color.blue) : GraphColors.color(Color.black));
		} else
			System.out.println("No solution");
		
		System.out.println("\n----- A* TEST -----\n");
		
		AStar<PlantVertex, PlantEdge, PlantSolution> aS = AStar.of(graph, PlantVertex::getSolution, null, null);
		Optional<GraphPath<PlantVertex, PlantEdge>> gpAS = aS.search();

		if (gpAS.isPresent()) {
			PlantSolution s = PlantVertex.getSolution(gpAS.get());
			System.out.println(s);
			GraphColors.toDot(
					aS.outGraph(),
					"graphSolutions/graphTestASEx1." + testFile + ".gv",
					v -> v.index() + ", " + v.varietiesPlanted() + ", " + v.spaceLeft(),
					e -> e.action().toString(),
					v -> gpAS.get().getVertexList().contains(v) ? GraphColors.color(Color.blue) : GraphColors.color(Color.black),
					e -> gpAS.get().getEdgeList().contains(e) ? GraphColors.color(Color.blue) : GraphColors.color(Color.black));
		} else
			System.out.println("No solution");
		
	}

}
