package Caja;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class CambioColorAgenda extends DefaultTableCellRenderer {
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		if (column == 1) { // alineamos columnas
			setHorizontalAlignment(SwingConstants.CENTER);
		} else {
			setHorizontalAlignment(SwingConstants.LEFT);
		}
		
		if (row % 2 == 0) { // Damos color a las filas
			setBackground(Color.LIGHT_GRAY);
		} else {
			setBackground(Color.WHITE);
		}

		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	}


}
