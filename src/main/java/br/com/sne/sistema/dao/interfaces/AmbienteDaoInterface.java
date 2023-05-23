package br.com.sne.sistema.dao.interfaces;

import java.util.List;

import br.com.sne.sistema.bean.Ambiente;

public interface AmbienteDaoInterface {

	public void atualizarAmbiente(Ambiente transientObject) ;
	
	public Ambiente carregarAmbiente(long id) ;

	public List<Ambiente> listarAmbientes()  ;

	public void removerAmbiente(Ambiente persistentObject)  ;

	public void salvarAmbiente(Ambiente newInstance)  ;

	public abstract void restaurarAmbiente(Ambiente persistentObject) ;
	

}