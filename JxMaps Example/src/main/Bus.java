package main;

import sofia_kp.KPICore;
import sofia_kp.SIBResponse;
import utils.OntologyReference;
import utils.SIBConfiguration;
import utils.Triple;
import parser.Parser;
import java.util.List;
import java.util.Vector;

import com.teamdev.jxmaps.LatLng;



public class Bus extends Thread {
	private String name;
	private SIBResponse resp;

	Bus(String name) {
		this.name = name;
	}
	
	@Override
	public void run() {
		LatLng nextPoint;
		//Vector<Vector<String>> triplesToInsert = new Vector<>();		
		//Vector<Vector<String>> oldTriples = new Vector<>();

		KPICore kp = new KPICore(SIBConfiguration.getInstance().getHost(),
				SIBConfiguration.getInstance().getPort(),
				SIBConfiguration.getInstance().getSmartSpaceName());
		
		if(!kp.join().isConfirmed())
			System.err.println ("Error joining the SIB");
		else
			System.out.println ("Bus joined SIB correctly");
	
		//insert bus into SIB
		if(!kp.insert(
				OntologyReference.NS + name,
				OntologyReference.RDF_TYPE,
				OntologyReference.BUS,
				Triple.URI,
				Triple.URI).isConfirmed())
			System.err.println ("Error inserting bus");
		else
			System.out.println ("Bus correctly inserted");
		
		//get list of stops
		Parser p1;
    	List<LatLng> points1;
    	p1 = new Parser("./gpx/32.gpx");
		points1 = p1.getListOfPoint();
		int size1 = points1.size();
		nextPoint = points1.get(0);
		
		//move bus: for each point insert new triple
		for (int i = 0; i < 5; i++) {
			try {
				Vector<Vector<String>> newTripleToInsert = new Vector<>();		
				Thread.sleep(100);
				nextPoint = points1.get(i);
				
				newTripleToInsert.add(new Triple(
						OntologyReference.NS + name +"locationData"+i,
						OntologyReference.RDF_TYPE,
						OntologyReference.LOCATION_DATA,
						Triple.URI,
						Triple.URI).getAsVector());
				
				newTripleToInsert.add(new Triple(
						OntologyReference.NS + name,
						OntologyReference.HAS_LOCATION_DATA,
						OntologyReference.NS + name +"locationData"+i,
						Triple.URI,
						Triple.URI).getAsVector());
				
				newTripleToInsert.add(new Triple(
						OntologyReference.NS + name +"locationData"+ i,
						OntologyReference.HAS_LAT,
						String.valueOf(nextPoint.getLat()),
						Triple.URI,
						Triple.LITERAL).getAsVector());
				
				newTripleToInsert.add(new Triple(
						OntologyReference.NS + name +"locationData"+ i,
						OntologyReference.HAS_LON,
						String.valueOf(nextPoint.getLng()),
						Triple.URI,
						Triple.LITERAL).getAsVector());
				
				resp = kp.insert(newTripleToInsert);
			System.out.println("Point " + i + " inserted");
			
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
