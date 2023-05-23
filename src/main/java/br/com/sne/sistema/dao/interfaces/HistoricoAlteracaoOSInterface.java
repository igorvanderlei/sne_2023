package br.com.sne.sistema.dao.interfaces;

import java.util.List;

import br.com.sne.sistema.bean.HistoricoAlteracaoOS;

public interface HistoricoAlteracaoOSInterface {

	public HistoricoAlteracaoOS carregarHistoricoAlteracaoOS(long id)
			;

	public List<HistoricoAlteracaoOS> listarHistoricoAlteracaoOSs()
			;

	public void salvarHistoricoAlteracaoOS(HistoricoAlteracaoOS newInstance)
			;

}