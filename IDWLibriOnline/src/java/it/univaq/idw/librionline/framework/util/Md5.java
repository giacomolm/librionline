/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.framework.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Zilfio
 */
public class Md5 {
    public Md5() {
    }
    public static String md5(String message) throws NoSuchAlgorithmException {
        // utilizziamo la classe MessageDigest del package java.security
        MessageDigest md;
        try {
            // getInstance vuole come argomento la costante “MD5” che rappresenta
            // l'algoritmo md5
            md = MessageDigest.getInstance("Md5");
        } catch (NoSuchAlgorithmException ex) {
            throw new NoSuchAlgorithmException("Errore. Non esiste un tale algoritmo.");
        }
        // stringbuffer di appoggio
        StringBuffer sb = new StringBuffer();
        // il metodo digest vuole come argomento un array di byte e restituisce un array
        // di byte
        byte[] messDig5 = md.digest(message.getBytes());
        // trasformiamo l'array di byte in una stringa
        for( int i = 0 ; i < messDig5.length ; i++ ) {
            String tmpStr = "0"+Integer.toHexString( (0xff & messDig5[i]));
            sb.append(tmpStr.substring(tmpStr.length()-2));
        }
        // restituiamo la stringa ottenuta da sb
        return sb.toString();
    } // fine metodo md5
} // fine classe Md5