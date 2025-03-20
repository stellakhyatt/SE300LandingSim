package SE300ProjectCode;
import java.math.*;
public class SpaceCraft {
	private double mass;
	private double frontalArea;
	private double drag_coeff;
	private String type;
	private double altitude;
	private double[] position = new double[3];
	private double[] velocity = new double[3];
	
	public SpaceCraft(String t, double m, double a) {
		if(!t.equalsIgnoreCase("Capsule")|| !t.equalsIgnoreCase("Lander")) {
			t = " ";
		}
		if(m<0) {
			m = 0;
		}
		if(a<0) {
			a = 0;
		}
		type = t;
		if(type.equalsIgnoreCase("Capsule")) {
			drag_coeff = 0.42;
		}
		else if(type.equalsIgnoreCase("Lander")) {
			drag_coeff = 0.83;//assuming height is double diameter
		}
		else {
			drag_coeff = 0;
		}
	}
	
	public void setType(String t) {
		type = t;
		if(type.equalsIgnoreCase("Capsule")) {
			drag_coeff = 0.42;
		}
		else if(type.equalsIgnoreCase("Lander")) {
			drag_coeff = 0.83;//assuming height is double diameter
		}
		else {
			drag_coeff = 0;
		}
	}
	
	public void setMass(double m) {
		if(m<0) {
			m = 0;
		}
		mass = m;
	}
	
	public void setArea(double a) {
		if(a<0) {
			a = 0;
		}
		frontalArea = a;
	}
	
	public void setPosition(double x, double y, double z) {
		
		position[0] = x;
		position[1] = y;
		position[2] = z;
		altitude = Math.sqrt((x*x)+(y*y)+(z*z));
	}
	
	public void setVelocity(double x, double y, double z) {
		velocity[0] = x;
		velocity[1] = y;
		velocity[2] = z;
	}
	
	public double getMass() {
		return mass;
	}
	
	public double getFrontalArea() {
		return frontalArea;
	}
	
	public String getType() {
		return type;
	}
	
	public double getAltitude() {
		return altitude;
	}
	
	public double[] getPosition() {
		return position;
	}
	
	public double[] getVelocity() {
		return velocity;
	}

}
