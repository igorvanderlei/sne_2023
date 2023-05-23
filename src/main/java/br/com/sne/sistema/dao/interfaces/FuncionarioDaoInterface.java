package br.com.sne.sistema.dao.interfaces;

import java.util.List;
import br.com.sne.sistema.bean.Funcionario;

public interface FuncionarioDaoInterface {

	public void atualizarFuncionario(Funcionario transientObject) ;
	
	public Funcionario carregarFuncionario(long id) ;

	public List<Funcionario> listarFuncionarios()  ;

	public void removerFuncionario(Funcionario persistentObject)  ;

	public void salvarFuncionario(Funcionario newInstance)  ;
	
	public List<Funcionario> procurarFuncionarioPorNome (String arg0) ;

	public abstract void restaurarFuncionario(Funcionario persistentObject)
			;

	public abstract List<Funcionario> listarFuncionariosExcluidos() ;

}