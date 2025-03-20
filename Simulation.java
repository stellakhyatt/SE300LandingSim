package SE300ProjectCode;

import java.util.*;
public class Simulation {
	// Attributes
	PlanetaryBody planetaryBody = new PlanetaryBody(" ",0,0,0,0);
	SpaceCraft spacecraft = new SpaceCraft(" ",0,0);
	private double currentTime;

	// main
	public static void main(String[] args) {
		Simulation simulation = new Simulation();
		simulation.runSimulation();
	}

	// Run simulation
	public void runSimulation() {
		double timeStep = 1.0; // Time step in seconds
		boolean simRuns = loadParameters();
		if(simRuns) {
			//while (!spacecraft.hasLanded()) {

			// use loaded parameters to calculate until landing

			updateState(timeStep);
			currentTime += timeStep;	
			displayResults();
			//}
		}
	}

	// Load simulation parameters
	public boolean loadParameters() {
		SimulationParameters simulationParameters = new SimulationParameters();
		if(!simulationParameters.setPlanetParameters("se300_planet_data.csv")){
			System.out.println("Planet File Failed to Load!");
			return false;
		}
		// else if (!simulationParameters.setSpacecraftParameters(spacecraft file)){
		//System.out.println("Spacecraft File Not Found");
		//}	
		else{
			ArrayList<PlanetaryBody> planetParams = simulationParameters.getPlanets();
			String[] spacecraftParams = simulationParameters.getSpacecraftParameters();
			//Setup Screen
			spacecraft = new SpaceCraft(" ",0,0);//Test spacecraft
			Scanner scan = new Scanner(System.in);
			PlanetSelection(planetParams, scan);
			boolean doneChanging = false;
			while(!doneChanging) {
				System.out.println("Please select what you would like to change or do");
				System.out.println("b: Change Planetary Body from " + planetaryBody.getName());
				System.out.println("s: Change spacecraft type from " + spacecraft.getType());
				System.out.println("m: Change spacecraft mass from " + spacecraft.getMass() + " kg");
				System.out.println("a: Change spacecraft frontal area from " + spacecraft.getFrontalArea() + " m^2") ;
				System.out.println("p: Change spacecraft initial position from [" + spacecraft.getPosition()[0] + "," + spacecraft.getPosition()[1] + "," + spacecraft.getPosition()[2]+ "] km");
				System.out.println("v: Change spacecraft initial velocity from [" + spacecraft.getVelocity()[0] + "," + spacecraft.getVelocity()[1] + "," + spacecraft.getVelocity()[2]+ "] km/s");
				System.out.println("r: Run Simulation");
				System.out.println("q: Quit Program");
				String userChoice = scan.nextLine();
				boolean validInput = false;
				if( userChoice.equalsIgnoreCase("r")) {
					doneChanging = true;
				}
				else if(userChoice.equalsIgnoreCase("b")) {
					PlanetSelection(planetParams, scan);
				}
				else if(userChoice.equalsIgnoreCase("s")) {
					while(!validInput) {
						System.out.println("Enter a spacecraft type, valid types are Lander and Capsule");
						String type = scan.nextLine();
						if(type.equalsIgnoreCase("Capsule")||type.equalsIgnoreCase("Lander")) {
							validInput = true;
							spacecraft.setType(type);
						}
						else {
							System.out.println("ERROR! Invalid spacecraft type entered!");
						}
					}
				}
				else if(userChoice.equalsIgnoreCase("m")) {
					while(!validInput) {
						System.out.println("Enter a spacecraft mass,in kg, must be between 0 and 10000");
						double mass = scan.nextDouble();
						if(0 < mass && mass < 10000) {
							validInput = true;
							spacecraft.setMass(mass);
						}
						else {
							System.out.println("ERROR! Invalid mass value entered!");
						}
					}
				}
				else if(userChoice.equalsIgnoreCase("a")) {
					while(!validInput) {
						System.out.println("Enter a spacecraft frontal area,in m^2, must be between 0 and 100");
						double area = scan.nextDouble();
						if(0 < area && area < 100) {
							validInput = true;
							spacecraft.setArea(area);
						}
						else {
							System.out.println("ERROR! Invalid area value entered!");
						}
					}
				}
				else if(userChoice.equalsIgnoreCase("p")) {
					while(!validInput) {
						System.out.println("Current altitude: " + (spacecraft.getAltitude()-planetaryBody.getRadius()) + " km");
						System.out.println("Enter x, y, and z positions, respectively, in km");
						double x_pos = scan.nextDouble();
						double y_pos = scan.nextDouble();
						double z_pos = scan.nextDouble();
						if(-100000 < x_pos && x_pos < 100000 && -100000 < y_pos && y_pos < 100000 && -100000 < z_pos && z_pos < 100000) {
							validInput = true;
							spacecraft.setPosition(x_pos,y_pos,z_pos);
						}
						else {
							System.out.println("ERROR! One or more position values entered is invalid!");
						}
					}
				}
				else if(userChoice.equalsIgnoreCase("v")) {
					while(!validInput) {
						double speed = Math.sqrt((spacecraft.getVelocity()[0] * spacecraft.getVelocity()[0])+(spacecraft.getVelocity()[1] * spacecraft.getVelocity()[1])+(spacecraft.getVelocity()[2] * spacecraft.getVelocity()[2]));
						System.out.println("Current speed: " + speed + " km/s");
						System.out.println("Enter x, y, and z velocities, respectively, in km/s");
						double x_vel = scan.nextDouble();
						double y_vel = scan.nextDouble();
						double z_vel = scan.nextDouble();
						if(-100 < x_vel && x_vel < 100 && -100 < y_vel && y_vel < 100 && -100 < z_vel && z_vel < 100) {
							validInput = true;
							spacecraft.setVelocity(x_vel,y_vel,z_vel);
						}
						else {
							System.out.println("ERROR! One or more velocity values entered is invalid!");
						}
					}
				}
				else if(userChoice.equalsIgnoreCase("q")) {
					return doneChanging;
				}
				else {
					System.out.println("ERROR! Invalid input entered!");
				}
			}
			System.out.println("Simulation runs...");//marker for ensuring continues to sim after setup screen
			return doneChanging;
		}
	}

