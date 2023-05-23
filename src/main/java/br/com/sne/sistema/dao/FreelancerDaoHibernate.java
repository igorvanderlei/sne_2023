package br.com.sne.sistema.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.com.sne.sistema.bean.Freelancer;
import br.com.sne.sistema.dao.interfaces.FreelancerDaoInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class FreelancerDaoHibernate implements FreelancerDaoInterface {
	@Autowired
	private EntityManager entityManager;
	
	public void atualizarFreelancer(Freelancer transientObject) {
		entityManager.merge(transientObject);
	}

	public Freelancer carregarFreelancer(long id) {
		return entityManager.find(Freelancer.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Freelancer> listarFreelancers() {
		Query consulta = 
				entityManager.createQuery("from Freelancer where deletado = false");
		return consulta.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Freelancer> listarFreelancersExcluidos() {
		Query consulta = 
				entityManager.createQuery("from Freelancer where deletado = true");
		return consulta.getResultList();
	}

	public void removerFreelancer(Freelancer persistentObject) {
		persistentObject.setDeletado(true);
		entityManager.merge(persistentObject);
	}

	public void restaurarFreelancer(Freelancer persistentObject) {
		persistentObject.setDeletado(false);
		entityManager.merge(persistentObject);	
	}	

	public void salvarFreelancer(Freelancer newInstance) {
		entityManager.persist(newInstance);
	}
	

}