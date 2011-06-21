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
public interface Autore {

    /**
     * Verifica se due oggetti fanno riferimento allo stesso autore
     * @param object
     * @return true se si fa riferimento allo stesso autore
     */
    @Override
    boolean equals(Object object);

    /**
     * Ritorna l'id dell'autore a cui facciamo riferimento
     * @return intero che indica l'id dell'autore
     */
    Integer getId();

    /**
     * Restituisce l'insieme di libri che sono stati scritti da un particolare autore
     * @return collezione di libri
     */
    @XmlTransient
    Collection<Libro> getLibroCollection();

    /**
     * Restituisce il nome dell'autore (nome completo)
     * @return una stringa rappresentante il nome
     */
    String getNome();
    
    /**
     * Resituisce il cognome dell'autore
     * @return Stringa indicante il cognome dell'autore
     */
    String getCognome() ;

    /**
     * Imposta il cognome di un autore
     * @param cognome Stringa indicante il cognome dell'autore
     */
    void setCognome(String cognome);
    

    /**
     * 
     * @return
     */
    int hashCode();

    /**
     * Permette di impostare la chiave del record relativo l'autore
     * @param id la chiave della tupla di quell'autore
     */
    void setId(Integer id);

    /**
     * Permette di impostare l'insieme di libri che rappresentano la traduzione di un particolare libro
     * @param libroCollection collezione di libri
     */
    void setLibroCollection(Collection<Libro> libroCollection);

    /**
     * Permette di impostare il nome (completo) dell'autore
     * @param nome
     */
    void setNome(String nome);

    /**
     * Eredita l'implementazione fornita dalla classe object del metodo toString
     * @return stringa rappresentante l'oggetto.
     */
    @Override
    String toString();
    
}
