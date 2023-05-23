package br.com.sne.sistema.gui.util.form;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JDialog;

import br.com.sne.sistema.gui.util.components.JMoedaRealTextField;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;

public class FloatChooserDialog extends JDialog implements PropertyChangeListener {
	private static final long serialVersionUID = 1L;
	private JMoedaRealTextField fieldValor;
	private JOptionPane optionPane;
	private BigDecimal valorEscolhido;

	private String btnString1 = "Ok";
	private String btnString2 = "Cancelar";

	public Double getValor() {
		try {
			return fieldValor.getValor().doubleValue();
		} catch (Exception e) {
			return null;
		}
	}

	public FloatChooserDialog(String title, String message) {
		super();
		setTitle(title);
		setSize(400, 140);   
		setIconImage((new ImageIcon(getClass().getResource("/images/Money-18x18.png"))).getImage());
		setModalityType(DEFAULT_MODALITY_TYPE);
		setLocationRelativeTo(null);

		fieldValor = new JMoedaRealTextField();
		Object[] array = {message, fieldValor};
		Object[] options = {btnString1, btnString2};

		optionPane = new JOptionPane(array,
				JOptionPane.QUESTION_MESSAGE,
				JOptionPane.OK_CANCEL_OPTION,
				null,
				options);

		setContentPane(optionPane);
		
		optionPane.addPropertyChangeListener(this);
	}



	public BigDecimal showDialog(Component comp) {
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
				valorEscolhido = fieldValor.getValor();
				clearAndHide();
			} else {
				valorEscolhido = null;
				clearAndHide();
			}
		}
	}

	public void clearAndHide() {
		fieldValor.setValor(null);
		setVisible(false);
	}
}