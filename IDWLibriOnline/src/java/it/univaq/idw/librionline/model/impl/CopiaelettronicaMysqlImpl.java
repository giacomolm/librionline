/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.model.impl;

import it.univaq.idw.librionline.model.Copiaelettronica;
import it.univaq.idw.librionline.model.Libro;
import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author giacomolm
 */
@Entity
@Table(name = "copiaelettronica")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CopiaelettronicaMysqlImpl.findAll", query = "SELECT c FROM CopiaelettronicaMysqlImpl c"),
    @NamedQuery(name = "CopiaelettronicaMysqlImpl.findById", query = "SELECT c FROM CopiaelettronicaMysqlImpl c WHERE c.id = :id"),
    @NamedQuery(name = "CopiaelettronicaMysqlImpl.findByMimetype", query = "SELECT c FROM CopiaelettronicaMysqlImpl c WHERE c.mimetype = :mimetype"),
    @NamedQuery(name = "CopiaelettronicaMysqlImpl.findByUrl", query = "SELECT c FROM CopiaelettronicaMysqlImpl c WHERE c.url = :url")})
public class CopiaelettronicaMysqlImpl implements Serializable, Copiaelettronica {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "mimetype")
    private String mimetype;
    @Basic(optional = false)
    @Column(name = "url")
    private String url;
    @JoinColumn(name = "libro", referencedColumnName = "isbn")
    @ManyToOne(optional = false)
    private LibroMysqlImpl libro;

    public CopiaelettronicaMysqlImpl() {
    }

    public CopiaelettronicaMysqlImpl(Integer id) {
        this.id = id;
    }

    public CopiaelettronicaMysqlImpl(Integer id, String mimetype, String url) {
        this.id = id;
        this.mimetype = mimetype;
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public void setLibro(LibroMysqlImpl libro) {
        this.libro = libro;
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
        if (!(object instanceof CopiaelettronicaMysqlImpl)) {
            return false;
        }
        CopiaelettronicaMysqlImpl other = (CopiaelettronicaMysqlImpl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.univaq.idw.librionline.model.impl.CopiaelettronicaMysqlImpl[ id=" + id + " ]";
    }

    @Override
    public Libro getLibro() {
        return (Libro) libro;
    }

    @Override
    public void setLibro(Libro libro) {
        setLibro(libro);
    }
    
}
