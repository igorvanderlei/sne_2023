package br.com.sne.sistema.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.com.sne.sistema.bean.Fornecedor;
import br.com.sne.sistema.bean.RegistroSublocacao;
import br.com.sne.sistema.dao.interfaces.RegistroSublocacaoDaoInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class RegistroSublocacaoDaoHibernate implements RegistroSublocacaoDaoInterface {
	@Autowired
	private EntityManager entityManager;

	public void atualizarRegistroSublocacao(RegistroSublocacao transientObject) {
		entityManager.merge(transientObject);	
	}

	public RegistroSublocacao carregarRegistroSublocacao(long id) {
		return entityManager.find(RegistroSublocacao.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<RegistroSublocacao> listarRegistroSublocacaos() {
		Query consulta = 
				entityManager.createQuery("from RegistroSublocacao where deletado = false");
		return consulta.getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<RegistroSublocacao> listarRegistroSublocacaosPendentesPorFornecedor(Fornecedor f) {
		Query consulta = 
				entityManager.createQuery("from RegistroSublocacao where deletado = false and finalizada = false and fornecedor.id = ?1");
		consulta.setParameter(1, f.getId());
		return consulta.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<RegistroSublocacao> listarRegistroSublocacaosExcluidos() {
		Query consulta = 
				entityManager.createQuery("from RegistroSublocacao where deletado = true");
		return consulta.getResultList();
	}
	
	public void removerRegistroSublocacao(RegistroSublocacao persistentObject) {
		persistentObject.setDeletado(true);
		entityManager.merge(persistentObject);	
	}
	
	public void restaurarRegistroSublocacao(RegistroSublocacao persistentObject) {
		persistentObject.setDeletado(false);
		entityManager.merge(persistentObject);		
	}

	public void salvarRegistroSublocacao(RegistroSublocacao newInstance) {
		entityManager.persist(newInstance);
	}
}