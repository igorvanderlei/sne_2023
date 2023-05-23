package br.com.sne.sistema.gui.util.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class JFormGroup extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public JFormGroup (String title) {
		super();
		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray, 1), title, TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.BOLD, 12), new Color(51, 51, 51)), BorderFactory.createEmptyBorder (0, 0, 0, 0)));
	}

}
