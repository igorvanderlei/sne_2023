package br.com.sne.sistema.dao.interfaces;

import java.util.List;

import br.com.sne.sistema.bean.Despesa;
import br.com.sne.sistema.bean.Receita;
import br.com.sne.sistema.bean.Recibo;

public interface ReciboDaoInterface {

	public void atualizarRecibo(Recibo transientObject);
	
	public Recibo carregarRecibo(long id);

	public List<Recibo> listarRecibo() ;

	public void cancelarRecibo(Recibo persistentObject) ;

	public void salvarRecibo(Recibo newInstance) ;

	public abstract Recibo localizarReciboPorDespesa(Despesa despesa)
			;
	
	public abstract Recibo localizarReciboPorReceita(Receita receita)
			;

	public abstract void restaurarRecibo(Recibo persistentObject);

	public abstract List<Recibo> listarRecibosCancelados();
	

}