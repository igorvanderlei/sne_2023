package br.com.sne.sistema.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import br.com.sne.sistema.bean.Cliente;
import br.com.sne.sistema.bean.EquipamentoEnviado;
import br.com.sne.sistema.bean.Funcionario;
import br.com.sne.sistema.bean.LocalEvento;
import br.com.sne.sistema.bean.OrdemServico;
import br.com.sne.sistema.bean.OrdemServicoSemEquipamento;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.StatusEquipamento;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.StatusOS;
import br.com.sne.sistema.dao.interfaces.OrdemServicoDaoInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
@Transactional
public class OrdemServicoDaoHibernate implements OrdemServicoDaoInterface {
	@Autowired
	private EntityManager entityManager;


	@Transactional
	public void atualizarOrdemServico(final OrdemServico o) {
		if(o.getEquipamentoEnviado()!=null&&o.getEquipamentoEnviado().size() >=0 ) {
			for(EquipamentoEnviado e: o.getEquipamentoEnviado()) {
				if(e.isStatus() == false) {
					e.getEquipamento().setStatus(StatusEquipamento.LOCADO);
					entityManager.merge(e.getEquipamento());
				}
			}
		}
		entityManager.merge(o);
		//getHibernateTemplate().flush();
	}

	public void atualizarEquipamentoEnviado(EquipamentoEnviado transientObject) {
		entityManager.merge(transientObject);
	}


	public OrdemServico carregarOrdemServico(long id) {
		Query consulta = entityManager.createQuery("from OrdemServico where id = ?1 and deletado = false");
		consulta.setParameter(1, id);
		return (OrdemServico) consulta.getSingleResult();
	}

	public List<OrdemServico> listarOrdemServicos(int campo, String texto) {
		ArrayList<StatusOS> conjuntoStatus = new ArrayList<StatusOS>();
		conjuntoStatus.add(StatusOS.OS_EMERGENCIAL);
		conjuntoStatus.add(StatusOS.OS_EMERGENCIAL_CONCLUIDA); 
		conjuntoStatus.add(StatusOS.OS_EMERGENCIAL_INICIADA);
		conjuntoStatus.add(StatusOS.OS_SEM_EQUIPAMENTO);
		conjuntoStatus.add(StatusOS.OS_SEM_EQUIPAMENTO_CONCLUIDA);

		CriteriaQuery<OrdemServico> crit = montarCriteriaLista(campo, texto, conjuntoStatus, -1);
		return entityManager.createQuery(crit).setMaxResults(100).getResultList();
	}

	public List<OrdemServico> listarOrdemServicosAprovadas(int campo, String texto) {
		ArrayList<StatusOS> conjuntoStatus = new ArrayList<StatusOS>();
		for(StatusOS st : StatusOS.values())
			if(st != StatusOS.APROVADA)
				conjuntoStatus.add(st);
		CriteriaQuery<OrdemServico> crit = montarCriteriaLista(campo, texto, conjuntoStatus, -1);
		return entityManager.createQuery(crit).setMaxResults(100).getResultList();
	}

	private CriteriaQuery<OrdemServico> montarCriteriaLista (int campo, String texto, ArrayList<StatusOS> v, long idFuncionario) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<OrdemServico> criteria = builder.createQuery(OrdemServico.class);
		Root<OrdemServico> root = criteria.from(OrdemServico.class);
		Join<OrdemServico, Cliente> clienteJoin = root.join( "cliente" );
		Join<OrdemServico, LocalEvento> localJoin = root.join( "local" );
		Join<OrdemServico, Funcionario> funcionarioJoin = root.join( "funcionario" );
		criteria.select(root);
		Predicate filtro = builder.and(builder.equal(root.get("deletado"), false),
				builder.not(builder.in(root.get("status")).value(v)));

		if(idFuncionario > 0) {
			filtro = builder.and(filtro, 
					builder.or( 
							builder.equal(root.get("funcionario"), idFuncionario),
							builder.equal(root.get("vendedorConjunto"), idFuncionario)
							)
					);
		}

