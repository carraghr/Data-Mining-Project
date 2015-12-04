import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeMap;

public class NearestNeighbour {

	public static void main(String[] args) {
				
		try{
			BufferedReader input;

			input = new BufferedReader(new FileReader(new File(args[0])));//read from the file missing its real country name

			String row;
			int line = 0;
			while((row = input.readLine())!=null){
				
				InstanceOfCountry unknownCountry = InstanceOfCountry.stringToInstance(row);
				String [] topCountries = getTopInstances(unknownCountry, args[1]);
				
				TreeMap <String,Integer> NearestCountrysCount = new TreeMap<>();
				
				for(int i=0; i<topCountries.length;i++){//get country name and number of occurrences 
					if(NearestCountrysCount.containsKey(topCountries[i])){
						NearestCountrysCount.put(topCountries[i], NearestCountrysCount.get(topCountries[i])+1);
					}else{
						NearestCountrysCount.put(topCountries[i],1);
					}
				}
				
				//get the highest occurrence
				
				//in case there is more then on store all max == occurrence in a list
				
				LinkedList<String> occurrence = new LinkedList<>();
				
				Set<String> names = NearestCountrysCount.keySet();
				
				int max = 0;//save to do since each will at worst have one
				for(String x:names){
					if(NearestCountrysCount.get(x) > max){
						occurrence = new LinkedList<>();
						occurrence.add(x);
					}else if(NearestCountrysCount.get(x) == max){
						occurrence.add(x);
					}
				}
				System.out.println( (line + 1) + ": ");
				for(String name:occurrence){
					System.out.print(name+", ");
				}
				
				System.out.println();
			}
			
			input.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static String [] getTopInstances(InstanceOfCountry unknownCountry, String fileName){
		
		NearestInstances namesOfTopFive = new NearestInstances(5);
		
		try{
			BufferedReader input;

			input = new BufferedReader(new FileReader(new File(fileName)));

			String row;
			
			for(int i = 0; i<namesOfTopFive.size(); i++){//place first five on the list as the nearest anything else would skew results.
				row = input.readLine();
				float distance = unknownCountry.getDistanceFromPoint(InstanceOfCountry.stringToInstance(row));
				
				NearestInstance temp = new NearestInstance(row.substring(0, row.indexOf(",")),distance);
				
				namesOfTopFive.addToList(temp,i);
			}
			
			while((row=input.readLine())!=null){
				row = input.readLine();
				float distance = unknownCountry.getDistanceFromPoint(InstanceOfCountry.stringToInstance(row));
				
				NearestInstance temp = new NearestInstance(row.substring(0, row.indexOf(",")),distance);
				
				namesOfTopFive.checkList(temp);
			}
			input.close();
			return namesOfTopFive.names();
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
