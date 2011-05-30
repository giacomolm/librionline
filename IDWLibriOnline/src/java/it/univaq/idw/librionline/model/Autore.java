/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.model;

import it.univaq.idw.librionline.model.impl.LibroMysqlImpl;
import java.util.Collection;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author giacomolm
 */
public interface Autore {

    boolean equals(Object object);

    Integer getId();

    @XmlTransient
    Collection<LibroMysqlImpl> getLibroCollection();

    String getNome();

    int hashCode();

    void setId(Integer id);

    void setLibroCollection(Collection<LibroMysqlImpl> libroCollection);

    void setNome(String nome);

    String toString();
    
}
