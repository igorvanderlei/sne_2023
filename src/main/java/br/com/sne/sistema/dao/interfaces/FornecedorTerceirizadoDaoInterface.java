package br.com.sne.sistema.dao.interfaces;

import java.util.List;
import br.com.sne.sistema.bean.FornecedorTerceirizado;

public interface FornecedorTerceirizadoDaoInterface {

	public void atualizarFornecedorTerceirizado(FornecedorTerceirizado transientObject);
	
	public FornecedorTerceirizado carregarFornecedorTerceirizado(long id);

	public List<FornecedorTerceirizado> listarFornecedorTerceirizados() ;

	public void removerFornecedorTerceirizado(FornecedorTerceirizado persistentObject) ;

	public void salvarFornecedorTerceirizado(FornecedorTerceirizado newInstance) ;

	public abstract void restaurarFornecedorTerceirizado(FornecedorTerceirizado persistentObject);

	public abstract List<FornecedorTerceirizado> listarFornecedorTerceirizadosExcluidos();

	public abstract FornecedorTerceirizado localizarFornecedorTerceirizadoPorCodigo(String codigo);
	

}