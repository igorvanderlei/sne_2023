package br.com.sne.sistema.dao.interfaces;

import java.util.List;

import br.com.sne.sistema.bean.Equipamento;
import br.com.sne.sistema.bean.Funcionario;
import br.com.sne.sistema.bean.Recolhimento;

public interface RecolhimentoDaoInterface {

	public abstract void atualizarRecolhimento(
			Recolhimento transientObject) ;

	public abstract Recolhimento carregarRecolhimento(long id)
			;

	public abstract List<Recolhimento> listarRecolhimentos()
			;

	public abstract List<Recolhimento> listarRecolhimentosExcluidos()
			;

	public abstract Recolhimento localizarRecolhimentoPorEquipamento(
			Equipamento eq) ;

	public abstract List<Recolhimento> localizarRecolhimentoPorTecnico(
			Funcionario tec) ;

	public abstract void salvarRecolhimento(
			Recolhimento newInstance) ;

}