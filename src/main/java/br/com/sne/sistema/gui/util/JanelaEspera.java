package br.com.sne.sistema.gui.util;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

public class JanelaEspera implements Runnable {
    
    private JDialog waitDialog;
    private String text="";
    private String title="";
    private boolean running=false;
    private JInternalFrame main;
    
     
    public JanelaEspera(String text_,String title_, JInternalFrame main) {
        text=text_;
        title=title_;
        this.main = main;
        initialize();
    }
     
    private void initialize()
    {
        JOptionPane msg=new JOptionPane(text,
                JOptionPane.INFORMATION_MESSAGE,JOptionPane.DEFAULT_OPTION,null,new Object[]{},null);
        waitDialog=msg.createDialog(title);
        waitDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        waitDialog.setLocationRelativeTo(main);
        waitDialog.setModal(false);
        waitDialog.pack();
        waitDialog.setVisible(true);
        running=true;
    }
    @Override
    public void run() {
         
        while (running) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                running=false;
            }
        }
    }
 
    public void terminate() {
        running=false;
        waitDialog.dispose();
    }
}