import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

public class Pinky extends Enemy{
	boolean alive = true;
	RelativeObject lastDamageDealer;
	Image[] walk = new Image[4];
	Image dead = loadImage("resources/SARGN0.png");
	Image attack = loadImage("resources/SARGF1.png");
	Image img = walk[0];

	long lastAttackFrame = 0;
	double attackTime = 15;

	public Pinky(double x, double y) {
		super(x, y);
		this.radius = 10;
		this.speed = 2;
		this.ObjectType = "Pinky";
		this.fullHealth = 100;
		this.health = fullHealth;
		this.pointsWorth = 100;
		for(int i=0; i<=3; i++) {
			walk[i] = loadImage("resources/PinkyWalking"+ i +".png");
		}
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
			
			drawImage(img, camera, g, world, size, xOnScreen, yOnScreen);
			if(alive) {
				img = walk[(int) (Math.round((currentFrame/10+id)%4))];
				if(img == null) {
					img = walk[0];
				}
			}
			
			if(alive) {
				g.setColor(Color.red);
				g.fillRect(xOnScreen-size/2, yOnScreen-size/7, size, size/15);
				g.setColor(Color.green);
				g.fillRect(xOnScreen-size/2, yOnScreen-size/7, (int) (size*(health/fullHealth)), size/15);
			}
		}
	}
	
	@Override
	public World update(ArrayList<String> keySaver, World world, long currentFrame) {
		if(alive) {
			if(health <= 0) {
				//System.out.println("dead");
				alive = false;
				img = dead;
				color = Color.yellow;
				world.givePoints(lastDamageDealer.id, pointsWorth);
			}
			
			goOutCollision(world);
			RelativeObject player = world.getPlayer(0);
			double angleToPlayer = calAngleToRObject(this, player);
			
			speed = 1+calDistance(player, this)/500;
			x-= speed*Math.sin(angleToPlayer);
			y+= speed*Math.cos(angleToPlayer);
			
			goOutCollision(world);
			
			if(calDistance(player, this) < player.radius+this.radius+10) {
				if(lastAttackFrame+attackTime < currentFrame) {
					world.doDamage(player.id, 5, this);
					lastAttackFrame = currentFrame;
					img = attack;
				}
			}
		}
		
		return world;
	}
	
	@Override
	public void recieveDamage(double damage, RelativeObject dealer) {
		if(alive) {
			health -= damage;
			lastDamageDealer = dealer;
		}
	}
}
