
package SE300ProjectCode;

import java.util.*;
import java.io.*;
public class SimulationParameters {
	private ArrayList<PlanetaryBody> planets;
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

	public boolean setPlanetParameters(String filename){
		ArrayList<String> data = loadFromFile(filename);
		if (data == null){
			return false;
		}
		for(int i = 0; i<data.size(); i++){
			if(data.get(i) != null){
				String [] planet = data.get(i).split(",");
				PlanetaryBody p = new PlanetaryBody(planet[0],
								    Double.parseDouble(planet[1]),
								    Double.parseDouble(planet[2]),
								    Double.parseDouble(planet[4]),
								    Double.parseDouble(planet[3]));
				planets.add(p);
			}
		}
		return true;		
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
