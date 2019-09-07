import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Player extends RelativeObject{
	public double viewAngle = 0;
	Camera camera;
	Weapon weapon;
	boolean alive = true;
	double fullHealth = 150;
	double health = fullHealth;

	public Player(int x, int y, Color color, Weapon weapon) {
		super(x, y, color);
		this.speed = 3;
		this.ObjectType = "Player";
		this.weapon = weapon;
	}
	
	@Override
	public void paint(Graphics g, Camera camera, World world, long currentFrame) {
		this.camera = camera;
		radius = 10;
		if(camera.viewType == 0) {
			int xOnScreen = (int) (x - camera.x + camera.screenWidth/2);
			int yOnScreen = (int) (y - camera.y + camera.screenHeight/2);
			
			g.setColor(color);
			g.fillOval((int)(xOnScreen - radius), (int)(yOnScreen - radius), (int)(radius*2), (int)(radius*2));
			g.setColor(Color.BLACK);
			g.drawLine(xOnScreen, yOnScreen, (int) ( xOnScreen + Math.sin(viewAngle)*100), (int) ( yOnScreen - Math.cos(viewAngle)*100));
			g.drawLine(xOnScreen, yOnScreen, (int) ( xOnScreen + Math.sin(viewAngle+camera.FOV)*100), (int) ( yOnScreen - Math.cos(viewAngle+camera.FOV)*100));
		}
		if(camera.viewType == 1) {
			g.setColor(Color.red);
			g.fillRect(25, camera.screenHeight-50, 250, 5);
			g.setColor(Color.green);
			g.fillRect(25, camera.screenHeight-50, (int) (250*(health/fullHealth)), 5);
		}
	}
	
	@Override
	public World update(ArrayList<String> keySaver, World world, long currentFrame) {
		if(alive) {
			if(health <= 0) {
				alive = false;
			}
			
			
			goOutCollision(world);
			
			if(keySaver.contains("D")) {
				y+= speed*Math.sin(viewAngle+camera.FOV/2);
				x+= speed*Math.cos(viewAngle+camera.FOV/2);
			}
			if(keySaver.contains("A")) {
				y-= speed*Math.sin(viewAngle+camera.FOV/2);
				x-= speed*Math.cos(viewAngle+camera.FOV/2);
			}
			if(keySaver.contains("S")) {
				x-= speed*Math.sin(viewAngle+camera.FOV/2);
				y+= speed*Math.cos(viewAngle+camera.FOV/2);
			}
			if(keySaver.contains("W")) {
				x+= speed*Math.sin(viewAngle+camera.FOV/2);
				y-= speed*Math.cos(viewAngle+camera.FOV/2);
			}
			if(keySaver.contains("Right")) {
				viewAngle+= 0.02;
			}
			if(keySaver.contains("Left")) {
				viewAngle-= 0.02;
			}
			if(keySaver.contains("Space")) {
				weapon.attack(this, world, camera, currentFrame);
			}
			
			
			if(viewAngle > 2*Math.PI) {
				viewAngle -= 2*Math.PI;
			}
			if(viewAngle < 0) {
				viewAngle += 2*Math.PI;
			}

			goOutCollision(world);
		}
		
		return world;
	}
	
	double getViewAngle() {
		return viewAngle;
	}
	
	@Override
	public void recievePoints(double points) {
		score +=  points;
	}
	
	@Override
	public void recieveDamage(double damage, RelativeObject dealer) {
		if(alive) {
			health -= damage;
		}
	}
}
