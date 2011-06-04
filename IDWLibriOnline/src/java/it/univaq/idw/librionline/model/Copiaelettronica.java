/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.model;

import it.univaq.idw.librionline.model.impl.LibroMysqlImpl;

/**
 *
 * @author giacomolm
 */
public interface Copiaelettronica {

    /**
     * Permette di verificare se si fa riferimento allo stessa copia elettronica
     * @param object
     * @return true se si fa riferimento alla stessa CopiaElettronica
     */
    boolean equals(Object object);

    /**
     * restituisce l'id della copia elettronica
     * @return intero rappresentante la chiave della copia elettronica
     */
    Integer getId();

    /**
     * Restituisce il libro (cioè la copia cartacea) a cui fa riferimento la copia elettronica
     * @return il libro materiale associato
     */
    Libro getLibro();

    /**
     * Restituisce il formato utilizzato per codificare il file
     * @return Stringa rappresentante il formato
     */
    String getMimetype();

    /**
     * Restituisce l'indirizzo della risorsa, cioè della copia elettronica, presente in rete
     * @return Stringa che ne rappresenta l'indirizzo
     */
    String getUrl();

    /**
     * 
     * @return
     */
    int hashCode();

    /**
     * Permette di impostare l'id, quindi la chiave del record associato a quella copia elettronica
     * @param id chiave della tupla
     */
    void setId(Integer id);

    /**
     * Imposta il libro, cioè la copia cartacea, a cui quella elettronica fa riferimento
     * @param libro
     */
    void setLibro(Libro libro);

    /**
     * Imposta il formato utilizzato per codificare il file
     * @param mimetype Stringa relativa al suo formato
     */
    void setMimetype(String mimetype);

    /**
     * Permette di impostare l'indirizzo della risorsa, cioè della copia elettronica, presente in rete
     * @param url Stringa raffigurante l'indirizzo della risorsa
     */
    void setUrl(String url);

    /**
     * Eredita l'implementazione fornita dalla classe object del metodo toString
     * @return stringa rappresentante l'oggetto.
     */
    String toString();
    
}
