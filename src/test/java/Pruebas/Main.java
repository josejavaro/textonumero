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
            
            String texto = textoNumero.toString(45.89);
            System.out.println(texto);
            
            textoNumero.setMoneda("ES");
            texto = textoNumero.toString(45.846);
            System.out.println(texto);
            
            double d = textoNumero.doubleValue(texto);
            System.out.println(d);
            
            textoNumero.anularMoneda();
            texto = textoNumero.toString(239273947.45);
            System.out.println(texto);
            
            System.out.println(d);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
            
    }
}
