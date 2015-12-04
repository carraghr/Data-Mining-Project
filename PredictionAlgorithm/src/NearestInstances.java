public class NearestInstances{
	
	NearestInstance [] topList;
	
	public NearestInstances(int numberOfInstances){
		this.topList = new NearestInstance [numberOfInstances]; 
	}
	
	public void checkList(NearestInstance possibleNearest){
		
		for(int i = 0; i<topList.length;i++){
			if(topList[i].compareTo(possibleNearest) > 0){ //if top item is greater then the possibleNeastest value add it to that place
				addToList(possibleNearest,i);//update the list
				return;//no need to continue after this list is up to date now.
			}
		}
	}
	
	public int size(){
		return this.topList.length;
	}
	
	public void addToList(NearestInstance nearest, int index){
		
		
		if(index == topList.length - 1){
			topList[index] = nearest;
			return;
		}
		
		NearestInstance oldest = topList[index]; //keeps last varaible remove
		
		for(int i=index+1; i<topList.length;i++){
			
			NearestInstance temp = topList[i];
			topList[i] = oldest;
			oldest = temp;
		}
		
		topList[index] = nearest;
	}
	
	public String [] names(){
		String [] names = new String[topList.length];
		for(int i=0;i<topList.length;i++){
			names[i]=topList[i].country;
		}
		return names;
	}
}
