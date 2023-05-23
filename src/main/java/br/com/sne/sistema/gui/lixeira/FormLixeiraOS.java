package br.com.sne.sistema.gui.lixeira;

import java.util.List;

import br.com.sne.sistema.bean.OrdemServico;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.os.OrdemServicoTableModel;
import br.com.sne.sistema.gui.util.form.DefaultGarbageForm;

public class FormLixeiraOS extends DefaultGarbageForm<OrdemServico, OrdemServicoTableModel>{
	private static final long serialVersionUID = 1L;

	public FormLixeiraOS () {
		super(new OrdemServicoTableModel(), "/images/icon_trash_18.png", "Ordens de Serviço Excluídas");
	}

	public List<OrdemServico> listAll() {
		return Facade.getInstance().listarOrdemServicosExcluidas();
	}

	public boolean restore(OrdemServico current) {
		Facade.getInstance().restaurarOrdemServico(current);
		return true;
	}
	

}
