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

    boolean equals(Object object);

    String getGruppo();

    Integer getId();

    @XmlTransient
    Collection<Servizio> getServizioCollection();

    @XmlTransient
    Collection<User> getUserCollection();

    int hashCode();

    void setGruppo(String gruppo);

    void setId(Integer id);

    void setServizioCollection(Collection<Servizio> servizioCollection);

    void setUserCollection(Collection<User> userCollection);

    String toString();
    
}
