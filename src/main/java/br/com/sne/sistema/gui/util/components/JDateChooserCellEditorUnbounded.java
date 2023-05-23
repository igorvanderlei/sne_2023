package br.com.sne.sistema.gui.util.components;

import java.awt.Component;
import javax.swing.JTable;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JDateChooserCellEditor;

public class JDateChooserCellEditorUnbounded extends JDateChooserCellEditor{
	private static final long serialVersionUID = 1L;

	public JDateChooserCellEditorUnbounded() {
		super();
	}

	public Component getTableCellEditorComponent(JTable arg0, Object arg1,
			boolean arg2, int arg3, int arg4) {
		JDateChooser editor = (JDateChooser) super.getTableCellEditorComponent(arg0, arg1, arg2, arg3, arg4);
		return editor;
	}
	
	

}
