package br.com.sne.sistema.dao.interfaces;

import java.util.List;

import br.com.sne.sistema.bean.Funcionario;
import br.com.sne.sistema.bean.GrupoPauta;


public interface GrupoPautaDaoInterface {

	public void atualizarGrupoPauta(GrupoPauta transientObject) ;
	
	public GrupoPauta carregarGrupoPauta(long id) ;

	public List<GrupoPauta> listarGrupoPautas() ;
	
	public List<GrupoPauta> listarGrupoPautas(Funcionario f) ;
	
	public void salvarGrupoPauta(GrupoPauta newInstance)  ;	
	
}