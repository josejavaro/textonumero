/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.josejava.textonumero;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Otro
 */
public class TextoNumero {
    public TextoNumero() {

    }

    public void setDigitosUnitarios(boolean digitosUnitarios) {
        this.digitosUnitarios = digitosUnitarios;
    }

    public void setMoneda(String tipoMonedaEnteros,String tipoMonedaCentimos) throws Exception {
        setMoneda(tipoMonedaEnteros, "", tipoMonedaCentimos, "", 2);
    }

    public void setMoneda(String tipoMonedaEnteros,String tipoMonedaCentimos, int redondeoCentimos) throws Exception {
        setMoneda(tipoMonedaEnteros, "", tipoMonedaCentimos, "", redondeoCentimos);
    }

    public void setMoneda(String tipoMonedaEnteros,String tipoMonedaEnterosPlural, String tipoMonedaCentimos, String tipoMonedaCentimosPlural) throws Exception {
        setMoneda(tipoMonedaEnteros, tipoMonedaEnterosPlural, tipoMonedaCentimos, tipoMonedaCentimosPlural, 2);
    }

    public void setMoneda(String tipoMonedaEnteros,String tipoMonedaEnterosPlural, String tipoMonedaCentimos, String tipoMonedaCentimosPlural, int redondeoDecimales) throws Exception {
        anularMoneda();

        tipoMonedaEnteros = tipoMonedaEnteros == null ? "" : tipoMonedaEnteros.trim();
        tipoMonedaEnterosPlural = tipoMonedaEnterosPlural == null ? "" : tipoMonedaEnterosPlural.trim();
        tipoMonedaCentimos = tipoMonedaCentimos == null ? "" : tipoMonedaCentimos.trim();
        tipoMonedaCentimosPlural = tipoMonedaCentimosPlural == null ? "" : tipoMonedaCentimosPlural.trim();

        if(tipoMonedaEnteros.equals(""))
            throw new Exception("tipoMonedaEnteros no puede estar vacío");

        if(tipoMonedaCentimos.equals(""))
            throw new Exception("tipoMonedaCentimos no puede estar vacío");

        
        this.esMoneda = true;
        this.redondeoDecimales = redondeoDecimales;
        this.tipoMonedaEnteros = dameMoneda(tipoMonedaEnteros);
        this.tipoMonedaCentimos = dameMoneda(tipoMonedaCentimos);
        this.tipoMonedaEnterosPlural = tipoMonedaEnterosPlural.equals("") ? ponPlural(this.tipoMonedaEnteros) : tipoMonedaEnterosPlural;
        this.tipoMonedaCentimosPlural = tipoMonedaCentimosPlural.equals("") ? ponPlural(this.tipoMonedaCentimos) : tipoMonedaCentimosPlural;
    }

    public void setMoneda(String pais) throws Exception {
        asignaMoneda(pais, 2);
    }

    public void setMoneda(String pais, int redondeoDecimales) throws Exception {
        if(redondeoDecimales < 0)
            throw new Exception("redondeoDecimales no puede ser menor que cero");
        
        asignaMoneda(pais, redondeoDecimales);
    }

    public void anularMoneda() {
        esMoneda = false;
        redondeoDecimales = 0;
    }

    public void setRedondeo(int redondeoDecimales) throws Exception {
        if(redondeoDecimales < 0)
            throw new Exception("redondeoDecimales no puede ser menor que cero");
        
        this.redondeoDecimales = redondeoDecimales;
    }

