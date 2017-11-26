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
	public static MapExample map;

		
	public Aggregator(String busName) {
		this.busName = busName;
		kp = new KPICore(SIBConfiguration.getInstance().getHost(),
					SIBConfiguration.getInstance().getPort(),
					SIBConfiguration.getInstance().getSmartSpaceName());
		}

	@Override
	public void run() {
		map = new MapExample();
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
		Handler2 MyHandler = new Handler2(); 
		resp = kp.subscribeSPARQL(sparqlQuery, MyHandler );
		
		
		/*
		System.out.println(sparqlQuery);
		resp = kp.querySPARQL(sparqlQuery);
		//System.out.println(resp.sparqlquery_results.print_as_string());
		Vector<Vector<String[]>> data = resp.sparqlquery_results.getResults();
		List<LatLng> points = new ArrayList<LatLng>();
		for(Vector<String[]> riga : data) {
			System.out.println("Location data:" + riga.get(0)[2] + "has lat: " + riga.get(1)[2]+ " has lon: " + riga.get(2)[2]); 	
			String lat = riga.get(1)[2]+"0";
			String lon = riga.get(2)[2]+"0";
			System.out.println(lat + " " + lon);
			points.add(0, new LatLng(Double.parseDouble(lat),Double.parseDouble(lon)));
		}
		*/
	
		JFrame frame = new JFrame("Polylines");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.add(map, BorderLayout.CENTER);
		frame.setSize(700, 500);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setResizable(false);
		
	
	}
}




//select ?ld ?la ?lo  where { <http://project/IoES1718#BUS32> <http://project/IoES1718#hasLocatioData> ?ld . ?ld <http://project/IoES1718#hasLat> ?la .	?ld  <http://project/IoES1718#hasLon> ?lo }
