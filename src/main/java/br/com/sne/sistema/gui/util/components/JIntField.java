package br.com.sne.sistema.gui.util.components;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;

public class JIntField extends JTextField {
	private static final long serialVersionUID = 1L;

	public JIntField() {
		super();
		this.addKeyListener(
				new KeyAdapter() {
					public void keyTyped(KeyEvent arg0) {
						if(!Character.isDigit(arg0.getKeyChar())) {
							arg0.consume();
						}
					}
				}
		);
	}
}
