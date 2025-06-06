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
		int fileCheck_planet = simulationParameters.setPlanetParameters("se300_planet_data.csv");
		int fileCheck_spacecraft = simulationParameters.setPlanetParameters("se300_parameters.csv");
		if(fileCheck_planet == 0){
			System.out.println("Error: File planets.csv is missing. New File was created");
			return false;
		}
		else if(fileCheck_planet == 1){
			System.out.println("Error: File planets.csv has missing data. Delete it and run the program again to generate a new file.");
			return false;
		} 
		else if(fileCheck_planet == 3) {
			System.out.println("Error: File planets.csv has invalid data. Delete it and run the program again to generate a new file.");
			return false;
		}
		/* 
		else if(fileCheck_spacecraft == 0){
			System.out.println("Couldn't find spacecraft file!");
			return false;
		}
		else if(fileCheck_spacecraft == 1){
			System.out.println("Error: File parameters.csv has invalid data. Delete it and run the program again to generate a new file.");
			return false;
		}
		*/
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
				System.out.println("Enter a number to change a parameter: ");
				System.out.println("[ 1] Planet: " + planetaryBody.getName());
				System.out.println("     Mass: " + planetaryBody.getMass() + " kg");
				System.out.println("     Radius: " + planetaryBody.getRadius() + " km");
				System.out.println("     Surface graviatational acceleration: " + planetaryBody.getGravity(0) + " m/s^2");
				System.out.println("     Spacecraft type: " + spacecraft.getType());
				System.out.println("[ 2] Spacecraft mass: " + spacecraft.getMass() + " kg");
				System.out.println("[ 3] Spacecraft frontal area: " + spacecraft.getFrontalArea() + " m^2");
				System.out.println("[ 4] Initial argument of perigee: " + spacecraft.getArgumentOfPerigee() + " deg");
				System.out.println("[ 5] Initial eccentricity: " + spacecraft.getEccentricity());
				System.out.println("[ 6] Initial angular momentum: " + spacecraft.getAngularMomentum() + " kg km^2/s^2");
				System.out.println("[ 7] Initial inclination: " + spacecraft.getInclination() + " deg");
				System.out.println("[ 8] Initial right ascension: " + spacecraft.getRightAscension() + " deg");
				System.out.println("[ 9] Initial true anomaly: " + spacecraft.getTrueAnomaly() + " deg");		System.out.println("[10] Add maneuver");
				System.out.println("[11] Remove maneuver");
				System.out.println("[12] Run simulation");
				System.out.println("[ q] Quit program");

				/*
				 * user input 4-9 → Use case 7-12
				 */

				String userChoice = scan.nextLine();
				boolean validInput = false;
				if( userChoice.equalsIgnoreCase("12")) {
					doneChanging = true;
				}
				else if(userChoice.equalsIgnoreCase("1")) {
					PlanetSelection(planetParams, scan);
					spacecraft.setType(planetaryBody.getSpacecraftType());
				}
				else if(userChoice.equalsIgnoreCase("2")) {
					double mass = 0;
					while(!validInput) {
						try{
							Scanner sc = new Scanner(System.in);
							System.out.print("Enter a spacecraft mass,in kg, must be between 0 and 10000: ");
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
				else if(userChoice.equalsIgnoreCase("3")) {
					double area = 0;
					while(!validInput) {
						try{
							Scanner sc = new Scanner(System.in);
							System.out.print("Enter a spacecraft frontal area,in m^2, must be between 0 and 100: ");
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
				else if(userChoice.equalsIgnoreCase("4")) {
					double perigee = 0;
					System.out.println("Initial argument of perigee: " + spacecraft.getArgumentOfPerigee() + " deg");
				
					while (!validInput) {
						try {
							Scanner sc = new Scanner(System.in);
							System.out.print("Enter a new value for initial argument of perigee in deg (0 < ω < 360):");
							String perig_str = sc.nextLine();
				
							if (perig_str.trim().isEmpty()) {
								System.out.println("Invalid: You did not enter anything."); // Req 7.2.1
								continue;
							}
				
							perigee = Double.parseDouble(perig_str);
				
							if (perigee < 0 || perigee > 360) {
								System.out.println("Invalid: Angle must be at least 0 or at most 360."); // Req 7.2.3
								continue;
							}
				
							// Check: Valid input (Req 7.3)
							spacecraft.setArgumentOfPerigee(perigee);
							validInput = true;
				
						} catch (NumberFormatException e) {
							System.out.println("Invalid: You did not enter a number."); // Req 7.2.2
						}
					}
				}
				else if (userChoice.equalsIgnoreCase("5")) {
					System.out.println("Current eccentricity: " + spacecraft.getEccentricity());
					while (!validInput) {
						try {
							Scanner sc = new Scanner(System.in);
							System.out.print("Enter a new value for initial eccentricity (0 < e < 1): ");
							String input = sc.nextLine();
							if (input.trim().isEmpty()) {
								System.out.println("Invalid: You did not enter anything.");
								continue;
							}
							double ecc = Double.parseDouble(input);
							if (ecc <= 0) {
								System.out.println("Invalid: Eccentricity must be a positive number.");
							} else if (ecc >= 1) {
								System.out.println("Invalid: Eccentricity must be less than 1.");
							} else if (planetaryBody.getRadius() * 1.01 >= spacecraft.getSemiMajorAxis() * (1 - ecc)) {
								System.out.println("Invalid: That eccentricity is too large, the orbit intersects with the planet.");
								System.out.print("Press Enter to return to the setup menu...");
								sc.nextLine();
								break;
							} else {
								spacecraft.setEccentricity(ecc);
								validInput = true;
							}
						} catch (NumberFormatException e) {
							System.out.println("Invalid: You did not enter a number.");
						}
					}
				}

				else if (userChoice.equalsIgnoreCase("6")) {
					System.out.println("Initial angular momentum: " + spacecraft.getAngularMomentum() + " kg km^2/s");
					while (!validInput) {
						try {
							Scanner sc = new Scanner(System.in);
							System.out.print("Enter a new value for initial angular momentum in kg km^2/ (0 < p < 100000): ");
							String input = sc.nextLine();
							if (input.trim().isEmpty()) {
								System.out.println("Invalid: You did not enter anything.");
								continue;
							}
							double momentum = Double.parseDouble(input);
							if (momentum <= 0) {
								System.out.println("Invalid: Angular momentum must be a positive number.");
							} else if (momentum > 100000) {
								System.out.println("Invalid: That number is too large!");
							} else {
								spacecraft.setAngularMomentum(momentum);
								validInput = true;
							}
						} catch (NumberFormatException e) {
							System.out.println("Invalid: You did not enter a number.");
						}
					}
				}

				else if (userChoice.equalsIgnoreCase("7")) {
					System.out.println("Initial inclination: " + spacecraft.getInclination() + " deg");
					while (!validInput) {
						try {
							Scanner sc = new Scanner(System.in);
							System.out.print("Enter a new value for initial inclination in deg (0 < θ < 360): ");
							String input = sc.nextLine();
							if (input.trim().isEmpty()) {
								System.out.println("Invalid: You did not enter anything.");
								continue;
							}
							double inc = Double.parseDouble(input);
							if (inc < 0 || inc > 360) {
								System.out.println("Invalid: Inclination must be at least 0 and at most 360.");
							} else {
								spacecraft.setInclination(inc);
								validInput = true;
							}
						} catch (NumberFormatException e) {
							System.out.println("Invalid: You did not enter a number.");
						}
					}
				}

				else if (userChoice.equalsIgnoreCase("8")) {
					System.out.println("Initial right ascension: " + spacecraft.getRightAscension() + " deg");
					while (!validInput) {
						try {
							Scanner sc = new Scanner(System.in);
							System.out.print("Enter a new value for right ascension in deg (0 < α < 360): ");
							String input = sc.nextLine();
							if (input.trim().isEmpty()) {
								System.out.println("Invalid: You did not enter anything.");
								continue;
							}
							double ra = Double.parseDouble(input);
							if (ra < 0 || ra > 360) {
								System.out.println("Invalid: Right ascension must be at least 0 and at most 360.");
							} else {
								spacecraft.setRightAscension(ra);
								validInput = true;
							}
						} catch (NumberFormatException e) {
							System.out.println("Invalid: You did not enter a number.");
						}
					}
				}

				else if (userChoice.equalsIgnoreCase("9")) {
					System.out.println("Initial true anomaly: " + spacecraft.getTrueAnomaly() + " deg");
					while (!validInput) {
						try {
							Scanner sc = new Scanner(System.in);
							System.out.print("Enter a new value for initial true anomaly in deg (0 < ν < 360): ");
							String input = sc.nextLine();
							if (input.trim().isEmpty()) {
								System.out.println("Invalid: You did not enter anything.");
								continue;
							}
							double anomaly = Double.parseDouble(input);
							if (anomaly < 0 || anomaly > 360) {
								System.out.println("Invalid: True anomaly must be at least 0 and at most 360.");
							} else {
								spacecraft.setTrueAnomaly(anomaly);
								validInput = true;
							}
						} catch (NumberFormatException e) {
							System.out.println("Invalid: You did not enter a number.");
						}
					}
				}
				/* 
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
									System.out.println("Enter x-value of spacecraft initial position, in km, in geocentric equatorial frame (-100000 ≦ x ≦ 100000)");
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
									System.out.println("Enter y-value of spacecraft initial position, in km, in geocentric equatorial frame (-100000 ≦ y ≦ 100000)");
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
									System.out.println("Enter z-value of spacecraft initial position, in km, in geocentric equatorial frame (-100000 ≦ z ≦ 100000)");
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
									System.out.println("Enter x-value of spacecraft initial velocity, in km/s, in geocentric equatorial frame (-100 ≦ x ≦ 100)");
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
									System.out.println("Enter y-value of spacecraft initial velocity, in km/s, in geocentric equatorial frame (-100 ≦ y ≦ 100)");
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
									System.out.println("Enter z-value of spacecraft initial velocity, in km/s, in geocentric equatorial frame (-100 ≦ z ≦ 100)");
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
				*/
				else if(userChoice.equalsIgnoreCase("q") || userChoice.equalsIgnoreCase("quit") || userChoice.equalsIgnoreCase("exit")) {
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
					+ " km Surface Gravitational Acceleration: " 
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

