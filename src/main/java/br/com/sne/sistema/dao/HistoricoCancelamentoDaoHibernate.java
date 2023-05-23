package br.com.sne.sistema.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.com.sne.sistema.bean.HistoricoCancelamento;
import br.com.sne.sistema.dao.interfaces.HistoricoCancelamentoDaoInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class HistoricoCancelamentoDaoHibernate implements HistoricoCancelamentoDaoInterface {
	@Autowired
	private EntityManager entityManager;
	
	public HistoricoCancelamento carregarHistoricoCancelamento(long id)  {
		return entityManager.find(HistoricoCancelamento.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<HistoricoCancelamento> listarHistoricoCancelamentos()  {
		Query consulta = 
				entityManager.createQuery("from HistoricoCancelamento");
		return consulta.getResultList();
	}
	
	public void salvarHistoricoCancelamento(HistoricoCancelamento newInstance)  {
		entityManager.persist(newInstance);
	}
}