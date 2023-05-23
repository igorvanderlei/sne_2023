package br.com.sne.sistema.dao.interfaces;

import java.util.List;
import br.com.sne.sistema.bean.TipoUsuario;

public interface TipoUsuarioDaoInterface {

	public void atualizarTipoUsuario(TipoUsuario transientObject) ;
	
	public TipoUsuario carregarTipoUsuario(long id) ;

	public List<TipoUsuario> listarTipoUsuarios()  ;

	public void removerTipoUsuario(TipoUsuario persistentObject)  ;

	public void salvarTipoUsuario(TipoUsuario newInstance)  ;

	public abstract List<TipoUsuario> procurarTipoUsuarios(String nome);

	public abstract void restaurarTipoUsuario(TipoUsuario persistentObject);

	public abstract List<TipoUsuario> listarTipoUsuariosExcluidos() ;
	

}