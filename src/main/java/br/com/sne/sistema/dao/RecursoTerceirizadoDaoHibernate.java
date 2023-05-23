package br.com.sne.sistema.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.com.sne.sistema.bean.Grupo; 
import br.com.sne.sistema.bean.RecursoTerceirizado;
import br.com.sne.sistema.dao.interfaces.RecursoTerceirizadoDaoInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class RecursoTerceirizadoDaoHibernate implements RecursoTerceirizadoDaoInterface {
	@Autowired
	private EntityManager entityManager;

	public void atualizarRecursoTerceirizado(RecursoTerceirizado transientObject)  {
		entityManager.merge(transientObject);
	}

	public RecursoTerceirizado carregarRecursoTerceirizado(long id)  {
		return (RecursoTerceirizado) entityManager.find(RecursoTerceirizado.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<RecursoTerceirizado> listarRecursoTerceirizados()  {
		Query consulta = entityManager.createQuery("from RecursoTerceirizado where deletado = false order by nome");
		return consulta.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<RecursoTerceirizado> listarRecursoTerceirizadosExcluidos()  {
		Query consulta = entityManager.createQuery("from RecursoTerceirizado where deletado = true");
		return consulta.getResultList();
	}

	public void removerRecursoTerceirizado(RecursoTerceirizado persistentObject)  {
		persistentObject.setDeletado(true);
		entityManager.merge(persistentObject);
	}

	public void restaurarRecursoTerceirizado(RecursoTerceirizado persistentObject)  {
		persistentObject.setDeletado(false);
		entityManager.merge(persistentObject);
	}

	public void salvarRecursoTerceirizado(RecursoTerceirizado newInstance)  {
		entityManager.persist(newInstance);
	}

	@SuppressWarnings("unchecked")
	public List<RecursoTerceirizado> listarRecursoTerceirizado(Grupo grp)  {
		Query consulta = entityManager.createQuery("from RecursoTerceirizado rec where rec.grupo.id = ?1 and deletado = false");
		consulta.setParameter(1, grp.getId());
		return consulta.getResultList();
	}


	@SuppressWarnings("unchecked")
	public List<RecursoTerceirizado> listarRecursoTerceirizadosAtivos()  {
		Query consulta = entityManager.createQuery("from DescricaoEquipamento where deletado = false");
		return consulta.getResultList();
	}


}