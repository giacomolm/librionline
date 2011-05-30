/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.model;

import it.univaq.idw.librionline.model.impl.ServizioMysqlImpl;
import it.univaq.idw.librionline.model.impl.UserMysqlImpl;
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
    Collection<ServizioMysqlImpl> getServizioCollection();

    @XmlTransient
    Collection<UserMysqlImpl> getUserCollection();

    int hashCode();

    void setGruppo(String gruppo);

    void setId(Integer id);

    void setServizioCollection(Collection<ServizioMysqlImpl> servizioCollection);

    void setUserCollection(Collection<UserMysqlImpl> userCollection);

    String toString();
    
}
