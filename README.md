# ¿Que es TextoNumero?
Es una librería que convierte de texto a número y de número a texto alfabético en español, con o sin decimales.
Posibilidad de añadir nombres de monedas al número convertido a texto. También tiene varias opciones que se pueden consultar en el apartado "Características y Uso" más abajo.
# ¿Donde se puede usar?
Está programado en Java y se puede usar en aplicaciones de escritorio/servidores Java y en proyectos de Android en Java o Kotlin.
# Clonar & Descargar
- Puedes clonar el repositorio Git como proyecto Maven.
- Programas de escritorio/servidores: Descarga la librería JAR desde aquí (JDK 8.0_201): https://github.com/josejavaro/textonumero/releases/download/1.1.0/textonumero-1.1.0.jar
- En proyectos de Android añade la depencencia en el Gradle (App):
    
        dependencies {
            implementation 'com.github.josejavaro:textonumero:1.1.0'
        }
    
    Puede ser necesario añadir también el repositorio a GitHub(JitPack) en el Gradle (Build):
    
        repositories {
            maven { url 'https://jitpack.io' }            
        }
# Características y Uso
    TextoNumero textoNumero = new TextoNumero();

    //Convertir de número a texto
    String texto = textoNumero.toString(45.846);
    System.out.println(texto);
    //Salida: cuarenta y cinco con ochocientos cuarenta y seis
            
    //Convertir de texto a número
    textoNumero = new TextoNumero();
    double d = textoNumero.doubleValue("cuarenta y cinco con ochocientos cuarenta y ocho");
    System.out.println(d);
    //Salida: 45.848        
    
    //Convertir de texto a número con redondeo a 2 decimales
    textoNumero = new TextoNumero();
    textoNumero.setRedondeo(2);
    d = textoNumero.doubleValue("cuarenta y cinco con ochocientos cuarenta y ocho");
    System.out.println(d);
    //Salida: 45.85

    //Convertir de número a texto asignando la moneda por defecto en España (euro)
    textoNumero = new TextoNumero();
    textoNumero.setMoneda("ES");
    texto = textoNumero.toString(239273947.45);
    System.out.println(texto);
    //Salida: doscientos treinta y nueve millones doscientos setenta y tres mil novecientos cuarenta y siete euros con cuarenta y cinco céntimos
    
    //Convertir de número a texto asignando la moneda por defecto en Argentina (pesos argentinos)
    textoNumero = new TextoNumero();
    textoNumero.setMoneda("AR");
    texto = textoNumero.toString(672746.91);
    System.out.println(texto);
    //Salida: seiscientos setenta y dos mil setecientos cuarenta y seis pesos argentinos con noventa y un centavos
    
    //Obtener los países soportados
    ArrayList<String> paises = textoNumero.getPaises();
    paises.forEach((pais) -> {
        System.out.println(pais);
    });
    
    //Convertir de número a texto asignando una moneda de otro país que no esté soportada. P.ej: Gran Bretaña.
    textoNumero = new TextoNumero();
    textoNumero.setMoneda("libra", "penique");
    textoNumero.setMonedaFemenino(true);
    texto = textoNumero.toString(781.13);
    System.out.println(texto);
    //Salida: setecientas ochenta y un libras con trece peniques
    
    //Convertir a dígitos unitarios desde texto
    textoNumero = new TextoNumero();
    textoNumero.setDigitosUnitarios(true);
    int numero = textoNumero.intValue("seis cinco siete ocho");
    System.out.println(numero);
    //Salida: 6578
    
    //Convertir a texto (dígitos unitarios) desde número
    textoNumero = new TextoNumero();
    textoNumero.setDigitosUnitarios(true);
    texto = textoNumero.toString(78373);
    System.out.println(texto);
    //Salida: siete ocho tres siete tres
    
    //Convertir a texto de dígitos unitarios desde número grande
    textoNumero = new TextoNumero();
    textoNumero.setDigitosUnitarios(true);
    texto = textoNumero.toString(new BigInteger("4082000005000000439"));
    System.out.println(texto);
    //Salida: cuatro cero ocho dos cero cero cero cero cero cinco cero cero cero cero cero cero cuatro tres nueve
    
    //Números grandes -> texto a número
    textoNumero = new TextoNumero();    
    BigInteger bigInteger = textoNumero.bigIntegerValue("cuatro trillones ochenta y dos mil billones cinco mil millones cuatrocientos treinta y nueve");
    System.out.println(bigInteger.toString());
    //Salida: 4082000005000000439   
    
    //Texto con números
    textoNumero = new TextoNumero();    
    double valor = textoNumero.doubleValue("cuatro mil 201");
    System.out.println(valor);
    //Salida: 4201.0
    
    //Especificar un separador de la parte entera con la decimal
    textoNumero = new TextoNumero();    
    textoNumero.setSeparadorEnterosDecimales("aaaaaaaaaaaaaaaa");
    double valor = textoNumero.doubleValue("cuatro mil aaaaaaaaaaaaaaaa tres");
    System.out.println(valor);
    //Salida: 4000.3
    
    //Por defecto cuando se usa moneda TextoNumero detecta la posición decimal en cantidades como: veintiocho doce (se convertiría en veintiocho con doce)
    textoNumero = new TextoNumero();
    textoNumero.setMoneda("ES");
    double valor = textoNumero.doubleValue("veintiocho doce");
    System.out.println(valor);
    //Salida: 28.12
    
    //Se puede cancelar la autodetección de la posición decimal en monedas con el método setAutodetectaDecimalesMoneda
    textoNumero = new TextoNumero();
    textoNumero.setMoneda("ES");
    textoNumero.setAutodetectaDecimalesMoneda(false);
    double valor = textoNumero.doubleValue("veintiocho doce");
    System.out.println(valor);
    //Aquí daría error de TextoNumeroException -> Número en texto mal formado: veintiocho doce
    
    
    //Capturar error
    try {
        textoNumero = new TextoNumero();
        String numero = "9000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
        String texto = textoNumero.toString(new BigDecimal(numero));            
        System.out.println(texto);
    } catch(TextoNumeroException ex) {
        System.out.println(ex.getMessage());
    }
