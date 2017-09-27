package eaglezr.solar_system_simulation;

public class Physics {

	public Physics() {
		// TODO Auto-generated constructor stub
	}
	
	public double gravityStrength(Body body1, Body body2){
		return 6.673 * Math.exp(-11) * body1.getMass() * body2.getMass() / body2.getAltitude();
	}

}
