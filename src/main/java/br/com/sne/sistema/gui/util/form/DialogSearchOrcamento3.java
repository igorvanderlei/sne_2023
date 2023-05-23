package br.com.sne.sistema.gui.util.form;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import br.com.sne.sistema.auth.AccessManager.permission;
import br.com.sne.sistema.bean.AmbienteEvento;
import br.com.sne.sistema.bean.Orcamento;
import br.com.sne.sistema.bean.RecursoSolicitado;
import br.com.sne.sistema.bean.RecursoTerceirizadoSolicitado;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.orcamento.OrcamentoTableModel;

public class DialogSearchOrcamento3 extends DefaultFilterSearchDialog<Orcamento, OrcamentoTableModel>{
	private static final long serialVersionUID = 1L;

	public DialogSearchOrcamento3(Component comp) {
		super(comp,  "Localizar Orçamento", new OrcamentoTableModel());
	}

	protected List<Orcamento> listAll() {
		List<Orcamento> lista;
		
		int campo = getJComboBoxFiltro().getSelectedIndex();
		String texto = getJTextFielBusca().getText();
		
		if(campo == 0 || texto.trim().length() == 0) {
			JOptionPane.showMessageDialog(this, "Selecione uma opção de filtro para listar os dados", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
			return new ArrayList<Orcamento>();
		}
		
		if(Facade.getInstance().getUsuarioLogado().getTipoUsuario().getPermissao().contains(permission.LISTAR_TODOS_ORCAMENTOS)){
			lista = Facade.getInstance().listarOrcamentos(campo, texto);
		} else {
			lista = Facade.getInstance().listarOrcamentos(campo, texto, Facade.getInstance().getUsuarioLogado().getFuncionario());
		}
		
		
		
		List<Orcamento> listaFiltrada = new ArrayList<Orcamento>();
		List<Long> excluirID = new ArrayList<Long>();
		
		for(Orcamento o : lista) {
			if(!excluirID.contains(o.getId()) && o.getOrcOriginal() != null && !excluirID.contains(o.getIdPai())) {
				listaFiltrada.add(o);
				if(o.getIdPai() != 0)
					excluirID.add(o.getIdPai());
			} else {
				System.out.println("Excluido: " + o.getId() + " : " + o.getIdPai() + " : " + o.getDataOrcamento());
			}
		}
		
		
		return listaFiltrada;		
		
/*		listaVerdadeira.addAll(lista);
		
		for(Orcamento o: lista) {
			long id = o.getId();
			Date dataOrc = o.getDataOrcamento();
			for(Orcamento o2: lista) {
				System.out.println(o.getId() +" ERRO "+ o2.getId());
				Date dataOrcPai = o2.getDataOrcamento();
				long idPai = o2.getIdPai();
				if(id == idPai) {
					if(dataOrcPai != null && dataOrc != null) {
						if(dataOrcPai.after(dataOrc)) {
							listaVerdadeira.remove(o);
						}
						else if(dataOrcPai.equals(dataOrc) && 
								o.getId() < o2.getId()) {
							listaVerdadeira.remove(o);
						}
					}
				}
			}
		}
		
		return listaVerdadeira;*/
	}

	@Override
	public Orcamento init(Orcamento value) {
		Facade.getInstance().beginTransaction();
		Orcamento orc = Facade.getInstance().carregarOrcamento(value.getId());
		for(AmbienteEvento a : orc.getAmbientes())
			a.getNome();
		for(RecursoSolicitado rec : orc.getRecursoSolicitado())
			rec.getDescricao();
		for(RecursoTerceirizadoSolicitado rec : orc.getRecursoTerceirizadoSolicitado())
			rec.getDescricao();
		orc.getCliente().getCnpj();
		
		orc.getLocal().getLocal();
				
		Facade.getInstance().commit();
		return orc;
	}
	
	
	
}
