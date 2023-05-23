package br.com.sne.sistema.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.com.sne.sistema.bean.DadosBancarios;
import br.com.sne.sistema.bean.Grupo;
import br.com.sne.sistema.dao.interfaces.DadosBancariosDaoInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class DadosBancariosDaoHibernate implements DadosBancariosDaoInterface {
	@Autowired
	private EntityManager entityManager;

	public void atualizarDadosBancarios(DadosBancarios transientObject)  {
		entityManager.merge(transientObject);
	}

	public DadosBancarios carregarDadosBancarios(long id)  {
		return entityManager.find(DadosBancarios.class, id);
	}
	
	private CriteriaQuery<DadosBancarios> montarCriteriaLista (int campo, String texto) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<DadosBancarios> criteria = builder.createQuery(DadosBancarios.class);
		Root<DadosBancarios> root = criteria.from(DadosBancarios.class);
		criteria.select(root);
		Predicate filtro = builder.equal(root.get("deletado"), false);
		
		//{"Id", "Tipo", "Razão Social/Nome", "CNPJ/Cpf", "X", "Contato", "X", "Funcionário", "X"};
		switch(campo) {
		case 1:
			criteria.where(builder.and( builder.like(root.get("id").as(String.class),  "%" + texto + "%"),
					filtro));
			break;
		case 2:
			criteria.where(builder.and( builder.like(builder.upper(root.get("banco")),  "%"+texto.toUpperCase()+"%"),
					filtro));			
			break;				
		case 3:
			criteria.where(builder.and( builder.like(builder.upper(root.get("agencia")),  "%"+texto.toUpperCase()+"%"),
					filtro));	
			break;
		case 4:
			criteria.where(builder.and( builder.like(builder.upper(root.get("conta")),  "%"+texto.toUpperCase()+"%"),
					filtro));	
			break;
		}
		return criteria;
	}

	public List<DadosBancarios> listarDadosBancarios(int campo, String texto)  {
			CriteriaQuery<DadosBancarios> crit = montarCriteriaLista(campo, texto);
			return entityManager.createQuery(crit).getResultList();
	}
		
	@SuppressWarnings("unchecked")
	public List<DadosBancarios> listarDadosBancariossExcluidos()  {
		Query consulta = entityManager.createQuery("from DadosBancarios where deletado = true", Grupo.class);
		return consulta.getResultList();
	}
	
	public void removerDadosBancarios(DadosBancarios persistentObject)  {
		persistentObject.setDeletado(true);
		entityManager.merge(persistentObject);		
	}
	
	public void restaurarDadosBancarios(DadosBancarios persistentObject)  {
		persistentObject.setDeletado(false);
		entityManager.merge(persistentObject);	
	}

	public void salvarDadosBancarios(DadosBancarios newInstance)  {
		entityManager.persist(newInstance);
	}
	

}