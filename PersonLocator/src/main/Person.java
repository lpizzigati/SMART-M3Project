package main;

import sofia_kp.KPICore;
import utils.OntologyReference;
import utils.SIBConfiguration;
import utils.Triple;

public class Person extends Thread {

	private String name;
	
	public Person(String name) {
		this.name = name;
	}
	
	@Override
	public void run() {
		KPICore kp = new KPICore(SIBConfiguration.HOST,
				SIBConfiguration.PORT,
				SIBConfiguration.SMART_SPACE_NAME);
		
		if(!kp.join().isConfirmed())
			System.err.println ("Error joining the SIB");
		else
			System.out.println ("SIB joined correctly");
		
		if(!kp.insert(
				OntologyReference.NS + name,
				OntologyReference.RDF_TYPE,
				OntologyReference.PERSON,
				Triple.URI,
				Triple.URI).isConfirmed())
			System.err.println ("Error inerting the person into the SIB");
		else
			System.out.println ("Person correctly inserted");
			
	}
	
}
