package br.com.sne.sistema;

import java.net.URL;
import java.sql.Connection;
import java.util.HashMap;

import javax.sql.DataSource;
import javax.swing.JFrame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sne.sistema.dao.GrupoDaoHibernate;
import br.com.sne.sistema.facade.Facade;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;


public class TestReport extends JFrame{
	
	
	public TestReport() {
		super("teste");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(400, 300);
		setVisible(true);
	}
	
	
	public void testar() {
		HashMap<String, Object> hm = new HashMap<String, Object>();

		hm.put("id", 1l);
		try {
			URL arquivo = getClass().getResource("/br/com/sne/sistema/report/grupo.jasper");  
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(arquivo);  
			JasperPrint impressao = JasperFillManager.fillReport(jasperReport, hm, Facade.getInstance().getConnection());
			JasperViewer.viewReport(impressao,false);
		} catch (JRException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
