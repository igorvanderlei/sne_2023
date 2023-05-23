package br.com.sne.sistema.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.com.sne.sistema.bean.Cliente;
import br.com.sne.sistema.bean.Funcionario;
import br.com.sne.sistema.bean.OrdemServico;
import br.com.sne.sistema.bean.Receita;
import br.com.sne.sistema.dao.interfaces.ReceitaDaoInterface;
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
public class ReceitaDaoHibernate implements ReceitaDaoInterface {
	@Autowired
	private EntityManager entityManager;

	public void atualizarReceita(Receita transientObject)  {
		entityManager.merge(transientObject);
	}

	public Receita carregarReceita(long id)  {
		return (Receita) entityManager.find(Receita.class, id);
	}

	private CriteriaQuery<Receita> montarCriteriaLista (int campo, String texto) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Receita> criteria = builder.createQuery(Receita.class);
		Root<Receita> root = criteria.from(Receita.class);
		Join<Receita, OrdemServico> osJoin = root.join( "ordemServico" );
		Join<OrdemServico, Cliente> clienteJoin = osJoin.join( "cliente" );
		Join<OrdemServico, Funcionario> funcionarioJoin = osJoin.join( "funcionario" );
		criteria.select(root);
		Predicate filtro = builder.equal(root.get("deletado"), false);


		//{"Id", "Cliente", "Vendedor", "Evento", "Descrição", "Faturado",  "Vencimento", "X", "X", "Situação"};
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
			criteria.where(builder.and( builder.like(builder.upper(funcionarioJoin.get("nome")),  "%" + texto.toUpperCase() + "%"),
					filtro));
			break;
		case 4:
			criteria.where(builder.and( builder.like(builder.upper(osJoin.get("nomeEvento")),  "%" + texto.toUpperCase() + "%"),
					filtro));
			break;
		case 5:
			criteria.where(builder.and( builder.like(builder.upper(root.get("descricao")),  "%" + texto.toUpperCase() + "%"),
					filtro));
			break;
		case 6:
			if("sim".indexOf(texto.toLowerCase())!= -1)
				criteria.where(builder.and( builder.equal(root.get("faturado"),  true), filtro));
			else 
				if("não".indexOf(texto.toLowerCase())!= -1)
					criteria.where(builder.and( builder.equal(root.get("faturado"),  false), filtro));					
				else
					criteria.where(builder.and( builder.lt(root.get("id"),  0l), filtro));
			break;
		case 7:
			if("pago".indexOf(texto.toLowerCase())!= -1)
				criteria.where(builder.and( builder.equal(root.get("situacao"),  true), filtro));
			else 
				if("aberto".indexOf(texto.toLowerCase())!= -1)
					criteria.where(builder.and( builder.equal(root.get("situacao"),  false), filtro));
				else
					criteria.where(builder.and( builder.lt(root.get("id"),  0l), filtro));
			break;
		}

		return criteria;
	}

	public List<Receita> listarReceitas(int campo,String texto)  {
		CriteriaQuery<Receita> crit = montarCriteriaLista(campo, texto);
		return entityManager.createQuery(crit).setMaxResults(100).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Receita> listarReceitasExcluidas()  {
		Query consulta = entityManager.createQuery("from Receita where deletado = true");
		return consulta.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Receita> listarReceitasPorOS(OrdemServico os) {
		Query consulta = entityManager.createQuery("from Receita where ordemServico.id = ?1 and deletado = false");
		consulta.setParameter(1, os.getId());
		return consulta.getResultList();
	}

	public void removerReceita(Receita persistentObject)  {
		persistentObject.setDeletado(true);
		entityManager.merge(persistentObject);
	}

	public void restaurarReceita(Receita persistentObject)  {
		persistentObject.setDeletado(false);
		entityManager.merge(persistentObject);
	}

	public void salvarReceita(Receita newInstance)  {
		entityManager.persist(newInstance);
	}

}