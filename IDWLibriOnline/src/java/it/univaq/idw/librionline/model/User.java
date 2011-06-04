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

    /**
     * Verifica se l'oggetto object e fa riferimento allo stesso User
     * @param object
     * @return true se fa riferimento allo stesso User
     */
    boolean equals(Object object);

    /**
     * Ritorna il CAP (Codice di avviamento postale) di residenza dell'utente
     * @return intero rappresentante il CAP
     */
    int getCap();

    /**
     * Ritorna la città di residenza dell'utente
     * @return Stringa rappresentante la città
     */
    String getCitta();

    /**
     * Ritorna il codice fiscale dell'utente referenziato
     * @return Stringa rappresentante il codice fiscale
     */
    String getCodiceFiscale();

    /**
     * Restituisce il cognome dell'utente
     * @return Stringa relativa al cognome
     */
    String getCognome();

    /**
     * Restituisce l'indirizzo di posta elettronica dell'utente
     * @return Stringa relativa all'indirizzo
     */
    String getEmail();

    /**
     * Restituisce il gruppo di appartenenza dell'utente
     * @return Gruppo di appartenenza
     */
    Gruppo getGruppo();

    /**
     * Restituisce l'id dell'utente relativo al record in cui è presente
     * @return Intero relativo alla chiave
     */
    Integer getId();

    /**
     * Restituisce l'indirizzo di residenza dell'utente
     * @return Stringa relativa al indirizzo 
     */
    String getIndirizzo();

    /**
     * Restituisce il nome dell'utente
     * @return Stringa relativa al nome
     */
    String getNome();

    /**
     * Restituisce la password di accesso al portale dell'utente
     * @return Stringa relativa al password
     */
    String getPassword();

    /**
     * Restituisce l'insieme di prestiti effettuati dall'utente
     * @return Collezione di Prestito
     */
    @XmlTransient
    Collection<Prestito> getPrestitoCollection();

    /**
     * Restituisce la provincia di residenza dell'utente
     * @return Stringa relativa la provincia
     */
    String getProvincia();

    /**
     * Restituisce il numero telefonico di proprietà dell'utente
     * @return Stringa relativa al numero telefonico
     */
    String getTelefono();

    /**
     * Restituisce l'username con il quale l'utente effettua l'accesso alla libreria on line
     * @return Stringa relativa all'username
     */
    String getUsername();

    /**
     * 
     * @return
     */
    int hashCode();

    /**
     * Imposta il CAP di residenza dell'utente
     * @param cap intero rappresentante il CAP
     */
    void setCap(int cap);

    /**
    * Imposta città di residenza dell'utente 
     * @param citta Stringa relativa la città
     */
    void setCitta(String citta);

    /**
     * Imposta il codice fiscale dell'utente a cui ci riferiamo
     * @param codiceFiscale Stringa relativa al codice fiscale
     */
    void setCodiceFiscale(String codiceFiscale);

    /**
     * Imposta il cognome dell'utente a cui ci riferiamo
     * @param cognome Stringa rappresentante il cognome
     */
    void setCognome(String cognome);

    /**
     * Imposta l'indirizzo di posta elettronica dell'utente a cui ci riferiamo
     * @param email Stringa relativo l'indirizzo
     */
    void setEmail(String email);

    /**
     * Imposta il Gruppo di appartenenza dell'utente a cui ci riferiamo
     * @param gruppo Gruppo di appartenenza
     */
    void setGruppo(Gruppo gruppo);

    /**
     * Imposta l'identificato dell'utente a cui ci riferiamo nel record dell'entità
     * @param id
     */
    void setId(Integer id);

    /**
     * Imposta l'indirizzo di residenza dell'utente
     * @param indirizzo Stringa relativo l'indirizzo
     */
    void setIndirizzo(String indirizzo);

    /**
     * Imposta il nome dell'utente
     * @param nome Strinfa relativo il nome
     */
    void setNome(String nome);

    /**
     * Imposta la password con la quale l'utente accede alla libreria online
     * @param password Stringa 
     */
    void setPassword(String password);

    /**
     * Imposta l'insieme dei prestiti effettuati dell'utente; permette l'aggiunta, l'eliminazione o la modifica dell'insieme
     * @param prestitoCollection Collezione riferito al prestito
     */
    void setPrestitoCollection(Collection<Prestito> prestitoCollection);

    /**
     * Imposta la provincia di residenza dell'utente
     * @param provincia Stringa relativa la provincia
     */
    void setProvincia(String provincia);

    /**
     * Imposta il numero telefonico di proprietà dell'utente
     * @param telefono Stringa relativa il numero
     */
    void setTelefono(String telefono);

    /**
     * Imposta  'username di proprietà dell'utente che permette l'accesso alla libreria on line
     * @param username Stringa
     */
    void setUsername(String username);

    /**
     * Restituisce la versione testuale del particolare oggetto User
     * @return Stringa riferita all'oggetto
     */
    String toString();
    
}
