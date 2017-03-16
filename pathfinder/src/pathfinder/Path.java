package pathfinder;

import java.util.ArrayList;

public class Path {
	ArrayList<Waypoint> waypoints;

	public Path(Waypoint start, Waypoint end, int numberOfPoints, double velocity) {
		waypoints = new ArrayList<Waypoint>();
		genBezierPath(start, end, numberOfPoints, 0.5);
		setSpeeds(velocity);
		alignWaypoints();
		calcDerivatives();
	}
	
	void setSpeeds(double velocity){
		for(int i = 0; i < waypoints.size(); i++){
			waypoints.get(i).derivatives.linVelocity = (-0.5 * Math.cos(((double) i / (double)waypoints.size()) * 2.0 * Math.PI) + 0.5) * velocity;//wow big equation makes an S-curve
		}
	}
	
	void genBezierPath(Waypoint start, Waypoint end, int numberOfPoints, double tightness) {
		Point startPoint = start.getPoint();
		Point endPoint = end.getPoint();
		double distance = startPoint.distance(endPoint);
		double gpLength = distance / 2 * tightness;

		Point startOffset = Point.PolarPoint(-gpLength, start.rotation);
		Point endOffset =  Point.PolarPoint(gpLength,end.rotation);

		Point gp1 = startPoint.sum(startOffset);
		Point gp2 = endPoint.sum(endOffset);

		waypoints.add(start);
		for (int i = 1; i < numberOfPoints; i++) {
			double alpha = (double) i / (double) numberOfPoints;
			Point p1 = Point.lerp(startPoint, gp1, alpha);
			Point p2 = Point.lerp(gp1, gp2, alpha);
			Point p3 = Point.lerp(gp2, endPoint, alpha);

			Point p5 = Point.lerp(p1, p2, alpha);
			Point p6 = Point.lerp(p2, p3, alpha);

			Point p = Point.lerp(p5, p6, alpha);
			Waypoint wp = new Waypoint(p, 0);
			waypoints.add(wp);
		}
		waypoints.add(end);
	}

	void alignWaypoints() {
		for (int i = 1; i < waypoints.size() - 1; i++) {
			waypoints.get(i).pointTowards(waypoints.get(i + 1).getPoint());
		}
	}
	
	void calcDerivatives(){
		for(int i = 0; i < waypoints.size() - 1; i++){//First derivative (velocity) and time and second derivative (acceleration)
			Waypoint currentWP = waypoints.get(i);
			Waypoint nextWP = waypoints.get(i+1);//get current WP and next WP
			
			double dist = currentWP.getPoint().distance(nextWP.getPoint());//get distance between WP's
			double turn = nextWP.rotation - currentWP.rotation;
			
			currentWP.time = dist / currentWP.derivatives.linVelocity;//calculate time to next WP for deriving
			
			currentWP.derivatives.calcRotVelocity(turn, currentWP.time);//calculate rotational velocity
			
			currentWP.derivatives.calcAcceleration(nextWP.derivatives, currentWP.time);//calculate accelerations
			
			waypoints.set(i, currentWP);
		}
		for(int i = 0; i < waypoints.size() - 1; i++){//3rd derivative (jerk)
			Waypoint currentWP = waypoints.get(i);
			Waypoint nextWP = waypoints.get(i+1);//get current WP and next WP
			
			currentWP.derivatives.calcJerk(nextWP.derivatives, currentWP.time);
			waypoints.set(i, currentWP);
		}
	}
}
