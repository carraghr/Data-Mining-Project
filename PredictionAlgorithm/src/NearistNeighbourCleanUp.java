import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedList;

public class NearistNeighbourCleanUp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
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
			input = new BufferedReader(new FileReader(new File("C:/Users/richa/Desktop/New Data Set for Data Mining.csv")));
		
			//get all values
			String row;
			while ((row = input.readLine())!=null){
				if(row.contains(Attribute)){
					String [] attributValues = row.split(",");	//need to change this to ; for what other guy did.
					for(int i = 3; i < attributValues.length; i++){
						values.add(Float.valueOf(attributValues[i]));
					}
				}
			}
			out.close();
			
			//get max and min values.
			float min = values.getFirst(), max = values.getFirst();
			
			for(int i = 1; i<values.size();	i++){
				float value = values.get(i);
				if(value > max){
					max = value;
				}else if(value<min){
					min = value;
				}
			}
			
			//now to clean all values in the file
			
			input = new BufferedReader(new FileReader(new File("C:/Users/richa/Desktop/New Data Set for Data Mining.csv")));
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
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void Changedlayout(int numberOfAttributes,int numberOfYears){
		
		BufferedReader input;
		
		float [] []  allValues = new float [numberOfAttributes][numberOfYears];
		
		try{
			
			FileWriter fstream = new FileWriter((new File("")));
			
			BufferedWriter out = new BufferedWriter(fstream);
			input = new BufferedReader(new FileReader(new File("C:/Users/richa/Desktop/New Data Set for Data Mining.csv")));
		
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
						allValues[i][year] = Float.valueOf(values[year+3]);
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
