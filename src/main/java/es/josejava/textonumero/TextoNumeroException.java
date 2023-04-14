/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.josejava.textonumero;

/**
 *
 * @author jose-java
 */
public class TextoNumeroException extends Exception {

    public TextoNumeroException(String string) {
        super(string);
    }

    public TextoNumeroException(String string, Throwable thrwble) {
        super(string, thrwble);
    }

    public TextoNumeroException(Throwable thrwbl) {
        super(thrwbl);
    }

    public TextoNumeroException(String string, Throwable thrwbl, boolean bln, boolean bln1) {
        super(string, thrwbl, bln, bln1);
    }
    
    
}
