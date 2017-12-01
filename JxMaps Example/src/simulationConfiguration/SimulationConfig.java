package simulationConfiguration;

public class SimulationConfig {

	private double simulationVelocity = 1;
	private int peopleAtBusStop = 0;
	private int percErrorPeopleAtBusStop = 0;
	
	private static SimulationConfig instance = null;
	
	private SimulationConfig() {}
	
	public static SimulationConfig getInstance() {
		if(instance == null)
			instance = new SimulationConfig();
		return instance;
	}
	
	public double getSimulationVelocity() {
		return simulationVelocity;
	}

	public void setSimulationVelocity(double simulationVelocity) {
		this.simulationVelocity = simulationVelocity;
	}

	public int getPeopleAtBusStop() {
		return peopleAtBusStop;
	}

	public void setPeopleAtBusStop(int peopleAtBusStop) {
		this.peopleAtBusStop = peopleAtBusStop;
	}

	public int getPercErrorPeopleAtBusStop() {
		return percErrorPeopleAtBusStop;
	}

	public void setPercErrorPeopleAtBusStop(int percErrorPeopleAtBusStop) {
		this.percErrorPeopleAtBusStop = percErrorPeopleAtBusStop;
	}
	
}
