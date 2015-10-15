import java.util.LinkedList;

public class Country {
	
	String name, countryCode;
	
	LinkedList<String> indicatorNames = new LinkedList<String>();
	
	/*
	 * Linked list in a linked list so that you can have indicator index with 
	 * */
	LinkedList<LinkedList<Float>> valuesForEachYear = new LinkedList<LinkedList<Float>>();
	
	Country(String name, String countryCode){
		
		this.name=name;
		this.countryCode=countryCode;
	}
	
	Country(){}
	
	void setIndicatorName(String indicatorName){
		
		if(!this.indicatorNames.contains(indicatorName)){
			this.indicatorNames.add(indicatorName);
		}
	}
	
	boolean setValuesForYears(String indicator, String [] values){
		
		int index = this.indicatorNames.indexOf(indicator);
		this.valuesForEachYear.add(new LinkedList<Float>());
		if( index > -1){
			for(String value:values){
				if(!value.isEmpty())
					this.valuesForEachYear.get(index).add(Float.parseFloat(value));
				else{
					this.valuesForEachYear.get(index).add(0.0f);
				}
			}
			return true;
		}else{
			return false;
		}
	}
	
	double [] getIndicatorValues(String indicatorName){
		
		int indicatorIndex = this.indicatorNames.indexOf(indicatorName);
		
		if(indicatorIndex > -1){
			
			Object[] values = this.valuesForEachYear.get(indicatorIndex).toArray();
			double [] retValues = new double[values.length];
			for(int i=0;i<retValues.length;i++){
				retValues[i] = this.valuesForEachYear.get(indicatorIndex).get(i);
			}
			return retValues;
		}
		double [] v = new double [0];
		return v;
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