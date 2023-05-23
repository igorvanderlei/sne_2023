package br.com.sne.sistema.gui.util.components;

import java.io.File;
import javax.swing.filechooser.*;
 
public class SpreadsheetFilter extends FileFilter {
 
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
 
        String extension = UtilsDoc.getExtension(f);
        if (extension != null) {
            if (extension.equals(UtilsDoc.xls) ||
                extension.equals(UtilsDoc.xml) ||
                extension.equals(UtilsDoc.xlam) ||
                extension.equals(UtilsDoc.xlsx) ||
                extension.equals(UtilsDoc.xlsm) ||
                extension.equals(UtilsDoc.xlt)) {
                    return true;
            } else {
                return false;
            }
        }
 
        return false;
    }
 
    public String getDescription() {
        return "Arquivos de Planilha";
    }
}

class UtilsDoc {

    public final static String xls = "xls";
    public final static String xml = "xml";
    public final static String xlam = "xlam";
    public final static String xlsx = "xlsx";
    public final static String xlsm = "xlsm";
    public final static String xlt = "xlt";

    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
}