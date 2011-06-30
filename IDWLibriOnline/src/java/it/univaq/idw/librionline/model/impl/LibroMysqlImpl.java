/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * 
CREATE TABLE IF NOT EXISTS `libro` (
  `isbn` varchar(10) NOT NULL,
  `titolo` varchar(50) NOT NULL,
  `editore` varchar(25) DEFAULT NULL,
  `annoPubblicazione` int(4) DEFAULT NULL,
  `recensione` text,
  `lingua` int(11) DEFAULT NULL,
  PRIMARY KEY (`isbn`),
  KEY `lingua` (`lingua`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
 */
package it.univaq.idw.librionline.model.impl;

import it.univaq.idw.librionline.model.Autore;
import it.univaq.idw.librionline.model.Copiaelettronica;
import it.univaq.idw.librionline.model.Libro;
import it.univaq.idw.librionline.model.Lingua;
import it.univaq.idw.librionline.model.Tag;
import it.univaq.idw.librionline.model.Volume;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    @NamedQuery(name = "LibroMysqlImpl.findAll", query = "SELECT l FROM LibroMysqlImpl l"),
    @NamedQuery(name = "LibroMysqlImpl.findByIsbn", query = "SELECT l FROM LibroMysqlImpl l WHERE l.isbn = :isbn"),
    @NamedQuery(name = "LibroMysqlImpl.findByTitolo", query = "SELECT l FROM LibroMysqlImpl l WHERE l.titolo = :titolo"),
    @NamedQuery(name = "LibroMysqlImpl.findByEditore", query = "SELECT l FROM LibroMysqlImpl l WHERE l.editore = :editore"),
    @NamedQuery(name = "LibroMysqlImpl.findByAnnoPubblicazione", query = "SELECT l FROM LibroMysqlImpl l WHERE l.annoPubblicazione = :annoPubblicazione")})
