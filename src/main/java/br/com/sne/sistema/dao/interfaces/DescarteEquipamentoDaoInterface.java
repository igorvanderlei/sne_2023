package br.com.sne.sistema.dao.interfaces;

import java.util.List;

import br.com.sne.sistema.bean.DescarteEquipamento;

public interface DescarteEquipamentoDaoInterface {

	public void atualizarDescarteEquipamento(DescarteEquipamento transientObject) ;
	
	public DescarteEquipamento carregarDescarteEquipamento(long id) ;

	public List<DescarteEquipamento> listarDescarteEquipamentos()  ;

	public void removerDescarteEquipamento(DescarteEquipamento persistentObject)  ;

	public void salvarDescarteEquipamento(DescarteEquipamento newInstance)  ;

	public abstract void restaurarDescarteEquipamento(DescarteEquipamento persistentObject)
			;

}