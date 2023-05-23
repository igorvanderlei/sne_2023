package br.com.sne.sistema.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import br.com.sne.sistema.bean.EquipamentoEnviado;
import br.com.sne.sistema.bean.Comodato;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.StatusComodato;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.StatusEquipamento;
import br.com.sne.sistema.dao.interfaces.ComodatoDaoInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

@Repository
@Transactional
public class ComodatoDaoHibernate implements ComodatoDaoInterface {
	@Autowired
	private EntityManager entityManager;

	@Override
	public void atualizarComodato(final Comodato o)  {
		if(o.getEquipamentoEnviado()!=null&&o.getEquipamentoEnviado().size() >=0 ) {
			for(EquipamentoEnviado e: o.getEquipamentoEnviado()) {
				if(e.isStatus() == false) {
					e.getEquipamento().setStatus(StatusEquipamento.COMODATO);
					entityManager.merge(e.getEquipamento());
				}
			}
		}
		entityManager.merge(o);
	}

	@Override
	public Comodato carregarComodato(long id)  {
		
		try {
			Query consulta = 
					entityManager.createQuery("from Comodato where id = ?1 and deletado = false", Comodato.class);
			consulta.setParameter(1 , id);
			return (Comodato) consulta.getSingleResult();
		} catch(NoResultException ex) {
			return null;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Comodato> listarComodatos()  {
		Query consulta = 
				entityManager.createQuery("from Comodato where deletado = false");
		return consulta.getResultList();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Comodato> listarComodatosEnviarEquipamento()  {
		try {
			String conjuntoStatus = "(" + StatusComodato.INICIADO.ordinal() + ", " + StatusComodato.PENDENTE.ordinal() +")";
			Query consulta = 
					entityManager.createQuery("from Comodato where deletado = false and status in " + conjuntoStatus );
			return consulta.getResultList();
		} catch(NoResultException ex) {
			return null;
		}	
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Comodato> listarComodatosExcluidas()  {
		Query consulta = 
				entityManager.createQuery("from Comodato where deletado = true");
		return consulta.getResultList();
	}
	

	@Override
	@SuppressWarnings("unchecked")
	public List<Comodato> listarComodatosPorEquipamento(long equipamento )  {
			Query consulta = entityManager.createQuery("select os from Comodato as os " +
					"									inner join os.equipamentoEnviado as eq " +
														"Where eq.equipamento = ?1 and os.deletado = false " +
														"order by os.dataInicio desc");
			consulta.setParameter(1, equipamento);
			return consulta.getResultList();
	}
	
	
	@Override
	public Comodato localizaComodatosPorEquipamento(long equipamento)  {
		try {
			Query consulta = entityManager.createQuery("select os from Comodato as os " +
					"									inner join os.equipamentoEnviado as eq " +
														"Where eq.id = ?1 and os.deletado = false");
			consulta.setParameter(1, equipamento);
			return (Comodato)consulta.getSingleResult();
		} catch(NoResultException ex) {
			return null;
		}	
	}
	
	@Override
	public void removerComodato(Comodato persistentObject)  {
		persistentObject.setDeletado(true);
		entityManager.merge(persistentObject);	
	}
	
	@Override
	public void restaurarComodato(Comodato persistentObject)  {
		persistentObject.setDeletado(false);
		entityManager.merge(persistentObject);	
	}

	@Override
	public void salvarComodato(Comodato newInstance)  {
		entityManager.persist(newInstance);
	}
	

}