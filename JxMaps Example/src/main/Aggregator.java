package main;

import java.util.Vector;

import sofia_kp.KPICore;
import sofia_kp.SIBResponse;
import utils.OntologyReference;
import utils.SIBConfiguration;


public class Aggregator extends Thread {

	private String busName;
	private KPICore kp;
	private SIBResponse resp;
		
	public Aggregator(String busName) {
		this.busName = busName;
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
					+ "<" + busNameWithNS + "> <" + OntologyReference.HAS_LOCATION_DATA + "> ?ld . "
					+ "?ld <" + OntologyReference.HAS_LAT + "> ?la ."
					+ "?ld <" + OntologyReference.HAS_LON + "> ?lo"
				+ " }";			
		//execute query
		System.out.println(sparqlQuery);
		resp = kp.querySPARQL(sparqlQuery);
		//System.out.println(resp.sparqlquery_results.print_as_string());
		Vector<Vector<String[]>> data = resp.sparqlquery_results.getResults();
		for(Vector<String[]> riga : data) {
			System.out.println("Location data:" + riga.get(0)[2] + "has lat: " + riga.get(1)[2]+ " has lon: " + riga.get(2)[2]); 
			
		}
	}
}




//select ?ld ?la ?lo  where { <http://project/IoES1718#BUS32> <http://project/IoES1718#hasLocatioData> ?ld . ?ld <http://project/IoES1718#hasLat> ?la .	?ld  <http://project/IoES1718#hasLon> ?lo }
