/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.model;

import it.univaq.idw.librionline.model.impl.LibroMysqlImpl;

/**
 *
 * @author giacomolm
 */
public interface Copiaelettronica {

    boolean equals(Object object);

    Integer getId();

    Libro getLibro();

    String getMimetype();

    String getUrl();

    int hashCode();

    void setId(Integer id);

    void setLibro(Libro libro);

    void setMimetype(String mimetype);

    void setUrl(String url);

    String toString();
    
}
