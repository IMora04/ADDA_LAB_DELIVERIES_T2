package exercisesP4.exercise3;

import java.util.List;

import us.lsi.ag.ValuesInRangeData;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;

public class Exercise3GA implements ValuesInRangeData<Integer, SolutionEx3GA> {
	
	public Exercise3GA(String file) {
		Exercise3Reader.read(file);
	}

	public Integer size() {
		return Exercise3LP.getNProducts() * Exercise3LP.getNDestinations();
	}

	public ChromosomeType type() {
		return ChromosomeType.Range;
	}

	public Double fitnessFunction(List<Integer> value) {
		Integer k = 1000;
		Integer penalty = 0;
		Integer of = 0;
			
		Integer totalDest = Exercise3LP.getNDestinations();
		Integer totalProd = Exercise3LP.getNProducts();
		
		Integer it = (totalDest.compareTo(totalProd) < 0)?totalProd:totalDest;
		
		// i is used as:
			// destination, in the penalty of minUnits
			// product, in the penalty of availableProducts
		// with j, the other way around
		
		for(Integer i = 0; i < it; i++) {
			
			Integer unitsDelivered = 0;
			Integer productsSent = 0;
			
			for(Integer j = 0; j < it; j++) {
				
				//Penalty for minUnits
				if(i < totalDest && j < totalProd) {
					unitsDelivered += value.get(i*totalProd + j);
					of += value.get(i*totalProd + j) * Exercise3LP.getCost(j, i);
				}
				
				//Penalty for availableProducts
				if(i < totalProd && j < totalDest) {
					productsSent += value.get(j*totalProd + i);
				}
			}
			if(i < totalDest && unitsDelivered < Exercise3LP.getMinDemand(i)) {
				penalty += 1;
			}
			if(i < totalProd && productsSent > Exercise3LP.getProductsAvailable(i)) {
				penalty += 1;
			}
			
		}
		
		return Double.valueOf(- of - k * penalty);
	}
	
	public Integer getProductFromIndex(Integer index) {
		return index%Exercise3LP.getNProducts();
	}
	
	public Integer getDestinationFromIndex(Integer index) {
		return Integer.valueOf(index/Exercise3LP.getNProducts());
	}

	public SolutionEx3GA solucion(List<Integer> value) {
		return SolutionEx3GA.of_range(value);
	}

	public Integer max(Integer i) {
		return Exercise3LP.getProductsAvailable(getProductFromIndex(i));
	}

	public Integer min(Integer i) {
		return 0;
	}
	
	/*
3. A product distributor must decide the number of units of different products to send
to different destinations. For each destination, the minimum total quantity demand
of products to be stored at that destination, which must be met, is known. For each
type of product, the maximum number of units available that the distributor can
send is known. Additionally, for each type of product and each destination,
information is available about the storage cost of each unit of product at that
destination. The objective is to know how many units of each product to send to
each destination, minimizing the total storage cost, and meeting the minimum
product demands of each destination.

n of units of products to different destinations

sum units < minUnits
sum units (of each products) < prodAvailable

Less sum of costs
	 */

	/*
	 * If values in range:
	 * 		Range: YES
	 * 			List of nProd*nDeliv elements
	 */
	

}