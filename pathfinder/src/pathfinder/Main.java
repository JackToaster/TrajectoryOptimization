package pathfinder;

import javax.swing.SwingUtilities;

public class Main {

	public static void main(String[] args) {
		System.out.println("Starting path generation...");
		Waypoint start = new Waypoint(new Point(0.5, 0.5),3 * Math.PI / 2);
		Waypoint end = new Waypoint(new Point(5, 3),Math.PI);
		Path path = new Path(start,end, 50, 2);
		System.out.println("Path generation finished.");
		TrajectoryOptimizer traj = new TrajectoryOptimizer(path);
		Path newPath = traj.path;
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Drawer d = new Drawer(newPath); // Let the constructor do the job
			}
		});
	}

}
