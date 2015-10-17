import java.util.HashMap;

public class CentralTendency {
	
	public static double getMean(double [] indicatorValues){
		double sum = countOfValues(indicatorValues); // add all values together
		return sum / ((double) indicatorValues.length); //cast double so that return is of type double since all values are double numbers
	}
	
	public static double getMedianOfCountryIndicator(double [] indicatorValues){//get the median value of a countrys indicator ie from 1990 - 2000 what is the median value.

		indicatorValues = sortArrayOfDoubles(indicatorValues);
		int middleSlot = indicatorValues.length / 2;
		
		if(indicatorValues.length % 2 == 0){//if the length is even middle is between two slots
			return (indicatorValues[middleSlot] + indicatorValues[middleSlot-1] )/2.0;//get mean of the two values to get the median.
		}
		else{//odd so median is in one slot in the array
			return indicatorValues[middleSlot];
		}
	}
	
	public static double getMedianofWorldIndicator(double[] rangeStarts, double [] rangeEnds, double [] allValuesforIndicator){
		
		//with a given range from x-y y-z group all values into accurances with in the ranges.
		HashMap<Float,Integer> rangeValuesOccernaces  = range(rangeStarts,rangeEnds,allValuesforIndicator);
		
		int sumOfOccurrences=countOfOccurrences(rangeValuesOccernaces);
		
		double middleValue = sumOfOccurrences/2.0;//double as for x/2 could be odd and rounding to make int will be less accurate 
		
		//get the ranges keys for access into map.
		Object [] keyObjects = rangeValuesOccernaces.keySet().toArray();
		Float[] rangeKeys = new  Float[keyObjects.length];
		for(int i=0; i<keyObjects.length; i++){
			rangeKeys[i] = (Float) keyObjects[i];//casting again
		}
		
		
		int subtotals = 0;//stores additon of all occurance from start to where the middleValue is in the range of.
		Float startOfRange = new Float(0);//start of the range that middle is in
		Float endOfRange = new Float(0);//end of the range that middle is in.
		
		for(int i=0; i < rangeKeys.length; i++){//loop to fine values for the above varaibles. 
			
			subtotals += rangeValuesOccernaces.get(rangeKeys[i]);//add occurrences together
			
			if(i+1 <= rangeKeys.length){//make sure not to go out of bound 
				int endOfrangeAdded = subtotals + rangeValuesOccernaces.get(rangeKeys[i]);//get the value of the end of the range
				if(middleValue >= subtotals && middleValue < endOfrangeAdded){// so basically if the middle value is between the total occurances at the start/end of the range.
					startOfRange = rangeKeys[i];//store start of range.
					endOfRange = rangeKeys[i+1];//store end of range.
					break;//break out of loop as no longer needed.
				}
			}
			
		}
		
		//filling out the formula for the median and return result.
		
		return startOfRange * ((middleValue - subtotals) / rangeValuesOccernaces.get(startOfRange)) * (endOfRange - startOfRange);
	}
	
