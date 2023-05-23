package br.com.sne.sistema.gui.util.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.InputMap;
import javax.swing.JPasswordField;
import javax.swing.KeyStroke;
import javax.swing.Timer;

public class JBarCodeInputField extends JPasswordField {
	private static final long serialVersionUID = 1L;
	private BarCodeListener listener = null;
	private boolean cronometroIniciado = false;
	private ActionListener action;
	private Timer timer;
	private boolean debug = false;
	
	
	public JBarCodeInputField() {
		super();
		
		InputMap map = getInputMap(); 
		if(!debug) {
			map.put(KeyStroke.getKeyStroke('V', InputEvent.CTRL_DOWN_MASK), "beep");  
			map.put(KeyStroke.getKeyStroke('C', InputEvent.CTRL_DOWN_MASK), "beep");
			map.put(KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, InputEvent.SHIFT_DOWN_MASK), "beep");
		}

		action = new ActionListener() {  
            public void actionPerformed(ActionEvent e) {
            	if(!debug)
            		setText("");
            	
                System.out.println("timer");
                if(timer != null)
                	timer.stop();
                cronometroIniciado = false;
            }  
        }; 
		
		
		this.addKeyListener(
				new KeyAdapter() {
					public void keyTyped(KeyEvent arg0) {
						if(arg0.isControlDown()) {
							arg0.consume();
						}
						if(!Character.isDigit(arg0.getKeyChar())) {
							arg0.consume();
						} else {
							if(!cronometroIniciado) {
								cronometroIniciado = true;
						        timer = new Timer(1200, action);  
					        	timer.start();  
							}
							
						}
					}
					public void keyPressed(java.awt.event.KeyEvent evt) {
						if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
							if(listener != null) {
								if(timer != null)
									timer.stop();
								
								String code  = new String(getPassword());
								setText("");
								
							//Descomentar para vers�o final
								if(!debug && code.length() == 12)
									code = "0" + code;
								if(!debug) {
									if(validar(code)) {
										listener.barCodeEntered(code.substring(0,12));
									}
								} else {
									listener.barCodeEntered(code);
								}
								
							//	listener.barCodeEntered(code);
								cronometroIniciado = false;
							}
						}
					}
				}
		);
	}
	
	public void setListener(BarCodeListener listener) {
		this.listener = listener;
	}
	
	public boolean validar(String code) {
		if(code.length() != 13)
			return false;
		
		int soma = 0;
		for(int i=0; i <12; i++) {
			soma = soma + Integer.valueOf("" +  code.charAt(i)) * (((i % 2) * 2 + 1));
		}
		int top = 10 * ((int) Math.ceil(soma / 10.0f ));
		int dv = top - soma;
		
		return dv == (Integer.valueOf("" + code.charAt(12)));
	}

}
