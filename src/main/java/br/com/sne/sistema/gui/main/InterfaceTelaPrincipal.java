package br.com.sne.sistema.gui.main;

import javax.swing.JDesktopPane;
import javax.swing.JLabel;

public interface InterfaceTelaPrincipal {

	public JDesktopPane getDesktop();

	/* ----------------------------------------------
	 * Configurações de Segurança para os botões ---*
	 * ---------------------------------------------*/
	public void habilitaBotoes();

	public void desabilitaBotoes();

	public abstract JLabel getLogomarca();

}