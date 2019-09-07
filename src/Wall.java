import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Wall extends PaintObject{
	int id;
	int corner1id;
	int corner2id;
	Color color;

	public Wall(int corner1id, int corner2id, Color color) {
		this.corner1id = corner1id;
		this.corner2id = corner2id;
		this.color = color;
	}
	
	@Override
	public World update(ArrayList<String> keySaver, World world, long currentFrame) {
		return world;
		
	}
	
	@Override
	public void paint(Graphics g, Camera camera, World world, long currentFrame) {
		//System.out.println("paint wall");
		RelativeObject corner1 = world.getRObject(corner1id);
		RelativeObject corner2 = world.getRObject(corner2id);			
		g.setColor(color);
		
		if(camera.viewType == 0) {
			int x1 = (int) (corner1.x - camera.x + camera.screenWidth/2);
			int y1 = (int) (corner1.y - camera.y + camera.screenHeight/2);
			int x2 = (int) (corner2.x - camera.x + camera.screenWidth/2);
			int y2 = (int) (corner2.y - camera.y + camera.screenHeight/2);
			
			g.drawLine(x1, y1, x2, y2);
			
		} else if(camera.viewType == 1) {
			int x1[] = corner1.calRXopts(corner1, camera);
			int y1 = corner1.calRY(corner1, camera);
			int x2[] = corner2.calRXopts(corner2, camera);
			int y2 = corner2.calRY(corner2, camera);
			//System.out.println(x1 + " " + x2);
		    int yPoly[] = {y1, y1+(int)(corner1.calRSize(corner1, camera)), y2+(int)(corner1.calRSize(corner2, camera)), y2};
			
			if(Math.abs(x1[0] - x2[0]) < camera.screenWidth/(camera.FOV/Math.PI)) {
				int xPolyOpt0[] = {x1[0], x1[0], x2[0], x2[0]};
				int xPolyOpt1[] = {x1[1], x1[1], x2[1], x2[1]};
			    g.fillPolygon(xPolyOpt0, yPoly, yPoly.length);
			    g.fillPolygon(xPolyOpt1, yPoly, yPoly.length);
			} else {
				int xPolyOpt0[] = {x1[1], x1[1], x2[0], x2[0]};
				int opt1x1 = (int) (x1[1]-2*Math.PI/camera.FOV*camera.screenWidth);
				int opt1x2 = (int) (x2[0]-2*Math.PI/camera.FOV*camera.screenWidth);
				int xPolyOpt1[] = {opt1x1, opt1x1, opt1x2, opt1x2};
				//int opt2x1 = (int) (x1[1]+2*Math.PI/camera.FOV*camera.screenWidth);
				//int opt2x2 = (int) (x2[0]+2*Math.PI/camera.FOV*camera.screenWidth);
				int xPolyOpt2[] = {opt1x1, opt1x1, opt1x2, opt1x2};
			    g.fillPolygon(xPolyOpt0, yPoly, yPoly.length);
			    g.fillPolygon(xPolyOpt1, yPoly, yPoly.length);
			    g.fillPolygon(xPolyOpt2, yPoly, yPoly.length);
			}
		}
	}
	
	void identify(int id){
		this.id = id;
	}
	
}
