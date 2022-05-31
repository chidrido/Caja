package Caja;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class EditorCeldasFruta extends AbstractCellEditor implements TableCellEditor, ActionListener {

	Boolean currentValue;
	JCheckBox checkbox;
	protected static final String EDIT = "edit";

	public EditorCeldasFruta(JTable jTable) {
		checkbox = new JCheckBox();
		checkbox.setActionCommand(EDIT);
		checkbox.addActionListener(this);
		checkbox.setBorderPainted(false);
	}

	public void actionPerformed(ActionEvent e) {

		fireEditingStopped();
	}

	public Object getCellEditorValue() {
		return currentValue;
	}

	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

		if (value instanceof Boolean) {
			checkbox.setSelected((Boolean) value);
		}

		if (row <= table.getModel().getRowCount() - 1) {
			currentValue = (Boolean) value;
			return checkbox;
		}
		return new JCheckBox();

	}
}
