package br.com.sne.sistema.gui.util.form;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.com.sne.sistema.bean.OrdemServico;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.equipamentoenviado.LocalizarOrdemServicoTableModel;
import br.com.sne.sistema.gui.os.OrdemServicoTableModel;

public class DialogSearchOSExtrasAprovadas extends DefaultFilterSearchDialog<OrdemServico, OrdemServicoTableModel>{
	private static final long serialVersionUID = 1L;

	public DialogSearchOSExtrasAprovadas(Component comp) {
		super(comp,  "Localizar OS", new OrdemServicoTableModel());
	}

	protected List<OrdemServico> listAll() {
		List<OrdemServico> lista;
		List<OrdemServico> listaVerdadeira = new ArrayList<OrdemServico>();

		int campo = getJComboBoxFiltro().getSelectedIndex();
		String texto = getJTextFielBusca().getText();
		
		if(campo == 0 || texto.trim().length() == 0) {
			JOptionPane.showMessageDialog(this, "Selecione uma opção de filtro para listar os dados", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
			return new ArrayList<OrdemServico>();
		}
		
		lista = Facade.getInstance().listarOrdemServicosAprovadas(campo,texto); //(campo, texto);
		
		for(OrdemServico o: lista) {
			if(o.getOSOriginal() != null)
				listaVerdadeira.add(o);
		}
		
		return listaVerdadeira;
	}
}
