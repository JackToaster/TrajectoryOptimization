package pathfinder;

import javax.swing.SwingUtilities;

public class Main {

	public static void main(String[] args) {
		System.out.println("Starting path generation...");
		Waypoint start = new Waypoint(new Point(0.5, 0.5),3 * Math.PI / 2);
		Waypoint end = new Waypoint(new Point(5, 3),Math.PI);
		Path path = new Path(start,end, 30, 2);
		System.out.println("Path generation finished.");
		TrajectoryOptimizer traj = new TrajectoryOptimizer(path);
		Path newPath = traj.path;
		Drawer d = new Drawer(newPath); // draw the path
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e){
			e.printStackTrace();
		}
		while(true){
			try {
				Thread.sleep((int)1000/60);
				traj.optimizeCost();
				newPath = traj.path;
				d.iterations += traj.ITERATIONS;
				d.cost = traj.calculateCost();
				d.redraw();
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
