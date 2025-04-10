package SE300ProjectCode;

import java.util.*;
public class Simulation {
	// Attributes
	PlanetaryBody planetaryBody;
	SpaceCraft spacecraft;
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
		int fileCheck = simulationParameters.setPlanetParameters("se300_planet_data.csv");
		if(fileCheck == 0){
			System.out.println("Couldn't find planet file!");
			return false;
		}
		else if(fileCheck == 1){
			System.out.println("Couldn't read planet file, improper format!");
			return false;
		}
		// else if (!simulationParameters.setSpacecraftParameters(spacecraft file)){
		//System.out.println("Spacecraft File Not Found");
		//}	
		else{
			ArrayList<PlanetaryBody> planetParams = simulationParameters.getPlanets();
			String[] spacecraftParams = simulationParameters.getSpacecraftParameters();
			//Setup Screen
			Scanner scan = new Scanner(System.in);
			PlanetSelection(planetParams, scan);
			spacecraft = new SpaceCraft(planetaryBody.getSpacecraftType(),0,0);
			boolean doneChanging = false;
			while(!doneChanging) {
				System.out.println("Please select what you would like to change or do");
				System.out.println("b: Change Planetary Body from " + planetaryBody.getName());
				System.out.println("   Mass: " + planetaryBody.getMass() + " kg");
				System.out.println("   Radius: " + planetaryBody.getRadius() + " km");
				System.out.println("   Surface graviatational acceleration:" + planetaryBody.getGravity(0) + " m/s^2");
				System.out.println("   Spacecraft type:" + spacecraft.getType());
				System.out.println("m: Change spacecraft mass from " + spacecraft.getMass() + " kg");
				System.out.println("a: Change spacecraft frontal area from " + spacecraft.getFrontalArea() + " m^2") ;
				System.out.println("p: Change spacecraft initial position in geocentric equatorial frame from [" + spacecraft.getPosition()[0] + "," + spacecraft.getPosition()[1] + "," + spacecraft.getPosition()[2]+ "] km");
				System.out.println("   Altitude: " + (spacecraft.getAltitude() - planetaryBody.getRadius()) +" km");
				System.out.println("v: Change spacecraft initial velocity in geocentric equatorial frame from [" + spacecraft.getVelocity()[0] + "," + spacecraft.getVelocity()[1] + "," + spacecraft.getVelocity()[2]+ "] km/s");
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
				else if(userChoice.equalsIgnoreCase("m")) {
					double mass = 0;
					while(!validInput) {
						try{
							Scanner sc = new Scanner(System.in);
							System.out.println("Enter a spacecraft mass,in kg, must be between 0 and 10000");
							String mass_str = sc.nextLine();
							mass = Double.parseDouble(mass_str);
						}
						catch(Exception e){
							System.out.println("Must enter a number!");
						}
						if(0 < mass && mass <= 10000) {
							validInput = true;
							spacecraft.setMass(mass);
						}
						else {
							System.out.println("ERROR! Invalid mass value entered!");
						}
					}
				}
				else if(userChoice.equalsIgnoreCase("a")) {
					double area = 0;
					while(!validInput) {
						try{
							Scanner sc = new Scanner(System.in);
							System.out.println("Enter a spacecraft frontal area,in m^2, must be between 0 and 100");
							String area_str = sc.nextLine();
							area = Double.parseDouble(area_str);
						}
						catch(Exception e){
							System.out.println("Must enter a number!");
						}
						if(0 < area && area <= 100) {
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
						boolean validChoice = false;
						double x_pos = spacecraft.getPosition()[0];
						double y_pos = spacecraft.getPosition()[1];
						double z_pos = spacecraft.getPosition()[2];
						while(!validChoice) {
							System.out.println("Enter X, Y, or Z to change X, Y, or Z position, or enter D to return to setup page:");
							String choice = scan.nextLine();
							if(choice.equalsIgnoreCase("X")) {
								try{
									Scanner sc = new Scanner(System.in);
									validChoice = true;
									System.out.println("Enter x-value of spacecraft initial position, in km, in geocentric equatorial frame (must be greater than -100000 and less than 100000)");
									String x_pos_str = sc.nextLine();
									x_pos = Double.parseDouble(x_pos_str);
								}
								catch(Exception e){
									System.out.println("Must enter a number!");
									x_pos = spacecraft.getPosition()[0];
								}
							}
							else if(choice.equalsIgnoreCase("Y")) {
								try{
									Scanner sc = new Scanner(System.in);
									validChoice = true;
									System.out.println("Enter y-value of spacecraft initial position, in km, in geocentric equatorial frame (must be greater than -100000 and less than 100000)");
									String y_pos_str = sc.nextLine();
									y_pos = Double.parseDouble(y_pos_str);
								}
								catch(Exception e){
									System.out.println("Must enter a number!");
									y_pos = spacecraft.getPosition()[1];
								}
							}
							else if(choice.equalsIgnoreCase("Z")) {
								try{
									Scanner sc = new Scanner(System.in);
									validChoice = true;
									System.out.println("Enter z-value of spacecraft initial position, in km, in geocentric equatorial frame (must be greater than -100000 and less than 100000)");
									String z_pos_str = sc.nextLine();
									z_pos = Double.parseDouble(z_pos_str);
								}
								catch(Exception e){
									System.out.println("Must enter a number!");
									z_pos = spacecraft.getPosition()[2];
								}
							}
							else if(choice.equalsIgnoreCase("D")) {
								validChoice = true;
								validInput = true;
							}
							else {
								System.out.println("ERROR! Invalid choice entered! Please enter X,Y,Z, or D!");
							}
						}
						if(-100000 <= x_pos && x_pos <= 100000 && -100000 <= y_pos && y_pos <= 100000 && -100000 <= z_pos && z_pos <= 100000) {
							spacecraft.setPosition(x_pos,y_pos,z_pos);
						}
						else {
							System.out.println("ERROR! The position value entered is invalid!");
						}
					}
				}
				else if(userChoice.equalsIgnoreCase("v")) {
					while(!validInput) {
						double speed = Math.sqrt((spacecraft.getVelocity()[0] * spacecraft.getVelocity()[0])+(spacecraft.getVelocity()[1] * spacecraft.getVelocity()[1])+(spacecraft.getVelocity()[2] * spacecraft.getVelocity()[2]));
						System.out.println("Current speed: " + speed + " km/s");
						boolean validChoice = false;
						double x_vel = spacecraft.getVelocity()[0];
						double y_vel = spacecraft.getVelocity()[1];
						double z_vel = spacecraft.getVelocity()[2];
						while(!validChoice) {
							System.out.println("Enter X, Y, or Z to change X, Y, or Z velocity, or enter D to return to setup page:");
							String choice = scan.nextLine();
							if(choice.equalsIgnoreCase("X")) {
								try{
									Scanner sc = new Scanner(System.in);
									validChoice = true;
									System.out.println("Enter x-value of spacecraft initial velocity, in km/s, in geocentric equatorial frame (must be greater than -100 and less than 100)");
									String x_vel_str = sc.nextLine();
									x_vel = Double.parseDouble(x_vel_str);
								}
								catch(Exception e){
									System.out.println("Please enter a number!");
									x_vel = spacecraft.getVelocity()[0];
								}
							}
							else if(choice.equalsIgnoreCase("Y")) {
								try{
									Scanner sc = new Scanner(System.in);
									validChoice = true;
									System.out.println("Enter y-value of spacecraft initial velocity, in km/s, in geocentric equatorial frame (must be greater than -100 and less than 100)");
									String y_vel_str = sc.nextLine();
									y_vel = Double.parseDouble(y_vel_str);
								}
								catch(Exception e){
									System.out.println("Please enter a number!");
									y_vel = spacecraft.getVelocity()[1];
								}
							}
							else if(choice.equalsIgnoreCase("Z")) {
								try{
									Scanner sc = new Scanner(System.in);
									validChoice = true;
									System.out.println("Enter z-value of spacecraft initial velocity, in km/s, in geocentric equatorial frame (must be greater than -100 and less than 100)");
									String z_vel_str = sc.nextLine();
									z_vel = Double.parseDouble(z_vel_str);
								}
								catch(Exception e){
									System.out.println("Please enter a number!");
									z_vel = spacecraft.getVelocity()[2];
								}
							}
							else if(choice.equalsIgnoreCase("D")) {
								validChoice = true;
								validInput = true;
							}
							else {
								System.out.println("ERROR! Invalid choice entered! Please enter X,Y,Z, or D!");
							}
						}
						if(-100 <= x_vel && x_vel <= 100 && -100 <= y_vel && y_vel <= 100 && -100 <= z_vel && z_vel <= 100) {
							spacecraft.setVelocity(x_vel,y_vel,z_vel);
						}
						else {
							System.out.println("ERROR! The velocity value entered is invalid!");
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
}

