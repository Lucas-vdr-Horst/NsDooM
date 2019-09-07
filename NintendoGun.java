import java.awt.Graphics;

public class NintendoGun extends Weapon{
	
	NintendoGun(){
		//img = loadImage("images/Gun0.png");
		img = loadImage("resources/Gun0.png");
		this.bulletSpeed = 6;
		this.damage = 10;
		this.attackTime = 1;
	}
	
	@Override
	public void paint(Graphics g, Camera camera, World world, long currentFrame){
		if(camera.viewType == 1) {
			double size = 1.8;
			int width = (int)(img.getWidth(null)*size);
			int height = (int)(img.getHeight(null)*size);
			//System.out.println("paintGun");
			//System.out.println(img);
			g.drawImage(img, camera.screenWidth/2 - width/2, camera.screenHeight - height, width, height, null);
		}
		
	}
	
	@Override
	void attack(RelativeObject rObject, World world, Camera camera, long currentFrame) {
		if(lastAttackFrame + attackTime < currentFrame) {
			world.addRObject(new Bullet(rObject.x, rObject.y, camera.viewAngle+camera.FOV/2, rObject, bulletSpeed, damage));
			lastAttackFrame = currentFrame;
		}
	}
}
