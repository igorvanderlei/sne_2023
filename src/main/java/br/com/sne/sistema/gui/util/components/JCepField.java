package br.com.sne.sistema.gui.util.components;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;

public class JCepField extends JFormattedTextField{
	private static final long serialVersionUID = 1L;

	public JCepField() {
		try {
			MaskFormatter maskTelefone = new MaskFormatter("##.###-###");
			this.setHorizontalAlignment(JFormattedTextField.RIGHT);  
			maskTelefone.setPlaceholder("00000000000");
			maskTelefone.install(this);
		}catch (ParseException e) { }
		addFocusListener(
				new FocusAdapter() {
					public void focusGained(FocusEvent e) {
						setCaretPosition(0);
						super.focusGained(e);
					}
					
				}
		);
		
	}

}
