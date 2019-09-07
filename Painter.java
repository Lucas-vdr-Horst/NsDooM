import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

public class Painter extends JPanel {
	private static final long serialVersionUID = 1L;
	public ArrayList<PaintObject> Background;
	public ArrayList<RelativeObject> Middle;
	public ArrayList<PaintObject> Top;
	public Color backgroundColor;
	Camera camera;
	World world;
	long currentFrame = 0;
	
	public Painter(Color color, int viewType){
		backgroundColor = color;
		Background = new ArrayList<PaintObject>();
		Middle = new ArrayList<RelativeObject>();
		Top = new ArrayList<PaintObject>();
	}
	
	public void updateCamera(Camera camera){
		this.camera = camera;
	}
	
	public void updateWorld(World world){
		this.world = world;
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		setBackground(backgroundColor);
		
		Collections.sort(Middle, new Comparator<RelativeObject>() {
		    @Override
		    public int compare(RelativeObject ro1, RelativeObject ro2) {
		    	double ro1distance = ro1.calDistanceToCamera(ro1, camera);
		    	double ro2distance = ro2.calDistanceToCamera(ro2, camera);
		        if (ro1distance < ro2distance)
		            return 1;
		        if (ro1distance > ro2distance)
		            return -1;
		        return 0;
		    }
		}); 
		
		for(PaintObject po : Background){
			po.paint(g, camera, world, currentFrame);
		}
		for(PaintObject po : Middle){
			po.paint(g, camera, world, currentFrame);
		}
		for(PaintObject po : Top){
			po.paint(g, camera, world, currentFrame);
		}
	}
	
}
