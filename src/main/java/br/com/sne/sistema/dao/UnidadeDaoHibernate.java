package br.com.sne.sistema.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.com.sne.sistema.bean.Unidade;
import br.com.sne.sistema.dao.interfaces.UnidadeDaoInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class UnidadeDaoHibernate implements UnidadeDaoInterface {
	@Autowired
	private EntityManager entityManager;

	public void atualizarUnidade(Unidade transientObject) {
		entityManager.merge(transientObject);
	}

	public Unidade carregarUnidade(long id) {
		return entityManager.find(Unidade.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Unidade> listarUnidade() {
		Query consulta = 
				entityManager.createQuery("from Unidade where deletado = false");
		return consulta.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Unidade> listarUnidadesExcluidas() {
		Query consulta = 
				entityManager.createQuery("from Unidade where deletado = true");
		return consulta.getResultList();
	}

	public void removerUnidade(Unidade persistentObject) {
		persistentObject.setDeletado(true);
		entityManager.merge(persistentObject);
	}

	public void restaurarUnidade(Unidade persistentObject) {
		persistentObject.setDeletado(false);
		entityManager.merge(persistentObject);
	}

	public Unidade localizarUnidadePorCodigo(String codigo) {
		try {
			Query consulta = entityManager.createQuery("from Unidade where codigo like ?1 and deletado = false");
			consulta.setParameter(1, codigo);
			return (Unidade) consulta.getSingleResult();
		} catch(NoResultException ex) {
			return null;
		}
	}

	public void salvarUnidade(Unidade newInstance) {
		entityManager.persist(newInstance);
	}


}