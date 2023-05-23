package br.com.sne.sistema.gui.util.components;

import java.awt.CardLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.SoftBevelBorder;

public class BordedPanel extends JPanel{
	private static final long serialVersionUID = 1L;

	public BordedPanel (JComponent content) {
		super();
		this.setLayout(new CardLayout());
		/*this.setBorder(BorderFactory.createCompoundBorder(
							BorderFactory.createCompoundBorder(new SoftBevelBorder(SoftBevelBorder.RAISED), BorderFactory.createBevelBorder(BevelBorder.RAISED)), 
							BorderFactory.createBevelBorder(BevelBorder.LOWERED)));*/
		//this.setBorder(	new SoftBevelBorder(SoftBevelBorder.RAISED));
		this.add(content, "Botao");		
	}
}
