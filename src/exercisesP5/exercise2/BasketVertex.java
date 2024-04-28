package exercisesP5.exercise2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import org.jgrapht.GraphPath;

import us.lsi.graphs.virtual.VirtualVertex;

public record BasketVertex(Integer index, Integer sumRating) implements VirtualVertex<BasketVertex, BasketEdge, Integer>{

	public List<Integer> actions() {
		if(this.index.equals(BasketData.nCategories)) {
			return new ArrayList<>();
		}
		if(this.index.equals(BasketData.nCategories-1)) {
			List<Integer> ac = new ArrayList<>();
			for(int i = 0; i < BasketData.nProducts; i++) {
				if(!BasketData.categories.get(i).equals(this.index)) {
					continue;
				}
				if(BasketData.prices.get(i) > BasketData.budget) {
					continue;
				}
				if(this.sumRating + BasketData.ratings.get(i) < 3*BasketData.nCategories) {
					continue;
				}
				ac.add(i);
			}
			if(ac.isEmpty()) {
				return new ArrayList<>();
			}
			return new ArrayList<Integer>(List.of(ac.stream().min(Comparator.comparing(p -> BasketData.prices.get(p))).get()));
		}
		List<Integer> s = IntStream.range(0, BasketData.nProducts)
				.filter(p -> BasketData.categories.get(p).equals(this.index))
				.boxed().toList();
		return s;
	}

	public BasketVertex neighbor(Integer a) {
		return new BasketVertex(this.index + 1, sumRating + BasketData.ratings.get(a));
	}

	public BasketEdge edge(Integer a) {
		return BasketEdge.of(this, this.neighbor(a), a);
	}	
	
	public static BasketVertex initial() {
		return new BasketVertex(0, 0);
	}
	
	public static Predicate<BasketVertex> goal() {
		return v -> v.index().equals(BasketData.nCategories);
	}
	
	public static List<Integer> minPricePerCat() {
		List<Integer> minPrices = new ArrayList<>();
		for(int i = 0; i < BasketData.nCategories; i++) {
			int index = i;
			Integer j = IntStream.range(0, BasketData.nProducts)
					.filter(p -> BasketData.categories.get(p).equals(index)).min().orElse(0);
			minPrices.add(j);
		}
		return minPrices;
	}
	
	public static BasketSolution getSolution(GraphPath<BasketVertex, BasketEdge> g) {
		return BasketSolution.of(g);
	}
	
}
