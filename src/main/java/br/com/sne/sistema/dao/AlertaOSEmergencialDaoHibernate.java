package br.com.sne.sistema.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.com.sne.sistema.bean.AlertaOSEmergencial;
import br.com.sne.sistema.bean.OrdemServico;
import br.com.sne.sistema.dao.interfaces.AlertaOSEmergencialDaoInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class AlertaOSEmergencialDaoHibernate implements AlertaOSEmergencialDaoInterface {
	@Autowired
	private EntityManager entityManager;
	
	public void atualizarAlertaOSEmergencial(AlertaOSEmergencial transientObject)  {
		entityManager.merge(transientObject);	
	}

	public AlertaOSEmergencial carregarAlertaOSEmergencial(long id)  {
		return entityManager.find(AlertaOSEmergencial.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<AlertaOSEmergencial> listarAlertaOSEmergencials()  {
		Query consulta = 
				entityManager.createQuery("from AlertaOSEmergencial where status = false");
		return consulta.getResultList();
	}
	
	@Override
	public AlertaOSEmergencial localizarAlertaPorEmergencial(OrdemServico emergencial) {
		try {
			Query consulta = entityManager.createQuery("from AlertaOSEmergencial where osEmergencial.id = " + emergencial.getId());
			return (AlertaOSEmergencial) consulta.getSingleResult();
		} catch(NoResultException ex) {
			return null;
		}
	} 

	public void salvarAlertaOSEmergencial(AlertaOSEmergencial newInstance)  {
		entityManager.persist(newInstance);
	}
}