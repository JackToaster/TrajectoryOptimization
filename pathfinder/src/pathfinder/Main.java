package pathfinder;

import javax.swing.SwingUtilities;

public class Main {

	public static void main(String[] args) {

		Waypoint start = new Waypoint(new Point(100,400),Math.PI/2,1);
		Waypoint end = new Waypoint(new Point(600,200),Math.PI,1);
		Path path = new Path(start,end, 10);
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Drawer(path); // Let the constructor do the job
			}
		});
	}

}
