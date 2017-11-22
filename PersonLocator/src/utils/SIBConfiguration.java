package utils;

import java.io.FileInputStream;
import java.util.Properties;

public class SIBConfiguration {
	
	private static String CONFIG_FILE_NAME = "path/to/file";
	
	private String host;
	private int port;
	private String smartSpaceName;
	
	private static SIBConfiguration instance = null;
	
	private SIBConfiguration() {
		Properties props = new Properties();
		
		try {
			props.load(new FileInputStream(CONFIG_FILE_NAME));
			
			host = props.getProperty("host");
			port = Integer.parseInt(props.getProperty("port"));
			smartSpaceName = props.getProperty("smartSpaceName");
		}
		catch(Exception e) {
			loadDefault();
		}
	}
	
	private void loadDefault() {
		host = "macadri6.ddns.net";
		port = 7701;
		smartSpaceName = "X";		
	}

	public static SIBConfiguration getInstance() {
		if(instance == null)
			instance = new SIBConfiguration();
		return instance;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String getSmartSpaceName() {
		return smartSpaceName;
	}
	
}
