package pathfinder;

public class RobotSim {
	public static final double LIN_VELOCITY = 5;
	public static final double LIN_ACCELERATION = 8;
	public static final double LIN_JERK = 20;
	
	public static final double ROT_VELOCITY = 2;
	public static final double ROT_ACCELERATION = 4;
	public static final double ROT_JERK = 10;
	
	public Point position;
	public double rotation;
	
	public Derivatives derivs;
	public double linAccTarget;
	public double rotAccTarget;
	
	public RobotSim(Point pos, double rot){
		position = pos;
		rotation = rot;
		derivs = new Derivatives();
	}
	public void integratePosition(double time){
		calcJerk(time);
		derivs.linAcceleration += derivs.linJerk * time;
		derivs.rotAcceleration += derivs.rotJerk * time;
		limitAcceleration();
		
		derivs.linVelocity += derivs.linAcceleration * time;
		derivs.rotVelocity += derivs.rotAcceleration * time;
		
		limitVelocity();
		
		rotation += derivs.rotVelocity;
		
		Point offset = Point.PolarPoint(derivs.linVelocity * time, rotation);
		position.add(offset);
	}
	void calcJerk(double time){
		double dAcc = linAccTarget - derivs.linAcceleration;
		derivs.linJerk = dAcc / time;
		
		double dRotAcc = rotAccTarget - derivs.rotAcceleration;
		derivs.rotJerk = rotAccTarget;
		
		limitJerk();
	}
	void limitJerk(){
		if(derivs.linJerk > LIN_JERK) derivs.linJerk = LIN_JERK;
		else if(derivs.linJerk < -LIN_JERK) derivs.linJerk = -LIN_JERK;
		
		if(derivs.rotJerk > ROT_JERK) derivs.rotJerk = ROT_JERK;
		else if(derivs.rotJerk < -ROT_JERK) derivs.rotJerk = -ROT_JERK;
	}
	
	void limitAcceleration(){
		if(derivs.linAcceleration > LIN_ACCELERATION) derivs.linAcceleration = LIN_ACCELERATION;
		else if(derivs.linAcceleration < -LIN_ACCELERATION) derivs.linAcceleration = -LIN_ACCELERATION;
		
		if(derivs.rotAcceleration > ROT_ACCELERATION) derivs.rotAcceleration = ROT_ACCELERATION;
		else if(derivs.rotAcceleration < -ROT_ACCELERATION) derivs.rotAcceleration = -ROT_ACCELERATION;
	}
	
	void limitVelocity(){
		if(derivs.linVelocity > LIN_VELOCITY) derivs.linVelocity = LIN_VELOCITY;
		else if(derivs.linVelocity < -LIN_VELOCITY) derivs.linVelocity = -LIN_VELOCITY;
		
		if(derivs.rotVelocity > ROT_VELOCITY) derivs.rotVelocity = ROT_VELOCITY;
		else if(derivs.rotVelocity < -ROT_VELOCITY) derivs.rotVelocity = -ROT_VELOCITY;
	}
	
	public void setMotorPowers(double left, double right){
		double turn = right - left;
		double speed = right + left;
		linAccTarget = speed - derivs.linVelocity;
		rotAccTarget = turn - derivs.rotVelocity;
	}
}
