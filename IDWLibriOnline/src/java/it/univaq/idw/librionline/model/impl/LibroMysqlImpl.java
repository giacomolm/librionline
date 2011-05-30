/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.model.impl;

import it.univaq.idw.librionline.model.Libro;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author giacomolm
 */
@Entity
@Table(name = "libro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Libro.findAll", query = "SELECT l FROM Libro l"),
    @NamedQuery(name = "Libro.findByIsbn", query = "SELECT l FROM Libro l WHERE l.isbn = :isbn"),
    @NamedQuery(name = "Libro.findByTitolo", query = "SELECT l FROM Libro l WHERE l.titolo = :titolo"),
    @NamedQuery(name = "Libro.findByEditore", query = "SELECT l FROM Libro l WHERE l.editore = :editore"),
    @NamedQuery(name = "Libro.findByAnnoPubblicazione", query = "SELECT l FROM Libro l WHERE l.annoPubblicazione = :annoPubblicazione")})
public class LibroMysqlImpl implements Serializable, Libro {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "isbn")
    private String isbn;
    @Basic(optional = false)
    @Column(name = "titolo")
    private String titolo;
    @Column(name = "editore")
    private String editore;
    @Column(name = "annoPubblicazione")
    private Integer annoPubblicazione;
    @Lob
    @Column(name = "recensione")
    private String recensione;
    @JoinTable(name = "libro_autore", joinColumns = {
        @JoinColumn(name = "libro", referencedColumnName = "isbn")}, inverseJoinColumns = {
        @JoinColumn(name = "autore", referencedColumnName = "id")})
    @ManyToMany
    private Collection<AutoreMysqlImpl> autoreCollection;
    @JoinTable(name = "libro_tag", joinColumns = {
        @JoinColumn(name = "libro", referencedColumnName = "isbn")}, inverseJoinColumns = {
        @JoinColumn(name = "tag", referencedColumnName = "id")})
    @ManyToMany
    private Collection<TagMysqlImpl> tagCollection;
    @JoinTable(name = "traduzione", joinColumns = {
        @JoinColumn(name = "libro1", referencedColumnName = "isbn")}, inverseJoinColumns = {
        @JoinColumn(name = "libro2", referencedColumnName = "isbn")})
    @ManyToMany
    private Collection<LibroMysqlImpl> libroCollection;
    @ManyToMany(mappedBy = "libroCollection")
    private Collection<LibroMysqlImpl> libroCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "libro")
    private Collection<CopiaelettronicaMysqlImpl> copiaelettronicaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "libro")
    private Collection<VolumeMysqlImpl> volumeCollection;
    @JoinColumn(name = "lingua", referencedColumnName = "id")
    @ManyToOne
    private LinguaMysqlImpl lingua;

    public LibroMysqlImpl() {
    }

    public LibroMysqlImpl(String isbn) {
        this.isbn = isbn;
    }

    public LibroMysqlImpl(String isbn, String titolo) {
        this.isbn = isbn;
        this.titolo = titolo;
    }

    @Override
    public String getIsbn() {
        return isbn;
    }

    @Override
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public String getTitolo() {
        return titolo;
    }

    @Override
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    @Override
    public String getEditore() {
        return editore;
    }

    @Override
    public void setEditore(String editore) {
        this.editore = editore;
    }

    @Override
    public Integer getAnnoPubblicazione() {
        return annoPubblicazione;
    }

    @Override
    public void setAnnoPubblicazione(Integer annoPubblicazione) {
        this.annoPubblicazione = annoPubblicazione;
    }

    @Override
    public String getRecensione() {
        return recensione;
    }

    @Override
    public void setRecensione(String recensione) {
        this.recensione = recensione;
    }

    @XmlTransient
    @Override
    public Collection<AutoreMysqlImpl> getAutoreCollection() {
        return autoreCollection;
    }

    @Override
    public void setAutoreCollection(Collection<AutoreMysqlImpl> autoreCollection) {
        this.autoreCollection = autoreCollection;
    }

    @XmlTransient
    @Override
    public Collection<TagMysqlImpl> getTagCollection() {
        return tagCollection;
    }

    @Override
    public void setTagCollection(Collection<TagMysqlImpl> tagCollection) {
        this.tagCollection = tagCollection;
    }

    @XmlTransient
    @Override
    public Collection<LibroMysqlImpl> getLibroCollection() {
        return libroCollection;
    }

    @Override
    public void setLibroCollection(Collection<LibroMysqlImpl> libroCollection) {
        this.libroCollection = libroCollection;
    }

    @XmlTransient
    @Override
    public Collection<LibroMysqlImpl> getLibroCollection1() {
        return libroCollection1;
    }

    @Override
    public void setLibroCollection1(Collection<LibroMysqlImpl> libroCollection1) {
        this.libroCollection1 = libroCollection1;
    }

    @XmlTransient
    @Override
    public Collection<CopiaelettronicaMysqlImpl> getCopiaelettronicaCollection() {
        return copiaelettronicaCollection;
    }

    @Override
    public void setCopiaelettronicaCollection(Collection<CopiaelettronicaMysqlImpl> copiaelettronicaCollection) {
        this.copiaelettronicaCollection = copiaelettronicaCollection;
    }

    @XmlTransient
    @Override
    public Collection<VolumeMysqlImpl> getVolumeCollection() {
        return volumeCollection;
    }

    @Override
    public void setVolumeCollection(Collection<VolumeMysqlImpl> volumeCollection) {
        this.volumeCollection = volumeCollection;
    }

    @Override
    public LinguaMysqlImpl getLingua() {
        return lingua;
    }

    @Override
    public void setLingua(LinguaMysqlImpl lingua) {
        this.lingua = lingua;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (isbn != null ? isbn.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LibroMysqlImpl)) {
            return false;
        }
        LibroMysqlImpl other = (LibroMysqlImpl) object;
        if ((this.isbn == null && other.isbn != null) || (this.isbn != null && !this.isbn.equals(other.isbn))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.univaq.idw.librionline.model.impl.Libro[ isbn=" + isbn + " ]";
    }
    
}
