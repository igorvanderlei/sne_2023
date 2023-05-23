package br.com.sne.sistema.dao.interfaces;
import java.util.List;
import java.util.Set;
import br.com.sne.sistema.bean.Equipamento;
import br.com.sne.sistema.bean.EquipamentoEnviado;
import br.com.sne.sistema.bean.Grupo;
import br.com.sne.sistema.bean.Recurso;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.StatusEquipamento;

public interface EquipamentoDaoInterface {

	public void atualizarEquipamento(Equipamento transientObject, String serieAntigo);
	
	public void atualizarEquipamentoDevolucao(Equipamento e);
	
	public Equipamento carregarEquipamento(long id);

	public List<Equipamento> listarEquipamento() ;
	
	public List<Equipamento> listarEquipamentosDisponiveis() ;

	public void removerEquipamento(Equipamento persistentObject) ;

	public void salvarEquipamento(Equipamento newInstance) ;

	public List<Equipamento> listarEquipamentosPorStatus(Set<StatusEquipamento> status);

	public abstract Equipamento localizarEquipamentoCodigo(String codigo)
			;
	
	public EquipamentoEnviado localizarEquipamentoEnviado(String codigo) 
	;

	public abstract List<Equipamento> listarEquipamentosPorGrupo(Grupo g, boolean incluirSublocados);

	public abstract List<Equipamento> listarEquipamentosPorRecurso(Recurso r, boolean incluirSublocados);

	public abstract void restaurarEquipamento(Equipamento persistentObject)
			;

	public abstract List<Equipamento> listarEquipamentosExcluidos();

	public abstract EquipamentoEnviado localizarEquipamentoEnviadoRastrear(String codigo)
			; 

}