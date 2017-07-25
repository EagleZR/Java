package eaglezr.physics;

public class Coordinates {

	//BigNumber is in meters
	// 3.0 x 10 ^ 0 = 3 meters
	private BigNumber xPos;
	private BigNumber yPos;
	private BigNumber zPos;
	
	public Coordinates() {
		this.xPos = 0;
		this.yPos = 0;
		this.zPos = 0;
	}
	
	public Coordinates(BigNumber x, BigNumber y){
		this.xPos = x;
		this.yPos = y;
	}
	
	public Coordinates(BigNumber x, BigNumber y, BigNumber z){
		this.xPos = x;
		this.yPos = y;
		this.zPos = z;
	}
	
	public BigNumber getX(){
		return this.xPos;
	}
	
	public BigNumber getY(){
		return this.yPos;
	}
	
	public BigNumber getZ(){
		return this.zPos;
	}

	public void setX(BigNumber x){
		this.xPos = x;
	}
	
	public void setY(BigNumber y){
		this.yPos = y;
	}
	
	public void setZ(BigNumber z){
		this.zPos = z;
	}
	
	public void setXY(BigNumber x, BigNumber y){
		this.xPos = x;
		this.yPos = y;
	}
	
	public void setXYZ(BigNumber x, BigNumber y, BigNumber z){
		this.xPos = x;
		this.yPos = y;
		this.zPos = z;
	}
	
	public BigNumber getDistance(Coordinates location){
		// sqrt((x1-x2)^2 + (y1-y2)^2 + (z1-z2)^2)
	}
}
