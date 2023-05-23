package br.com.sne.sistema.gui.util.components;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.ibm.icu.text.DateFormat;

import br.com.sne.sistema.gui.util.form.ZebraDecorator;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateCellRenderer2 extends ZebraDecorator{
	private static final long serialVersionUID = 1L;
	private static SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM");
	
	public Component getTableCellRendererComponent(JTable pTable, Object pValue, boolean pIsSelected, boolean pHasFocus, int pRow, int pColumn) {
        Component renderedComponent = super.getTableCellRendererComponent(pTable, pValue, pIsSelected, pHasFocus, pRow, pColumn);

        if (pValue instanceof Date){
            Date d = (Date) pValue;
            String s = formato.format(d); 
            renderedComponent = super.getTableCellRendererComponent(pTable, s, pIsSelected, pHasFocus, pRow, pColumn);
            ((DefaultTableCellRenderer) renderedComponent).setHorizontalAlignment(JLabel.CENTER);
        }
        return renderedComponent;
    }

}
