package main;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		//points = new ArrayList<LatLng>();
		MapExample map = new MapExample();
		Gui gui = new Gui(map);	
		
		Aggregator aggregator = new Aggregator("BUS32", map);
		aggregator.run();
		
		Aggregator aggregator2 = new Aggregator("BUS33", map);
		aggregator2.run();
				
		new Bus("BUS33", "gpx/path2.gpx").run();
		new Bus("BUS32", "gpx/32.gpx").run();

		
	}

}
