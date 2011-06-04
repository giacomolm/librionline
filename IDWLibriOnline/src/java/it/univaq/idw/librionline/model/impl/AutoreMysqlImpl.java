/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 *    CREATE TABLE IF NOT EXISTS `autore` (
      `id` int(11) NOT NULL AUTO_INCREMENT,
      `nome` varchar(50) NOT NULL,
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;
 */

package it.univaq.idw.librionline.model.impl;

import it.univaq.idw.librionline.model.Autore;
import it.univaq.idw.librionline.model.Libro;
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
@Table(name = "autore")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AutoreMysqlImpl.findAll", query = "SELECT a FROM AutoreMysqlImpl a"),
    @NamedQuery(name = "AutoreMysqlImpl.findById", query = "SELECT a FROM AutoreMysqlImpl a WHERE a.id = :id"),
    @NamedQuery(name = "AutoreMysqlImpl.findByNome", query = "SELECT a FROM AutoreMysqlImpl a WHERE a.nome = :nome")})
public class AutoreMysqlImpl implements Serializable,Autore {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nome")
    private String nome;
    @ManyToMany(mappedBy = "autoreMysqlImplCollection")
    private Collection<LibroMysqlImpl> libroMysqlImplCollection;

    /**
     * 
     */
    public AutoreMysqlImpl() {
    }

    /**
     * 
     * @param id
     */
    public AutoreMysqlImpl(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @param id
     * @param nome
     */
    public AutoreMysqlImpl(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
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
    public String getNome() {
        return nome;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
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
        if (!(object instanceof AutoreMysqlImpl)) {
            return false;
        }
        AutoreMysqlImpl other = (AutoreMysqlImpl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.univaq.idw.librionline.model.impl.AutoreMysqlImpl[ id=" + id + " ]";
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
