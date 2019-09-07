
public class Camera {
	public int viewType, screenWidth, screenHeight, yShearing;
	public double  FOV, viewAngle, x, y;
	
	Camera(double x, double y, int viewType, double FOV, double viewAngle, int screenWidth, int screenHeight, int yShearing){
		this.x = x;
		this.y = y;
		this.viewType = viewType;
		this.FOV = FOV;
		this.viewAngle = viewAngle;
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.yShearing = yShearing;
	}
}
