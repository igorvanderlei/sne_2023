package br.com.sne.sistema.dao.interfaces;

import java.util.List;
import br.com.sne.sistema.bean.Freelancer;

public interface FreelancerDaoInterface {

	public void atualizarFreelancer(Freelancer transientObject);
	
	public Freelancer carregarFreelancer(long id);

	public List<Freelancer> listarFreelancers() ;

	public void removerFreelancer(Freelancer persistentObject) ;

	public void salvarFreelancer(Freelancer newInstance) ;

	public abstract List<Freelancer> listarFreelancersExcluidos();
	

}