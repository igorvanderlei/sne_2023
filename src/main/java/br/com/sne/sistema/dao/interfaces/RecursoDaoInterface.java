package br.com.sne.sistema.dao.interfaces;

import java.util.List;
import br.com.sne.sistema.bean.Grupo;
import br.com.sne.sistema.bean.Recurso;

public interface RecursoDaoInterface {

	public void atualizarRecurso(Recurso transientObject);
	
	public Recurso carregarRecurso(long id);

	public List<Recurso> listarRecursos() ;

	public void removerRecurso(Recurso persistentObject) ;

	public void salvarRecurso(Recurso newInstance) ;

	public List<Recurso> listarRecurso(Grupo grp) ;
	 
	public List<Recurso> listarRecursosAtivos() ;

	public abstract void restaurarRecurso(Recurso persistentObject);

	public abstract List<Recurso> listarRecursosExcluidos();
	

}