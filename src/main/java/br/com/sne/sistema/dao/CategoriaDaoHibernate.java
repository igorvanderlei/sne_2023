package br.com.sne.sistema.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.com.sne.sistema.bean.Categoria;
import br.com.sne.sistema.dao.interfaces.CategoriaDaoInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class CategoriaDaoHibernate implements CategoriaDaoInterface {
	@Autowired
	private EntityManager entityManager;

	public void atualizarCategoria(Categoria transientObject)  {
		entityManager.merge(transientObject);
	}

	public Categoria carregarCategoria(long id)  {
		return entityManager.find(Categoria.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Categoria> listarCategorias()  {
		Query consulta = 
				entityManager.createQuery("from Categoria where deletado = false");
		return consulta.getResultList();
	}

	public void removerCategoria(Categoria persistentObject)  {
		persistentObject.setDeletado(true);
		entityManager.merge(persistentObject);	
	}
	
	public void restaurarCategoria(Categoria persistentObject)  {
		persistentObject.setDeletado(false);
		entityManager.merge(persistentObject);	
	}

	public void salvarCategoria(Categoria newInstance)  {
		entityManager.persist(newInstance);
	}
}