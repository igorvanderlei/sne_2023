package br.com.sne.sistema.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.com.sne.sistema.bean.FontePagadora;
import br.com.sne.sistema.dao.interfaces.FontePagadoraDaoInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class FontePagadoraDaoHibernate implements FontePagadoraDaoInterface {
	@Autowired
	private EntityManager entityManager;

	public void atualizarFontePagadora(FontePagadora transientObject){
		entityManager.merge(transientObject);
	}

	public FontePagadora carregarFontePagadora(long id){
		return entityManager.find(FontePagadora.class, id);	
	}

	private CriteriaQuery<FontePagadora> montarCriteriaLista (int campo, String texto) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<FontePagadora> criteria = builder.createQuery(FontePagadora.class);
		Root<FontePagadora> root = criteria.from(FontePagadora.class);
		criteria.select(root);
		Predicate filtro = builder.equal(root.get("deletado"), false);

		//{"Id", "Nome", "Observações"};
		switch(campo) {
		case 1:
			criteria.where(builder.and( builder.like(root.get("id").as(String.class),  "%" + texto + "%"),
					filtro));
			break;
		case 2:
			criteria.where(builder.and( builder.like(builder.upper(root.get("nome")),  "%" + texto.toUpperCase() + "%"),
					filtro));
			break;				
		case 3:
			criteria.where(builder.and( builder.like(builder.upper(root.get("observacoes")),  "%" + texto.toUpperCase() + "%"),
					filtro));
			break;
		}
		return criteria;
	}

	public List<FontePagadora> listarFontePagadoras(int campo, String texto){
		CriteriaQuery<FontePagadora> crit = montarCriteriaLista(campo, texto);
		return entityManager.createQuery(crit).setMaxResults(100).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<FontePagadora> listarFontePagadorasExcluidas(){
		Query consulta = entityManager.createQuery("from FontePagadora where deletado = true");
		return consulta.getResultList();
	}

	public void removerFontePagadora(FontePagadora persistentObject){
		persistentObject.setDeletado(true);
		entityManager.merge(persistentObject);	
	}

	public void restaurarFontePagadora(FontePagadora persistentObject){
		persistentObject.setDeletado(false);
		entityManager.merge(persistentObject);	
	}

	public void salvarFontePagadora(FontePagadora newInstance){
		entityManager.persist(newInstance);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FontePagadora> listarFontePagadoras(){
		Query consulta = entityManager.createQuery("from FontePagadora where deletado = false");
		return consulta.getResultList();
	}


}