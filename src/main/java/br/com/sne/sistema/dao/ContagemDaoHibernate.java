package br.com.sne.sistema.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.com.sne.sistema.bean.ContagemEstoque;
import br.com.sne.sistema.dao.interfaces.ContagemDaoInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class ContagemDaoHibernate implements ContagemDaoInterface {
	@Autowired
	private EntityManager entityManager;

	public void atualizarContagem(ContagemEstoque transientObject) {
		entityManager.merge(transientObject);
	}

	public ContagemEstoque carregarContagem(long id)  {
		return entityManager.find(ContagemEstoque.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<ContagemEstoque> listarContagens()  {
		Query consulta = 
				entityManager.createQuery("from ContagemEstoque where deletado = false");
		return consulta.getResultList();
	}

	public void removerContagem(ContagemEstoque persistentObject) {
		persistentObject.setDeletado(true);
		entityManager.merge(persistentObject);
	}
	
	public void restaurarContagem(ContagemEstoque persistentObject) {
		persistentObject.setDeletado(false);
		entityManager.merge(persistentObject);
	}

	public void salvarContagem(ContagemEstoque newInstance)  {
		entityManager.persist(newInstance);
	}

}
