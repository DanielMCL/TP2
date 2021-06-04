package simulator.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import org.json.JSONObject;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class ControlPanel extends JPanel implements SimulatorObserver{
	private Controller _ctrl;
	private boolean _stopped;
	private JToolBar bar;
	private JButton open;
	private JButton physics;
	private JButton run;
	private JButton stop;
	private JButton exit;
	private JSpinner steps;
	private JTextField dtime;
	
	public ControlPanel(Controller ctrl) {
		_ctrl = ctrl;
		_stopped = false;
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
		
		open = new JButton(im1); 
		open.setToolTipText("Open file");
		
		physics = new JButton(im2);
		physics.setToolTipText("Change the law of physics");
		
		run = new JButton(im3); 
		run.setToolTipText("Run");
		
		stop = new JButton(im4); 
		stop.setToolTipText("Stop");
		
		JLabel stepsText = new JLabel("Steps:");
		
		steps = new JSpinner(new SpinnerNumberModel(10000, 0, 50000, 100));
		steps.setPreferredSize(new Dimension(70, 40));
		steps.setMaximumSize(new Dimension(70, 40));

		
		JLabel dtText = new JLabel("Delta-Time:");
		
		dtime = new JTextField();
		dtime.setPreferredSize(new Dimension(70, 40));
		dtime.setMaximumSize(new Dimension(70, 40));

		
		exit = new JButton(im5); 
		exit.setToolTipText("Exit");
		
		ForceLawsDialog fld = new ForceLawsDialog((Frame) SwingUtilities.getWindowAncestor(this), _ctrl.getForceLawsInfo());
		
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
					} catch (Exception e) {
						JOptionPane.showMessageDialog(bar, "Failed to load a file");
					}
				} 
			}
		};
		open.addActionListener(openAction);
		
		ActionListener physicsAction = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				int status = fld.open();
				if (status == 1) {
					try {
						_ctrl.setForceLaws(fld.getJSON());
					}
					catch (Exception e) {
						JOptionPane.showMessageDialog(ControlPanel.this,
								"Wrong arguments" , "Could not change the Force Law",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			
		};
		physics.addActionListener(physicsAction);
		
		ActionListener runAction = new ActionListener() { // Boton run

			@Override
			public void actionPerformed(ActionEvent ae) {
				_stopped = false;
				
				double det;
				if (dtime.getText() == null) {
					det = 0.0;
					_ctrl.setDeltaTime(det);
				}
				else {
					det = Double.parseDouble(dtime.getText());
					if (det < 0) {
						JOptionPane.showMessageDialog(ControlPanel.this,
								"Invalid value of delta-time" , "Incorrect arguments",
								JOptionPane.ERROR_MESSAGE);
					}
					else _ctrl.setDeltaTime(det);
				}
				
				int st = Integer.parseInt(steps.getValue().toString());
				setButtons(false);
				
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
		bar.add(dtime);
		bar.add(Box.createGlue());
		bar.add(exit);
		
		this.add(bar);
		this.setVisible(true);
		
	}
	
	private void run_sim(int n) {
		if ( n>0 && !_stopped ) {
			try {
				_ctrl.run(1);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this,
						e.getMessage(), "Execution error",
						JOptionPane.ERROR_MESSAGE);
				
				setButtons(true);
				
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
			
			setButtons(true);
		}
	}
	
	private void setButtons(boolean b) {
		open.setEnabled(b);
		physics.setEnabled(b);
		run.setEnabled(b);
		dtime.setEnabled(b);
		steps.setEnabled(b);
		exit.setEnabled(b);
	}
	// SimulatorObserver methods
	// ...
	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
		dtime.setText(Double.toString(dt));
	}
	@Override
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		dtime.setText(Double.toString(dt));
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
		dtime.setText(Double.toString(dt));
	}
	@Override
	public void onForceLawsChanged(String fLawsDesc) {
		// TODO Auto-generated method stub
		
	}

}