    private void asignaMoneda(String codigoPais, int redondeoDecimales) throws Exception {

        //Códigos alfanuméricos de paises sacados de aquí:
//        https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2

        switch(codigoPais.toUpperCase()) {
            case "ES": //España
                tipoMonedaEnteros = "euro";
                tipoMonedaCentimos = "céntimo";
//                tipoMonedaEnterosPlural = "euros";
//                tipoMonedaCentimosPlural = "céntimos";
                break;

            case "AR": //Argentina
                tipoMonedaEnteros = "peso argentino";
                tipoMonedaCentimos = "centavo";
//                tipoMonedaEnterosPlural = "pesos argentinos";
//                tipoMonedaCentimosPlural = "centavos";
                break;

            case "CL": //Chile
                tipoMonedaEnteros = "peso chileno";
                tipoMonedaCentimos = "centavo";
//                tipoMonedaEnterosPlural = "pesos chilenos";
//                tipoMonedaCentimosPlural = "centavos";
                break;

            case "CO": //Colombia
                tipoMonedaEnteros = "peso colombiano";
                tipoMonedaCentimos = "centavo";
//                tipoMonedaEnterosPlural = "pesos colombianos";
//                tipoMonedaCentimosPlural = "centavos";
                break;

            case "DO": //República Dominicana
                tipoMonedaEnteros = "peso dominicano";
                tipoMonedaCentimos = "centavo";
//                tipoMonedaEnterosPlural = "pesos dominicanos";
//                tipoMonedaCentimosPlural = "centavos";
                break;

            case "CR": //Costa Rica
                tipoMonedaEnteros = "colón";
                tipoMonedaCentimos = "centavo";
//                tipoMonedaEnterosPlural = "colones";
//                tipoMonedaCentimosPlural = "centavos";
                break;

            case "EC": //Ecuador
            case "SV": //El Salvador
            case "PA-DOLAR": //Panamá Dólar
            case "PR": //Puerto Rico
            case "US": //Estados Unidos
                tipoMonedaEnteros = "dólar";
                tipoMonedaCentimos = "centavo";
//                tipoMonedaEnterosPlural = "dólares";
//                tipoMonedaCentimosPlural = "centavos";
                break;

            case "GT": //Guatemala
                tipoMonedaEnteros = "quetzal";
                tipoMonedaCentimos = "centavo";
//                tipoMonedaEnterosPlural = "quetzales";
//                tipoMonedaCentimosPlural = "centavos";
                break;

            case "HN": //Honduras
                tipoMonedaEnteros = "lempira";
                tipoMonedaCentimos = "centavo";
//                tipoMonedaEnterosPlural = "lempiras";
//                tipoMonedaCentimosPlural = "centavos";
                break;

            case "MX": //México
                tipoMonedaEnteros = "peso mexicano";
                tipoMonedaCentimos = "centavo";
//                tipoMonedaEnterosPlural = "pesos mexicanos";
//                tipoMonedaCentimosPlural = "centavos";
                break;

            case "NI": //Nicaragua
                tipoMonedaEnteros = "córdoba";
                tipoMonedaCentimos = "centavo";
//                tipoMonedaEnterosPlural = "córdobas";
//                tipoMonedaCentimosPlural = "centavos";
                break;

            case "PA": //Panamá - Balboa
                tipoMonedaEnteros = "balboa";
                tipoMonedaCentimos = "centésimo";
//                tipoMonedaEnterosPlural = "balboas";
//                tipoMonedaCentimosPlural = "centésimos";
                break;
                
            case "PE": //Perú
                tipoMonedaEnteros = "sol";
                tipoMonedaCentimos = "céntimo";
//                tipoMonedaEnterosPlural = "soles";
//                tipoMonedaCentimosPlural = "céntimos";
                break;

            case "PY": //Paraguay
                tipoMonedaEnteros = "guaraní";
                tipoMonedaCentimos = "céntimo";
//                tipoMonedaEnterosPlural = "guaraníes";
//                tipoMonedaCentimosPlural = "céntimos";
                break;

            case "UY": //Uruguay
                tipoMonedaEnteros = "peso uruguayo";
                tipoMonedaCentimos = "centésimo";
//                tipoMonedaEnterosPlural = "pesos uruguayos";
//                tipoMonedaCentimosPlural = "centésimos";
                break;

            case "VE": //Venezuela
                tipoMonedaEnteros = "bolívar";
                tipoMonedaCentimos = "céntimo";
//                tipoMonedaEnterosPlural = "bolívares";
//                tipoMonedaCentimosPlural = "céntimos";
                break;

            case "BO": //Bolivia
                tipoMonedaEnteros = "boliviano";
                tipoMonedaCentimos = "centavo";
//                tipoMonedaEnterosPlural = "bolivianos";
//                tipoMonedaCentimosPlural = "centavos";
                break;

            default:
                throw new Exception("Código de país no soportado: " + codigoPais);
                
        }

        esMoneda = true;
        tipoMonedaEnterosPlural = ponPlural(tipoMonedaEnteros);
        tipoMonedaCentimosPlural = ponPlural(tipoMonedaCentimos);
        this.redondeoDecimales = redondeoDecimales;
    }

    private String ponPlural(String palabra) {
        ArrayList<String> vocales = new ArrayList<>(Arrays.asList(new String[]{"a", "e", "o", "u"}));
        String[] palabraArray = palabra.trim().split("\\s+");
        palabra = "";

        for(String item : palabraArray) {
            try {
                if(vocales.contains(String.valueOf(Op.quitaTildes(item).charAt(item.length() - 1)).toLowerCase()))
                    palabra = palabra.concat(item).concat("s").concat(" ");
                else {
                    try {
                        if(vocales.contains(String.valueOf(Op.quitaTildes(item).charAt(item.length() - 2)).toLowerCase())) {
                            palabra = palabra.concat(Op.quitaTildes(item)).concat("es").concat(" ");
                        } else {
                            palabra = palabra.concat(item).concat("es").concat(" ");
                        }
                    } catch(IndexOutOfBoundsException e) {
                        palabra = palabra.concat(item).concat("es").concat(" ");
                    }
                }
            } catch(IndexOutOfBoundsException e) {}
        }
        return palabra.trim();
    }

