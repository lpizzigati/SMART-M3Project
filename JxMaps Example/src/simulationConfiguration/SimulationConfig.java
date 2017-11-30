package simulationConfiguration;

public class SimulationConfig {

	private double simulationVelocity = 1;
	
	private static SimulationConfig instance = null;
	
	private SimulationConfig() {}
	
	public static SimulationConfig getInstance() {
		if(instance == null)
			instance = new SimulationConfig();
		return instance;
	}
	
	public void makeConfig(double simulationVelocity) {
		setSimulationVelocity(simulationVelocity);
	}

	public double getSimulationVelocity() {
		return simulationVelocity;
	}

	public void setSimulationVelocity(double simulationVelocity) {
		this.simulationVelocity = simulationVelocity;
	}
	
	
	
	
}
