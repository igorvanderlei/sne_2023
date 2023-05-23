package br.com.sne.sistema.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.com.sne.sistema.bean.CentroCusto;
import br.com.sne.sistema.dao.interfaces.CentroCustoDaoInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class CentroCustoDaoHibernate implements CentroCustoDaoInterface {
	@Autowired
	private EntityManager entityManager;

	public void atualizarCentroCusto(CentroCusto transientObject)  {
		entityManager.merge(transientObject);
	}

	public CentroCusto carregarCentroCusto(long id)  {
		return entityManager.find(CentroCusto.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<CentroCusto> listarCentroCustos()  {
		Query consulta = 
				entityManager.createQuery("from CentroCusto where deletado = false order by nome");
		return consulta.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<CentroCusto> listarCentroCustosExcluidos()  {
		Query consulta = 
				entityManager.createQuery("from CentroCusto where deletado = true order by nome");
		return consulta.getResultList();	
	}
	
	public CentroCusto localizarCentroCustoPorNome(String nome)  {
		try {
			Query consulta = entityManager.createQuery("from CentroCusto where nome like ?1 and deletado = false");
			consulta.setParameter(1, nome);
			return (CentroCusto) consulta.getSingleResult();
		} catch(NoResultException ex) {
			return null;
		}
	}

	public void removerCentroCusto(CentroCusto persistentObject)  {
		persistentObject.setDeletado(true);
		entityManager.merge(persistentObject);		
	}
	
	public void restaurarCentroCusto(CentroCusto persistentObject)  {
		persistentObject.setDeletado(false);
		entityManager.merge(persistentObject);	
	}

	public void salvarCentroCusto(CentroCusto newInstance)  {
		entityManager.persist(newInstance);
	}
}