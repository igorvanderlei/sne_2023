package br.com.sne.sistema.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.com.sne.sistema.bean.FornecedorTerceirizado;
import br.com.sne.sistema.dao.interfaces.FornecedorTerceirizadoDaoInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class FornecedorTerceirizadoDaoHibernate implements FornecedorTerceirizadoDaoInterface {
	@Autowired
	private EntityManager entityManager;

	public void atualizarFornecedorTerceirizado(FornecedorTerceirizado transientObject) {
		entityManager.merge(transientObject);
	}

	public FornecedorTerceirizado carregarFornecedorTerceirizado(long id) {
		return entityManager.find(FornecedorTerceirizado.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<FornecedorTerceirizado> listarFornecedorTerceirizados() {
		Query consulta = entityManager.createQuery("from FornecedorTerceirizado where deletado = false order by nome");
		return consulta.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<FornecedorTerceirizado> listarFornecedorTerceirizadosExcluidos() {
		Query consulta = entityManager.createQuery("from FornecedorTerceirizado where deletado = true");
		return consulta.getResultList();
	}

	public void removerFornecedorTerceirizado(FornecedorTerceirizado persistentObject) {
		persistentObject.setDeletado(true);
		entityManager.merge(persistentObject);
	}

	public void restaurarFornecedorTerceirizado(FornecedorTerceirizado persistentObject) {
		persistentObject.setDeletado(false);
		entityManager.merge(persistentObject);
	}	

	public FornecedorTerceirizado localizarFornecedorTerceirizadoPorCodigo(String codigo) {
		Query consulta = entityManager.createQuery("from FornecedorTerceirizado where codigo like ?1 and deletado = false");
		consulta.setParameter(1, codigo);
		try {
			return (FornecedorTerceirizado) consulta.getSingleResult();
		} catch(NoResultException ex) {
			return null;
		}
	}

	public void salvarFornecedorTerceirizado(FornecedorTerceirizado newInstance) {
		entityManager.persist(newInstance);
	}
}