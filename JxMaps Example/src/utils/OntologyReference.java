package utils;

public class OntologyReference {

	private OntologyReference(){};
	
	private static final String RDF = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
	private static final String RDFS = "http://www.w3.org/2000/01/rdf-schema#";
	
	public static final String RDF_TYPE = RDF + "type";
	
	public static final String NS = "http://project/IoES1718#";
	
	public static final String BUS = NS + "Bus";
	public static final String HAS_LAT = NS + "hasLat";
	public static final String HAS_LON = NS + "hasLon";

	
}
