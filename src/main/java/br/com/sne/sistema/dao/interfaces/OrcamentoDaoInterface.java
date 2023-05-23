package br.com.sne.sistema.dao.interfaces;

import java.util.Date;
import java.util.List;

import br.com.sne.sistema.bean.Funcionario;
import br.com.sne.sistema.bean.Orcamento;

public interface OrcamentoDaoInterface {

	public void atualizarOrcamento(Orcamento transientObject);
	
	public Orcamento carregarOrcamento(long id);

	public List<Orcamento> listarOrcamentos(int campo, String texto) ;
	
	public List<Orcamento> listarOrcamentos() ;
	
	public List<Orcamento> listarOrcamentos(Date inicio, Date fim) ;

	public List<Orcamento> listarOrcamentos(Date inicio, Date fim,Funcionario f) ;

	public void removerOrcamento(Orcamento persistentObject) ;

	public void salvarOrcamento(Orcamento newInstance) ;

	public abstract List<Orcamento> listarOrcamentos(int campo, String texto,Funcionario f);

	public abstract List<Orcamento> listarOrcamentos(Funcionario f);
	
	public abstract List<Orcamento> listarOrcamentos(long idPai);

	public abstract void restaurarOrcamento(Orcamento persistentObject);

	public abstract List<Orcamento> listarOrcamentosExcluidos();

}