package eaglezr.physics;

public class Velocity {
	
	double vx;
	double vy;
	double vz;

	public Velocity() {
		this.vx = 0;
		this.vy = 0;
		this.vz = 0;
	}
	
	public Velocity(double xVelocity, double yVelocity){
		this.vx = xVelocity;
		this.vy = yVelocity;
	}
	
	public Velocity(double xVelocity, double yVelocity, double zVelocity){
		this.vx = xVelocity;
		this.vy = yVelocity;
		this.vz = zVelocity;
	}
	
	public double getX(){
		return this.vx;
	}
	
	public double getY(){
		return this.vy;
	}
	
	public double getZ(){
		return this.vz;
	}

	public void setX(double x){
		this.vx = x;
	}
	
	public void setY(double y){
		this.vy = y;
	}
	
	public void setZ(double z){
		this.vz = z;
	}
	
	public void setXY(double xVelocity, double yVelocity){
		this.vx = xVelocity;
		this.vy = yVelocity;
	}
	
	public void setXYZ(double xVelocity, double yVelocity, double zVelocity){
		this.vx = xVelocity;
		this.vy = yVelocity;
		this.vz = zVelocity;
	}

}
