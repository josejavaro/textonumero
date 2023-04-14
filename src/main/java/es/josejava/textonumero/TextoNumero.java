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
 * @author jose-java
 */
public class TextoNumero extends Op {
    
    private boolean esMoneda;
    private String tipoMonedaEnteros = "";
    private String tipoMonedaCentimos = "";
    private String tipoMonedaEnterosPlural = "";
    private String tipoMonedaCentimosPlural = "";
    private int redondeoDecimales;
    private boolean digitosUnitarios;
    private String separadorEnterosDecimales = "con";
    private final ArrayList<String> separadoresEnterosDecimales = new ArrayList<>();
    private boolean monedaFemenino;
    private boolean autodetectaDecimalesMoneda = true;
    private final String[] juntarCientos = new String[]{"dos cientos", "dos cientas", "tres cientos", "tres cientas", "cuatro cientos", "cuatro cientas", "seis cientos", "seis cientas", "ocho cientos", "ocho cientas"};
    private final String[] eliminaTexto = new String[]{"y", "de"};
    private boolean ya;
    
    
    public TextoNumero() {
        
    }
    
    public void setDigitosUnitarios(boolean digitosUnitarios) {
        this.digitosUnitarios = digitosUnitarios;
    }

    public void setMoneda(String tipoMonedaEnteros,String tipoMonedaCentimos) throws TextoNumeroException {
        setMoneda(tipoMonedaEnteros, "", tipoMonedaCentimos, "", 2);
    }

    public void setMoneda(String tipoMonedaEnteros,String tipoMonedaCentimos, int redondeoCentimos) throws TextoNumeroException {
        setMoneda(tipoMonedaEnteros, "", tipoMonedaCentimos, "", redondeoCentimos);
    }

    public void setMoneda(String tipoMonedaEnteros,String tipoMonedaEnterosPlural, String tipoMonedaCentimos, String tipoMonedaCentimosPlural) throws TextoNumeroException {
        setMoneda(tipoMonedaEnteros, tipoMonedaEnterosPlural, tipoMonedaCentimos, tipoMonedaCentimosPlural, 2);
    }

    public void setMoneda(String tipoMonedaEnteros,String tipoMonedaEnterosPlural, String tipoMonedaCentimos, String tipoMonedaCentimosPlural, int redondeoDecimales) throws TextoNumeroException {
        anularMoneda();

        tipoMonedaEnteros = tipoMonedaEnteros == null ? "" : tipoMonedaEnteros.trim();
        tipoMonedaEnterosPlural = tipoMonedaEnterosPlural == null ? "" : tipoMonedaEnterosPlural.trim();
        tipoMonedaCentimos = tipoMonedaCentimos == null ? "" : tipoMonedaCentimos.trim();
        tipoMonedaCentimosPlural = tipoMonedaCentimosPlural == null ? "" : tipoMonedaCentimosPlural.trim();

        if(tipoMonedaEnteros.equals(""))
            throw new TextoNumeroException("tipoMonedaEnteros no puede estar vacío");

        if(tipoMonedaCentimos.equals(""))
            throw new TextoNumeroException("tipoMonedaCentimos no puede estar vacío");

        
        this.esMoneda = true;
        this.redondeoDecimales = redondeoDecimales;
        this.tipoMonedaEnteros = dameMoneda(tipoMonedaEnteros);
        this.tipoMonedaCentimos = dameMoneda(tipoMonedaCentimos);
        this.tipoMonedaEnterosPlural = tipoMonedaEnterosPlural.equals("") ? ponPlural(this.tipoMonedaEnteros) : tipoMonedaEnterosPlural;
        this.tipoMonedaCentimosPlural = tipoMonedaCentimosPlural.equals("") ? ponPlural(this.tipoMonedaCentimos) : tipoMonedaCentimosPlural;
    }

    public void setMoneda(String pais) throws TextoNumeroException {
        asignaMoneda(pais, 2);
    }

    public void setMoneda(String pais, int redondeoDecimales) throws TextoNumeroException {
        if(redondeoDecimales < 0)
            throw new TextoNumeroException("redondeoDecimales no puede ser menor que cero");
        
        asignaMoneda(pais, redondeoDecimales);
    }
    
