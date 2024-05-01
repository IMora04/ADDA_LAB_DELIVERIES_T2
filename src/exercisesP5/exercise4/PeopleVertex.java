package exercisesP5.exercise4;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.jgrapht.GraphPath;

import us.lsi.graphs.virtual.VirtualVertex;

public record PeopleVertex(Integer index, Set<Integer> unpairedPeople, Integer lastChosen)
	implements VirtualVertex<PeopleVertex, PeopleEdge, Integer> {

	public List<Integer> actions() {
		List<Integer> ac = new ArrayList<>();
		if(this.index.equals(PeopleData.nPeople)) {
			
		} else if(this.index%2!=0 && this.index.equals(PeopleData.nPeople - 1)) {
			Integer person = filteredPeople().max(Comparator.comparing(p -> getAffinity(p, lastChosen))).orElse(null);
			if(person != null) {
				ac.add(person);
			}
		} else if (this.index%2==0){
			Integer person = unpairedPeople.stream().findFirst().orElse(null);
			if(person != null) {
				ac.add(person);
			}
		} else {
			ac.addAll(new ArrayList<>(filteredPeople().toList()));
		}
		return ac;
	}
	
	public Stream<Integer> filteredPeople() {
		Set<String> thisLangs = PeopleData.languages.get(this.lastChosen);
		return unpairedPeople.stream()
				.filter(
					p -> PeopleData.languages.get(p).stream().anyMatch(l -> thisLangs.contains(l))
					&& -5 <= PeopleData.ages.get(p) - PeopleData.ages.get(this.lastChosen)
					&& 5 >= PeopleData.ages.get(p) - PeopleData.ages.get(this.lastChosen)
					&& PeopleData.nationalities.get(p) != PeopleData.nationalities.get(this.lastChosen)
				);
	}

	public PeopleVertex neighbor(Integer a) {
		Set<Integer> uP = new HashSet<>(this.unpairedPeople);
		uP.remove(a);
		Integer lC = a;
		if(this.index%2!=0) {
			lC = this.lastChosen;
		}
		return new PeopleVertex(this.index+1, uP, lC);
	}

	public PeopleEdge edge(Integer a) {
		return PeopleEdge.of(this, this.neighbor(a), a);
	}
	
	public static Integer getAffinity(Integer a, Integer b) {
		return PeopleData.affinities.get(a).get(b);
	}
	
	public static PeopleSolution getSolution(GraphPath<PeopleVertex, PeopleEdge> g) {
		return PeopleSolution.of(g);
	}
	
	public static PeopleVertex initial() {
		return new PeopleVertex(0, new HashSet<>(IntStream.range(0, PeopleData.nPeople).boxed().collect(Collectors.toSet())), 0);
	}
	
	public static Predicate<PeopleVertex> goal() {
		return v -> v.index().equals(PeopleData.nPeople);
	}
	
	public static Predicate<PeopleVertex> isSolution() {
		return v -> goal().test(v) && v.unpairedPeople().isEmpty();
	}

}
