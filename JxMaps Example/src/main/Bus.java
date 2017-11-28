package main;

import sofia_kp.KPICore;
import sofia_kp.SIBResponse;
import utils.OntologyReference;
import utils.SIBConfiguration;
import utils.Triple;
import parser.Parser;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.Semaphore;

import com.teamdev.jxmaps.LatLng;



public class Bus extends Thread {
	private String name;
	private SIBResponse resp;
	protected static Semaphore semaphore;

	Bus(String name) {
		this.name = name;
		semaphore = new Semaphore(0);
	}
	
	@Override
	public void run() {
		LatLng nextPoint;
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
		
		try {
			semaphore.acquire();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		//move bus: for each point insert new triple
		for (int i = 0; i < size1; i++) {
			Vector<Vector<String>> newTripleToInsert = new Vector<>();		
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
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
