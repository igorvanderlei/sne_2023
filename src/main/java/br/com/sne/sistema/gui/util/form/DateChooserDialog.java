package br.com.sne.sistema.gui.util.form;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JDialog;

import com.toedter.calendar.JDateChooser;

import java.util.Date;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DateChooserDialog extends JDialog implements PropertyChangeListener {
	private static final long serialVersionUID = 1L;
	private JDateChooser fieldData;
	private JOptionPane optionPane;
	private Date dataEscolhida;

	private String btnString1 = "Ok";
	private String btnString2 = "Cancelar";

	public Date getData() {
		return fieldData.getDate();
	}

	public DateChooserDialog(String title, String message) {
		super();
		setTitle(title);
		setSize(400, 140);   
		setIconImage((new ImageIcon(getClass().getResource("/images/calendar.png"))).getImage());
		setModalityType(DEFAULT_MODALITY_TYPE);
		setLocationRelativeTo(null);

		fieldData = new JDateChooser();
		Object[] array = {message, fieldData};
		Object[] options = {btnString1, btnString2};

		optionPane = new JOptionPane(array,
				JOptionPane.QUESTION_MESSAGE,
				JOptionPane.OK_CANCEL_OPTION,
				null,
				options);

		setContentPane(optionPane);
		
		optionPane.addPropertyChangeListener(this);
	}



	public Date showDialog(Component comp) {
		setVisible(true);
		return dataEscolhida;
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
				dataEscolhida = fieldData.getDate();
				clearAndHide();
			} else {
				dataEscolhida = null;
				clearAndHide();
			}
		}
	}

	public void clearAndHide() {
		fieldData.setDate(null);
		setVisible(false);
	}
}