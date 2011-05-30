/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.model;

import it.univaq.idw.librionline.model.impl.AutoreMysqlImpl;
import it.univaq.idw.librionline.model.impl.CopiaelettronicaMysqlImpl;
import it.univaq.idw.librionline.model.impl.LibroMysqlImpl;
import it.univaq.idw.librionline.model.impl.LinguaMysqlImpl;
import it.univaq.idw.librionline.model.impl.TagMysqlImpl;
import it.univaq.idw.librionline.model.impl.VolumeMysqlImpl;
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
    Collection<AutoreMysqlImpl> getAutoreCollection();

    @XmlTransient
    Collection<CopiaelettronicaMysqlImpl> getCopiaelettronicaCollection();

    String getEditore();

    String getIsbn();

    @XmlTransient
    Collection<LibroMysqlImpl> getLibroCollection();

    @XmlTransient
    Collection<LibroMysqlImpl> getLibroCollection1();

    LinguaMysqlImpl getLingua();

    String getRecensione();

    @XmlTransient
    Collection<TagMysqlImpl> getTagCollection();

    String getTitolo();

    @XmlTransient
    Collection<VolumeMysqlImpl> getVolumeCollection();

    int hashCode();

    void setAnnoPubblicazione(Integer annoPubblicazione);

    void setAutoreCollection(Collection<AutoreMysqlImpl> autoreCollection);

    void setCopiaelettronicaCollection(Collection<CopiaelettronicaMysqlImpl> copiaelettronicaCollection);

    void setEditore(String editore);

    void setIsbn(String isbn);

    void setLibroCollection(Collection<LibroMysqlImpl> libroCollection);

    void setLibroCollection1(Collection<LibroMysqlImpl> libroCollection1);

    void setLingua(LinguaMysqlImpl lingua);

    void setRecensione(String recensione);

    void setTagCollection(Collection<TagMysqlImpl> tagCollection);

    void setTitolo(String titolo);

    void setVolumeCollection(Collection<VolumeMysqlImpl> volumeCollection);

    String toString();
    
}
