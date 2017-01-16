/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spsoundex;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Juan Gonzalo Quiroz Cadavid.
 */

public class Lista {

    /**
     * Declaracion del constructor del soundex.
     * implementado para obtener el codigo
     * correspondiente a cada Nodo.
     */
    private static Soundex soundex = new Soundex();
    
    /**
     * TablaHash, sera el algoritmos de ordenamiento
     * empleado para guardar los nodos, comenzando por
     * su primera letra.
     * 
     *      Letras -> Header.charAt(0)-97
     *      
     *      & -> 26
     *   
     *      ' -> 27
     *      
     *      Numeros -> Header.charAt(0)-21
     */
    private static Nodo tablaHash[] = new Nodo[37];
    
    /**
     * Arreglo de apuntadores.
     * 
     * Cada uno apunta al ultimo nodo puesto en
     * cada poscicion del arreglo tablaHash
     * con el fin de hacer mas rapido el algoritmo.
     */
    static Nodo nodoApuntador [] = new Nodo[37];
    
    /**
     * Constrctor por default.
     * 
     * invoca el metodo inicio()
     * para generar la tabla hash
     * leer los nodos y ubicarlos
     * en su respectiva poscicion.
     */
    Lista(){
        try {
            inicio();
        } catch (IOException ex) {
            Logger.getLogger(Lista.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

    
    /**
     *   Este metodo se encarga de Listar los nodos
     *   En una tabla hash, ordenada de la forma
     *   Fig 1(Documentos)
     *
     *   Lee de un documento los valores a ingresar.
     *   Identifica cual es a poscicon en la tabla has
     *   a la cual va a acceder.
     * 
     *   Llama al metodo crearNodo que sera el encargado
     *   de poner el nodo en su lugar y hacer las conexiones.
     * 
     * @throws FileNotFoundException FileNotFoundException
     * @throws IOException IOException 
     */
    public static void ListarNodos() throws FileNotFoundException, IOException {
        
        String archivo = "C:\\Users\\udeev\\Desktop/words.txt";
        System.out.println("Listando Nodos");
        
        //Creamos el Nodo antes, para asumir la recurision
        
        
        //Variables para Leer el Archivo
        String cadena;
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        
        //Estamos Leyendo ell Archivo
        
        //Creamos la tabla Hash
        CreartablaHash(tablaHash);
        
        String HeaderAntiguo = "";
        
        
        
       
        while((cadena = b.readLine())!=null) {
            if(!cadena.equals("")){
                String Header = cadena.substring(0,1);
                //System.out.println(Header);
                //Nodo nodoNuevo = new Nodo();
                
                    
                    if(Header.charAt(0) >= 'a'){crearNodo(cadena,tablaHash[Header.charAt(0)-97],Header.charAt(0)-97);}                
                    else if(Header.charAt(0) >= '&'){crearNodo(cadena,tablaHash[26],26);}
                    else if(Header.charAt(0) >= '1'){crearNodo(cadena,tablaHash[Header.charAt(0)-21],Header.charAt(0)-21); }       
                    else{crearNodo(cadena,tablaHash[27],27);}
                    HeaderAntiguo = Header;      
                
            }

            
            
        }
        b.close();
    }

    /**
     * Crea la tabla hash, obtiene la poscion mediante
     * una formula.
     * 
     * Letras: i =0 i menor que 26
     *      i+97
     *      char i+97 siendo i = 0 es "a" y haci consecutivamnete
     * 
     * Numeros: i = 28, e i es menor que 37
     *      i+21
     *      char i+21 siendo i = 28 es "1" y haci consecutivamente
     * 
     * Ampersan: i =26.
     * Coma: i = 27
     * @param Tabla Tabla 
     */
    public static void CreartablaHash(Nodo[] Tabla){
        /*
            i es la pos y a la ves la letra, ya que
            si cojemos la i (0) y lo pasamos a char
            nos daria un char rarro, pero si se sumanos
            97 comenzamos a estar en la A y haci 
            consecutivamente.
        */
        
        //Abecedario
        for (int i = 0; i < 26; i++) {
            
            char letra = (char)(i+97);
            
            Tabla[i] = new Nodo(letra+"");
            
            
        }
        
        //Casos Especiales
        
        Tabla[26] = new Nodo("&");
        Tabla[27] = new Nodo("'");
        
        //Numeros
        
        /*
            Como 1 = 49, y la tabla para numeros
            empieza desde 28, 49-28 = 21
            por ese el +21
        */
        for (int i = 28; i < Tabla.length; i++) {
            char numero = (char)(i+21);
            Tabla[i] = new Nodo(numero+"");
        }
        
        /*
        for (int i = 0; i < Tabla.length; i++) {
            System.out.println(Tabla[i].getTexto());
        }
        */
    }
    

    /**
     *   El metodo crear Nodo depende de dos metodos mas
     *   el metodo ObtenerApuntador, que me dice donde 
     *   comenzare a poner, y el metodo Actuualuizar Apuntador
     *   para que la proxima ves que entre a esta pos de hash,
     *   saber cual es el ultimo nodo sin tener que recorrer todo.
     *
     *   Este metodo Establece el nodo y sus vinculaciones, con el nodo
     *   Antes (Obtenido por ObtenerApuntador) y luego se establece
     *   como el ultimo nodo(Actualizar Nodo)
     * @param valor valor
     * @param nodoAntes nodoAntes
     * @param Apuntador Apuntador 
     */
    public static void crearNodo(String valor, Nodo nodoAntes, int Apuntador){
        
            //establece nodoAntes como el ultimo nodo puesto.
            nodoAntes = obtenerApuntador(nodoAntes, Apuntador);
        
        
       
            //Creando Nodo
            Nodo nodoNuevo = new Nodo();

            //Dando los atributos al Nodo
            nodoNuevo.setTexto(valor);
            nodoNuevo.setCode(soundex.getGode(valor));
            nodoNuevo.setVeces(0);

            //Generando Conexiones

            nodoNuevo.setNodoAtras(nodoAntes);
            nodoAntes.setNodoSiguiente(nodoNuevo);
            
            actualizarApuntador(nodoNuevo, Apuntador);

    }
    
    /**
     *   Me devuelve el ultimo apuntador
     *   establecido en esta posicion de la
     *   tabla hash, representada por Apuntador.
     * @param nodoAntes nodoAntes
     * @param Apuntador Apuntador
     * @return Nodo
     */
    public static Nodo obtenerApuntador(Nodo nodoAntes, int Apuntador){
        //Letras
        if(Apuntador <=25){
                if(nodoApuntador[Apuntador] == null){
                    nodoApuntador[Apuntador] = nodoAntes;
                    return nodoApuntador[Apuntador];
                }else{
                    
                    return nodoApuntador[Apuntador];
                
                }
            }
        //Ampersan
        else if(Apuntador == 26){
                if(nodoApuntador[Apuntador] == null){
                    nodoApuntador[Apuntador] = nodoAntes;
                    return nodoApuntador[Apuntador];
                }else{
                    return nodoApuntador[Apuntador];
                }
            
            }
        //'
        else if(Apuntador == 27){
                if(nodoApuntador[Apuntador] == null){
                    nodoApuntador[Apuntador] = nodoAntes;
                    return nodoApuntador[Apuntador];
                }else{
                    return nodoApuntador[Apuntador];
                }
            
            }
        //Numeros
        else{
                
                if(nodoApuntador[Apuntador] == null){
                    nodoApuntador[Apuntador] = nodoAntes;
                    return nodoApuntador[Apuntador];
                }else{
                    return nodoApuntador[Apuntador];
                }
            
            }
        
    }
    
    /**
     *   Actualiza el ultimo nodo guardardo
     *   en la posicion de la tabla hash,
     *   esta posicion es representada por
     *   Apuntador.
     * @param nodoAntes nodoAntes
     * @param Apuntador Apuntador 
     */
    public static void actualizarApuntador(Nodo nodoAntes, int Apuntador){
        nodoApuntador[Apuntador] = nodoAntes;
    }

    /**
     * Metodo principal
     * 
     * Este llama a ListarNodos()
     * para generar la tabal Hash con
     * todos los nodos.
     * 
     * @throws IOException IOException 
     */
    public static void inicio() throws IOException{
        ListarNodos();
    }
    
    /**
     * Main de la clase.
     * 
     * Usado en pruebas.
     * 
     * @param args args
     * @throws IOException IOException 
     */
    public static void main(String[] args) throws IOException {
            ListarNodos();
            System.out.println("Listo los nodos");
            RecorrerNodos();
            

        }
   
    /**
     * Recorre toda la tabla hash.
     * poscion por poscicion y en cada
     * poscicion llega hasta el ultimo
     * nodo.
     */
    public static void RecorrerNodos(){
  
        //Para que empeize en la primera Pos.
        int pos = 0;
        Nodo nodoActual;

                
        while(pos < 37){
            nodoActual = tablaHash[pos].nodoSiguiente;

            while(nodoActual != null){
                //System.out.println(nodoActual.texto);
                nodoActual = nodoActual.getNodoSiguiente();

            }
            
            pos++;
        }
   
    }

    public static Nodo[] getTablaHash() {
        return tablaHash;
    }

    
    /**
     * devuelve el nodo que esta en
     * la poscicion i de la tabla Hash.
     * 
     * @param i i
     * @return Nodo 
     */
    
    public static Nodo getTablaHash(int i) {
        return tablaHash[i];
    }

    public static void setTablaHash(Nodo[] tablaHash) {
        Lista.tablaHash = tablaHash;
    }

    public static Nodo[] getNodoApuntador() {
        return nodoApuntador;
    }

    public static void setNodoApuntador(Nodo[] nodoApuntador) {
        Lista.nodoApuntador = nodoApuntador;
    }
    
    
    public static Soundex getSoundex() {
        return soundex;
    }

    public static void setSoundex(Soundex soundex) {
        Lista.soundex = soundex;
    }
    
    
    
    
}
            
            
            
