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
public interface Libro {

    boolean equals(Object object);

    Integer getAnnoPubblicazione();

    @XmlTransient
    Collection<Autore> getAutoreCollection();

    @XmlTransient
    Collection<Copiaelettronica> getCopiaelettronicaCollection();

    String getEditore();

    String getIsbn();

    @XmlTransient
    Collection<Libro> getLibroCollection();

    @XmlTransient
    Collection<Libro> getLibroCollection1();

    Lingua getLingua();

    String getRecensione();

    @XmlTransient
    Collection<Tag> getTagCollection();

    String getTitolo();

    @XmlTransient
    Collection<Volume> getVolumeCollection();

    int hashCode();

    void setAnnoPubblicazione(Integer annoPubblicazione);

    void setAutoreCollection(Collection<Autore> autoreCollection);

    void setCopiaelettronicaCollection(Collection<Copiaelettronica> copiaelettronicaCollection);

    void setEditore(String editore);

    void setIsbn(String isbn);

    void setLibroCollection(Collection<Libro> libroCollection);

    void setLibroCollection1(Collection<Libro> libroCollection1);

    void setLingua(Lingua lingua);

    void setRecensione(String recensione);

    void setTagCollection(Collection<Tag> tagCollection);

    void setTitolo(String titolo);

    void setVolumeCollection(Collection<Volume> volumeCollection);

    String toString();
    
}
