package br.com.sne.sistema.dao.interfaces;

import java.util.List;
import br.com.sne.sistema.bean.DescricaoEquipamento;
import br.com.sne.sistema.bean.Funcao;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.StatusEquipamento;

public interface DescricaoEquipamentoDaoInterface {

	public void atualizarDescricaoEquipamento(DescricaoEquipamento transientObject) ;
	
	public DescricaoEquipamento carregarDescricaoEquipamento(long id) ;

	public List<DescricaoEquipamento> listarDescricoesEquipamentos()  ;

	public void removerDescricaoEquipamento(DescricaoEquipamento persistentObject)  ;

	public void salvarDescricaoEquipamento(DescricaoEquipamento newInstance)  ;

	public int contarEquipamentos(DescricaoEquipamento desc, StatusEquipamento status);

	public int contarTodosEquipamentos(DescricaoEquipamento desc);

	public int contarTodosFuncionario(Funcao func);

	public abstract void restaurarDescricaoEquipamento(DescricaoEquipamento persistentObject)
			;
	

}