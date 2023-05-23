package br.com.sne.sistema.gui.util;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.*;

public class SwingExceptionHandler implements Thread.UncaughtExceptionHandler {
  public void uncaughtException(final Thread t,
                                final Throwable e) {
    if (SwingUtilities.isEventDispatchThread()) {
      showMessage(t, e);
    } else {
      try {
        SwingUtilities.invokeAndWait(new Runnable() {
          public void run() {
            showMessage(t, e);
          }
        });
      } catch (InterruptedException ie) {
        Thread.currentThread().interrupt();
      } catch (InvocationTargetException ite) {
        // not much more we can do here except log the exception
        ite.getCause().printStackTrace();
      }
    }
  }

  private String generateStackTrace(Throwable e) {
    StringWriter writer = new StringWriter();
    PrintWriter pw = new PrintWriter(writer);
    e.printStackTrace(pw);
    pw.close();
    return writer.toString();
  }

  private void showMessage(Thread t, Throwable e) {
	  e.printStackTrace();
  /*  String stackTrace = generateStackTrace(e);
    
    JTextArea fieldErro = new JTextArea();
    fieldErro.setText(stackTrace);
    JScrollPane scroll = new JScrollPane(fieldErro);
    
    JFrame janelaErro = new JFrame("Erro");
    janelaErro.setSize(new Dimension(600, 400));
    janelaErro.setContentPane(scroll);
    janelaErro.setVisible(true);
    Logger logger = Logger.getLogger("br.com.sne.sistema.gui.util.SwingExceptionHandler");
    logger.error("Deu erro", e);
    */
    
 
  }

}
