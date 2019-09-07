import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Background extends PaintObject{
	Color color;

	public Background(Color color) {
		this.color = color;
	}
	
	@Override
	public void paint(Graphics g, Camera camera, World world, long currentFrame){
		if(camera.viewType == 1) {
			g.setColor(color);
			int horizon = camera.screenHeight/2+camera.yShearing;
			g.fillRect(0, horizon, camera.screenWidth, camera.screenHeight-horizon);
		}
	}
	
	@Override
	public World update(ArrayList<String> keySaver, World world, long currentFrame){
		return world;
		
	}
}
