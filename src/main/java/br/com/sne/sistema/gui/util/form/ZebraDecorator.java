package br.com.sne.sistema.gui.util.form;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ZebraDecorator extends DefaultTableCellRenderer {
	private Color whiteColor = new Color(254, 254, 254);  
	private Color alternateColor = new Color(230, 238, 245);  
	private Color selectedColor = new Color(61, 128, 223);  

	@Override  
	public Component getTableCellRendererComponent(JTable table,  
			Object value, boolean selected, boolean focused, int row,  
			int column) {  

		Component comp = super.getTableCellRendererComponent(table, value, selected, focused, row, column);
		
		if(value instanceof Boolean) {
			System.out.println("Boolean");
			comp = new JCheckBox();
			((JCheckBox) comp).setHorizontalAlignment(JLabel.CENTER);
			((JCheckBox) comp).setSelected((value != null && ((Boolean) value).booleanValue()));
		}

		Color bg;  
		if (!selected)  
			bg = (row % 2 == 0 ? alternateColor : whiteColor);  
		else  
			bg = selectedColor;  

		comp.setBackground(bg);  
		comp.setForeground(selected ? Color.white : Color.black);  

		return comp;  
	}  
	
	/*public class CheckBoxRenderer extends JCheckBox implements TableCellRenderer {

        CheckBoxRenderer() {
          setHorizontalAlignment(JLabel.CENTER);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
          if (isSelected) {
            setForeground(table.getSelectionForeground());
            //super.setBackground(table.getSelectionBackground());
            setBackground(table.getSelectionBackground());
          } else {
            setForeground(table.getForeground());
            setBackground(table.getBackground());
          }
          setSelected((value != null && ((Boolean) value).booleanValue()));
          return this;
        }
}*/
	
	
	
}