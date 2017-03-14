package pathfinder;

import java.awt.*; // Using AWT's Graphics and Color
import java.awt.event.*; // Using AWT event classes and listener interfaces
import javax.swing.*; // Using Swing's components and containers

public class Drawer extends JFrame {

	public static final int CANVAS_WIDTH = 640;
	public static final int CANVAS_HEIGHT = 480;

	public static final int ROBOT_SIZE = 30;
	public static final int POWER_MULT = 20;
	
	public Path path;
	
	private DrawCanvas canvas;

	public Drawer(Path p) {
		path = p;
		
		canvas = new DrawCanvas(); // Construct the drawing canvas
		canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

		// Set the Drawing JPanel as the JFrame's content-pane
		Container cp = getContentPane();
		cp.add(canvas);
		// or "setContentPane(canvas);"

		setDefaultCloseOperation(EXIT_ON_CLOSE); // Handle the CLOSE button
		pack(); // Either pack() the components; or setSize()
		setTitle("PathFinder"); // "super" JFrame sets the title
		setVisible(true); // "super" JFrame show
	}

	public class DrawCanvas extends JPanel {
		// Override paintComponent to perform your own painting
		public void paintComponent(Graphics graphics) {
			super.paintComponent(graphics); // paint parent's background
			setBackground(Color.WHITE); // set background color for this JPanel
			Graphics2D g = (Graphics2D) graphics;
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			g.setColor(Color.LIGHT_GRAY);
			
			drawPath(g, path);
		}
		
		private void drawPath(Graphics2D g, Path p){
			float hue = 0;
			for(Waypoint wp : p.waypoints){
				g.setColor(Color.getHSBColor(hue, 1.0f, 0.8f));
				hue += 0.1;
				hue = hue % 1;
				drawWP(g, wp);
			}
		}
		private void drawWP(Graphics2D g, Waypoint wp) {
			Graphics2D gg = (Graphics2D) g.create();
			gg.translate((int) wp.position.x, (int) wp.position.y);
			gg.rotate(wp.rotation);
			gg.fillRect(-ROBOT_SIZE / 2, -ROBOT_SIZE / 2, ROBOT_SIZE, ROBOT_SIZE);
			gg.setColor(Color.BLACK);
			gg.drawRect(-ROBOT_SIZE / 2, -ROBOT_SIZE / 2, ROBOT_SIZE, ROBOT_SIZE);
			gg.drawLine(0, 0, (int) wp.power * -POWER_MULT, 0);
			gg.dispose();
		}
	}
}
