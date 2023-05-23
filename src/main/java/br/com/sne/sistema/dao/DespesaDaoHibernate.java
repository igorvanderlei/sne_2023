package br.com.sne.sistema.dao;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.com.sne.sistema.bean.CentroCusto;
import br.com.sne.sistema.bean.Despesa;
import br.com.sne.sistema.bean.Fornecedor;
import br.com.sne.sistema.bean.Freelancer;
import br.com.sne.sistema.bean.OrdemServico;
import br.com.sne.sistema.dao.interfaces.DespesaDaoInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class DespesaDaoHibernate implements DespesaDaoInterface {
	@Autowired
	private EntityManager entityManager;

	public void atualizarDespesa(Despesa transientObject)  {
		entityManager.merge(transientObject);	
	}

	public Despesa carregarDespesa(long id)  {
		return entityManager.find(Despesa.class, id);
	}

	private CriteriaQuery<Despesa> montarCriteriaLista (int campo, String texto) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Despesa> criteria = builder.createQuery(Despesa.class);
		Root<Despesa> root = criteria.from(Despesa.class);
		Join<Despesa, CentroCusto> centroCustoJoin = root.join( "centroCusto" );
		Join<Despesa, Fornecedor> fornecedorJoin = root.join( "fornecedor", JoinType.LEFT);
		Join<Despesa, Freelancer> freelancerJoin = root.join("freelancer", JoinType.LEFT);
		criteria.select(root);
		Predicate filtro = builder.equal(root.get("deletado"), false);
		
		texto = texto.toUpperCase();

		//{"Id", "Descrição", "Centro de Custo", "Fornecedor", "X", "Valor", "X", "Situação", "X"};
		switch(campo) {
		case 1:
			criteria.where(builder.and( builder.like(root.get("id").as(String.class),  "%" + texto + "%"),
					filtro));			
			break;
		case 2:
			criteria.where(builder.and( builder.like(builder.upper(root.get("descricao")),  "%" + texto + "%" ),
					filtro));
			break;				
		case 3:
			criteria.where(builder.and( builder.like(builder.upper(centroCustoJoin.get("nome")),  "%" + texto + "%" ),
					filtro));
			break;
		case 4:
			criteria.where(
					builder.and( 
							builder.or( builder.like(builder.upper(fornecedorJoin.get("nome")),  "%" + texto + "%" ),
									    builder.like(builder.upper(freelancerJoin.get("nome")),  "%" + texto + "%" )
									  ),
							filtro)
					);
			break;
		case 5:
			criteria.where(builder.and( builder.like(root.get("valor").as(String.class),  "%" + texto + "%"),
					filtro));	
			break;
		case 6:
			if("pago".indexOf(texto.toLowerCase())!= -1)
				criteria.where(builder.and( builder.equal(root.get("situacao"),  true),
						filtro));
			else 
				if("aberto".indexOf(texto.toLowerCase())!= -1)
					criteria.where(builder.and( builder.equal(root.get("situacao"),  false),
							filtro));
				else
					criteria.where(builder.and( builder.lt(root.get("id"),  0l),
							filtro));
			break;

		}
		return criteria;
	}

	public List<Despesa> listarDespesas(int campo, String texto)  {
		CriteriaQuery<Despesa> crit = montarCriteriaLista(campo, texto);
		return entityManager.createQuery(crit).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Despesa> listarDespesas(OrdemServico os)  {
		Query consulta = entityManager.createQuery("from Despesa where deletado = false and ordemServico = ?1");
		consulta.setParameter(1, os.getId());
		return consulta.getResultList();
	}

	public Despesa buscarDespesaComissao(OrdemServico os)  {
		Query consulta = entityManager.createQuery("from Despesa d inner join fetch d.ordemServico where ordemServico.id = ?1 and d.deletado = false and comissao = true");
//		Query consulta = entityManager.createQuery("from Despesa where deletado = false and comissao = true and ordemervico = ?1");
		consulta.setParameter(1, os.getId());
		try {
			return (Despesa)consulta.getSingleResult();
		} catch(NoResultException ex) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	private List<Despesa> listarDespesasAbertas()  {
		Query consulta = entityManager.createQuery("from Despesa where deletado = false and situacao=false");
		return consulta.getResultList();
	}

	@SuppressWarnings("unchecked")
	private List<Despesa> listarDespesasAbertasAPartirDe(Date inicio)  {
		Query consulta = entityManager.createQuery("from Despesa where deletado = false and situacao=false and cast(dataVencimento as date) >= ?1");
		consulta.setParameter(1, inicio);
		return consulta.getResultList();
	}

	@SuppressWarnings("unchecked")
	private List<Despesa> listarDespesasAbertasAte(Date fim)  {
		Query consulta = entityManager.createQuery("from Despesa where deletado = false and situacao=false and cast(dataVencimento as date) <= ?1");
		consulta.setParameter(1, fim);
		return consulta.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Despesa> listarDespesasAbertas(Date inicio, Date fim)  {
		if(inicio == null) {
			if(fim == null)
				return listarDespesasAbertas();
			else
				return listarDespesasAbertasAte(fim);
		} else {
			if(fim == null) {
				return listarDespesasAbertasAPartirDe(inicio);
			} else {
				Query consulta = entityManager.createQuery("from Despesa where deletado = false and situacao=false and cast(dataVencimento as date) between ?1 and ?2");
				consulta.setParameter(1, inicio);
				consulta.setParameter(2, fim);
				return consulta.getResultList();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public List<Despesa> listarDespesasExcluidas()  {
		Query consulta = entityManager.createQuery("from Despesa where deletado = true");
		return consulta.getResultList();
	}

	public void removerDespesa(Despesa persistentObject)  {
		persistentObject.setDeletado(true);
		entityManager.merge(persistentObject);	
	}

	public void restaurarDespesa(Despesa persistentObject)  {
		persistentObject.setDeletado(false);
		entityManager.merge(persistentObject);	
	}

	public void salvarDespesa(Despesa newInstance)  {
		entityManager.persist(newInstance);
	}
}