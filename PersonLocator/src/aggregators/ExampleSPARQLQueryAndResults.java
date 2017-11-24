package aggregators;

import java.util.Vector;

import sofia_kp.KPICore;
import sofia_kp.SIBResponse;
import utils.OntologyReference;
import utils.SIBConfiguration;

public class ExampleSPARQLQueryAndResults extends Thread {
	@Override
	public void run() {
		KPICore kp = new KPICore(SIBConfiguration.getInstance().getHost(),
				SIBConfiguration.getInstance().getPort(),
				SIBConfiguration.getInstance().getSmartSpaceName());
		SIBResponse resp;
		
		if(!kp.join().isConfirmed())
			System.err.println ("Error joining the SIB");
		else
			System.out.println ("SIB joined correctly");
		
		String person = OntologyReference.NS + "Mario";
		
		String sparqlQuery =
				"select ?ld ?p ?o "
				+ "where { "
					+ "<" + person + "> <" + OntologyReference.HAS_LOCATION_DATA + "> ?ld . "
					+ "?ld ?p ?o"
				+ " }";
		
		System.out.println("Printing the query:");
		System.out.println(sparqlQuery);
		resp = kp.querySPARQL(sparqlQuery);
		if(!resp.isConfirmed())
			System.err.println("Errore nell'ottenere i dati dalla SIB");
		else {
			System.out.println(resp.sparqlquery_results.print_as_string());
			Vector<Vector<String[]>> res = resp.sparqlquery_results.getResults();
			int i = 0;
			for(Vector<String[]> riga : res) {
				System.out.println("Riga " + i++ + ":");
				int nc = 0;
				for(String[] cella : riga) {
					System.out.println("----inizioCella " + nc + ":");
					for(String elementoCella : cella) {
						System.out.println(elementoCella);
					}
					System.out.println("----fineCella " + nc++);
				}
				System.out.println();
			}
		}
	}
}
