package simulator.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.swing.Box;
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
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
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
		
		JLabel stepsText = new JLabel("Steps:");
		
		JSpinner steps = new JSpinner(new SpinnerNumberModel(0, 0, 50000, 100));
		steps.setPreferredSize(new Dimension(70, 40));
		steps.setMaximumSize(new Dimension(70, 40));

		
		JLabel dtText = new JLabel("Delta-Time:");
		
		JTextField dt = new JTextField();
		dt.setPreferredSize(new Dimension(70, 40));
		dt.setMaximumSize(new Dimension(70, 40));

		
		JButton exit = new JButton(im5); 
		exit.setToolTipText("Exit");
		
		// Acciones de los botones
		
		ActionListener openAction = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				JFileChooser fc = new JFileChooser();
				int ret = fc.showOpenDialog(bar);
				
				if (ret == JFileChooser.APPROVE_OPTION) {
					_ctrl.reset();
					try {
						_ctrl.loadBodies(new FileInputStream(fc.getSelectedFile()));
					} catch (FileNotFoundException e) { // Habrá que cambiar eso
						e.printStackTrace();
					}
				} 
				else {
					JOptionPane.showMessageDialog(bar, "Se ha pulsado cancelar o ha ocurrido un error.");
				}
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
				dt.setEnabled(false);
				steps.setEnabled(false);
				exit.setEnabled(false);
				
				double det;
				if (dt.getText() != null) {
					det = Double.parseDouble(dt.getText());
				}
				else {
					det = 0.0;
				}
				int st = Integer.parseInt(steps.getValue().toString());
				
				_ctrl.setDeltaTime(det);
				run_sim(st);
				
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
		bar.addSeparator();
		bar.add(physics);
		bar.addSeparator();
		bar.add(run);
		bar.add(stop);
		bar.addSeparator(new Dimension(5, 40));
		bar.add(stepsText);
		bar.addSeparator(new Dimension(5, 40));
		bar.add(steps);
		bar.addSeparator(new Dimension(5, 40));
		bar.add(dtText);
		bar.addSeparator(new Dimension(5, 40));
		bar.add(dt);
		bar.add(Box.createGlue());
		bar.add(exit);
		
		this.add(bar);
		this.setVisible(true);
		
	}
	
	private void run_sim(int n) {
		if ( n>0 && !_stopped ) {
			try {
				// _ctrl.run(1);
			} catch (Exception e) {
				// TODO show the error in a dialog box
				bar.getComponent(0).setEnabled(true);
				bar.getComponent(2).setEnabled(true);
				bar.getComponent(4).setEnabled(true);
				bar.getComponent(9).setEnabled(true);
				bar.getComponent(13).setEnabled(true);
				bar.getComponent(15).setEnabled(true);
				
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
			bar.getComponent(0).setEnabled(true);
			bar.getComponent(2).setEnabled(true);
			bar.getComponent(4).setEnabled(true);
			bar.getComponent(9).setEnabled(true);
			bar.getComponent(13).setEnabled(true);
			bar.getComponent(15).setEnabled(true);
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
