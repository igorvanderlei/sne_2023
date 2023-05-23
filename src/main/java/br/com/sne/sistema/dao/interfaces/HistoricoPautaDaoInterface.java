package br.com.sne.sistema.dao.interfaces;

import java.util.List;

import br.com.sne.sistema.bean.HistoricoPauta;
import br.com.sne.sistema.bean.Pauta;

public interface HistoricoPautaDaoInterface {

	public HistoricoPauta carregarHistoricoPauta(long id)
			;

	public List<HistoricoPauta> listarHistoricoPautas(Pauta pauta)
			;

	public void salvarHistoricoPauta(HistoricoPauta newInstance)
			;

}