    private String dameMoneda(String moneda) {
        return (moneda.endsWith("s") ? moneda.substring(0, moneda.length() - 1) : moneda).trim();
    }
    
    private Object[] getTextoNumero(boolean llardos) {
        return Op.getTextoNumero(llardos);        
    }

    private ArrayList<String> getTexto(boolean llardos) {
        Object[] textoNumero = getTextoNumero(llardos);
        return (ArrayList<String>)textoNumero[0];
    }

    private ArrayList<BigInteger> getNumero(boolean llardos) {
        Object[] textoNumero = getTextoNumero(llardos);
        return (ArrayList<BigInteger>)textoNumero[1];
    }

    public int intValue(String textoAConvertir) throws Exception {
        BigInteger bigInteger = convierteTextoANumero(textoAConvertir, false);

        int valorEntero = bigInteger.intValue();
        if(bigInteger.compareTo(new BigInteger(Integer.toString(valorEntero))) != 0)
            throw new Exception("Valor muy grande para un número entero: " + textoAConvertir);

        return valorEntero;
    }

    public long longValue(String textoAConvertir) throws Exception {
        return longValue(textoAConvertir, false);
    }

    private long longValue(String textoAConvertir, boolean esParteDecimal) throws Exception {
        BigInteger bigInteger = convierteTextoANumero(textoAConvertir, esParteDecimal);

        long valorLargo = bigInteger.longValue();
        if(bigInteger.compareTo(new BigInteger(Long.toString(valorLargo))) != 0)
            throw new Exception("Valor muy grande para un número entero largo: " + textoAConvertir);

        return valorLargo;
    }

    public BigInteger bigIntegerValue(String textoAConvertir) throws Exception {
        return bigIntegerValue(textoAConvertir, false);
    }

    private BigInteger bigIntegerValue(String textoAConvertir, boolean esParteDecimal) throws Exception {
        return convierteTextoANumero(textoAConvertir, esParteDecimal);
    }

    public double doubleValue(String textoAConvertir) throws Exception {
        BigDecimal valor = bigDecimalValue(textoAConvertir);

        double valorFinal = valor.doubleValue();
        if(valor.compareTo(BigDecimal.valueOf(valorFinal)) != 0)
            throw new Exception("Valor muy grande para un número doble");

        return valorFinal;
    }

    public float floatValue(String textoAConvertir) throws Exception {
        BigDecimal valor = bigDecimalValue(textoAConvertir);

        float valorFinal = valor.floatValue();
        if(valor.compareTo(BigDecimal.valueOf(valorFinal)) != 0)
            throw new Exception("Valor muy grande para un número doble");

        return valorFinal;
    }

    public BigDecimal bigDecimalValue(String textoAConvertir) throws Exception {
        ArrayList<String> separadorDecimalesTotal = new ArrayList<>(Arrays.asList(new String[]{"con", "coma", "punto", ".", ","}));
        ArrayList<String> separadorDecimales = new ArrayList<>(Arrays.asList(new String[]{"con"}));
        DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance();
        DecimalFormatSymbols symbols = format.getDecimalFormatSymbols();
        String separadorDecimalesSistema = Character.toString(symbols.getDecimalSeparator());
        separadorDecimales.add(separadorDecimalesSistema);
        if(separadorDecimalesSistema.equals(","))
            separadorDecimales.add("coma");
        else
            separadorDecimales.add("punto");

        int cuentaCerosIzquierdaDecimal = 0;
        int dondeSeparadorDecimales = -1;
        String[] enterosDecimales = new String[2];
        textoAConvertir = textoAConvertir.toLowerCase();

        for(String separador : separadorDecimalesTotal) {
            if(!separadorDecimales.contains(separador)) {
                int donde = textoAConvertir.indexOf(" " + separador + " ");
                if(donde != -1)
                    textoAConvertir = textoAConvertir.replace(separador + " ", "");
            }
        }

        for(String separador : separadorDecimales) {
            int donde = textoAConvertir.indexOf(" " + separador + " ");
            if(donde != -1) {
                textoAConvertir = textoAConvertir.replace(separador + " ", "");
                dondeSeparadorDecimales = donde;
                break;
            }
        }

        if(dondeSeparadorDecimales != -1) {
            enterosDecimales[0] = textoAConvertir.substring(0, dondeSeparadorDecimales);
            enterosDecimales[1] = textoAConvertir.substring(dondeSeparadorDecimales, textoAConvertir.length());

            //Contar los ceros por la izquierda, ya que va a meterse este valor en un BigInteger van a desaparecer
            cuentaCerosIzquierdaDecimal = cuantosCerosIzquierdaDecimal(enterosDecimales[1]);
        } else {
            enterosDecimales[0] = textoAConvertir;
            enterosDecimales[1] = "0";
        }

        BigInteger[] resultado = new BigInteger[2];
        for(int a = 0; a < enterosDecimales.length; a++) 
            resultado[a] = bigIntegerValue(enterosDecimales[a], a == 1).abs();

        BigInteger valorDecimal = BigInteger.ONE;
        for(int a = 0; a < resultado[1].toString().length() + cuentaCerosIzquierdaDecimal; a++)
            valorDecimal = valorDecimal.multiply(BigInteger.TEN);

        BigDecimal valor = new BigDecimal(resultado[1]);        
        valor = valor.divide(new BigDecimal(valorDecimal));
        valor = valor.add(new BigDecimal(resultado[0]));

//        if(esMoneda || redondeoDecimales != 0)
//        valor = valor.setScale(redondeoDecimales == 0 ? valorDecimal.toString().length() - 1 : (redondeoDecimales > valorDecimal.toString().length() - 1 ? valorDecimal.toString().length() - 1 : redondeoDecimales), RoundingMode.HALF_UP);
        if(redondeoDecimales != 0)
            valor = valor.setScale(redondeoDecimales > valorDecimal.toString().length() - 1 ? valorDecimal.toString().length() - 1 : redondeoDecimales, RoundingMode.HALF_UP);

        return (textoAConvertir.trim().startsWith("menos") && valor.compareTo(BigDecimal.ZERO) != -1 ? valor.negate() : valor);
    }

