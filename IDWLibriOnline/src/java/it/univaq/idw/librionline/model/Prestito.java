/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.model;

import java.util.Date;

/**
 *
 * @author giacomolm
 */
public interface Prestito {

    /**
     * Verifica se i due oggetti, quello a cui facciamo riferimento e quello indicato dal parametro object, fanno riferimento allo stesso prestito
     * @param object
     * @return true se fanno riferimento allo stesso prestito
     */
    boolean equals(Object object);

    /**
     * Restituisce la data del prestito, nel formato definito dall'oggetto Date
     * @return Date dell'inizio del prestito
     */
    Date getDataPrestito();

    /**
     * Restituisce la data della restituzione, nel formato definito dall'oggetto Date
     * @return Date indicante la restituzione
     */
    Date getDataRestituzione();

    /**
     * Ritorna l'id che contraddistingue il prestito
     * @return Integer rappresentante la chiave
     */
    Integer getId();

    /**
     * Restituisce un booleano indicante se un determinato libro prestato è rientrato nella libreria
     * @return true se il libro riferito da quel prestitio è stato restituito
     */
    boolean getRestituito();

    /**
     * Restituisce l'utente, attraverso il tipo User 
     * @return User che ha sottoscritto il prestito
     */
    User getUser();

    /**
     * Restituisce la copia fisica oggetto del prestito
     * @return Volume indicante la copia oggetto del prestito
     */
    Volume getVolume();

    /**
     * 
     * @return
     */
    int hashCode();

    /**
     * Imposta la data iniziale del prestito
     * @param dataPrestito Date indicante l'inizio del prestito
     */
    void setDataPrestito(Date dataPrestito);

    /**
     * Imposta la data per la quale il libro viene restituito
     * @param dataRestituzione Date indicante la data di restituzione
     */
    void setDataRestituzione(Date dataRestituzione);

    /**
     * Imposta la chiave del record indicante il prestito
     * @param id Integer indicante la chiave
     */
    void setId(Integer id);

    /**
     * Definisce lo stato del libro, cioè se esso non è stato ancora restituito oppure se è rientrato nella libreria
     * @param restituito boolean indicante lo stato del libro
     */
    void setRestituito(boolean restituito);

    /**
     * Imposta l'utente che ha sottoscritto il prestito
     * @param user User sottoscrittore del prestito
     */
    void setUser(User user);

    /**
     * Imposta il Volume che è materialmente uscito dalla libreria
     * @param volume Volume indicante la copia fisica
     */
    void setVolume(Volume volume);

    /**
     * Restituisce la versione testuale dell'oggetto Prestito in considerazione
     * @return String rappresentante l'oggetto Prestito
     */
    String toString();
    
    /**
     * Calcoliamo la data di presunta restituzione di un prestito, a partire
     * dalla data di inizio prestito e dalla sua durata
     * @return la data di presunta restituzione di un volume
     */
    Date getDataPresuntaRestituzione();
    
    /**
     * Questo metodo provvede al controllo della data di restituzione del prestito
     * @return true se il prestito è scaduto
     */
    public boolean isExpired();
    
}
