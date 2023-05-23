package br.com.sne.sistema.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.com.sne.sistema.bean.Equipamento;
import br.com.sne.sistema.bean.EquipamentoEnviado;
import br.com.sne.sistema.bean.Grupo;
import br.com.sne.sistema.bean.Recurso;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.StatusEquipamento;
import br.com.sne.sistema.dao.interfaces.EquipamentoDaoInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class EquipamentoDaoHibernate implements EquipamentoDaoInterface {
	@Autowired
	private EntityManager entityManager;

	public void atualizarEquipamento(Equipamento transientObject, String serieAntigo){
		entityManager.merge(transientObject);
	}

	public void atualizarEquipamentoDevolucao(Equipamento e){
		entityManager.merge(e);
	}

	public Equipamento carregarEquipamento(long id){
		return entityManager.find(Equipamento.class, id);
	}

	public Equipamento localizarEquipamentoCodigo(String codigo){
		try {
			Query consulta = entityManager.createQuery("from Equipamento where numeroSerie = ?1 and deletado = false");
			consulta.setParameter(1, codigo);
			return (Equipamento) consulta.getSingleResult();
		} catch(NoResultException ex) {
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	public EquipamentoEnviado localizarEquipamentoEnviado(String codigo){
		try {
			Query consulta = entityManager.createQuery("select e from EquipamentoEnviado as e " +
					"inner join e.equipamento as eq " +
					"Where eq.numeroSerie = ?1 " +
					"and e.status = false");
			consulta.setParameter(1, codigo);
			List<EquipamentoEnviado> lista = consulta.getResultList();
			if(lista.size() > 0) {
				return lista.get(0);
			} else{
				return null;
			}
		} catch(NoResultException ex) {
			return null;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public EquipamentoEnviado localizarEquipamentoEnviadoRastrear(String codigo){
		try {
			Query consulta = entityManager.createQuery("select e from EquipamentoEnviado as e " +
					"inner join e.equipamento as eq " +
					"Where eq.numeroSerie = ?1 " +
					"order by dataSaida desc");
			consulta.setParameter(1, codigo);
			List<EquipamentoEnviado> lista = consulta.getResultList();
			if(lista.size() > 0){
				return lista.get(0);
			} else {
				return null;
			}
		} catch(NoResultException ex) {
			return null;
		}
	}


	@SuppressWarnings("unchecked")
	public List<Equipamento> listarEquipamento(){
		Query consulta = entityManager.createQuery("from Equipamento as e " +
				"where e.class <> EquipamentoSublocado and deletado = false");
		return consulta.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Equipamento> listarEquipamentosExcluidos(){
		Query consulta = entityManager.createQuery("from Equipamento as e " +
				"where e.class <> EquipamentoSublocado and deletado = true");
		return consulta.getResultList();

	}

	@SuppressWarnings("unchecked")
	public List<Equipamento> listarEquipamentosPorStatus(Set<StatusEquipamento> status){
		String conjunto = "";
		for(StatusEquipamento st : status) {
			conjunto += st.ordinal()  +", ";
		}

		if(conjunto.length() > 0) {
			conjunto = "( " +conjunto.substring(0, conjunto.length() -2) + ") ";
			Query consulta = entityManager.createQuery("from Equipamento eq where deletado = false and eq.status in (" + conjunto + ")");	
			return consulta.getResultList();	
		} else {
			return new ArrayList<Equipamento>();
		}
	}	

	@SuppressWarnings("unchecked")
	public List<Equipamento> listarEquipamentosPorRecurso(Recurso r, boolean incluirSublocados) {
		Query consulta;
		if(incluirSublocados) {
			consulta = entityManager.createQuery("from Equipamento eq where eq.descricaoEquipamento.id = ?1 and deletado = false");
		} else {
			consulta = entityManager.createQuery("from Equipamento eq where eq.class <> EquipamentoSublocado and eq.descricaoEquipamento.id = ?1 and deletado = false");
		}

		consulta.setParameter(1, r.getId());
		return consulta.getResultList();	
	}

	@SuppressWarnings("unchecked")
	public List<Equipamento> listarEquipamentosPorGrupo(Grupo g, boolean incluirSublocados) {
		Query consulta;
		if(incluirSublocados) {
			consulta = entityManager.createQuery("from Equipamento eq where eq.descricaoEquipamento.grupo.id = ?1 and deletado = false");
		} else {
			consulta = entityManager.createQuery("from Equipamento eq where eq.class <> EquipamentoSublocado and eq.descricaoEquipamento.grupo.id = ?1 and deletado = false");
		}
		consulta.setParameter(1, g.getId());
		return consulta.getResultList();	
	}

	@SuppressWarnings("unchecked")
	public List<Equipamento> listarEquipamentosDisponiveis(){
		Query consulta = entityManager.createQuery("from Equipamento where status = 0 and deletado = false");
		return consulta.getResultList();
	}

	public void removerEquipamento(Equipamento persistentObject){
		persistentObject.setDeletado(true);
		entityManager.merge(persistentObject);
	}

	public void restaurarEquipamento(Equipamento persistentObject){
		persistentObject.setDeletado(false);
		entityManager.merge(persistentObject);
	}

	public void salvarEquipamento(Equipamento newInstance){
		entityManager.persist(newInstance);
	}


}