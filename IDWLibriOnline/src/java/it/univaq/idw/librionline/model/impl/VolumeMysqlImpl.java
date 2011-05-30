/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.model.impl;

import it.univaq.idw.librionline.model.Volume;
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
public class VolumeMysqlImpl implements Serializable, Volume {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "libro", referencedColumnName = "isbn")
    @ManyToOne(optional = false)
    private LibroMysqlImpl libro;
    @JoinColumn(name = "stato", referencedColumnName = "id")
    @ManyToOne
    private StatoMysqlImpl stato;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "volume")
    private Collection<PrestitoMysqlImpl> prestitoCollection;

    public VolumeMysqlImpl() {
    }

    public VolumeMysqlImpl(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public LibroMysqlImpl getLibro() {
        return libro;
    }

    @Override
    public void setLibro(LibroMysqlImpl libro) {
        this.libro = libro;
    }

    @Override
    public StatoMysqlImpl getStato() {
        return stato;
    }

    @Override
    public void setStato(StatoMysqlImpl stato) {
        this.stato = stato;
    }

    @XmlTransient
    @Override
    public Collection<PrestitoMysqlImpl> getPrestitoCollection() {
        return prestitoCollection;
    }

    @Override
    public void setPrestitoCollection(Collection<PrestitoMysqlImpl> prestitoCollection) {
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
        if (!(object instanceof VolumeMysqlImpl)) {
            return false;
        }
        VolumeMysqlImpl other = (VolumeMysqlImpl) object;
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
