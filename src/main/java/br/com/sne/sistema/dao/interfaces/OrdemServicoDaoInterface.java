package br.com.sne.sistema.dao.interfaces;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import br.com.sne.sistema.bean.EquipamentoEnviado;
import br.com.sne.sistema.bean.Funcionario;
import br.com.sne.sistema.bean.OrdemServico;
import br.com.sne.sistema.bean.OrdemServicoSemEquipamento;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.StatusOS;

@Transactional
public interface OrdemServicoDaoInterface {

	public void atualizarOrdemServico(OrdemServico transientObject);
	
	public OrdemServico carregarOrdemServico(long id);

	public List<OrdemServico> listarOrdemServicos(int campo, String texto) ;
	
	public List<OrdemServico> listarOrdemServicosAprovadas(int campo, String texto) ;
	
	public void removerOrdemServico(OrdemServico persistentObject) ;

	public void salvarOrdemServico(OrdemServico newInstance) ;

	public abstract List<OrdemServico> listarOrdemServicos(int campo, String texto,Funcionario f)
			;
	
	public List<OrdemServico> listarOrdemServicosPorEquipamento(long equipamento ) 
			;
	
	public OrdemServico localizaOrdemServicosPorEquipamento(long equipamento) 
			;

	public abstract List<OrdemServico> listarOrdemServicos(Date d)
			;

	public abstract List<OrdemServico> listarOrdemEmAndamento();
	
	public void atualizarEquipamentoEnviado(EquipamentoEnviado transientObject) 
	;

	public abstract void restaurarOrdemServico(OrdemServico persistentObject)
			;

	public abstract List<OrdemServico> listarOrdemServicosExcluidas();

	public abstract List<OrdemServico> listarOrdemServicosPagamento();

	public abstract List<OrdemServico> listarOrdemServicosEstornada();

	public abstract List<OrdemServico> listarOrdemAtiva();

	public abstract List<OrdemServico> listarOrdemServicosEmergencial()
			;

	public abstract List<OrdemServico> listarOrdemServicosEnviarEquipamento()
			;

	public abstract List<OrdemServico> listarOrdemServicosEmergencialAberta()
			;

	public abstract List<OrdemServico> listarOrdemServicosEmergencialAlertaAberto()
			;

	public abstract List<OrdemServicoSemEquipamento> listarOrdemServicoSemEquipamento()
			;

	public abstract List<OrdemServico> listarOrdemServicosPagamento(Date inicio,
			Date fim,Funcionario f,boolean osExtra);
	
	public abstract List<OrdemServico> listarOrdemServicosPagamento(Date inicio,
			Date fim,boolean osExtra);

	public abstract List<OrdemServico> listarOrdemServicosPagamento(Date inicio,
			Date fim, StatusOS status);

	public abstract List<OrdemServico> listarOrdemServicosPagamento(StatusOS status)
			;

	public abstract void apagarOS(Date dataLimite);
	

}