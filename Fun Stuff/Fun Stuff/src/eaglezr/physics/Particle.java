package eaglezr.physics;

import java.util.ArrayList;

public class Particle implements Runnable{
	
	private Coordinates coordinates;
	private double mass = 0; //TODO turn into BigNumber
	private Velocity velocity;
	//private charge; TODO
	public ArrayList<Particle> subParticles = new ArrayList<Particle>();
	Size size;

	public Particle(){
		
	}
	
	public Particle(Coordinates location, Velocity velocity) {
		this.coordinates = location;
		this.velocity = velocity;
	}
	
	public double getGravity(Particle source){
		double gravityConstant = 1; //TODO find exact number
		gravityConstant = 6.674; 
		return gravityConstant * this.mass * source.mass / this.coordinates.getDistance(source.coordinates); //TODO add formula
	}
	
	public void move(){
		// TODO
	}
	
	public Size getSize(){
		return size;
	}
	
	public boolean detectCollision(Particle secondObject){
		if (this.coordinates == secondObject.coordinates){
			return true;
		}
		return false;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
//	public static long exponent(long base, int exponent){
//		long answer;
//		
//		if(exponent > 0){ // Positive Exponent
//			while(exponent > 0){
//				base = base * base;
//				exponent --;
//			}
//			answer = base;
//		} else if (exponent == 0){ // 0 Exponent
//			answer = 1;
//		} else { // Negative Exponent
//			answer = 1 / exponent(base, exponent *-1);
//		}
//		
//		return answer;
//	}

}
