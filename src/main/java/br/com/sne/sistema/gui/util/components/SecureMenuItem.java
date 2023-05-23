package br.com.sne.sistema.gui.util.components;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JMenuItem;

import br.com.sne.sistema.auth.AccessManager.permission;

public class SecureMenuItem extends JMenuItem implements SecureComponent {
	private static final long serialVersionUID = 1L;

	public SecureMenuItem() {
		super();
	}

	public SecureMenuItem(Action arg0) {
		super(arg0);
	}

	public SecureMenuItem(Icon arg0) {
		super(arg0);
	}

	public SecureMenuItem(String arg0, Icon arg1) {
		super(arg0, arg1);
	}

	public SecureMenuItem(String arg0, int arg1) {
		super(arg0, arg1);
	}

	public SecureMenuItem(String arg0) {
		super(arg0);
	}

	private permission permission;

	public permission getPermission() {
		return permission;
	}

	public void setPermission(permission permission) {
		this.permission = permission;
	}

	
}
