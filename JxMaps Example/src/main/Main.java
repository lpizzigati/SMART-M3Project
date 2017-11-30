package main;

import gui.SimConfigurationFrame;
import simulationConfiguration.SimulationConfig;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		//points = new ArrayList<LatLng>();
		//MapExample map = new MapExample();
		//Gui gui = new Gui(map);	
		
		SimConfigurationFrame configurator = new SimConfigurationFrame();
		configurator.setVisible(true);
		
		//SimulationConfig.getInstance().setSimulationVelocity(2.25);
		
		//Aggregator aggregator = new Aggregator("BUS32", map);
		//aggregator.start();
		
		//Aggregator aggregator2 = new Aggregator("BUS33", map);
		//aggregator2.start();
				
		//new Bus("BUS32", "gpx/32.gpx").start();
		//new Bus("BUS33", "gpx/path2.gpx").start();
		

		
	}

}
