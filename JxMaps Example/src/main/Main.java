package main;


public class Main {
	

	public static void main(String[] args) throws InterruptedException {
		//points = new ArrayList<LatLng>();
		new Aggregator("BUS32").run();
		Thread.sleep(1000);
		new Bus("BUS32").run();	
		
	}

}
