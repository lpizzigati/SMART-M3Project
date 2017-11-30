package main;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import sofia_kp.KPICore;
import sofia_kp.SIBResponse;
import utils.OntologyReference;
import utils.SIBConfiguration;


public class Aggregator extends Thread {
	private String busName;
	private KPICore kp;
	private SIBResponse resp;
	private MapExample map;

		
	public Aggregator(String busName, MapExample map) {
		this.busName = busName;
		this.map = map;
		kp = new KPICore(SIBConfiguration.getInstance().getHost(),
					SIBConfiguration.getInstance().getPort(),
					SIBConfiguration.getInstance().getSmartSpaceName());
		}

	@Override
	public void run() {
		
		if(!kp.join().isConfirmed())
			System.err.println ("Error joining the SIB");
		else
			System.out.println ("SIB joined correctly");
		String busNameWithNS = OntologyReference.NS + busName;
		String sparqlQuery =
				"select ?ld ?la ?lo "
					+ "where { "
					+ "<" + busNameWithNS + "> <" + OntologyReference.HAS_LOCATION_DATA + "> ?ld ."
					+ "?ld <" + OntologyReference.HAS_LAT + "> ?la ."
					+ "?ld <" + OntologyReference.HAS_LON + "> ?lo"
				+ " }";			
		
		Handler2 MyHandler = new Handler2(map);  
		try {
			map.waitForMap();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		map.waitReady();
		

		resp = kp.subscribeSPARQL(sparqlQuery, MyHandler );
		//Gui gui = new Gui(map);
	}
	
	public MapExample getMap() {
		return map;
	}
}




//select ?ld ?la ?lo  where { <http://project/IoES1718#BUS32> <http://project/IoES1718#hasLocatioData> ?ld . ?ld <http://project/IoES1718#hasLat> ?la .	?ld  <http://project/IoES1718#hasLon> ?lo }
