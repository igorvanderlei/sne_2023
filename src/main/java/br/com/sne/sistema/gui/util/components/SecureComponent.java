package br.com.sne.sistema.gui.util.components;

import br.com.sne.sistema.auth.AccessManager.permission;

public interface SecureComponent {
	public abstract permission getPermission();
	public abstract void setPermission(permission permission);
	public void setEnabled(boolean status);

}