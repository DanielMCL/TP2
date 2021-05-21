package simulator.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
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

		centerPanel.setPreferredSize(new Dimension(800, 400));
		
		BodiesTable t = new BodiesTable(_ctrl);
		t.setPreferredSize(new Dimension(800, 400));
		centerPanel.add(t);
		
		
		Viewer v = new Viewer(_ctrl);
		v.setPreferredSize(new Dimension(800, 1000));
		centerPanel.add(v);
	
		mainPanel.add(centerPanel, BorderLayout.CENTER);
	
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

	}
}
