package sensors;

import java.util.Random;
import java.util.Vector;

import sofia_kp.KPICore;
import sofia_kp.SIBResponse;
import utils.OntologyReference;
import utils.SIBConfiguration;
import utils.Triple;

public class Sensor extends Thread{

	private String personName;
	private String precision;
	private String sourceType;
	private int updatePeriodMillis;
		
	private KPICore kp;
	private SIBResponse resp;
	
	private Random r;
		
	/**
	 * @param person The ID of the person (NOT the complete URI)
	 * @param sourceType The type of the sensor (Use the TYPE_SOURCE string in OntologyReference
	 * @param precision A string that represents the precision of the sensor
	 * @param updatePeriodMillis The update period in millis
	 */ 
	public Sensor(String person, String sourceType, String precision, int updatePeriodMillis) {
		this.personName = person;
		this.sourceType = sourceType;
		this.precision = precision;
		this.updatePeriodMillis = updatePeriodMillis;
		kp = new KPICore(SIBConfiguration.getInstance().getHost(),
				SIBConfiguration.getInstance().getPort(),
				SIBConfiguration.getInstance().getSmartSpaceName());
		r = new Random(person.hashCode());
	}
	
	
	@Override
	public void run() {
		
		/*
		 * Questo processo ad intervalli regolari dovrà inserire (poi fare l'upload) nella SIB
		 * di un LocationData con alcune proprietà:
		 * 		hasLat literal
		 *		hasLon literal
		 *		hasPrecision literal
		 *		hasSource LocationSourceType
		 *		hasTimestamp literal
		 * poi inserisce una tripla che lo collega alla persona (solo la prima volta)
		 * 
		 */
		
		String name = sourceType + "_" + personName;
		String person = OntologyReference.NS + personName;
		String lat;
		String lon;		
		String timestamp;
		
		
		if(!kp.join().isConfirmed())
			System.err.println ("Error joining the SIB");
		else
			System.out.println ("SIB joined correctly");
		
		
		Vector<Vector<String>> triplesToInsert = new Vector<>();
		Vector<Vector<String>> oldTriples = new Vector<>();
		
		//Inserisco la tripla che collega la persona al LocationData
		Vector<String> personLocationDataArch = new Triple(
				person,
				OntologyReference.HAS_LOCATION_DATA,
				name,
				Triple.URI,
				Triple.URI).getAsVector();		
		triplesToInsert.add(personLocationDataArch);
		
		//Inserisco l'oggetto LocationData per questo sensore
		Vector<String> locationDataInstance = new Triple(
				name,
				OntologyReference.RDF_TYPE,
				OntologyReference.LOCATION_DATA,
				Triple.URI,
				Triple.URI).getAsVector();
		triplesToInsert.add(locationDataInstance);
		
		//Genero i valori valori della "lettura" del sensore
		lat = getDecimalDegreeStringLat(); 
		lon = getDecimalDegreeStringLon();
		timestamp = getStringTimestamp();
		
		triplesToInsert.add(new Triple(
				name,
				OntologyReference.HAS_LAT,
				lat,
				Triple.URI,
				Triple.LITERAL).getAsVector());

		triplesToInsert.add(new Triple(
				name,
				OntologyReference.HAS_LON,
				lon,
				Triple.URI,
				Triple.LITERAL).getAsVector());
		
		Vector<String> triplePrec = new Triple(
				name,
				OntologyReference.HAS_PRECISION,
				precision,
				Triple.URI,
				Triple.LITERAL).getAsVector();
		triplesToInsert.add(triplePrec);
		
		Vector<String> tripleSource = new Triple(
				name,
				OntologyReference.HAS_SOURCE,
				sourceType,
				Triple.URI,
				Triple.URI).getAsVector();
		triplesToInsert.add(tripleSource);
		
		triplesToInsert.add(new Triple(
				name,
				OntologyReference.HAS_TIMESTAMP,
				timestamp,
				Triple.URI,
				Triple.LITERAL).getAsVector());
		
		//Faccio il primo inserimento
		resp = kp.insert(triplesToInsert);
		
		if(!resp.isConfirmed())
			System.err.println("#" + name + ": Errore nel primo inserimento");
		
		triplesToInsert.remove(locationDataInstance);
		triplesToInsert.remove(personLocationDataArch);
		triplesToInsert.remove(tripleSource);
		triplesToInsert.remove(triplePrec);
		//perchè delle altre ne farò l'update.
		
		oldTriples = triplesToInsert;
		
		//Ciclo dove genero i valori e faccio l'update delle tuple
		try {
			for(;;sleep(updatePeriodMillis)) {
				lat = getDecimalDegreeStringLat(); 
				lon = getDecimalDegreeStringLon();
				timestamp = getStringTimestamp();
				
				triplesToInsert = new Vector<>();
				
				triplesToInsert.add(new Triple(
						name,
						OntologyReference.HAS_LAT,
						lat,
						Triple.URI,
						Triple.LITERAL).getAsVector());

				triplesToInsert.add(new Triple(
						name,
						OntologyReference.HAS_LON,
						lon,
						Triple.URI,
						Triple.LITERAL).getAsVector());
				
				triplesToInsert.add(new Triple(
						name,
						OntologyReference.HAS_PRECISION,
						precision,
						Triple.URI,
						Triple.LITERAL).getAsVector());
				
				triplesToInsert.add(new Triple(
						name,
						OntologyReference.HAS_SOURCE,
						sourceType,
						Triple.URI,
						Triple.URI).getAsVector());
				
				triplesToInsert.add(new Triple(
						name,
						OntologyReference.HAS_TIMESTAMP,
						timestamp,
						Triple.URI,
						Triple.LITERAL).getAsVector());
				
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

	
	/*
	 * Gradi decimali (DD)
	 * Di solito da 4 a 6 cifre decimali. Esempio: 41.890230°, 012.492226°.
	 * 
	 * Notare che l'indicazione degli emisferi N (nord) / S (sud) e E (est) / O (ovest) può essere sostituita dal segno.
	 * In particolare, avremo valori negativi per latitudini nell'emisfero sud e longitudini ad ovest del meridiano fondamentale.
	 */	
	private String getDecimalDegreeStringLat() {
		return (r.nextInt(180) - 90) + "." + r.nextInt(9999);
	}
	
	private String getDecimalDegreeStringLon() {
		return (r.nextInt(360) - 180) + "." + r.nextInt(9999);
	}
	
	private String getStringTimestamp() {
		return "" + System.currentTimeMillis();
	}
}
