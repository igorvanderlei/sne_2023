package br.com.sne.sistema.gui.util.components;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;

public class JCnpjField extends JFormattedTextField{
	private static final long serialVersionUID = 1L;
	private MaskFormatter maskCnpj;
	private MaskFormatter maskCpf;
	public JCnpjField() {
		this.setHorizontalAlignment(JFormattedTextField.RIGHT);
		
		try {
			maskCnpj = new MaskFormatter("##.###.###/####-##");
			maskCnpj.setPlaceholderCharacter('0');
			maskCpf = new MaskFormatter("###.###.###-##");
			maskCpf.setPlaceholderCharacter('0');

			setMaskCnpj();
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
	
	public void setMaskCnpj() {
			setValue(null);
			setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(maskCnpj));
	}
	
	public void setMaskCpf() {
			setValue(null);
			setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(maskCpf));
	}

}