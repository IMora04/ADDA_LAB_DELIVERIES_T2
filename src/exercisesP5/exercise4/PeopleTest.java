package exercisesP5.exercise4;

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

public class PeopleTest {

	public static void main(String[] args) {
		
		Locale.setDefault(Locale.of("en", "US"));
		Integer testFile = 3;
		String route = "filesPI5/Ejercicio4DatosEntrada" + testFile + ".txt";
		PeopleReader.read(route);
		
		PeopleVertex initial = PeopleVertex.initial();
		
		EGraph<PeopleVertex, PeopleEdge> graph = EGraph.virtual(initial, PeopleVertex.goal(), PathType.Sum, Type.Max)
				.edgeWeight(e -> e.weight())
				.goal(PeopleVertex.goal())
				.goalHasSolution(PeopleVertex.isSolution())
				.heuristic((a,b,c) -> {
					Integer pairsLeft = (PeopleData.nPeople - a.index());
					pairsLeft = pairsLeft%2==0 ? pairsLeft/2 : (pairsLeft/2)+1;
					return pairsLeft*5.;
				})
				.build();
		
		System.out.println("\n----- TESTS FOR FILE " + route + " -----\n");
		System.out.println("\n----- BT TEST -----\n");
		
		BT<PeopleVertex, PeopleEdge, PeopleSolution> bt = BT.of(graph, PeopleVertex::getSolution, null, null, true);
		Optional<GraphPath<PeopleVertex, PeopleEdge>> gpBT = bt.search();
		
		if (gpBT.isPresent()) {
			PeopleSolution s = PeopleVertex.getSolution(gpBT.get());
			System.out.println(s);
			GraphColors.toDot(
					bt.outGraph(),
					"graphSolutions/graphTestBTEx4." + testFile + ".gv",
					v -> "p: " + v.index()%2 + ", last: " + v.lastChosen() + ", unpaired: " + v.unpairedPeople(),
					e -> "pair: " + e.action().toString() + ", aff: " + e.weight(),
					v -> gpBT.get().getVertexList().contains(v) ? GraphColors.color(Color.blue) : GraphColors.color(Color.black),
					e -> gpBT.get().getEdgeList().contains(e) ? GraphColors.color(Color.blue) : GraphColors.color(Color.black));
		} else
			System.out.println("No solution");
		
		System.out.println("\n----- DP TEST -----\n");
		
		PDR<PeopleVertex, PeopleEdge, ?> dp = PDR.of(graph, PeopleVertex::getSolution, null, null, true);
		Optional<GraphPath<PeopleVertex, PeopleEdge>> gpDP = dp.search();
		
		if (gpDP.isPresent()) {
			PeopleSolution s = PeopleVertex.getSolution(gpDP.get());
			System.out.println(s);
			GraphColors.toDot(
					dp.outGraph(),
					"graphSolutions/graphTestDPEx4." + testFile + ".gv",
					v -> "p: " + v.index()%2 + ", last: " + v.lastChosen() + ", unpaired: " + v.unpairedPeople(),
					e -> "pair: " + e.action().toString() + ", aff: " + e.weight(),
					v -> gpDP.get().getVertexList().contains(v) ? GraphColors.color(Color.blue) : GraphColors.color(Color.black),
					e -> gpDP.get().getEdgeList().contains(e) ? GraphColors.color(Color.blue) : GraphColors.color(Color.black));
		} else
			System.out.println("No solution");
		
		System.out.println("\n----- A* TEST -----\n");
		
		AStar<PeopleVertex, PeopleEdge, PeopleSolution> aS = AStar.of(graph, PeopleVertex::getSolution, null, null);
		Optional<GraphPath<PeopleVertex, PeopleEdge>> gpAS = aS.search();

		if (gpAS.isPresent()) {
			PeopleSolution s = PeopleVertex.getSolution(gpAS.get());
			System.out.println(s);
			GraphColors.toDot(
					aS.outGraph(),
					"graphSolutions/graphTestASEx4." + testFile + ".gv",
					v -> "p: " + v.index()%2 + ", last: " + v.lastChosen() + ", unpaired: " + v.unpairedPeople(),
					e -> "pair: " + e.action().toString() + ", aff: " + e.weight(),
					v -> gpAS.get().getVertexList().contains(v) ? GraphColors.color(Color.blue) : GraphColors.color(Color.black),
					e -> gpAS.get().getEdgeList().contains(e) ? GraphColors.color(Color.blue) : GraphColors.color(Color.black));
		} else
			System.out.println("No solution");
		
	}
	
}
