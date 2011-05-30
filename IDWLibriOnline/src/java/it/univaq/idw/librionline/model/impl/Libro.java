/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.model.impl;

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
public class Libro implements Serializable {
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
    private Collection<Autore> autoreCollection;
    @JoinTable(name = "libro_tag", joinColumns = {
        @JoinColumn(name = "libro", referencedColumnName = "isbn")}, inverseJoinColumns = {
        @JoinColumn(name = "tag", referencedColumnName = "id")})
    @ManyToMany
    private Collection<Tag> tagCollection;
    @JoinTable(name = "traduzione", joinColumns = {
        @JoinColumn(name = "libro1", referencedColumnName = "isbn")}, inverseJoinColumns = {
        @JoinColumn(name = "libro2", referencedColumnName = "isbn")})
    @ManyToMany
    private Collection<Libro> libroCollection;
    @ManyToMany(mappedBy = "libroCollection")
    private Collection<Libro> libroCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "libro")
    private Collection<Copiaelettronica> copiaelettronicaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "libro")
    private Collection<Volume> volumeCollection;
    @JoinColumn(name = "lingua", referencedColumnName = "id")
    @ManyToOne
    private Lingua lingua;

    public Libro() {
    }

    public Libro(String isbn) {
        this.isbn = isbn;
    }

    public Libro(String isbn, String titolo) {
        this.isbn = isbn;
        this.titolo = titolo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getEditore() {
        return editore;
    }

    public void setEditore(String editore) {
        this.editore = editore;
    }

    public Integer getAnnoPubblicazione() {
        return annoPubblicazione;
    }

    public void setAnnoPubblicazione(Integer annoPubblicazione) {
        this.annoPubblicazione = annoPubblicazione;
    }

    public String getRecensione() {
        return recensione;
    }

    public void setRecensione(String recensione) {
        this.recensione = recensione;
    }

    @XmlTransient
    public Collection<Autore> getAutoreCollection() {
        return autoreCollection;
    }

    public void setAutoreCollection(Collection<Autore> autoreCollection) {
        this.autoreCollection = autoreCollection;
    }

    @XmlTransient
    public Collection<Tag> getTagCollection() {
        return tagCollection;
    }

    public void setTagCollection(Collection<Tag> tagCollection) {
        this.tagCollection = tagCollection;
    }

    @XmlTransient
    public Collection<Libro> getLibroCollection() {
        return libroCollection;
    }

    public void setLibroCollection(Collection<Libro> libroCollection) {
        this.libroCollection = libroCollection;
    }

    @XmlTransient
    public Collection<Libro> getLibroCollection1() {
        return libroCollection1;
    }

    public void setLibroCollection1(Collection<Libro> libroCollection1) {
        this.libroCollection1 = libroCollection1;
    }

    @XmlTransient
    public Collection<Copiaelettronica> getCopiaelettronicaCollection() {
        return copiaelettronicaCollection;
    }

    public void setCopiaelettronicaCollection(Collection<Copiaelettronica> copiaelettronicaCollection) {
        this.copiaelettronicaCollection = copiaelettronicaCollection;
    }

    @XmlTransient
    public Collection<Volume> getVolumeCollection() {
        return volumeCollection;
    }

    public void setVolumeCollection(Collection<Volume> volumeCollection) {
        this.volumeCollection = volumeCollection;
    }

    public Lingua getLingua() {
        return lingua;
    }

    public void setLingua(Lingua lingua) {
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
        if (!(object instanceof Libro)) {
            return false;
        }
        Libro other = (Libro) object;
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