    private BigInteger convierteTextoANumero(String textoAConvertir, boolean esParteDecimal) throws Exception {
        BigInteger numeroFinal = new BigInteger("0");
        String textoAConvertirOriginal = textoAConvertir;
        String[] eliminaTexto = new String[]{"y", "de"};        
        BigInteger valorASumar = BigInteger.ZERO;
        BigInteger valorASumarParcial = BigInteger.ZERO;
        boolean esNegativo = false;
        boolean sonDigitosUnitarios = true;        
        int largoTextoActual = 0;
        ArrayList<Integer> numeroDigitos = new ArrayList<>();
        String digitosUnitariosStr = "";
        ArrayList<String> texto = getTexto(true);
        ArrayList<BigInteger> numero = getNumero(true);
        int dondeEmpiezaRuptura = texto.indexOf("mil");
        int dondeAntes = texto.size();
        int dondeMillon = numero.indexOf(BigInteger.valueOf(1000000));

        textoAConvertir = Op.quitaTildes(textoAConvertir.trim().toLowerCase(), true);

        try {
            numeroFinal = new BigInteger(textoAConvertir);
            return numeroFinal;
        } catch(NumberFormatException e) {}

        for (String juntarCiento : juntarCientos) {
            if (textoAConvertir.contains(juntarCiento)) {
                textoAConvertir = textoAConvertir.replace(juntarCiento, juntarCiento.replace(" ", ""));
            }
        }

        for(int a = 0; a < texto.size(); a++)
            texto.set(a, Op.quitaTildes(texto.get(a), true));

        for (String eliminaTexto1 : eliminaTexto) {
            textoAConvertir = textoAConvertir.replace(" " + eliminaTexto1 + " ", " ");
        }
        String[] textos = textoAConvertir.split("\\s+");

        textoAConvertir = "";
        ArrayList<String> textosAEliminar = new ArrayList<>();
        for(int a = 0; a < textos.length; a++) {
            if(!texto.contains(textos[a])) {                
                try {
                    long numeroATexto = Long.parseLong(textos[a]);
                    String textoReemplazar = toString(numeroATexto);
                    textos[a] = textoReemplazar;
                    textoAConvertir = "";
                    for (String texto1 : textos) {
                        textoAConvertir = textoAConvertir.concat(texto1).concat(" ");
                    }
                    textos = textoAConvertir.trim().split("\\s+");
                    textoAConvertir = "";
                    a = -1;
                    
                } catch(NumberFormatException e) {
                    //buscar en monedas para quitarlos textos de monedas
                    if(textos[a].equals(Op.quitaTildes(tipoMonedaEnteros.toLowerCase())) || textos[a].equals(Op.quitaTildes(tipoMonedaEnterosPlural.toLowerCase())) || textos[a].equals(tipoMonedaCentimos.toLowerCase()) || textos[a].equals(Op.quitaTildes(tipoMonedaCentimosPlural.toLowerCase())))
                        textosAEliminar.add(textos[a]);
                    else
                        throw new Exception("Palabra no reconocida: " + textos[a] + ". Texto completo: " + textoAConvertirOriginal);
                }
            }

            if(a != -1) {
                textoAConvertir = textoAConvertir.concat(textos[a]).concat(" ");

                try {
                    if(numero.get(texto.indexOf(textos[a])).toString().length() != 1 && !textos[a].equals("menos"))
                        sonDigitosUnitarios = false;
                } catch(IndexOutOfBoundsException e) {}
            }
        }
        if(!textosAEliminar.isEmpty()) {
            for(String textosAEliminarStr : textosAEliminar) 
                textoAConvertir = textoAConvertir.replace(textosAEliminarStr, "");
            textos = textoAConvertir.trim().split("\\s+");
        }
        textoAConvertir = textoAConvertir.trim();

        //mirar que no haya combinaciones del estilo: millón de millones, millón de trillones, billón de billones, etc...
        for(int a = dondeMillon; a < texto.size(); a++) {
            for(int b = dondeMillon; b < texto.size(); b++) {
                int donde = textoAConvertir.indexOf(texto.get(a).concat(" ").concat(texto.get(b)));
                if(donde != -1)
                    throw new Exception("No se permiten combinaciones de millones como: millón de millones, millón de billones, billón de trillones, cuatrillón de billones, etc...");
            }
        }

        for(int a = 0; a < textos.length; a++) {
            if(textos[a].equals("menos")) {
                if(esParteDecimal)
                    throw new Exception("Palabra 'menos' no permitida en la parte decimal: " + textoAConvertirOriginal);
                else {
                    if(a != 0)
                        throw new Exception("Palabra 'menos' no está en el principio de la frase: " + textoAConvertirOriginal);
                    else
                        esNegativo = true;
                }
            } else {
                if(sonDigitosUnitarios) {
                    int dondeTexto = texto.indexOf(textos[a]);
                    digitosUnitariosStr = digitosUnitariosStr.concat(numero.get(dondeTexto).toString());
                } else {
                    int donde = texto.indexOf(textos[a]);
                    BigInteger valorAhora = numero.get(donde);
                    largoTextoActual += textos[a].length() + 1;

                    if(numeroDigitos.contains(valorAhora.toString().length()))
                        throw new Exception("Número en texto mal formado: " + textoAConvertirOriginal);
                    numeroDigitos.add(valorAhora.toString().length());

                    if(donde < dondeAntes) {
                        valorASumar = valorASumar.add(valorAhora);
                    } else {
                        if(donde >= dondeEmpiezaRuptura) {
                            int valorMayor = -1;

                            //buscar si hay un "miles" mayor que este a partir de esta posición
                            try {
                                String subTexto = textoAConvertir.substring(largoTextoActual);
                                for(int b = donde; b < texto.size() && valorMayor == -1; b++)
                                    valorMayor = subTexto.indexOf(texto.get(b));
                            } catch(IndexOutOfBoundsException e) {}


                            if(valorMayor >= 0) {
                                valorASumar = valorASumar.multiply(valorAhora);

                                valorASumarParcial = valorASumarParcial.add(valorASumar);
                                valorASumar = new BigInteger("0");
                            } else {
                                valorASumarParcial = valorASumarParcial.add(valorASumar);
                                valorASumarParcial = valorASumarParcial.multiply(valorAhora);

                                numeroFinal = numeroFinal.add(valorASumarParcial);

                                valorASumarParcial = BigInteger.ZERO;
                                valorASumar = BigInteger.ZERO;                                
                            }

                            numeroDigitos = new ArrayList<>();
                        }                        
                    }
                    dondeAntes = texto.indexOf(textos[a]);
                }
            }
        }

        if(sonDigitosUnitarios) {
            numeroFinal = new BigInteger(digitosUnitariosStr);
        } else {
            if(!valorASumarParcial.equals(new BigInteger("0")))
                valorASumar = valorASumar.add(valorASumarParcial);

            if(!valorASumar.equals(new BigInteger("0")))
                numeroFinal = numeroFinal.add(valorASumar);
        }

        return esNegativo ? numeroFinal.negate() : numeroFinal;
    }

