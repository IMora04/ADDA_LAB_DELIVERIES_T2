package contest1;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import us.lsi.ag.AuxiliaryAg;
import us.lsi.ag.SeqNormalData;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;

public class ChromosomeSubsetData implements SeqNormalData<SubsetSolution> {
	
	public ChromosomeType type() {                                                                                                              
		return ChromosomeType.SubList;                                                                                                          
	}                                                                                                                                           
                                                                                                                                                                                                                                                                                   
	public Double fitnessFunction(List<Integer> value) {    
		SubsetSolution sol = SubsetSolution.createFromSublist(value);     
		Set<Integer> s = new HashSet<Integer>();                                                                                                
		for (int i : value) {                                                                                                                   
			s.addAll(SubsetsData.getSubSet(i).elements());                                                                                      
		}                                                                                                                                       
		                                                                                                                                        
		return -sol.total - 100 * AuxiliaryAg.distanceToEqZero((double) SubsetsData.getNumElements() - s.size());                 
	}                                                                                                                                           
                                                                                                                                                
	public SubsetSolution solucion(List<Integer> value) {                                                                                       
		return SubsetSolution.createFromSublist(value);                                                                                         
	}                                                                                                                                           
                                                                                                                                                
	public Integer itemsNumber() {                                                                                                              
		return SubsetsData.getNumSubsets();                                                                                                     
	}                                                                                                                                           

}
