package br.com.sne.sistema.dao.interfaces;

import java.util.List;

import br.com.sne.sistema.bean.ContagemEstoque;

public interface ContagemDaoInterface {
	
	public void atualizarContagem(ContagemEstoque transientObject) ;
	
	public ContagemEstoque carregarContagem(long id) ;

	public List<ContagemEstoque> listarContagens()  ;

	public void removerContagem(ContagemEstoque persistentObject)  ;

	public void salvarContagem(ContagemEstoque newInstance)  ;

	public abstract void restaurarContagem(ContagemEstoque persistentObject) ;
	

}
