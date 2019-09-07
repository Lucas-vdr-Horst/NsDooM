import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Plane extends PaintObject{
	int id, height;
	ArrayList<Integer> wallsIds = new ArrayList<Integer>();
	ArrayList<Integer> cornerIds = new ArrayList<Integer>();
	Color color;

	public Plane(Color color, ArrayList<Integer> cornerIds, int height) {
		this.color = color;
		this.cornerIds = cornerIds;
		this.height = height;
	}
	
	@Override
	public World update(ArrayList<String> keySaver, World world, long currentFrame) {
		return world;
		
	}
	
	@Override
	public void paint(Graphics g, Camera camera, World world, long currentFrame) {
		
	}
	
	void identify(int id){
		this.id = id;
	}
	
}
