package br.com.sne.sistema.dao.interfaces;

import java.util.List;

import br.com.sne.sistema.bean.Funcionario;
import br.com.sne.sistema.bean.Pauta;

public interface PautaDaoInterface {

	public void atualizarPauta(Pauta transientObject);
	
	public Pauta carregarPauta(long id);

	public List<Pauta> listarPautas(int campo, String texto);
	
	public List<Pauta> listarPautas(int campo, String texto,Funcionario f);

	public List<Pauta> listarPautas();
	
	public List<Pauta> listarPautas(Funcionario f);
	
	public void salvarPauta(Pauta newInstance) ;	
	
	public void removerPauta(Pauta persistentObject) ;

	public abstract void restaurarPauta(Pauta persistentObject);

	public abstract List<Pauta> listarPautasCanceladas();
}