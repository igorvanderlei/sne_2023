package br.com.sne.sistema.dao.interfaces;

import java.util.List;

import br.com.sne.sistema.bean.DevolucaoSublocados;

public interface DevolucaoSublocadosDaoInterface {

	public abstract void atualizarDevolucaoSublocados(
			DevolucaoSublocados transientObject) ;

	public abstract DevolucaoSublocados carregarDevolucaoSublocados(long id)
			;

	public abstract List<DevolucaoSublocados> listarDevolucaoSublocadoss()
			;

	public abstract List<DevolucaoSublocados> listarDevolucaoSublocadossExcluidos()
			;

	public abstract void removerDevolucaoSublocados(
			DevolucaoSublocados persistentObject) ;

	public abstract void restaurarDevolucaoSublocados(
			DevolucaoSublocados persistentObject) ;

	public abstract void salvarDevolucaoSublocados(
			DevolucaoSublocados newInstance) ;

}