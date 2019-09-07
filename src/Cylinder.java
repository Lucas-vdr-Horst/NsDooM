import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Cylinder extends RelativeObject{
	Random rand = new Random();

	public Cylinder(double x, double y, Color color) {
		super(x, y, color);
		this.radius = 10;
		this.ObjectType = "Cylinder";
	}
	
	@Override
	public void paint(Graphics g, Camera camera, World world, long currentFrame) {
		if(camera.viewType == 0) {
			g.setColor(color);
			int xOnScreen = (int) (x - camera.x + camera.screenWidth/2);
			int yOnScreen = (int) (y - camera.y + camera.screenHeight/2);
			g.fillOval((int)(xOnScreen - radius), (int)(yOnScreen - radius), (int)(radius*2), (int)(radius*2));
		} else if(camera.viewType == 1) {
			
			int xOnScreen = calRX(this, camera);
			int yOnScreen = calRY(this, camera);
			int size = (int) calRSize(this, camera);
			
			g.setColor(color);
			drawRect(xOnScreen-size/2, yOnScreen, size, size, camera, g);
		}
	}
	
	void move(RelativeObject rObject, double spawnRadius) {
		if(calDistance(rObject, this) > spawnRadius) {
			x = rObject.x + Math.sin(rand.nextInt((int)(Math.PI*2*1000))/100)*spawnRadius;
			y = rObject.y - Math.cos(rand.nextInt((int)(Math.PI*2)))*spawnRadius;
		}
	}
}