    public void anularMoneda(boolean anularDecimales) {
        esMoneda = false;
        redondeoDecimales = anularDecimales ? 0 : redondeoDecimales;
    }

    public void anularMoneda() {
        anularMoneda(false);
    }        

    public void setRedondeo(int redondeoDecimales) throws TextoNumeroException {
        if(redondeoDecimales < 0)
            throw new TextoNumeroException("redondeoDecimales no puede ser menor que cero");
        
        this.redondeoDecimales = redondeoDecimales;
    }

    private void asignaMoneda(String codigoPais, int redondeoDecimales) throws TextoNumeroException {
        String[][] paises = getCodigosPaises();        
    
        for(String[] pais : paises) {
            if(pais[0].equals(codigoPais.trim())) {
                tipoMonedaEnteros = pais[2];
                tipoMonedaCentimos = pais[3];
                break;
            }    
        }

        esMoneda = true;
        tipoMonedaEnterosPlural = ponPlural(tipoMonedaEnteros);
        tipoMonedaCentimosPlural = ponPlural(tipoMonedaCentimos);
        this.redondeoDecimales = redondeoDecimales;
    }
    
    public String[][] getCodigosPaises() {
//        Códigos alfabéticos de paises extraídos de aquí:
//        https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2
        
        String[][] paises = new String[][]{
            {"ES", "España", "euro", "céntimo"},
            {"AR", "Argentina", "peso argentino", "centavo"},
            {"CL", "Chile", "peso chileno", "centavo"},
            {"CO", "Colombia", "peso colombiano", "centavo"},
            {"DO", "República Dominicana", "peso dominicano", "centavo"},
            {"CR", "Costa Rica", "colón", "centavo"},
            {"SV", "El Salvador", "dólar", "centavo"},
            {"EC", "Ecuador", "dólar", "centavo"},
            {"PR", "Puerto Rico", "dólar", "centavo"},
            {"PA", "Panamá", "balboa", "centésimo"},
            {"PA-DOLAR", "Panamá", "dólar", "centavo"},
            {"US", "Estados Unidos", "dólar", "centavo"},
            {"GT", "Guatemala", "quetzal", "centavo"},
            {"HN", "Honduras", "lempira", "centavo"},
            {"MX", "México", "peso mexicano", "centavo"},
            {"NI", "Nicaragua", "córdoba", "centavo"},
            {"PE", "Perú", "sol", "céntimo"},
            {"PY", "Paraguay", "guaraní", "céntimo"},
            {"UY", "Uruguay", "peso uruguayo", "centésimo"},
            {"VE", "Venezuela", "bolivar", "céntimo"},
            {"BO", "Bolivia", "boliviano", "centavo"},
        };
        
        //ordenar el array por el código de país
        Arrays.sort(paises, (String[] a, String[] b) -> a[0].compareTo(b[0]));
        
        return paises;
    }
    
    public ArrayList<String> getPaises() {
        ArrayList<String> paisesList = new ArrayList<>();
        String[][] paises = getCodigosPaises();
        
        for(String[] pais : paises) {
            paisesList.add("");
            for(String paisArray : pais) 
                paisesList.set(paisesList.size() - 1, paisesList.get(paisesList.size() - 1).concat(paisArray).concat(", "));
            paisesList.set(paisesList.size() - 1, paisesList.get(paisesList.size() - 1).substring(0, paisesList.get(paisesList.size() - 1).length() - 2));            
        }
        
        return paisesList;
    }
    
    public void setAutodetectaDecimalesMoneda(boolean autodetectaDecimalesMoneda) {
        this.autodetectaDecimalesMoneda = autodetectaDecimalesMoneda;
    }

