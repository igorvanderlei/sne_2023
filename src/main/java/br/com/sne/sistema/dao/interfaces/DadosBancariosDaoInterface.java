package br.com.sne.sistema.dao.interfaces;

import java.util.List;

import br.com.sne.sistema.bean.DadosBancarios;

public interface DadosBancariosDaoInterface {

	public void atualizarDadosBancarios(DadosBancarios transientObject) ;
	
	public DadosBancarios carregarDadosBancarios(long id) ;

	public List<DadosBancarios> listarDadosBancarios(int campo, String texto) ;

	public void removerDadosBancarios(DadosBancarios persistentObject)  ;

	public void salvarDadosBancarios(DadosBancarios newInstance)  ;
	
	public abstract void restaurarDadosBancarios(DadosBancarios persistentObject) ;

	public abstract List<DadosBancarios> listarDadosBancariossExcluidos() ;
	

}