package Caja;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

//Creamos clase para cambiar el color de las fila y para centrar el texto

public class CambioColor extends DefaultTableCellRenderer {

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		if (column <= 1) { // alineamos columnas
			setHorizontalAlignment(SwingConstants.LEFT);
		} else {
			setHorizontalAlignment(SwingConstants.CENTER);
		}

		if (row % 2 == 0) { // Damos color a las filas
			setBackground(Color.LIGHT_GRAY);
		} else {
			setBackground(Color.WHITE);
		}

		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	}

}
