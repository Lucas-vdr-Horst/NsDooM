import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Bullet extends RelativeObject{
	double angle;
	double speed;
	double damage;
	RelativeObject owner;
	boolean bulletHole = false;
	
	public Bullet(double x, double y, double angle, RelativeObject owner, double speed, double damage) {
		super(x, y, Color.gray);
		this.angle = angle;
		this.owner = owner;
		this.ObjectType = "Bullet";
		this.speed = speed;
		this.damage = damage;
	}
	
	@Override
	public void paint(Graphics g, Camera camera, World world, long currentFrame) {
		if(camera.viewType == 0) {
			g.setColor(Color.BLACK);
			int xOnScreen = (int) (x - camera.x + camera.screenWidth/2);
			int yOnScreen = (int) (y - camera.y + camera.screenHeight/2);
			g.fillOval(xOnScreen, yOnScreen, 5, 5);
		} else if(camera.viewType == 1) {
			g.setColor(color);
			
			int xOnScreen = calRX(this, camera);
			//int yOnScreen = (int) (calRY(this, camera));
			int yOnScreen = camera.screenHeight/2 + camera.yShearing;
			int size = (int) calRSize(this, camera)/15;
			
			g.fillOval(xOnScreen - size/2, yOnScreen + size, size, size);
		}
		if(calDistanceToCamera(this, camera) > 2000) {
			removeBullet(world, new ArrayList<RelativeObject>());
		}
	}
	
	@Override
	public World update(ArrayList<String> keySaver, World world, long currentFrame) {
		if(!(bulletHole)) {
			//System.out.println(angle);
			
			x+= speed*Math.sin(angle);
			y-= speed*Math.cos(angle);
			ArrayList<RelativeObject> collisions = checkCollisions(this, world);
			if(collisions.size() > 0) {
				if(collisions.contains(owner)) {
					if(collisions.size() > 1) {
						removeBullet(world, collisions);
					}
				} else  {
					removeBullet(world, collisions);
				}
				
			}
		}
		
		return world;
	}
	
	void removeBullet(World world, ArrayList<RelativeObject> collisions) {
		boolean deleteBullet = true;
		for(RelativeObject rObject : collisions) {
			if(rObject.ObjectType == "Cylinder") {
				//deleteBullet = false;
				bulletHole = true;
			} else if(rObject.ObjectType == "Pinky") {
				//System.out.println("damage");
				world.doDamage(rObject.id, damage, owner);
			}
		}
		
		if(deleteBullet) {
			world.deleteRObject(this);
		}
	}
}
