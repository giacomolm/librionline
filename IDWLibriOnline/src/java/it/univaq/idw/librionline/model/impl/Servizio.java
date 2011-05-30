/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.model.impl;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author giacomolm
 */
@Entity
@Table(name = "servizio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Servizio.findAll", query = "SELECT s FROM Servizio s"),
    @NamedQuery(name = "Servizio.findById", query = "SELECT s FROM Servizio s WHERE s.id = :id"),
    @NamedQuery(name = "Servizio.findByServizio", query = "SELECT s FROM Servizio s WHERE s.servizio = :servizio")})
public class Servizio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "servizio")
    private String servizio;
    @ManyToMany(mappedBy = "servizioCollection")
    private Collection<Gruppo> gruppoCollection;

    public Servizio() {
    }

    public Servizio(Integer id) {
        this.id = id;
    }

    public Servizio(Integer id, String servizio) {
        this.id = id;
        this.servizio = servizio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getServizio() {
        return servizio;
    }

    public void setServizio(String servizio) {
        this.servizio = servizio;
    }

    @XmlTransient
    public Collection<Gruppo> getGruppoCollection() {
        return gruppoCollection;
    }

    public void setGruppoCollection(Collection<Gruppo> gruppoCollection) {
        this.gruppoCollection = gruppoCollection;
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
        if (!(object instanceof Servizio)) {
            return false;
        }
        Servizio other = (Servizio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.univaq.idw.librionline.model.impl.Servizio[ id=" + id + " ]";
    }
    
}
