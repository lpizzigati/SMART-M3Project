package main;

import aggregators.Aggregator;
import aggregators.ExampleSPARQLQueryAndResults;
import sensors.Sensor;
import utils.OntologyReference;

public class Main {

	public static void main(String[] args) {
		
		new Person("Mario").start();
		new Sensor("Mario", OntologyReference.WIFI_SOURCE, "5", 5000).start();
		new Sensor("Mario", OntologyReference.GPS_SOURCE, "10", 5000).start();
		new Sensor("Mario", OntologyReference.NFC_SOURCE, "1", 30000).start();
		new Sensor("Mario", OntologyReference.NFC_SOURCE, "1", 30000).start();
		
//		new ExampleSPARQLQueryAndResults().start();
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		new Aggregator("Mario", 10 * 1000).start();
		
	}

}
