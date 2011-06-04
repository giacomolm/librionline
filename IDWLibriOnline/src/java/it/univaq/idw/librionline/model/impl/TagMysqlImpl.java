/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * 
CREATE TABLE IF NOT EXISTS `tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tag` varchar(25) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

 */
package it.univaq.idw.librionline.model.impl;

import it.univaq.idw.librionline.model.Libro;
import it.univaq.idw.librionline.model.Tag;
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
@Table(name = "tag")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TagMysqlImpl.findAll", query = "SELECT t FROM TagMysqlImpl t"),
    @NamedQuery(name = "TagMysqlImpl.findById", query = "SELECT t FROM TagMysqlImpl t WHERE t.id = :id"),
    @NamedQuery(name = "TagMysqlImpl.findByTag", query = "SELECT t FROM TagMysqlImpl t WHERE t.tag = :tag")})
public class TagMysqlImpl implements Serializable, Tag {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "tag")
    private String tag;
    @ManyToMany(mappedBy = "tagMysqlImplCollection")
    private Collection<LibroMysqlImpl> libroMysqlImplCollection;

    /**
     * 
     */
    public TagMysqlImpl() {
    }

    /**
     * 
     * @param id
     */
    public TagMysqlImpl(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @param id
     * @param tag
     */
    public TagMysqlImpl(Integer id, String tag) {
        this.id = id;
        this.tag = tag;
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
    public String getTag() {
        return tag;
    }

    @Override
    public void setTag(String tag) {
        this.tag = tag;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TagMysqlImpl)) {
            return false;
        }
        TagMysqlImpl other = (TagMysqlImpl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.univaq.idw.librionline.model.impl.TagMysqlImpl[ id=" + id + " ]";
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
