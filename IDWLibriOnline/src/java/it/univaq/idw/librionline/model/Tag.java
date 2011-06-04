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
public interface Tag {

    /**
     * Verifica se l'oggetto indicato object e fa riferimento allo stesso Tag
     * @param object
     * @return true se fanno riferimento allo stesso tag
     */
    boolean equals(Object object);

    /**
     * Ritorna l'id del tag referenziato
     * @return Intero rappresentante la chiave del record relativo al tag
     */
    Integer getId();

    /**
     * Restituisce l'insieme dei Libri referenziati da quel tag
     * @return Collezione di Libro 
     */
    @XmlTransient
    Collection<Libro> getLibroCollection();

    /**
     * Ritorna il nome del tag a cui ci riferiamo
     * @return Stringa riferita al tag
     */
    String getTag();

    /**
     * 
     * @return
     */
    int hashCode();

    /**
     * Imposta la chiave del record relativo al tag
     * @param id Integer rappresentante la chiave
     */
    void setId(Integer id);

    /**
     * Imposta l'insieme dei libri che vengono referenziati dal tag, permettendo l'aggiunta o la rimozione di Libri
     * @param libroCollection Collezione di Libro
     */
    void setLibroCollection(Collection<Libro> libroCollection);

    /**
     * Imposta il nominativo del tag
     * @param tag Stringa del nuovo tag
     */
    void setTag(String tag);

    /**
     * Versione testuale dell'oggetto tag riferito
     * @return String rappresentante l'oggetto
     */
    String toString();
    
}
