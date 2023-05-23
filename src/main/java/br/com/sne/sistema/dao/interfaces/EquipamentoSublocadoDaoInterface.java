package br.com.sne.sistema.dao.interfaces;

import java.util.List;
import br.com.sne.sistema.bean.EquipamentoSublocado;

public interface EquipamentoSublocadoDaoInterface {

	public void atualizarEquipamentoSublocado(EquipamentoSublocado transientObject) ;
	
	public EquipamentoSublocado carregarEquipamentoSublocado(long id) ;

	public List<EquipamentoSublocado> listarEquipamentosSublocados()  ;

	public void removerEquipamentoSublocado(EquipamentoSublocado persistentObject)  ;

	public void salvarEquipamentoSublocado(EquipamentoSublocado newInstance)  ;

	public abstract void restaurarEquipamentoSublocado(EquipamentoSublocado persistentObject)
			;
	

}