package br.com.sne.sistema.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.sne.sistema.bean.DescricaoEquipamento;
import br.com.sne.sistema.bean.Funcao;
import br.com.sne.sistema.bean.Funcionario;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.StatusEquipamento;
import br.com.sne.sistema.dao.interfaces.DescricaoEquipamentoDaoInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class DescricaoEquipamentoDaoHibernate implements DescricaoEquipamentoDaoInterface {
	@Autowired
	private EntityManager entityManager;
	
	public void atualizarDescricaoEquipamento(DescricaoEquipamento transientObject)  {
		entityManager.merge(transientObject);	
	}

	public DescricaoEquipamento carregarDescricaoEquipamento(long id)  {
		return entityManager.find(DescricaoEquipamento.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<DescricaoEquipamento> listarDescricoesEquipamentos()  {
		Query consulta = entityManager.createQuery("from DescricaoEquipamento where deletado = false", DescricaoEquipamento.class);
		return consulta.getResultList();
	}

	public void removerDescricaoEquipamento(DescricaoEquipamento persistentObject)  {
		persistentObject.setDeletado(true);
		entityManager.merge(persistentObject);		
	}
	
	public void restaurarDescricaoEquipamento(DescricaoEquipamento persistentObject)  {
		persistentObject.setDeletado(false);
		entityManager.merge(persistentObject);	
	}

	public void salvarDescricaoEquipamento(DescricaoEquipamento newInstance)  {
		entityManager.persist(newInstance);
	}
	
	public int contarEquipamentos(DescricaoEquipamento desc, StatusEquipamento status) {
		Query consulta = entityManager.createQuery("select count(*) from Equipamento as equip join equip.descricaoEquipamento as descricao where equip.class <> EquipamentoSublocado and descricao.id=?1 and equip.status=?2 and equip.deletado = false");
		consulta.setParameter(1, desc.getId());
		consulta.setParameter(2, status);
		long result = (Long) consulta.getSingleResult();
		return (int) result;
	}
	
	public int contarTodosEquipamentos(DescricaoEquipamento desc) {
		Query consulta = entityManager.createQuery("select count(*) from Equipamento as equip join equip.descricaoEquipamento as descricao where equip.class <> EquipamentoSublocado and  descricao.id=?1 and equip.deletado = false ");
		consulta.setParameter(1, desc.getId());
		long result = (Long) consulta.getSingleResult();
		return (int) result;	
	}
	
	public int contarTodosFuncionario(Funcao func) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> counter = builder.createQuery(Long.class);
		Root<Funcionario> root = counter.from(Funcionario.class);
		Join<Funcionario, Funcao> funcaoJoin = root.join( "funcao", JoinType.RIGHT );
		counter.select(builder.count(root));
		counter.where(builder.equal(funcaoJoin.get("id"), func.getId()));
		return (int) entityManager.createQuery(counter).getSingleResult().longValue();
	}
}