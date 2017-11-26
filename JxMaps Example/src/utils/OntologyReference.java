package utils;

public class OntologyReference {

	private OntologyReference(){};
	
	private static final String RDF = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
	private static final String RDFS = "http://www.w3.org/2000/01/rdf-schema#";
	
	public static final String RDF_TYPE = RDF + "type";
	
	public static final String NS = "http://lab2#";
	
	public static final String LOCATION_DATA = NS + "LocationData";
	public static final String HAS_LAT = NS + "hasLat";
	public static final String HAS_LON = NS + "hasLon";
	public static final String HAS_PRECISION = NS + "hasPrecision";
	public static final String HAS_SOURCE = NS + "hasSource";
	public static final String HAS_TIMESTAMP = NS + "hasTimestamp";
	
	public static final String PERSON = NS + "Person";
	public static final String HAS_LOCATION_DATA = NS + "hasLocationData";
	public static final String HAS_LOCATION = NS + "hasLocation";
	
	public static final String LOCATION = NS + "Location";
	
	public static final String WIFI_SOURCE = NS + "WifiSource";
	public static final String GPS_SOURCE = NS + "GPSSource";
	public static final String NFC_SOURCE = NS + "NFCSource";	
	
}
