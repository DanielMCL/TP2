package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import simulator.control.Controller;

public class BodiesTable extends JPanel {
	private static final long serialVersionUID = 1L;

	public BodiesTable(Controller ctrl) {
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder(
		BorderFactory.createLineBorder(Color.black, 2),
		"Bodies",
		TitledBorder.LEFT, TitledBorder.TOP));

		BodiesTableModel tableModel = new BodiesTableModel(ctrl);
		JTable table = new JTable(tableModel);
		table.setBackground(Color.white);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.getViewport().setBackground(Color.white);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		this.add(scrollPane);
	}
}
