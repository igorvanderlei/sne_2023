package br.com.sne.sistema.gui.util.components;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import br.com.sne.sistema.gui.util.form.ZebraDecorator;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class MoedaCellRenderer extends ZebraDecorator{
	private static final long serialVersionUID = 1L;
	private static NumberFormat formato  = NumberFormat.getInstance(Locale.getDefault());
	static {
		formato.setMinimumFractionDigits(2);
		formato.setMaximumFractionDigits(2);
	}
	
	public Component getTableCellRendererComponent(JTable pTable, Object pValue, boolean pIsSelected, boolean pHasFocus, int pRow, int pColumn) {
        Component renderedComponent = super.getTableCellRendererComponent(pTable, pValue, pIsSelected, pHasFocus, pRow, pColumn);

        if (pValue instanceof BigDecimal){
            BigDecimal d = (BigDecimal) pValue;
            String s = "R$ " + formato.format(d); 
            renderedComponent = super.getTableCellRendererComponent(pTable, s, pIsSelected, pHasFocus, pRow, pColumn);
        }
        ((DefaultTableCellRenderer) renderedComponent).setHorizontalAlignment(JLabel.RIGHT);        
        return renderedComponent;
    }

}
