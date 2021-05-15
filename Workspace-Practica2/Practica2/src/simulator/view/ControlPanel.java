package simulator.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class ControlPanel extends JPanel implements SimulatorObserver{
	private Controller _ctrl;
	private boolean _stopped;
	private JToolBar bar;
	
	public ControlPanel(Controller ctrl) {
		_ctrl = ctrl;
		_stopped = true;
		initGUI();
		_ctrl.addObserver(this);
	}
	private void initGUI() {
		bar = new JToolBar();
		this.setLayout(new BoxLayout(bar, BoxLayout.X_AXIS));
		
		ImageIcon im1 = new ImageIcon("resources/icons/open.png");
		ImageIcon im2 = new ImageIcon("resources/icons/physics.png");
		ImageIcon im3 = new ImageIcon("resources/icons/run.png");
		ImageIcon im4 = new ImageIcon("resources/icons/stop.png");
		ImageIcon im5 = new ImageIcon("resources/icons/exit.png");
		
		JButton open = new JButton(im1); 
		open.setToolTipText("Open file");
		
		JButton physics = new JButton(im2);
		physics.setToolTipText("Change the law of physics");
		
		JButton run = new JButton(im3); 
		run.setToolTipText("Run");
		
		JButton stop = new JButton(im4); 
		stop.setToolTipText("Stop");
		
		JButton exit = new JButton(im5); 
		exit.setToolTipText("Exit");
		
		JLabel stepsText = new JLabel("Steps:");
		
		JSpinner steps = new JSpinner(new SpinnerNumberModel(0, 0, 50000, 100));
		
		JLabel dtText = new JLabel("Delta-Time:");
		
		JTextField dt = new JTextField();
		
		// Acciones de los botones
		
		ActionListener openAction = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				JFileChooser fc = new JFileChooser();
				
				
			}
			
		};
		open.addActionListener(openAction);
		
		ActionListener physicsAction = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				
			}
			
		};
		physics.addActionListener(physicsAction);
		
		ActionListener runAction = new ActionListener() { // Boton run

			@Override
			public void actionPerformed(ActionEvent ae) {
				open.setEnabled(false);
				physics.setEnabled(false);
				run.setEnabled(false);
				exit.setEnabled(false);
				
				_ctrl.setDeltaTime(Integer.parseInt(dt.getText()));
				run_sim(Integer.parseInt(steps.getValue().toString()));
				
			}
			
		};
		run.addActionListener(runAction);
		
		ActionListener stopAction = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_stopped = true;
			}
			
		};
		
		stop.addActionListener(stopAction);
		
		ActionListener exitAction = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int n = JOptionPane.showOptionDialog(ControlPanel.this,
						"Are sure you want to quit?", "Quit",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, null, null);
				
				if (n == 0) System.exit(0);
			}
			
		};
		
		exit.addActionListener(exitAction);
		
		bar.add(open);
		bar.add(physics);
		bar.add(run);
		bar.add(stop);
		bar.add(exit);
		bar.add(stepsText);
		bar.add(steps);
		bar.add(dtText);
		bar.add(dt);
		
	}
	
	private void run_sim(int n) {
		if ( n>0 && !_stopped ) {
			try {
				// _ctrl.run(1);
			} catch (Exception e) {
				// TODO show the error in a dialog box
				// TODO enable all buttons
				_stopped = true;
				return;
			}
			SwingUtilities.invokeLater( new Runnable() {
				@Override
				public void run() {
					run_sim(n-1);
				}
			});
		} else {
			_stopped = true;
			// TODO enable all buttons
		}
	}
	// SimulatorObserver methods
	// ...
	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onAdvance(List<Body> bodies, double time) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onForceLawsChanged(String fLawsDesc) {
		// TODO Auto-generated method stub
		
	}

}
