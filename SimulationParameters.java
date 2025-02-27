
package SE300ProjectCode;

import java.util.*;
import java.io.*;
public class SimulationParameters {
	private String [] planetParameters;
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

	public boolean setPlanetParameters(String filename, int index){
		ArrayList<String> data = loadFromFile(filename);
		if (data == null){
			return false;
		}
		for(int i = 1; i<data.size(); i++){
			if(data.get(i) != null){
				String [] planet = data.get(i).split(",");
				if(i == index){
					planetParameters = planet;
				}
			}
		}
		return true;		
	}

	public String [] getPlanetParameters(){
		return planetParameters;
	}

	public String [] getSpacecraftParameters(){
		return spacecraftParameters;
	}

}
