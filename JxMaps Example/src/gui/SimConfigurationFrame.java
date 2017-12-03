package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.BusVisualizerAggregator;
import main.Bus;
import main.BusMap;
import simulationConfiguration.SimulationConfig;

@SuppressWarnings("serial")
public class SimConfigurationFrame extends JFrame {

	private JPanel contentPane;
	private BusMap busMap;
	private JCheckBox lineNo32CheckBox, lineNo20CheckBox;
	
	public SimConfigurationFrame() {
		setTitle("SimConfiguration");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		lineNo20CheckBox = new JCheckBox("Line No. 20");
		lineNo20CheckBox.setSelected(true);
		GridBagConstraints gbc_lineNo20CheckBox = new GridBagConstraints();
		gbc_lineNo20CheckBox.insets = new Insets(0, 0, 5, 0);
		gbc_lineNo20CheckBox.gridx = 0;
		gbc_lineNo20CheckBox.gridy = 3;
		contentPane.add(lineNo20CheckBox, gbc_lineNo20CheckBox);
		
		lineNo32CheckBox = new JCheckBox("Line No. 32");
		lineNo32CheckBox.setSelected(true);
		GridBagConstraints gbc_lineNo32CheckBox = new GridBagConstraints();
		gbc_lineNo32CheckBox.insets = new Insets(0, 0, 5, 0);
		gbc_lineNo32CheckBox.gridx = 0;
		gbc_lineNo32CheckBox.gridy = 2;
		contentPane.add(lineNo32CheckBox, gbc_lineNo32CheckBox);
		
		JButton startSimButton = new JButton("START SIM");
		startSimButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startSimButtonPressed();
			}
		});
		
		GridBagConstraints gbc_startSimButton = new GridBagConstraints();
		gbc_startSimButton.gridx = 0;
		gbc_startSimButton.gridy = 4;
		contentPane.add(startSimButton, gbc_startSimButton);
	}
	
	public void startSimButtonPressed() {
		this.busMap = new BusMap();
		if(lineNo32CheckBox.isSelected()) {
			BusVisualizerAggregator aggregator = new BusVisualizerAggregator("BUS32", busMap);
			aggregator.start();
			new Bus("BUS32", "gpx/bus32.gpx").start();
		}
		if(lineNo20CheckBox.isSelected()) {
			BusVisualizerAggregator aggregator2 = new BusVisualizerAggregator("BUS20", busMap);
			aggregator2.start();
			new Bus("BUS20", "gpx/bus20.gpx").start();
		}
		new BusMapFrame(busMap);	
		SimulationConfig.getInstance().setSimulationVelocity(1.5);
		this.dispose();
	}
}
