package br.com.sne.sistema.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.com.sne.sistema.bean.Freelancer;
import br.com.sne.sistema.bean.Funcionario;
import br.com.sne.sistema.dao.interfaces.FuncionarioDaoInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Transactional
@Repository
public class FuncionarioDaoHibernate implements FuncionarioDaoInterface {
	@Autowired
	private EntityManager entityManager;

	public void atualizarFuncionario(Funcionario transientObject)  {
		entityManager.merge(transientObject);	
	}

	public Funcionario carregarFuncionario(long id)  {
		Funcionario f = entityManager.find(Funcionario.class, id);
		if(f != null && f instanceof Freelancer)
			return null;
		return f;
	}

	@SuppressWarnings("unchecked")
	public List<Funcionario> listarFuncionarios()  {
		Query consulta = 
				entityManager.createQuery("from Funcionario f where deletado = false and TYPE(f) <> Freelancer");
		return consulta.getResultList();	
	}

	@SuppressWarnings("unchecked")
	public List<Funcionario> listarFuncionariosExcluidos()  {
		Query consulta = 
				entityManager.createQuery("from Funcionario f where deletado = true and TYPE(f) <> Freelancer");
		return consulta.getResultList();
	}

	public void removerFuncionario(Funcionario persistentObject)  {
		persistentObject.setDeletado(true);
		entityManager.merge(persistentObject);
	}

	public void restaurarFuncionario(Funcionario persistentObject)  {
		persistentObject.setDeletado(false);
		entityManager.merge(persistentObject);
	}

	public void salvarFuncionario(Funcionario newInstance)  {
		entityManager.persist(newInstance);
	}

	@SuppressWarnings("unchecked")
	public List<Funcionario> procurarFuncionarioPorNome (String arg0)  {
		Query consulta = 
				entityManager.createQuery("from Funcionario f where f.nome = ?1 and f.deletado = false and TYPE(f) <> Freelancer");
		consulta.setParameter(1, arg0);
		return consulta.getResultList();	
	}

}