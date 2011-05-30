/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.model;

import it.univaq.idw.librionline.model.impl.VolumeMysqlImpl;
import java.util.Collection;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author giacomolm
 */
public interface Stato {

    boolean equals(Object object);

    Integer getId();

    String getStato();

    @XmlTransient
    Collection<VolumeMysqlImpl> getVolumeCollection();

    int hashCode();

    void setId(Integer id);

    void setStato(String stato);

    void setVolumeCollection(Collection<VolumeMysqlImpl> volumeCollection);

    String toString();
    
}
