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
public interface Gruppo {

    /**
     *  
     * @param object
     * @return true se l'object passato come parametro fa riferimento allo stesso Gruppo
     */
    boolean equals(Object object);

    /**
     * Restituisce il nome del gruppo a cui facciamo riferimento
     * @return la stringa relativo al nomde del gruppo
     */
    String getGruppo();

    /**
     * Ritorna la chiave del gruppo a cui facciamo riferimento
     * @return intero rappresentante la chiave
     */
    Integer getId();

    /**
     * Restituisce l'insieme di servizi di cui un gruppo può usufruire
     * @return collection di servizi inerenti al gruppo
     */
    @XmlTransient
    Collection<Servizio> getServizioCollection();

    /**
     * Restituisce l'insieme degli utente appartenti a quel gruppo
     * @return collezione di utenti di quel gruppo
     */
    @XmlTransient
    Collection<User> getUserCollection();

    /**
     * 
     * @return
     */
    int hashCode();

    /**
     * Permette di impostare il nome del gruppo
     * @param gruppo Stringa che ne rappresenti il nome
     */
    void setGruppo(String gruppo);

    /**
     * Permette di impostare la chiave del record del gruppo a cui facciamo riferimento
     * @param id chiave del gruppo riferito
     */
    void setId(Integer id);

    /**
     * Imposta l'insieme dei servizi connessi al gruppo
     * @param servizioCollection collezione dei servizi da aggiornare
     */
    void setServizioCollection(Collection<Servizio> servizioCollection);

    /**
     * Imposta l'insieme di utenti associati al gruppo
     * @param userCollection collezione di utenti associati al gruppo
     */
    void setUserCollection(Collection<User> userCollection);

    /**
     * Restituisce la stringa raffigurante l'oggetto Gruppo 
     * @return Stringa del gruppo
     */
    String toString();
    
}
