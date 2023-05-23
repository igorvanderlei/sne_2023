package br.com.sne.sistema.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.com.sne.sistema.bean.DevolucaoSublocados;
import br.com.sne.sistema.dao.interfaces.DevolucaoSublocadosDaoInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class DevolucaoSublocadosDaoHibernate implements DevolucaoSublocadosDaoInterface {
	@Autowired
	private EntityManager entityManager;
	
	public void atualizarDevolucaoSublocados(DevolucaoSublocados transientObject)  {
		entityManager.merge(transientObject);
	}

	public DevolucaoSublocados carregarDevolucaoSublocados(long id)  {
		return entityManager.find(DevolucaoSublocados.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<DevolucaoSublocados> listarDevolucaoSublocadoss()  {
		Query consulta = 
				entityManager.createQuery("from DevolucaoSublocados where deletado = false");
		return consulta.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<DevolucaoSublocados> listarDevolucaoSublocadossExcluidos()  {
		Query consulta = 
				entityManager.createQuery("from DevolucaoSublocados where deletado = true");
		return consulta.getResultList();
	}
	
	public void removerDevolucaoSublocados(DevolucaoSublocados persistentObject)  {
		persistentObject.setDeletado(true);
		entityManager.merge(persistentObject);	
	}
	
	public void restaurarDevolucaoSublocados(DevolucaoSublocados persistentObject)  {
		persistentObject.setDeletado(false);
		entityManager.merge(persistentObject);	
	}

	public void salvarDevolucaoSublocados(DevolucaoSublocados newInstance)  {
		entityManager.persist(newInstance);
	}
}