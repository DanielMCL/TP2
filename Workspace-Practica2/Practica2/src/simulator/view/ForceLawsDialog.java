package simulator.view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.json.JSONObject;

import simulator.misc.Vector2D;

import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ForceLawsDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private int _status;
	private JsonTableModel _dataTableModel;
	private JComboBox _combo;
	private List<JSONObject> _forceLawsInfo;
	private int _selectedLawsIndex;

	// This table model stores internally the content of the table. Use
	// getData() to get the content as JSON.
	//
	private class JsonTableModel extends AbstractTableModel {
		
		private static final long serialVersionUID = 1L;
		private static final String G_DESC = "The gravitational constant (a number) ";
		private static final String GR_DESC = "The length of the acceleration vector (a number)";
		private static final String C_DESC = "The point towards which bodies move (a json list of 2 numbers, e.g. [100.0, 50.0]";

		private String[] _header = { "Key", "Value", "Description" };
		String[][] _data;

		JsonTableModel() {
			_data = new String[_forceLawsInfo.get(_selectedLawsIndex).getJSONObject("data").keySet().size()][3];
			// inicializamos con la cadena vacía
			initTable();

		}

		public void clear() {
			for (int i = 0; i < _data.length; i++)
				for (int j = 0; j < _data[i].length; j++)
					_data[i][j] = "";
			fireTableStructureChanged();
		}
		
		public void initTable() {
			if (_selectedLawsIndex == 0) {
				setValueAt("G", 0, 0);
				setValueAt(6.67E-11, 0, 1);
				setValueAt(G_DESC, 0, 2);
			}
			else if (_selectedLawsIndex == 1) {
				setValueAt("c", 0, 0);
				setValueAt(new Vector2D(0, 0), 0, 1);
				setValueAt(C_DESC, 0, 2);
				
				setValueAt("g", 1, 0);
				setValueAt(9.81, 1, 1);
				setValueAt(GR_DESC, 1, 2);
			}
			
		}

		@Override
		public String getColumnName(int column) {
			return _header[column];
		}

		@Override
		public int getRowCount() {
			return _data.length;
		}

		@Override
		public int getColumnCount() {
			return _header.length;
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			if (columnIndex == 1) return true;
			else return false;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return _data[rowIndex][columnIndex];
		}

		@Override
		public void setValueAt(Object o, int rowIndex, int columnIndex) {
			_data[rowIndex][columnIndex] = o.toString();
		}

		// Method getData() returns a String corresponding to a JSON structure
		// with column 1 as keys and column 2 as values.

		// This method return the coIt is important to build it as a string, if
		// we create a corresponding JSONObject and use put(key,value), all values
		// will be added as string. This also means that if users want to add a
		// string value they should add the quotes as well as part of the
		// value (2nd column).
		//
		public String getData() {
			StringBuilder s = new StringBuilder();
			s.append("type" + '"' + " : " + _forceLawsInfo.get(_selectedLawsIndex).getString("type") + '"' + ", ");
			s.append('"' + "data" + '"' + " : ");
			s.append('{');
			for (int i = 0; i < _data.length; i++) {
				// Si hay datos en las dos columnas
				if (!_data[i][0].isEmpty() && !_data[i][1].isEmpty() && !_data[i][2].isEmpty()) {
					s.append('"');
					s.append(_data[i][0]);
					s.append('"');
					s.append(':');
					s.append(_data[i][1]);
					s.append(',');
				}
			}
			// Quita la "," y pone la "}"
			if (s.length() > 1)
				s.deleteCharAt(s.length() - 1);
			s.append("} ");
			s.append('"' + "desc" + " : " + _forceLawsInfo.get(_selectedLawsIndex).getString("desc"));
			return s.toString();
		}
	}

	ForceLawsDialog(Frame frame, List<JSONObject> flInfo) {
		super(frame, true);
		_forceLawsInfo = flInfo;
		initGUI();
	}

	
	


	private void initGUI() {

		_status = 0;

		this.setTitle("Force Laws Selection");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		this.setContentPane(mainPanel);
		
		JLabel label = new JLabel();
		String[] laws = new String[_forceLawsInfo.size()]; int i = 0;
		for (JSONObject jo: _forceLawsInfo) {
			laws[i] = jo.getString("desc");
			i++;
		}
		JComboBox <String> combo = new JComboBox<String>(laws);
		combo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				label.setText((String) combo.getSelectedItem());
				_selectedLawsIndex = combo.getSelectedIndex();
				_dataTableModel.clear();
				_dataTableModel.initTable();
			}
		});
		
		combo.setAlignmentX(CENTER_ALIGNMENT);
		combo.setAlignmentY(CENTER_ALIGNMENT);
		mainPanel.add(combo);

		// help
		JLabel help = new JLabel("Select a force law and provide values for the parameters"
				+ " in the value column (default values are used for the parameters with no value");

		help.setAlignmentX(CENTER_ALIGNMENT);
		help.setAlignmentY(TOP_ALIGNMENT);
		mainPanel.add(help);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		// data table
		
		_dataTableModel = new JsonTableModel();
		JTable dataTable = new JTable(_dataTableModel) {
			private static final long serialVersionUID = 1L;
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				// La siguiente línea me da el componente da la celda row,column
				Component component = super.prepareRenderer(renderer, row, column);

				// La siguiente línea me da el ancho del contenido de la celda
				int rendererWidth = component.getPreferredSize().width;

				// Cojo la columna
				TableColumn tableColumn = getColumnModel().getColumn(column);

				// Y el tamaño será el máximo entre el contenido y el tamaño actual
				tableColumn.setPreferredWidth(Math.max(rendererWidth, tableColumn.getPreferredWidth()));
				return component;
			}
		};
		
		JScrollPane tabelScroll = new JScrollPane(dataTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mainPanel.add(tabelScroll);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		// bottons
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setAlignmentX(CENTER_ALIGNMENT);
		buttonsPanel.setAlignmentY(BOTTOM_ALIGNMENT);
		mainPanel.add(buttonsPanel);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_status = 0;
				ForceLawsDialog.this.setVisible(false);
			}
		});
		buttonsPanel.add(cancelButton);

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_status = 1;
				ForceLawsDialog.this.setVisible(false);
			}
		});
		buttonsPanel.add(okButton);

		this.setPreferredSize(new Dimension(400, 400));

		this.pack();
		this.setResizable(true); // change to 'true' if you want to allow resizing
		this.setVisible(false); // we will show it only whe open is called
	}

	public int open() {

		if (getParent() != null)
			setLocation(getParent().getLocation().x + 50, getParent().getLocation().y + 50);
		pack();
		setVisible(true);
		return _status;
	}

	public String getJSON() {
		return _dataTableModel.getData();
	}

}

