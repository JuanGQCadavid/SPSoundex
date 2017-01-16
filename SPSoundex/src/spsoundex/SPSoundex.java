/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spsoundex;

import java.io.IOException;
import java.util.ArrayList;
import ParteGrafica.*;

/**
 *
 * @author Juan Gonzalo Quiroz Cadavid
 */
public class SPSoundex {
    /**
     * Variable de Listas.
     * genera la tablaHash con sus
     * valores.
     */
    public static Lista DataBace = new Lista();
    
    
    /**
     * Tabla generada en Listas.
     */
    private static Nodo tablaHash[] = DataBace.getTablaHash();
    
    /**
     * Nodo principal.
     */
    public static Nodo nodoRaiz;

    /**
     * Constructor vacio.
     * 
     */
    public SPSoundex() {
        
        
    }
    
    
    /**
     * Metodo principal de la clase.
     * 
     * Llama la interfaz y la muestra.
     * @param args args
     * @throws IOException IOException 
     */
    public static void main(String[] args) throws IOException {
        
        main ventana = new main();
        ventana.setVisible(true);
    }
    
    
    /**
     * Este metodo se encarga de generar el nodo para
     * Autocompletar.
     * 
     * Obtiene el nodo mediante ObtenerNodo(String)
     * 
     * Llama a autocompletar con el Nodo optenido
     * 
     * Devuelve el Arreglo obtenido en forma ordenada
     * mediante OrdenarArray.
     * 
     * @param cadena cadena
     * @return ArrayList(Nodo)
     */
    public static ArrayList<Nodo> preAutocompletar(String cadena){
        nodoRaiz = Lista.getTablaHash(obtenerNodo(cadena));
        return OrdenarArry(Autocompletador(nodoRaiz.getNodoSiguiente(),cadena));
    }
    
    /**
     * Mediante un String, buscara el nodo
     * ubicandolo primero en la tabla hash
     * y luego pasando nodo por nodo hasta 
     * encotrarlo.
     * @param nodoText nodoText
     * @return Nodo
     */
    public static Nodo buscarNodo(String nodoText){
        Nodo nodoResultante = null;
        nodoText = nodoText.toLowerCase();
        int iteradorHash = obtenerNodo(nodoText);
        
        Nodo nodoActual = tablaHash[iteradorHash];
        nodoActual = nodoActual.getNodoSiguiente();
        
        while(nodoActual.getNodoSiguiente()!= null){
            if(nodoActual.getTexto().equals(nodoText)){
                return nodoActual;
            }else{
                nodoActual = nodoActual.getNodoSiguiente();
            }
        }
        
        return nodoResultante;
    }
    
    /**
     * Retorna la poscicion del Nodo
     * inicial en la tabla hash.
     * @param Header Header
     * @return int Poscicon en Hash
     */
    public static int obtenerNodo(String Header){
        if(Header.charAt(0) >= 'a'){
            return Header.charAt(0)-97;
        }      
        else if(Header.charAt(0) >= '&'){
            return 26;
        }
        else if(Header.charAt(0) >= '1'){
            return Header.charAt(0)-21;
        }       
        else{
            return 27;
        }
    }

    /**
     * Recorre los nodos uno por uno comparando que
     * el Scring Bcc sea igual a la cadena de longitud Bcc
     * generada en el nodoActual.getText, de ser esto verdadero
     * comenzara a guadar todos los nodos con iguales atributos.
     * 
     * cuando dejen de ser iguales, para el proseso.
     * 
     * @param nodoActual nodoActual
     * @param Bcc Cadena solicituada
     * @return ArrayList(Nodo) ArrayList(Nodo) resultante
     */
    public static ArrayList<Nodo> Autocompletador(Nodo nodoActual, String Bcc){
        
        ArrayList<Nodo>  nodos = new ArrayList<Nodo>();
        
        //Esta variable me represneta que encontramos el Objetico.
        boolean estado = false;
        
        /*
            En este metodo vamos a buscar
            todas las palabras que empiezen con el prefijo
            establecido.
        
        Nota:   No se hace Con recursion por que crearia muchos llamados en Cola
        
        */
        while(true){
            
            /*
                Este metodo es el que busca, en esta parte
                validamos nuestra parada, osea que ya no
                tengamos mas nodos displonibles.
            */ 
            
            if(nodoActual!= null){
                /*
                    Hasta que el estado sea falso
                    seguiremos buscando el lugar donde debemos empezar
                    a mostrar las palabras
                */
                if(!estado){
                    
                    /*
                        Esto se usa para evitar errores de
                        StringIndexOutOfBoundsException
                    */
                    if(nodoActual.getTexto().length()>= Bcc.length()){
                        
                        //System.out.println("Nodo Actual: " + nodoActual.getTexto());
                        
                        //System.out.println(Bcc +" vs " + nodoActual.getTexto().substring(0,Bcc.length()));
                        
                        /*
                            Buscamos la similitud entre las palabras
                            pàra luego comenar a mostrar las palabras con
                            el prefijo igual.
                        */
                        
                            if(Bcc.equals(nodoActual.getTexto().substring(0,Bcc.length()))){
                                /*
                                    Indicamos el estado true
                                    para decir que ya puede conebnzar a 
                                    mostrar.
                                */
                                estado = true;
                                
                            }else{
                                /*
                                    Indicamos que puede seguir al siguiente nodo.
                                */
                                nodoActual = nodoActual.nodoSiguiente;
                            }
                           
                        
                    }else{
                        nodoActual = nodoActual.nodoSiguiente;
                    }
                    
                }else{
                    /*
                         Mostraeremos los datos hasta que el Prefijo sea
                         diferente, entonces break.
                    */
                    if(Bcc.length()<=nodoActual.getTexto().length()){
                        if(Bcc.equals(nodoActual.getTexto().substring(0,Bcc.length()))){
                            //System.out.println(nodoActual.getTexto());
                            nodos.add(nodoActual);
                            nodoActual = nodoActual.nodoSiguiente;
                        }else{
                            break;
                        }
                    }else{
                        break;
                    }
                }
            
            
            }else{
                /*
                    Si la bandera nunca se cambio a verdadero
                     esq ue no lo encontreo, por ende, no existe.
                */
                if(!estado){
                    System.out.println("Palabra no encontrada");
                }
                break;
            }
        
        }
        return nodos;
    }

