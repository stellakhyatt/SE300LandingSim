package SE300ProjectCode;

import java.math.*;
import java.util.ArrayList;
public class PlanetaryBody {
	private double mass;
	private double radius;
	private String name;
	private double rotationRate;
	private double oblateness;
	private double atmDensity;
	
	public PlanetaryBody(String n, double m, double r, double rr, double o) {
		if(m<0) {
			m = 0;
		}
		if(r<0) {
			r=0;
		}
		mass = m;
		radius = r;
		name = n;
		rotationRate = rr;
		oblateness = o;
		atmDensity = 0;
	}
	
	public void setDensity(double height) {
		double T = 0;
		double p = 0;
		if(name.equalsIgnoreCase("Mars")) {
			p = 0.699*Math.exp(-0.00009*height);
			if(height > 7000) {
				T = -23.4 - 0.00222*height;
			}
			else {
				T = -31 - 0.000998*height;
			}
			atmDensity = p/(0.1921*(T+273.15));
		}
		else if(name.equalsIgnoreCase("Venus")) {
			double R = 0.19139*1000;
			p = -0.0035*Math.pow(height, 5)+ 1.3004*Math.pow(height, 4) - 195.23*Math.pow(height, 3) + (1.4908E4)*Math.pow(height, 2) - (5.822E5)*height + 9.3239E6;
			T = -(1.4953E-7)*Math.pow(height, 5)+(3.2362E-5)*Math.pow(height, 4) - 0.0018*Math.pow(height, 3) + 0.0255*Math.pow(height, 2) - 7.8467*height + 735.6017;
			if(p<0) {
				p = 0;
			}
			atmDensity = p/(R*T);
		}
		else if(name.equalsIgnoreCase("Moon") || name.equalsIgnoreCase("Pluto")) {
			atmDensity = 0; //atmosphere is negligible on both these bodies
		}
		else {
			double G = 6.67428E-11;
			double g = (G*mass)/Math.pow(radius,2);
			double B = (6.5/1000);
			double T_0 = 15+273.15;
			double power = g/(287*B);
			atmDensity = 1.225*Math.pow((1-(B/T_0)*height),power);
		}
	}
	public double getDensity() {
		return atmDensity;
	}
	public double getMass() {
		return mass;
	}
	public double getRadius() {
		return radius;
	}
	public double getRotationRate() {
		return rotationRate;
	}
	public double getOblateness() {
		return oblateness;
	}
	public String getName() {
		return name;
	}
	public double getGravity(double h){
		double G = 6.67428E-11;
		double g = (G*mass)/Math.pow(h+radius,2);
		return g;
	}

	//Test Main
	public static void main(String [] args) {
		SimulationParameters parameters = new SimulationParameters();
		if(!parameters.setPlanetParameters("se300_planet_data.csv")){
			System.out.println("Planet file not found!");
		}
		else{
			ArrayList<PlanetaryBody> planets = parameters.getPlanets();
			PlanetaryBody venus = planets.get(0); 
			venus.setDensity(98.5);
			System.out.println(venus.getDensity());
		}
	}
}
