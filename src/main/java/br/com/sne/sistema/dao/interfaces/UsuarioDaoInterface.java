package br.com.sne.sistema.dao.interfaces;

import java.util.List;

import br.com.sne.sistema.bean.Funcionario;
import br.com.sne.sistema.bean.TipoUsuario;
import br.com.sne.sistema.bean.Usuario;

public interface UsuarioDaoInterface {

	public void atualizarUsuario(Usuario transientObject) ;
	
	public Usuario carregarUsuario(long id) ;
	
	public Usuario localizarUsuarioPorLogin(String login) ;

	public List<Usuario> listarUsuarios()  ;

	public void removerUsuario(Usuario persistentObject)  ;

	public void salvarUsuario(Usuario newInstance)  ;

	public abstract void restaurarUsuario(Usuario persistentObject) ;

	public abstract List<Usuario> listarUsuariosExcluidos() ;

	public abstract List<Usuario> listarUsuarios(TipoUsuario tipo) ;

	public abstract List<Usuario> listarUsuarios(Funcionario func) ;
	

}