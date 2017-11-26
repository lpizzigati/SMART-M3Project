package main;

import sofia_kp.KPICore;
import utils.OntologyReference;
import utils.SIBConfiguration;
import utils.Triple;

public class Bus extends Thread {
	private String name;
	
	public Bus(String name) {
		this.name = name;
	}
	
	@Override
	public void run() {
		KPICore kp = new KPICore(SIBConfiguration.getInstance().getHost(),
				SIBConfiguration.getInstance().getPort(),
				SIBConfiguration.getInstance().getSmartSpaceName());
		
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
