package br.com.sne.sistema.dao.interfaces;

import java.util.List;

import br.com.sne.sistema.bean.Funcionario;
import br.com.sne.sistema.bean.OsDePassagem;

public interface OsDePassagemDaoInterface {

	public abstract void atualizarOsDePassagem(final OsDePassagem o)
			;

	public abstract OsDePassagem carregarOsDePassagem(long id)
			;

	public abstract List<OsDePassagem> listarOsDePassagem() ;

	public abstract List<OsDePassagem> listarOsDePassagem(Funcionario f)
			;

	public abstract void removerOsDePassagem(OsDePassagem persistentObject)
			;

	public abstract void salvarOsDePassagem(OsDePassagem newInstance)
			;

	public abstract void restaurarOsDePassagem(OsDePassagem persistentObject)
			;

}