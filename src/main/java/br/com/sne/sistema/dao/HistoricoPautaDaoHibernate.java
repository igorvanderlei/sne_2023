package br.com.sne.sistema.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.com.sne.sistema.bean.HistoricoPauta;
import br.com.sne.sistema.bean.Pauta;
import br.com.sne.sistema.dao.interfaces.HistoricoPautaDaoInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class HistoricoPautaDaoHibernate implements HistoricoPautaDaoInterface {
	@Autowired
	private EntityManager entityManager;

	public HistoricoPauta carregarHistoricoPauta(long id)  {
		return (HistoricoPauta) entityManager.find(HistoricoPauta.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<HistoricoPauta> listarHistoricoPautas(Pauta pauta)  {
		Query consulta = 
				entityManager.createQuery("from HistoricoPauta where pauta = ?1");
		consulta.setParameter(1, pauta.getId());
		return consulta.getResultList();
	}

	public void salvarHistoricoPauta(HistoricoPauta newInstance)  {
		entityManager.persist(newInstance);
	}


}