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
public interface Stato {

    /**
     * Verifica se l'oggetto object e fa riferimento allo stesso Stato (del volume, cioè se Usarato, Nuovo) a cui facciamo riferimento
     * @param object
     * @return true se fanno riferimento allo stesso stao
     */
    boolean equals(Object object);

    /**
     * Restituisce l'id dello Stato
     * @return la chiave del record nel quale p contenuto quel particolare stato
     */
    Integer getId();

    /**
     * Ritorna lo stato, cioè una stringa del tipo "Nuovo", "Usurato", etc. etc.
     * @return Stringa rappresentante lo stato
     */
    String getStato();

    /**
     * Restituisce tutti quanti i Volumi che sono in quel particolare stato
     * @return Collezione di Volume
     */
    @XmlTransient
    Collection<Volume> getVolumeCollection();

    /**
     * 
     * @return
     */
    int hashCode();

    /**
     * Imposta la chiave del record contenente quello statp
     * @param id Integer relativo la chiave
     */
    void setId(Integer id);

    /**
     * Imposta il nome dello stato
     * @param stato Stringa rappresentante il nuovo nome dello stato 
     */
    void setStato(String stato);

    /**
     * Imposta l'insieme dei volumi che sono in quello stato. Si permette la modifica la modifica dell'insieme dei volumi associati
     * @param volumeCollection Collezione di Volume
     */
    void setVolumeCollection(Collection<Volume> volumeCollection);

    /**
     * Versione testuale dell'oggetto Stato riferito
     * @return la stringa rappresentante l'oggetto
     */
    String toString();
    
}
