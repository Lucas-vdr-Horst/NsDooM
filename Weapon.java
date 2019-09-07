import java.awt.Image;

public class Weapon extends PaintObject{
	Image img;
	double bulletSpeed, damage, attackTime;
	long lastAttackFrame = 0;
	
	void attack(RelativeObject rObject, World world, Camera camera, long currentFrame) {
		
	}
}
