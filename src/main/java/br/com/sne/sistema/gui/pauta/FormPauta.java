package br.com.sne.sistema.gui.pauta;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.toedter.calendar.JDateChooser;

import br.com.sne.sistema.auth.AccessManager.permission;
import br.com.sne.sistema.bean.Funcionario;
import br.com.sne.sistema.bean.Pauta;
import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.StatusPauta;
import br.com.sne.sistema.facade.DuplicatedRegisterException;
import br.com.sne.sistema.facade.Facade;
import br.com.sne.sistema.gui.util.JanelaEspera;
import br.com.sne.sistema.gui.util.components.RestricaoLayout;
import br.com.sne.sistema.gui.util.components.SpreadsheetFilter;
import br.com.sne.sistema.gui.util.components.TitledPanel;
import br.com.sne.sistema.gui.util.form.DefaultForm;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class FormPauta extends DefaultForm<Pauta, PautaTableModel> {
	private static final long serialVersionUID = 1L;

	private JComboBox fieldFuncionario;
	private JComboBox fieldStatus;
	private JTextField fieldMes;
	private JDateChooser fieldProxContato;
	private JDateChooser fieldCadastro;
	private JTextField fieldCliente;
	private JTextField fieldContato;
	private JTextField fieldEvento;
	private JTextField fieldTelefone;
	private JTextField fieldEmail;
	private JTextField fieldLocal;
	private JTextField fieldId;
	private JButton buttonImportarPauta;
	private JTextArea fieldHistoricoObservacoes;
	
	private Pauta pauta;
	private JButton buttonAcompanharPauta;

	public FormPauta() {
		super(new PautaTableModel(),"/images/icon_pauta_18.png", "Pautas");
		clear();
	}

	public void createInputFields() {
		fieldFuncionario = new JComboBox();
		fieldHistoricoObservacoes = new JTextArea();
		fieldStatus = new JComboBox();
		fieldMes = new JTextField();
		fieldProxContato = new JDateChooser();
		fieldCadastro = new JDateChooser();
		fieldCliente = new JTextField();
		fieldContato = new JTextField();
		fieldEmail = new JTextField();
		fieldLocal = new JTextField();
		fieldTelefone = new JTextField();
		fieldId = new JTextField();
		fieldEvento = new JTextField();
		
		fieldHistoricoObservacoes.setEditable(false);
		fieldProxContato.setEnabled(false);
		fieldStatus.setEnabled(false);
		fieldId.setEditable(false);
		fieldCadastro.setEnabled(false);

		JScrollPane scrollHistoricoObservacoes = new JScrollPane();
		scrollHistoricoObservacoes.setViewportView(fieldHistoricoObservacoes);
		//scrollHistoricoObservacoes.setMinimumSize(new Dimension(600, 300));
		
		carregarFieldFuncionario();
		carregarFieldStatus();
		
		this.addInputField(new TitledPanel("Id", fieldId), new RestricaoLayout(0, 0, true, false));
		this.addInputField(new TitledPanel("Evento", fieldEvento), new RestricaoLayout(1, 0,2, true, false));
		this.addInputField(new TitledPanel("Cliente", fieldCliente), new RestricaoLayout(2, 0,2, true, false));
		this.addInputField(new TitledPanel("Status", fieldStatus), new RestricaoLayout(0, 4, true, false));
		this.addInputField(new TitledPanel("Data do Evento", fieldMes), new RestricaoLayout(1, 2,1, true, false));
		this.addInputField(new TitledPanel("Data do Cadastro", fieldCadastro), new RestricaoLayout(0, 2	,1, true, false));
		this.addInputField(new TitledPanel("Contato", fieldContato), new RestricaoLayout(2, 2,1, true, false));

		
		this.addInputField(new TitledPanel("Local do Evento", getPanelCodigoLocal()), new RestricaoLayout(2, 3, 2, true, false));
		
		this.addInputField(new TitledPanel("Funcionário", fieldFuncionario), new RestricaoLayout(0,1, true, false));
		this.addInputField(new TitledPanel("E-mail", fieldEmail), new RestricaoLayout(1,3,1, true, false));
		this.addInputField(new TitledPanel("Telefone", fieldTelefone), new RestricaoLayout(1,4, true, false));
		this.addInputField(new TitledPanel("Próx. Contato", fieldProxContato), new RestricaoLayout(0,3,1, true, false));

		this.addInputField(new TitledPanel("Histórico de Observações", scrollHistoricoObservacoes), new RestricaoLayout(3,0,5,1, true, true));

		this.addInputField(new TitledPanel(" ",getButtonAcompanhar()), new RestricaoLayout(4, 0,true, false));
		this.addInputField(new TitledPanel(" ",getButtonImportarPauta()), new RestricaoLayout(4, 1,true, false));


	}
	

	public JPanel getPanelCodigoLocal() {
		JPanel panelRecurso = new JPanel();
		panelRecurso.setLayout(new BoxLayout(panelRecurso, BoxLayout.LINE_AXIS));
		fieldLocal = new JTextField();
		panelRecurso.add(fieldLocal);
		return panelRecurso;
	}
	
	
	
	private void openSpreadSheetFile(File file,Funcionario funcionarioImportacao) {        
		try {
			int i = 0;
			
			FileInputStream inputStream = null;
			Workbook workbook = null;
			Sheet firstSheet = null;

			try {
				inputStream = new FileInputStream(file);
				workbook = new HSSFWorkbook(inputStream);
				
			} catch(OfficeXmlFileException err) {
				try {
					inputStream = new FileInputStream(file);
					workbook = new XSSFWorkbook(inputStream);
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
			
			if(workbook == null) {
				JOptionPane.showMessageDialog(null, "Erro ao Importar o documento\nFormato de arquivo inválido.", "ERRO", JOptionPane.ERROR_MESSAGE);
				return ;
			}
			
			JanelaEspera msg=new JanelaEspera("Lendo pautas da planilha\nPor favor aguarde...","Importar Pautas", FormPauta.this);
			// some long running code that necessitates the wait message
			
			firstSheet = (Sheet) workbook.getSheetAt(0);
			
	        Iterator<Row> iterator = firstSheet.iterator();
	        iterator.next();	        
	        
	        while (iterator.hasNext()) {
	            Row nextRow = iterator.next();
	            
	            Pauta p = new Pauta();
	            p.setStatus(StatusPauta.ABERTO);
	            
	            
	            Iterator<Cell> cellIterator = nextRow.cellIterator();
	            clear();
	            while (cellIterator.hasNext()) {
	            	Cell nextCell = cellIterator.next();
	                preencherCampo(nextRow,nextCell, p);
	            }
	            
                if(p.getNomeEvento()!= null && !p.getNomeEvento().trim().equals("")&& !p.getNomeEvento().toLowerCase().trim().equals("evento")) {
                	p.setFuncionario(Facade.getInstance().getUsuarioLogado().getFuncionario());
        	        Facade.getInstance().salvarPauta(p);
                	i++;
                }
	        }
	        
	        msg.terminate(); 
	        workbook.close();
	        inputStream.close();
	        JOptionPane.showMessageDialog(null, i + " Pautas importadas com sucesso!");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Erro ao Importar o documento", "ERRO", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}


	
	private JButton getButtonImportarPauta() {
		if(buttonImportarPauta == null){
			buttonImportarPauta = new JButton("Importar Pauta");
			buttonImportarPauta.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							JFileChooser fc = new JFileChooser();
							fc.setFileFilter(new SpreadsheetFilter());
							fc.setAcceptAllFileFilterUsed(false);
					        int returnVal = fc.showOpenDialog(FormPauta.this);
					        if (returnVal == JFileChooser.APPROVE_OPTION) {
					        	
					        	if(fieldFuncionario.getSelectedIndex() < 1) {
					        		JOptionPane.showMessageDialog(FormPauta.this, "Selecione o funcionário" , "ERRO", JOptionPane.ERROR_MESSAGE);
									return ;
					        	}
					        
						        File file = fc.getSelectedFile();
						        openSpreadSheetFile(file,(Funcionario)fieldFuncionario.getSelectedItem());
					        }
						}
					}
			);			
		}
		return buttonImportarPauta;
	}
			
	private String getValueFromCell(Cell cell){
		if(cell.getCellType() == CellType.STRING) {
			return cell.getStringCellValue();
		}
		else if(cell.getCellType() == CellType.NUMERIC) {
			return ""+cell.getNumericCellValue();
		}
		return null;
	}
	
	private void preencherCampo(Row nextRow,Cell nextCell, Pauta p){
		int columnIndex = nextCell.getColumnIndex();
		
        //System.out.println(nextCell.getStringCellValue());
		System.out.println(nextCell.getCellType());
        String valorCelula = getValueFromCell(nextCell);
        switch(columnIndex) {
        case 0:
       		p.setData(valorCelula);
        	break;
        case 1:
        	p.setNomeEvento(valorCelula);
        	break;
        case 2:
        	p.setEmpresa(valorCelula);
        	break;
        case 3:
        	p.setLocalEvento(valorCelula);
        	break;
        case 4:
        	p.setContato(valorCelula);
        	break;
        case 5:
        	p.setFone(valorCelula);
        	break;
        case 6:
        	p.setEmail(valorCelula);
        	break;       
        }
      }
		
	private void carregarFieldFuncionario() {
		fieldFuncionario.removeAllItems();
		List<Funcionario> lista = Facade.getInstance().listarFuncionarios();
		fieldFuncionario.addItem(" ");
		for(Funcionario rec: lista)
			fieldFuncionario.addItem(rec);

		fieldFuncionario.setSelectedItem((Funcionario) Facade.getInstance().getUsuarioLogado().getFuncionario());
	}
	
	private void carregarFieldStatus() {
		fieldStatus.removeAllItems();
		for(StatusPauta rec: StatusPauta.values())
			fieldStatus.addItem(rec);
	}
	
	protected void clear() {
		fieldHistoricoObservacoes.setText("");
		pauta = null;
		fieldFuncionario.setSelectedIndex(0);
		fieldStatus.setSelectedIndex(0);
		fieldMes.setText("");
		fieldProxContato.setDate(new Date());
		fieldCliente.setText("");
		fieldContato.setText("");
		fieldEmail.setText("");
		fieldTelefone.setText("");
		fieldId.setText("");
		fieldEvento.setText("");
		fieldLocal.setText("");
		fieldCadastro.setDate(new Date());
	}
	
	private JButton getButtonAcompanhar() {
		if(buttonAcompanharPauta == null){
			buttonAcompanharPauta = new JButton("Acompanhar Pauta");
			buttonAcompanharPauta.addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							if(pauta == null) {
								JOptionPane.showMessageDialog(FormPauta.this, "Selecione uma pauta para acompanhar", "ERRO", JOptionPane.ERROR_MESSAGE);
								return;
							}
							JFrame telaParametro = new TelaParamAcompanharPauta(pauta);
							JDialog dialog = new JDialog(telaParametro, "Acompanhamento de Pauta", true);
							dialog.setContentPane(telaParametro.getContentPane());  
							dialog.setBounds(telaParametro.getBounds());  
							dialog.setVisible(true); 
							clear(); 
						}
					}
			);			
		}
		return buttonAcompanharPauta;
	}

	public boolean save(Pauta current) {
		boolean s = false;
		try {
			current.setDeletado(false);
			current.setDataCadastro(new Date());	
			Facade.getInstance().salvarPauta(current);
			pauta = current;
			s = true;
		}
		catch (DuplicatedRegisterException err) {
			JOptionPane.showMessageDialog(this, "A Pauta já se encontra cadastrada.", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		catch(RuntimeException err) {
			JOptionPane.showMessageDialog(this, "Erro de acesso ao banco de dados", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return s;
	}

	@Override
	public boolean update(Pauta current) {
		Facade.getInstance().atualizarPauta(pauta);
		clear();	
		return true;
	}

	@Override
	public boolean remove(Pauta current) {
		boolean test = false;
		try {
			Facade.getInstance().removerPauta(current);
			test = true;
		} catch (RuntimeException err) {
			JOptionPane.showMessageDialog(this, "Erro ao remover a Pauta.", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return test;
	}

	public boolean print(Pauta current){
		HashMap<String, Object> hm = new HashMap<String, Object>();

		if(pauta != null){
			hm.put("id",  pauta.getId());
			hm.put("imagem", Facade.getInstance().getUsuarioLogado().getUnidade().getImagemFormularioImage().getImage());

			try {
				Connection c  = Facade.getInstance().getConnection() ;
				URL arquivo = getClass().getResource("/br/com/sne/sistema/report/pauta.jasper");  
				JasperReport jasperReport = (JasperReport) JRLoader.loadObject(arquivo);  
				JasperPrint impressao = JasperFillManager.fillReport(jasperReport, hm, c);
				JasperViewer.viewReport(impressao,false);  
			} catch (JRException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			JOptionPane.showMessageDialog(this, "Selecione uma Pauta para poder visualizar sua impressão", "ERRO", JOptionPane.ERROR_MESSAGE);
		}
		return true;
	}

	@Override
	public Pauta newElement() {
		return new Pauta();
	}

	@Override
	public void loadInputFields(Pauta pauta) {
		Funcionario funcionario = null;
		if(fieldFuncionario.getSelectedItem() instanceof Funcionario) {
			funcionario = (Funcionario) fieldFuncionario.getSelectedItem();
		}
		
		String empresa = fieldCliente.getText();
		String email = fieldEmail.getText();
		String data = fieldMes.getText();
		String telefone = fieldTelefone.getText();
		String nomeEvento = fieldEvento.getText();
		String contato = fieldContato.getText();
		String observacoes = fieldHistoricoObservacoes.getText();
		String localEvento = fieldLocal.getText();
		
		pauta.setContato(contato);
		pauta.setData(data);
		pauta.setEmail(email);
		pauta.setEmpresa(empresa);
		pauta.setFone(telefone);
		pauta.setFuncionario(funcionario);
		pauta.setNomeEvento(nomeEvento);
		pauta.setLocalEvento(localEvento);
		
		pauta.setStatus((StatusPauta)fieldStatus.getSelectedItem());

		pauta.setObservacoes(observacoes);
		
	}

	@Override
	protected void loadForm(Pauta pau) {
		fieldId.setText("" + pau.getId());
		fieldCliente.setText(pau.getEmpresa());
		fieldEvento.setText(pau.getNomeEvento());
		fieldEmail.setText(pau.getEmail());
		fieldFuncionario.setSelectedItem(pau.getFuncionario());
		fieldTelefone.setText(pau.getFone());
		fieldMes.setText(pau.getData());
		fieldContato.setText(pau.getContato());
		fieldStatus.setSelectedItem(pau.getStatus());
		fieldProxContato.setDate(pau.getProxContato());
		fieldCadastro.setDate(pau.getDataCadastro());
		fieldLocal.setText(pau.getLocalEvento());
		
		if(pau.getStatus() == StatusPauta.ABERTO) {
			fieldStatus.setForeground(Color.yellow);
		}
		else if(pau.getStatus() == StatusPauta.CANCELADO) {
			fieldStatus.setForeground(Color.red);
		}
		else if(pau.getStatus() == StatusPauta.FOLLOW_UP) {
			fieldStatus.setForeground(Color.blue);
		}
		else {
			fieldStatus.setForeground(Color.green);
		}
			
		fieldHistoricoObservacoes.setText(pau.getObservacoes());
		pauta = pau;
	}

	@Override
	protected boolean validateFormUpdate() {
		return validateFormInsert();
	}

	@Override
	protected boolean validateFormInsert() {
		boolean test = true;
		String error = "ATENÇÃO:";
		if(fieldEvento.getText().length() <= 0) {
			test = false;
			error += "\nPreencha o nome do Evento";
		}
		else if(fieldContato.getText().length() <= 0) {
			test = false;
			error += "\nPreencha o nome do Contato";
		}
		else if(fieldCliente.getText().length() <= 0) {
			test = false;
			error += "\nPreencha o nome do Cliente";
		}
		else if(fieldTelefone.getText().length() <= 0 && fieldEmail.getText().length() <= 0)  {
			test = false;
			error += "\nPreencha um campo de contato (Telefone, Email)";
		}

		if(!test)
			JOptionPane.showMessageDialog(this, error, "ERRO", JOptionPane.ERROR_MESSAGE);

		return test;
	}

	@Override
	public List<Pauta> listAll() {
		List<Pauta> lista;
		int campo = getJComboBoxFiltro().getSelectedIndex();
		String texto = getJTextFielBusca().getText();
		
		
		if(campo == 0 || texto.trim().length() == 0) {
			JOptionPane.showMessageDialog(this, "Selecione uma opção de filtro para listar os dados", "ATENÇÃO", JOptionPane.INFORMATION_MESSAGE);
			return new ArrayList<Pauta>();
		}
		
		if(Facade.getInstance().getUsuarioLogado().getTipoUsuario().getPermissao().contains(permission.LISTAR_TODAS_PAUTAS)){
			lista = Facade.getInstance().listarPautas(campo, texto);
		} else {
			lista = Facade.getInstance().listarPautas(campo, texto, Facade.getInstance().getUsuarioLogado().getFuncionario());
		}

		return lista;
	}
	
	
	

}
