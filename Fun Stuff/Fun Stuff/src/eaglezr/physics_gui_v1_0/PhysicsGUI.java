package eaglezr.physics_gui_v1_0;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class PhysicsGUI extends JFrame {
	
	public class Particle{
		private double x = 0;
		private double y = 0;
		
		private double velocityX = 0;
		private double velocityY = 0;
		
		public Particle(double setX, double setY){
			this.x = setX;
			this.y = setY;
		}
		
		public Particle(double setX, double setY, double setVelocityX, double setVelocityY){
			this.x = setX;
			this.y = setY;
			this.velocityX = setVelocityX;
			this.velocityY = setVelocityY;
		}
		
		public double[] getPosition(){
			double[] returnValue = {this.x, this.y};
			return returnValue;
		}
		
		public double[] getVelocity(){
			double[] returnValue = {this.velocityX, this.velocityY};
			return returnValue;
		}
		
		public void move(double timeInterval){
			double moveIntervalX = this.velocityX * timeInterval;
			double moveIntervalY = this.velocityY * timeInterval;
			
			this.x += moveIntervalX;
			this.y += moveIntervalY;
		}
	}

	public class Window{
		private int width = 0;
		private int height = 0;
		private boolean gravity = false;
		private ArrayList<Particle> particles = new ArrayList<Particle>();
		
		public Window(int setHeight, int setWidth){
			this.width = setWidth;
			this.height = setHeight;
		}
		
		public Window(int setHeight, int setWidth, boolean setGravity){
			this.width = setWidth;
			this.height = setHeight;
			this.gravity = setGravity;
		}
		
		public void addParticle(Particle newParticle){
			particles.add(newParticle);
		}
		
		public int getHeight(){
			return this.height;
		}
		
		public int getWidth(){
			return this.width;
		}
		
		public void move(double timeInterval){
			// move each particle
			for(int i = 0; i < this.particles.size(); i++){
				this.particles.get(i).move(timeInterval);
			}
			// check for collisions and adjust velocities
			// factor in gravity
		}
		
		
	}
	
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PhysicsGUI frame = new PhysicsGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PhysicsGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
