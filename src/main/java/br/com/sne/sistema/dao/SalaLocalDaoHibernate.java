package br.com.sne.sistema.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.com.sne.sistema.bean.LocalEvento;
import br.com.sne.sistema.bean.SalaLocal;
import br.com.sne.sistema.dao.interfaces.SalaLocalDaoInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class SalaLocalDaoHibernate implements SalaLocalDaoInterface {
	@Autowired
	private EntityManager entityManager;
	
	public void atualizarSalaLocal(SalaLocal transientObject)  {
		entityManager.merge(transientObject);
	}

	public SalaLocal carregarSalaLocal(long id)  {
		return entityManager.find(SalaLocal.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<SalaLocal> listarSalaLocals()  {
		Query consulta = 
				entityManager.createQuery("from SalaLocal where deletado = false ");
		return consulta.getResultList();
		
	}

	public void removerSalaLocal(SalaLocal persistentObject)  {
			persistentObject.setDeletado(true);
			entityManager.merge(persistentObject);	
	}
	
	public void restaurarSalaLocal(SalaLocal persistentObject)  {
		persistentObject.setDeletado(false);
		entityManager.merge(persistentObject);			
	}

	public void salvarSalaLocal(SalaLocal newInstance)  {
		entityManager.persist(newInstance);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SalaLocal> listarSalaLocalsPorLocalEvento(LocalEvento localEvento)  {
		Query consulta = 
				entityManager.createQuery("from SalaLocal salo where deletado = false and salo.localevento.id = ?1 ");
		consulta.setParameter(1, localEvento.getId());
		return consulta.getResultList();
	}

}