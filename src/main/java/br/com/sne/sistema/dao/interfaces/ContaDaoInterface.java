package br.com.sne.sistema.dao.interfaces;

import java.util.List;

import br.com.sne.sistema.bean.Conta;

public interface ContaDaoInterface {

	public void atualizarConta(Conta transientObject) ;
	
	public Conta carregarConta(long id) ;

	public List<Conta> listarContas()  ;

	public void removerConta(Conta persistentObject)  ;

	public void salvarConta(Conta newInstance)  ;

	public abstract void restaurarConta(Conta persistentObject) ;
	

}