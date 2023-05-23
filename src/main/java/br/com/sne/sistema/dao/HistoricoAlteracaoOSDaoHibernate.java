package br.com.sne.sistema.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.com.sne.sistema.bean.HistoricoAlteracaoOS;
import br.com.sne.sistema.dao.interfaces.HistoricoAlteracaoOSInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class HistoricoAlteracaoOSDaoHibernate implements HistoricoAlteracaoOSInterface  {
	@Autowired
	private EntityManager entityManager;

	public HistoricoAlteracaoOS carregarHistoricoAlteracaoOS(long id)  {
		return (HistoricoAlteracaoOS) entityManager.find(HistoricoAlteracaoOS.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<HistoricoAlteracaoOS> listarHistoricoAlteracaoOSs()  {
		Query consulta = entityManager.createQuery("from HistoricoAlteracaoOS");
		return consulta.getResultList();
	}

	public void salvarHistoricoAlteracaoOS(HistoricoAlteracaoOS newInstance)  {
		entityManager.persist(newInstance);
	}


}