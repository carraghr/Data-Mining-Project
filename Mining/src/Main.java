import java.io.*;
import java.util.Arrays;


public class Main {

	public static void main(String [] args){
		
		String [] selectedCountrys = new String [] {"Finland"};//,"Germany","Ireland","Poland","United States"};
		
		Country [] countrys = new Country [selectedCountrys.length];

		BufferedReader br;
		String temp1 ="", temp2="";
		int lenght=0;
		int startYear = 1962, endYear = 1965, startPosition=0, endPosition=0;
		try {
			
			br = new BufferedReader(new FileReader(new File("C:/Users/Richard/Desktop/19_Topic_en_csv_v2.csv")));
			String row;
			
			int countryCount=0;
			
			while ((row = br.readLine()) != null && countryCount<countrys.length) {

				String[] values = row.split(",");
				
				final int STARTOFYEARS = 3;
				
				startPosition = (startYear - 1961) + STARTOFYEARS;
				endPosition = (endYear - 1961) + STARTOFYEARS;
				
				if(values.length > 3 && Arrays.asList(selectedCountrys).contains(values[0])){
					
					countrys[countryCount] = new Country(values[0],values[1]);
					
					for(int i = 0; i < 61;i++){//61 is the number of max attributs 
						if(startPosition < values.length && endPosition < values.length){
							countrys[countryCount].setIndicatorName(values[2]);
							countrys[countryCount].setValuesForYears(values[2],Arrays.copyOfRange(values, startPosition, endPosition));				
						}
						//attribut is skipped if it doesn't have any values
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
		//countrys[0].print();
		System.out.println(CentralTendency.getMean(countrys[0].getIndicatorValues("CO2 emissions from liquid fuel consumption (kt)")));
		System.out.println(CentralTendency.getMedian(countrys[0].getIndicatorValues("CO2 emissions from liquid fuel consumption (kt)")));
		double [] modeValues = CentralTendency.getMode(countrys[0].getIndicatorValues("CO2 emissions from liquid fuel consumption (kt)"));
		//modeValues are not sorted.
		for(double value:modeValues){
			System.out.print(value+", ");
		}	
	}
}