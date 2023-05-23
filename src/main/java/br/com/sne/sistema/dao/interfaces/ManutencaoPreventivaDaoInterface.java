package br.com.sne.sistema.dao.interfaces;

import java.util.List;

import br.com.sne.sistema.bean.Equipamento;
import br.com.sne.sistema.bean.Funcionario;
import br.com.sne.sistema.bean.ManutencaoPreventiva;

public interface ManutencaoPreventivaDaoInterface {

	public abstract void atualizarManutencaoPreventiva(
			ManutencaoPreventiva transientObject) ;

	public abstract ManutencaoPreventiva carregarManutencaoPreventiva(long id)
			;

	public abstract List<ManutencaoPreventiva> listarManutencaoPreventivas()
			;

	public abstract List<ManutencaoPreventiva> listarManutencaoPreventivasExcluidos()
			;

	public abstract ManutencaoPreventiva localizarManutencaoPreventivaPorEquipamento(
			Equipamento eq) ;

	public abstract List<ManutencaoPreventiva> localizarManutencaoPreventivaPorTecnico(
			Funcionario tec) ;

	public abstract void removerManutencaoPreventiva(
			ManutencaoPreventiva persistentObject) ;

	public abstract void restaurarManutencaoPreventiva(
			ManutencaoPreventiva persistentObject) ;

	public abstract void salvarManutencaoPreventiva(
			ManutencaoPreventiva newInstance) ;

}