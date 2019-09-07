import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

public class World {
	HashMap<Integer, RelativeObject> rObjects = new HashMap<Integer, RelativeObject>();
	HashMap<Integer, Wall> walls = new HashMap<Integer, Wall>();
	HashMap<Integer, Plane> planes = new HashMap<Integer, Plane>();
	HashMap<Integer, Player> players = new HashMap<Integer, Player>();
	HashMap<Integer, RelativeObject> collisionables = new HashMap<Integer, RelativeObject>();
	HashMap<Integer, Cylinder> cylinders = new HashMap<Integer, Cylinder>();
	
	int rObjectsAdded = 0;
	int wallsAdded = 0;
	int planesAdded = 0;
	int playersAdded = 0;
	int collisionablesAdded = 0;
	int cylindersAdded = 0;
	
	ArrayList<Integer> deletedRObjects = new ArrayList<Integer>();
	
	Field ParentField;
	static Editor editor = new Editor();
	
	public World(){
		
	}
	
	int getAmountObjects(){
		return rObjects.size();
	}
	
	RelativeObject getRObject(int id){
		return rObjects.get(id);
	}
	
	void addRObject(RelativeObject rObject){
		int id;
		//System.out.println(deletedRObjects);
		if(deletedRObjects.size()>0) {
			id = deletedRObjects.get(0);
			deletedRObjects.remove(0);
		} else {
			id = rObjectsAdded;
			rObjectsAdded++;
		}
		//System.out.println(id);
		rObject.identify(id);
		rObjects.put(id, rObject);
		if(!(rObject.radius == 0)){
			collisionables.put(collisionablesAdded, rObject);
			collisionablesAdded++;
		}
		
	}
	
	void addCylinder(Cylinder cylinder) {
		cylinders.put(cylindersAdded, cylinder);
		cylindersAdded++;
		addRObject(cylinder);
	}
	
	Wall getWall(int id){
		return walls.get(id);
	}
	
	public void addWall(Wall wall){
		wall.identify(wallsAdded);
		walls.put(wallsAdded, wall);
		rObjects.get(wall.corner1id).addWall(wall.id);
		rObjects.get(wall.corner2id).addWall(wall.id);
		wallsAdded++;
	}
	
	private int addCorner(Corner corner) {
		corner.identify(rObjectsAdded);
		rObjects.put(rObjectsAdded, corner);
		rObjectsAdded++;
		return corner.id;
	}
	
	public void addPlayer(Player player){
		rObjects.put(rObjectsAdded, player);
		players.put(playersAdded, player);
		rObjectsAdded++;
		playersAdded++;
	}
	
	Player getPlayer(int id){
		return players.get(id);
	}
	
	public Wall createWall(int x1, int y1, int x2, int y2, Color color) {
		Corner corner1 = new Corner(x1, y1);
		Corner corner2 = new Corner(x2, y2);
		int corner1id = addCorner(corner1);
		int corner2id = addCorner(corner2);
		Wall wall = new Wall(corner1id, corner2id, color);
		return wall;
	}
	
	Plane getPlane(int id){
		return planes.get(id);
	}
	
	public Plane createTrianglePlane(int x1, int y1, int x2, int y2, int x3, int y3, Color color, int height){
		Corner corner1 = new Corner(x1, y1);
		Corner corner2 = new Corner(x2, y2);
		Corner corner3 = new Corner(x3, y3);
		ArrayList<Integer> cornerIds = new ArrayList<Integer>();
		cornerIds.add(addCorner(corner1));
		cornerIds.add(addCorner(corner2));
		cornerIds.add(addCorner(corner3));
		Plane plane = new Plane(color, cornerIds, height);
		return plane;
	}
	
	public void addPlane(Plane plane){
		plane.identify(planesAdded);
		planes.put(planesAdded, plane);
		rObjects.get(plane.cornerIds.get(0)).addPlane(plane.id);
		wallsAdded++;
	}
	
	static void editWorld(Mouse mouse){
		editor.edit(mouse);
	}
	
	RelativeObject getCollisionable(int id) {
		return collisionables.get(id);
	}
	
	void deleteRObject(RelativeObject rObject) {
		int id = rObject.id;
		deletedRObjects.add(id);
		rObjects.remove(id, rObject);
	}
	
	void doDamage(int id, double damage, RelativeObject dealer) {
		rObjects.get(id).recieveDamage(damage, dealer);
	}
	
	void givePoints(int id, double points) {
		rObjects.get(id).recievePoints(points);
	}
	
	void replaceCylinder(RelativeObject rObject, int id) {
		rObjects.replace(id, rObject);
	}
}
