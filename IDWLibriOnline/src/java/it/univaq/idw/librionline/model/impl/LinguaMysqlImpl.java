/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.model.impl;

import it.univaq.idw.librionline.model.Libro;
import it.univaq.idw.librionline.model.Lingua;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "lingua")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LinguaMysqlImpl.findAll", query = "SELECT l FROM LinguaMysqlImpl l"),
    @NamedQuery(name = "LinguaMysqlImpl.findById", query = "SELECT l FROM LinguaMysqlImpl l WHERE l.id = :id"),
    @NamedQuery(name = "LinguaMysqlImpl.findByLingua", query = "SELECT l FROM LinguaMysqlImpl l WHERE l.lingua = :lingua")})
public class LinguaMysqlImpl implements Serializable,Lingua {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "lingua")
    private String lingua;
    @OneToMany(mappedBy = "lingua")
    private Collection<LibroMysqlImpl> libroMysqlImplCollection;

    public LinguaMysqlImpl() {
    }

    public LinguaMysqlImpl(Integer id) {
        this.id = id;
    }

    public LinguaMysqlImpl(Integer id, String lingua) {
        this.id = id;
        this.lingua = lingua;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLingua() {
        return lingua;
    }

    public void setLingua(String lingua) {
        this.lingua = lingua;
    }

    @XmlTransient
    public Collection<LibroMysqlImpl> getLibroMysqlImplCollection() {
        return libroMysqlImplCollection;
    }

    public void setLibroMysqlImplCollection(Collection<LibroMysqlImpl> libroMysqlImplCollection) {
        this.libroMysqlImplCollection = libroMysqlImplCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LinguaMysqlImpl)) {
            return false;
        }
        LinguaMysqlImpl other = (LinguaMysqlImpl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.univaq.idw.librionline.model.impl.LinguaMysqlImpl[ id=" + id + " ]";
    }

    @Override
    public Collection<Libro> getLibroCollection() {
        return (Collection) getLibroMysqlImplCollection();
    }

    @Override
    public void setLibroCollection(Collection<Libro> libroCollection) {
         setLibroMysqlImplCollection((Collection) libroCollection);
    }
    
}
