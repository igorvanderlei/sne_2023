package br.com.sne.sistema.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.com.sne.sistema.bean.Equipamento;
import br.com.sne.sistema.bean.ManutencaoCorretiva;
import br.com.sne.sistema.dao.interfaces.ManutencaoCorretivaDaoInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class ManutencaoCorretivaDaoHibernate implements ManutencaoCorretivaDaoInterface {
	@Autowired
	private EntityManager entityManager;
	
	public void atualizarManutencaoCorretiva(ManutencaoCorretiva transientObject) {
		entityManager.merge(transientObject);
	}

	public ManutencaoCorretiva carregarManutencaoCorretiva(long id) {
		return entityManager.find(ManutencaoCorretiva.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<ManutencaoCorretiva> listarManutencaoCorretiva() {
		Query consulta = 
				entityManager.createQuery("from ManutencaoCorretiva where deletado = false");
		return consulta.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<ManutencaoCorretiva> listarManutencaoCorretivaExcluidos() {
		Query consulta = 
				entityManager.createQuery("from ManutencaoCorretiva where deletado = true");
		return consulta.getResultList();	
	}
	
	public ManutencaoCorretiva localizarManutencaoCorretivaPorEquipamento(Equipamento eq) {
		try {
			Query consulta = entityManager.createQuery("from ManutencaoCorretiva where equipamento.id = ?1 and deletado = false  and status = false");
			consulta.setParameter(1, eq.getId());
			return (ManutencaoCorretiva) consulta.getSingleResult();
		} catch(NoResultException ex) {
			return null;
		}	
	}

	@SuppressWarnings("unchecked")
	public List<ManutencaoCorretiva> localizarManutencaoCorretivaPorAssistencia(String ass) {
			Query consulta = entityManager.createQuery("from ManutencaoCorretiva where nomeAssistencia like ?1 and deletado = false");
			consulta.setParameter(1, ass);
			return consulta.getResultList();
	}
	
	public void removerManutencaoCorretiva(ManutencaoCorretiva persistentObject) {
		persistentObject.setDeletado(true);
		entityManager.merge(persistentObject);	
	}
	
	public void restaurarManutencaoCorretiva(ManutencaoCorretiva persistentObject) {
		persistentObject.setDeletado(false);
		entityManager.merge(persistentObject);	
	}

	public void salvarManutencaoCorretiva(ManutencaoCorretiva newInstance) {
		entityManager.persist(newInstance);
	}
}