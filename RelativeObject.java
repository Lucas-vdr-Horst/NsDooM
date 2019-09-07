import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class RelativeObject extends PaintObject{
	public double x, y, speed;
	public double radius = 0;
	public int id;
	public Color color;
	public String ObjectType = "RelativeObject";
	double score = 0;
	boolean alive = false;
	
	public RelativeObject(double x, double y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}

	@Override
	public void paint(Graphics g, Camera camera, World world, long currentFrame) {
		
	}
	
	double calAngleToCamera(RelativeObject rObject, Camera camera) {
		double xDifference = rObject.x - camera.x;
		double yDifference = rObject.y - camera.y;
		double angleToCamera=0;
		if(yDifference==0) {
			if(xDifference>0) {
				angleToCamera = Math.PI/2;
			} else {
				angleToCamera = Math.PI*1.5;
			}
		} else {
			angleToCamera = Math.atan(xDifference/-yDifference);
		}
		if(yDifference < 0) {
			if(xDifference < 0) {
				angleToCamera += 2*Math.PI;
			}
		} else {
			angleToCamera += Math.PI;
		}
		return angleToCamera;
	}
	
	double calAngleToRObject(RelativeObject rObject1, RelativeObject rObject2){
		double xDifference = rObject1.x - rObject2.x;
		double yDifference = rObject1.y - rObject2.y;
		double angleToObject=0;
		if(yDifference==0) {
			if(xDifference>0) {
				angleToObject = Math.PI/2;
			} else {
				angleToObject = Math.PI*1.5;
			}
		} else {
			angleToObject = Math.atan(xDifference/-yDifference);
		}
		if(yDifference < 0) {
			if(xDifference < 0) {
				angleToObject += 2*Math.PI;
			}
		} else {
			angleToObject += Math.PI;
		}
		return angleToObject;
	}
	
	int[] calRXopts(RelativeObject rObject, Camera camera) { //calculate relative x location options
		int[] opts = new int[2];
		
		double angleToCamera = calAngleToCamera(rObject, camera);
		opts[0] = (int) ((angleToCamera-camera.viewAngle)/camera.FOV*camera.screenWidth);
		opts[1] = (int) (opts[0] + 2*Math.PI/camera.FOV*camera.screenWidth);
		
		return opts;
	}
	
	int determineRXopt(int[] opts, Camera camera) {
		int xOnScreen1 = opts[0];
		int xOnScreen2 = opts[1];
		//System.out.println(xOnScreen1 + " " + xOnScreen2);
		int firstToMiddle = Math.abs(xOnScreen1-(camera.screenWidth/2));
		int secondToMiddle = Math.abs(xOnScreen2-(camera.screenWidth/2));
		//System.out.println(firstToMiddle + " " + secondToMiddle);
		if(firstToMiddle < secondToMiddle) {
			//System.out.println("xOnScreen1 " + firstToMiddle);
			return 0;
		} else {
			//System.out.println("xOnScreen2 " + secondToMiddle);
			return 1;
		}
	}
	
	int calRX(RelativeObject rObject, Camera camera) { //calculate relative x location on screen
		int [] opts = calRXopts(rObject, camera);
		
		return opts[determineRXopt(opts, camera)];
	}
	
	int calRY(RelativeObject rObject, Camera camera) {
		int size = (int) calRSize(rObject, camera);
		return camera.screenHeight/2 - size/2 + camera.yShearing;
	}
	
	double calRSize(RelativeObject rObject, Camera camera) {
		double distanceToCamera = calDistanceToCamera(rObject, camera);
		//double angleDifference = Math.abs(calAngleToCamera(rObject, camera)-Math.PI)-(camera.viewAngle+camera.FOV/2);
		//double angleCorrection = angleDifference;
		return (camera.screenHeight*2) / ((distanceToCamera+0.01)/10);
	}
	
	void drawRect(int xOnScreen, int yOnScreen, int width, int height, Camera camera, Graphics g) {
		g.fillRect(xOnScreen, yOnScreen, width, height);
		//g.fillRect((int) (xOnScreen + 2*Math.PI/camera.FOV*camera.screenWidth), yOnScreen, width, height);
	}
	
	@Override
	public World update(ArrayList<String> keySaver, World world, long currentFrame) {
		return world;
		
	}
	
	double calDistanceToCamera(RelativeObject rObject, Camera camera){
		double xDifference = rObject.x - camera.x;
		double yDifference = rObject.y - camera.y;
		return Math.sqrt(Math.pow(xDifference, 2) + Math.pow(yDifference, 2));
	}
	
	void identify(int id){
		this.id = id;
	}
	
	boolean closerToCamera(RelativeObject rObject1, RelativeObject rObject2, Camera camera) {
		return calDistanceToCamera(rObject1, camera) < calDistanceToCamera(rObject2, camera);
		//return true;
	}
	
	void addWall(int wallId){
		
	}
	
	void addPlane(int planeId){
		
	}
	
	double calDistance(RelativeObject rObject1, RelativeObject rObject2) {
		return Math.sqrt(Math.pow(Math.abs(rObject1.x-rObject2.x), 2) + Math.pow(Math.abs(rObject1.y-rObject2.y), 2));
	}
	
	boolean isCollision(RelativeObject rObject1, RelativeObject rObject2) {
		return calDistance(rObject1, rObject2) < rObject1.radius+rObject2.radius;
	}
	
	public ArrayList<RelativeObject> checkCollisions(RelativeObject rObject, World world){
		ArrayList<RelativeObject> collisionCases = new ArrayList<RelativeObject>();
		for(int id=0; id<world.collisionables.size(); id++) {
			RelativeObject checkObject = world.getCollisionable(id);
			if(!(checkObject == rObject)) {
				if(isCollision(rObject, checkObject)) {
					collisionCases.add(checkObject);
				}
			}
		}
		return collisionCases;
	}
	
	void goOutCollision(World world) {
		ArrayList<RelativeObject> collisionCases = checkCollisions(this, world);
		//System.out.println(collisionCases);
		for(int i=0; i<collisionCases.size(); i++) {
			RelativeObject checkObject = collisionCases.get(i);
			
			double angleToCollision = calAngleToRObject(this, checkObject);
			while(isCollision(this, checkObject)) {
				x+= Math.sin(angleToCollision);
				y-= Math.cos(angleToCollision);
			}
		}
	}
	
	public void recieveDamage(double damage, RelativeObject dealer) {
		
	}
	
	void recievePoints(double points) {
		
	}
}
