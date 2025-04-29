
package SE300ProjectCode;

import java.util.*;
import java.io.*;

public class SimulationParameters {
	private ArrayList<PlanetaryBody> planets = new ArrayList<PlanetaryBody>();
	private String [] spacecraftParameters;
	
	private ArrayList<String> loadFromFile(String filename) {
		ArrayList<String> data = new ArrayList<String>();
		try
			{
				Scanner inFile = new Scanner(new File(filename));
				while(inFile.hasNext())
					{
						String line = inFile.nextLine();
						data.add(line);
					}
			}
		catch(Exception e)
			{
				return null;
			}
		return data;
	}
	
	public void saveToFile (String filename) {
		
	}

	public int setPlanetParameters(String filename) {
		ArrayList<String> data = loadFromFile(filename);
		if (data == null) {
			SimulationParameters simParameters = new SimulationParameters();
			simParameters.createDefaultPlanetsFile(filename);
			return 0;		
		}
		
		// I think we need to keep header to make the file easy to edit
		if (data.size() < 2) {
			return 1; // Missing header or no planet entries
		}
	
		// Check header
		String expectedHeader = "name,mass_kg,radius_km,rotation_rate_radps,surface_atm_density_kgpm3,surface_gravity_mps2,spacecraft_type";
		if (!data.get(0).trim().equalsIgnoreCase(expectedHeader)) {
			return 3; // Header incorrect (invalid data)
		}
	
		for (int i = 1; i < data.size(); i++) {
			if (data.get(i) != null && !data.get(i).trim().isEmpty()) {
				String[] planet = data.get(i).split(",", -1); // Keep empty fields
				if (planet.length != 7) {
					return 1; // Wrong number of fields (missing data)
				}
	
				// Check for missing fields
				for (String field : planet) {
					if (field.trim().isEmpty()) {
						return 1; // Missing data
					}
				}

				// Check for each values
				double mass;
				try {
					mass = Double.parseDouble(planet[1]);
					if (mass <= 0 || mass > 1e35) {
						return 3;
					}
				} catch (NumberFormatException e) {
					return 3;
				}
	
				double radius;
				try {
					radius = Double.parseDouble(planet[2]);
					if (radius <= 0 || radius > 100000) {
						return 3;
					}
				} catch (NumberFormatException e) {
					return 3;
				}
	
				double rotationRate;
				try {
					rotationRate = Double.parseDouble(planet[3]);
					if (rotationRate < -1 || rotationRate > 1) {
						return 3;
					}
				} catch (NumberFormatException e) {
					return 3;
				}
	
				double atmDensity;
				try {
					atmDensity = Double.parseDouble(planet[4]);
					if (atmDensity < 0 || atmDensity > 1000) {
						return 3;
					}
				} catch (NumberFormatException e) {
					return 3;
				}

				PlanetaryBody p;
                            p = new PlanetaryBody(planet[0],
						mass,                      
						radius,              
						rotationRate,
						atmDensity,
						Double.parseDouble(planet[5]));
				planets.add(p);
			}
		}
	
		return 2; // Success
	}

	public void createDefaultPlanetsFile(String filename) {
		try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
			writer.println("name,mass_kg,radius_km,rotation_rate_radps,surface_atm_density_kgpm3,surface_gravity_mps2,spacecraft_type");
			writer.println("venus,4.8673e24,6051.8,-2.9923e-7,65,8.87,lander");
			writer.println("earth,5.9722e24,6378.1,7.2921e-5,1.217,9.806,capsule");
			writer.println("moon,7.346e22,1738.1,2.6617e-6,0,1.62,lander");
			writer.println("mars,6.4169e23,3396.2,7.0882e-5,0.020,3.73,lander");
			writer.println("pluto,1.303e22,1188,-1.1386e-5,0,0.62,lander");
		} catch (IOException e) {
			System.out.println("Error creating default planets.csv: " + e.getMessage());
		}
	}

	/*
	 * 	public int setPlanetParameters(String filename){
		ArrayList<String> data = loadFromFile(filename);
		if (data == null){
			return 0;
		}
		for(int i = 0; i<data.size(); i++){
			if(data.get(i) != null){
				String [] planet = data.get(i).split(",");
				if(planet.length != 5){
					return 1;
				}
				PlanetaryBody p = new PlanetaryBody(planet[0],
								    Double.parseDouble(planet[1]),
								    Double.parseDouble(planet[2]),
								    Double.parseDouble(planet[4]),
								    Double.parseDouble(planet[3]));
				planets.add(p);
			}
		}
		return 2;		
	}
	 */
	public int setSpacecraftParameters(String filename) {
		ArrayList<String> data = loadFromFile(filename);
		if (data == null) {
			return 0; // File missing
		}
	
		if (data.size() < 11) {
			return 1; // Not enough lines, bad format
		}
	
		// Check first header
		if (!data.get(0).trim().equalsIgnoreCase("variable,value")) {
			return 1;
		}
	
		// Check parameter labels
		String[] expectedLabels = {
			"planet",
			"spacecraft_mass_kg",
			"spacecraft_frontal_area_m2",
			"initial_inclination_deg",
			"initial_ascending_deg",
			"initial_eccentricity",
			"initial_momentum_kgkm2s",
			"initial_anomaly_deg",
			"initial_argument_perigee_deg"
		};
	
		for (int i = 0; i < expectedLabels.length; i++) {
			String[] parts = data.get(i + 1).split(",", -1); // Keep empty values
			if (parts.length != 2 || !parts[0].trim().equalsIgnoreCase(expectedLabels[i])) {
				return 1;
			}
			if (parts[1].trim().isEmpty()) {
				return 3; 
			}
		}
	
		// Check second header
		if (!data.get(10).trim().equalsIgnoreCase("maneuver_num,condition_variable,condition_value,thrust_N,duration_s")) {
			return 1;
		}
	
		// Check at least one maneuver
		for (int i = 11; i < data.size(); i++) {
			String[] parts = data.get(i).split(",", -1);
			if (parts.length != 5) {
				return 1;
			}
			for (String part : parts) {
				if (part.trim().isEmpty()) {
					return 3; // Missing data in maneuver
				}
			}
		}
	
		/*
		0	File missing (loadFromFile() returned null)
		1	File format error (bad headers, bad structure)
		2	File OK
		3	Missing data value
		*/

		return 2; //basic structure is correct
	}

	public ArrayList<PlanetaryBody> getPlanets(){
		return planets;
	}

	public String [] getSpacecraftParameters(){
		return spacecraftParameters;
	}
	
	public static void main(String[] args) {
		SimulationParameters parameters = new SimulationParameters();
		parameters.setPlanetParameters("se300_planet_data.csv");
		ArrayList<PlanetaryBody> planets = parameters.getPlanets();
		System.out.println(planets.get(0).getName());
	}

}
