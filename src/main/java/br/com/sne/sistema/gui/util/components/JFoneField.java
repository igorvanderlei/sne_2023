package br.com.sne.sistema.gui.util.components;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;

public class JFoneField extends JFormattedTextField {
	private static final long serialVersionUID = 1L;

	public JFoneField() {
		this.setHorizontalAlignment(JFormattedTextField.RIGHT);
/*		try {
			MaskFormatter maskTelefone = new MaskFormatter("(##)####-####");
			this.setHorizontalAlignment(JFormattedTextField.RIGHT);  
			maskTelefone.setPlaceholder("0000000000000");
			maskTelefone.install(this);
		}catch (ParseException e) { }
		addFocusListener(
				new FocusAdapter() {
					public void focusGained(FocusEvent e) {
						setCaretPosition(0);
						super.focusGained(e);
					}
					
				}
		);*/
	}
}
