/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.model.impl;

import it.univaq.idw.librionline.model.Stato;
import it.univaq.idw.librionline.model.Volume;
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
@Table(name = "stato")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StatoMysqlImpl.findAll", query = "SELECT s FROM StatoMysqlImpl s"),
    @NamedQuery(name = "StatoMysqlImpl.findById", query = "SELECT s FROM StatoMysqlImpl s WHERE s.id = :id"),
    @NamedQuery(name = "StatoMysqlImpl.findByStato", query = "SELECT s FROM StatoMysqlImpl s WHERE s.stato = :stato")})
public class StatoMysqlImpl implements Serializable,Stato {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "stato")
    private String stato;
    @OneToMany(mappedBy = "stato")
    private Collection<VolumeMysqlImpl> volumeMysqlImplCollection;

    public StatoMysqlImpl() {
    }

    public StatoMysqlImpl(Integer id) {
        this.id = id;
    }

    public StatoMysqlImpl(Integer id, String stato) {
        this.id = id;
        this.stato = stato;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    @XmlTransient
    public Collection<VolumeMysqlImpl> getVolumeMysqlImplCollection() {
        return volumeMysqlImplCollection;
    }

    public void setVolumeMysqlImplCollection(Collection<VolumeMysqlImpl> volumeMysqlImplCollection) {
        this.volumeMysqlImplCollection = volumeMysqlImplCollection;
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
        if (!(object instanceof StatoMysqlImpl)) {
            return false;
        }
        StatoMysqlImpl other = (StatoMysqlImpl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.univaq.idw.librionline.model.impl.StatoMysqlImpl[ id=" + id + " ]";
    }

    @Override
    public Collection<Volume> getVolumeCollection() {
        return (Collection) getVolumeMysqlImplCollection();
    }

    @Override
    public void setVolumeCollection(Collection<Volume> volumeCollection) {
        setVolumeMysqlImplCollection((Collection) volumeCollection);
    }
    
}