    /**
     * Obtiene un nodo, aumenta su frecuencia(veces)
     * luego llama al metodo generarArray que devolvera
     * el arrayList con los nodos foneticamente iguales.
     * y posteriormente, entrega e resultado en orden.
     * 
     * @param nodo Nodo bace
     * @param max Maximos elementos como respuesta
     * @return ArrayList(Nodo)
     */
    public static ArrayList<Nodo> obtenerSoundex(Nodo nodo, int max){
        nodo.setVeces(nodo.getVeces()+1);
        return OrdenarArry(generarArray(nodo,max));
        
    }
    
    /**
     * Ordena un ArrayLists de orden
     * Mayor a menor, mediante su atributo
     * (int) veces.
     * @param arrayOriginal arrayOriginal proseciente a ser ordenado
     * @return arrayOriginal
     */
    public static ArrayList<Nodo> OrdenarArry(ArrayList<Nodo> arrayOriginal){
       
        Nodo nodoTem;
        for (int i = 0; i < arrayOriginal.size(); i++) {
            for (int j = i; j < arrayOriginal.size(); j++) {
                if(arrayOriginal.get(i).getVeces()<arrayOriginal.get(j).getVeces()){
                    nodoTem = arrayOriginal.get(i);
                    arrayOriginal.set(i,arrayOriginal.get(j));
                    arrayOriginal.set(j,nodoTem);
                }
            }
        }

        return arrayOriginal;
    }
    
    /**
     * Este metodo genera el Array List original,
     *   en donde recorre toda la lista a partir de
     *   la tabla hash con su poscion correspoindiente
     *   ya que el codigo comienza por
     *
     *   Letra - Numeros usaremos la letra para indicar la pos.
     * @param nodo Nodo
     * @param max Maximos elementos por respuesta
     * @return arrayNuevo
     */
    public static ArrayList<Nodo> generarArray(Nodo nodo, int max){
        ArrayList<Nodo> arrayNuevo = new ArrayList<Nodo>();
        
        String Header = nodo.getCode();
        Header = Header.toLowerCase();
        
         int inicioHash = 0;
        
        /*
            Letras -> Header.charAt(0)-97
            
            & -> 26
        
            ' -> 27
            
            Numeros -> Header.charAt(0)-21
        */
        
        
        if(Header.charAt(0) >= 'a'){
            inicioHash = Header.charAt(0)-97;
        }      
        else if(Header.charAt(0) >= '&'){
            inicioHash = 26;
        }
        else if(Header.charAt(0) >= '1'){
            inicioHash = Header.charAt(0)-21;
        }       
        else{
            inicioHash = 27;
        }
        
        Header = Header.toUpperCase();
        
        Nodo nodoActual = tablaHash[inicioHash];
        
        nodoActual = nodoActual.getNodoSiguiente();
        
        int contador = 0;
        while(nodoActual.nodoSiguiente != null && contador <= max){
            //System.out.println(nodoActual.getCode());
            
            if(nodoActual.getCode().equals(Header)){
                
                System.out.println("Macht");
                arrayNuevo.add(nodoActual); 
                contador++;
            }
            
            nodoActual = nodoActual.getNodoSiguiente();
            
        }
        
        
        
        
        return arrayNuevo;
    }
    
    }