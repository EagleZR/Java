package eaglezr.solar_system_simulation;

public class Planet extends Body{
	
	private int diameter;
	private int height;

	public Planet(float mass, double altitude, int velocityX, int velocityY, int velocityZ, int diameter, int height) {
		super.mass = mass;
		super.altitude = altitude;
		super.velocityX = velocityX;
		super.velocityY = velocityY;
		super.velocityZ = velocityZ;
		this.diameter = diameter;
		this.height = height;
	}
	
//	public int getMass(){
//		return super.mass;
//	}
//	
//	public int getAltitude(){
//		return super.altitude;
//	}
//	
//	public int getVelocityX(){
//		return super.velocityX;
//	}
//	
//	public int getVelocityY(){
//		return super.velocityY;
//	}
//	
//	public int getVelocityZ(){
//		return super.velocityZ;
//	}
	
	public int getDiameter(){
		return this.diameter;
	}
	
	public int getHeight(){
		return this.height;
	}

}
