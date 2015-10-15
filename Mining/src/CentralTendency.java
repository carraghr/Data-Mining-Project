import java.util.HashMap;


public class CentralTendency {
	
	public static double getMean(double [] indicatorValues){
		double sum = countOfValues(indicatorValues);
		return sum / ((double) indicatorValues.length);
	}

	public static double [] sortArrayOfDoubles(double [] indicatorValues){
		
		double [] sortedArray = new double [indicatorValues.length];

		System.arraycopy( indicatorValues, 0, sortedArray, 0, indicatorValues.length );
		
		for(int i=0; i < sortedArray.length; i++){
			
			int smallestValue = i; // stores the index of the lowest value.
			
			for(int j = i+1; j < sortedArray.length; j++){
				if(sortedArray[smallestValue] > sortedArray[j]){
					smallestValue = j;
				}
			}
			
			//swap values
			double temp = sortedArray[i]; //was smallest
			sortedArray[i] = sortedArray[smallestValue];
			sortedArray[smallestValue] = temp;
			
		}
		return sortedArray;
	}
	
	public static double getMedian(double [] indicatorValues){

		indicatorValues = sortArrayOfDoubles(indicatorValues);
		int middleSlot = indicatorValues.length / 2;
		if(indicatorValues.length % 2 == 0){
			return (indicatorValues[middleSlot] + indicatorValues[middleSlot-1] )/2.0;
		}
		else{
			return indicatorValues[middleSlot];
		}
	}
	
	public static double countOfValues(double[] indicatorValues) {
		double sum = 0.0;
		for(double value:indicatorValues){
			sum+=value;
		}
		return sum;
	}
	
	public static double [] getMode(double [] indicatorValues){
		
		HashMap<Float,Integer> countOfOccurrence = new HashMap<Float,Integer>();
		
		//populates the hash map of all occurrences of a value.
		
		for(int i = 0; i<indicatorValues.length;i++){
			double value = indicatorValues[i];
			if(countOfOccurrence.containsKey(value)){
				countOfOccurrence.put((float) value, countOfOccurrence.get(value) + 1);
			}else{
				countOfOccurrence.put((float) value, 1);
			}
		}
		
		//get the value(s) of the most occurring 
		double [] mostOccuring = new double [countOfOccurrence.size()];
		
		
		Object[] keyObjects = countOfOccurrence.keySet().toArray();
		Float[] keys = new  Float[keyObjects.length];
		for(int i=0; i<keyObjects.length; i++){
			keys[i] = (Float) keyObjects[i];
		}
		
		if(keys.length > 0){
			
			mostOccuring[0] = keys[0];
			double min = countOfOccurrence.get(keys[0]);
			int valuesOfSameOccurance = 0;
			for(int i=1;i<keys.length;i++){
				
				if(countOfOccurrence.get(keys[i]) > min){
					min = countOfOccurrence.get(keys[i]);//reset min to now highest recurring value
					mostOccuring = new double [keys.length - i]; //create a new array
					valuesOfSameOccurance = 0;//reset place of index keeping track of values that accrue at the same rate
					mostOccuring[valuesOfSameOccurance] = keys[i]; 
				}
				else if(countOfOccurrence.get(keys[i]) == min){
					valuesOfSameOccurance++;
					mostOccuring[valuesOfSameOccurance] = keys[i]; 
				}
				
			}
			double [] retArray = new double [valuesOfSameOccurance + 1];
			System.arraycopy( mostOccuring, 0, retArray, 0, valuesOfSameOccurance + 1 );
			
			return  retArray;
		}
		
		return new double[0];
		
	}
}
