package aggregators;

import java.util.Vector;

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
		
		
		System.out.println("Printing the query:");
		System.out.println(sparqlQuery);
		resp = kp.querySPARQL(sparqlQuery);
		if(!resp.isConfirmed())
			System.err.println("Errore nell'ottenere i dati dalla SIB");
		else {
			
			String mostPreciseLD = getMostPreciseLD(resp.sparqlquery_results.getResults());
			
			triplesToInsert.add(new Triple(
					name,
					OntologyReference.HAS_SOURCE,
					resp.sparqlquery_results.getResults().stream().filter(riga -> riga.get(0)[2].equals(mostPreciseLD) && riga.get(1)[2].equals(OntologyReference.HAS_SOURCE)).findFirst().get().get(2)[2],
					Triple.URI,
					Triple.URI
					).getAsVector());
			triplesToInsert.add(new Triple(
					name,
					OntologyReference.HAS_PRECISION,
					resp.sparqlquery_results.getResults().stream().filter(riga -> riga.get(0)[2].equals(mostPreciseLD) && riga.get(1)[2].equals(OntologyReference.HAS_PRECISION)).findFirst().get().get(2)[2],
					Triple.URI,
					Triple.LITERAL
					).getAsVector());
			triplesToInsert.add(new Triple(
					name,
					OntologyReference.HAS_LAT,
					resp.sparqlquery_results.getResults().stream().filter(riga -> riga.get(0)[2].equals(mostPreciseLD) && riga.get(1)[2].equals(OntologyReference.HAS_LAT)).findFirst().get().get(2)[2],
					Triple.URI,
					Triple.LITERAL
					).getAsVector());
			triplesToInsert.add(new Triple(
					name,
					OntologyReference.HAS_LON,
					resp.sparqlquery_results.getResults().stream().filter(riga -> riga.get(0)[2].equals(mostPreciseLD) && riga.get(1)[2].equals(OntologyReference.HAS_LON)).findFirst().get().get(2)[2],
					Triple.URI,
					Triple.LITERAL
					).getAsVector());
			triplesToInsert.add(new Triple(
					name,
					OntologyReference.HAS_TIMESTAMP,
					resp.sparqlquery_results.getResults().stream().filter(riga -> riga.get(0)[2].equals(mostPreciseLD) && riga.get(1)[2].equals(OntologyReference.HAS_TIMESTAMP)).findFirst().get().get(2)[2],
					Triple.URI,
					Triple.LITERAL
					).getAsVector());
		}
		
		
		
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
				
				String mostPreciseLD = getMostPreciseLD(resp.sparqlquery_results.getResults());
				
				triplesToInsert.add(new Triple(
						name,
						OntologyReference.HAS_SOURCE,
						resp.sparqlquery_results.getResults().stream().filter(riga -> riga.get(0)[2].equals(mostPreciseLD) && riga.get(1)[2].equals(OntologyReference.HAS_SOURCE)).findFirst().get().get(2)[2],
						Triple.URI,
						Triple.URI
						).getAsVector());
				triplesToInsert.add(new Triple(
						name,
						OntologyReference.HAS_PRECISION,
						resp.sparqlquery_results.getResults().stream().filter(riga -> riga.get(0)[2].equals(mostPreciseLD) && riga.get(1)[2].equals(OntologyReference.HAS_PRECISION)).findFirst().get().get(2)[2],
						Triple.URI,
						Triple.LITERAL
						).getAsVector());
				triplesToInsert.add(new Triple(
						name,
						OntologyReference.HAS_LAT,
						resp.sparqlquery_results.getResults().stream().filter(riga -> riga.get(0)[2].equals(mostPreciseLD) && riga.get(1)[2].equals(OntologyReference.HAS_LAT)).findFirst().get().get(2)[2],
						Triple.URI,
						Triple.LITERAL
						).getAsVector());
				triplesToInsert.add(new Triple(
						name,
						OntologyReference.HAS_LON,
						resp.sparqlquery_results.getResults().stream().filter(riga -> riga.get(0)[2].equals(mostPreciseLD) && riga.get(1)[2].equals(OntologyReference.HAS_LON)).findFirst().get().get(2)[2],
						Triple.URI,
						Triple.LITERAL
						).getAsVector());
				triplesToInsert.add(new Triple(
						name,
						OntologyReference.HAS_TIMESTAMP,
						resp.sparqlquery_results.getResults().stream().filter(riga -> riga.get(0)[2].equals(mostPreciseLD) && riga.get(1)[2].equals(OntologyReference.HAS_TIMESTAMP)).findFirst().get().get(2)[2],
						Triple.URI,
						Triple.LITERAL
						).getAsVector());			
						
				
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
	
	private String getMostPreciseLD(Vector<Vector<String[]>> locationData) {
		String mostPreciseLD = "";
		int maxPrecision = Integer.MAX_VALUE;
		
		for(Vector<String[]> riga : locationData) {
			/*
			 * so che la precisione è nel terzo campo del vettore, per quelle righe
			 * che come secondo campo hanno il predicato hasPrecision
			 */
			/*
			 * il valore del campo è alla 3 posizione dell'array
			 */
			
			if(riga.get(1)[2].equals(OntologyReference.HAS_PRECISION) && Integer.parseInt(riga.get(2)[2]) < maxPrecision) {
				mostPreciseLD = riga.get(0)[2];
				maxPrecision = Integer.parseInt(riga.get(2)[2]);
			}
		}
		return mostPreciseLD;
	}
	
	
}
