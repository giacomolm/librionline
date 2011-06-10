/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.model;

import it.univaq.idw.librionline.model.Libro;
import java.util.Collection;

/**
 *
 * @author giacomolm
 */
public interface LibriOnLineDataLayer {

    Collection<Libro> simpleBookSearch(String titolo);
    
}
