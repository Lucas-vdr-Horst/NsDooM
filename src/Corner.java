import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Corner extends RelativeObject{
	
	public Corner(int x, int y) {
		super(x, y, Color.black);
		this.ObjectType = "Corner";
	}

	ArrayList<Integer> partOfWalls = new ArrayList<Integer>();
	ArrayList<Integer> partOfPlanes = new ArrayList<Integer>();
	
	@Override
	public void paint(Graphics g, Camera camera, World world, long currentFrame){
		//System.out.println("paint corner: " + partOfWalls);
		//System.out.println("amoutn of walls: " + world.wallsAdded);
		for(int i=0; i<partOfWalls.size(); i++){
			Wall wall = world.getWall(partOfWalls.get(i));
			RelativeObject corner1;
			RelativeObject corner2;
			if(wall.corner1id == id){
				corner1 = world.getRObject(wall.corner1id);
				corner2 = world.getRObject(wall.corner2id);
			} else {
				corner2 = world.getRObject(wall.corner1id);
				corner1 = world.getRObject(wall.corner2id);
			}
			//System.out.println(id + " " + connectedTo);
			
			if(closerToCamera(corner1, corner2, camera)){
				wall.paint(g, camera, world, currentFrame);
			}
			
			/**if(camera.viewType == 1){
				int xOnScreenOpt0 = calRXopts(this, camera)[0];
				int xOnScreenOpt1 = calRXopts(this, camera)[1];
				int xOnScreen = calRX(this, camera);
				int yOnScreen = calRY(this, camera);
				int size = (int) calRSize(this, camera);
				
				g.setColor(color);
				drawRect(xOnScreen, yOnScreen, size/10, size, camera, g);
				g.setColor(Color.yellow);
				drawRect(xOnScreenOpt0-5, yOnScreen, size/10, size, camera, g);
				g.setColor(Color.red);
				drawRect(xOnScreenOpt1-5, yOnScreen, size/10, size, camera, g);
			}*/
		}
	}
	
	@Override
	void addWall(int wallId){
		partOfWalls.add(wallId);
	}
	
	@Override
	void addPlane(int planeId){
		partOfPlanes.add(planeId);
	}
	
	void paintWall(Corner corner1, RelativeObject corner2, Camera camera, Graphics g, Color color) {
		int xOnScreen1 = calRX(corner1, camera);
		int yOnScreen1 = calRY(corner1, camera);
		int size1 = (int) calRSize(corner1, camera);
		
		int xOnScreen2 = calRX(corner2, camera);
		int yOnScreen2 = calRY(corner2, camera);
		int size2 = (int) calRSize(corner2, camera);
		
		g.setColor(color);
		drawRect(xOnScreen1, yOnScreen1, 2, size1, camera, g);
		
		System.out.println(xOnScreen1);
		System.out.println(xOnScreen2);
		
		int[] polX = new int[4];
		polX = new int[] {xOnScreen1, xOnScreen2, xOnScreen1, xOnScreen2};
		int[] polY = new int[4];
		polY = new int[] {yOnScreen1, yOnScreen2, yOnScreen1+size1, yOnScreen2+size2};
		
		g.fillPolygon(polY, polX, 4);
	}
}
