package main;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		//points = new ArrayList<LatLng>();
		MapExample map = new MapExample();
		Aggregator aggregator = new Aggregator("BUS32", map);
		aggregator.run();
		Gui gui = new Gui(map);
		new Bus("BUS32").run();	
	}

}
