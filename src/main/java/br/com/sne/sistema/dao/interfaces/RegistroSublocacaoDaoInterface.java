package br.com.sne.sistema.dao.interfaces;

import java.util.List;

import br.com.sne.sistema.bean.Fornecedor;
import br.com.sne.sistema.bean.RegistroSublocacao;

public interface RegistroSublocacaoDaoInterface {

	public void atualizarRegistroSublocacao(RegistroSublocacao transientObject);
	
	public RegistroSublocacao carregarRegistroSublocacao(long id);

	public List<RegistroSublocacao> listarRegistroSublocacaos() ;

	public void removerRegistroSublocacao(RegistroSublocacao persistentObject) ;

	public void salvarRegistroSublocacao(RegistroSublocacao newInstance) ;

	public abstract void restaurarRegistroSublocacao(RegistroSublocacao persistentObject);

	public abstract List<RegistroSublocacao> listarRegistroSublocacaosExcluidos();

	public abstract List<RegistroSublocacao> listarRegistroSublocacaosPendentesPorFornecedor(
			Fornecedor f);
	

}