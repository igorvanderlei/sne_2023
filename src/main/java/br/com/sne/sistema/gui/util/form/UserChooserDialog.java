package br.com.sne.sistema.gui.util.form;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JDialog;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import br.com.sne.sistema.bean.Usuario;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.util.components.JMoedaRealTextField;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class UserChooserDialog extends JDialog implements PropertyChangeListener {
	private static final long serialVersionUID = 1L;
	private JTextField fieldUsuario;
	private JPasswordField fieldSenha;
	
	private JOptionPane optionPane;
	private Usuario valorEscolhido;

	private String btnString1 = "Ok";
	private String btnString2 = "Cancelar";

	public Usuario getValor() {
		try {
			Usuario u = Facade.getInstance().localizarUsuarioPorLogin(fieldUsuario.getText());
			if(u!=null && u.verificarPassword(new String(fieldSenha.getPassword()))) {
					return u;
			}
		} catch (Exception e) { }
		return null;
	}

	public UserChooserDialog(String title, String message) {
		super();
		setTitle(title);
		setSize(400, 220);   
		setIconImage((new ImageIcon(getClass().getResource("/images/Money-18x18.png"))).getImage());
		setModalityType(DEFAULT_MODALITY_TYPE);
		setLocationRelativeTo(null);

		fieldUsuario = new JTextField();
		fieldSenha = new JPasswordField();
		Object[] array = {message, "Usuário:", fieldUsuario, "Senha: ", fieldSenha};
		Object[] options = {btnString1, btnString2};

		optionPane = new JOptionPane(array,
				JOptionPane.QUESTION_MESSAGE,
				JOptionPane.OK_CANCEL_OPTION,
				null,
				options);

		setContentPane(optionPane);
		
		optionPane.addPropertyChangeListener(this);
	}



	public Usuario showDialog(Component comp) {
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
				valorEscolhido = getValor();
				clearAndHide();
			} else {
				valorEscolhido = null;
				clearAndHide();
			}
		}
	}

	public void clearAndHide() {
		fieldUsuario.setText("");
		fieldSenha.setText("");
		setVisible(false);
	}
}