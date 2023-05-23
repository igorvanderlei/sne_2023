package br.com.sne.sistema.gui.util.components;

import javax.swing.JComboBox;

public class JComboEstado extends JComboBox{
	private static final long serialVersionUID = 1L;

	public JComboEstado() {
		this.addItem(" ");
		this.addItem("AC");
		this.addItem("AL");
		this.addItem("AM");
		this.addItem("AP");
		this.addItem("BA");
		this.addItem("CE");
		this.addItem("DF");
		this.addItem("GO");
		this.addItem("MA");
		this.addItem("MG");
		this.addItem("MS");
		this.addItem("MT");
		this.addItem("PA");
		this.addItem("PB");
		this.addItem("PE");
		this.addItem("PI");
		this.addItem("PR");
		this.addItem("RJ");
		this.addItem("RN");
		this.addItem("RO");
		this.addItem("RR");
		this.addItem("RS");
		this.addItem("SC");
		this.addItem("SE");
		this.addItem("SP");
		this.addItem("TO");
		this.setSelectedItem("SP");
	}
}
