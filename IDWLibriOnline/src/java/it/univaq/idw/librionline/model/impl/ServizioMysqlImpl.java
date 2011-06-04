/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * 
CREATE TABLE IF NOT EXISTS `servizio` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `servizio` varchar(25) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;
 */
package it.univaq.idw.librionline.model.impl;

import it.univaq.idw.librionline.model.Gruppo;
import it.univaq.idw.librionline.model.Servizio;
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
    @NamedQuery(name = "ServizioMysqlImpl.findAll", query = "SELECT s FROM ServizioMysqlImpl s"),
    @NamedQuery(name = "ServizioMysqlImpl.findById", query = "SELECT s FROM ServizioMysqlImpl s WHERE s.id = :id"),
    @NamedQuery(name = "ServizioMysqlImpl.findByServizio", query = "SELECT s FROM ServizioMysqlImpl s WHERE s.servizio = :servizio")})
public class ServizioMysqlImpl implements Serializable, Servizio {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "servizio")
    private String servizio;
    @ManyToMany(mappedBy = "servizioMysqlImplCollection")
    private Collection<GruppoMysqlImpl> gruppoMysqlImplCollection;

    /**
     * 
     */
    public ServizioMysqlImpl() {
    }

    /**
     * 
     * @param id
     */
    public ServizioMysqlImpl(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @param id
     * @param servizio
     */
    public ServizioMysqlImpl(Integer id, String servizio) {
        this.id = id;
        this.servizio = servizio;
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
    public String getServizio() {
        return servizio;
    }

    @Override
    public void setServizio(String servizio) {
        this.servizio = servizio;
    }

    /**
     * 
     * @return
     */
    @XmlTransient
    public Collection<GruppoMysqlImpl> getGruppoMysqlImplCollection() {
        return gruppoMysqlImplCollection;
    }

    /**
     * 
     * @param gruppoMysqlImplCollection
     */
    public void setGruppoMysqlImplCollection(Collection<GruppoMysqlImpl> gruppoMysqlImplCollection) {
        this.gruppoMysqlImplCollection = gruppoMysqlImplCollection;
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
        if (!(object instanceof ServizioMysqlImpl)) {
            return false;
        }
        ServizioMysqlImpl other = (ServizioMysqlImpl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.univaq.idw.librionline.model.impl.ServizioMysqlImpl[ id=" + id + " ]";
    }

    @Override
    public Collection<Gruppo> getGruppoCollection() {
        return (Collection) getGruppoMysqlImplCollection();
    }

    @Override
    public void setGruppoCollection(Collection<Gruppo> gruppoCollection) {
        setGruppoMysqlImplCollection((Collection) gruppoCollection);
    }
    
}
