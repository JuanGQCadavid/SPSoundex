/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package spsoundex;

/**
 *
 * @author juanu
 */
public class Soundex {
    /**
     * Constructor publico del
     * Soundex.
     */
    public Soundex(){}
    
    /**
     * Genera y devuelve el codigo fonetico
     * que le corresponde mediante un proceso
     * selectivo de letras que suenan iguales.
     * 
     * @param s String texto
     * @return  String codigo
     */
    public static String getGode(String s) 
    {
        
        char[] x = s.toUpperCase().toCharArray();
        
        char firstLetter = x[0];
        
        //RULE [ 2 ]
        //Convert letters to numeric code
        for (int i = 0; i < x.length; i++) {
            
            switch (x[i]) {
                case 'B':
                case 'F':
                case 'P':
                case 'V': {
                    x[i] = '1';
                    break;
                }

                case 'C':
                case 'G':
                case 'J':
                case 'K':
                case 'Q':
                case 'S':
                case 'X':
                case 'Z': {
                    x[i] = '2';
                    break;
                }

                case 'D':
                case 'T': {
                    x[i] = '3';
                    break;
                }

                case 'L': {
                    x[i] = '4';
                    break;
                }

                case 'M':
                case 'N': {
                    x[i] = '5';
                    break;
                }

                case 'R': {
                    x[i] = '6';
                    break;
                }

                default: {
                    x[i] = '0';
                     break;
                }
            }
        }

        //Remove duplicates
        //RULE [ 1 ]
        String output = "" + firstLetter;

        //RULE [ 3 ]
        for (int i = 1; i < x.length; i++)
            if (x[i] != x[i - 1] && x[i] != '0')
                output += x[i];

        //RULE [ 4 ]
        //Pad with 0's or truncate
        output = output + "0000";
        //System.out.println(s);
        return output.substring(0, 4);
    }
    
    /**
     * Compara 2 palabras para comprobar
     * si son foneticamente iguales o no.
     * @param palabra1 palabra1
     * @param palabra2 palabra2 
     * @return boolean respuesta
     */
    public static boolean Comparar(String palabra1,String palabra2){
        String result1 = getGode(palabra1);
        String result2 = getGode(palabra2);
       /* char r1 [] = result1.toCharArray();
        char r2 [] = result2.toCharArray();
        for(*/
        if(result1.substring(1).equals(result2.substring(1))){
            System.out.println("Su pronunciación es igual");
            return true;
        }
        else{
            System.out.println("Su pronunciación no  es igual");
            return false;
        }
    }
}
