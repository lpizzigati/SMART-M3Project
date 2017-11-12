package main;

import sensors.Sensor;
import utils.OntologyReference;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		new Person("Mario").start();
		new Sensor("Mario", OntologyReference.WIFI_SOURCE, "5", 5000).start();
		new Sensor("Mario", OntologyReference.GPS_SOURCE, "10", 5000).start();
		new Sensor("Mario", OntologyReference.NFC_SOURCE, "1", 30000).start();
		
	}

}
