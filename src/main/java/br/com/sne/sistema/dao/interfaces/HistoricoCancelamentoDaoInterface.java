package br.com.sne.sistema.dao.interfaces;

import java.util.List;

import br.com.sne.sistema.bean.HistoricoCancelamento;

public interface HistoricoCancelamentoDaoInterface {

	public HistoricoCancelamento carregarHistoricoCancelamento(long id)
			;

	public List<HistoricoCancelamento> listarHistoricoCancelamentos()
			;

	public void salvarHistoricoCancelamento(HistoricoCancelamento newInstance)
			;

}