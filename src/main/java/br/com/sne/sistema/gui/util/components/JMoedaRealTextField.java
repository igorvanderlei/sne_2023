package br.com.sne.sistema.gui.util.components;

import java.awt.event.KeyEvent;
import java.math.BigDecimal;

import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;

public class JMoedaRealTextField  extends JTextField {
	private static final long serialVersionUID = 1L;
	boolean teclado = false;
	boolean movendo = false;
	private InputListener listener = null;
	private String mascara = "R$ _.___.___,__"; 

	public InputListener getListener() {
		return listener;
	}

	public void setListener(InputListener listener) {
		this.listener = listener;
	}

	public void clear() {
		setText(mascara);
	}

	public JMoedaRealTextField() {
		setSize(108, 20);
		this.setHorizontalAlignment(JFormattedTextField.RIGHT);  
		addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseReleased(java.awt.event.MouseEvent evt) {
				setCaretPosition(mascara.length());
				evt.consume();
			}
		});

		addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(KeyEvent arg0) {
				arg0.consume();
			}
			public void keyPressed(java.awt.event.KeyEvent evt) {
				if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_BACK_SPACE ) {
					try {
						apagarDigito();
					} catch (BadLocationException ex) {
						ex.printStackTrace();
					}
				}

				if(evt.getKeyCode() == java.awt.event.KeyEvent.VK_DELETE){
					setText(mascara);
				}

				if(Character.isDigit(evt.getKeyChar())) {
					try {
						inserirDigito(Integer.parseInt(""+evt.getKeyChar()));
					} catch (BadLocationException e) {
						e.printStackTrace();
					}
				} 
				setCaretPosition(mascara.length());
				evt.consume();				
			}
		});

		setText(mascara);
		setCaretPosition(mascara.length());
	}

	public void inserirDigito(int offs) throws BadLocationException {
		if (getText(3, 1).equals("_")) {
			movendo = true;
			for (int i = 4; i < mascara.length(); i++) {
				String atual = getText(i-1, 1);
				String proximo = getText(i,1);

				if(atual.equals(".") || atual.equals(",")) {
					continue;
				}
				if (proximo.equals(".") || proximo.equals(",")) {
					proximo = getText(i+1,1);
				}

				select(i-1, i);
				replaceSelection(proximo);
			}

			select(mascara.length() - 1, mascara.length());
			replaceSelection(""+offs);
		}
		setCaretPosition(mascara.length());
		if(listener != null)
			listener.valueChanged();
	}

	public void apagarDigito() throws BadLocationException {
		for (int i = mascara.length(); i > 3; i--) {
			String atual = getText(i-1, 1);
			String anterior = getText(i-2,1);

			if(atual.equals("_"))
				break;

			if(atual.equals(".") || atual.equals(",")) 
				continue;

			if (anterior.equals(".") || anterior.equals(",")) {
				anterior = getText(i-3,1);
			}
			if(anterior.equals(" "))
				anterior="_";
			select(i-1, i);
			replaceSelection(anterior);
		}
		setCaretPosition(mascara.length());
		if(listener != null)
			listener.valueChanged();
	}

	public BigDecimal getValor() {
		BigDecimal valor = new BigDecimal(0);
		BigDecimal j = new BigDecimal(1);
		BigDecimal dez = new BigDecimal(10);
		char c[] = getText().toCharArray();
		for (int i = c.length - 1; i > 2; i--) {
			if(c[i] == '_' || c[i] == ' ')
				break;
			if(c[i] != ',' && c[i] != '.') {
				valor = valor.add(new BigDecimal("" + c[i]).multiply(j));// = valor + j*Integer.parseInt();
				j = j.multiply(dez);
			}
		}
		valor = valor.divide(new BigDecimal(100));
		return valor;
	}

	public void setValor(BigDecimal valor) {
		setText(mascara);
		if(valor != null && !valor.equals(BigDecimal.ZERO)) {
			String sValor = String.valueOf(valor);
			char ponto = '.';
			if(!sValor.contains(".")) {
				sValor = sValor + ".00";
			}
			if (sValor != "") {
				for (int i = 0; i < sValor.length(); i++) {
					if(sValor.charAt(i) != ponto){
						try {
							inserirDigito(Integer.parseInt(""+sValor.charAt(i)));
						} catch (BadLocationException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

}
