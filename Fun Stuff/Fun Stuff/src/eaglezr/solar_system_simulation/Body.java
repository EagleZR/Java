package eaglezr.solar_system_simulation;

public class Body {
	
	protected float mass;
	protected double altitude;
	protected int velocityX;
	protected int velocityY;
	protected int velocityZ;
	
	public Body(){
		
	}

	public Body(float mass, double altitude, int velocityX, int velocityY, int velocityZ) {
		this.mass = mass;
		this.altitude = altitude;
		this.velocityX = velocityX;
		this.velocityY = velocityY;
		this.velocityZ = velocityZ;
	}

	public float getMass(){
		return this.mass;
	}
	
	public double getAltitude(){
		return this.altitude;
	}
	
	public int getVelocityX(){
		return this.velocityX;
	}
	
	public int getVelocityY(){
		return this.velocityY;
	}
	
	public int getVelocityZ(){
		return this.velocityZ;
	}
}
