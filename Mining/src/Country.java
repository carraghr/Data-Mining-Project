import java.util.LinkedList;

public class Country {
	
	String name, region;
	
	LinkedList<String> indicatorNames = new LinkedList<String>();
	
	/*
	 * Linked list in a linked list so that you can have indicator index with the indicatorNames matched up. 
	 * Easier for finding values.
	 */
	LinkedList<LinkedList<Float>> valuesForEachYear = new LinkedList<LinkedList<Float>>();
	
	Country(String name, String region){
		
		this.name=name;
		this.region=region;
	}
	
	Country(){}
	
	void setIndicatorName(String indicatorName){
		
		if(!this.indicatorNames.contains(indicatorName)){//just in case of a bad read.
			this.indicatorNames.add(indicatorName);
		}
	}
	
	boolean setValuesForYears(String indicator, String [] values){
		
		int index = this.indicatorNames.indexOf(indicator);
		this.valuesForEachYear.add(new LinkedList<Float>());
		if( index > -1){
			for(String value:values){
				if(value.equals("unknown")||value.isEmpty()){	
					this.valuesForEachYear.get(index).add(0.0f);
				}
				else{
					this.valuesForEachYear.get(index).add(Float.parseFloat(value));
				}
			}
			return true;
		}else{
			return false;
		}
	}
	
	double [] getIndicatorValues(String indicatorName){
		
		int indicatorIndex = this.indicatorNames.indexOf(indicatorName);
		
		if(indicatorIndex > -1){//making sure that the indicator name is in the country
			
			Object[] values = this.valuesForEachYear.get(indicatorIndex).toArray();
			double [] retValues = new double[values.length];
			for(int i=0;i<retValues.length;i++){
				retValues[i] = this.valuesForEachYear.get(indicatorIndex).get(i); //convert object into doubles.
			}
			return retValues;
		}
		double [] v = new double [0];
		return v;
	}
	
	boolean isInRegion(String region){
		return this.region.equals(region);
	}
	
	double getIndicatorValuesFromRegionForYear(String indicatorName,int year){
		double []  ret = getIndicatorValues(indicatorName);
		return ret[year];
	}
	
	void print(){
		System.out.println(this.name);
		for(String value:indicatorNames){
			System.out.print(value);
			double [] e = getIndicatorValues(value);
			for(double v:e){
				System.out.print(", "+v);
			}
			System.out.println();
		}
	}
}	