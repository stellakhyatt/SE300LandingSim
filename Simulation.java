package SE300ProjectCode;

import java.util.*;
public class Simulation {
	// Attributes
	PlanetaryBody planetaryBody = new PlanetaryBody();
	SpaceCraft spacecraft = new SpaceCraft();
	private double currentTime;

	// main
	public static void main(String[] args) {
		Simulation simulation = new Simulationi();
		simulation.runSimulation();
	}

	// Run simulation
	public void runSimulation() {
	        double timeStep = 1.0; // Time step in seconds
		loadParameters();
				    
	        while (!spacecraft.hasLanded()) {

			// use loaded parameters to calculate until landing
			
			updateState(timeStep);
			currentTime += timeStep;	
	        }
	        displayResults();
	}

	// Load simulation parameters
   	public void loadParameters() {
		SimulationParameters simulationParameters = new SimulationParameters();
		if(!simulationParameters.setPlanetParameters("se300_planet_data.csv")){
			System.out.println("Planet File Failed to Load!"):
		}
		// else if (!simulationParameters.setSpacecraftParameters(spacecraft file)){
			//System.out.println("Spacecraft File Not Found");
		//}	
		else{
	        ArrayList<PlanetaryBody> planetParams = simulationParameters.getPlanetParameters();
	        String[] spacecraftParams = simulationParameters.getSpacecraftParameters();
		//Setup Screen
		
	        //Getting planet parameters from user
		for (int i = 0; i<planetParams.size(); i++){
			String planet = "Name: " + planetParams.get(i).getName() + " Mass: " + planetParams.get(i).getMass() + " kg Radius: " + planetParams.get(i).getRadius() + " km Gravitational Acceleration: " + planetParams.get(i).getGravity(0) + " m/s^2";
			System.out.println(planet);
		}
		Scanner scan = new Scanner(System.in);
		boolean isValidPlanet = false;
		while(!isValidPlanet) {
			System.out.println("Please select planet from above list by entering name:");
			String userPlanet = scan.nextLine();
			for(int i = 0; i<planets.size(); i++) {
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
    	}
	
	// Update the simulation state
    	private void updateState(double timeStep) {
	        // Calculate gravitational force
	        double gravity = planetaryBody.getGravity(spacecraft.getPosition().getMagnitude());
	        spacecraft.applyForce(new Vector3D(0, -gravity * spacecraft.getTotalMass(), 0));
	        spacecraft.updateState(timeStep);
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
