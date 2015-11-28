import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class CentralTendency {
	
	public static double getMeanOfIndicator(double [] indicatorValues){
		double sum = countOfValues(indicatorValues); // add all values together
		return sum / ((double) indicatorValues.length); //cast double so that return is of type double since all values are double numbers
	}
	
	static void meanOfIndicatorForEachCountryInRegion(Country[] countrys,List<String> countryTracker,HashMap <String, Integer> regionOccurrence,String indicator){
		/*
		 * Get the mean of all regions
		 */
		
		HashMap <String,Float> regionalTotal = new HashMap<>();
		for(int i=0; i < countryTracker.size();i++){
			if(!countrys[i].isInRegion("unknown")){
				double count = CentralTendency.countOfValues(countrys[i].getIndicatorValues(indicator));
				
				if(regionalTotal.containsKey(countrys[i].getRegion())){
					regionalTotal.put(countrys[i].getRegion(), (float) (regionalTotal.get(countrys[i].getRegion())+count));
				}else{
					regionalTotal.put(countrys[i].getRegion(), (float) count);
				}
			}
		}
		
		Set<String> keyObjects = regionalTotal.keySet();
		for(String key:keyObjects){
			/*
			 * total at start is the total value for a region for 10 years.
			 * Divide this by the number of years to get the mean of the region for 10 years.
			 * for each of these years a country as added to this total to get the average of this addition divide by the number of countries in region
			 */
			
			System.out.println("Mean value of "+ indicator +" for each country in "+ key +" is: " + (regionalTotal.get(key)/(double)regionOccurrence.get(key))/(double)10  );
		}
	}
	
	static void meanOfIndicatorForRegion(Country[] countrys,List<String> countryTracker,HashMap <String, Integer> regionOccurrence,String indicator){
		
		HashMap <String,Float> regionalTotal = new HashMap<>();
		for(int i=0; i < countryTracker.size();i++){
			
			if(!countrys[i].isInRegion("unknown")){
				double count = CentralTendency.countOfValues(countrys[i].getIndicatorValues(indicator));//get the value for a country for indicator
				
				if(regionalTotal.containsKey(countrys[i].getRegion())){
					/*Add the value to a regions total*/
					regionalTotal.put(countrys[i].getRegion(), (float) (regionalTotal.get(countrys[i].getRegion())+count));
				}else{
					regionalTotal.put(countrys[i].getRegion(), (float) count);
				}
			}
		}
		
		Set<String> keyObjects = regionalTotal.keySet();
		for(String key:keyObjects){
			//for the region mean it is the total div by the number of years.
			System.out.println("Mean value of "+ indicator +" for the "+ key +" is: " + (regionalTotal.get(key)/(double)10));
		}
	}
	
	public static double getMedianOfIndicator(double [] indicatorValues){//get the median value of a countries indicator ie from 1990 - 2000 what is the median value.

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
		
		//with a given range from x-y y-z group all values into occurrences with in the ranges.
		HashMap<Float,Integer> rangeValuesOccernaces  = range(rangeStarts,rangeEnds,allValuesforIndicator);
		
		int sumOfOccurrences=countOfOccurrences(rangeValuesOccernaces);
		
		double middleValue = sumOfOccurrences/2.0;//double as for x/2 could be odd and rounding to make int will be less accurate 
		
		//get the ranges keys for access into map.
		Object [] keyObjects = rangeValuesOccernaces.keySet().toArray();
		Float[] rangeKeys = new  Float[keyObjects.length];
		for(int i=0; i<keyObjects.length; i++){
			rangeKeys[i] = (Float) keyObjects[i];//casting again
		}
		
		
		int subtotals = 0;//stores addition of all occurrence from start to where the middleValue is in the range of.
		Float startOfRange = new Float(0);//start of the range that middle is in
		Float endOfRange = new Float(0);//end of the range that middle is in.
		
		for(int i=0; i < rangeKeys.length; i++){//loop to fine values for the above variables. 
			
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

	public static void medianOfIndicatorForWorld(Country[] countrys,String indicator, double rangeDevider) {
		
		double[] rangeStarts;
		double[] rangeEnds;
		
		double[] allValuesforIndicator = new double[countrys.length*10];//length of the number countries by the number of years for each country
		double min;//starting value for the range
		double max;//ending value for the range
		double [] tempMinMax = countrys[0].getIndicatorValues(indicator);
		min = tempMinMax[0];
		max = tempMinMax[0];
		
		
		for(int i=0; i<216;i++){//loop through each country and get the lowest and highest values. And collect all values into one array
			double [] valuesForIndictor = countrys[i].getIndicatorValues(indicator);
			 
			for(int j = 0; j < valuesForIndictor.length; j++){
				if(valuesForIndictor[j] > max){
					max = valuesForIndictor[j];
				}
				else if(valuesForIndictor[j] < min){
					min = valuesForIndictor[j];
				}
				allValuesforIndicator[(i*10)+j] = valuesForIndictor[j];
			}
		}
		
	
		//need to make ranges;
		double startOfRange = min;
		int howManyRanges =  (int) (max / rangeDevider);
		
		if(max > howManyRanges*rangeDevider){// if the max value is more then the total value of the last range then max is out side of the range with a remainder so add another howManyRanges and you will get it in the last range.
			howManyRanges++;//there should only be a remainder left so adding another range should solve the problem
		}
		
		rangeStarts = new double[howManyRanges];
		rangeEnds = new double[howManyRanges];
		
		rangeStarts[0] = min;
		rangeEnds[0] = min + rangeDevider;
		
		for(int i=1; i < howManyRanges; i++){
			rangeStarts[i] = rangeEnds[i-1];
			rangeEnds[i] = rangeStarts[i] + rangeDevider;
		}
		
		
		System.out.println("Median for " +indicator+ " for the world is: "  + getMedianofWorldIndicator(rangeStarts, rangeEnds, allValuesforIndicator));
		
	}
	
	public static void medianOfIndicatorForRegion(Country[] countrys,String indicator, double rangeDevider,String region) {
		
		double[] rangeStarts;
		double[] rangeEnds;
		
		double[] allValuesforIndicator = new double[countrys.length*10];//length of the number countrys by the number of years for each country
		double min;//starting value for the range
		double max;//ending value for the range
		double [] tempMinMax = countrys[0].getIndicatorValues(indicator);
		min = tempMinMax[0];
		max = tempMinMax[0];
		
		
		for(int i=0; i<216;i++){//loop through each country and get the lowest and highest values. And collect all values into one array
			double [] valuesForIndictor = countrys[i].getIndicatorValues(indicator);
			if(countrys[i].isInRegion(region)){
				for(int j = 0; j < valuesForIndictor.length; j++){
					if(valuesForIndictor[j] > max){
						max = valuesForIndictor[j];
					}
					else if(valuesForIndictor[j] < min){
						min = valuesForIndictor[j];
					}
					allValuesforIndicator[(i*10)+j] = valuesForIndictor[j];
				}
			}
		}
		
	
		//need to make ranges;
		double startOfRange = min;
		int howManyRanges =  (int) (max / rangeDevider);
		
		if(max > howManyRanges*rangeDevider){// if the max value is more then the total value of the last range then max is out side of the range with a remainder so add another howManyRanges and you will get it in the last range.
			howManyRanges++;//there should only be a remainder left so adding another range should solve the problem
		}
		
		rangeStarts = new double[howManyRanges];
		rangeEnds = new double[howManyRanges];
		
		rangeStarts[0] = min;
		rangeEnds[0] = min + rangeDevider;
		
		for(int i=1; i < howManyRanges; i++){
			rangeStarts[i] = rangeEnds[i-1];
			rangeEnds[i] = rangeStarts[i] + rangeDevider;
		}
	
		System.out.println("Median for " +indicator+ " for " +region+ " is: "  + getMedianofWorldIndicator(rangeStarts, rangeEnds, allValuesforIndicator));
		
	}
}