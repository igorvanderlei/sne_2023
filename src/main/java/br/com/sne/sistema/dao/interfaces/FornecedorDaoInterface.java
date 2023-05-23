package br.com.sne.sistema.dao.interfaces;

import java.util.List;

import br.com.sne.sistema.bean.DadosBancarios;
import br.com.sne.sistema.bean.Fornecedor;

public interface FornecedorDaoInterface {

	public void atualizarFornecedor(Fornecedor transientObject) ;
	
	public Fornecedor carregarFornecedor(long id) ;

	public Fornecedor carregarFornecedor(DadosBancarios dadosBancarios) ;
	
	public List<Fornecedor> listarFornecedores()  ;

	public void removerFornecedor(Fornecedor persistentObject)  ;

	public void salvarFornecedor(Fornecedor newInstance)  ;

	public abstract void restaurarFornecedor(Fornecedor persistentObject)
			;

	public abstract List<Fornecedor> listarFornecedoresExcluidos() ;

	public abstract Fornecedor localizarFornecedorPorCodigo(String codigo)
			;
	

}