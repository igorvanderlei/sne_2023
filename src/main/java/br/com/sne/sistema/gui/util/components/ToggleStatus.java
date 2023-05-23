package br.com.sne.sistema.gui.util.components;

import javax.swing.ImageIcon;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

public class ToggleStatus extends JToggleButton{
	private static final long serialVersionUID = 1L;

	public ToggleStatus(String title, String image) {
		super("<html><center>" + title + "</center>", new ImageIcon(image));
		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.setHorizontalTextPosition(SwingConstants.CENTER);
		this.setVerticalAlignment(SwingConstants.TOP);
		this.setVerticalTextPosition(SwingConstants.BOTTOM);
		this.setSelected(true);
	}
}
