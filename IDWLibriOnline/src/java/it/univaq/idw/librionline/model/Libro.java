/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.model;

import java.util.Collection;
import java.util.Date;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author giacomolm
 */
public interface Libro {

    /**
     * 
     * @param object
     * @return true se object fa riferimento allo stesso libro
     */
    @Override
    boolean equals(Object object);

    /**
     * Restituisce l'anno di pubblicazione del libro
     * @return intero rappresentante l'anno
     */
    Integer getAnnoPubblicazione();

    /**
     * Restituisce l'insieme degli autori del libro
     * @return collezione degli autori
     */
    @XmlTransient
    Collection<Autore> getAutoreCollection();

    /**
     * Restituisce l'insieme delle copie elettroniche associate al libro
     * @return collezione dell'oggetto copie elettroniche
     */
    @XmlTransient
    Collection<Copiaelettronica> getCopiaelettronicaCollection();

    /**
     * Restituisce l'editore del Libro 
     * @return Stringa rappresentante l'editore
     */
    String getEditore();

    /**
     * Restituisce il codice Isbn del libro, che viene utilizzato come chiave primaria per ciascun record
     * @return Stringa rappresentante il codice Isbn
     */
    String getIsbn();

    /**
     * Restituisce l'insieme dei libri che sono la traduzione di quel libro
     * @return collezione di libri
     */
    @XmlTransient
    Collection<Libro> getLibroCollection();

    /**
     * Utilizzato dalla libreria della persistenza
     * @return
     */
    @XmlTransient
    Collection<Libro> getLibroCollection1();

    /**
     * Restituisce la lingua del libro; l'oggetto Lingua contiene la siglia IT per rappresentare la lingua italiana
     * @return Lingua che codifica la lingua del libro
     */
    Lingua getLingua();

    /**
     * Restituisce la recensione del libro, cioè una piccola anticipazione del suo contenuto
     * @return Stringa rappresentante la recensione
     */
    String getRecensione();

    /**
     * Restituisce l'insieme di tag che caratterizzano il libro; in questo modo si permette ad esempio una sua ricerca in relazione a determinate parole chiave
     * @return Collezione di tag (Keyword) rappresentante il libro
     */
    @XmlTransient
    Collection<Tag> getTagCollection();

    /**
     * Restituisce il titolo del libro
     * @return Stringa rappresentante il titolo
     */
    String getTitolo();

    /**
     * Restituisce l'insieme delle copie fisiche relative quel libro, cioè quelle effettivamente presenti nella libreria
     * @return collezione di Volume
     */
    @XmlTransient
    Collection<Volume> getVolumeCollection();

    /**
     * 
     * @return
     */
    int hashCode();

    /**
     * Imposta l'anno di pubblicazione del libro
     * @param annoPubblicazione
     */
    void setAnnoPubblicazione(Integer annoPubblicazione);

    /**
     * Imposta l'insieme di autori che hanno scritto quel libro
     * @param autoreCollection collezione di Autori
     */
    void setAutoreCollection(Collection<Autore> autoreCollection);

    /**
     * Imposta l'insieme delle copie elettroniche riferite a quelo libro
     * @param copiaelettronicaCollection collezioni di CopiaElettronica
     */
    void setCopiaelettronicaCollection(Collection<Copiaelettronica> copiaelettronicaCollection);

    /**
     * Imposta il nome dell'editore
     * @param editore Stringa che indica il nome dell'editore
     */
    void setEditore(String editore);

    /**
     * Imposta la chiave del record indicante il libro a cui facciamo riferimento
     * @param isbn Stringa rappresentante il codice isbn
     */
    void setIsbn(String isbn);

    /**
     * Imposta l'insieme di libri che rappresentano la traduzione del libro a cui ci riferiamo
     * @param libroCollection Collezione di Libro
     */
    void setLibroCollection(Collection<Libro> libroCollection);

    /**
     * Funzione utilizzata internamente dalla persistenza
     * @param libroCollection1
     */
    void setLibroCollection1(Collection<Libro> libroCollection1);

    /**
     * Imposta la lingua con la quale è stato scritto il libro
     * @param lingua oggetto Lingua 
     */
    void setLingua(Lingua lingua);

    /**
     * Imposta la recensione del libro
     * @param recensione String rappresentante la recensione
     */
    void setRecensione(String recensione);

    /**
     * Imposta i Tag relativi al libro, per permettere la sua localizzazione
     * @param tagCollection Collezione di Tag
     */
    void setTagCollection(Collection<Tag> tagCollection);

    /**
     * Imposta il titolo del libro
     * @param titolo Stringa rappresentante il titolo
     */
    void setTitolo(String titolo);

    /**
     * Imposta l'insieme di volumi relativi il libro, cioè l'insieme delle copie fisiche appartententi alla libreria
     * @param volumeCollection Collezione di Volume
     */
    void setVolumeCollection(Collection<Volume> volumeCollection);

    /**
     * Restituisce la versione testuale dell'oggetto Libro
     * @return Stringa rappresentante l'oggetto libro 
     */
    String toString();
    
    /*
     * Restituisce la data di inserimento di un libro
     */
    Date getDataIns();
    
    /*
     * Imposta la data di inserimento di un libro, in modo tale che i libri 
     * più recenti possano essere più visibili rispetto ad altri
     */
    void setDataIns(Date dataIns);
}