    private String ponPlural(String palabra) {
        ArrayList<String> vocales = new ArrayList<>(Arrays.asList(new String[]{"a", "e", "o", "u"}));
        String[] palabraArray = palabra.trim().split("\\s+");
        palabra = "";

        for(String item : palabraArray) {
            try {
                if(vocales.contains(String.valueOf(quitaTildes(item).charAt(item.length() - 1)).toLowerCase()))
                    palabra = palabra.concat(item).concat("s").concat(" ");
                else {
                    try {
                        char c = item.charAt(item.length() - 2);
                        if(!vocales.contains(String.valueOf(c)) && vocales.contains(quitaTildes(String.valueOf(c)))) 
                            palabra = palabra.concat(item.substring(0, item.length() - 2)).concat(quitaTildes(String.valueOf(c))).concat(item.substring(item.length() - 1)).concat("es").concat(" ");
                        else
                            palabra = palabra.concat(item).concat("es").concat(" ");
                        
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
    
//    private Object[] getTextoNumero(boolean llardos) {
//        return getTextoNumero(llardos);        
//    }

    private ArrayList<String> getTexto(boolean llardos) {
        Object[] textoNumero = getTextoNumero(llardos);
        return (ArrayList<String>)textoNumero[0];
    }

    private ArrayList<BigInteger> getNumero(boolean llardos) {
        Object[] textoNumero = getTextoNumero(llardos);
        return (ArrayList<BigInteger>)textoNumero[1];
    }
    
    public void addSeparadorEnterosDecimales(String separadorEnterosDecimales) throws TextoNumeroException{
        separadorEnterosDecimales = separadorEnterosDecimales.trim().toLowerCase();
        
        if(separadorEnterosDecimales.equals("y"))
            throw new TextoNumeroException("Separador 'y' no permitido, se puede confundir con la conjunción de cantidades como: treinta y cinco, ochenta y nueve, etc...");
        else if(separadorEnterosDecimales.equals(""))
            throw new TextoNumeroException("No se ha especificado un separador de decimales ya que está vacío");
        else if(((ArrayList<String>)getTexto(true)).indexOf(separadorEnterosDecimales) != -1) 
            throw new TextoNumeroException("Separador de decimales no válido: " + separadorEnterosDecimales + " es una cantidad númerica en texto");
            
        this.separadorEnterosDecimales = separadorEnterosDecimales;
        if(!this.separadoresEnterosDecimales.contains(separadorEnterosDecimales))
            this.separadoresEnterosDecimales.add(separadorEnterosDecimales);
    }

    public int intValue(String textoAConvertir) throws TextoNumeroException {
        BigInteger bigInteger = convierteTextoANumero(textoAConvertir, false);

        int valorEntero = bigInteger.intValue();
        if(bigInteger.compareTo(new BigInteger(Integer.toString(valorEntero))) != 0)
            throw new TextoNumeroException("Valor muy grande para un número entero: " + textoAConvertir);

        return valorEntero;
    }

    public long longValue(String textoAConvertir) throws TextoNumeroException {
        return longValue(textoAConvertir, false);
    }

    private long longValue(String textoAConvertir, boolean esParteDecimal) throws TextoNumeroException {
        BigInteger bigInteger = convierteTextoANumero(textoAConvertir, esParteDecimal);

        long valorLargo = bigInteger.longValue();
        if(bigInteger.compareTo(new BigInteger(Long.toString(valorLargo))) != 0)
            throw new TextoNumeroException("Valor muy grande para un número entero largo: " + textoAConvertir);

        return valorLargo;
    }

    public BigInteger bigIntegerValue(String textoAConvertir) throws TextoNumeroException {
        return bigIntegerValue(textoAConvertir, false);
    }

    private BigInteger bigIntegerValue(String textoAConvertir, boolean esParteDecimal) throws TextoNumeroException {
        return convierteTextoANumero(textoAConvertir, esParteDecimal);
    }

    public double doubleValue(String textoAConvertir) throws TextoNumeroException {
        BigDecimal valor = bigDecimalValue(textoAConvertir);

        double valorFinal = valor.doubleValue();
        if(valor.compareTo(BigDecimal.valueOf(valorFinal)) != 0)
            throw new TextoNumeroException("Valor muy grande para un número doble: " + textoAConvertir);

        return valorFinal;
    }

    public float floatValue(String textoAConvertir) throws TextoNumeroException {
        BigDecimal valor = bigDecimalValue(textoAConvertir);

        float valorFinal = valor.floatValue();
        if(valor.compareTo(BigDecimal.valueOf(valorFinal)) != 0)
            throw new TextoNumeroException("Valor muy grande para un número doble");

        return valorFinal;
    }

    public BigDecimal bigDecimalValue(String textoAConvertir) throws TextoNumeroException {
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
        
        for(String separaEnterosDecimales : separadoresEnterosDecimales) {
            separadorDecimalesTotal.add(quitaTildes(separaEnterosDecimales));
            separadorDecimales.add(quitaTildes(separaEnterosDecimales));
        }

        if(esMoneda) {
            separadorDecimales.add(quitaTildes(tipoMonedaEnterosPlural));
            separadorDecimales.add(quitaTildes(tipoMonedaEnteros));
        }
        
        int cuentaCerosIzquierdaDecimal = 0;
        int dondeSeparadorDecimales = -1;
        String[] enterosDecimales = new String[2];
        textoAConvertir = quitaTildes(textoAConvertir.toLowerCase());
        
        //Evitar que se introduzcan valores con el separador de decimales "y" como: trescientos euros y un céntimo
        //Cambiar a: trescientos euros con un céntimo
        if(esMoneda) {
            try {
                ArrayList<String> listaTextoAConvertir = new ArrayList<>(Arrays.asList(textoAConvertir.split("\\s+")));
                int dondeMonedaEnteros = Math.max(listaTextoAConvertir.indexOf(tipoMonedaEnteros), listaTextoAConvertir.indexOf(tipoMonedaEnterosPlural));
                if(listaTextoAConvertir.get(dondeMonedaEnteros + 1).equals("y"))
                    listaTextoAConvertir.set(dondeMonedaEnteros + 1, "con");
                textoAConvertir = String.join(" ", listaTextoAConvertir);
            } catch(IndexOutOfBoundsException e) {}        
        }

        for(String separador : separadorDecimalesTotal) {
            if(!separadorDecimales.contains(separador)) {
                int donde = textoAConvertir.indexOf(" ".concat(separador).concat(" "));
                if(donde != -1)
                    textoAConvertir = textoAConvertir.replace(separador.concat(" "), "");
                else if(separador.length() == 1)
                    textoAConvertir = textoAConvertir.replace(separador, " ".concat(separador).concat(" "));
                
            } else if(separador.length() == 1) {
                textoAConvertir = textoAConvertir.replace(separador, " ".concat(separador).concat(" "));
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
        
        if(esMoneda && dondeSeparadorDecimales == -1 && autodetectaDecimalesMoneda) 
            dondeSeparadorDecimales = detectaDecimalesMoneda(textoAConvertir);

        if(dondeSeparadorDecimales != -1) {
            enterosDecimales[0] = textoAConvertir.substring(0, dondeSeparadorDecimales);
            enterosDecimales[1] = textoAConvertir.substring(dondeSeparadorDecimales, textoAConvertir.length());

            //Contar los ceros por la izquierda, ya que va a meterse este valor en un BigInteger van a desaparecer
            cuentaCerosIzquierdaDecimal = cuantosCerosIzquierdaDecimal(enterosDecimales[1]);
        } else {                        
            if(esMoneda && !textoAConvertir.contains(quitaTildes(tipoMonedaEnteros)) && !textoAConvertir.contains(quitaTildes(tipoMonedaEnterosPlural)) && 
                    (textoAConvertir.contains(quitaTildes(tipoMonedaCentimos)) || textoAConvertir.contains(quitaTildes(tipoMonedaCentimosPlural)))) {
                
                enterosDecimales[0] = "0";
                enterosDecimales[1] = textoAConvertir;
            } else {
                enterosDecimales[0] = textoAConvertir;
                enterosDecimales[1] = "0";
            }
        }

        BigInteger[] resultado = new BigInteger[2];
        for(int a = 0; a < enterosDecimales.length; a++) 
            resultado[a] = bigIntegerValue(enterosDecimales[a], a == 1).abs();

        BigInteger valorDecimal = BigInteger.ONE;
        for(int a = 0; a < (esMoneda ? Math.max(redondeoDecimales, resultado[1].toString().length()) : resultado[1].toString().length() + cuentaCerosIzquierdaDecimal); a++)
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

    public String toString(int numeroAConvertir) throws TextoNumeroException {
        if(digitosUnitarios)
            return dameTextoDigitosUnitarios(BigInteger.valueOf(numeroAConvertir), numeroAConvertir < 0);
        else 
            return getMoneda(toStringPriv(BigInteger.valueOf(numeroAConvertir)));
    }

    public String toString(long numeroAConvertir) throws TextoNumeroException {
        if(digitosUnitarios)
            return dameTextoDigitosUnitarios(BigInteger.valueOf(numeroAConvertir), numeroAConvertir < 0);
        else
            return getMoneda(toStringPriv(BigInteger.valueOf(numeroAConvertir)));
    }
    
    public String toString(BigInteger numeroAConvertir) throws TextoNumeroException {
        compruebaValorMaximo(numeroAConvertir);
        
        if(digitosUnitarios)
            return dameTextoDigitosUnitarios(numeroAConvertir, numeroAConvertir.compareTo(BigInteger.ZERO) == -1);
        else
            return getMoneda(toStringPriv(numeroAConvertir));
    }

    public String toString(float numeroAConvertir) throws TextoNumeroException {
        if(digitosUnitarios)
            return dameTextoDigitosUnitarios(BigDecimal.valueOf(numeroAConvertir));
        else
            return toString(BigDecimal.valueOf(numeroAConvertir));
    }

    public String toString(double numeroAConvertir) throws TextoNumeroException {
        if(digitosUnitarios) 
            return dameTextoDigitosUnitarios(BigDecimal.valueOf(numeroAConvertir));
        else 
            return toString(BigDecimal.valueOf(numeroAConvertir));
    }
    
    public String toString(BigDecimal numeroAConvertir) throws TextoNumeroException {       
        String[] enteroDecimal = numeroAConvertir.toPlainString().split("\\.");        
        compruebaValorMaximo(new BigInteger(enteroDecimal[0]));
        
        if(digitosUnitarios)
            return dameTextoDigitosUnitarios(numeroAConvertir);
        else {
            if(enteroDecimal.length < 2)            
                return getMoneda(toStringPriv(new BigInteger(numeroAConvertir.toPlainString())));
            else {
                if(redondeoDecimales != 0) {
                    if(enteroDecimal[1] == null || enteroDecimal[1].equals(""))
                        enteroDecimal[1] = "0";

                    numeroAConvertir = numeroAConvertir.setScale(redondeoDecimales > enteroDecimal[1].length() ? enteroDecimal[1].length() : redondeoDecimales, RoundingMode.HALF_UP);
                    enteroDecimal = numeroAConvertir.toPlainString().split("\\.");
                }

                //mirar si la parte decimal tiene ceros a su izquierda
                int cuentaCerosIzquierdaDecimal = cuantosCerosIzquierdaDecimal(enteroDecimal[1]);

                String[] valor = numeroAConvertir.toPlainString().split("\\.");

                //Si es moneda y la parte decimal es un solo dígito multiplicarlo por 10
                if(esMoneda && valor[1].length() < redondeoDecimales) 
                    valor[1] = Integer.toString(Integer.parseInt(enteroDecimal[1]) * (Integer.parseInt(dameCeros(redondeoDecimales - valor[1].length()))));

                for(int a = 0; a < valor.length; a++) 
                    valor[a] = toStringPriv(new BigInteger(valor[a])).trim();

                BigInteger bigIntegerEurosParteEntera = convierteTextoANumero(valor[0], false);
                String[] monedas = getMoneda(valor[0], valor[1]);
                String monedaEnteros = monedas[0];
                String monedaCentimos = monedas[1];

                if(bigIntegerEurosParteEntera.compareTo(BigInteger.ZERO) == 0 && numeroAConvertir.compareTo(BigDecimal.ZERO) == -1)
                    valor[0] = "menos ".concat(valor[0]);

                if(cuentaCerosIzquierdaDecimal > 0 && valor[1].trim().equals("cero"))
                    cuentaCerosIzquierdaDecimal = 0;                

                try {
                    return valor[0].concat(monedaEnteros).concat(valor[1].equals("cero") ? "" : " ".concat(separadorEnterosDecimales).concat(" ").concat(dameCeros(cuentaCerosIzquierdaDecimal, false, esMoneda).concat(valor[1]).concat(monedaCentimos)));
                } catch(IndexOutOfBoundsException e) {
                    return valor[0].concat(monedaEnteros);
                }
            }        
        }
    }
    
    public void setMonedaFemenino(boolean monedaFemenino) {
        this.monedaFemenino = monedaFemenino;
    }
    
    private void compruebaValorMaximo(BigInteger numeroAConvertir) throws TextoNumeroException {       
        if(numeroAConvertir.abs().toString().length() > 84) {            
            String numeroMaximo = dameCeros(83).replace("1", "0").replace("0", "9");
            throw new TextoNumeroException("Máximo permitido " + numeroMaximo.length() + " dígitos: " + numeroMaximo + " (" + toString(new BigInteger(numeroMaximo)));
        } 
    }
    
    private BigInteger convierteTextoANumero(String textoAConvertir, boolean esParteDecimal) throws TextoNumeroException {
        BigInteger numeroFinal = new BigInteger("0");
        String textoAConvertirOriginal = textoAConvertir;        
        BigInteger valorASumar = BigInteger.ZERO;
        BigInteger valorASumarParcial = BigInteger.ZERO;
        boolean esNegativo = false;
        boolean sonDigitosUnitarios = true;        
        int largoTextoActual = 0;
        boolean ultimoRangoDiezVeintinueve = false;
        ArrayList<Integer> numeroDigitos = new ArrayList<>();
        String digitosUnitariosStr = "";
        ArrayList<String> texto = getTexto(true);
        ArrayList<BigInteger> numero = getNumero(true);
        int dondeEmpiezaRuptura = texto.indexOf("mil");
        int dondeAntes = texto.size();
        int dondeMillon = numero.indexOf(BigInteger.valueOf(1000000));        

        textoAConvertir = quitaTildes(textoAConvertir.trim().toLowerCase(), true);

        try {
            numeroFinal = new BigInteger(textoAConvertir);
            return numeroFinal;
        } catch(NumberFormatException e) {}
        
        for(int a = 0; a < texto.size(); a++)
            texto.set(a, quitaTildes(texto.get(a), true));
        
        textoAConvertir = procesaTexto(textoAConvertir, true);
        
        String[] textos = quitaTildes(textoAConvertir).split("\\s+");

        textoAConvertir = "";
        ArrayList<String> textosAEliminar = new ArrayList<>();
        for(int a = 0; a < textos.length; a++) {
            if(!texto.contains(textos[a])) {                
                try {
                    long numeroATexto = Long.parseLong(textos[a]);
                    String textoReemplazar = toString(numeroATexto);
                    for (String eliminaTexto1 : eliminaTexto) 
                        textoReemplazar = textoReemplazar.replace(" " + eliminaTexto1 + " ", " ");
                    textos[a] = textoReemplazar;
                    textoAConvertir = "";
                    for (String texto1 : textos) {
                        textoAConvertir = textoAConvertir.concat(texto1).concat(" ");
                    }
                    textos = quitaTildes(textoAConvertir).trim().split("\\s+");
                    textoAConvertir = "";
                    a = -1;
                    
                } catch(NumberFormatException e) {
                    //buscar en monedas para quitarlos textos de monedas
                    if(textos[a].equals(quitaTildes(tipoMonedaEnteros.toLowerCase())) || textos[a].equals(quitaTildes(tipoMonedaEnterosPlural.toLowerCase())) || textos[a].equals(quitaTildes(tipoMonedaCentimos).toLowerCase()) || textos[a].equals(quitaTildes(tipoMonedaCentimosPlural.toLowerCase())))
                        textosAEliminar.add(textos[a]);
                    else
                        throw new TextoNumeroException("Palabra no reconocida: " + textos[a] + ". Texto completo: " + textoAConvertirOriginal);
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
                    throw new TextoNumeroException("No se permiten combinaciones de millones como: millón de millones, millón de billones, billón de trillones, cuatrillón de billones, etc...");
            }
        }

        for(int a = 0; a < textos.length; a++) {
            if(textos[a].equals("menos")) {
                if(esParteDecimal)
                    throw new TextoNumeroException("Palabra 'menos' no permitida en la parte decimal: " + textoAConvertirOriginal);
                else {
                    if(a != 0)
                        throw new TextoNumeroException("Palabra 'menos' no está en el principio de la frase: " + textoAConvertirOriginal);
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

                    if(numeroDigitos.contains(valorAhora.toString().length()) || (ultimoRangoDiezVeintinueve && donde >= texto.indexOf("cero") && donde <= texto.indexOf("nueve")))
                        throw new TextoNumeroException("Número en texto mal formado: " + textoAConvertirOriginal);
                    numeroDigitos.add(valorAhora.toString().length());                    
                    ultimoRangoDiezVeintinueve = donde >= texto.indexOf("diez") && donde <= texto.indexOf("veintinueve");

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
                        } else {
                            throw new TextoNumeroException("Número en texto mal formado: " + textoAConvertirOriginal);
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

    private String toStringPriv(BigInteger numeroAConvertir) {
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
            }
            
            if((contaTotal % 3 == 0 && a != textoAConvertir.length() - 1) || a == 0) {
                String tmp = convierteTresDigitosATexto(textoTmp);
                
                if(contaTotal <= 6 && esMoneda && monedaFemenino && !tmp.equals("")) {
                    String[] monedasPosibleFemenino = new String[]{"uno", "doscientos", "trescientos", "cuatrocientos", "quinientos", "seiscientos", "setecientos", "ochocientos", "novecientos"};
                    for (String monedaPosibleFemenino : monedasPosibleFemenino) 
                        tmp = tmp.replace(monedaPosibleFemenino, getFemenino(monedaPosibleFemenino));
                }

                if(esMiles) {
                    //mirar si el texto de millones ya lo tiene el textoFinal y si lo tiene no ponerlo
                    if(textoFinal.contains(quitaTildes(textoMiles, true)))
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
                            textoMiles = quitaTildes(textoMiles, true).concat("es");
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
        String[] tmpS = tmp.trim().split("\\s+");
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
            if(tmpS[tmpS.length - 1].endsWith(quitaTildes(texto.get(dondeVeintiuno + 1), true))) {
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

    private String dameTextoDigitosUnitarios(BigInteger entrada, boolean esNegativo) throws TextoNumeroException {
        ArrayList<String> texto = getTexto(true);
        ArrayList<BigInteger> numero = getNumero(true);
        String textos = entrada.abs().toString();
        String textoFinal = "";

        for(int a = 0; a < textos.length(); a++) 
            textoFinal = textoFinal.concat(texto.get(numero.indexOf(new BigInteger(String.valueOf(textos.charAt(a)))))).concat(" ");

        return (esNegativo ? "menos " : "").concat(textoFinal.trim());
    }

    private String dameTextoDigitosUnitarios(BigDecimal entrada) throws TextoNumeroException {        
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
            textoFinal = textoFinal.concat(a == 1 ? (esMoneda ? tipoMonedaEnterosPlural : "").concat(" ".concat(separadorEnterosDecimales).concat(" ")) : "").concat((dameCeros(cuentaCerosIzquierdaDecimal, false, esMoneda)) + dameTextoDigitosUnitarios(bigInteger, esNegativo)).concat(" ").concat(a == 1 && esMoneda ? tipoMonedaCentimosPlural : "");
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
    
    private String getMoneda(String valorEntero) throws TextoNumeroException {
        String[] monedas = getMoneda(valorEntero, "0");
        return valorEntero.concat(" ").concat(monedas[0]).trim().replaceAll("\\s+", " ");
    }
    
    private String[] getMoneda(String valorEntero, String valorDecimal) throws TextoNumeroException {
        String monedaEnteros = "";
        String monedaCentimos = "";
        
        if(esMoneda) {
            BigInteger bigIntegerEurosParteEntera = convierteTextoANumero(valorEntero, false);
            BigInteger bigIntegerEurosParteDecimal = BigInteger.ONE;

            try {
                bigIntegerEurosParteDecimal = convierteTextoANumero(valorDecimal, true);
            } catch(IndexOutOfBoundsException e) {}
        
            String monedaTmp = " ";

            //si acaba en millones y trillones o de ahí hacia adelante poner la preposición "de" delante de la moneda
            String[] valoresTmp = valorEntero.split("\\s+");
            ArrayList<String> textoTmp = getTexto(true);
            ArrayList<BigInteger> numeroTmp = getNumero(true);
            int donde = textoTmp.indexOf(valoresTmp[valoresTmp.length - 1]);
            if(donde >= numeroTmp.indexOf(BigInteger.valueOf(1000000)))
                monedaTmp = " de ";

            monedaEnteros = monedaTmp.concat(bigIntegerEurosParteEntera.compareTo(BigInteger.ONE) == 0 ? tipoMonedaEnteros : tipoMonedaEnterosPlural);
            monedaCentimos = " ".concat(bigIntegerEurosParteDecimal.compareTo(BigInteger.ONE) == 0 ? tipoMonedaCentimos : tipoMonedaCentimosPlural);
        }
        
        return new String[]{monedaEnteros, monedaCentimos};
    }
    
    private String getFemenino(String texto) {
        if(texto.endsWith("os"))
            return texto.substring(0, texto.length() - 2).concat("as");
        else
            return texto.substring(0, texto.length() - 1).concat("a");
    }
    
    private String procesaTexto(String textoAConvertir, boolean eliminarTexto) {
        for (String juntarCiento : juntarCientos) {
            if (textoAConvertir.contains(juntarCiento)) 
                textoAConvertir = textoAConvertir.replace(juntarCiento, juntarCiento.replace(" ", ""));
        }

        if(eliminarTexto)
            for (String eliminaTexto1 : eliminaTexto) 
                textoAConvertir = textoAConvertir.replace(" " + eliminaTexto1 + " ", " ");
        
        return textoAConvertir;
    }
    
    private int detectaDecimalesMoneda(String textoDetectar) {
        ArrayList<String> texto = getTexto(false);
        ArrayList<BigInteger> numero = getNumero(false);
        BigInteger resultado = BigInteger.ZERO;
        int numDigitosAntes = 0;
        int dondeSeparadorDecimalesTmp = -1;
        int dondeSeparadorDecimales = -1;

        for(int a = 0; a < texto.size(); a++)
            texto.set(a, quitaTildes(texto.get(a), true));
        
        textoDetectar = procesaTexto(textoDetectar, false);

        ArrayList<String> textos = new ArrayList<>(Arrays.asList(textoDetectar.split("\\s+")));                
        
        for(int a = textos.size() - 1; a >= 0; a--) {            
            int donde = texto.indexOf(textos.get(a));
            if(donde != -1) {                
                if(donde >= texto.indexOf("ciento")) {
                    break;
                } else if(donde > 0) {
                    BigInteger valorAhora = numero.get(donde);
                    if(valorAhora.toString().length() <= numDigitosAntes || donde == texto.indexOf("cien") || (a < textos.size() - 1 && donde >= texto.indexOf("diez") && donde <= texto.indexOf("veintinueve"))) {
                        dondeSeparadorDecimales = textoDetectar.length() - dondeSeparadorDecimalesTmp;
                        break;
                    }                    

                    resultado = resultado.add(valorAhora);                
                    numDigitosAntes = valorAhora.toString().length();
                }
            }
            
            dondeSeparadorDecimalesTmp += textos.get(a).length() + 1;
        }
        
        return dondeSeparadorDecimales;
    }    
    
}
