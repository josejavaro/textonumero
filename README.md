# ¿Que es TextoNumero?
Es una librería escrita en Java que convierte de texto a número y de número a texto alfabético en español, con o sin decimales.
Posibilidad de añadir nombres de monedas al número convertido a texto. También tiene varias opciones que se pueden consultar en el apartado "Características y Uso" más abajo.
# ¿Donde se puede usar?
Se puede usar en aplicaciones de escritorio/servidores Java y en proyectos de Android.
En Android se puede usar en añadido al reconocimiento de voz de la clase android.speech.RecognizerIntent para convertir a número el texto recibido por esta clase y es válido para introducir precios o stock de artículos entre otras posibilidades de introducción de números mediante voz.
# Clonar & Descargar
- Puedes clonar el repositorio Git como proyecto Maven.
- También puedes añadir el repositorio a tu proyecto Maven. En tu pom.xml añade:

        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
        <dependency>
            <groupId>com.github.josejavaro</groupId>
            <artifactId>textonumero</artifactId>
            <version>1.2.9</version>
        </dependency>
- Programas de escritorio/servidores: Descarga la librería JAR desde aquí (JDK 8.0_201) (También se puede descargar para proyectos de Android, aunque en este caso deberás de añadir a tus dependencias el archivo jar localizado en una carpeta dentro de tu PC): https://github.com/josejavaro/textonumero/releases/download/1.2.9/textonumero-1.2.9.jar
- En proyectos de Android añade la depencencia en el Gradle (App):
    
        dependencies {
            implementation 'com.github.josejavaro:textonumero:1.2.9'
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
    double d = textoNumero.doubleValue("cuarenta y cinco con ochocientos cuarenta y ocho");
    System.out.println(d);
    //Salida: 45.85

    //Convertir de número a texto asignando la moneda por defecto en España (euro)
    textoNumero = new TextoNumero();
    textoNumero.setMoneda("ES");
    String texto = textoNumero.toString(239273947.45);
    System.out.println(texto);
    //Salida: doscientos treinta y nueve millones doscientos setenta y tres mil novecientos cuarenta y siete euros con cuarenta y cinco céntimos
    
    //Convertir de número a texto asignando la moneda por defecto en Argentina (pesos argentinos)
    textoNumero = new TextoNumero();
    textoNumero.setMoneda("AR");
    String texto = textoNumero.toString(672746.91);
    System.out.println(texto);
    //Salida: seiscientos setenta y dos mil setecientos cuarenta y seis pesos argentinos con noventa y un centavos
    
    //Obtener los países soportados
    ArrayList<String> paises = textoNumero.getPaises();
    paises.forEach((pais) -> {
        System.out.println(pais);
    });
    
    //Convertir de número a texto asignando una moneda de otro país que no esté soportada. P.ej: Inglaterra
    textoNumero = new TextoNumero();
    textoNumero.setMoneda("libra", "penique");
    textoNumero.setMonedaFemenino(true);
    String texto = textoNumero.toString(781.13);
    System.out.println(texto);
    //Salida: setecientas ochenta y una libras con trece peniques
    
    //Convertir a dígitos unitarios desde texto
    textoNumero = new TextoNumero();
    textoNumero.setDigitosUnitarios(true);
    int numero = textoNumero.intValue("seis cinco siete ocho");
    System.out.println(numero);
    //Salida: 6578
    
    //Convertir a texto (dígitos unitarios) desde número
    textoNumero = new TextoNumero();
    textoNumero.setDigitosUnitarios(true);
    String texto = textoNumero.toString(78373);
    System.out.println(texto);
    //Salida: siete ocho tres siete tres
    
    //Convertir a texto de dígitos unitarios desde número grande
    textoNumero = new TextoNumero();
    textoNumero.setDigitosUnitarios(true);
    String texto = textoNumero.toString(new BigInteger("4082000005000000439"));
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
    
    //Añadir un separador de la parte entera con la decimal
    textoNumero = new TextoNumero();    
    textoNumero.addSeparadorEnterosDecimales("aaaaaaaaaaaaaaaa");
    double valor = textoNumero.doubleValue("cuatro mil aaaaaaaaaaaaaaaa tres");
    System.out.println(valor);
    //Salida: 4000.3
    
    //Se pueden añadir varios separadores y el que queda como actual es el último añadido
    textoNumero = new TextoNumero();
    textoNumero.setMoneda("ES");
    textoNumero.addSeparadorEnterosDecimales("aaa");
    textoNumero.addSeparadorEnterosDecimales("bbb");
    textoNumero.addSeparadorEnterosDecimales("ccc");
    String texto = textoNumero.toString(12.78);
    System.out.println(texto);
    //Salida: doce euros ccc setenta y ocho céntimos
    
    double valor = textoNumero.doubleValue("cuatro bbb seis");
    System.out.println(valor);
    //Salida: 4.06
    
    //Reconocimiento de monedas fraccionadas sin necesidad de decir toda la frase
    textoNumero = new TextoNumero();
    textoNumero.setMoneda("ES");
    double numero = textoNumero.doubleValue("cinco céntimos");
    System.out.println(numero);
    //Salida: 0.05
    
    //Convertir texto a número con salida String y con el símbolo de moneda
    //Por ejemplo quiero convertir el texto "cuatro mil doce con ocho" a número y con el símbolo de moneda de España
    textoNumero = new TextoNumero();
    textoNumero.setMoneda("ES");
    String numero = textoNumero.doubleValueAsString("cuatro mil doce con ocho");
    System.out.println(numero);
    //Salida: 4012.08 €
    
    //Convertir texto a número con salida String y con símbolo de moneda de un país no soportado, por ejemplo de Inglaterra
    textoNumero = new TextoNumero();
    textoNumero.setMonedaSimbolo("£");
    textoNumero.setRedondeo(2);            
    String numero = textoNumero.doubleValueAsString("cuatro mil doce con ocho");
    System.out.println(numero);
    //Salida: 4012.08 £    
    
    //Convertir texto a número con salida String con formato de moneda pero sin el símbolo de la moneda
    //Se puede usar en métodos Object.setText(String s) en componentes de UI como JTextField o EditText que contengan campos de precios
    //Con esto evitamos que aparezca el símbolo de la moneda al final de número y así puede ser editado más fácilmente por el usuario posteriormente
    textoNumero = new TextoNumero();
    textoNumero.setMoneda("ES");
    textoNumero.anularMonedaSimbolo();
    String numero = textoNumero.doubleValueAsString("ciento veinticinco mil novecientos con nueve");
    //EditText.setText(numero);
    System.out.println(numero);
    //Salida: 125900.09
    
    //Se pueden añadir palabras no determinantes que son las palabras que no tienen un contenido relevante en la frase
    //Estas palabras no se tienen en cuenta en la conversión de texto a número
    //En AI se conocen también como "Stop Words"
    //P. ej: se puede añadir la palabra no determinante 'más' en este caso:
    textoNumero = new TextoNumero();
    textoNumero.setMoneda("ES");
    textoNumero.addPalabraNoDeterminante("más");            
    String valor = textoNumero.doubleValueAsString("veinte millones más ocho");
    System.out.println(valor);
    //Salida: 20000008.00 €
    //Si no se especificase esta palabra no determinante daría un error de TextoNumeroException    
    
    //Por defecto cuando se usa moneda TextoNumero detecta la posición decimal en cantidades como:
    //veintiocho doce (se convertiría en veintiocho con doce)
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
    
# Licencia
Copyright (c) 2023-presente <a href='https://github.com/josejavaro'>Jose Java</a><br>
Licencia <a href='https://github.com/josejavaro/textonumero/blob/main/LICENSE'>MIT</a>
