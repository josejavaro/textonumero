/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.josejava.textonumero;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 *
 * @author Otro
 */
public class Op {
    
    protected String quitaTildes(String input) {
        return quitaTildes(input, true);
    }

    protected String quitaTildes(String input, boolean quitaSoloTildes) {

        String original =   "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑ=:+çÇ?¿????/ '\\,.<>{}";
        String convertido = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUN---cC--cIcc----------";
        String output = input;
        
        for(int i=0; i < (quitaSoloTildes ? 14 : original.length()); i++) 
            output = output.replace(original.charAt(i), convertido.charAt(i));

        if(!quitaSoloTildes) {
            if(output.length() > 1) {
                for(int a = 0; a < output.length(); a++) {
                    try {
                        if(output.charAt(a) == '-' && output.charAt(a - 1) == '-') {
                            String parteFinal = "";
                            try {
                                parteFinal = output.substring(a + 1, output.length());
                            } catch(IndexOutOfBoundsException e) {}

                            output = output.substring(0, a) + parteFinal;
                            a--;
                        }
                    } catch(IndexOutOfBoundsException e) {}
                }
            }

            while(true) {
                if(output.endsWith("-"))
                    output = output.substring(0, output.length() - 1);
                else
                    break;
            }

            while(true) {
                if(output.startsWith("-"))
                    output = output.substring(1, output.length());
                else
                    break;
            }
        }

        return output.trim();
    }
    
