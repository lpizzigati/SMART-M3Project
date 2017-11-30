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
	private BusMap map;
	private JCheckBox lineNo32CheckBox, lineNo33CheckBox;
	
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
		
		lineNo32CheckBox = new JCheckBox("Line No. 32");
		GridBagConstraints gbc_lineNo32CheckBox = new GridBagConstraints();
		gbc_lineNo32CheckBox.insets = new Insets(0, 0, 5, 0);
		gbc_lineNo32CheckBox.gridx = 0;
		gbc_lineNo32CheckBox.gridy = 2;
		contentPane.add(lineNo32CheckBox, gbc_lineNo32CheckBox);
		
		lineNo33CheckBox = new JCheckBox("Line No. 33");
		GridBagConstraints gbc_lineNo33CheckBox = new GridBagConstraints();
		gbc_lineNo33CheckBox.insets = new Insets(0, 0, 5, 0);
		gbc_lineNo33CheckBox.gridx = 0;
		gbc_lineNo33CheckBox.gridy = 3;
		contentPane.add(lineNo33CheckBox, gbc_lineNo33CheckBox);
		
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
		this.map = new BusMap();
		if(lineNo32CheckBox.isSelected()) {
			BusVisualizerAggregator aggregator = new BusVisualizerAggregator("BUS32", map);
			aggregator.start();
			new Bus("BUS32", "gpx/bus32.gpx").start();
		}
		if(lineNo33CheckBox.isSelected()) {
			BusVisualizerAggregator aggregator2 = new BusVisualizerAggregator("BUS33", map);
			aggregator2.start();
			new Bus("BUS33", "gpx/bus33.gpx").start();
		}
		new BusMapFrame(map);	
		SimulationConfig.getInstance().setSimulationVelocity(2.25);
		this.dispose();
	}
}