package br.com.sne.sistema.gui.util.components;

import java.awt.Component;
import java.text.NumberFormat;

import javax.swing.AbstractCellEditor;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class MoedaCellEditor extends AbstractCellEditor implements TableCellEditor {
	private static final long serialVersionUID = 1L;
	JComponent component = new JFormattedTextField(NumberFormat.getCurrencyInstance());

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int rowIndex, int vColIndex) {
        ((JFormattedTextField)component).setText((String)value);
        return component;
    }

    public Object getCellEditorValue() {
        return ((JFormattedTextField)component).getText();
    }
}