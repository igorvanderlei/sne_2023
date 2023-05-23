package br.com.sne.sistema.bean;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.ImageIcon;

import java.util.Base64;

public class Recibo {
	private long id;
	private Despesa despesa;
	private Receita receita;
	private String imagemRecibo;
	private boolean cancelado;	 
	
	public boolean isCancelado() {
		return cancelado;
	}

	public void setCancelado(boolean cancelado) {
		this.cancelado = cancelado;
	}
	
	public boolean getCancelado() {
		return cancelado;
	}

	public Despesa getDespesa() {
		return despesa;
	}
	
	public void setDespesa(Despesa despesa) {
		this.despesa = despesa;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getImagemRecibo() {
		return imagemRecibo;
	}
	public void setImagemRecibo(String imagemRecibo) {
		this.imagemRecibo = imagemRecibo;
	}

	public boolean equals(Object o)  {
		if(o instanceof Recibo) {
			Recibo uo = (Recibo) o;
			return uo.getImagemRecibo().equalsIgnoreCase(this.getImagemRecibo());
		}
		return false;
	}

	public void alterarImagemRecibo(String path) {
		this.imagemRecibo = converterImagemString(path);
	}
	
	public ImageIcon getImagemReciboImage() {
		if(imagemRecibo != null)
			return new ImageIcon(converterStringImageData(imagemRecibo));
		return null;
	}
	
	public byte[] converterStringImageData(String data) {
		return Base64.getDecoder().decode(data);
	}

	public String toString() {
		return despesa.getDescricao();
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

	public Receita getReceita() {
		return receita;
	}

	public void setReceita(Receita receita) {
		this.receita = receita;
	}
	
	
}

