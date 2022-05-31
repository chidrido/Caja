package Caja;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class Renderizado extends JLabel implements TableCellRenderer {

	boolean isBordered = true;

	public Renderizado(boolean isBordered) {
		this.isBordered = isBordered;
		setOpaque(true);
	}

	public Component getTableCellRendererComponent(JTable table, Object color, boolean isSelected, boolean hasFocus,
			int row, int column) {

		if (row <= table.getModel().getRowCount() - 2) {
			return new JButton("Borrar");
		}
		return this;

	}
}
