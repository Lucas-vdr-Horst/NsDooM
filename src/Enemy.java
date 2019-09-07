import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class Enemy extends RelativeObject{
	double fullHealth = 100;
	double health = fullHealth;
	double pointsWorth = 100;

	public Enemy(double x, double y) {
		super(x, y, Color.cyan);
		this.ObjectType = "Enemy";
	}
	
	void drawImage(Image Img, Camera camera, Graphics g, World world, int size, int x, int y){
		//System.out.println("paintEnemy");
		//System.out.println(Img);
		g.drawImage(Img, x - size/2, y, size, size, null);
	}

}
