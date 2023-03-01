/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pruebas;

import es.josejava.textonumero.TextoNumero;

/**
 *
 * @author Otro
 */
public class Main {

    public Main() {
    }
    
    
    
    public static void main (String[] arg)  {
        TextoNumero textoNumero = new TextoNumero();
        try {
            textoNumero.setMoneda("ES");
            String holas = textoNumero.toString(45.89);
            System.out.println(holas);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
            
    }
}
