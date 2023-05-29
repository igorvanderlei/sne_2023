package br.com.sne.sistema.gui.util.form;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.com.sne.sistema.auth.AccessManager.permission;
import br.com.sne.sistema.bean.Cliente;
import br.com.sne.sistema.bean.Fornecedor;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.fornecedor.FornecedorTableModel;

public class DialogSearchFornecedor extends DefaultFilterSearchDialog<Fornecedor, FornecedorTableModel>{
	private static final long serialVersionUID = 1L;

	public DialogSearchFornecedor(Component comp) {
		super(comp, "Localizar Fornecedor", new FornecedorTableModel());
	}
	
	protected List<Fornecedor> listAll() {
		List<Fornecedor> lista;
		
		int campo = getJComboBoxFiltro().getSelectedIndex();
		String texto = getJTextFielBusca().getText();
		
		if(campo == 0 || texto.trim().length() == 0) {
			JOptionPane.showMessageDialog(this, "Selecione uma opção de filtro para listar os dados", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
			return new ArrayList<Fornecedor>();
		}
		
		lista = Facade.getInstance().listarFornecedores();
		
		return lista;
	}

	@Override
	public Fornecedor init(Fornecedor value) {
		Facade.getInstance().beginTransaction();
		Fornecedor f = Facade.getInstance().carregarFornecedor(value.getId());
		f.getDadosBancarios1();
		f.getDadosBancarios2();
		Facade.getInstance().commit();
		
		return f;
	}
	
	
}
