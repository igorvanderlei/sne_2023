package br.com.sne.sistema.bean;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

import br.com.sne.sistema.bean.enumerations.BusinessEnumerations.TipoContrato;
import br.com.sne.sistema.gui.util.components.SpreadsheetFilter;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.util.Base64;

public class Contrato {
	private long id;
	
	@Enumerated(EnumType.ORDINAL)
	private TipoContrato tipo;
	
	private String documentoContrato;
	private String imagemAssinatura;
	private OrdemServico ordemServico;
	private String observacoes;
	public boolean deletado = false;
	
	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public String toString() {
		return ordemServico.getNomeEvento();
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getImagemAssinatura() {
		return imagemAssinatura;
	}
	public void setImagemAssinatura(String imagemAssinatura) {
		this.imagemAssinatura = imagemAssinatura;
	}

	public boolean equals(Object o)  {
		if(o instanceof Contrato) {
			Contrato uo = (Contrato) o;
			return uo.getDocumentoContrato().equalsIgnoreCase(this.getDocumentoContrato());
		}
		return false;
	}

	public void alterarImagemAssinatura(String path) {
		this.imagemAssinatura = converterImagemString(path);
	}
	
	public void alterarContrato(File arquivo) {
		this.documentoContrato = converterPDFString(arquivo);
	}
	
/*	public void alterarAnexo1(File arquivo) {
		this.anexo1 = converterPDFString(arquivo);
	}
	
	public void alterarAnexo2(File arquivo) {
		this.anexo2 = converterPDFString(arquivo);
	}
	*/
	public ImageIcon getImagemAssinaturaImage() {
		if(imagemAssinatura != null)
			return new ImageIcon(converterStringArquivoData(imagemAssinatura));
		return null;
	}
	
	public static byte[] converterStringArquivoData(String data) {
		return Base64.getDecoder().decode(data);
	}

	private String converterImagemString(String path) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			FileInputStream fis = new FileInputStream(path);

			byte[] buf = new byte[1024];
			int len;

			while ((len = fis.read(buf)) > 0){
				baos.write(buf, 0, len);
			}

			byte[] objeto = baos.toByteArray();
			String objetoStr = Base64.getEncoder().encodeToString(objeto);
			fis.close();
			baos.close();
			return objetoStr;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;


	}

	public String converterPDFString(File arquivo) {
        try {
        	FileInputStream fin = new FileInputStream(arquivo);
        	byte buffer[];
            byte conteudo[] = new byte[(int)arquivo.length()];

            fin.read(conteudo);
            fin.close();
            
            buffer = conteudo;
    		String objetoStr = Base64.getEncoder().encodeToString(buffer);
    		return objetoStr;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;

	}
	
	public void salvarImagem(String imagem) {
		if(imagem != null) {
        	try {
        		JFileChooser chooser = new JFileChooser();
	            chooser.setFileFilter (new SpreadsheetFilter());
	   	     	int retrival = chooser.showSaveDialog(null); //Substitui o null pelo "nome da janela".this
	   	     
	   	     	if (retrival == JFileChooser.APPROVE_OPTION) {      
		   	     	File arquivo = chooser.getSelectedFile();
		        	FileOutputStream fw = new FileOutputStream(arquivo);
		            fw.write(converterStringArquivoData(imagem));
		            fw.close();
	   	     	}
	            
        	} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
	}
	
	public String getDocumentoContrato() {
		return documentoContrato;
	}
	public void setDocumentoContrato(String documentoContrato) {
		this.documentoContrato = documentoContrato;
	}
	public OrdemServico getOrdemServico() {
		return ordemServico;
	}
	public void setOrdemServico(OrdemServico ordemServico) {
		this.ordemServico = ordemServico;
	}
	public boolean isDeletado() {
		return deletado;
	}
	public void setDeletado(boolean deletado) {
		this.deletado = deletado;
	}
	public TipoContrato getTipo() {
		return tipo;
	}
	public void setTipo(TipoContrato tipo) {
		this.tipo = tipo;
	}
/*
	public String getAnexo1() {
		return anexo1;
	}

	public void setAnexo1(String anexo1) {
		this.anexo1 = anexo1;
	}

	public String getAnexo2() {
		return anexo2;
	}

	public void setAnexo2(String anexo2) {
		this.anexo2 = anexo2;
	}
	*/
	
}

