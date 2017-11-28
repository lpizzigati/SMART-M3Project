package main;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		//points = new ArrayList<LatLng>();
		new Aggregator("BUS32").run();
		new Bus("BUS32").run();	
		
	}

}
