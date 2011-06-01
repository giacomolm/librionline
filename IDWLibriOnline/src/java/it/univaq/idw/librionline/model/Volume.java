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

    boolean equals(Object object);

    Integer getId();

    Libro getLibro();

    @XmlTransient
    Collection<Prestito> getPrestitoCollection();

    Stato getStato();

    int hashCode();

    void setId(Integer id);

    void setLibro(Libro libro);

    void setPrestitoCollection(Collection<Prestito> prestitoCollection);

    void setStato(Stato stato);

    String toString();
    
}
