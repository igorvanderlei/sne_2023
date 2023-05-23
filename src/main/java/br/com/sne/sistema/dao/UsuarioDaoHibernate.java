package br.com.sne.sistema.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.com.sne.sistema.bean.Funcionario;
import br.com.sne.sistema.bean.TipoUsuario;
import br.com.sne.sistema.bean.Usuario;
import br.com.sne.sistema.dao.interfaces.UsuarioDaoInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class UsuarioDaoHibernate implements UsuarioDaoInterface {

	@Autowired
	private EntityManager entityManager;

	public void atualizarUsuario(Usuario transientObject)  {
		entityManager.merge(transientObject);
	}

	public Usuario carregarUsuario(long id)  {
		return entityManager.find(Usuario.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> listarUsuarios()  {
		Query consulta = 
				entityManager.createQuery("from Usuario where deletado = false");
		return consulta.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> listarUsuarios(TipoUsuario tipo)  {
		Query consulta = 
				entityManager.createQuery("from Usuario where deletado = false and tipoUsuario.id = ?1");
		consulta.setParameter(1, tipo.getId());
		return consulta.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Usuario> listarUsuarios(Funcionario func)  {
		Query consulta = 
				entityManager.createQuery("from Usuario where deletado = false and funcionario.id = ?1");
		consulta.setParameter(1, func.getId());
		return consulta.getResultList();		
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> listarUsuariosExcluidos()  {
		Query consulta = 
				entityManager.createQuery("from Usuario where deletado = true");
		return consulta.getResultList();	
	}

	public void removerUsuario(Usuario persistentObject)  {
		persistentObject.setDeletado(true);
		entityManager.merge(persistentObject);
	}

	public void restaurarUsuario(Usuario persistentObject)  {
		persistentObject.setDeletado(false);
		entityManager.merge(persistentObject);	
	}

	public void salvarUsuario(Usuario newInstance)  {
		entityManager.persist(newInstance);
	}


	public Usuario localizarUsuarioPorLogin(String login)   {
		try {
			Query consulta = 
					entityManager.createQuery("from Usuario where login = ?1 and deletado = false");
			consulta.setParameter(1, login);
			return (Usuario) consulta.getSingleResult();
		} catch(NoResultException ex) {
			return null;
		}
	}


}