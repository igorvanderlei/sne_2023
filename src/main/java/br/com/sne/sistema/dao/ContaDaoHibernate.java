package br.com.sne.sistema.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.com.sne.sistema.bean.Conta;
import br.com.sne.sistema.dao.interfaces.ContaDaoInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class ContaDaoHibernate implements ContaDaoInterface {
	@Autowired
	private EntityManager entityManager;

	public void atualizarConta(Conta transientObject)  {
		entityManager.merge(transientObject);
	}

	public Conta carregarConta(long id)  {
		return entityManager.find(Conta.class, id);	
	}

	@SuppressWarnings("unchecked")
	public List<Conta> listarContas()  {
		Query consulta = 
				entityManager.createQuery("from Conta where deletado = false");
		return consulta.getResultList();
	}

	public void removerConta(Conta persistentObject)  {
		persistentObject.setDeletado(true);
		entityManager.merge(persistentObject);	
	}

	public void restaurarConta(Conta persistentObject)  {
		persistentObject.setDeletado(false);
		entityManager.merge(persistentObject);	
	}

	public void salvarConta(Conta newInstance)  {
		entityManager.persist(newInstance);
	}


}