    public String toString(int numeroAConvertir) throws Exception {
        if(digitosUnitarios)
            return dameTextoDigitosUnitarios(BigInteger.valueOf(numeroAConvertir), numeroAConvertir < 0);
        else
            return toString(BigInteger.valueOf(numeroAConvertir));
    }

    public String toString(long numeroAConvertir) throws Exception {
        if(digitosUnitarios)
            return dameTextoDigitosUnitarios(BigInteger.valueOf(numeroAConvertir), numeroAConvertir < 0);
        else
            return toString(BigInteger.valueOf(numeroAConvertir));
    }

    public String toString(float numeroAConvertir) throws Exception {
        if(digitosUnitarios)
            return dameTextoDigitosUnitarios(BigDecimal.valueOf(numeroAConvertir));
        else
            return toString(BigDecimal.valueOf(numeroAConvertir));
    }

    public String toString(double numeroAConvertir) throws Exception {
        if(digitosUnitarios) {
            return dameTextoDigitosUnitarios(BigDecimal.valueOf(numeroAConvertir));
        } else
            return toString(BigDecimal.valueOf(numeroAConvertir));
    }

    public String toString(BigDecimal numeroAConvertir) throws Exception {
        String monedaEnteros = "";
        String monedaCentimos = "";
        String[] enteroDecimal = numeroAConvertir.toString().split("\\.");

        if(redondeoDecimales != 0) {
            if(enteroDecimal[1] == null || enteroDecimal[1].equals(""))
                enteroDecimal[1] = "0";

            numeroAConvertir = numeroAConvertir.setScale(redondeoDecimales > enteroDecimal[1].length() ? enteroDecimal[1].length() : redondeoDecimales, RoundingMode.HALF_UP);
            enteroDecimal = numeroAConvertir.toString().split("\\.");
        }

        //mirar si la parte decimal tiene ceros a su izquierda
        int cuentaCerosIzquierdaDecimal = cuantosCerosIzquierdaDecimal(enteroDecimal[1]);

        String[] valor = numeroAConvertir.toPlainString().split("\\.");
        for(int a = 0; a < valor.length; a++) 
            valor[a] = toString(new BigInteger(valor[a])).trim();

        BigInteger bigIntegerEurosParteEntera = convierteTextoANumero(valor[0], false);
        BigInteger bigIntegerEurosParteDecimal = BigInteger.ONE;

        try {
            bigIntegerEurosParteDecimal = convierteTextoANumero(valor[1], true);
        } catch(IndexOutOfBoundsException e) {

        }
        if(esMoneda) {
            String monedaTmp = " ";

            //si acaba en millones y trillones o de ahí hacia adelante poner la preposición "de" delante de la moneda
            String[] valoresTmp = valor[0].split("\\s+");
            ArrayList<String> textoTmp = getTexto(true);
            ArrayList<BigInteger> numeroTmp = getNumero(true);
            int donde = textoTmp.indexOf(valoresTmp[valoresTmp.length - 1]);
            if(donde >= numeroTmp.indexOf(BigInteger.valueOf(1000000)))
                monedaTmp = " de ";

            monedaEnteros = monedaTmp.concat(bigIntegerEurosParteEntera.compareTo(BigInteger.ONE) == 0 ? tipoMonedaEnteros : tipoMonedaEnterosPlural);
            monedaCentimos = " ".concat(bigIntegerEurosParteDecimal.compareTo(BigInteger.ONE) == 0 ? tipoMonedaCentimos : tipoMonedaCentimosPlural);
        }

        if(bigIntegerEurosParteEntera.compareTo(BigInteger.ZERO) == 0 && numeroAConvertir.compareTo(BigDecimal.ZERO) == -1)
            valor[0] = "menos ".concat(valor[0]);

        if(cuentaCerosIzquierdaDecimal > 0 && valor[1].trim().equals("cero"))
            cuentaCerosIzquierdaDecimal = 0;

        try {
            return valor[0].concat(monedaEnteros).concat(" con ").concat(Op.dameCeros(cuentaCerosIzquierdaDecimal, false)).concat(valor[1]).concat(monedaCentimos);
        } catch(IndexOutOfBoundsException e) {
            return valor[0].concat(monedaEnteros);
        }
    }


