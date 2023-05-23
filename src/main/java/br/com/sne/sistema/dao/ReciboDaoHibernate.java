package br.com.sne.sistema.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.com.sne.sistema.bean.Despesa;
import br.com.sne.sistema.bean.Receita;
import br.com.sne.sistema.bean.Recibo;
import br.com.sne.sistema.dao.interfaces.ReciboDaoInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class ReciboDaoHibernate implements ReciboDaoInterface {
	@Autowired
	private EntityManager entityManager;
	
	public void atualizarRecibo(Recibo transientObject) {
		entityManager.merge(transientObject);
	}

	public Recibo carregarRecibo(long id) {
		return entityManager.find(Recibo.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Recibo> listarRecibo() {
			Query consulta = entityManager.createQuery("from Recibo where deletado = false");
			return consulta.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Recibo> listarRecibosCancelados() {
		Query consulta = entityManager.createQuery("from Recibo where deletado = true");
		return consulta.getResultList();
	}

	public void cancelarRecibo(Recibo persistentObject) {
			persistentObject.setCancelado(true);
			entityManager.merge(persistentObject);
	}
	
	public void restaurarRecibo(Recibo persistentObject) {
			persistentObject.setCancelado(false);
			entityManager.merge(persistentObject);
	}

	public Recibo localizarReciboPorDespesa(Despesa despesa) {
		try {
			Query consulta = entityManager.createQuery("from Recibo where despesa.id = ?1 and cancelado = false");
			consulta.setParameter(1, despesa.getId());
			return (Recibo) consulta.getSingleResult();
		} catch(NoResultException ex) {
			return null;
		}	
	}
	
	public Recibo localizarReciboPorReceita(Receita receita) {
		try {
			Query consulta = entityManager.createQuery("from Recibo where receita.id = ?1 and cancelado = false");
			consulta.setParameter(1, receita.getId());
			return (Recibo) consulta.getSingleResult();
		} catch(NoResultException ex) {
			return null;
		}	
	}
	
	public void salvarRecibo(Recibo newInstance) {
		entityManager.persist(newInstance);
	}

}