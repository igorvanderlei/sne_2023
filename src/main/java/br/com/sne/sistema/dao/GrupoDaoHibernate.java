package br.com.sne.sistema.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.com.sne.sistema.bean.Grupo;
import br.com.sne.sistema.dao.interfaces.GrupoDaoInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class GrupoDaoHibernate implements GrupoDaoInterface {
	@Autowired
	private EntityManager entityManager;

	public void atualizarGrupo(Grupo transientObject) {
		entityManager.merge(transientObject);
	}

	public Grupo carregarGrupo(long id) {
		return entityManager.find(Grupo.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Grupo> listarGrupos() {
		Query consulta = 
				entityManager.createQuery("from Grupo where deletado = false order by nome");
		return consulta.getResultList();
	}

	public Grupo localizarGrupoPorNome(String nome){
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Grupo> criteria = builder.createQuery(Grupo.class);
		Root<Grupo> root = criteria.from(Grupo.class);
		criteria.select(root);
		criteria.where(builder.like(builder.upper(root.get("nome")),  nome));

		try {
			return entityManager.createQuery(criteria).getSingleResult();
		} catch(NoResultException ex) {
			return null;
		}
	}

	public Grupo localizarGrupoPorCodigo(String codigo) {
		try {
			Query consulta = entityManager.createQuery("from Grupo where codigo like ?1 and deletado = false");
			consulta.setParameter(1, codigo);
			return (Grupo) consulta.getSingleResult();
		} catch(NoResultException ex) {
			return null;
		}
	}

	public void removerGrupo(Grupo persistentObject)  {
		persistentObject.setDeletado(true);
		entityManager.merge(persistentObject);
	}

	public void restaurarGrupo(Grupo persistentObject){
		persistentObject.setDeletado(false);
		entityManager.merge(persistentObject);
	}

	public void salvarGrupo(Grupo newInstance){
		entityManager.persist(newInstance);
	}

	@SuppressWarnings("unchecked")
	public List<Grupo> listarGruposExcluidos() {
		Query consulta = entityManager.createQuery("from Grupo where deletado = true", Grupo.class);
		return consulta.getResultList();
	}


}