package br.com.sne.sistema.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.com.sne.sistema.bean.Funcionario;
import br.com.sne.sistema.bean.OsDePassagem;
import br.com.sne.sistema.dao.interfaces.OsDePassagemDaoInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class OsDePassagemDaoHibernate implements OsDePassagemDaoInterface {
	@Autowired
	private EntityManager entityManager;

	public void atualizarOsDePassagem(final OsDePassagem o)  {
		entityManager.merge(o);
		entityManager.flush();
	}

	public OsDePassagem carregarOsDePassagem(long id)  {
		Query consulta = entityManager.createQuery("from OsDePassagem where id = ?1 and deletado = false");
		consulta.setParameter(1, id);
		return (OsDePassagem) consulta.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<OsDePassagem> listarOsDePassagem()  {
		Query consulta = entityManager.createQuery("from OsDePassagem where deletado = false");
		return consulta.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<OsDePassagem> listarOsDePassagem(Funcionario f )  {
		Query consulta = entityManager.createQuery("from OsDePassagem o where o.funcionario.id = ?1 and deletado = false");
		consulta.setParameter(1, f.getId());
		return consulta.getResultList();
	}

	public void removerOsDePassagem(OsDePassagem persistentObject)  {
		persistentObject.setDeletado(true);
		entityManager.merge(persistentObject);
	}

	public void restaurarOsDePassagem(OsDePassagem persistentObject)  {
		persistentObject.setDeletado(false);
		entityManager.merge(persistentObject);
	}

	public void salvarOsDePassagem(OsDePassagem newInstance)  {
		entityManager.persist(newInstance);
	}
}