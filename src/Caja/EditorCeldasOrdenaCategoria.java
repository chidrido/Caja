package Caja;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class EditorCeldasOrdenaCategoria extends AbstractCellEditor implements TableCellEditor, ActionListener {

	Boolean currentValue;
	JButton button;
	protected static final String EDIT = "edit";

	public EditorCeldasOrdenaCategoria(JTable jTable) {
		button = new JButton();
		button.setActionCommand(EDIT);
		button.addActionListener(this);
		button.setBorderPainted(false);
	}

	public void actionPerformed(ActionEvent e) {

		fireEditingStopped();
	}

	public Object getCellEditorValue() {
		return currentValue;
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

		if (row <= table.getModel().getRowCount()) {
			// currentValue = (Boolean) value;
			return button;
		}
		return new JLabel();

	}

}