public class LibroMysqlImpl implements Serializable,Libro {
    @Column(name =     "annoPubblicazione")
    private String annoPubblicazione;
    @Basic(optional =     false)
    @Column(name = "data_ins")
    @Temporal(TemporalType.DATE)
    private Date dataIns;
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
    @Lob
    @Column(name = "recensione")
    private String recensione;
    @JoinTable(name = "libro_autore", joinColumns = {
        @JoinColumn(name = "libro", referencedColumnName = "isbn")}, inverseJoinColumns = {
        @JoinColumn(name = "autore", referencedColumnName = "id")})
    @ManyToMany
    private Collection<AutoreMysqlImpl> autoreMysqlImplCollection;
    @JoinTable(name = "libro_tag", joinColumns = {
        @JoinColumn(name = "libro", referencedColumnName = "isbn")}, inverseJoinColumns = {
        @JoinColumn(name = "tag", referencedColumnName = "id")})
    @ManyToMany
    private Collection<TagMysqlImpl> tagMysqlImplCollection;
    @JoinTable(name = "traduzione", joinColumns = {
        @JoinColumn(name = "libro1", referencedColumnName = "isbn")}, inverseJoinColumns = {
        @JoinColumn(name = "libro2", referencedColumnName = "isbn")})
    @ManyToMany
    private Collection<LibroMysqlImpl> libroMysqlImplCollection;
    @ManyToMany(mappedBy = "libroMysqlImplCollection")
    private Collection<LibroMysqlImpl> libroMysqlImplCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "libro")
    private Collection<CopiaelettronicaMysqlImpl> copiaelettronicaMysqlImplCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "libro")
    private Collection<VolumeMysqlImpl> volumeMysqlImplCollection;
    @JoinColumn(name = "lingua", referencedColumnName = "id")
    @ManyToOne
    private LinguaMysqlImpl lingua;

    public LibroMysqlImpl() {
    }

    /**
     * 
     * @param isbn
     */
    LibroMysqlImpl(String isbn) {
        this.isbn = isbn;
    }

    /**
     * 
     * @param isbn
     * @param titolo
     */
    LibroMysqlImpl(String isbn, String titolo) {
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
    public String getRecensione() {
        return recensione;
    }

    @Override
    public void setRecensione(String recensione) {
        this.recensione = recensione;
    }

    /**
     * 
     * @return
     */
    @XmlTransient
    public Collection<AutoreMysqlImpl> getAutoreMysqlImplCollection() {
        return autoreMysqlImplCollection;
    }

    /**
     * 
     * @param autoreMysqlImplCollection
     */
    public void setAutoreMysqlImplCollection(Collection<AutoreMysqlImpl> autoreMysqlImplCollection) {
        this.autoreMysqlImplCollection = autoreMysqlImplCollection;
    }

    /**
     * 
     * @return
     */
    @XmlTransient
    public Collection<TagMysqlImpl> getTagMysqlImplCollection() {
        return tagMysqlImplCollection;
    }

    /**
     * 
     * @param tagMysqlImplCollection
     */
    public void setTagMysqlImplCollection(Collection<TagMysqlImpl> tagMysqlImplCollection) {
        this.tagMysqlImplCollection = tagMysqlImplCollection;
    }

    /**
     * 
     * @return
     */
    @XmlTransient
    public Collection<LibroMysqlImpl> getLibroMysqlImplCollection() {
        return libroMysqlImplCollection;
    }

    /**
     * 
     * @param libroMysqlImplCollection
     */
    public void setLibroMysqlImplCollection(Collection<LibroMysqlImpl> libroMysqlImplCollection) {
        this.libroMysqlImplCollection = libroMysqlImplCollection;
    }

    /**
     * 
     * @return
     */
    @XmlTransient
    public Collection<LibroMysqlImpl> getLibroMysqlImplCollection1() {
        return libroMysqlImplCollection1;
    }

    /**
     * 
     * @param libroMysqlImplCollection1
     */
    public void setLibroMysqlImplCollection1(Collection<LibroMysqlImpl> libroMysqlImplCollection1) {
        this.libroMysqlImplCollection1 = libroMysqlImplCollection1;
    }

    /**
     * 
     * @return
     */
    @XmlTransient
    public Collection<CopiaelettronicaMysqlImpl> getCopiaelettronicaMysqlImplCollection() {
        return copiaelettronicaMysqlImplCollection;
    }

    /**
     * 
     * @param copiaelettronicaMysqlImplCollection
     */
    public void setCopiaelettronicaMysqlImplCollection(Collection<CopiaelettronicaMysqlImpl> copiaelettronicaMysqlImplCollection) {
        this.copiaelettronicaMysqlImplCollection = copiaelettronicaMysqlImplCollection;
    }

    /**
     * 
     * @return
     */
    @XmlTransient
    public Collection<VolumeMysqlImpl> getVolumeMysqlImplCollection() {
        return volumeMysqlImplCollection;
    }

    /**
     * 
     * @param volumeMysqlImplCollection
     */
    public void setVolumeMysqlImplCollection(Collection<VolumeMysqlImpl> volumeMysqlImplCollection) {
        this.volumeMysqlImplCollection = volumeMysqlImplCollection;
    }

    /**
     * 
     * @param lingua
     */
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
        return "it.univaq.idw.librionline.model.impl.LibroMysqlImpl[ isbn=" + isbn + " ]";
    }

    @Override
    public Collection<Autore> getAutoreCollection() {
        return (Collection) getAutoreMysqlImplCollection();
    }

    @Override
    public Collection<Copiaelettronica> getCopiaelettronicaCollection() {
        return (Collection) getCopiaelettronicaMysqlImplCollection();
    }

    @Override
    public Collection<Libro> getLibroCollection() {
        return (Collection) getLibroMysqlImplCollection();
    }

    @Override
    public Collection<Libro> getLibroCollection1() {
        return (Collection) getLibroMysqlImplCollection1();
    }

    @Override
    public Lingua getLingua() {
        return (Lingua) lingua;
    }

    @Override
    public Collection<Tag> getTagCollection() {
        return (Collection) getTagMysqlImplCollection();
    }

    @Override
    public Collection<Volume> getVolumeCollection() {
        return (Collection) getVolumeMysqlImplCollection();
    }

    @Override
    public void setAutoreCollection(Collection<Autore> autoreCollection) {
        setAutoreMysqlImplCollection((Collection) autoreCollection);
    }

    @Override
    public void setCopiaelettronicaCollection(Collection<Copiaelettronica> copiaelettronicaCollection) {
        setCopiaelettronicaMysqlImplCollection((Collection) copiaelettronicaCollection);
    }

    @Override
    public void setLibroCollection(Collection<Libro> libroCollection) {
        setLibroMysqlImplCollection((Collection) libroCollection);
    }

    @Override
    public void setLibroCollection1(Collection<Libro> libroCollection1) {
        setLibroMysqlImplCollection1((Collection) libroCollection1);
    }

    @Override
    public void setLingua(Lingua lingua) {
       this.lingua = (LinguaMysqlImpl) lingua;
    }

    @Override
    public void setTagCollection(Collection<Tag> tagCollection) {
       setTagMysqlImplCollection((Collection) tagCollection);
    }

    @Override
    public void setVolumeCollection(Collection<Volume> volumeCollection) {
       setVolumeMysqlImplCollection((Collection) volumeCollection);
    }

    @Override
    public Date getDataIns() {
        return dataIns;
    }

    @Override
    public void setDataIns(Date dataIns) {
        this.dataIns = dataIns;
    }

    /*public Date getAnnoPubblicazione() {
        return annoPubblicazione;
    }

    public void setAnnoPubblicazione(Date annoPubblicazione) {
        this.annoPubblicazione = annoPubblicazione;
    }

    public Date getDataIns() {
        return dataIns;
    }

    public void setDataIns(Date dataIns) {
        this.dataIns = dataIns;
    }*/

    @Override
    public String getAnnoPubblicazione() {
        return annoPubblicazione;
    }

    @Override
    public void setAnnoPubblicazione(String annoPubblicazione) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
