
public class InstanceOfCountry {

	private String name, region;
	
	private float distanceFromPoint;
	
	private float [] instanceValues;
	
	InstanceOfCountry(String name, String region, float [] values){
		this.name = name;
		this.region=region;
		this.instanceValues=values;
	}
	
	public float getDistanceFromPoint(InstanceOfCountry pointB){
		float total = 0;
		
		if(!this.region.equals(pointB.region)){
			total++;
		}
		
		for(int i=0; i < instanceValues.length; i++){
			float temp = instanceValues[i] - pointB.instanceValues[i];
			temp = temp * temp;
			total+=temp;
		}
		
		return (float)Math.sqrt(total);
	}
	
	public float getDistanceFromPointWithWeight(InstanceOfCountry pointB, float [] weight){
		float total = 0;
		if(!this.region.equals(pointB.region)){
			total++;
		}
		for(int i=0; i < instanceValues.length; i++){
			float temp = instanceValues[i] - pointB.instanceValues[i];
			temp = weight[i] * (temp * temp);
			total+=temp;
		}
		
		return (float)Math.sqrt(total);
	}
	
	public static InstanceOfCountry stringToInstance(String instanceString){
		
		if(instanceString == null | !(instanceString.length() > 1)){
			return null;
		}
		
		String [] instance = instanceString.split(",");
		String name = instance[0], region = instance[1];
		float [] values = new float[instance.length - 3]; 
		for(int i=0; i<values.length;i++){
			values[i] = Float.valueOf(instance[i+3]);
		}
		
		return new InstanceOfCountry(name, region, values);
	}
}