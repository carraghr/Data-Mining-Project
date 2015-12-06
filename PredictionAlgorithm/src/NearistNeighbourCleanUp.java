import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedList;

public class NearistNeighbourCleanUp {

	public static void main(String[] args) {
	
	
		NearistNeighbourCleanUp.CleanUpAttribute("Access to electricity (% of population)", "results1.csv",  "C:/Users/richa/Desktop/Cleared-Database.csv");
		NearistNeighbourCleanUp.CleanUpAttribute("Agricultural land (sq. km)", "results2.csv",  "results1.csv");
		NearistNeighbourCleanUp.CleanUpAttribute("Agriculture  value added (% of GDP)", "results3.csv",  "results2.csv");
		NearistNeighbourCleanUp.CleanUpAttribute("Cereal yield (kg per hectare)", "results4.csv",  "results3.csv");
		NearistNeighbourCleanUp.CleanUpAttribute("CO2 emissions (kg per 2011 PPP $ of GDP)", "results5.csv",  "results4.csv");
		NearistNeighbourCleanUp.CleanUpAttribute("CO2 emissions (kt)", "results6.csv",  "results5.csv");
		NearistNeighbourCleanUp.CleanUpAttribute("CO2 emissions (metric tons per capita)", "results7.csv",  "results6.csv");
		NearistNeighbourCleanUp.CleanUpAttribute("CO2 emissions from gaseous fuel consumption (% of total)", "results8.csv",  "results7.csv");
		NearistNeighbourCleanUp.CleanUpAttribute("CO2 emissions from liquid fuel consumption (% of total)", "results9.csv",  "results8.csv");
		NearistNeighbourCleanUp.CleanUpAttribute("CO2 emissions from solid fuel consumption (% of total)", "results11.csv",  "results9.csv");
		NearistNeighbourCleanUp.CleanUpAttribute("Electric power consumption (kWh per capita)", "results12.csv",  "results11.csv");
		NearistNeighbourCleanUp.CleanUpAttribute("Electricity production from coal sources (% of total)", "results13.csv",  "results12.csv");
		NearistNeighbourCleanUp.CleanUpAttribute("Electricity production from hydroelectric sources (% of total)", "results14.csv",  "results13.csv");
		NearistNeighbourCleanUp.CleanUpAttribute("Electricity production from natural gas sources (% of total)", "results15.csv",  "results14.csv");
		NearistNeighbourCleanUp.CleanUpAttribute("Electricity production from nuclear sources (% of total)", "results16.csv",  "results15.csv");
		NearistNeighbourCleanUp.CleanUpAttribute("Electricity production from oil sources (% of total)", "results17.csv",  "results16.csv");
		NearistNeighbourCleanUp.CleanUpAttribute("Electricity production from renewable sources  excluding hydroelectric (% of total)", "results18.csv",  "results17.csv");
		NearistNeighbourCleanUp.CleanUpAttribute("Energy use (kg of oil equivalent per capita)", "results19.csv",  "results18.csv");
		NearistNeighbourCleanUp.CleanUpAttribute("Energy use (kg of oil equivalent) per $1 000 GDP (constant 2011 PPP)", "results20.csv",  "results19.csv");
		NearistNeighbourCleanUp.CleanUpAttribute("Foreign direct investment  net inflows (BoP  current US$)", "results21.csv",  "results20.csv");
		NearistNeighbourCleanUp.CleanUpAttribute("Forest area (sq. km)", "results22.csv",  "results21.csv");
		NearistNeighbourCleanUp.CleanUpAttribute("GDP (current US$)", "results23.csv",  "results22.csv");
		NearistNeighbourCleanUp.CleanUpAttribute("GHG net emissions/removals by LUCF (Mt of CO2 equivalent)", "results24.csv",  "results23.csv");
		NearistNeighbourCleanUp.CleanUpAttribute("Investment in energy with private participation (current US$)", "results25.csv",  "results24.csv");
		NearistNeighbourCleanUp.CleanUpAttribute("Investment in telecoms with private participation (current US$)", "results26.csv",  "results25.csv");
		NearistNeighbourCleanUp.CleanUpAttribute("Investment in transport with private participation (current US$)", "results27.csv",  "results26.csv");
		NearistNeighbourCleanUp.CleanUpAttribute("Investment in water and sanitation with private participation (current US$)", "results28.csv",  "results27.csv");
		NearistNeighbourCleanUp.CleanUpAttribute("Methane emissions (kt of CO2 equivalent)", "results29.csv",  "results28.csv");
		NearistNeighbourCleanUp.CleanUpAttribute("Nitrous oxide emissions (thousand metric tons of CO2 equivalent)", "results30.csv",  "results29.csv");
		NearistNeighbourCleanUp.CleanUpAttribute("Other greenhouse gas emissions  HFC  PFC and SF6 (thousand metric tons of CO2 equivalent)", "results31.csv",  "results30.csv");
		NearistNeighbourCleanUp.CleanUpAttribute("Population  total", "results32.csv",  "results31.csv");
		NearistNeighbourCleanUp.CleanUpAttribute("Population growth (annual %)", "results33.csv",  "results32.csv");
		NearistNeighbourCleanUp.CleanUpAttribute("Population in urban agglomerations of more than 1 million (% of total population)", "results34.csv",  "results33.csv");
		NearistNeighbourCleanUp.CleanUpAttribute("Population living in areas where elevation is below 5 meters (% of total population)", "results35.csv",  "results34.csv");
		NearistNeighbourCleanUp.CleanUpAttribute("Poverty headcount ratio at $1.25 a day (PPP) (% of population)", "results36.csv",  "results35.csv");
		NearistNeighbourCleanUp.CleanUpAttribute("Urban population", "results37.csv",  "results36.csv");
		NearistNeighbourCleanUp.CleanUpAttribute("Urban population growth (annual %)", "results38.csv",  "results37.csv");
		
		NearistNeighbourCleanUp.Changedlayout(39, 11,"TrainingDataChanged.csv","TrainingData.csv");
		
		NearistNeighbourCleanUp.Changedlayout(39, 13,"TestingDataChanged.csv","TestingData.csv");
	}
	