	public void PlanetSelection(ArrayList<PlanetaryBody> planetParams, Scanner scan){
		for (int i = 0; i<planetParams.size(); i++){
			String planet = "Name: " + planetParams.get(i).getName() 
					+ " Mass: " + planetParams.get(i).getMass() 
					+ " kg Radius: " + planetParams.get(i).getRadius() 
					+ " km Gravitational Acceleration: " 
					+ planetParams.get(i).getGravity(0) + " m/s^2";
			System.out.println(planet);
		}
		boolean isValidPlanet = false;
		while(!isValidPlanet) {
			System.out.println("Please select planet from above list by entering name:");
			String userPlanet = scan.nextLine();
			for(int i = 0; i<planetParams.size(); i++) {
				if(userPlanet.equalsIgnoreCase(planetParams.get(i).getName())) {
					planetaryBody = planetParams.get(i);
					isValidPlanet = true;
				}

			}
			if(!isValidPlanet) {
				System.out.println("Error! Invalid planet or no planet entered!");
			}
		}
		System.out.println("You selected the planet: " + planetaryBody.getName());//As of now this line is for testing purposes (ensuring the correct planet is selected)
	}


	// Update the simulation state
	private void updateState(double timeStep) {
		// Calculate gravitational force
		//double gravity = planetaryBody.getGravity(spacecraft.getPosition().getMagnitude());
		//spacecraft.applyForce(new Vector3D(0, -gravity * spacecraft.getTotalMass(), 0));
		//spacecraft.updateState(timeStep);
	}


	// Save the current state of the simulation
	public void saveState() {
		System.out.println("Saving simulation state at time: " + currentTime);
		// Logic to save spacecraft state and simulation parameters
	}

	// Display the simulation results
	public void displayResults() {
		System.out.println("Simulation completed.");
		System.out.println("Final spacecraft position: " + spacecraft.getPosition());
		System.out.println("Final spacecraft velocity: " + spacecraft.getVelocity());
		// Other results?? Trajectories?
	}
