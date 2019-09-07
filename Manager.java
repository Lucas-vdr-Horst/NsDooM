import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
//import java.time.Duration;
//import java.time.Instant;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Manager implements KeyListener, MouseListener{
	JFrame frame;
	Painter painter;
	ArrayList<String> keySaver = new ArrayList<String>();
	public Mouse mouse = new Mouse();
	Random rand = new Random();
	World world = new World();
	Camera camera;
	
	int firstViewType = 1; //0=TopDown, 1=FirstPerson
	double firstFOV = Math.PI/2;
	int maxFrameRate = 60;
	int frameWidth = 960;
	int frameHeight = 540;
	
	long timeAlive= 0;
	boolean running = true;
	
	public Manager(){
		System.setProperty("sun.java2d.opengl", "true");
		
		painter = new Painter(Color.GRAY, 0);
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		makeObjects();
		frame.add(painter);
		frame.setSize(frameWidth, frameHeight);
		frame.setTitle("NsDooM");
        frame.addKeyListener(this);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        
		
        long currentFrame = 0;
        Player firstObject = world.getPlayer(0);
		//camera only holds and give information about the view to paintObjects
		camera = new Camera(firstObject.x, firstObject.y, firstViewType, firstFOV, 0, frameWidth, frameHeight, 0);
		double score = 0;
		
		while(running){
			//Instant starts = Instant.now();
			
			//System.out.println(keySaver);
			painter.Middle.clear();
			for(int id = 0; id < world.getAmountObjects(); id++){
				if (!(world.deletedRObjects.contains(id))) {
					addHashToMiddle(id);
				}
			}
			
			camera.x = firstObject.x;
			camera.y = firstObject.y;
			camera.viewAngle = firstObject.getViewAngle();
			//camera.screenWidth = frame.getWidth();
			//camera.screenHeight = frame.getHeight();
			
			if(firstObject.score > score) {
				score = firstObject.score;
				System.out.println("score: " + score);
				addRandomPinkys(firstObject, 2, 3000);
			}
			
			if(keySaver.contains("Up")){
				camera.yShearing+= 3;
			}
			if(keySaver.contains("Down")){
				camera.yShearing-= 3;
			}
			
			painter.updateCamera(camera);
			painter.updateWorld(world);
			painter.currentFrame = currentFrame;
			painter.repaint();
			
			for(PaintObject po : painter.Background){
				po.update(keySaver, world, currentFrame);
			}
			for(int id = 0; id < world.getAmountObjects(); id++){
				RelativeObject rObject = world.getRObject(id);
				if (!(world.deletedRObjects.contains(id))) {
					rObject.update(keySaver, world, currentFrame);
				}
			}
			for(PaintObject po : painter.Top){
				po.update(keySaver, world, currentFrame);
			}
			
			World.editWorld(mouse);
			
			mouse.click = false;
			
			//Instant ends = Instant.now();
			//float timeDifference = Duration.between(starts, ends).toMillis();
			float timeDifference = 0;
			//System.out.println(timeDifference);
			try {
				Thread.sleep((long) (10-timeDifference));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//addRandomCylinders(firstObject, 1, 3000);
			/**int i = rand.nextInt(0+world.rObjects.size());
			if(world.rObjects.get(i) != null) {
				Cylinder tmpCylinder = getRandomCylinder(firstObject, 3000);
				world.rObjects.get(i).x = tmpCylinder.x;
				world.rObjects.get(i).y = tmpCylinder.y;
			}*/
			world.cylinders.get(rand.nextInt(world.cylinders.size())).move(firstObject, 3000);
			if(firstObject.alive) {
				timeAlive = currentFrame;
			}
			if(timeAlive + 200 < currentFrame) {
				running = false;
			}
			
			currentFrame++;
		}
		frame.dispose();
	}
	
	private void makeObjects() {
		world.addPlayer(new Player(0, 0, Color.GREEN, new NintendoGun()));
		/**painter.Top.add(new PaintObject(100, 100, Color.RED));
		painter.Top.add(new PaintObject(100, 150, Color.YELLOW));
		painter.Top.add(new PaintObject(100, 200, Color.GREEN));
		painter.Top.add(new PaintObject(100, 250, Color.BLUE));*/
		painter.Top.add(new NintendoGun());
		//world.addWall(world.createWall(10, 100, -200, 50, Color.WHITE));
		
		/**world.addRObject(new Cylinder(200, 200, Color.RED));
		world.addRObject(new Cylinder(-100, -100, Color.YELLOW));
		world.addRObject(new Cylinder(300, 50, Color.BLUE));
		world.addRObject(new Cylinder(-200, 300, Color.BLACK));
		world.addRObject(new Cylinder(50, 300, Color.GREEN));
		world.addRObject(new Cylinder(200, -100, Color.PINK));*/
		painter.Background.add(new Background(Color.DARK_GRAY));
		
		
		/**for(int i = 0; i < 5; i++) {
			world.addRObject(new Corner(rand.nextInt(500)-600, rand.nextInt(500)-600, Color.white, new ArrayList<Integer>(rObjectsAdded+1)));
		}*/
		
		for(int i = 0; i < 1000; i++) {
			world.addCylinder(new Cylinder(rand.nextInt(10000)-5000, rand.nextInt(10000)-5000, Color.red));
		}
		/**Corner corner1 = new Corner(50, 0, Color.RED);
		corner1.addConnectPoint(rObjectsAdded + 1);
		corner1.addConnectPoint(rObjectsAdded + 2);
		world.addRObject(corner1);
		Corner corner2 = new Corner(100, 100, Color.white);
		corner2.addConnectPoint(rObjectsAdded - 1);
		corner2.addConnectPoint(rObjectsAdded + 1);
		world.addRObject(corner2);
		Corner corner3 = new Corner(-200, -100, Color.BLUE);
		corner3.addConnectPoint(rObjectsAdded - 1);
		corner3.addConnectPoint(rObjectsAdded - 2);
		world.addRObject(corner3);*/
		
		/**Corner corner4 = new Corner(50, 0, Color.pink);
		corner4.addConnectPoint(world.getAmountObjects() + 1);
		world.addRObject(corner4);
		Corner corner5 = new Corner(100, 100, Color.cyan);
		corner5.addConnectPoint(world.getAmountObjects() - 1);
		world.addRObject(corner5);*/
		
		//world.addWall(world.createWall(-100, -200, 500, 500, Color.WHITE));
		
		/**addRObject(new RelativeObject(200, -100, Color.PINK));
		addRObject(new RelativeObject(200, -100, Color.PINK));*/
		

		//world.addRObject(new Pinky(-400, 0));
		addRandomPinkys(world.getPlayer(0), 5, 3000);
	}
	
	void addHashToMiddle(int id){
		RelativeObject rObject = world.getRObject(id);
		if (rObject != null) {
			painter.Middle.add(rObject);
		}
		
	}
	
	public Image loadImage(String fileName){
		Image img = null;
		try {
			img = ImageIO.read(new File(fileName));
		} catch (IOException e) {
			System.out.println("Something went wrong while loading the image.");
		}
		return img;
	}
	
	@Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
    	String key = KeyEvent.getKeyText(e.getKeyCode());
    	//System.out.println(key);
    	
    	if(!(keySaver.contains(key))) {
    		keySaver.add(key);
    	}
    	//System.out.println(keySaver);
    }

    @Override
    public void keyReleased(KeyEvent e) {
    	String key = KeyEvent.getKeyText(e.getKeyCode());
    	//System.out.println(key);
    	
    	if(keySaver.contains(key)) {
    		keySaver.remove(key);
    	}
    	//System.out.println(keySaver);

        if (e.getKeyCode() == KeyEvent.VK_C) {
        	if(camera.viewType == 0) {
            	camera.viewType = 1;
            } else {
            	camera.viewType = 0;
            }
        }
    }

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		mouse.pressed = true;
		mouse.click = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		mouse.pressed = false;
	}
	
	void addRandomPinky(RelativeObject rObject, double spawnRadius){
		double x = rObject.x + Math.sin(rand.nextInt((int)(Math.PI*2*1000))/100)*spawnRadius;
		double y = rObject.y - Math.cos(rand.nextInt((int)(Math.PI*2)))*spawnRadius;
		world.addRObject(new Pinky(x, y));
	}
	
	void addRandomPinkys(RelativeObject rObject, int times, double spawnRadius) {
		for(int i=0; i<times; i++) {
			addRandomPinky(rObject, spawnRadius);
		}
	}
	
	Color randomColor() {
		float r = rand.nextFloat();
		float g = rand.nextFloat();
		float b = rand.nextFloat();
		return new Color(r, g, b);
	}

}
