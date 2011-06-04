/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.model;

import java.util.Collection;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author giacomolm
 */
public interface Lingua {

    /**
     * 
     * @param object
     * @return true se le lingue, quella a cui facciamo riferimento e il parametro object coincidono
     */
    boolean equals(Object object);

    /**
     * Ritorna l'ID della lingua, cioè la sua chiave
     * @return Integer rappresentante la chiave
     */
    Integer getId();

    /**
     * Ritorna l'insieme di libri scritti in quella lingua
     * @return Collezione di Libro
     */
    @XmlTransient
    Collection<Libro> getLibroCollection();

    /**
     * Restituisce la lingua, cioè la stringa come ad esempio "Italiano", a cui facciamo riferimento
     * @return Stringa rappresentante la ligua
     */
    String getLingua();

    /**
     * 
     * @return
     */
    int hashCode();

    /**
     * Imposta la chiave del record contenente la lingua
     * @param id Integer rappresentante la chiave
     */
    void setId(Integer id);

    /**
     * Imposta la collezione di libri scritti in quella particolare lingua
     * @param libroCollection Collezione di Libro
     */
    void setLibroCollection(Collection<Libro> libroCollection);

    /**
     * Imposta il nome della lingua
     * @param lingua Stringa rappresentante il nome della lingua
     */
    void setLingua(String lingua);

    /**
     * Versione testuale dell'oggetto lingua a cui si fa riferimento
     * @return String che definisce il nome della lingua
     */
    String toString();
    
}