	public static double [] getMode(double [] indicatorValues){//get mode.
		
		HashMap<Float,Integer> countOfOccurrence = new HashMap<Float,Integer>();//float is for double values. integer for the times the double(Float) comes up.
		
		//populates the hash map of all occurrences of a value.
		
		for(int i = 0; i<indicatorValues.length;i++){
			double value = indicatorValues[i];
			if(countOfOccurrence.containsKey(value)){//value already occurred so add one to the total.
				countOfOccurrence.put((float) value, countOfOccurrence.get(value) + 1);//cast to float so you can add it as a Float, doesn't auto cast double to Float
			}
			else{//new value occurred so add it to the map with on value.
				countOfOccurrence.put((float) value, 1);//same reason as above. 
			}
		}
		
		//get the value(s) of the most occurring 
		double [] mostOccuring = new double [countOfOccurrence.size()];
		
		//populate an float array with all keys for better access.
		Object[] keyObjects = countOfOccurrence.keySet().toArray();//from map to set to array
		Float[] keys = new  Float[keyObjects.length];//convert object array into float array for use ability 
		for(int i=0; i<keyObjects.length; i++){
			keys[i] = (Float) keyObjects[i];
		}
		
		if(keys.length > 0){//better safe then sorry (can't remember why I though this was a good idea) 
			
			mostOccuring[0] = keys[0];//keep track of values that are the most occurring 
			double min = countOfOccurrence.get(keys[0]);// set the min of the most occurring to the first value.
			int valuesOfSameOccurance = 0;//keep track of indexing and how many values occured at the same rate.
			
			for(int i=1;i<keys.length;i++){//
				
				if(countOfOccurrence.get(keys[i]) > min){
					min = countOfOccurrence.get(keys[i]);//reset min to now highest recurring value
					mostOccuring = new double [keys.length - i]; //create a new array
					valuesOfSameOccurance = 0;//reset place of index keeping track of values that accrue at the same rate
					mostOccuring[valuesOfSameOccurance] = keys[i]; //add new highest occurrence
				}
				else if(countOfOccurrence.get(keys[i]) == min){//if the value of the next value that occurred has the same as the last value of occurrence then add it to array
					valuesOfSameOccurance++;
					mostOccuring[valuesOfSameOccurance] = keys[i]; 
				}
				
			}
			double [] retArray = new double [valuesOfSameOccurance + 1];//create new array to told nothing but the highest occurring values.
			System.arraycopy( mostOccuring, 0, retArray, 0, valuesOfSameOccurance + 1 );//get rid of empty space and values that aren't highest occurring
			
			return  retArray;
		}
		
		return new double[0];
	}

	public static double [] sortArrayOfDoubles(double [] indicatorValues){
		
		double [] sortedArray = new double [indicatorValues.length];//create new array of the same size as the one been taken in

		System.arraycopy( indicatorValues, 0, sortedArray, 0, indicatorValues.length );//copy values over to new array.
		
		for(int i=0; i < sortedArray.length; i++){//Selection sort below
			
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
	
	public static double countOfValues(double[] indicatorValues) {//just adds up all values in a double array.
		double sum = 0.0;
		for(double value:indicatorValues){
			sum+=value;
		}
		return sum;
	}
	
	private static int countOfOccurrences(HashMap<Float,Integer> values){//gets the occurrences added together.
		
		int totalOccurrences= 0;
		
		Object [] keyObjects = values.keySet().toArray();
		Float[] keys = new  Float[keyObjects.length];
		for(int i=0; i<keyObjects.length; i++){
			keys[i] = (Float) keyObjects[i];
		}
		
		for(Float key:keys){
			totalOccurrences+=values.get(key);
		}
		return totalOccurrences;
	}
	
	public static HashMap <Float,Integer> range(double[] rangeStarts, double [] rangeEnds, double [] allValuesforIndicator){
		
		HashMap <Float,Integer> rangeValues = new HashMap<>();
		
		for(int i =0; i < rangeStarts.length; i++){
			
			
			Float startOfRange = new Float(rangeStarts[i]);//float for adding to map.
			rangeValues.put(startOfRange , 0);//place the next range in the map and set it to 0
			for(int j =0; j < allValuesforIndicator.length; j++ ){//loop through each value and place it in the right range.
					
				if(i+1 < rangeEnds.length){//if there is and end range.
					
					if(allValuesforIndicator[j] >= rangeStarts[i] && allValuesforIndicator[j] < rangeEnds[i+1] ){ //if value is in range A and B
						rangeValues.put(startOfRange , rangeValues.get(startOfRange)+1);
					}
						
				}else if((i+1 > rangeEnds.length) && rangeEnds.length < rangeStarts.length){//if rangeEnd is not the same length as rangeStart there is a last range of X+
					
					if(allValuesforIndicator[j] >= rangeStarts[i]){ //if value is in range A+
						rangeValues.put(startOfRange , rangeValues.get(startOfRange)+1);
					}
				}
			}
		}
		
		return rangeValues;
	}
}
