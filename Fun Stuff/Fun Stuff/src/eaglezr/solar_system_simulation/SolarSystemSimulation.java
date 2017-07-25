package eaglezr.solar_system_simulation;

public class SolarSystemSimulation {

//	public SolarSystemSimulation() {
//		
//	}

	public static void main(String[] args) {
		Body sun = new Body((float)(1.989 * 10 * Math.getExponent(30)), 0, 0, 0, 0);
		double altitude = 151930000;
		altitude = altitude * 1000;
		Body earth = new Body((float)(5.972 * 10 * Math.getExponent(24)), altitude, 29300, 0, 0);
		
	}

}
