package eaglezr.physics;

public class Space {
	private double width;
	private double depth;
	private double height;
	public Particle referenceParticle = new Particle(); //This is the base particle to which all others are based.

	public Space() {
		this.width = Double.MAX_VALUE;
		this.depth = Double.MAX_VALUE;
		this.height = Double.MAX_VALUE;
	}

	public Space(double x, double y) {
		this.width = x;
		this.height = y;
	}

	public Space(double x, double y, double z) {
		this.width = x;
		this.height = y;
		this.depth = z;
	}
	
	public double getWidth(){
		return this.width;
	}
	
	public double getHeight(){
		return this.height;
	}
	
	public double getDepth(){
		return this.depth;
	}
	
	/**
	 * Adds a new particle to the system
	 * @param newItem Particle to be added
	 * @param referenceItem The particle the new particle is subordinate to
	 */
	public void addObject(Particle newItem, Particle referenceItem){
		if(this.referenceParticle.equals(referenceItem)){
			referenceItem.subParticles.add(newItem);
		} 
	}
	
}
