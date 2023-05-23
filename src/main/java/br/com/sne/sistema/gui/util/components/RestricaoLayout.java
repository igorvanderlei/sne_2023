package br.com.sne.sistema.gui.util.components;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class RestricaoLayout extends GridBagConstraints{
	private static final long serialVersionUID = 1L;

	public RestricaoLayout(int row, int col, boolean fillRow, boolean fillCol) {
		super();
		this.insets = new Insets(1, 1, 1, 1);
		this.gridx = col;
		this.gridy = row;
		this.ipadx = 40;
		this.ipady = 4;
		this.gridwidth = 1;
		this.gridheight = 1;
		if( fillRow && fillCol) {
			this.fill = GridBagConstraints.BOTH;
		} else {
			if(fillRow) {
				this.fill = GridBagConstraints.HORIZONTAL;
			} else if(fillCol) {
				this.fill = GridBagConstraints.VERTICAL;
			}
		}
	}
	
	public RestricaoLayout(int row, int col) {
		super();
		this.gridx = col;
		this.gridy = row;
		this.ipady = 0;
		this.insets = new Insets(0,0,0,0);
	}
	
	public RestricaoLayout(int row, int col, int width, boolean fillRow, boolean fillCol) {
		this(row, col, fillRow, fillCol);
		this.gridwidth = width;
		this.weightx = 0.1 * width;
	}
	
	public RestricaoLayout(int row, int col, int width, int height, boolean fillRow, boolean fillCol) {
		this(row, col, width, fillRow, fillCol);
		this.gridheight = height;
		this.weighty = 0.1 * height;
	}

}
