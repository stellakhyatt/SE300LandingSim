package SE300ProjectCode;

import java.math.*;
public class PlanetaryBody {
	private double mass;
	private double radius;
	private String name;
	private double rotationRate;
	private double oblateness;
	private double atmDensity;
	
	public PlanetaryBody(double m, double r, String n, double rr, double o) {
		if(m>0) {
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
			double R = 0.19139;
			if(height==100) {
				p = 0.0000266*101325;
				T = 273.15-112;
			}
			else if(height==90) {
				p = 0.0003736*101325;
				T = 273.15-104;
			}
			else if(height==80) {
				p = 0.00476*101325;
				T = 273.15-76;
			}
			else if(height==70) {
				p = 0.0369*101325;
				T = 273.15-43;
			}
			else if(height==65) {
				p = 0.09765*101325;
				T = 273.15-30;
			}
			else if(height==60) {
				p = 0.2357*101325;
				T = 273.15-10;
			}
			else if(height==55) {
				p = 0.5314*101325;
				T = 273.15+27;
			}
			else if(height==50) {
				p = 1.066*101325;
				T = 273.15+75;
			}
			else if(height==45) {
				p = 1.979*101325;
				T = 273.15+110;
			}
			else if(height==40) {
				p = 3.501*101325;
				T = 273.15+143;
			}
			else if(height==35) {
				p = 5.917*101325;
				T = 273.15+180;
			}
			else if(height==30) {
				p = 9.851*101325;
				T = 273.15+222;
			}
			else if(height==25) {
				p = 14.93*101325;
				T = 273.15+264;
			}
			else if(height==20) {
				p = 22.52*101325;
				T = 273.15+306;
			}
			else if(height==15) {
				p = 33.04*101325;
				T = 273.15+348;
			}
			else if(height==10) {
				p = 47.39*101325;
				T = 273.15+385;
			}
			else if(height==5) {
				p = 66.65*101325;
				T = 273.15+424;
			}
			else if(height==0) {
				p =92.1*101325;
				T =273.15+462;
			}
			else {
				p = (92.1*101325)+(((0.0000266-92.1)*101325)/100)*height;
				T = (273.15+462)+((-112-462)/100)*height;
			}
			atmDensity = p/(R*T);
		}
		else if(name.equalsIgnoreCase("Moon") || name.equalsIgnoreCase("Pluto")) {
			atmDensity = 0; //atmosphere is negligible on both these bodies
		}
		else {
			double G = 6.67428*Math.pow(10,-11);
			double g = (G*mass)/Math.pow(radius,2);
			double B = (6.5/1000);
			double T_0 = 15+273.15;
			double power = g/(287*B);
			atmDensity = 1.225*Math.pow((1-(B/T_0)*height),power);
		}
	}

}
