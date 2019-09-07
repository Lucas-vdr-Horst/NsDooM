import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class PaintObject {
	
	public PaintObject(){
		
	}

	public void paint(Graphics g, Camera camera, World world, long currentFrame){
		
	}
	
	public World update(ArrayList<String> keySaver, World world, long currentFrame) {
		return world;
	}
	
	double getViewAngle() {
		return 0;
	}
	
	public Image loadImage(String fileName){
		Image img = null;
		try {
			//img = ImageIO.read(new File(fileName));
			img = ImageIO.read(PaintObject.class.getResourceAsStream(fileName));
		} catch (IOException e) {
			System.out.println("Something went wrong while loading the image.");
		}
		return img;
	}
}
