package br.com.sne.sistema.gui.util.components;

import java.awt.CardLayout;
import javax.swing.JComponent;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

public class TitledPanel extends JComponent {
	private static final long serialVersionUID = 1L;

	public TitledPanel (String title, JComponent content) {
		super();
		this.setLayout(new CardLayout());
		this.setBorder(				
				new TitledBorder(new EmptyBorder(0,0,0,0), title, 
								 TitledBorder.LEADING, TitledBorder.TOP, null));
		this.add(content, title);
	}
	
}
