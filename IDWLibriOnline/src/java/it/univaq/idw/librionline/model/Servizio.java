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
public interface Servizio {

    /**
     * Verifica se l'oggetto object e fa riferimento allo stesso servizio
     * @param object
     * @return true se si riferiscono allo stesso servizio
     */
    boolean equals(Object object);

    /**
     * Restituisce l'insieme dei gruppi che possono usufruire di quel servizio
     * @return Collezione di Gruppo 
     */
    @XmlTransient
    Collection<Gruppo> getGruppoCollection();

    /**
     * Restitusce l'id del record riferito a quel servizio
     * @return Integer rappresentante la chiave
     */
    Integer getId();

    /**
     * Restituisce il nome del servizio
     * @return Stringa rappresentante il nome del servizio
     */
    String getServizio();

    /**
     * 
     * @return
     */
    int hashCode();

    /**
     * Imposta l'insieme do Gruppo che può usufruire del servizio referenziato
     * @param gruppoCollection veien passata la Collezione di Gruppo aggiornata
     */
    void setGruppoCollection(Collection<Gruppo> gruppoCollection);

    /**
     * Imposta il valore della chiave relativo al record contenente quel servizio
     * @param id Integer rappresentante la chiave
     */
    void setId(Integer id);

    /**
     * Imposta il nome del servizio
     * @param servizio String
     */
    void setServizio(String servizio);

    /**
     * Restituisce la versione testuale dell'oggetto Servizio a cui facciamo riferimento
     * @return Stringa rappresentante l'oggetto
     */
    String toString();
    
}