	public static void CleanUpAttribute(String Attribute, String file,String fileName){
		/*
		 * For this method we need to go through with attribute we need to normalise values that are two large.
		 * get each value for an attribute and keep track of the highest and lowest value.
		 * perform calculation on each value and replace old value with the result
		 */
		BufferedReader input;
		
		LinkedList <Float> values = new LinkedList<>();
		
		try{
			
			FileWriter fstream = new FileWriter((new File(file)));
			
			BufferedWriter out = new BufferedWriter(fstream);
			input = new BufferedReader(new FileReader(new File(fileName)));
		
			//get all values
			String row;
			while ((row = input.readLine())!=null){
				//System.out.println(fileName + " "+ row);
				if(row.contains(Attribute)){
					String [] attributValues = row.split(",");	//need to change this to ; for what other guy did.
					for(int i = 3; i < attributValues.length; i++){
						values.add(Float.valueOf(attributValues[i]));
					}
				}
			}
			//System.out.println(fileName + " "+ row);
			input.close();
			
			//get max and min values.
			float min = values.getFirst();
			float max = values.getFirst();
			
			for(int i = 1; i<values.size();	i++){
				float value = values.get(i);
				if(value > max){
					max = value;
				}else if(value<min){
					min = value;
				}
			}
			
			//now to clean all values in the file
			
			input = new BufferedReader(new FileReader(new File(fileName)));
			while ((row = input.readLine())!=null){
				if(row.contains(Attribute)){
					String [] valuesToBeChanged = row.split(",");	//need to change this to ; for what other guy did.
					String newLine = "";
					for(int i=0; i < 3;i++){
						newLine+= valuesToBeChanged[i] + ",";
					}
					for(int i=3; i<valuesToBeChanged.length;i++){
						float tempValue = Float.valueOf(valuesToBeChanged[i]);
						tempValue = (tempValue - min) / (max - min);
						newLine+=tempValue+",";
					}
					out.write(newLine);
					out.write("\n");
				}else{
					row = row.replace("\n", "").replace("\r", "");
					out.write(row);
					out.write("\n");
				}
			}
			out.close();
			input.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void Changedlayout(int numberOfAttributes,int numberOfYears, String newFile,String oldFile){
		
		BufferedReader input;
		
		String [] []  allValues = new String [numberOfAttributes][numberOfYears];
		
		try{
			
			FileWriter fstream = new FileWriter((new File(newFile)));
			
			BufferedWriter out = new BufferedWriter(fstream);
			input = new BufferedReader(new FileReader(new File(oldFile)));
		
			//get all values
			String row;
			while ((row = input.readLine())!=null){
				String countryName ="",region="";
			
				for(int i = 0; i<numberOfAttributes;i++){
					//get country and region
					
					String [] values = row.split(",");
					countryName = values[0];
					region = values[1];
					for(int year = 0; year<numberOfYears; year++){ //for index you need to add 3 to year when getting from values array
						allValues[i][year] = values[year+3];
					}
					row = input.readLine();
				}
				
				//now that all the data is in array just print it all out
				
				for(int year = 0; year<numberOfYears; year++){
					String newLine = countryName + "," + region + ",";
					
					for(int i = 0; i<numberOfAttributes; i++){ //for each year get all attribute values.
						newLine+= allValues[i][year]+",";
					}
					out.write(newLine);
					out.write("\n");
				}
			}
			out.close();			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
