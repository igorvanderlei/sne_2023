package br.com.sne.sistema.gui.util.components;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import br.com.sne.sistema.gui.util.form.ZebraDecorator;

public class IntegerCellRenderer extends ZebraDecorator{
	private static final long serialVersionUID = 1L;

	public Component getTableCellRendererComponent(JTable pTable, Object pValue, boolean pIsSelected, boolean pHasFocus, int pRow, int pColumn) {
        Component renderedComponent = super.getTableCellRendererComponent(pTable, pValue, pIsSelected, pHasFocus, pRow, pColumn);
            String s ="" + pValue; 
            renderedComponent = super.getTableCellRendererComponent(pTable, s, pIsSelected, pHasFocus, pRow, pColumn);
            ((DefaultTableCellRenderer) renderedComponent).setHorizontalAlignment(JLabel.CENTER);
        return renderedComponent;
    }
}
