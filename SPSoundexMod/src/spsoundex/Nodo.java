/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spsoundex;

/**
 *
 * @author juanu
 */
public class Nodo {
    
    Nodo nodoSiguiente;
    Nodo nodoAtras;
    String texto;
    String code;
    int veces;
    
    
    Nodo(){this.veces = 0;}

    Nodo(String texto){ this.texto = texto;this.veces = 0;}
    public Nodo(Nodo nodoSiguiente, Nodo nodoAtras, String texto, String code, int veces) {
        this.nodoSiguiente = nodoSiguiente;
        this.nodoAtras = nodoAtras;
        this.texto = texto;
        this.code = code;
        this.veces = veces;
    }
    /**
     * Obtener nodoSigiente.
     * @return Nodo
     */
    public Nodo getNodoSiguiente() {
        return nodoSiguiente;
    }
    /**
     * estableces nodoSiguiente.
     * @param nodoSiguiente nodoSiguiente 
     */
    public void setNodoSiguiente(Nodo nodoSiguiente) {
        this.nodoSiguiente = nodoSiguiente;
    }
    /**
     * Obtener nodoAtras.
     * @return Nodo
     */
    public Nodo getNodoAtras() {
        return nodoAtras;
    }
    /**
     * Establecer nodoAntras.
     * @param nodoAtras nodoAtras 
     */
    public void setNodoAtras(Nodo nodoAtras) {
        this.nodoAtras = nodoAtras;
    }
    /**
     * Obtener nodoTexto.
     * @return String
     */
    public String getTexto() {
        return texto;
    }
    /**
     * Estabelcer nodoTexto.
     * @param texto texto 
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }
    
    /**
     * Obtener nodoCodigo.
     * @return String
     */
    public String getCode() {
        return code;
    }
    /**
     * Establecer nodoCodigo.
     * @param code String codigo
     */
    public void setCode(String code) {
        this.code = code;
    }
    /**
     * Obtener veces.
     * @return int
     */
    public int getVeces() {
        return veces;
    }
    /**
     * Establecer veces.
     * @param veces  int veces
     */
    public void setVeces(int veces) {
        this.veces = veces;
    }
    
    
}
