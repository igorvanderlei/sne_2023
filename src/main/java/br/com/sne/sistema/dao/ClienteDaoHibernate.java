package br.com.sne.sistema.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.com.sne.sistema.bean.Cliente;
import br.com.sne.sistema.bean.Funcionario;
import br.com.sne.sistema.dao.interfaces.ClienteDaoInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class ClienteDaoHibernate implements ClienteDaoInterface {
	@Autowired
	private EntityManager entityManager;

	public void atualizarCliente(Cliente transientObject)  {
		entityManager.merge(transientObject);
	}

	public Cliente carregarCliente(long id)  {
		try {
			Query consulta = 
					entityManager.createQuery("from Cliente where  id=:id and deletado = false", Cliente.class);
			consulta.setParameter("id" , id);
			return (Cliente) consulta.getSingleResult();
		} catch(NoResultException ex) {
			return null;
		}	
	}

	private CriteriaQuery<Cliente> montarCriteriaLista (int campo, String texto, long idFuncionario) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Cliente> criteria = builder.createQuery(Cliente.class);
		Root<Cliente> root = criteria.from(Cliente.class);
		Join<Cliente, Funcionario> funcionarioJoin = root.join( "funcionario" );
		criteria.select(root);
		Predicate filtro;

		if(idFuncionario > 0) {
			filtro = builder.and(builder.equal(root.get("deletado"), false), 
					builder.equal(funcionarioJoin.get("id"), idFuncionario) );
		} else {
			filtro = builder.equal(root.get("deletado"), false);
		}

		//Criteria crit = getSession().createCriteria(Cliente.class);
		//{"Id", "Tipo", "Razão Social/Nome", "CNPJ/Cpf", "X", "Contato", "X", "Funcionário", "X"};
		switch(campo) {
		case 1:
		/*	Long id = 0l;
			try {
				id = Long.parseLong(texto);
			} catch (Exception e) {}

			criteria.where(builder.and( builder.equal(root.get("id"),  id),
					filtro));*/
			criteria.where(builder.and( builder.like(root.get("id").as(String.class),  "%" + texto + "%"),
					filtro));
			break;
		case 2:
			criteria.where(builder.and( builder.like(builder.upper(root.get("tipo")),  (""+texto.trim().charAt(0)).toUpperCase() ),
					filtro));
			break;				
		case 3:
			criteria.where(builder.and( builder.like(builder.upper(root.get("nome")),  "%" + texto.toUpperCase() + "%"),
					filtro));
			break;
		case 4:
			criteria.where(builder.and( builder.like(builder.upper(root.get("cnpj")),  "%" + texto.toUpperCase() + "%"),
					filtro));
			break;
		case 5:
			criteria.where(builder.and( builder.like(builder.upper(root.get("contato")),  "%" + texto.toUpperCase() + "%"),
					filtro));
			break;
		case 6:
			criteria.where(builder.and( builder.like(builder.upper(funcionarioJoin.get("nome")),  "%" + texto.toUpperCase() + "%"),
					filtro));
			break;

		}
		return criteria;
	}

	public List<Cliente> listarClientes(int campo, String texto)  {
		CriteriaQuery<Cliente> crit = montarCriteriaLista(campo, texto, -1);

		return entityManager.createQuery(crit).setMaxResults(100).getResultList();
	}

	public List<Cliente> listarClientes(int campo, String texto, Funcionario f)  {
		System.out.println(">>>>>>>>>" + f.getId());
		CriteriaQuery<Cliente> crit = montarCriteriaLista(campo, texto, f.getId());
		return entityManager.createQuery(crit).setMaxResults(100).getResultList();
	}	


	@SuppressWarnings("unchecked")
	public List<Cliente> listarClientesExcluidos()  {
		Query consulta = entityManager.createQuery("from Cliente where deletado = true");
		return consulta.getResultList();
	}



	@SuppressWarnings("unchecked")
	public List<Cliente> listarClientes(String cnpj, String razaoSocial, String contato) {
		Query consulta = entityManager.createQuery("from Cliente c where c.nome LIKE ?1 AND c.cnpj LIKE ?2 AND c.contato LIKE ?3 and deletado = false");
		consulta.setParameter(1, razaoSocial);
		consulta.setParameter(2, cnpj);
		consulta.setParameter(3, contato);
		return consulta.getResultList();
	}

	public void removerCliente(Cliente persistentObject)  {
		persistentObject.setDeletado(true);
		entityManager.merge(persistentObject);	
	}

	public void restaurarCliente(Cliente persistentObject)  {
		persistentObject.setDeletado(false);
		entityManager.merge(persistentObject);		
	}

	public void salvarCliente(Cliente newInstance)  {
		entityManager.persist(newInstance);
	}
}