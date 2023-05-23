package br.com.sne.sistema.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.com.sne.sistema.bean.DadosBancarios;
import br.com.sne.sistema.bean.Fornecedor;
import br.com.sne.sistema.dao.interfaces.FornecedorDaoInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class FornecedorDaoHibernate implements FornecedorDaoInterface {
	@Autowired
	private EntityManager entityManager;
	
	public void atualizarFornecedor(Fornecedor transientObject)  {
		entityManager.merge(transientObject);	
	}

	public Fornecedor carregarFornecedor(long id)  {
		return entityManager.find(Fornecedor.class, id);
	}
	
	public Fornecedor carregarFornecedor(DadosBancarios dadosBancarios)  {
		try {
			Query consulta = entityManager.createQuery("from Fornecedor where ("
					+ "dadosbancarios1 = ?1 or "
					+ "dadosbancarios2 = ?2) "
					+ "and deletado = false");
			consulta.setParameter(1, dadosBancarios.getId());
			consulta.setParameter(2, dadosBancarios.getId());
			return (Fornecedor) consulta.getSingleResult();
		} catch(NoResultException ex) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Fornecedor> listarFornecedores()  {
		Query consulta = 
				entityManager.createQuery("from Fornecedor where deletado = false order by nome");
		return consulta.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Fornecedor> listarFornecedoresExcluidos()  {
		Query consulta = 
				entityManager.createQuery("from Fornecedor where deletado = true order by nome");
		return consulta.getResultList();	
	}

	public void removerFornecedor(Fornecedor persistentObject)  {
		persistentObject.setDeletado(true);
		entityManager.merge(persistentObject);	
	}

	public void restaurarFornecedor(Fornecedor persistentObject)  {
		persistentObject.setDeletado(false);
		entityManager.merge(persistentObject);
	}	
	
	public Fornecedor localizarFornecedorPorCodigo(String codigo)  {
		try {
			Query consulta = entityManager.createQuery("from Fornecedor where codigo like ?1 and deletado = false");
			consulta.setParameter(1, codigo);
			return (Fornecedor) consulta.getSingleResult();
		} catch(NoResultException ex) {
			return null;
		}	
	}

	public void salvarFornecedor(Fornecedor newInstance)  {
		entityManager.persist(newInstance);
	}
	

}