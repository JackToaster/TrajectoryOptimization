package pathfinder;

public class Waypoint {
	public Point position;
	public double rotation;
	
	public double time;
	
	public Derivatives derivatives;
	
	public Waypoint(Point waypointPosition, double wpRotation) {
		position = waypointPosition;
		rotation = wpRotation;
		time = 0;
		derivatives = new Derivatives();
	}

	public void setPosition(double xPosition, double yPosition) {
		position.x = xPosition;
		position.y = yPosition;
	}

	public void setRotation(double wpRotation) {
		rotation = wpRotation;
	}

	public void translateWaypoint(double xOffset, double yOffset) {
		position.add(new Point(xOffset, yOffset));
	}

	public double pointTowards(Point target) {
		double xOffset = target.x - position.x;
		double yOffset = target.y - position.y;
		if (xOffset == 0) {
			if (yOffset > 0) {
				rotation = Math.PI / 2.0;
			} else {
				rotation = 3.0 * Math.PI / 2.0;
			}
		} else {
			rotation = Math.atan(yOffset / xOffset);
			if (xOffset > 0) {
				rotation += Math.PI;
			}
		}
		return rotation;
	}
	
	public Point getPoint(){
		return position;
	}
}
