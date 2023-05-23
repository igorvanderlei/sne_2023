package br.com.sne.sistema.dao.interfaces;

import java.util.List;

import br.com.sne.sistema.bean.LocalEvento;
import br.com.sne.sistema.bean.SalaLocal;

public interface SalaLocalDaoInterface {

	public void atualizarSalaLocal(SalaLocal transientObject) ;
	
	public SalaLocal carregarSalaLocal(long id) ;

	public List<SalaLocal> listarSalaLocals()  ;

	public List<SalaLocal> listarSalaLocalsPorLocalEvento(LocalEvento localEvento)  ;

	public void removerSalaLocal(SalaLocal persistentObject)  ;

	public void salvarSalaLocal(SalaLocal newInstance)  ;

	public abstract void restaurarSalaLocal(SalaLocal persistentObject) ;
	

}