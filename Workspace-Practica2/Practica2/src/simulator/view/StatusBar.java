package simulator.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class StatusBar extends JPanel implements SimulatorObserver {
	private JLabel _currTime; // for current time
	private JLabel _currLaws; // for gravity laws
	private JLabel _numOfBodies; // for number of bodies
	private static final String INI_LAW = "Newton's Universal Gravitation";
	private static final String INI_CONST = "G = 6.67E-11";
	private static final String INI_BODIES = "0";
	
	public StatusBar(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
	}
	private void initGUI() {
		this.setLayout( new FlowLayout( FlowLayout.LEFT ));
		this.setBorder( BorderFactory.createBevelBorder(1));
		
		JToolBar bar = new JToolBar();
		
		_currTime = new JLabel("Time:  0.0");
		_currLaws = new JLabel("Laws:  " + INI_LAW + " with " + INI_CONST);
		_numOfBodies = new JLabel("Bodies:  " + INI_BODIES);
		
		bar.add(_currTime);
		bar.addSeparator(new Dimension(100, 10));
		bar.add(_numOfBodies);
		bar.addSeparator(new Dimension(100, 10));
		bar.add(_currLaws);
		
		this.add(bar);
		this.setVisible(true);
	}
	// other private/protected methods
	// ...
	// SimulatorObserver methods
	// ...

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
		_currLaws.setText("Laws:  " + fLawsDesc);
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		_currTime.setText("Time:  0.0");
		_currLaws.setText("Laws:  " + INI_LAW + " with " + INI_CONST);
		_numOfBodies.setText("Bodies:  " + INI_BODIES);
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		_numOfBodies.setText("Bodies:  " + bodies.size());
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		_numOfBodies.setText("Bodies:  " + bodies.size());
		_currTime.setText("Time:  " + time);
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onForceLawsChanged(String fLawsDesc) {
		_currLaws.setText("Laws:  " + fLawsDesc);
	}

}
