package br.com.sne.sistema.gui.googlecalendar;

import com.toedter.calendar.JDateChooser;

import br.com.sne.sistema.auth.AccessManager.permission;
import br.com.sne.sistema.bean.Orcamento;
import br.com.sne.sistema.bean.Usuario;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.SituacaoOrcamento;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.util.components.SpreadsheetFilter;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class TelaParamListarEventos extends JFrame implements ActionListener{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
   
    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
	private static Workbook workbook;
	private static org.apache.poi.ss.usermodel.Font defaultFont;

	private JLabel labelDataInicial;
	private JLabel labelDataFinal;
	private JLabel labelImagem;
	
	private JDateChooser chooserDataInicial;
	private JDateChooser chooserDataFinal;
	
	private JButton botaoOk;
	private JButton botaoCancelar;
	
	
	
    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */

    public static void writeExcel(List<Orcamento> listEvent, Date startDate, Date endDate) throws IOException {
        try {
	    	workbook = new HSSFWorkbook();
	        Sheet sheet = workbook.createSheet();
	     
	        int rowCount = 0;
	     
			SimpleDateFormat formatoDateTime = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
	
			Date startRealDate = formatoDateTime.parse(startDate.toString());
			Date endRealDate = formatoDateTime.parse(endDate.toString());
			
			java.util.Calendar calendarStart = java.util.Calendar.getInstance();
			java.util.Calendar calendarEnd = java.util.Calendar.getInstance();
			calendarStart.setTime(startRealDate);
			calendarEnd.setTime(endRealDate);
			calendarEnd.add(java.util.Calendar.DATE , 1);
            endRealDate = calendarEnd.getTime();
            
			defaultFont= workbook.createFont();
        	defaultFont.setFontHeightInPoints((short)10);
        	defaultFont.setFontName("Arial");
        	defaultFont.setBold(true);
        	
        	org.apache.poi.ss.usermodel.Font whiteDefaultFont = workbook.createFont();
        	whiteDefaultFont.setFontName("Arial");
        	whiteDefaultFont.setColor(IndexedColors.WHITE.getIndex());
        	whiteDefaultFont.setFontHeightInPoints((short)10);
        	whiteDefaultFont.setBold(true);

        	CellStyle style = workbook.createCellStyle();
            style.setFont(whiteDefaultFont);
        	style.setFillForegroundColor(IndexedColors.BLACK.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setWrapText(true);
            
            Row row = sheet.createRow(rowCount);

	        while (startRealDate.before(endRealDate)) {
            	row.setHeight((short)500);   
	        	Cell cell;                  
                for(int i = 1; i <=7;i++) {
	                if(calendarStart.get(java.util.Calendar.DAY_OF_WEEK) == i) {
	                	Date dt = calendarStart.getTime();
	                	String dayOfWeek = calendarStart.getDisplayName(java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.LONG, Locale.getDefault()).toUpperCase();
	                	cell = row.createCell(i-1);
	                	cell.setCellValue(dayOfWeek+"\n"+formato.format(dt));
	                	cell.setCellStyle(style);
	                	
	                    sheet.autoSizeColumn(i-1);
	                    int count = 0;
	                    HashSet<String> incluidos = new HashSet<String>();
	                    for(Orcamento e: listEvent) {
	                    	
	                    	String calendarStartString = formato.format(calendarStart.getTime());
	                    	String eventStart = formato.format(e.getDataInicio());
	                    	String eventEnd = formato.format(e.getDataFim());
	                    	String eventMont = formato.format(e.getDataMontagem());
	                    	
	                    	
	                    	if(calendarStartString.equals(eventMont)){
	                    		if(!incluidos.contains(e.getNomeEvento() + e.getLocal().getEndereco().getEstado())) {

	                    			count++;
	                    			Row row2 = null;
	                    			if(sheet.getLastRowNum() < (rowCount + count))
	                    				row2 = sheet.createRow(rowCount + count);
	                    			else 
	                    				row2 = sheet.getRow(rowCount + count);
	                    			row2.setHeight((short)500);   

	                    			cell = row2.createCell(i-1);
	                    			
	                    			if(eventMont.equals(eventStart)) {
	                    				
	                    			cell.setCellValue("MONTAGEM E EVENTO: "+
	                    					e.getNomeEvento().toUpperCase()+
	                    					"\n"+e.getLocal().getNome());
	                    			} else {
		                    			cell.setCellValue("MONTAGEM: "+
		                    					e.getNomeEvento().toUpperCase()+
		                    					"\n"+e.getLocal().getNome());	                    				
	                    				
	                    			}
	                    			cell.setCellStyle(getCellStyle(e));
	                    			incluidos.add(e.getNomeEvento() + e.getLocal().getEndereco().getEstado());
	                    		}
	                    	}
	                    	//"\nMontagem do evento "+formato.format(e.getDataMontagem()));	
	                    	
	                    	/*if((formato.parse(calendarStartString).after(formato.parse(eventStart)) 
	                    		&& formato.parse(calendarStartString).before(formato.parse(eventEnd)))
   	                    		|| calendarStartString.equals(eventEnd)
   	                    		|| calendarStartString.equals(eventStart)
	                    	)*/
	                    	if(calendarStartString.compareTo(eventEnd) <= 0 && calendarStartString.compareTo(eventStart) >= 0)
	                    	
	                    	{
	                    		if(!incluidos.contains(e.getNomeEvento() + e.getLocal().getEndereco().getEstado())) {

	                    			count++;
	                    			Row row2 = null;
	                    			if(sheet.getLastRowNum() < (rowCount + count))
	                    				row2 = sheet.createRow(rowCount + count);
	                    			else 
	                    				row2 = sheet.getRow(rowCount + count);
	                    			row2.setHeight((short)500);   

	                    			cell = row2.createCell(i-1);
	                    			cell.setCellValue("EVENTO: " + e.getNomeEvento().toUpperCase()+
	                    					"\n"+e.getLocal().getNome());

	                    			cell.setCellStyle(getCellStyle(e));
	                    			incluidos.add(e.getNomeEvento() + e.getLocal().getEndereco().getEstado());
	                    		}
	                    	}
	                    }
	                    if(calendarStart.get(java.util.Calendar.DAY_OF_WEEK) == 7) {
	                    	rowCount = sheet.getLastRowNum() + 2;
	        	            row = sheet.createRow(rowCount);

	                    }
	                    break;
	                }
                }
	           
	            calendarStart.add(java.util.Calendar.DATE , 1);
	            startRealDate = calendarStart.getTime();
	        }
	        JFileChooser chooser = new JFileChooser();
    	    chooser.setFileFilter(new SpreadsheetFilter());
    	    FileOutputStream output = null;
    	    if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) { 
    	    	output = new FileOutputStream(chooser.getSelectedFile()+".xls");
    	        workbook.write(output);
				JOptionPane.showMessageDialog(null,"Planilha criada com sucesso!");
    	    }
    	    else {
    	    	return;
    	    }
	        
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
    }
    
    public static CellStyle getCellStyle(Orcamento e) {
    	CellStyle style2 = workbook.createCellStyle();
		style2.setFont(defaultFont);
		style2.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style2.setAlignment(HorizontalAlignment.CENTER);
		style2.setWrapText(true);

		if(e.getSituacao() == SituacaoOrcamento.ABERTO)
			style2.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		else 
			style2.setFillForegroundColor(getForegroundColorIndex(e.getLocal().getEndereco().getEstado()));
    	
		return style2;
    }
    
    public static short getForegroundColorIndex(String estado) {
    	if(estado.equals("BA"))
           return (IndexedColors.ROSE.getIndex());
		if(estado.equals("RN"))
           return (IndexedColors.LIGHT_TURQUOISE.getIndex());
		if(estado.equals("AL"))
            return(IndexedColors.LIME.getIndex());
		if(estado.equals("PB"))
            return (IndexedColors.LIGHT_YELLOW.getIndex());
		if(estado.equals("PE"))
            return (IndexedColors.LIGHT_GREEN.getIndex());
		if(estado.equals("CE"))
            return(IndexedColors.LIGHT_ORANGE.getIndex());
		if(estado.equals("SP"))
            return(IndexedColors.PALE_BLUE.getIndex());
		if(estado.equals("RJ"))
            return(IndexedColors.PINK.getIndex());
		
		//else geral
        return (IndexedColors.GREY_25_PERCENT.getIndex());
    	
    }
    
    public TelaParamListarEventos() {
		setTitle("Criar Planilha de Eventos");
		setSize(new Dimension(600, 180));
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		setResizable(false);
		
		labelDataInicial = new JLabel("Data Mínima da Planilha:");
		labelDataFinal = new JLabel("Data Máxima da Planilha:");
		
		chooserDataInicial = new JDateChooser();
		chooserDataFinal = new JDateChooser();

		botaoOk = new JButton("Gerar Planilha");
		botaoCancelar = new JButton("Cancelar");
		
		Icon imagem = new ImageIcon(getClass().getResource("/images/OS2_48.png"));
		labelImagem = new JLabel(imagem);

		labelImagem.setBounds(new Rectangle(8, 1, 50, 50));
		labelDataInicial.setBounds(new Rectangle(65, 20, 225, 29));
		chooserDataInicial.setBounds(new Rectangle(65,50,225,29));
		labelDataFinal.setBounds(new Rectangle(300, 20, 235, 29));
		chooserDataFinal.setBounds(new Rectangle(300,50,235,29));

		botaoOk.setBounds(new Rectangle(225,100,140,29));
		
		chooserDataInicial.addPropertyChangeListener(
			new PropertyChangeListener(){
				public void propertyChange(PropertyChangeEvent arg0) {
					chooserDataFinal.setMinSelectableDate(chooserDataInicial.getDate());

				}
			}
		);
		
		chooserDataFinal.addPropertyChangeListener(
				new PropertyChangeListener(){
					public void propertyChange(PropertyChangeEvent arg0) {
						chooserDataInicial.setMaxSelectableDate(chooserDataFinal.getDate());

					}
				}
			);

		getContentPane().add(labelImagem);
		getContentPane().add(labelDataInicial);
		getContentPane().add(chooserDataInicial);
        getContentPane().add(labelDataFinal);
        getContentPane().add(chooserDataFinal);

		getContentPane().add(botaoOk);
		getContentPane().add(botaoCancelar);
				
		botaoOk.addActionListener(this);
		botaoCancelar.addActionListener(this);
	}
    
	@Override
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == botaoOk) {	
			try {
				
				SimpleDateFormat formatoDateTime = new SimpleDateFormat("yyyy-MM-dd");

				 // Build a new authorized API client service.

		        Date min = chooserDataInicial.getDate();
		        Date max = chooserDataFinal.getDate();
				/*
				 * List<OrdemServico> events = null;
				 * 
				 * Usuario u = Facade.getInstance().getUsuarioLogado();
				 * 
				 * if(u.getTipoUsuario().getPermissao().contains(permission.LISTAR_TODAS_OS))
				 * events =
				 * Facade.getInstance().listarOrdemServicosPagamento(chooserDataInicial.getDate(
				 * ),chooserDataFinal.getDate(),false); else events =
				 * Facade.getInstance().listarOrdemServicosPagamento(chooserDataInicial.getDate(
				 * ),chooserDataFinal.getDate(),u.getFuncionario(),false);
				 * 
				 * if (events.isEmpty()) { System.out.println("No upcoming events found."); }
				 * else { System.out.println("Upcoming events"); for (OrdemServico event :
				 * events) { DateTime start = new
				 * DateTime(formatoDateTime.format(event.getDataInicio()));
				 * 
				 * System.out.printf("%s (%s)\n", event.getNomeEvento(), start); } }
				 */
		        
		        List<Orcamento> events = null;
		        List<Orcamento> eventsFinal = new ArrayList<Orcamento>();

		        Usuario u = Facade.getInstance().getUsuarioLogado();
		        
		        if(u.getTipoUsuario().getPermissao().contains(permission.LISTAR_TODOS_ORCAMENTOS))
		        	events = Facade.getInstance().listarOrcamentos(chooserDataInicial.getDate(),chooserDataFinal.getDate());
		        else
		        	events = Facade.getInstance().listarOrcamentos(chooserDataInicial.getDate(),chooserDataFinal.getDate(),u.getFuncionario());
		        
		       // events.addAll(events);
		        
		        if (events.isEmpty()) {
		            System.out.println("No upcoming events found.");
		        } else {
		            System.out.println("Upcoming events");
		            for (Orcamento event : events) {
		            	Date start = event.getDataInicio();
		            	if(event.getSituacao() == SituacaoOrcamento.ABERTO || event.getSituacao() == SituacaoOrcamento.FECHADO)
		            		eventsFinal.add(event);
		            	System.out.printf("%d - %s (%s)\n", event.getId(), event.getNomeEvento(), start);
		            }
		        }
		        System.out.println(events.size() + " - " + eventsFinal.size());
		        writeExcel(eventsFinal,min,max);
				this.dispose();
				return;
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(e.getSource() == botaoCancelar) {
			this.dispose();
			return;
		}		
	}
}