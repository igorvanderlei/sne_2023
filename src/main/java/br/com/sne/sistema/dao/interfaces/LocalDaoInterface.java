package br.com.sne.sistema.dao.interfaces;

import java.util.List;
import br.com.sne.sistema.bean.Local;

public interface LocalDaoInterface {

	public void atualizarLocal(Local transientObject);
	
	public Local carregarLocal(long id);

	public List<Local> listarLocais() ;

	public void removerLocal(Local persistentObject) ;

	public void salvarLocal(Local newInstance) ;

	public abstract void restaurarLocal(Local persistentObject);

	public abstract List<Local> listarLocaisExcluidos();
	

}