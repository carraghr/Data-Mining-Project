import java.io.*;
import java.util.Arrays;


public class Main {

	public static void main(String [] args){
		
		String [] selectedCountrys = new String [] {"Finland","Germany","Ireland","Poland","United States"};
		
		Country [] countrys = new Country [selectedCountrys.length];

		BufferedReader br;
		
		String temp1 ="", temp2="";//for error tracking.
		int lenght=0;//stores length of the array that row is split into. for error tracking
		
		int startYear = 1962, endYear = 1965, startPosition=0, endPosition=0; //for indexing values from one year to another.
		
		try {
			
			br = new BufferedReader(new FileReader(new File("C:/Users/Richard/Desktop/19_Topic_en_csv_v2.csv")));
			String row;
			
			int countryCount=0;
			
			while ((row = br.readLine()) != null && countryCount<countrys.length) {//read in row and make sure we are reading anything we don't need.

				String[] values = row.split(",");//for this line we should add in blank values so that we can index to were values are based on there year
				
				final int STARTOFYEARS = 3;//index of csv that the values for a year start
				
				startPosition = (startYear - 1961) + STARTOFYEARS;//indexing of the csv file
				endPosition = (endYear - 1961) + STARTOFYEARS;
				
				if(values.length > 3 && Arrays.asList(selectedCountrys).contains(values[0])){//make sure there is enough values to index results for the year. 
					
					countrys[countryCount] = new Country(values[0],values[1]);//add to array
					
					for(int i = 0; i < 61;i++){//61 is the number of max attributes loop through them all.
						if(startPosition < values.length && endPosition < values.length){//make sure values are there
							countrys[countryCount].setIndicatorName(values[2]);// add indicator name
							countrys[countryCount].setValuesForYears(values[2],Arrays.copyOfRange(values, startPosition, endPosition));	//then value for that name.			
						}
						//Attribute is skipped if it doesn't have any values
						row = br.readLine();
						values=row.split(",");
					}
					
					countryCount++;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Start pos: " + startPosition +" end pos: "+ endPosition);
			System.out.println("length "+lenght +"  "+temp1 + " f " + temp2);
		}
		

		System.out.println(CentralTendency.getMean(countrys[0].getIndicatorValues("CO2 emissions from liquid fuel consumption (kt)")));
		System.out.println(CentralTendency.getMedianOfCountryIndicator(countrys[0].getIndicatorValues("CO2 emissions from liquid fuel consumption (kt)")));
		double [] modeValues = CentralTendency.getMode(countrys[0].getIndicatorValues("CO2 emissions from liquid fuel consumption (kt)"));
		//modeValues are not sorted.
		for(double value:modeValues){
			System.out.print(value+", ");
		}	
	}
	//
	static String fillMissingValues(String row){
		String newRow = "";
		for(int i=0; i > -1; i = row.indexOf(",", i)){//loop till no more , in string
			/*
			 * need a way of getting the next comma and seen if there is any char in between them.
			 * if the very first value is missing the second col becomes the first so no need to have a may to deal with this.
			 * if the last value is missing then the row ends at the last actual value accord so no case for this needed here.
			 */
			int nextComma = row.indexOf(",",i);
			if(nextComma - i == 1){//if the index of the next , is one grater then i currently then they are next to each other and a value is missing.
				
				newRow+="unknown,";
			}
			else{//else there is a comma and you just need to add the value to the new row
				if(nextComma == -1){//last value so just add it.
					newRow+=row.substring(nextComma);
				}
				else{//its still in the middle.
					newRow+=row.substring(i, nextComma);
				}
			}
		}
		return newRow;
	}
}