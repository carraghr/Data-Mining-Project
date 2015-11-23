
public class Dispersion {

	
	public static void getInterquartilRange(double [] values){
		double [] sortedValues = CentralTendency.sortArrayOfDoubles(values);
		double firstQu;
		double thirdQu;
		int upper = sortedValues.length / 2;	
		firstQu = sortedValues[(upper/2)];
		thirdQu = sortedValues[(upper/2)+upper];
		
		System.out.println("first quartile: " + firstQu +"\n_third quartile: " + thirdQu);
		
		double interQuartile = thirdQu - firstQu;
		
		System.out.println("Interquartile Range: " + interQuartile);
	}
	
	public static void getStandardDeviation(double [] values){
		
		double standardDeviation =  getVariance(values);
		standardDeviation = Math.sqrt(standardDeviation);
		System.out.println(standardDeviation);
	}

	public static double getVariance(double [] values) {
		double result = 0;
		double mean = CentralTendency.getMean(values);
		
		for(int i = 0; i < values.length; i++){
			result += Math.pow((values[i]-mean), 2); 
		}
		
		double temp = (double)1/(double)values.length;  //other wise 0.0 every time
		result = temp * result;
		
		return result;
	}
}
