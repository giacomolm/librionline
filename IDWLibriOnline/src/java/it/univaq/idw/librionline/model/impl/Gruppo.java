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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "gruppo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gruppo.findAll", query = "SELECT g FROM Gruppo g"),
    @NamedQuery(name = "Gruppo.findById", query = "SELECT g FROM Gruppo g WHERE g.id = :id"),
    @NamedQuery(name = "Gruppo.findByGruppo", query = "SELECT g FROM Gruppo g WHERE g.gruppo = :gruppo")})
public class Gruppo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "gruppo")
    private String gruppo;
    @JoinTable(name = "gruppo_servizio", joinColumns = {
        @JoinColumn(name = "gruppo", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "servizio", referencedColumnName = "id")})
    @ManyToMany
    private Collection<Servizio> servizioCollection;
    @OneToMany(mappedBy = "gruppo")
    private Collection<User> userCollection;

    public Gruppo() {
    }

    public Gruppo(Integer id) {
        this.id = id;
    }

    public Gruppo(Integer id, String gruppo) {
        this.id = id;
        this.gruppo = gruppo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGruppo() {
        return gruppo;
    }

    public void setGruppo(String gruppo) {
        this.gruppo = gruppo;
    }

    @XmlTransient
    public Collection<Servizio> getServizioCollection() {
        return servizioCollection;
    }

    public void setServizioCollection(Collection<Servizio> servizioCollection) {
        this.servizioCollection = servizioCollection;
    }

    @XmlTransient
    public Collection<User> getUserCollection() {
        return userCollection;
    }

    public void setUserCollection(Collection<User> userCollection) {
        this.userCollection = userCollection;
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
        if (!(object instanceof Gruppo)) {
            return false;
        }
        Gruppo other = (Gruppo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.univaq.idw.librionline.model.impl.Gruppo[ id=" + id + " ]";
    }
    
}
