package SE300ProjectCode;

import java.util.Scanner;
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
	        String[] planetParams = simulationParameters.getPlanetParameters();
	        String[] spacecraftParams = simulationParameters.getSpacecraftParameters();
	        
	        // Need checking: location of the parameters (row/column)
	        planetaryBody = new PlanetaryBody(
	            Double.parseDouble(planetParams[0]), // name
	            Double.parseDouble(planetParams[1]), // mass
	            Double.parseDouble(planetParams[2]), // radius
	            Double.parseDouble(planetParams[3]), // oblateness
	            Double.parseDouble(planetParams[4])); // Rotation rate(rad/s)

		// 
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
