/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.model;

import it.univaq.idw.librionline.model.impl.LibroMysqlImpl;
import it.univaq.idw.librionline.model.impl.PrestitoMysqlImpl;
import it.univaq.idw.librionline.model.impl.StatoMysqlImpl;
import java.util.Collection;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author giacomolm
 */
public interface Volume {

    /**
     * Verifica se i due riferimenti sono relativi allo stesso volume
     * @param object
     * @return true se fanno riferimento allo stesso Volume
     */
    boolean equals(Object object);

    /**
     * Restituisce l'identificativo del record nel quale è contenuto il volume
     * @return Integer relativo la chiave
     */
    Integer getId();

    /**
     * Restituisce il Libro per il quale il Volume ne è una rappresentazione concreta
     * @return Libro riferito dal volume
     */
    Libro getLibro();

    /**
     * Restituisce l'insieme di Prestiti a cui è stato ed è soggetto il volume
     * @return Collezione di Prestito
     */
    @XmlTransient
    Collection<Prestito> getPrestitoCollection();

    /**
     * Ritorna lo stato del volume, cioè le sue condizioni fisiche
     * @return Stato del Volume
     */
    Stato getStato();

    /**
     * 
     * @return
     */
    int hashCode();

    /**
     * Imposta l'identificativo del volume
     * @param id chiave del record nel quale il record è contenuto
     */
    void setId(Integer id);

    /**
     * Imposta il Libro a cui il volume si riferisce, cioè la Versione generica
     * @param libro Libro a cui si fa riferimento
     */
    void setLibro(Libro libro);

    /**
     * Imposta l'insieme dei prestiti a cui il volume è soggetto: permette di aggiungere un nuovo prestito nelle proprietà del volume
     * @param prestitoCollection Collezione di Prestito
     */
    void setPrestitoCollection(Collection<Prestito> prestitoCollection);

    /**
     * Imposta lo stato, cioè le condizioni fisiche del volume
     * @param stato
     */
    void setStato(Stato stato);

    /**
     * Restituisce la versione testuale dell'oggetto Volume referenziato
     * @return Stringa rappresentante la sua versione
     */
    String toString();
    
}
