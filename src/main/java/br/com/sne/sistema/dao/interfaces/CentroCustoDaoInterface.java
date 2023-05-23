package br.com.sne.sistema.dao.interfaces;

import java.util.List;

import br.com.sne.sistema.bean.CentroCusto;

public interface CentroCustoDaoInterface {

	public void atualizarCentroCusto(CentroCusto transientObject) ;
	
	public CentroCusto carregarCentroCusto(long id) ;

	public List<CentroCusto> listarCentroCustos()  ;

	public void removerCentroCusto(CentroCusto persistentObject)  ;

	public void salvarCentroCusto(CentroCusto newInstance)  ;

	public abstract CentroCusto localizarCentroCustoPorNome(String nome)
			;

	public abstract void restaurarCentroCusto(CentroCusto persistentObject)
			;

	public abstract List<CentroCusto> listarCentroCustosExcluidos() ;
	

}