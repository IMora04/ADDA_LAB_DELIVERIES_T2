package exercisesP5.exercise1;

import java.util.Locale;
import java.util.Optional;

import org.jgrapht.GraphPath;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.alg.BT;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class PlantTest {
	
	public static void main(String[] args) {
		
		Locale.setDefault(Locale.of("en", "US"));
		Exercise1Reader.read("files/Ejercicio1DatosEntrada3.txt");
		
		PlantVertex initial = PlantVertex.initial();
		
		EGraph<PlantVertex, PlantEdge> graph = EGraph.virtual(initial, PlantVertex.goal(), PathType.Sum, Type.Max)
				.edgeWeight(e -> e.weight())
				.goalHasSolution(PlantVertex.goal())
				.heuristic((a,b,c) -> {return (PlantData.nVarieties - a.index())*1. ;})
				.build();
		
		BT<PlantVertex, PlantEdge, PlantSolution> bt = BT.of(graph, PlantVertex::getSolution, null, null, true);
		Optional<GraphPath<PlantVertex, PlantEdge>> gp = bt.search();
		
		if (gp.isPresent()) {
			PlantSolution s = PlantVertex.getSolution(gp.get());
			System.out.println(s);
			GraphColors.toDot(
					bt.outGraph(),
					"files/graphTest.gv",
					v -> v.index() + ", " + v.varietiesPlanted() + ", " + v.spaceLeft(),
					e -> e.action().toString(),
					v -> gp.get().getVertexList().contains(v) ? GraphColors.color(Color.blue) : GraphColors.color(Color.black),
					e -> gp.get().getEdgeList().contains(e) ? GraphColors.color(Color.blue) : GraphColors.color(Color.black));
		} else
			System.out.println("No hay solucion");
		
	}

}
