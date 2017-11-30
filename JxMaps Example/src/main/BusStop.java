package main;

import com.teamdev.jxmaps.LatLng;

import sofia_kp.KPICore;
import utils.SIBConfiguration;

public class BusStop extends Thread {

	private String name;
	private String id;
	private LatLng location;
	public BusStop(String name, String id, LatLng location) {
		super();
		this.name = name;
		this.id = id;
		this.location = location;
	}
	
	@Override
	public void run() {
		
		KPICore kp = new KPICore(SIBConfiguration.getInstance().getHost(),
				SIBConfiguration.getInstance().getPort(),
				SIBConfiguration.getInstance().getSmartSpaceName());
		
		if(!kp.join().isConfirmed())
			System.err.println ("Error joining the SIB");
		else
			System.out.println ("Bus joined SIB correctly");
		
	}
	
	
	
}
