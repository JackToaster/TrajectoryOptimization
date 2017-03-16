package pathfinder;

public class Derivatives {
	public double linVelocity;
	public double linAcceleration;
	public double linJerk;

	public double rotVelocity;
	public double rotAcceleration;
	public double rotJerk;

	public Derivatives() {
		linVelocity = 0;
		linAcceleration = 0;
		linJerk = 0;

		rotVelocity = 0;
		rotAcceleration = 0;
		rotJerk = 0;
	}

	public Derivatives(double velocity, double rotationalVelocity) {
		linVelocity = velocity;
		rotVelocity = rotationalVelocity;
	}

	public Derivatives(double linearVelocity, double linearAcceleration, double linearJerk, double rotationalVelocity,
			double rotationalAcceleration, double rotationalJerk) {
		linVelocity = linearVelocity;
		linAcceleration = linearAcceleration;
		linJerk = linearJerk;

		rotVelocity = rotationalVelocity;
		rotAcceleration = rotationalAcceleration;
		rotJerk = rotationalJerk;
	}

	public void calcRotVelocity(double theta, double time) {// set velocity based on displacement/time
		// velocity = displacement/time
		rotVelocity = theta / time;
	}

	public void calcAcceleration(Derivatives d2, double time) {
		double dV = d2.linVelocity - linVelocity; // delta velocity
		linAcceleration = dV / time; // acceleration = dV/dT
		double dRotV = d2.rotVelocity - rotVelocity; // delta time
		rotAcceleration = dRotV / time; // acceleration = dV/dT
	}

	public void calcJerk(Derivatives d2, double time) {
		double dAcc = d2.linAcceleration - linAcceleration; // delta acceleration
		linJerk = dAcc / time;
		double dRotAcc = d2.rotAcceleration - rotAcceleration;
		rotJerk = dRotAcc / time;
	}
}