		//{"Id", "Cliente", "Local", "Evento", "Data", "X", "Funcionário", "Status"};
		switch(campo) {
		case 1:
			criteria.where(builder.and( builder.like(root.get("id").as(String.class),  "%" + texto + "%"),
					filtro));
			break;
		case 2:
			criteria.where(builder.and( builder.like(builder.upper(clienteJoin.get("nome")),  "%" + texto.toUpperCase() + "%"),
					filtro));
			break;				
		case 3:
			criteria.where(builder.and( builder.like(builder.upper(localJoin.get("nome")),  "%" + texto.toUpperCase() + "%"),
					filtro));
			break;
		case 4:
			criteria.where(builder.and( builder.like(builder.upper(root.get("nomeEvento")),  "%" + texto.toUpperCase() + "%"),
					filtro));
			break;
		case 5:
			criteria.where(builder.and(
					builder.like(
							builder.function("to_char", 
									String.class, 
									root.get("dataInicio"), 
									builder.literal("dd/MM/yyyy")),  
							"%" + texto + "%"),
					filtro));
			break;
		case 6:
			criteria.where(builder.and( builder.like(builder.upper(funcionarioJoin.get("nome")),  "%" + texto.toUpperCase() + "%"),
					filtro));
			break;
		case 7:
			boolean passou = false;
			ArrayList<StatusOS> conjuntoStatus = new ArrayList<StatusOS>();
			if("estornada".indexOf(texto.toLowerCase())!= -1) {
				conjuntoStatus.add(StatusOS.ESTORNADA);
				passou = true;
			}
			if("recusada".indexOf(texto.toLowerCase())!= -1) {
				conjuntoStatus.add(StatusOS.RECUSADA);
				passou = true;
			}
			if("concluida".indexOf(texto.toLowerCase())!= -1) {
				conjuntoStatus.add(StatusOS.CONCLUIDA);
				passou = true;
			}
			if("em realizacao".indexOf(texto.toLowerCase())!= -1) {
				conjuntoStatus.add(StatusOS.EM_REALIZACAO);
				passou = true;
			}
			if("aprovada".indexOf(texto.toLowerCase())!= -1) {
				conjuntoStatus.add(StatusOS.APROVADA);
				passou = true;
			}
			if("pendente".indexOf(texto.toLowerCase())!= -1) {
				conjuntoStatus.add(StatusOS.PENDENTE);
				passou = true;
			}
			if(!passou) {
				criteria.where(builder.and( builder.lt(root.get("id"), 0l),
						filtro));
				break;
			}
			criteria.where(builder.and( 
					builder.in(root.get("status")).value(conjuntoStatus),
					filtro));
			break;
		}
		return criteria;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<OrdemServico> listarOrdemServicosEnviarEquipamento() {
		String conjuntoStatus = "(" + StatusOS.APROVADA.ordinal() + ", " + StatusOS.EM_REALIZACAO.ordinal() + 
				", " + StatusOS.OS_EMERGENCIAL.ordinal() + ", " + StatusOS.OS_EMERGENCIAL_INICIADA.ordinal()  +")";

		Query consulta = entityManager.createQuery("from OrdemServico where deletado = false and status in " + conjuntoStatus );
		return consulta.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<OrdemServico> listarOrdemServicosEmergencial() {
		String conjuntoStatus = "(" + StatusOS.OS_EMERGENCIAL.ordinal() + ", " + StatusOS.OS_EMERGENCIAL_CONCLUIDA.ordinal() +  ", " + StatusOS.OS_EMERGENCIAL_INICIADA.ordinal() + ")";
		Query consulta = entityManager.createQuery("from OrdemServico where deletado = false and status in " + conjuntoStatus );
		return consulta.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<OrdemServico> listarOrdemServicosEmergencialAberta() {
		String conjuntoStatus = "(" + StatusOS.OS_EMERGENCIAL.ordinal() + ", " + StatusOS.OS_EMERGENCIAL_INICIADA.ordinal() + ")";
		Query consulta = entityManager.createQuery("from OrdemServico where deletado = false and status in " + conjuntoStatus );
		return consulta.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<OrdemServico> listarOrdemServicosExcluidas() {
		Query consulta = entityManager.createQuery("from OrdemServico where deletado = true");
		return consulta.getResultList();
	}

	public List<OrdemServico> listarOrdemServicos(int campo, String texto,Funcionario f ) {
		ArrayList<StatusOS> conjuntoStatus = new ArrayList<StatusOS>();
		conjuntoStatus.add(StatusOS.OS_EMERGENCIAL);
		conjuntoStatus.add(StatusOS.OS_EMERGENCIAL_CONCLUIDA); 
		conjuntoStatus.add(StatusOS.OS_EMERGENCIAL_INICIADA);
		conjuntoStatus.add(StatusOS.OS_SEM_EQUIPAMENTO);
		conjuntoStatus.add(StatusOS.OS_SEM_EQUIPAMENTO_CONCLUIDA);
		CriteriaQuery<OrdemServico> crit = montarCriteriaLista(campo, texto, conjuntoStatus, f.getId());
		return entityManager.createQuery(crit).setMaxResults(100).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<OrdemServico> listarOrdemServicos(Date d ) {
		String conjuntoStatus = "(" + StatusOS.APROVADA.ordinal() + ", " + StatusOS.EM_REALIZACAO.ordinal() + 
				", " + StatusOS.OS_EMERGENCIAL.ordinal() + ", " + StatusOS.OS_EMERGENCIAL_INICIADA.ordinal()  +")"; //+ ", " + StatusOS.CONCLUIDA.ordinal()
		Query consulta = entityManager.createQuery("from OrdemServico o where o.dataMontagem = ?1 and deletado = false and status in "+ conjuntoStatus);
		consulta.setParameter(1, d);
		return consulta.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<OrdemServico> listarOrdemServicosPagamento() {
		String conjuntoStatus = "(" + StatusOS.PENDENTE.ordinal() + ", " + StatusOS.APROVADA.ordinal() + ", " + StatusOS.CONCLUIDA.ordinal() + 
				", " + StatusOS.EM_REALIZACAO.ordinal() + ", " + StatusOS.OS_SEM_EQUIPAMENTO.ordinal() + 
				", " + StatusOS.OS_SEM_EQUIPAMENTO_CONCLUIDA.ordinal() + ")";
		Query consulta = entityManager.createQuery("from OrdemServico o where  deletado = false and status in "+ conjuntoStatus);
		return consulta.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<OrdemServico> listarOrdemServicosPagamento(Date inicio, Date fim,boolean osExtra) {
		String conjuntoStatus = "(" + StatusOS.PENDENTE.ordinal() + ", " + StatusOS.APROVADA.ordinal() + ", " + StatusOS.CONCLUIDA.ordinal() + 
				", " + StatusOS.EM_REALIZACAO.ordinal() + ", " + StatusOS.OS_SEM_EQUIPAMENTO.ordinal() + 
				", " + StatusOS.OS_SEM_EQUIPAMENTO_CONCLUIDA.ordinal() + ")";
		Query consulta = null;
		if(osExtra)
			consulta = entityManager.createQuery("from OrdemServico o where  deletado = false and cast(dataInicio as date) between ?1 and ?2 and status in " + conjuntoStatus );
		else
			consulta = entityManager.createQuery("from OrdemServico o where  deletado = false and cast(dataInicio as date) between ?1 and ?2 and OSOriginal = null and status in " + conjuntoStatus );
		consulta.setParameter(1, inicio);
		consulta.setParameter(2, fim);
		return consulta.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<OrdemServico> listarOrdemServicosPagamento(Date inicio, Date fim,Funcionario f, boolean osExtra) {
		String conjuntoStatus = "(" + StatusOS.PENDENTE.ordinal() + ", " + StatusOS.APROVADA.ordinal() + ", " + StatusOS.CONCLUIDA.ordinal() + 
				", " + StatusOS.EM_REALIZACAO.ordinal() + ", " + StatusOS.OS_SEM_EQUIPAMENTO.ordinal() + 
				", " + StatusOS.OS_SEM_EQUIPAMENTO_CONCLUIDA.ordinal() + ")";
		Query consulta = null;
		if(osExtra)
			consulta = entityManager.createQuery("from OrdemServico o where  deletado = false and cast(dataInicio as date) between ?1 and ?2  and funcionario = ?3 and status in " + conjuntoStatus );
		else
			consulta = entityManager.createQuery("from OrdemServico o where  deletado = false and cast(dataInicio as date) between ?1 and ?2  and funcionario = ?3 and OSOriginal = id and status in " + conjuntoStatus );
		consulta.setParameter(1, inicio);
		consulta.setParameter(2, fim);
		consulta.setParameter(3, f.getId());
		return consulta.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrdemServico> listarOrdemServicosPagamento(Date inicio, Date fim, StatusOS status) {
		Query consulta = entityManager.createQuery("from OrdemServico o where  deletado = false and cast(dataInicio as date) between ?1 and ?2 and status = " + status.ordinal());
		consulta.setParameter(1, inicio);
		consulta.setParameter(2, fim);
		return consulta.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrdemServico> listarOrdemServicosPagamento(StatusOS status) {
		Query consulta = entityManager.createQuery("from OrdemServico o where  deletado = false and status = " + status.ordinal());
		return consulta.getResultList();
	}


	@Override
	@SuppressWarnings("unchecked")
	public List<OrdemServico> listarOrdemServicosEstornada() {
		Query consulta = entityManager.createQuery("from OrdemServico o where  deletado = false and status =  " + StatusOS.ESTORNADA.ordinal());
		return consulta.getResultList();
	}


	@SuppressWarnings("unchecked")
	public List<OrdemServico> listarOrdemEmAndamento( ) {
		String conjuntoStatus = "(" + StatusOS.EM_REALIZACAO.ordinal() + ", " + StatusOS.OS_EMERGENCIAL_INICIADA.ordinal() + ")";
		Query consulta = entityManager.createQuery("from OrdemServico o where  deletado = false and status in "+ conjuntoStatus);
		return consulta.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<OrdemServico> listarOrdemAtiva( ) {
		String conjuntoStatus = "(" + StatusOS.APROVADA.ordinal() + ", " + StatusOS.EM_REALIZACAO.ordinal() + 
				", " + StatusOS.OS_EMERGENCIAL.ordinal() + ", " + StatusOS.OS_EMERGENCIAL_INICIADA.ordinal() + ")";


		Query consulta = entityManager.createQuery("from OrdemServico o where  deletado = false and status in "+ conjuntoStatus);
		return consulta.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<OrdemServico> listarOrdemServicosPorEquipamento(long equipamento ) {
		Query consulta = entityManager.createQuery("select os from OrdemServico as os " +
				"									inner join os.equipamentoEnviado as eq " +
				"Where eq.equipamento = ?1 and os.deletado = false " +
				"order by os.dataInicio desc");
		consulta.setParameter(1, equipamento);
		return consulta.getResultList();
	}


	//TODO: Arrumar a query
	@Override
	@SuppressWarnings("unchecked")
	public List<OrdemServico> listarOrdemServicosEmergencialAlertaAberto() {
		String conjuntoStatus = "(" + StatusOS.OS_EMERGENCIAL.ordinal() + ", " + StatusOS.OS_EMERGENCIAL_INICIADA.ordinal() + 
				", " + StatusOS.OS_EMERGENCIAL_CONCLUIDA.ordinal() + ")";


		Query consulta = entityManager.createQuery("from OrdemServico o " +
				"where o.deletado = false and  o.status in "+ conjuntoStatus  +
				"and o.id not in ( select ose.ordemServicoEmergencial.id from OrdemServicoSemEquipamento ose) ") ;
		//"and o.id not in ( select al.osEmergencial.id from AlertaOSEmergencial al where al.status = true) ") ;
		return consulta.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrdemServicoSemEquipamento> listarOrdemServicoSemEquipamento() {
		Query consulta = entityManager.createQuery("from OrdemServicoSemEquipamento o ") ;
		return consulta.getResultList();
	}

	public OrdemServico localizaOrdemServicosPorEquipamento(long equipamento) {
		try {
			Query consulta = entityManager.createQuery("select os from OrdemServico as os " +
					"									inner join os.equipamentoEnviado as eq " +
					"Where eq.id = ?1 and os.deletado = false");
			consulta.setParameter(1, equipamento);
			return (OrdemServico)consulta.getSingleResult();
		} catch(NoResultException ex) {
			return null;
		}
	}

	public void removerOrdemServico(OrdemServico persistentObject) {
		persistentObject.setDeletado(true);
		entityManager.merge(persistentObject);
	}

	public void restaurarOrdemServico(OrdemServico persistentObject) {
		persistentObject.setDeletado(false);
		entityManager.merge(persistentObject);
	}

	public void salvarOrdemServico(OrdemServico newInstance) {
		entityManager.persist(newInstance);
	}

	public void apagarOS(Date dataLimite){
		Query removerParcelas = entityManager.createNativeQuery("delete from receita where ordemservico in " +
				"(select id from ordemservico where deletado=false and cast(dataInicio as date) < ?1 and status = "+ + StatusOS.CONCLUIDA.ordinal() +"); " +
				"update ordemservico set status = "+ StatusOS.ESTORNADA.ordinal() +", observacoes='ROTINA DE RETIRADA' where id in " +
				"(select id from ordemservico where deletado=false and cast(dataInicio as date) < ?2 and status = "+ + StatusOS.CONCLUIDA.ordinal() +"); ");
		removerParcelas.setParameter(1, dataLimite);
		removerParcelas.setParameter(2, dataLimite);
		removerParcelas.executeUpdate();
	}	

}