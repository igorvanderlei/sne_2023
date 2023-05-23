package br.com.sne.sistema.dao.interfaces;

import java.util.List;
import br.com.sne.sistema.bean.Unidade;

public interface UnidadeDaoInterface {

	public void atualizarUnidade(Unidade transientObject);
	
	public Unidade carregarUnidade(long id);

	public List<Unidade> listarUnidade() ;

	public void removerUnidade(Unidade persistentObject) ;

	public void salvarUnidade(Unidade newInstance) ;

	public Unidade localizarUnidadePorCodigo(String codigo);

	public void restaurarUnidade(Unidade persistentObject);

	public List<Unidade> listarUnidadesExcluidas();
	

}