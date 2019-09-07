import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class NsDooM {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new JLabel(new ImageIcon(NsDooM.class.getResource("/resources/NsDoomWallpaper1.3.png"))));
		frame.setSize(1024, 557);
		frame.setTitle("NsDooM");
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
        while(true) {
        	frame.setVisible(true);
    		
    		try {
    			TimeUnit.SECONDS.sleep(1);
    		} catch (InterruptedException e1) {
    			e1.printStackTrace();
    		}
    		frame.setVisible(false);
    		new Manager();
        }
	}
}
