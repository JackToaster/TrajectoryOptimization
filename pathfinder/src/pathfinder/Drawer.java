package pathfinder;

import java.awt.*; // Using AWT's Graphics and Color
import java.awt.event.*; // Using AWT event classes and listener interfaces
import javax.swing.*; // Using Swing's components and containers

public class Drawer extends JFrame {

	public static final int CANVAS_WIDTH = 1080;
	public static final int CANVAS_HEIGHT = 720;
	
	public static final float scale = 200;
	
	public static final int ROBOT_SIZE = 30;
	public static final int VELOCITY_MULT = 20;
	public static final int ACCELERATION_MULT = 20;
	public static final int JERK_MULT = 5;
	
	
	public Path path;
	
	public int iterations;
	public double cost;
	
	private DrawCanvas canvas;

	public Drawer(Path p) {
		iterations = 0;
		cost = 0;
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

	public void redraw(){
		canvas.repaint();
	}
	public class DrawCanvas extends JPanel {
		// Override paintComponent to perform your own painting
		public void paintComponent(Graphics graphics) {
			super.paintComponent(graphics); // paint parent's background
			setBackground(Color.WHITE); // set background color for this JPanel
			Graphics2D g = (Graphics2D) graphics;
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
			g.setFont(new Font("SansSerif", 0,35));
			
			drawPath(g, path);
			
			g.setColor(Color.BLACK);
			g.drawString("Iteration: " + iterations, 100, 50);
			g.drawString("Cost function output: " + cost, 350, 50);
		}
		private void drawPath(Graphics2D g, Path p){
			float hue = 0;
			if(p.waypoints.size() > 15){
				for(int i = 0; i < 15; i++){
					g.setColor(Color.getHSBColor(hue, 0.4f, 0.8f));
					hue += 0.1;
					hue = hue % 1;
					drawWP(g, p.waypoints.get(Math.round(i * (p.waypoints.size()-1) / 15)));
				}
			drawWP(g, p.waypoints.get(p.waypoints.size() - 1));
			}else{
				for(Waypoint wp : p.waypoints){
					g.setColor(Color.getHSBColor(hue, 0.4f, 0.8f));
					hue += 0.1;
					hue = hue % 1;
					drawWP(g, wp);
				}
			}
		}
		private void drawWP(Graphics2D g, Waypoint wp) {
			Graphics2D gg = (Graphics2D) g.create();
			gg.translate((int) (wp.position.x * scale), (int) (wp.position.y * scale));
			gg.rotate(wp.rotation);
			gg.fillRect(-ROBOT_SIZE / 2, -ROBOT_SIZE / 2, ROBOT_SIZE, ROBOT_SIZE);
			gg.setColor(Color.BLACK);
			gg.drawRect(-ROBOT_SIZE / 2, -ROBOT_SIZE / 2, ROBOT_SIZE, ROBOT_SIZE);
			gg.drawLine(0, 5, (int) (wp.derivatives.linVelocity * -VELOCITY_MULT), 5);
			if(wp.derivatives.rotVelocity < 0){
				gg.drawLine(5, ROBOT_SIZE/2, 5, (int)(ROBOT_SIZE/2 + wp.derivatives.rotVelocity * -VELOCITY_MULT));
			}else{
				gg.drawLine(5, -ROBOT_SIZE/2, 5, (int)-(ROBOT_SIZE/2 + wp.derivatives.rotVelocity * VELOCITY_MULT));
			}
			gg.setColor(Color.BLUE);
			gg.drawLine(0, 0, (int) (wp.derivatives.linAcceleration * -ACCELERATION_MULT), 0);
			if(wp.derivatives.rotAcceleration < 0){
				gg.drawLine(0, ROBOT_SIZE/2, 0, (int)(ROBOT_SIZE/2 + wp.derivatives.rotAcceleration * -ACCELERATION_MULT));
			}else{
				gg.drawLine(0, -ROBOT_SIZE/2, 0, (int)-(ROBOT_SIZE/2 + wp.derivatives.rotAcceleration * ACCELERATION_MULT));
			}
			gg.setColor(Color.RED);
			gg.drawLine(0, -5, (int) (wp.derivatives.linJerk * -JERK_MULT), -5);
			if(wp.derivatives.rotJerk < 0){
				gg.drawLine(-5, ROBOT_SIZE/2, -5, (int)(ROBOT_SIZE/2 + wp.derivatives.rotJerk * -JERK_MULT));
			}else{
				gg.drawLine(-5, -ROBOT_SIZE/2, -5, (int)-(ROBOT_SIZE/2 + wp.derivatives.rotJerk * JERK_MULT));
			}
			gg.dispose();
		}
	}
}
