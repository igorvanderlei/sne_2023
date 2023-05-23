package br.com.sne.sistema.dao.interfaces;

import java.util.List;

import br.com.sne.sistema.bean.Cliente;
import br.com.sne.sistema.bean.Funcionario;

public interface ClienteDaoInterface {

	public void atualizarCliente(Cliente transientObject) ;
	
	public Cliente carregarCliente(long id) ;

	public List<Cliente> listarClientes(int campo, String texto) ;

	public void removerCliente(Cliente persistentObject)  ;

	public void salvarCliente(Cliente newInstance)  ;

	public List<Cliente> listarClientes(int campo, String texto, Funcionario f) ; 

	public abstract List<Cliente> listarClientes(String cnpj, String razaoSocial, String contato);

	public abstract void restaurarCliente(Cliente persistentObject) ;

	public abstract List<Cliente> listarClientesExcluidos() ;
	

}