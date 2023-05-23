package br.com.sne.sistema.dao;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.com.sne.sistema.bean.Cliente;
import br.com.sne.sistema.bean.Funcionario;
import br.com.sne.sistema.bean.Orcamento;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.SituacaoOrcamento;
import br.com.sne.sistema.dao.interfaces.OrcamentoDaoInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class OrcamentoDaoHibernate implements OrcamentoDaoInterface {
	@Autowired
	private EntityManager entityManager;
	
	public void atualizarOrcamento(Orcamento transientObject) {
		entityManager.merge(transientObject);
	}

	public Orcamento carregarOrcamento(long id) {
		return entityManager.find(Orcamento.class, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Orcamento> listarOrcamentos(Date inicio, Date fim) {
			Query consulta = null;
			consulta = entityManager.createQuery("from Orcamento o where deletado = false and maisatual= true and cast(dataInicio as date) between ?1 and ?2");
			
			consulta.setParameter(1, inicio);
			consulta.setParameter(2, fim);
			return consulta.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Orcamento> listarOrcamentos(Date inicio, Date fim,Funcionario f) {
			Query consulta = null;
			consulta = entityManager.createQuery("from Orcamento o where  deletado = false and cast(dataInicio as date) between ?1 and ?2  and maisatual= true and funcionario = ?3");
			
			consulta.setParameter(1, inicio);
			consulta.setParameter(2, fim);
			consulta.setParameter(3, f.getId());
			return consulta.getResultList();
	}
	
	public List<Orcamento> listarOrcamentos(int campo, String texto) {
		try {
			CriteriaQuery<Orcamento> crit = montarCriteriaLista(campo, texto);
			return entityManager.createQuery(crit).setMaxResults(100).getResultList();
		} catch(NoResultException ex) {
			return null;
		}	
	}
	
	private CriteriaQuery<Orcamento> montarCriteriaLista (int campo, String texto) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Orcamento> criteria = builder.createQuery(Orcamento.class);
		Root<Orcamento> root = criteria.from(Orcamento.class);
		Join<Orcamento, Cliente> clienteJoin = root.join( "cliente" );
		Join<Orcamento, Funcionario> funcionarioJoin = root.join( "funcionario" );
		criteria.select(root);
		Predicate filtro = builder.equal(root.get("deletado"), false);
		
		//{"Id", "Cliente", "Contato", "Evento", "X", "X", "Funcionário"};
		switch(campo) {
		case 1:
			criteria.where(builder.and( 
							builder.or(builder.like(root.get("id").as(String.class),  "%" + texto + "%"),
									   builder.like(root.get("idPai").as(String.class),  "%" + texto + "%")),
							filtro));
			break;
		case 2:
			criteria.where(builder.and( builder.like(builder.upper(clienteJoin.get("nome")),  "%" + texto.toUpperCase() + "%"),
					filtro));
			break;			
		case 3:
			criteria.where(builder.and( builder.like(builder.upper(clienteJoin.get("contato")),  "%" + texto.toUpperCase() + "%"),
					filtro));
			break;
		case 4:
			criteria.where(builder.and( builder.like(builder.upper(root.get("nomeEvento")),  "%" + texto.toUpperCase() + "%"),
					filtro));
			break;
		case 5:
			criteria.where(builder.and( builder.like(builder.upper(funcionarioJoin.get("nome")),  "%" + texto.toUpperCase() + "%"),
					filtro));
			break;
		case 6:
			if("fechado".indexOf(texto.toLowerCase())!= -1)
				criteria.where(builder.and( builder.equal(root.get("situacao"),  SituacaoOrcamento.FECHADO),
						filtro));
			else if("aberto".indexOf(texto.toLowerCase())!= -1)
				criteria.where(builder.and( builder.equal(root.get("situacao"),  SituacaoOrcamento.ABERTO),
						filtro));			
			else if("cancelado".indexOf(texto.toLowerCase())!= -1)
				criteria.where(builder.and( builder.equal(root.get("situacao"),  SituacaoOrcamento.CANCELADO),
						filtro));					
			break;

		}
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	public List<Orcamento> listarOrcamentosExcluidos() {
			Query consulta = entityManager.createQuery("from Orcamento where deletado = true");
			return consulta.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Orcamento> listarOrcamentos(long idPai) {
			Query consulta = entityManager.createQuery("from Orcamento where (id = ?1 or idPai = ?2) and deletado = false");
			consulta.setParameter(1, idPai);
			consulta.setParameter(2, idPai);
			return consulta.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Orcamento> listarOrcamentos(int campo, String texto,Funcionario f ) {
		Query consulta = entityManager.createQuery("from Orcamento where (funcionario = ?1 or vendedorConjunto = ?2) and deletado = false");
		consulta.setParameter(1, f.getId());
		consulta.setParameter(2, f.getId());
		return consulta.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Orcamento> listarOrcamentos() {
		Query consulta = entityManager.createQuery("from Orcamento where deletado = false");
		return consulta.getResultList();	
	}
	@SuppressWarnings("unchecked")
	public List<Orcamento> listarOrcamentos(Funcionario f ) {
			Query consulta = entityManager.createQuery("from Orcamento o where (o.funcionario.id = ?1 or o.vendedorConjunto.id = ?2) and deletado = false");
			consulta.setParameter(1, f.getId());
			consulta.setParameter(2, f.getId());
			return consulta.getResultList();
	}
	
	public void removerOrcamento(Orcamento persistentObject) {
		persistentObject.setDeletado(true);
		entityManager.merge(persistentObject);	
	}
	
	public void restaurarOrcamento(Orcamento persistentObject) {
		persistentObject.setDeletado(false);
		entityManager.merge(persistentObject);	
	}

	public void salvarOrcamento(Orcamento newInstance) {
		entityManager.persist(newInstance);
	}
}