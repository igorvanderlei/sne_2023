package br.com.sne.sistema.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.sne.sistema.bean.Grupo; 
import br.com.sne.sistema.bean.Recurso;
import br.com.sne.sistema.dao.interfaces.RecursoDaoInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class RecursoDaoHibernate implements RecursoDaoInterface {
	@Autowired
	private EntityManager entityManager;
	
	public void atualizarRecurso(Recurso transientObject)  {
		entityManager.merge(transientObject);
	}

	public Recurso carregarRecurso(long id)  {
		return entityManager.find(Recurso.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Recurso> listarRecursos()  {
		Query consulta = 
				entityManager.createQuery("from Recurso where deletado = false order by nome");
		return consulta.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Recurso> listarRecursosExcluidos()  {
		Query consulta = 
				entityManager.createQuery("from Recurso where deletado = true order by nome");
		return consulta.getResultList();
	}
		
	public void removerRecurso(Recurso persistentObject)  {
			persistentObject.setDeletado(true);
			entityManager.merge(persistentObject);
	}
	
	public void restaurarRecurso(Recurso persistentObject)  {
		persistentObject.setDeletado(false);
		entityManager.merge(persistentObject);		
	}

	public void salvarRecurso(Recurso newInstance)  {
		entityManager.persist(newInstance);
	}

	@SuppressWarnings("unchecked")
	public List<Recurso> listarRecurso(Grupo grp)  {
		Query consulta = 
				entityManager.createQuery("from Recurso rec where rec.grupo.id = ?1 and deletado = false");
		consulta.setParameter(1, grp.getId());
		return consulta.getResultList();		
	}
	
	@SuppressWarnings("unchecked")
	public List<Recurso> listarRecursosAtivos()  {
		Query consulta = 
				entityManager.createQuery("from DescricaoEquipamento where deletado = false order by nome");
		return consulta.getResultList();
	}
	

}