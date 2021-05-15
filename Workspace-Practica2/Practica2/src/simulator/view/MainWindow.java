package simulator.view;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import simulator.control.Controller;

public class MainWindow extends JFrame {
	Controller _ctrl;
	
	public MainWindow(Controller controller) {
		super("Physics Simulator");
		_ctrl = controller;
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel();
		setContentPane(mainPanel);
		mainPanel.setLayout(new BorderLayout());
		
		ControlPanel cp = new ControlPanel(_ctrl);
		mainPanel.add(cp, BorderLayout.PAGE_START);
		
		StatusBar bar = new StatusBar(_ctrl);
		mainPanel.add(bar, BorderLayout.PAGE_END);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		
		BodiesTable t = new BodiesTable(_ctrl);
		centerPanel.add(t);
		
		Viewer v = new Viewer(_ctrl);
		centerPanel.add(v);
		
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		
		this.setContentPane(mainPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);

	}
}
