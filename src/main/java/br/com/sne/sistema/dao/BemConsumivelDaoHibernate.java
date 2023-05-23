package br.com.sne.sistema.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.com.sne.sistema.bean.BemConsumivel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class BemConsumivelDaoHibernate {
	@Autowired
	private EntityManager entityManager;

	public void atualizarBemConsumivel(BemConsumivel transientObject) {
		entityManager.merge(transientObject);	
	}

	public BemConsumivel carregarBemConsumivel(long id) {
		return entityManager.find(BemConsumivel.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<BemConsumivel> listarBemConsumivels() {
		Query consulta = 
				entityManager.createQuery("from BemConsumivel where deletado = false");
		return consulta.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<BemConsumivel> listarBemConsumivelsExcluidos() {
		Query consulta = 
				entityManager.createQuery("from BemConsumivel where deletado = true");
		return consulta.getResultList();
	}
	
	public BemConsumivel localizarBemConsumivelPorNome(String nome) {
		try {
			Query consulta = entityManager.createQuery("from BemConsumivel where nome like ?1 and deletado = false");
			consulta.setParameter(1, nome);
			return (BemConsumivel) consulta.getSingleResult();
		} catch(NoResultException ex) {
			return null;
		}
	}

	public BemConsumivel localizarBemConsumivelPorCodigo(String codigo) {
		try {
			Query consulta = entityManager.createQuery("from BemConsumivel where codigo like ?1 and deletado = false");
			consulta.setParameter(1, codigo);
			return (BemConsumivel) consulta.getSingleResult();
		} catch(NoResultException ex) {
			return null;
		}
	}
	
	public void removerBemConsumivel(BemConsumivel persistentObject) {
		persistentObject.setDeletado(true);
		entityManager.merge(persistentObject);		
	}
	
	public void restaurarBemConsumivel(BemConsumivel persistentObject) {
		persistentObject.setDeletado(false);
		entityManager.merge(persistentObject);	
	}

	public void salvarBemConsumivel(BemConsumivel newInstance) {
		entityManager.persist(newInstance);
	}
}