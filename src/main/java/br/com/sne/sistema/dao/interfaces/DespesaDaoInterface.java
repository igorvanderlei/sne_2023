package br.com.sne.sistema.dao.interfaces;

import java.util.Date;
import java.util.List;

import br.com.sne.sistema.bean.Despesa;
import br.com.sne.sistema.bean.OrdemServico;

public interface DespesaDaoInterface {

	public void atualizarDespesa(Despesa transientObject) ;
	
	public Despesa carregarDespesa(long id) ;

	public List<Despesa> listarDespesas(int campo, String texto)  ;
	
	public List<Despesa> listarDespesas(OrdemServico os) ;
	
	public Despesa buscarDespesaComissao(OrdemServico os) ;
	
	public void removerDespesa(Despesa persistentObject)  ;

	public void salvarDespesa(Despesa newInstance)  ;

	public abstract void restaurarDespesa(Despesa persistentObject) ;

	public abstract List<Despesa> listarDespesasExcluidas() ;

	public abstract List<Despesa> listarDespesasAbertas(Date inicio, Date fim)
			;
	

}