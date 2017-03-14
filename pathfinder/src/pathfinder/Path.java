package pathfinder;

import java.util.ArrayList;

public class Path {
	ArrayList<Waypoint> waypoints;

	public Path(Waypoint start, Waypoint end, int numberOfPoints) {
		waypoints = new ArrayList<Waypoint>();
		genBezierPath(start, end, numberOfPoints, 0.8);
		alignWaypoints();
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
		for (double i = 1; i < numberOfPoints; i++) {
			double alpha = i / (double) numberOfPoints;
			Point p1 = Point.lerp(startPoint, gp1, alpha);
			Point p2 = Point.lerp(gp1, gp2, alpha);
			Point p3 = Point.lerp(gp2, endPoint, alpha);

			Point p5 = Point.lerp(p1, p2, alpha);
			Point p6 = Point.lerp(p2, p3, alpha);

			Point p = Point.lerp(p5, p6, alpha);

			waypoints.add(new Waypoint(p, 0, 1));
		}
		waypoints.add(end);
	}

	void alignWaypoints() {
		for (int i = 1; i < waypoints.size() - 1; i++) {
			waypoints.get(i).pointTowards(waypoints.get(i + 1).getPoint());
		}
	}
}
