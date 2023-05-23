package br.com.sne.sistema.dao.interfaces;

import java.util.List;
import br.com.sne.sistema.bean.Grupo;

public interface GrupoDaoInterface {

	public void atualizarGrupo(Grupo transientObject);
	public Grupo carregarGrupo(long id);
	public List<Grupo> listarGrupos() ;
	public void removerGrupo(Grupo persistentObject) ;
	public void salvarGrupo(Grupo newInstance) ;
	public abstract Grupo localizarGrupoPorCodigo(String codigo);
	public abstract Grupo localizarGrupoPorNome(String nome);
	public abstract void restaurarGrupo(Grupo persistentObject);
	public abstract List<Grupo> listarGruposExcluidos();

}