package br.com.sne.sistema.gui.util.form;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.com.sne.sistema.auth.AccessManager.permission;
import br.com.sne.sistema.bean.AmbienteEvento;
import br.com.sne.sistema.bean.Orcamento;
import br.com.sne.sistema.bean.OrdemServico;
import br.com.sne.sistema.bean.RecursoSolicitado;
import br.com.sne.sistema.bean.RecursoTerceirizadoSolicitado;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.receita.OrdemServicoTableModel;

public class DialogSearchOS extends DefaultFilterSearchDialog<OrdemServico, OrdemServicoTableModel>{
	private static final long serialVersionUID = 1L;

	public DialogSearchOS(Component comp) {
		super(comp,  "Localizar OS", new OrdemServicoTableModel());
	}

	protected List<OrdemServico> listAll() {
		List<OrdemServico> lista = null;
		
		int campo = getJComboBoxFiltro().getSelectedIndex();
		String texto = getJTextFielBusca().getText();
		
		if(campo == 0 || texto.trim().length() == 0) {
			JOptionPane.showMessageDialog(this, "Selecione uma opção de filtro para listar os dados", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
			return new ArrayList<OrdemServico>();
		}
		
		if(Facade.getInstance().getUsuarioLogado().getTipoUsuario().getPermissao().contains(permission.LISTAR_TODAS_OS))
			lista = Facade.getInstance().listarOrdemServicos(campo,texto); //(campo, texto);
		else
			lista = Facade.getInstance().listarOrdemServicos(campo,texto, Facade.getInstance().getUsuarioLogado().getFuncionario());
		
		return lista;
	}

	@Override
	public OrdemServico init(OrdemServico value) {
		Facade.getInstance().beginTransaction();
		OrdemServico os = Facade.getInstance().carregarOrdemServico(value.getId());
		for(AmbienteEvento a : os.getAmbientes())
			a.getNome();
		for(RecursoSolicitado rec : os.getRecursoSolicitado())
			rec.getDescricao();
		for(RecursoTerceirizadoSolicitado rec : os.getRecursoTerceirizadoSolicitado())
			rec.getDescricao();
		os.getCliente().getCnpj();
		
		os.getLocal().getLocal();
		os.getVendedorConjunto();
		
		Facade.getInstance().commit();
		// TODO Auto-generated method stub
		return os;
	}
}
