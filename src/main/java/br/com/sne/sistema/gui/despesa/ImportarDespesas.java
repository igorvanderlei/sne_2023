package br.com.sne.sistema.gui.despesa;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringTokenizer;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import br.com.sne.sistema.bean.CentroCusto;
import br.com.sne.sistema.bean.Despesa;
import br.com.sne.sistema.bean.FontePagadora;
import br.com.sne.sistema.bean.Fornecedor;
import br.com.sne.sistema.bean.Unidade;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.util.components.SpreadsheetFilter;


public class ImportarDespesas {
	private List<FontePagadora> fontesPagadoras = Facade.getInstance().listarFontePagadoras();
	private	List<Fornecedor> fornecedores = Facade.getInstance().listarFornecedores();
	private	List<Unidade> unidades = Facade.getInstance().listarUnidade();
	
	public static void main(String[] args) {
		
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new SpreadsheetFilter());
		fc.setAcceptAllFileFilterUsed(false);
        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
	        File file = fc.getSelectedFile();
	        openSpreadSheetFile(file);
        }

	}
	
	private static void openSpreadSheetFile(File file) {
        FileInputStream inputStream;
       
		try {
	        inputStream = new FileInputStream(file);
	        Workbook workbook = new XSSFWorkbook(inputStream);
	        Iterator<Sheet> sheetIterator = workbook.sheetIterator();
	        while(sheetIterator.hasNext()) {
	        	Sheet nextSheet = sheetIterator.next();
	        	Iterator<Row> iterator = nextSheet.iterator();
		        iterator.next();
		        iterator.next();
	        	List<CentroCusto> centrosCusto = Facade.getInstance().listarCentroCustos();
		        while (iterator.hasNext()) {
		            Row nextRow = iterator.next();
		            Iterator<Cell> cellIterator = nextRow.cellIterator();
		            Despesa despesa = new Despesa();
		            while (cellIterator.hasNext()) {
		            	Cell nextCell = cellIterator.next();
		            	importarDespesa(nextRow,nextCell,despesa,centrosCusto);
		            }
		            Facade.getInstance().salvarDespesa(despesa);
		        }
		        
		        workbook.close();
		        inputStream.close();
		        JOptionPane.showMessageDialog(null,"Registros cadastrados com sucesso!");
	        }
	        
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erro ao Importar o documento", "ERRO", JOptionPane.ERROR_MESSAGE);

		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, "Erro de escrita da Data no documento", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private static void importarDespesa(Row nextRow,Cell nextCell, Despesa despesa,List<CentroCusto> centrosCusto) throws ParseException {
		int columnIndex = nextCell.getColumnIndex();	
        NumberFormat formatoDinheiro  = NumberFormat.getInstance(Locale.getDefault());
        formatoDinheiro.setMinimumFractionDigits(2);
        formatoDinheiro.setMaximumFractionDigits(2);
        try {
		    if(columnIndex ==0) {
		    	if(nextCell.getDateCellValue() != null) {
		    		despesa.setDataPagamento(nextCell.getDateCellValue());
		    	}
			}
           	            
	    	else if(columnIndex ==1) {
	        	if(nextCell.getDateCellValue() != null) {
	            	despesa.setDataVencimento(nextCell.getDateCellValue());
	        	}
	        	else {
	        		despesa.setDataVencimento(despesa.getDataPagamento());
	        	}
	    	}
	    	else if(columnIndex ==2) {
	        	if(nextCell.getStringCellValue() != null || nextCell.getStringCellValue() != "")
	        		despesa.setDescricao(nextCell.getStringCellValue());
	    	}
	    	else if(columnIndex ==3) {
	        	if(nextCell.getNumericCellValue() != 0) {
	        		BigDecimal nextCellBD = new BigDecimal(nextCell.getNumericCellValue());
	        		despesa.setValor(nextCellBD);
	        	}
	    	}
	    		 
	    	else if(columnIndex ==4) {
	        	if(nextCell.getStringCellValue() != null || nextCell.getStringCellValue() != "") {
		        	boolean existe = false;
	        		CentroCusto centro = new CentroCusto();
		     		centro.setDeletado(false);
		     		centro.setNome(nextCell.getStringCellValue());
		     		centro.setObservacoes("Centro de Custo criado a partir de uma importação");
		     		for(CentroCusto fp:centrosCusto) {
		     			if(fp.getNome().equals(nextCell.getStringCellValue())) {
		     				centro = fp;
		     				existe = true;
		     				break;
		     			}
		     		}
		     		if(!existe)
		     			Facade.getInstance().salvarCentroCusto(centro);
		     		despesa.setCentroCusto(centro);
	        	}
	    	}
	    	else if(columnIndex ==5) {
	        	if(nextCell.getStringCellValue() != null || nextCell.getStringCellValue() != "")
	        		despesa.setObservacoes(nextCell.getStringCellValue());
	    	}
	    	else if(columnIndex ==6) {
	        	if(nextCell.getStringCellValue() != null || nextCell.getStringCellValue() != "") {
	        		String nome = nextCell.getStringCellValue().toUpperCase();
	                if(nome.contains("PAGO")) {
	                	despesa.setValorPago(despesa.getValor());
	                	despesa.setSituacao(true);
	                }
	                else {
	                 	despesa.setSituacao(false);
	                }
	        	}
	    	}
	        despesa.setEmpresa(Facade.getInstance().carregarUnidade(1));
	  		despesa.setDeletado(false);
        }
        catch(IllegalStateException e) {
        	e.printStackTrace();
        }

        
        /*switch(columnIndex){
    	case 0:
        	if(nextCell.getStringCellValue() != null || nextCell.getStringCellValue() != "") {

        		Date data = formato.parse(nextCell.getStringCellValue());
        		despesa.setDataPagamento(data);
        	}
           	            
    	case 1:
        	if(nextCell.getStringCellValue() != null || nextCell.getStringCellValue() != "") {
        		Date data2 = formato.parse(nextCell.getStringCellValue());
            	despesa.setDataVencimento(data2);
        	}
        	else {
        		despesa.setDataVencimento(despesa.getDataPagamento());
        	}

    	 case 2:
         	if(nextCell.getStringCellValue() != null || nextCell.getStringCellValue() != "") {
         		despesa.setEmpresa(Facade.getInstance().carregarUnidade(1));
         	             	
         	}
    	 case 3:
    		FontePagadora fontePag = new FontePagadora();
    		fontePag.setDeletado(false);
    		fontePag.setNome(nextCell.getStringCellValue());
    		fontePag.setObservacoes("Fonte Pagadora criada a partir de uma importação");
    		for(FontePagadora fp:fontesPagadoras) {
    			if(fp.getNome().equals(nextCell.getStringCellValue())) {
    				fontePag = fp;
    				break;
    			}
    		}
    		Facade.getInstance().salvarFontePagadora(fontePag);
    		despesa.setFontePagadora(fontePag);
    		
    	 case 4:
    		CentroCusto centro = new CentroCusto();
     		centro.setDeletado(false);
     		centro.setNome(nextCell.getStringCellValue());
     		centro.setObservacoes("Centro de Custo criado a partir de uma importação");
     		for(CentroCusto fp:centrosCusto) {
     			if(fp.getNome().equals(nextCell.getStringCellValue())) {
     				centro = fp;
     				break;
     			}
     		}
     		Facade.getInstance().salvarCentroCusto(centro);
     		despesa.setCentroCusto(centro);
         
    	 case 5:
    		Fornecedor fornecedor = new Fornecedor();
      		fornecedor.setDeletado(false);
      		fornecedor.setNome(nextCell.getStringCellValue());
      		fornecedor.setObservacoes("Fornecedor criado a partir de uma importação");
      		for(Fornecedor fp:fornecedores) {
      			if(fp.getNome().equals(nextCell.getStringCellValue())) {
      				fornecedor = fp;
      				break;
      			}
      		}
      		Facade.getInstance().salvarFornecedor(fornecedor);
      		despesa.setFornecedor(fornecedor);
         			
    	 case 6:
    		 despesa.setDescricao(nextCell.getStringCellValue());
    	
    	 case 7:
    		 BigDecimal nextCellBD = new BigDecimal(nextCell.getNumericCellValue());
    		 despesa.setValor(nextCellBD);
    		 
    	 case 8:
    		 despesa.setObservacoes(nextCell.getStringCellValue());
    		 
    	 case 9:
    		 String nome = nextCell.getStringCellValue().toUpperCase();
             if(nome.contains("PAGO")) {
             	
             	despesa.setSituacao(true);
             }
             else {
             	despesa.setSituacao(false);
    }*/
	}
}
