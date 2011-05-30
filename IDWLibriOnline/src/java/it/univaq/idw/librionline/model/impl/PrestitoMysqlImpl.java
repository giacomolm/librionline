/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.model.impl;

import it.univaq.idw.librionline.model.Prestito;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author giacomolm
 */
@Entity
@Table(name = "prestito")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Prestito.findAll", query = "SELECT p FROM Prestito p"),
    @NamedQuery(name = "Prestito.findById", query = "SELECT p FROM Prestito p WHERE p.id = :id"),
    @NamedQuery(name = "Prestito.findByDataPrestito", query = "SELECT p FROM Prestito p WHERE p.dataPrestito = :dataPrestito"),
    @NamedQuery(name = "Prestito.findByDataRestituzione", query = "SELECT p FROM Prestito p WHERE p.dataRestituzione = :dataRestituzione"),
    @NamedQuery(name = "Prestito.findByRestituito", query = "SELECT p FROM Prestito p WHERE p.restituito = :restituito")})
public class PrestitoMysqlImpl implements Serializable, Prestito {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "dataPrestito")
    @Temporal(TemporalType.DATE)
    private Date dataPrestito;
    @Basic(optional = false)
    @Column(name = "dataRestituzione")
    @Temporal(TemporalType.DATE)
    private Date dataRestituzione;
    @Basic(optional = false)
    @Column(name = "restituito")
    private boolean restituito;
    @JoinColumn(name = "user", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private UserMysqlImpl user;
    @JoinColumn(name = "volume", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private VolumeMysqlImpl volume;

    public PrestitoMysqlImpl() {
    }

    public PrestitoMysqlImpl(Integer id) {
        this.id = id;
    }

    public PrestitoMysqlImpl(Integer id, Date dataPrestito, Date dataRestituzione, boolean restituito) {
        this.id = id;
        this.dataPrestito = dataPrestito;
        this.dataRestituzione = dataRestituzione;
        this.restituito = restituito;
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
    public Date getDataPrestito() {
        return dataPrestito;
    }

    @Override
    public void setDataPrestito(Date dataPrestito) {
        this.dataPrestito = dataPrestito;
    }

    @Override
    public Date getDataRestituzione() {
        return dataRestituzione;
    }

    @Override
    public void setDataRestituzione(Date dataRestituzione) {
        this.dataRestituzione = dataRestituzione;
    }

    @Override
    public boolean getRestituito() {
        return restituito;
    }

    @Override
    public void setRestituito(boolean restituito) {
        this.restituito = restituito;
    }

    @Override
    public UserMysqlImpl getUser() {
        return user;
    }

    @Override
    public void setUser(UserMysqlImpl user) {
        this.user = user;
    }

    @Override
    public VolumeMysqlImpl getVolume() {
        return volume;
    }

    @Override
    public void setVolume(VolumeMysqlImpl volume) {
        this.volume = volume;
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
        if (!(object instanceof PrestitoMysqlImpl)) {
            return false;
        }
        PrestitoMysqlImpl other = (PrestitoMysqlImpl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.univaq.idw.librionline.model.impl.Prestito[ id=" + id + " ]";
    }
    
}
