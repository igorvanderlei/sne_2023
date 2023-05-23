package br.com.sne.sistema.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.com.sne.sistema.bean.Equipamento;
import br.com.sne.sistema.bean.Funcionario;
import br.com.sne.sistema.bean.ManutencaoPreventiva;
import br.com.sne.sistema.dao.interfaces.ManutencaoPreventivaDaoInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class ManutencaoPreventivaDaoHibernate implements ManutencaoPreventivaDaoInterface  {
	@Autowired
	private EntityManager entityManager;

	public void atualizarManutencaoPreventiva(ManutencaoPreventiva transientObject)  {
		entityManager.merge(transientObject);
	}

	public ManutencaoPreventiva carregarManutencaoPreventiva(long id)  {
		return (ManutencaoPreventiva) entityManager.find(ManutencaoPreventiva.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<ManutencaoPreventiva> listarManutencaoPreventivas()  {
		Query consulta = entityManager.createQuery("from ManutencaoPreventiva where deletado = false");
		return consulta.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<ManutencaoPreventiva> listarManutencaoPreventivasExcluidos()  {
		Query consulta = entityManager.createQuery("from ManutencaoPreventiva where deletado = true");
		return consulta.getResultList();
	}

	public ManutencaoPreventiva localizarManutencaoPreventivaPorEquipamento(Equipamento eq)  {
		Query consulta = entityManager.createQuery("from ManutencaoPreventiva where equipamento.id = ?1 and deletado = false and status = false");
		consulta.setParameter(1, eq.getId());
		return (ManutencaoPreventiva) consulta.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<ManutencaoPreventiva> localizarManutencaoPreventivaPorTecnico(Funcionario tec)  {
		Query consulta = entityManager.createQuery("from ManutencaoPreventiva where tecnicoResponsavel.id = ?1 and deletado = false");
		consulta.setParameter(1, tec.getId());
		return consulta.getResultList();
	}

	public void removerManutencaoPreventiva(ManutencaoPreventiva persistentObject)  {
		persistentObject.setDeletado(true);
		entityManager.merge(persistentObject);
	}

	public void restaurarManutencaoPreventiva(ManutencaoPreventiva persistentObject)  {
		persistentObject.setDeletado(false);
		entityManager.merge(persistentObject);
	}

	public void salvarManutencaoPreventiva(ManutencaoPreventiva newInstance)  {
		entityManager.persist(newInstance);
	}


}