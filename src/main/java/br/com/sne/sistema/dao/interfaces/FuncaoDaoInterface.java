package br.com.sne.sistema.dao.interfaces;

import java.util.List;
import br.com.sne.sistema.bean.Funcao;

public interface FuncaoDaoInterface {

	public void atualizarFuncao(Funcao transientObject) ;
	
	public Funcao carregarFuncao(long id) ;

	public List<Funcao> listarFuncoes()  ;

	public void removerFuncao(Funcao persistentObject)  ;

	public void salvarFuncao(Funcao newInstance)  ;

	public abstract void restaurarFuncao(Funcao persistentObject) ;

	public abstract List<Funcao> listarFuncoesExcluidas() ;
	

}