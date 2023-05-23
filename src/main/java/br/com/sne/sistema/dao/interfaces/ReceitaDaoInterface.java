package br.com.sne.sistema.dao.interfaces;

import java.util.List;

import br.com.sne.sistema.bean.OrdemServico;
import br.com.sne.sistema.bean.Receita;

public interface ReceitaDaoInterface {

	public void atualizarReceita(Receita transientObject) ;
	
	public Receita carregarReceita(long id) ;

	public List<Receita> listarReceitas(int campo,String texto)  ;

	public void removerReceita(Receita persistentObject)  ;

	public void salvarReceita(Receita newInstance)  ;

	public abstract List<Receita> listarReceitasPorOS(OrdemServico os);

	public abstract void restaurarReceita(Receita persistentObject) ;

	public abstract List<Receita> listarReceitasExcluidas() ;
	

}