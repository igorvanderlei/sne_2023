package br.com.sne.sistema.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.com.sne.sistema.bean.DescarteEquipamento;
import br.com.sne.sistema.dao.interfaces.DescarteEquipamentoDaoInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class DescarteEquipamentoDaoHibernate implements DescarteEquipamentoDaoInterface {
	@Autowired
	private EntityManager entityManager;
	
	public void atualizarDescarteEquipamento(DescarteEquipamento transientObject)  {
		entityManager.merge(transientObject);	
	}

	public DescarteEquipamento carregarDescarteEquipamento(long id)  {
		return entityManager.find(DescarteEquipamento.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<DescarteEquipamento> listarDescarteEquipamentos()  {
		Query consulta = 
				entityManager.createQuery("from DescarteEquipamento where deletado = false");
		return consulta.getResultList();
	}
	
	public void removerDescarteEquipamento(DescarteEquipamento persistentObject)  {
			persistentObject.setDeletado(true);
			entityManager.merge(persistentObject);	
	}
	
	public void restaurarDescarteEquipamento(DescarteEquipamento persistentObject)  {
			persistentObject.setDeletado(false);
			entityManager.merge(persistentObject);	
	}

	public void salvarDescarteEquipamento(DescarteEquipamento newInstance)  {
		entityManager.persist(newInstance);
	}
}