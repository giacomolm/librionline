/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.model;


import java.util.Collection;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author giacomolm
 */
public interface User {

    boolean equals(Object object);

    int getCap();

    String getCitta();

    String getCodiceFiscale();

    String getCognome();

    String getEmail();

    Gruppo getGruppo();

    Integer getId();

    String getIndirizzo();

    String getNome();

    String getPassword();

    @XmlTransient
    Collection<Prestito> getPrestitoCollection();

    String getProvincia();

    String getTelefono();

    String getUsername();

    int hashCode();

    void setCap(int cap);

    void setCitta(String citta);

    void setCodiceFiscale(String codiceFiscale);

    void setCognome(String cognome);

    void setEmail(String email);

    void setGruppo(Gruppo gruppo);

    void setId(Integer id);

    void setIndirizzo(String indirizzo);

    void setNome(String nome);

    void setPassword(String password);

    void setPrestitoCollection(Collection<Prestito> prestitoCollection);

    void setProvincia(String provincia);

    void setTelefono(String telefono);

    void setUsername(String username);

    String toString();
    
}
