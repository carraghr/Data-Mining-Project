
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class WorkShop1 {

	public static void main(String[] args) {
		
		if(args.length == 1){
			List<String> selectedCountrys = Arrays.asList ("Finland","Germany","Ireland","Poland","United States");			
			
			List<String> selectedRegions = Arrays.asList("East Asia & Pacific","Europe & Central Asia","Latin America & Caribbean","Middle East & North Africa"
					,"North America","South Asia","Sub-Saharan Africa");//is the list of regions there is in the dataset
			
			BufferedReader readFile;
			
			HashMap <String, Integer> regionOccurrence = new HashMap<>();//Occurrence for each region
			
			String row;
			
			int countryName=0, region = 1, indicatorName = 2 , startOfYears = 3, endOfYears = 3 + 9;
			
			Country [] countrys = new Country [300];//there is less then this amount of countries in the data set
			List<String> countryTracker = new ArrayList();
			
			try{
				
				readFile = new BufferedReader(new FileReader(args[0]));
				
				while((row = readFile.readLine()) != null ){
					
					row = fillMissingValues(row); //if a value is missing then I place unknown in the slot. This is for the case were the string has a,b,,d
					String [] attriutes = row.split(",");//split all values on , 
					
					if(selectedRegions.contains(attriutes[region])){//if the list of regions contains the region of the new line add it. Skips unknown/ grouped countrys
				
						//To keep track the occurrences of region for each country in data set
						if(regionOccurrence.containsKey(attriutes[region]) && !countryTracker.contains(attriutes[countryName])){//country has appered before
							regionOccurrence.put(attriutes[region], regionOccurrence.get(attriutes[region])+1);
						}else if(!countryTracker.contains(attriutes[countryName])){//country is new so add a new region
							regionOccurrence.put(attriutes[region], 1);
						}
						
						if(countryTracker.contains(attriutes[countryName])){//country already made just add indicator and values for the years.
							countrys[countryTracker.indexOf(attriutes[countryName])].setIndicatorName(attriutes[indicatorName]);
							String [] valuesForYears = new String [10];
							System.arraycopy( attriutes, 3, valuesForYears, 0, valuesForYears.length );
							countrys[countryTracker.indexOf(attriutes[countryName])].setValuesForYears(attriutes[indicatorName], valuesForYears);
						}
						else{//need to create country, add it to tracker and add indicator and values.
							countryTracker.add(attriutes[countryName]);
							countrys[countryTracker.indexOf(attriutes[countryName])] = new Country(attriutes[countryName],attriutes[region]);//create new country
							countrys[countryTracker.indexOf(attriutes[countryName])].setIndicatorName(attriutes[indicatorName]);
							String [] valuesForYears = new String [10];
							System.arraycopy( attriutes, 3, valuesForYears, 0, valuesForYears.length );
							countrys[countryTracker.indexOf(attriutes[countryName])].setValuesForYears(attriutes[indicatorName], valuesForYears);
						}
					}
				}
				
				/*
				 * Do some maths down here after file has been processed.
				 */
				//System.out.println(countrys[countryTracker.indexOf("Ireland")].isInRegion(""));
				
				CentralTendency.meanOfIndicatorForEachCountryInRegion(countrys, countryTracker, regionOccurrence, "Population  total");
				//CentralTendency.medianOfIndicatorForWorld(countrys,"Population  total",5000);
				//CentralTendency.medianOfIndicatorForRegion(countrys,"Population  total",5000,"Europe & Central Asia");
				System.out.println();System.out.println();
				CentralTendency.meanOfIndicatorForRegion(countrys, countryTracker, regionOccurrence, "Population  total");
				
				
				//countrys[countryTracker.indexOf("Ireland")].print();
				
			}catch(FileNotFoundException  e){
				System.out.println("File not found: " + args[0]);
			}catch(IOException e){
				System.out.println(e);
			}
			
			
		}
		else{
			System.out.println("Paths to CSV file missing");
		}
		
	}
	
	static String fillMissingValues(String row){
		String newRow = "";

		for(int i=0; i > -1; i = row.indexOf(",", i + 1)){//loop till no more , in string
			//System.out.println("index: "+ i);
			
			int nextComma = row.indexOf(",",i+1);
			
			if(nextComma - i == 1){//if the index of the next , is one grater then i currently then they are next to each other and a value is missing.
				newRow+=",unknown";
			}
			else{//else there is a comma and you just need to add the value to the new row
				if(nextComma != -1){//last value so just add it.	
					newRow+=row.substring(i, nextComma);
				}
				else{//its still in the middle.
					newRow+=row.substring(i);
				}
			}
			
		}
		if(newRow.length() <= 0){
			return row;
		}
		
		return newRow;
	}
}
