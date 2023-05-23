package br.com.sne.sistema.gui.main;

import javax.swing.JOptionPane;

import br.com.sne.sistema.facade.Facade;

public class AtualizarBanco {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Facade.getInstance().listarUsuarios();
		JOptionPane.showMessageDialog(null, "Banco de dados atualizado com sucesso");

	}

}
