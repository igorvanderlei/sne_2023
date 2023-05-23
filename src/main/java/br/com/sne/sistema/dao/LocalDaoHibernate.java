package br.com.sne.sistema.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.com.sne.sistema.bean.Local;
import br.com.sne.sistema.dao.interfaces.LocalDaoInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class LocalDaoHibernate implements LocalDaoInterface {
	@Autowired
	private EntityManager entityManager;
	
	public void atualizarLocal(Local transientObject) {
		entityManager.merge(transientObject);
	}

	public Local carregarLocal(long id) {
		return entityManager.find(Local.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Local> listarLocais() {
		Query consulta = 
				entityManager.createQuery("from Local where deletado = false");
		return consulta.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Local> listarLocaisExcluidos() {
		Query consulta = 
				entityManager.createQuery("from Local where deletado = true");
		return consulta.getResultList();
	}

	public void removerLocal(Local persistentObject) {
		persistentObject.setDeletado(true);
		entityManager.merge(persistentObject);	
	}
	
	public void restaurarLocal(Local persistentObject) {
		persistentObject.setDeletado(false);
		entityManager.merge(persistentObject);	
	}

	public void salvarLocal(Local newInstance) {
		entityManager.persist(newInstance);
	}
}