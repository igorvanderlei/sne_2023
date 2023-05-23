package br.com.sne.sistema.dao.interfaces;

import java.util.List;
import br.com.sne.sistema.bean.Grupo;
import br.com.sne.sistema.bean.RecursoTerceirizado;

public interface RecursoTerceirizadoDaoInterface {

	public void atualizarRecursoTerceirizado(RecursoTerceirizado transientObject) ;
	
	public RecursoTerceirizado carregarRecursoTerceirizado(long id) ;

	public List<RecursoTerceirizado> listarRecursoTerceirizados()  ;

	public void removerRecursoTerceirizado(RecursoTerceirizado persistentObject)  ;

	public void salvarRecursoTerceirizado(RecursoTerceirizado newInstance)  ;

	public List<RecursoTerceirizado> listarRecursoTerceirizado(Grupo grp)  ;
	 
	public List<RecursoTerceirizado> listarRecursoTerceirizadosAtivos()  ;

	public abstract void restaurarRecursoTerceirizado(RecursoTerceirizado persistentObject) ;

	public abstract List<RecursoTerceirizado> listarRecursoTerceirizadosExcluidos() ;
	

}