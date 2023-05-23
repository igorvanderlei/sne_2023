package br.com.sne.sistema.gui.util.form;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class StringChooserDialog extends JDialog implements PropertyChangeListener {
	private static final long serialVersionUID = 1L;
	private JTextArea fieldTexto;
	private JOptionPane optionPane;
	private String valorEscolhido;

	private String btnString1 = "Ok";
	private String btnString2 = "Cancelar";

	public String getValor() {
		return fieldTexto.getText();
	}

	public StringChooserDialog(String title, String message) {
		super();
		setTitle(title);
		setSize(400, 340);   
		setIconImage((new ImageIcon(getClass().getResource("/images/Money-18x18.png"))).getImage());
		setModalityType(DEFAULT_MODALITY_TYPE);
		setLocationRelativeTo(null);

		fieldTexto = new JTextArea();
		fieldTexto.setPreferredSize(new Dimension(400,200));
		
		JScrollPane scp = new JScrollPane();
		scp.setPreferredSize(new Dimension(350,200));
		scp.setViewportView(fieldTexto);
		
		Object[] array = {message, scp};
		Object[] options = {btnString1, btnString2};

		optionPane = new JOptionPane(array,
				JOptionPane.QUESTION_MESSAGE,
				JOptionPane.OK_CANCEL_OPTION,
				null,
				options);

		setContentPane(optionPane);
		
		optionPane.addPropertyChangeListener(this);
	}



	public String showDialog(Component comp) {
		setVisible(true);
		return valorEscolhido;
	}


	public void propertyChange(PropertyChangeEvent e) {
		String prop = e.getPropertyName();

		if (isVisible()
				&& (e.getSource() == optionPane)
				&& (JOptionPane.VALUE_PROPERTY.equals(prop) ||
						JOptionPane.INPUT_VALUE_PROPERTY.equals(prop))) {
			Object value = optionPane.getValue();

			if (value == JOptionPane.UNINITIALIZED_VALUE) {
				return;
			}

			optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);

			if (btnString1.equals(value)) {
				valorEscolhido = fieldTexto.getText();
				clearAndHide();
			} else {
				valorEscolhido = null;
				clearAndHide();
			}
		}
	}

	public void clearAndHide() {
		fieldTexto.setText("");
		setVisible(false);
	}
}