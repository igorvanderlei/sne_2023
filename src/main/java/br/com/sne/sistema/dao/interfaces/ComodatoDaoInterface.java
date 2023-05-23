package br.com.sne.sistema.dao.interfaces;

import java.util.List;
import br.com.sne.sistema.bean.Comodato;

public interface ComodatoDaoInterface {

	public void atualizarComodato(Comodato transientObject) ;
	
	public Comodato carregarComodato(long id) ;

	public List<Comodato> listarComodatos()  ;

	public void removerComodato(Comodato persistentObject)  ;

	public void salvarComodato(Comodato newInstance)  ;

	public List<Comodato> listarComodatosPorEquipamento(long equipamento ) 
			;
	
	public Comodato localizaComodatosPorEquipamento(long equipamento) 
			;

	public abstract void restaurarComodato(Comodato persistentObject)
			;

	public abstract List<Comodato> listarComodatosExcluidas() ;

	public abstract List<Comodato> listarComodatosEnviarEquipamento() ;

}