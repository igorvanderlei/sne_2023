package br.com.sne.sistema.dao;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.com.sne.sistema.bean.Funcionario;
import br.com.sne.sistema.bean.Pauta;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.StatusPauta;
import br.com.sne.sistema.dao.interfaces.PautaDaoInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class PautaDaoHibernate implements PautaDaoInterface {
	@Autowired
	private EntityManager entityManager;

	public void atualizarPauta(Pauta transientObject) {
		entityManager.merge(transientObject);
	}

	public Pauta carregarPauta(long id) {
		return entityManager.find(Pauta.class, id);
	}

	//ainda n
	private CriteriaQuery<Pauta> montarCriteriaLista (int campo, String texto, long idFuncionario) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pauta> criteria = builder.createQuery(Pauta.class);
		Root<Pauta> root = criteria.from(Pauta.class);
		Join<Pauta, Funcionario> funcionarioJoin = root.join( "funcionario" );
		criteria.select(root);
		Predicate filtro;

		if(idFuncionario > 0) {
			filtro = builder.and(builder.equal(root.get("deletado"), false), 
					builder.equal(funcionarioJoin.get("id"), idFuncionario) );
		} else {
			filtro = builder.equal(root.get("deletado"), false);
		}

		//{"Id", "Mês", "Evento", "Cliente",  "Local", "Contato", "X", "X", "Próx. Contato", "Funcionário", "Status"};
		System.out.println(campo);
		switch(campo) {
		case 1:
			criteria.where(builder.and( builder.like(root.get("id").as(String.class),  "%" + texto + "%"),
					filtro));
			break;
		case 2:
			criteria.where(builder.and( builder.like(root.get("data"),  "%" + texto + "%"),
					filtro));
			break;			
		case 3:
			criteria.where(builder.and( builder.like(builder.upper(root.get("nomeEvento")),  "%" + texto.toUpperCase() + "%"),
					filtro));
			break;
		case 4:
			criteria.where(builder.and( builder.like(builder.upper(root.get("empresa")),  "%" + texto.toUpperCase() + "%"),
					filtro));			
			break;
		case 5:
			criteria.where(builder.and( builder.like(builder.upper(root.get("localEvento")),  "%" + texto.toUpperCase() + "%"),
					filtro));
			break;
		case 6:
			criteria.where(builder.and( builder.like(builder.upper(root.get("contato")),  "%" + texto.toUpperCase() + "%"),
					filtro));
			break;
		case 7:
			criteria.where(builder.and(
					builder.like(
							builder.function("to_char", 
									String.class, 
									root.get("proxContato"), 
									builder.literal("dd/MM/yyyy")),  
							"%" + texto + "%"),
					filtro));
			break;
		case 8:	
			criteria.where(builder.and( builder.like(builder.upper(funcionarioJoin.get("nome")),  "%" + texto.toUpperCase() + "%"),
					filtro));
			break;
		case 9:
			if("aberto".indexOf(texto.toLowerCase())!= -1) 
				criteria.where(builder.and( builder.equal(root.get("status"),  StatusPauta.ABERTO),
						filtro));
			else if("confirmado".indexOf(texto.toLowerCase())!= -1)
				criteria.where(builder.and( builder.equal(root.get("status"),  StatusPauta.CONFIRMADO),
						filtro));
			else if("cancelado".indexOf(texto.toLowerCase())!= -1)
				criteria.where(builder.and( builder.equal(root.get("status"),  StatusPauta.CANCELADO),
						filtro));
			else if("follow up".indexOf(texto.toLowerCase())!= -1)
				criteria.where(builder.and( builder.equal(root.get("status"),  StatusPauta.FOLLOW_UP),
						filtro));
			else
				criteria.where(builder.and( builder.lt(root.get("id"), 0l),
						filtro));
			break;

		}
		return criteria;
	}

	public List<Pauta> listarPautas(int campo, String texto) {
		CriteriaQuery<Pauta> crit = montarCriteriaLista(campo, texto, -1);
		return entityManager.createQuery(crit).setMaxResults(100).getResultList();
	}

	public List<Pauta> listarPautas(int campo, String texto, Funcionario f) {
		CriteriaQuery<Pauta> crit = montarCriteriaLista(campo, texto, f.getId());
		return entityManager.createQuery(crit).setMaxResults(100).getResultList();
	}

	public void salvarPauta(Pauta newInstance) {
		entityManager.persist(newInstance);
	}

	public void removerPauta(Pauta persistentObject) {
		persistentObject.setDeletado(true);
		persistentObject.setStatus(StatusPauta.CANCELADO);
		entityManager.merge(persistentObject);	
	}

	@SuppressWarnings("unchecked")
	public List<Pauta> listarPautasCanceladas() {
		Query consulta = entityManager.createQuery("from Pauta where status = ?1");
		consulta.setParameter(1, StatusPauta.CANCELADO);
		return consulta.getResultList();
	}

	public void restaurarPauta(Pauta persistentObject) {
		persistentObject.setStatus(StatusPauta.ABERTO);
		entityManager.merge(persistentObject);	
	}

	public void confirmarPauta(Pauta persistentObject) {
		persistentObject.setStatus(StatusPauta.CONFIRMADO);
		entityManager.merge(persistentObject);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Pauta> listarPautas() {
		Query consulta = entityManager.createQuery("from Pauta where deletado = false");
		return consulta.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Pauta> listarPautas(Funcionario f) {
		Query consulta = entityManager.createQuery("from Pauta where deletado = false and funcionario = "+f.getId());
		return consulta.getResultList();
	}

}