/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.model.impl;

import it.univaq.idw.librionline.model.User;
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
@Table(name = "user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id"),
    @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username"),
    @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password"),
    @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
    @NamedQuery(name = "User.findByTelefono", query = "SELECT u FROM User u WHERE u.telefono = :telefono"),
    @NamedQuery(name = "User.findByNome", query = "SELECT u FROM User u WHERE u.nome = :nome"),
    @NamedQuery(name = "User.findByCognome", query = "SELECT u FROM User u WHERE u.cognome = :cognome"),
    @NamedQuery(name = "User.findByCodiceFiscale", query = "SELECT u FROM User u WHERE u.codiceFiscale = :codiceFiscale"),
    @NamedQuery(name = "User.findByIndirizzo", query = "SELECT u FROM User u WHERE u.indirizzo = :indirizzo"),
    @NamedQuery(name = "User.findByCitta", query = "SELECT u FROM User u WHERE u.citta = :citta"),
    @NamedQuery(name = "User.findByProvincia", query = "SELECT u FROM User u WHERE u.provincia = :provincia"),
    @NamedQuery(name = "User.findByCap", query = "SELECT u FROM User u WHERE u.cap = :cap")})
public class UserMysqlImpl implements Serializable, User {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "telefono")
    private String telefono;
    @Basic(optional = false)
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @Column(name = "cognome")
    private String cognome;
    @Basic(optional = false)
    @Column(name = "codiceFiscale")
    private String codiceFiscale;
    @Basic(optional = false)
    @Column(name = "indirizzo")
    private String indirizzo;
    @Basic(optional = false)
    @Column(name = "citta")
    private String citta;
    @Basic(optional = false)
    @Column(name = "provincia")
    private String provincia;
    @Basic(optional = false)
    @Column(name = "cap")
    private int cap;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<PrestitoMysqlImpl> prestitoCollection;
    @JoinColumn(name = "gruppo", referencedColumnName = "id")
    @ManyToOne
    private GruppoMysqlImpl gruppo;

    public UserMysqlImpl() {
    }

    public UserMysqlImpl(Integer id) {
        this.id = id;
    }

    public UserMysqlImpl(Integer id, String username, String password, String nome, String cognome, String codiceFiscale, String indirizzo, String citta, String provincia, int cap) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.indirizzo = indirizzo;
        this.citta = citta;
        this.provincia = provincia;
        this.cap = cap;
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
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getTelefono() {
        return telefono;
    }

    @Override
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String getCognome() {
        return cognome;
    }

    @Override
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    @Override
    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    @Override
    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    @Override
    public String getIndirizzo() {
        return indirizzo;
    }

    @Override
    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    @Override
    public String getCitta() {
        return citta;
    }

    @Override
    public void setCitta(String citta) {
        this.citta = citta;
    }

    @Override
    public String getProvincia() {
        return provincia;
    }

    @Override
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    @Override
    public int getCap() {
        return cap;
    }

    @Override
    public void setCap(int cap) {
        this.cap = cap;
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
    public GruppoMysqlImpl getGruppo() {
        return gruppo;
    }

    @Override
    public void setGruppo(GruppoMysqlImpl gruppo) {
        this.gruppo = gruppo;
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
        if (!(object instanceof UserMysqlImpl)) {
            return false;
        }
        UserMysqlImpl other = (UserMysqlImpl) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.univaq.idw.librionline.model.impl.User[ id=" + id + " ]";
    }
    
}