    private String toString(BigInteger numeroAConvertir) {
        String textoAConvertir = numeroAConvertir.toString();
        String textoFinal = "";
        String textoTmp = "";
        ArrayList<String> texto = getTexto(false);
        ArrayList<BigInteger> numero = getNumero(false);
        BigInteger multiplicarTotal = BigInteger.ONE;        
        int contaTotal = 1;
        String textoMiles = "";
        int dondeMilTotal = 0;
        int dondeMillon = numero.indexOf(BigInteger.valueOf(1000000));
        int dondeMil = numero.indexOf(BigInteger.valueOf(1000));
        int dondeUno = numero.indexOf(BigInteger.ONE);
        boolean esNegativo = false;
        boolean esMiles = false;
        String tmpTextoAntes = "";

        if(textoAConvertir.charAt(0) == '-') {
            esNegativo = true;
            textoAConvertir = textoAConvertir.substring(1);
        }
        
        for(int a = textoAConvertir.length() - 1; a >= 0; a--, contaTotal++)  {            
            textoTmp = textoAConvertir.substring(a, a + 1) + textoTmp;

            if(a < textoAConvertir.length() - 6) {
                int dondeMilTmp = numero.indexOf(multiplicarTotal);
                if(dondeMilTmp != -1) {
                    textoMiles = texto.get(dondeMilTmp);
                    dondeMilTotal = dondeMilTmp;
                }
//                else if(textoMiles.equals("")) {
//                    dondeMilTmp = numero.indexOf(BigInteger.valueOf(multiplicar));
//                    if(dondeMilTmp != -1) {
//                        textoMiles = texto.get(dondeMilTmp);
//                        dondeMilTotal = dondeMilTmp;
//                    }
//                }
            }
            
            if((contaTotal % 3 == 0 && a != textoAConvertir.length() - 1) || a == 0) {
                String tmp = convierteTresDigitosATexto(textoTmp);

                if(esMiles) {
                    //mirar si el texto de millones ya lo tiene el textoFinal y si lo tiene no ponerlo
                    if(textoFinal.contains(Op.quitaTildes(textoMiles, true)))
                        textoMiles = "";

                    tmpTextoAntes = textoTmp;
                    textoMiles = texto.get(dondeMil).concat(textoMiles.equals("") ? "" : " ").concat(textoMiles);
                }

                if((!tmp.equals("") && !tmp.equals("000") )|| (!tmpTextoAntes.equals("") && !tmpTextoAntes.equals("000"))) {
                    String tmpAhora = textoTmp + tmpTextoAntes;
                    if(!textoMiles.equals("") && dondeMilTotal >= dondeMillon && !textoMiles.trim().equals(texto.get(dondeMil))) {
                        boolean ponPlural = false;

                        try {
                            String subAtrasTres = textoAConvertir.substring(a - 1, a);
                            if(!subAtrasTres.equals("0"))
                                ponPlural = true;
                        } catch(IndexOutOfBoundsException e) {}

                        if(Integer.parseInt(tmpAhora) > 1 || ponPlural)
                            textoMiles = Op.quitaTildes(textoMiles, true).concat("es");
                    }

                    if(tmp.trim().endsWith(texto.get(dondeUno)) && !textoMiles.equals("")) 
                        tmp = cambiaUnoFinal(tmp, textoMiles, dondeMilTotal);

                    textoFinal = tmp.concat(textoMiles).concat(textoMiles.equals("") ? "" : " ").concat(textoFinal);
                }
                
                if(esMiles) {
                    textoMiles = "";                
                    tmpTextoAntes = "";
                }
                
                textoTmp = "";
            }

            multiplicarTotal = multiplicarTotal.multiply(BigInteger.valueOf(10));

            if(contaTotal % 3 == 0)
                esMiles = !esMiles;
        }

        textoFinal = textoFinal.trim();

        if(textoFinal.equals(""))
            textoFinal = texto.get(numero.indexOf(BigInteger.ZERO));

        if(esNegativo && !textoFinal.equals(""))
            textoFinal = "menos ".concat(textoFinal);

        if(esMoneda && textoFinal.endsWith(texto.get(dondeUno))) 
            textoFinal = cambiaUnoFinal(textoFinal, "", texto.size());        

        return textoFinal;
    }

    
    private String cambiaUnoFinal(String tmp, String textoMiles, int dondeMilTotal) {
        ArrayList<String> texto = getTexto(false);
        ArrayList<BigInteger> numero = getNumero(false);
        int dondeMillon = numero.indexOf(BigInteger.valueOf(1000000));
        int dondeMil = numero.indexOf(BigInteger.valueOf(1000));
        int dondeVeintiuno = numero.indexOf(BigInteger.valueOf(21));
        int dondeUno = numero.indexOf(BigInteger.ONE);        
        String[] tmpS = tmp.split("\\s+");
        int itemFinal = tmpS.length;

        if(tmp.trim().length() == texto.get(dondeUno).length()) {
            if(dondeMilTotal >= dondeMillon) {
                String[] textoMilesS = textoMiles.split("\\s+");
                if(textoMilesS[0].equals(texto.get(dondeMil)))
                    itemFinal = tmpS.length - 1;
                else {
                    int dondeUnoTmp = numero.indexOf(BigInteger.valueOf(1)) + 1;
                    tmpS[tmpS.length - 1] = texto.get(dondeUnoTmp);
                }
            } else {
                itemFinal = tmpS.length - 1;
            }
        } else {
            tmpS[tmpS.length - 1] = tmpS[tmpS.length - 1].substring(0, tmpS[tmpS.length - 1].length() - 1);
            if(tmpS[tmpS.length - 1].endsWith(Op.quitaTildes(texto.get(dondeVeintiuno + 1), true))) {
                tmpS[tmpS.length - 1] = texto.get(dondeVeintiuno + 1);
            }
        }

        tmp = "";
        for(int b = 0; b < itemFinal; b++)
            tmp = tmp.concat(tmpS[b]).concat(" ");

        return tmp;
    }

