package main;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		//points = new ArrayList<LatLng>();
		Aggregator aggregator = new Aggregator("BUS32");
		aggregator.run();
		Gui gui = new Gui(aggregator.getMap());
		new Bus("BUS32").run();	
	}

}
