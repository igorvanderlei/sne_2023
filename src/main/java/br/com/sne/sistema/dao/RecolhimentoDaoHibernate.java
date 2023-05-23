package br.com.sne.sistema.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.com.sne.sistema.bean.Equipamento;
import br.com.sne.sistema.bean.Funcionario;
import br.com.sne.sistema.bean.Recolhimento;
import br.com.sne.sistema.dao.interfaces.RecolhimentoDaoInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class RecolhimentoDaoHibernate implements RecolhimentoDaoInterface  {
	@Autowired
	private EntityManager entityManager;

	public void atualizarRecolhimento(Recolhimento transientObject)  {
		entityManager.merge(transientObject);
	}

	public Recolhimento carregarRecolhimento(long id)  {
		return (Recolhimento) entityManager.find(Recolhimento.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Recolhimento> listarRecolhimentos()  {
		Query consulta = entityManager.createQuery("from Recolhimento where deletado = false");
		return consulta.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Recolhimento> listarRecolhimentosExcluidos()  {
		Query consulta = entityManager.createQuery("from Recolhimento where deletado = true");
		return consulta.getResultList();
	}

	public Recolhimento localizarRecolhimentoPorEquipamento(Equipamento eq)  {
		Query consulta = entityManager.createQuery("from Recolhimento where equipamento.id = ?1 and deletado = false and status = false");
		consulta.setParameter(1, eq.getId());
		return (Recolhimento) consulta.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<Recolhimento> localizarRecolhimentoPorTecnico(Funcionario tec)  {
		Query consulta = entityManager.createQuery("from Recolhimento where tecnicoResponsavel.id = ?1 and deletado = false");
		consulta.setParameter(1, tec.getId());
		return consulta.getResultList();
	}

	public void salvarRecolhimento(Recolhimento newInstance)  {
		entityManager.persist(newInstance);
	}
}