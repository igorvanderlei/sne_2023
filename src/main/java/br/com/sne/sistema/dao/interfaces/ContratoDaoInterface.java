package br.com.sne.sistema.dao.interfaces;

import java.util.List;

import br.com.sne.sistema.bean.Contrato;
import br.com.sne.sistema.bean.Funcionario;

public interface ContratoDaoInterface {

	public void atualizarContrato(Contrato transientObject);
	
	public void removerContrato(Contrato transientObject);

	public Contrato carregarContrato(long id);

	public List<Contrato> listarContrato(int campo, String texto) ;
	
	public List<Contrato> listarContrato(int campo, String texto,Funcionario f) ;

	public void salvarContrato(Contrato newInstance) ;	

}