    private String convierteTresDigitosATexto(String textoAConvertir)  {
        String textoFinal = "";
        int multiplicar = 1;
        ArrayList<String> texto = getTexto(false);
        ArrayList<BigInteger> numero = getNumero(false);
        int dondeCien = numero.indexOf(BigInteger.valueOf(100));

        while(true) {
            try {
                if(textoAConvertir.charAt(0) == '0')
                    textoAConvertir = textoAConvertir.substring(1);
                else
                    break;
            } catch(IndexOutOfBoundsException e) {
                break;
            }
        }

        for(int a = textoAConvertir.length() - 1; a >= 0; a--)  {
            int tmpInt = 0;
            int donde = 0;

            if(multiplicar == 10) {
                tmpInt = Integer.parseInt(textoAConvertir.substring(a, a + 2));                
                donde = numero.indexOf(BigInteger.valueOf(tmpInt));
                if(donde != -1)
                    textoFinal = "";
            }

            if(donde == 0 || donde == -1)
                tmpInt = Integer.parseInt(textoAConvertir.substring(a, a + 1)) * multiplicar;

            if(tmpInt != 0) {
                donde = numero.indexOf(BigInteger.valueOf(tmpInt));
                textoFinal = texto.get(donde == dondeCien && !textoFinal.equals("") ? donde + 1 : donde).concat(!textoFinal.equals("") && multiplicar == 10 ? " y " : " ").concat(textoFinal);
            }

            multiplicar *= 10;            

            if(a % 3 == 0)
                multiplicar = 1;
        }

        return textoFinal;
    }

