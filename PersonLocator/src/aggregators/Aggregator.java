package aggregators;

import java.util.Vector;
import java.util.stream.Collectors;

import sofia_kp.KPICore;
import sofia_kp.SIBResponse;
import utils.OntologyReference;
import utils.SIBConfiguration;
import utils.Triple;

public class Aggregator extends Thread {

	private String personName;
	private long updatePeriodMillis;	
	
	private KPICore kp;
	private SIBResponse resp;
	
	public Aggregator(String personName, long updatePeriodMillis) {
		this.personName = personName;
		this.updatePeriodMillis = updatePeriodMillis;
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
		
		String person = OntologyReference.NS + personName;
		String name = OntologyReference.NS + "Location_" + personName;
		
		String sparqlQuery =
				"select ?ld ?p ?o "
				+ "where { "
					+ "<" + person + "> <" + OntologyReference.HAS_LOCATION_DATA + "> ?ld . "
					+ "?ld ?p ?o"
				+ " }";
		
		Vector<Vector<String>> triplesToInsert = new Vector<>();
		Vector<Vector<String>> oldTriples = new Vector<>();
		
		Vector<String> personLocationArch = new Triple(
				person,
				OntologyReference.HAS_LOCATION,
				name,
				Triple.URI,
				Triple.URI).getAsVector();		
		triplesToInsert.add(personLocationArch);
		
		
		
		resp = kp.querySPARQL(sparqlQuery);
		if(!resp.isConfirmed())
			System.err.println("Errore nell'ottenere i dati dalla SIB");
		

		
		
		
		resp = kp.insert(triplesToInsert);
		
		if(!resp.isConfirmed())
			System.err.println("#" + name + ": Errore nel primo inserimento");
		
		triplesToInsert.remove(personLocationArch);
		oldTriples = triplesToInsert;
		
	
		try {
			for(;;sleep(updatePeriodMillis)) {
				
				triplesToInsert = new Vector<>();
				
				
				resp = kp.querySPARQL(sparqlQuery);
				if(!resp.isConfirmed())
					System.err.println("Errore nell'ottenere i dati dalla SIB");
				
				
						
				
				resp = kp.update(triplesToInsert, oldTriples);
				
				if(!resp.isConfirmed())
					System.err.println("#" + name + ": Errore nell'inserimento");
				
				oldTriples = triplesToInsert;				
				
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
}
