
public class NearestInstance implements Comparable<NearestInstance>{

	String country;
	float distance;
	
	public NearestInstance(String country,float distance) {
		this.country=country;
		this.distance=distance;
	}
	
	public int compareTo(NearestInstance instanceB) {
		
		if(this.distance > instanceB.distance){
			return 1;
		}
		else if(this.distance < instanceB.distance){
			return -1;
		}
		return 0;
	}
}
