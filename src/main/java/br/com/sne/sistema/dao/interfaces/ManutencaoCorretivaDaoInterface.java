package br.com.sne.sistema.dao.interfaces;

import java.util.List;

import br.com.sne.sistema.bean.Equipamento;
import br.com.sne.sistema.bean.ManutencaoCorretiva;

public interface ManutencaoCorretivaDaoInterface {

	public abstract void atualizarManutencaoCorretiva(
			ManutencaoCorretiva transientObject);

	public abstract ManutencaoCorretiva carregarManutencaoCorretiva(long id)
			;

	public abstract List<ManutencaoCorretiva> listarManutencaoCorretiva()
			;

	public abstract List<ManutencaoCorretiva> listarManutencaoCorretivaExcluidos()
			;

	public abstract ManutencaoCorretiva localizarManutencaoCorretivaPorEquipamento(
			Equipamento eq);

	public abstract List<ManutencaoCorretiva> localizarManutencaoCorretivaPorAssistencia(
			String ass);

	public abstract void removerManutencaoCorretiva(
			ManutencaoCorretiva persistentObject);

	public abstract void restaurarManutencaoCorretiva(
			ManutencaoCorretiva persistentObject);

	public abstract void salvarManutencaoCorretiva(
			ManutencaoCorretiva newInstance);

}