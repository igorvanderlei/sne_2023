package br.com.sne.sistema.dao.interfaces;

import java.util.List;

import br.com.sne.sistema.bean.AlertaOSEmergencial;
import br.com.sne.sistema.bean.OrdemServico;

public interface AlertaOSEmergencialDaoInterface {

	public void atualizarAlertaOSEmergencial(AlertaOSEmergencial transientObject) ;
	
	public AlertaOSEmergencial carregarAlertaOSEmergencial(long id) ;

	public List<AlertaOSEmergencial> listarAlertaOSEmergencials()  ;

	public void salvarAlertaOSEmergencial(AlertaOSEmergencial newInstance)  ;

	public abstract AlertaOSEmergencial localizarAlertaPorEmergencial(OrdemServico emergencial)
			;

}