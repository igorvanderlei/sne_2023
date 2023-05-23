package br.com.sne.sistema.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.com.sne.sistema.bean.Funcao;
import br.com.sne.sistema.dao.interfaces.FuncaoDaoInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class FuncaoDaoHibernate implements FuncaoDaoInterface {
	@Autowired
	private EntityManager entityManager;

	public void atualizarFuncao(Funcao transientObject)  {
		entityManager.merge(transientObject);
	}

	public Funcao carregarFuncao(long id)  {
		return entityManager.find(Funcao.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Funcao> listarFuncoes()  {
		Query consulta = 
				entityManager.createQuery("from Funcao where deletado = false");
		return consulta.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Funcao> listarFuncoesExcluidas()  {
		Query consulta = 
				entityManager.createQuery("from Funcao where deletado = true");
		return consulta.getResultList();
	}

	public void removerFuncao(Funcao persistentObject)  {
		persistentObject.setDeletado(true);
		entityManager.merge(persistentObject);	
	}

	public void restaurarFuncao(Funcao persistentObject)  {
		persistentObject.setDeletado(false);
		entityManager.merge(persistentObject);		
	}

	public void salvarFuncao(Funcao newInstance)  {
		entityManager.persist(newInstance);
	}


}