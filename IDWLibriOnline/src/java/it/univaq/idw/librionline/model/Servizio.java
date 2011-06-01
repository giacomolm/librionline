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

    boolean equals(Object object);

    @XmlTransient
    Collection<Gruppo> getGruppoCollection();

    Integer getId();

    String getServizio();

    int hashCode();

    void setGruppoCollection(Collection<Gruppo> gruppoCollection);

    void setId(Integer id);

    void setServizio(String servizio);

    String toString();
    
}
