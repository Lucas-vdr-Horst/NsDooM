import java.util.ArrayList;

public class Field {
	ArrayList<Integer> subFields = new ArrayList<Integer>();
	ArrayList<Integer> rObjects = new ArrayList<Integer>();
	int maxObjects, subId;
	int xMin, xMax, yMin, yMax;
	
	public Field(int maxObjects, int subId, int xMin, int xMax, int yMin, int yMax){
		this.maxObjects = maxObjects;
		this.subId = subId;
		
		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;
		
	}
	
	public void addROject(int rObject){
		if(rObjects.size() >= maxObjects){
			
		} else {
			rObjects.add(rObject);
		}
	}
	
}
