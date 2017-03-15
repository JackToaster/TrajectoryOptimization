package pathfinder;

public class Waypoint {
	public Point position;
	public double rotation;
	
	public double time;
	
	public double linVelocity;
	public double linAcceleration;
	public double linJerk;
	
	public double rotVelocity;
	public double rotAcceleration;
	public double rotJerk;
	
	public Waypoint(Point waypointPosition, double wpRotation, double speed) {
		position = waypointPosition;
		rotation = wpRotation;
		linVelocity = speed;
		time = 0;
		linVelocity = 0;
		linAcceleration = 0;
		linJerk = 0;
		rotVelocity = 0;
		rotAcceleration = 0;
		rotJerk = 0;
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
