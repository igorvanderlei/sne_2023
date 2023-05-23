package br.com.sne.sistema.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.com.sne.sistema.bean.Cliente;
import br.com.sne.sistema.bean.Contrato;
import br.com.sne.sistema.bean.Funcionario;
import br.com.sne.sistema.bean.OrdemServico;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.StatusOS;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.TipoContrato;
import br.com.sne.sistema.dao.interfaces.ContratoDaoInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class ContratoDaoHibernate implements ContratoDaoInterface {
	@Autowired
	private EntityManager entityManager;

	public void atualizarContrato(Contrato transientObject) {
		entityManager.merge(transientObject);
	}

	public Contrato carregarContrato(long id) {
		return entityManager.find(Contrato.class, id);	
	}

	public List<Contrato> listarContrato(int campo, String texto) {
		CriteriaQuery<Contrato> crit = montarCriteriaLista(campo, texto);
		return entityManager.createQuery(crit).getResultList();
	}

	public List<Contrato> listarContrato(int campo, String texto,Funcionario f) {
		CriteriaQuery<Contrato> crit = montarCriteriaLista(campo, texto);
		List<Contrato> lista = new ArrayList<Contrato>();
		for(Contrato c: (List<Contrato>) entityManager.createQuery(crit).getResultList()) {
			if(c.getOrdemServico().getFuncionario().getId() == f.getId() || (c.getOrdemServico().getVendedorConjunto() != null && c.getOrdemServico().getVendedorConjunto().getId() == f.getId())) {
				lista.add(c);
			}
		}
		return lista;
	}

	private CriteriaQuery<Contrato> montarCriteriaLista (int campo, String texto) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Contrato> criteria = builder.createQuery(Contrato.class);
		Root<Contrato> root = criteria.from(Contrato.class);
		Join<Contrato, OrdemServico> ordemServicoJoin = root.join( "ordemServico" );
		Join<OrdemServico, Cliente> osClienteJoin = ordemServicoJoin.join("cliente");
		Join<OrdemServico, Funcionario> osFuncionarioJoin = ordemServicoJoin.join("funcionario");
		criteria.select(root);	

		Predicate filtro = builder.equal(root.get("deletado"), false);

		//{"Id","Evento","Cliente",,Funcionário "Tipo"};
		switch(campo) {
		case 1:
			/* Comparação exata do ID */
			/*Long id = 0l;
			try {
				id = Long.parseLong(texto);
			} catch (Exception e) {}

			criteria.where(builder.and( builder.equal(root.get("id"),  id),
					filtro));*/
			criteria.where(builder.and( builder.like(root.get("id").as(String.class),  "%" + texto + "%"),
					filtro));
			break;
		case 2:
			criteria.where(builder.and( builder.like(builder.upper(ordemServicoJoin.get("nomeEvento")), "%"+ texto.toUpperCase() + "%" ),
					filtro));
			break;		
		case 3:
			criteria.where(builder.and( builder.like(builder.upper(osClienteJoin.get("nome")), "%"+ texto.toUpperCase() + "%" ),
					filtro));
			break;
		case 4:
			criteria.where(builder.and( builder.like(builder.upper(osFuncionarioJoin.get("nome")), "%"+ texto.toUpperCase() + "%" ),
					filtro));
			break;
		case 5:
			if("contrato".indexOf(texto.toLowerCase())!= -1)
				criteria.where(builder.and( builder.equal(root.get("tipo"),  TipoContrato.CONTRATO),
						filtro));
			else 
				if("termo aditivo".indexOf(texto.toLowerCase())!= -1)
					criteria.where(builder.and( builder.equal(root.get("tipo"),  TipoContrato.TERMO_ADITIVO),
							filtro));
				else
					criteria.where(builder.and( builder.lt(root.get("id"),  0l),
							filtro));

		}

		return criteria;
	}

	public void salvarContrato(Contrato newInstance) {
		entityManager.persist(newInstance);
		OrdemServico ordemServico = newInstance.getOrdemServico();
		ordemServico.setGerouContrato(true);
		ordemServico.setStatus(StatusOS.APROVADA);
		entityManager.merge(ordemServico);
	}

	@Override
	public void removerContrato(Contrato transientObject) {
		transientObject.setDeletado(true);
		entityManager.merge(transientObject);

		OrdemServico ordem = transientObject.getOrdemServico();
		ordem.setGerouContrato(false);
		entityManager.merge(ordem);
	}
}