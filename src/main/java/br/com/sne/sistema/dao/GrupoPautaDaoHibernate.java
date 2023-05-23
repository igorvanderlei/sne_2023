package br.com.sne.sistema.dao;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import br.com.sne.sistema.bean.Funcionario;
import br.com.sne.sistema.bean.GrupoPauta;
import br.com.sne.sistema.dao.interfaces.GrupoPautaDaoInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class GrupoPautaDaoHibernate implements GrupoPautaDaoInterface {
	@Autowired
	private EntityManager entityManager;

	public void atualizarGrupoPauta(GrupoPauta transientObject)  {
			entityManager.merge(transientObject);
	}

	public GrupoPauta carregarGrupoPauta(long id)  {
			return (GrupoPauta) entityManager.find(GrupoPauta.class, id);
	}
	
	public void salvarGrupoPauta(GrupoPauta newInstance)  {
			entityManager.persist(newInstance);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<GrupoPauta> listarGrupoPautas()  {
			Query consulta = entityManager.createQuery("from GrupoPauta where deletado = false");
			return consulta.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GrupoPauta> listarGrupoPautas(Funcionario f)  {
			Query consulta = entityManager.createQuery("from GrupoPauta where deletado = false and funcionario = "+f.getId());
			return consulta.getResultList();
	}

}