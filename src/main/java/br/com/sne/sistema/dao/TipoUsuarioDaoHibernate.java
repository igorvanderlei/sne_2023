package br.com.sne.sistema.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.com.sne.sistema.bean.TipoUsuario;
import br.com.sne.sistema.dao.interfaces.TipoUsuarioDaoInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class TipoUsuarioDaoHibernate implements TipoUsuarioDaoInterface {
	@Autowired
	private EntityManager entityManager;
	
	public void atualizarTipoUsuario(TipoUsuario transientObject)  {
		entityManager.merge(transientObject);
	}

	public TipoUsuario carregarTipoUsuario(long id)  {
		return entityManager.find(TipoUsuario.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<TipoUsuario> listarTipoUsuarios()  {
		Query consulta = 
				entityManager.createQuery("from TipoUsuario where deletado = false");
		return consulta.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<TipoUsuario> listarTipoUsuariosExcluidos()  {
		Query consulta = 
				entityManager.createQuery("from TipoUsuario where deletado = true");
		return consulta.getResultList();	
	}
	
	@SuppressWarnings("unchecked")
	public List<TipoUsuario> procurarTipoUsuarios(String nome)  {
		Query consulta = 
				entityManager.createQuery("from TipoUsuario where nome like ?1 and deletado = false");
		consulta.setParameter(1, nome);
		return consulta.getResultList();
	}	

	public void removerTipoUsuario(TipoUsuario persistentObject)  {
		persistentObject.setDeletado(true);
		entityManager.merge(persistentObject);
	}
	
	public void restaurarTipoUsuario(TipoUsuario persistentObject)  {
		persistentObject.setDeletado(false);
		entityManager.merge(persistentObject);
	}

	public void salvarTipoUsuario(TipoUsuario newInstance)  {
		entityManager.persist(newInstance);
	}
}