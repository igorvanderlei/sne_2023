package br.com.sne.sistema.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.com.sne.sistema.bean.Ambiente;
import br.com.sne.sistema.dao.interfaces.AmbienteDaoInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class AmbienteDaoHibernate implements AmbienteDaoInterface {
	@Autowired
	private EntityManager entityManager;
	
	public void atualizarAmbiente(Ambiente transientObject)  {
		entityManager.merge(transientObject);
	}

	public Ambiente carregarAmbiente(long id)  {
		return entityManager.find(Ambiente.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Ambiente> listarAmbientes()  {
		Query consulta = 
				entityManager.createQuery("from Ambiente");
		return consulta.getResultList();
	}

	public void removerAmbiente(Ambiente persistentObject)  {
		persistentObject.setDeletado(true);
		entityManager.merge(persistentObject);	
	}
	
	public void restaurarAmbiente(Ambiente persistentObject)  {
		persistentObject.setDeletado(false);
		entityManager.merge(persistentObject);
	}

	public void salvarAmbiente(Ambiente newInstance)  {
		entityManager.persist(newInstance);
	}
	

}