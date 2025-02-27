
package SE300ProjectCode;

import java.util.*;
import java.io.*;
public class SimulationParameters {
	private ArrayList <String> planetParameters;
	private ArrayList <String> spacecraftParameters;
	
	public ArrayList<String,Double> loadFromFile(String filename) {
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

}
