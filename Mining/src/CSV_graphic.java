import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class CSV_graphic {
	
	static void createCSV(String fileName,String indicator){
		BufferedReader input;
		
		try{
			FileWriter fstream = new FileWriter((new File(fileName)));
			
			BufferedWriter out = new BufferedWriter(fstream);
			input = new BufferedReader(new FileReader(new File("C:/Users/richa/Desktop/New Data Set for Data Mining.csv")));
			
			String row;
			while ((row = input.readLine())!=null){
				if(row.indexOf(indicator)>=0){
					out.write(row);
					out.write("\n");
				}
			}
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void main(String [] args){
		CSV_graphic.createCSV("Test.csv","Population  total");
	}
}
