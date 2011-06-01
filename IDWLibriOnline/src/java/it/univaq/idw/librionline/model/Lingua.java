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
public interface Lingua {

    boolean equals(Object object);

    Integer getId();

    @XmlTransient
    Collection<Libro> getLibroCollection();

    String getLingua();

    int hashCode();

    void setId(Integer id);

    void setLibroCollection(Collection<Libro> libroCollection);

    void setLingua(String lingua);

    String toString();
    
}
