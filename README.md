# textonumero
Convierte de texto a número y de número a texto, con o sin decimales.
Posibilidad de añadir nombres de monedas al número convertido a texto.
# Programado en
Java para programas de escritorio/servidores o proyectos de Android.
# Descarga
Clona el proyecto como Maven a tu PC.
- Descarga la librería JAR desde aquí.
- Descarga  
# Formas de uso
TextoNumero textoNumero = new TextoNumero();
try {

    //Convertir de número a texto
    String texto = textoNumero.toString(45.846);
    System.out.println(texto);
    //Salida: cuarenta y cinco con ochocientos cuarenta y seis
            
    //Convertir de texto a número
    double d = textoNumero.doubleValue("cuarenta y cinco con ochocientos cuarenta y ocho");
    System.out.println(d);
    //Salida: 45.848        
    
    //Convertir de texto a número con redondeo a 2 decimales
    textoNumero.setRedondeo(2);
    d = textoNumero.doubleValue("cuarenta y cinco con ochocientos cuarenta y ocho");
    System.out.println(d);
    //Salida: 45.85

    //Convertir de número a texto asignando la moneda por defecto en España (euro)
    textoNumero.setMoneda("ES");
    texto = textoNumero.toString(239273947.45);
    System.out.println(texto);
    //Salida: doscientos treinta y nueve millones doscientos setenta y tres mil novecientos cuarenta y siete euros con cuarenta y cinco céntimos
    
    //Convertir de número a texto asignando la moneda por defecto en Argentina (pesos argentinos)
    textoNumero.setMoneda("AR");
    texto = textoNumero.toString(672746.91);
    System.out.println(texto);
    //Salida: seiscientos setenta y dos mil setecientos cuarenta y seis pesos argentinos con noventa y un centavos
    
    //Convertir de número a texto asignando una moneda de otro país que no esté soportada
    textoNumero.setMoneda("libra", "penique");
    texto = textoNumero.toString(781.13);
    System.out.println(texto);
    //Salida: setecientos ochenta y un libras con trece peniques
    
    //Convertir desde dígitos unitarios
    textoNumero.setDigitosUnitarios(true);
    int numero = textoNumero.intValue("seis cinco siete ocho");
    System.out.println(numero);
    //Salida: 6578
    
    //Convertir a dígitos unitarios
    textoNumero.setDigitosUnitarios(true);
    texto = textoNumero.toString(78373);
    System.out.println(texto);
    //Salida: siete ocho tres siete tres
    
    //Convertir a texto de dígitos unitarios desde número grande
    textoNumero.setDigitosUnitarios(true);
    texto = textoNumero.toString(new BigInteger("4082000005000000439"));
    System.out.println(texto);
    //Salida: cuatro cero ocho dos cero cero cero cero cero cinco cero cero cero cero cero cero cuatro tres nueve
    
    //Números grandes -> texto a número
    textoNumero.setDigitosUnitarios(false);
    BigInteger bigInteger = textoNumero.bigIntegerValue("cuatro trillones ochenta y dos mil billones cinco mil millones cuatrocientos treinta y nueve");
    System.out.println(bigInteger.toString());
    //Salida: 4082000005000000439
    
    
} catch (Exception ex) {
    System.out.println(ex.getMessage());
}
