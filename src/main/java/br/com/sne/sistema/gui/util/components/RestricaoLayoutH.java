package br.com.sne.sistema.gui.util.components;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class RestricaoLayoutH extends GridBagConstraints{
	private static final long serialVersionUID = 1L;

	public RestricaoLayoutH(int row, int col, boolean fillRow) {
		super();
		this.insets = new Insets(1, 1, 1, 1);
		this.gridx = col;
		this.gridy = row;
		this.ipadx = 40;
		this.ipady = 4;
		this.gridwidth = 1;
		this.gridheight = 1;
		if(fillRow) {
				this.fill = GridBagConstraints.HORIZONTAL;
		}
	}
	
	public RestricaoLayoutH(int row, int col, float weight, boolean fillRow) {
		this(row, col, fillRow);
		this.weightx = weight;
	}

}
