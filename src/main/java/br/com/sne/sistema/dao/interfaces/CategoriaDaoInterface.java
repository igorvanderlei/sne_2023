package br.com.sne.sistema.dao.interfaces;

import java.util.List;

import br.com.sne.sistema.bean.Categoria;

public interface CategoriaDaoInterface {

	public void atualizarCategoria(Categoria transientObject) ;
	
	public Categoria carregarCategoria(long id) ;

	public List<Categoria> listarCategorias()  ;

	public void removerCategoria(Categoria persistentObject)  ;

	public void salvarCategoria(Categoria newInstance)  ;

	public abstract void restaurarCategoria(Categoria persistentObject) ;
	

}