/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.model.impl;

import it.univaq.idw.librionline.model.Gruppo;
import it.univaq.idw.librionline.model.Prestito;
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
    @NamedQuery(name = "UserMysqlImpl.findAll", query = "SELECT u FROM UserMysqlImpl u"),
    @NamedQuery(name = "UserMysqlImpl.findById", query = "SELECT u FROM UserMysqlImpl u WHERE u.id = :id"),
    @NamedQuery(name = "UserMysqlImpl.findByUsername", query = "SELECT u FROM UserMysqlImpl u WHERE u.username = :username"),
    @NamedQuery(name = "UserMysqlImpl.findByPassword", query = "SELECT u FROM UserMysqlImpl u WHERE u.password = :password"),
    @NamedQuery(name = "UserMysqlImpl.findByEmail", query = "SELECT u FROM UserMysqlImpl u WHERE u.email = :email"),
    @NamedQuery(name = "UserMysqlImpl.findByTelefono", query = "SELECT u FROM UserMysqlImpl u WHERE u.telefono = :telefono"),
    @NamedQuery(name = "UserMysqlImpl.findByNome", query = "SELECT u FROM UserMysqlImpl u WHERE u.nome = :nome"),
    @NamedQuery(name = "UserMysqlImpl.findByCognome", query = "SELECT u FROM UserMysqlImpl u WHERE u.cognome = :cognome"),
    @NamedQuery(name = "UserMysqlImpl.findByCodiceFiscale", query = "SELECT u FROM UserMysqlImpl u WHERE u.codiceFiscale = :codiceFiscale"),
    @NamedQuery(name = "UserMysqlImpl.findByIndirizzo", query = "SELECT u FROM UserMysqlImpl u WHERE u.indirizzo = :indirizzo"),
    @NamedQuery(name = "UserMysqlImpl.findByCitta", query = "SELECT u FROM UserMysqlImpl u WHERE u.citta = :citta"),
    @NamedQuery(name = "UserMysqlImpl.findByProvincia", query = "SELECT u FROM UserMysqlImpl u WHERE u.provincia = :provincia"),
    @NamedQuery(name = "UserMysqlImpl.findByCap", query = "SELECT u FROM UserMysqlImpl u WHERE u.cap = :cap")})
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
    private Collection<PrestitoMysqlImpl> prestitoMysqlImplCollection;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public int getCap() {
        return cap;
    }

    public void setCap(int cap) {
        this.cap = cap;
    }

    @XmlTransient
    public Collection<PrestitoMysqlImpl> getPrestitoMysqlImplCollection() {
        return prestitoMysqlImplCollection;
    }

    public void setPrestitoMysqlImplCollection(Collection<PrestitoMysqlImpl> prestitoMysqlImplCollection) {
        this.prestitoMysqlImplCollection = prestitoMysqlImplCollection;
    }

    public GruppoMysqlImpl getGruppo() {
        return gruppo;
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
        return "it.univaq.idw.librionline.model.impl.UserMysqlImpl[ id=" + id + " ]";
    }

    @Override
    public Collection<Prestito> getPrestitoCollection() {
        return (Collection) getPrestitoMysqlImplCollection();
    }

    @Override
    public void setGruppo(Gruppo gruppo) {
        this.gruppo = (GruppoMysqlImpl) gruppo;
    }

    @Override
    public void setPrestitoCollection(Collection<Prestito> prestitoCollection) {
        setPrestitoMysqlImplCollection((Collection) prestitoCollection);
    }
    
}
