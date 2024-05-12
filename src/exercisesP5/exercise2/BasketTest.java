package exercisesP5.exercise2;

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

public class BasketTest {
	
	public static void main(String[] args) {
		
		Locale.setDefault(Locale.of("en", "US"));
		Integer testFile = 3;
		String route = "filesPI5/Ejercicio2DatosEntrada" + testFile + ".txt";
		BasketReader.read(route);
		
		BasketVertex initial = BasketVertex.initial();
		List<Integer> minPrices = BasketVertex.minPricePerCat();
		System.out.println(minPrices);
		EGraph<BasketVertex, BasketEdge> graph = EGraph.virtual(initial, BasketVertex.goal(), PathType.Sum, Type.Min)
				.edgeWeight(e -> e.weight())
				.goalHasSolution(BasketVertex.goal())
				.heuristic((a,b,c) -> {
					return 1.*minPrices.subList(a.index(), minPrices.size()).stream().mapToInt(m->m).sum();
				})
				.build();
		
		System.out.println("\n----- TESTS FOR FILE " + route + " -----\n");
		System.out.println("\n----- BT TEST -----\n");
		
		BT<BasketVertex, BasketEdge, BasketSolution> bt = BT.of(graph, BasketVertex::getSolution, null, null, true);
		Optional<GraphPath<BasketVertex, BasketEdge>> gpBT = bt.search();
		
		if (gpBT.isPresent()) {
			BasketSolution s = BasketVertex.getSolution(gpBT.get());
			System.out.println(s);
			GraphColors.toDot(
					bt.outGraph(),
					"graphSolutions/graphTestBTEx2." + testFile + ".gv",
					v -> v.index() + ", rating sum:" + v.sumRating(),
					e -> "product: " + e.action().toString() + ", price: " + e.weight(),
					v -> gpBT.get().getVertexList().contains(v) ? GraphColors.color(Color.blue) : GraphColors.color(Color.black),
					e -> gpBT.get().getEdgeList().contains(e) ? GraphColors.color(Color.blue) : GraphColors.color(Color.black));
		} else
			System.out.println("No solution");
		
		System.out.println("\n----- DP TEST -----\n");
		
		PDR<BasketVertex, BasketEdge, ?> dp = PDR.of(graph, BasketVertex::getSolution, null, null, true);
		Optional<GraphPath<BasketVertex, BasketEdge>> gpDP = dp.search();
		
		if (gpDP.isPresent()) {
			BasketSolution s = BasketVertex.getSolution(gpDP.get());
			System.out.println(s);
			GraphColors.toDot(
					dp.outGraph(),
					"graphSolutions/graphTestDPEx2." + testFile + ".gv",
					v -> v.index() + ", rating sum:" + v.sumRating(),
					e -> "product: " + e.action().toString() + ", price: " + e.weight(),
					v -> gpDP.get().getVertexList().contains(v) ? GraphColors.color(Color.blue) : GraphColors.color(Color.black),
					e -> gpDP.get().getEdgeList().contains(e) ? GraphColors.color(Color.blue) : GraphColors.color(Color.black));
		} else
			System.out.println("No solution");
		
		System.out.println("\n----- A* TEST -----\n");
		
		AStar<BasketVertex, BasketEdge, BasketSolution> aS = AStar.of(graph, BasketVertex::getSolution, null, null);
		Optional<GraphPath<BasketVertex, BasketEdge>> gpAS = aS.search();

		if (gpAS.isPresent()) {
			BasketSolution s = BasketVertex.getSolution(gpAS.get());
			System.out.println(s);
			GraphColors.toDot(
					aS.outGraph(),
					"graphSolutions/graphTestASEx2." + testFile + ".gv",
					v -> v.index() + ", rating sum:" + v.sumRating(),
					e -> "product: " + e.action().toString() + ", price: " + e.weight(),
					v -> gpAS.get().getVertexList().contains(v) ? GraphColors.color(Color.blue) : GraphColors.color(Color.black),
					e -> gpAS.get().getEdgeList().contains(e) ? GraphColors.color(Color.blue) : GraphColors.color(Color.black));
		} else
			System.out.println("No solution");
		
	}

}