    private String dameTextoDigitosUnitarios(BigInteger entrada, boolean esNegativo) throws Exception {
        ArrayList<String> texto = getTexto(true);
        ArrayList<BigInteger> numero = getNumero(true);
        String textos = entrada.abs().toString();
        String textoFinal = "";

        for(int a = 0; a < textos.length(); a++) 
            textoFinal = textoFinal.concat(texto.get(numero.indexOf(new BigInteger(String.valueOf(textos.charAt(a)))))).concat(" ");

        return (esNegativo ? "menos " : "").concat(textoFinal.trim());
    }

    private String dameTextoDigitosUnitarios(BigDecimal entrada) throws Exception {        
        String textoFinal = "";
        String[] textos;

        if(redondeoDecimales != 0) {
            String[] enteroDecimal = entrada.toString().split("\\.");
            if(enteroDecimal[1] == null || enteroDecimal[1].equals(""))
                enteroDecimal[1] = "0";
            
            entrada = entrada.setScale(redondeoDecimales > enteroDecimal[1].length() ? enteroDecimal[1].length() : redondeoDecimales, RoundingMode.HALF_UP);
        }
        textos = entrada.toString().split("\\.");

        for(int a = 0; a < textos.length; a++) {
            BigInteger bigInteger = new BigInteger(textos[a]);
            boolean esNegativo = a == 0 ? entrada.compareTo(BigDecimal.ZERO) == -1 : false;
            int cuentaCerosIzquierdaDecimal = a == 0 ? 0 : cuantosCerosIzquierdaDecimal(textos[a]);
            textoFinal = textoFinal.concat(a == 1 ? (esMoneda ? tipoMonedaEnterosPlural : "").concat(" con ") : "").concat((Op.dameCeros(cuentaCerosIzquierdaDecimal, false)) + dameTextoDigitosUnitarios(bigInteger, esNegativo)).concat(" ").concat(a == 1 && esMoneda ? tipoMonedaCentimosPlural : "");
        }

        return textoFinal.trim().replaceAll("\\s+"," ");
    }

    private int cuantosCerosIzquierdaDecimal(String entrada) {
        int cuentaCerosIzquierdaDecimal = 0;
        entrada = entrada.trim();
        try {
            Long.parseLong(entrada);
            for(int a = 0; a < entrada.length(); a++)
                if(entrada.charAt(a) == '0')
                    cuentaCerosIzquierdaDecimal++;
                else
                    break;
            
        } catch(NumberFormatException e) {
            String [] enterosDecimalesArray = entrada.split("\\s+");
            for (String enterosDecimalesArray1 : enterosDecimalesArray) {
                if (enterosDecimalesArray1.equals("cero")) {
                    cuentaCerosIzquierdaDecimal++;
                } else {
                    break;
                }
            }
        }

        return cuentaCerosIzquierdaDecimal;
    }
    
    private boolean esMoneda;
    private String tipoMonedaEnteros = "";
    private String tipoMonedaCentimos = "";
    private String tipoMonedaEnterosPlural = "";
    private String tipoMonedaCentimosPlural = "";
    private int redondeoDecimales;
    private boolean digitosUnitarios;
    private final String[] juntarCientos = new String[]{"dos cientos", "dos cientas", "tres cientos", "tres cientas", "cuatro cientos", "cuatro cientas", "seis cientos", "seis cientas", "ocho cientos", "ocho cientas"};
}
