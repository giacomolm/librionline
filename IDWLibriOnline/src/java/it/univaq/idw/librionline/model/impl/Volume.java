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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "volume")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Volume.findAll", query = "SELECT v FROM Volume v"),
    @NamedQuery(name = "Volume.findById", query = "SELECT v FROM Volume v WHERE v.id = :id")})
public class Volume implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "libro", referencedColumnName = "isbn")
    @ManyToOne(optional = false)
    private Libro libro;
    @JoinColumn(name = "stato", referencedColumnName = "id")
    @ManyToOne
    private Stato stato;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "volume")
    private Collection<Prestito> prestitoCollection;

    public Volume() {
    }

    public Volume(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Stato getStato() {
        return stato;
    }

    public void setStato(Stato stato) {
        this.stato = stato;
    }

    @XmlTransient
    public Collection<Prestito> getPrestitoCollection() {
        return prestitoCollection;
    }

    public void setPrestitoCollection(Collection<Prestito> prestitoCollection) {
        this.prestitoCollection = prestitoCollection;
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
        if (!(object instanceof Volume)) {
            return false;
        }
        Volume other = (Volume) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.univaq.idw.librionline.model.impl.Volume[ id=" + id + " ]";
    }
    
}