    protected Object[] getTextoNumero(boolean llardos) {
        ArrayList<String> texto = new ArrayList<>();
        ArrayList<BigInteger> numero = new ArrayList<>();
        
        texto.add("menos");
        numero.add(BigInteger.valueOf(-1));

        texto.add("cero");
        numero.add(BigInteger.ZERO);

        texto.add("uno");
        numero.add(BigInteger.ONE);

        texto.add("un");
        numero.add(BigInteger.ONE);

        texto.add("una");
        numero.add(BigInteger.ONE);

        texto.add("dos");
        numero.add(new BigInteger("2"));

        texto.add("tres");
        numero.add(new BigInteger("3"));

        texto.add("cuatro");
        numero.add(new BigInteger("4"));

        texto.add("cinco");
        numero.add(new BigInteger("5"));

        texto.add("seis");
        numero.add(new BigInteger("6"));

        texto.add("siete");
        numero.add(new BigInteger("7"));

        texto.add("ocho");
        numero.add(new BigInteger("8"));

        texto.add("nueve");
        numero.add(new BigInteger("9"));

        texto.add("diez");
        numero.add(new BigInteger("10"));

        texto.add("once");
        numero.add(new BigInteger("11"));

        texto.add("doce");
        numero.add(new BigInteger("12"));

        texto.add("trece");
        numero.add(new BigInteger("13"));

        texto.add("catorce");
        numero.add(new BigInteger("14"));

        texto.add("quince");
        numero.add(new BigInteger("15"));

        texto.add("dieciséis");
        numero.add(new BigInteger("16"));

        texto.add("diecisiete");
        numero.add(new BigInteger("17"));

        texto.add("dieciocho");
        numero.add(new BigInteger("18"));

        texto.add("diecinueve");
        numero.add(new BigInteger("19"));

        texto.add("veinte");
        numero.add(new BigInteger("20"));

        texto.add("veintiuno");
        numero.add(new BigInteger("21"));

        texto.add("veintiún");
        numero.add(new BigInteger("21"));

        texto.add("veintidós");
        numero.add(new BigInteger("22"));

        texto.add("veintitrés");
        numero.add(new BigInteger("23"));

        texto.add("veinticuatro");
        numero.add(new BigInteger("24"));

        texto.add("veinticinco");
        numero.add(new BigInteger("25"));

        texto.add("veintiséis");
        numero.add(new BigInteger("26"));

        texto.add("veintisiete");
        numero.add(new BigInteger("27"));

        texto.add("veintiocho");
        numero.add(new BigInteger("28"));

        texto.add("veintinueve");
        numero.add(new BigInteger("29"));

        texto.add("treinta");
        numero.add(new BigInteger("30"));

        texto.add("cuarenta");
        numero.add(new BigInteger("40"));

        texto.add("cincuenta");
        numero.add(new BigInteger("50"));

        texto.add("sesenta");
        numero.add(new BigInteger("60"));

        texto.add("setenta");
        numero.add(new BigInteger("70"));

        texto.add("ochenta");
        numero.add(new BigInteger("80"));

        texto.add("noventa");
        numero.add(new BigInteger("90"));

        texto.add("cien");
        numero.add(new BigInteger("100"));

        texto.add("ciento");
        numero.add(new BigInteger("100"));

        texto.add("cientos");
        numero.add(new BigInteger("100"));

        texto.add("cientas");
        numero.add(new BigInteger("100"));

        texto.add("doscientos");
        numero.add(new BigInteger("200"));

        texto.add("doscientas");
        numero.add(new BigInteger("200"));

        texto.add("trescientos");
        numero.add(new BigInteger("300"));

        texto.add("trescientas");
        numero.add(new BigInteger("300"));

        texto.add("cuatrocientos");
        numero.add(new BigInteger("400"));

        texto.add("cuatrocientas");
        numero.add(new BigInteger("400"));

        texto.add("quinientos");
        numero.add(new BigInteger("500"));

        texto.add("quinientas");
        numero.add(new BigInteger("500"));

        texto.add("seiscientos");
        numero.add(new BigInteger("600"));

        texto.add("seiscientas");
        numero.add(new BigInteger("600"));

        texto.add("setecientos");
        numero.add(new BigInteger("700"));

        texto.add("setecientas");
        numero.add(new BigInteger("700"));

        texto.add("ochocientos");
        numero.add(new BigInteger("800"));

        texto.add("ochocientas");
        numero.add(new BigInteger("800"));

        texto.add("novecientos");
        numero.add(new BigInteger("900"));

        texto.add("novecientas");
        numero.add(new BigInteger("900"));

        texto.add("mil");
        numero.add(new BigInteger(dameCeros(3)));

        texto.add("millón");
        numero.add(new BigInteger(dameCeros(6)));

        texto.add("millones");
        numero.add(new BigInteger(dameCeros(6)));

        if(llardos) {
            texto.add("millardo");
            numero.add(new BigInteger(dameCeros(9)));

            texto.add("millardos");
            numero.add(new BigInteger(dameCeros(9)));
        }

        texto.add("billón");
        numero.add(new BigInteger(dameCeros(12)));

        texto.add("billones");
        numero.add(new BigInteger(dameCeros(12)));

        if(llardos) {
            texto.add("billardo");
            numero.add(new BigInteger(dameCeros(15)));

            texto.add("billardos");
            numero.add(new BigInteger(dameCeros(15)));
        }

        texto.add("trillón");
        numero.add(new BigInteger(dameCeros(18)));

        texto.add("trillones");
        numero.add(new BigInteger(dameCeros(18)));

        if(llardos) {
            texto.add("trillardo");
            numero.add(new BigInteger(dameCeros(21)));

            texto.add("trillardos");
            numero.add(new BigInteger(dameCeros(21)));
        }

        texto.add("cuatrillón");
        numero.add(new BigInteger(dameCeros(24)));

        texto.add("cuatrillones");
        numero.add(new BigInteger(dameCeros(24)));

        if(llardos) {
            texto.add("cuatrillardo");
            numero.add(new BigInteger(dameCeros(27)));

            texto.add("cuatrillardos");
            numero.add(new BigInteger(dameCeros(27)));
        }

        texto.add("quintillón");
        numero.add(new BigInteger(dameCeros(30)));

        texto.add("quintillones");
        numero.add(new BigInteger(dameCeros(30)));

        if(llardos) {
            texto.add("quintillardo");
            numero.add(new BigInteger(dameCeros(33)));

            texto.add("quintillardos");
            numero.add(new BigInteger(dameCeros(33)));
        }

        texto.add("sextillón");
        numero.add(new BigInteger(dameCeros(36)));

        texto.add("sextillones");
        numero.add(new BigInteger(dameCeros(36)));

        if(llardos) {
            texto.add("sextillardo");
            numero.add(new BigInteger(dameCeros(39)));

            texto.add("sextillardos");
            numero.add(new BigInteger(dameCeros(39)));
        }

        texto.add("septillón");
        numero.add(new BigInteger(dameCeros(42)));

        texto.add("septillones");
        numero.add(new BigInteger(dameCeros(42)));

        if(llardos) {
            texto.add("septillardo");
            numero.add(new BigInteger(dameCeros(45)));

            texto.add("septillardos");
            numero.add(new BigInteger(dameCeros(45)));
        }

        texto.add("octillón");
        numero.add(new BigInteger(dameCeros(48)));

        texto.add("octillones");
        numero.add(new BigInteger(dameCeros(48)));

        if(llardos) {
            texto.add("octillardo");
            numero.add(new BigInteger(dameCeros(51)));

            texto.add("octillardos");
            numero.add(new BigInteger(dameCeros(51)));
        }

        texto.add("nonillón");
        numero.add(new BigInteger(dameCeros(54)));

        texto.add("nonillones");
        numero.add(new BigInteger(dameCeros(54)));

        if(llardos) {
            texto.add("nonillardo");
            numero.add(new BigInteger(dameCeros(57)));

            texto.add("nonillardos");
            numero.add(new BigInteger(dameCeros(57)));
        }

        texto.add("decillón");
        numero.add(new BigInteger(dameCeros(60)));

        texto.add("decillones");
        numero.add(new BigInteger(dameCeros(60)));
        
        if(llardos) {
            texto.add("decillardo");
            numero.add(new BigInteger(dameCeros(63)));

            texto.add("decillardos");
            numero.add(new BigInteger(dameCeros(63)));
        }

        texto.add("undecillón");
        numero.add(new BigInteger(dameCeros(66)));

        texto.add("undecillones");
        numero.add(new BigInteger(dameCeros(66)));

        if(llardos) {
            texto.add("undecillardo");
            numero.add(new BigInteger(dameCeros(69)));

            texto.add("undecillardos");
            numero.add(new BigInteger(dameCeros(69)));
        }

        texto.add("duodecillón");
        numero.add(new BigInteger(dameCeros(72)));

        texto.add("duodecillones");
        numero.add(new BigInteger(dameCeros(72)));

        if(llardos) {
            texto.add("duodecillardo");
            numero.add(new BigInteger(dameCeros(75)));

            texto.add("duodecillardos");
            numero.add(new BigInteger(dameCeros(75)));
        }

        texto.add("tridecillón");
        numero.add(new BigInteger(dameCeros(78)));

        texto.add("tridecillones");
        numero.add(new BigInteger(dameCeros(78)));

        if(llardos) {
            texto.add("tridecillardo");
            numero.add(new BigInteger(dameCeros(81)));

            texto.add("tridecillardos");
            numero.add(new BigInteger(dameCeros(81)));
        }

        //el resto los dejo, son demasiado grandes ya y no creo que se usen por nadie
        //Aquí están todos:
//        http://es-faq.blogspot.com/2015/10/numeros-grandes.html

//        texto.add("gúgol");
//        numero.add(new BigInteger(dameCeros(100)));
//
//        texto.add("gúgoles");
//        numero.add(new BigInteger(dameCeros(100)));


        return new Object[]{texto, numero};
    }
    
    protected String dameCeros(int cuantos) {
        return dameCeros(cuantos, true);
    }
    
    protected String dameCeros(int cuantos, boolean esNumero) {
        String devu = esNumero ? "1" : "";

        for(int a = 0; a < cuantos; a++)
            devu = devu.concat(esNumero ? "0" : "cero ");

        return devu;
    }
}
