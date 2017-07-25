package eaglezr.physics;

import java.util.ArrayList;

/**
 * Creates an ArrayList of Coordinates of every point contained within the
 * object. This is a quick and dirty method upon which I will improve.
 * 
 * @author Mark
 *
 */
public class Size {
	
	//The centerpoint is 1 at 1/2 of each frame
	Coordinates centerPoint;
	double xFrame;
	double yFrame;
	double zFrame;

	ArrayList<Coordinates> size = new ArrayList<Coordinates>();

	public Size() {
		this.xFrame = 0;
		this.yFrame = 0;
		this.zFrame = 0;
	}
	
	public Size(Coordinates center){
		this.xFrame = 0;
		this.yFrame = 0;
		this.zFrame = 0;
		this.centerPoint = center;
	}
	
	public Size(double length, double height, Coordinates center){
		this.xFrame = length;
		this.yFrame = height;
		this.zFrame = 0;
		this.centerPoint = center;
	}
	
	public Size(double length, double height, double depth, Coordinates center){
		this.xFrame = length;
		this.yFrame = height;
		this.zFrame = depth;
		this.centerPoint = center;
	}
	
	public double getCollisionCheckDistance(){
		double returnValue = this.xFrame;
		if(returnValue < this.yFrame){
			returnValue = this.yFrame;
		}
		if (returnValue < this.zFrame){
			returnValue = this.zFrame;
		}
		return returnValue/2;
	}

}
