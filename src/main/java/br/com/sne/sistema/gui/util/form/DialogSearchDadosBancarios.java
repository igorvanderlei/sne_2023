package br.com.sne.sistema.gui.util.form;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.com.sne.sistema.auth.AccessManager.permission;
import br.com.sne.sistema.bean.Cliente;
import br.com.sne.sistema.bean.DadosBancarios;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.cliente.ClienteTableModel;
import br.com.sne.sistema.gui.fornecedor.DadosBancariosTableModel;

public class DialogSearchDadosBancarios extends DefaultFilterSearchDialog<DadosBancarios, DadosBancariosTableModel>{
	private static final long serialVersionUID = 1L;

	public DialogSearchDadosBancarios(Component comp) {
		super(comp,  "Localizar Dados Bancários", new DadosBancariosTableModel());
	}

	protected List<DadosBancarios> listAll() {
		List<DadosBancarios> lista;
		
		int campo = getJComboBoxFiltro().getSelectedIndex();
		String texto = getJTextFielBusca().getText();
		
		if(campo == 0 || texto.trim().length() == 0) {
			JOptionPane.showMessageDialog(this, "Selecione uma opção de filtro para listar os dados", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
			return new ArrayList<DadosBancarios>();
		}
		
		lista = Facade.getInstance().listarDadosBancarios(campo, texto);
		
		
		return lista;
	}

	@Override
	public DadosBancarios init(DadosBancarios value) {
		return value;
	}
	
	
}
