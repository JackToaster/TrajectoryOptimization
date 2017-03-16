package pathfinder;

public class TrajectoryOptimizer {
	public static final double LIN_VELOCITY = 5;
	public static final double LIN_ACCELERATION = 3;
	public static final double LIN_JERK = 8;
	
	public static final double ROT_VELOCITY = 2;
	public static final double ROT_ACCELERATION = 4;
	public static final double ROT_JERK = 5;
	
	public static final double TIME_COST = 1;
	public static final double DIFFICULTY_COST = 20;
	public static final double MAX_VALUE_PERCENT = 0.6;
	
	public static final int ITERATIONS = 1000;
	public static final double ALPHA = 0.005;
	
	public Path path;
	
	public TrajectoryOptimizer(Path p){
		path = p;
		System.out.println("Start Cost: " + calculateCost());
		optimizeCost();
		System.out.println("End Cost:   " + calculateCost());
	}
	
	void optimizeCost(){
		for(int i = 0; i < ITERATIONS; i++){//loop through and optimize
			double startCost = calculateCost();
			for(int j = 1; j < path.waypoints.size() - 1; j++){//go through each point
				path.waypoints.get(j).derivatives.linVelocity += ALPHA;//try adjusting the point both ways
				path.calcDerivatives();
				if(calculateCost() >= startCost){
					path.waypoints.get(j).derivatives.linVelocity -= 2 * ALPHA;
					path.calcDerivatives();
					if(calculateCost() >= startCost){//if both ways are bad, put it back
						path.waypoints.get(j).derivatives.linVelocity += ALPHA;
					}
				}
			}
		}
	}
	
	double calculateCost(){
		double cost = 0;
		
		double difficulty = 0;
		double time = 0;
		for(Waypoint wp : path.waypoints){
			Derivatives derivs = wp.derivatives;
			if(Math.abs(derivs.linVelocity) > LIN_VELOCITY * MAX_VALUE_PERCENT) difficulty += Math.abs(derivs.linVelocity) - LIN_VELOCITY * MAX_VALUE_PERCENT;
			if(Math.abs(derivs.linAcceleration) > LIN_ACCELERATION * MAX_VALUE_PERCENT) difficulty += Math.abs(derivs.linAcceleration) - LIN_ACCELERATION * MAX_VALUE_PERCENT;
			if(Math.abs(derivs.linJerk) > LIN_JERK * MAX_VALUE_PERCENT) difficulty += Math.abs(derivs.linJerk) - LIN_JERK * MAX_VALUE_PERCENT;
			if(Math.abs(derivs.rotVelocity) > ROT_VELOCITY * MAX_VALUE_PERCENT) difficulty += Math.abs(derivs.rotVelocity) - ROT_VELOCITY * MAX_VALUE_PERCENT;
			if(Math.abs(derivs.rotAcceleration) > ROT_VELOCITY * MAX_VALUE_PERCENT) difficulty += Math.abs(derivs.rotAcceleration) - ROT_VELOCITY * MAX_VALUE_PERCENT;
			if(Math.abs(derivs.rotJerk) > ROT_VELOCITY * MAX_VALUE_PERCENT) difficulty += Math.abs(derivs.rotJerk) - ROT_VELOCITY * MAX_VALUE_PERCENT;
			
			if(Double.isFinite(wp.time)) time += Math.abs(wp.time);
		}
		
		cost += difficulty / path.waypoints.size() * DIFFICULTY_COST;
		cost += time * TIME_COST;
		System.out.println("Time:      " + time);
		System.out.println("Difficulty:" + difficulty / path.waypoints.size());
		
		return cost;
	}
}
