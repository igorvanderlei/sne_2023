package br.com.sne.sistema.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.com.sne.sistema.bean.EquipamentoSublocado;
import br.com.sne.sistema.dao.interfaces.EquipamentoSublocadoDaoInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class EquipamentoSublocadoDaoHibernate implements EquipamentoSublocadoDaoInterface {
	@Autowired
	private EntityManager entityManager;

	public void atualizarEquipamentoSublocado(EquipamentoSublocado transientObject)  {
		entityManager.merge(transientObject);
	}

	public EquipamentoSublocado carregarEquipamentoSublocado(long id)  {
		return entityManager.find(EquipamentoSublocado.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<EquipamentoSublocado> listarEquipamentosSublocados()  {
		Query consulta = 
				entityManager.createQuery("from EquipamentoSublocado where deletado = false");
		return consulta.getResultList();
	}

	public void removerEquipamentoSublocado(EquipamentoSublocado persistentObject)  {
		persistentObject.setDeletado(true);
		entityManager.merge(persistentObject);	
	}
	
	public void restaurarEquipamentoSublocado(EquipamentoSublocado persistentObject)  {
		persistentObject.setDeletado(false);
		entityManager.merge(persistentObject);	
	}

	public void salvarEquipamentoSublocado(EquipamentoSublocado newInstance)  {
		entityManager.persist(newInstance);
	}
}