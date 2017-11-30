package main;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class Gui extends JFrame{
	
	public Gui(BusMap map) {
		JFrame frame = new JFrame("Polylines");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.add(map, BorderLayout.CENTER);
		frame.setSize(1400, 1000);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setResizable(false);
	}
	
}
