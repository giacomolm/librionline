/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.model.impl;

import it.univaq.idw.librionline.model.LibriOnLineDataLayer;
import it.univaq.idw.librionline.model.Autore;
import it.univaq.idw.librionline.model.Gruppo;
import it.univaq.idw.librionline.model.Libro;
import it.univaq.idw.librionline.model.Lingua;
import it.univaq.idw.librionline.model.User;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

/**
 *
 * @author giacomolm
 */
public class LibriOnLineDataLayerMysqlImpl implements LibriOnLineDataLayer {
    
    EntityManagerFactory factory;
    EntityManager manager;
    
    /**
     * Questo construttore permette l'instanziazione di un oggetto
     * con il quale possono essere effettuate le operazioni con il DB.
     * Ricordiamo che i costruttori di tutti le classi presenti nel model
     * hanno un modificatore di accesso privato, quindi l'unico modo per
     * interagire con esse è quello di di dichiarare un oggetto che si
     * interpone tra lo strato del modello e quello dell controllo.
     * Nel costruttore si definisco due variabili, una di tipo EntityManagerFactor
     * l'altra invece EntityManager, per l'utilizzo della persistenza.
     */
    public LibriOnLineDataLayerMysqlImpl(){
        factory = Persistence.createEntityManagerFactory("IDWLibriOnlinePU");
        manager = factory.createEntityManager();
    }
    
    /* Inserisce un libro nella libreria, controllondo anticipatamente
     * se questo è presente già nella libreria, tramite il metodo bookIsThis(Libro).
     * È importante notare come gli autori vengono passati come una collezione 
     * dell'oggetto autore: gli utilizzatori della funzione devono prima creare
     * una collezione di autori relativi il libro e succesivamente passarlo alla
     * funzione che effettua l'inserimento del libro.
     * @param l Libro da inserire
     * @return true se il l'inserimento è stato effettuato in maniera corretta
     */
    public boolean insertBook(String isbn, String titolo, String editore, int annopubbl, String recens, Lingua lingua,Collection<Autore> autori){
        if(!bookIsThis(isbn)){
            manager.getTransaction().begin();
            //Inseriamo i campi opportuni nel nuovo oggetto libro
            Libro l = new LibroMysqlImpl(isbn, titolo);
            l.setEditore(editore);
            l.setAnnoPubblicazione(annopubbl);
            l.setRecensione(recens);
            //Si è deciso di creare un entità separata per le lingue. Per questo motivo
            //dobbiamo recuperare la lingua dall'entità per impostarla nel libro
            try{
                l.setLingua((LinguaMysqlImpl)(manager.createNamedQuery("LinguaMysqlImpl.findByLingua").setParameter("lingua", lingua.getLingua()).getSingleResult()));      
            }catch(NoResultException e){
                //System.out.printlm("Problemi con le lingue");
            }
            
            //Procediamo con l'inserimento degli autori. Dobbiamo prima recuperare ciascun autore
            //dalla propria entità e poi aggiungerlo al libro che si vuole inserire
            try{
                l.setAutoreCollection(autori);
            }catch(NoResultException e){
                //Ci sono stati dei problemi nell'aggiunta degli autori
            }
            //manager.persist(l);
            manager.getTransaction().commit();
            return true;
        }
        else return false;
    }
    /* Il controllo di presenza viene effettuato in relazione all'isbn. 
     * @param isbn 
     * @return true se il libro passato come parametro è presente nella libreria
     */
    public boolean bookIsThis(String isbn){
        manager.getTransaction().begin();
        
        //Potrebbe essere eseguita questa istruzione molto più generica
        //Libro tl = manager.find(LibroMysqlImpl.class, l.getIsbn());
        
        // Ci serviamo però della namedQuery, dato che questa è stata definita
        // dalla libreria della persistenza.
        
        Libro tl;
        try{
            //Potrebbe non restituire alcun libro, quindi sollevare l'eccezione indicata
            tl = (Libro) manager.createNamedQuery("LibroMysqlImpl.findByIsbn").setParameter("isbn", isbn).getSingleResult();
        }catch(NoResultException e){
            tl=null;
        }
        manager.getTransaction().commit();
        if(tl==null) return false;
        else return true;
    }
    
    /* Questo metodo viene utilizzato nella fase di ricerca base: i visitatori
     * possono eseguire una ricerca rapida per trovare il libro desiderato. Abbiamo
     * assunto che questo tipo di ricerca si basi solo sul titolo del libro.
     * @param String titolo indicannte il titolo del libro che si vuole cercare
     * @return Collection<Libro> restituisce una collezione di libri, in quanto il titolo potrebbe
     * coincidere tra i diversi libri
     */
    @Override
    public Collection<Libro> simpleBookSearch(String titolo){
    
        manager.getTransaction().begin();
        Collection<Libro> bl=null;
        try{
            //Sfruttiamo la funzione messa a disposizione della libreria JPA che effettua la ricerca per titolo
            bl =  manager.createNamedQuery("LibroMysqlImpl.findByTitolo").setParameter("titolo", titolo).getResultList();
        }catch(NoResultException e){
            bl=null;
            System.out.println("Ci sono");
        }
        manager.getTransaction().commit();
        return bl;
    }
    
    /*
     * Il metodo prevede la registrazione degli utenti al sistema. Il metodo controlla
     * anticipatamente se esiste un altro utente iscritto con lo stesso username. Se ciò
     * accada, si interrompe la registrazione
     * @param username, password, nome, cognome, codfisc, indirizzo, citta, prov, cap
     * @return true se la registrazione è stata eseguita correttamente, false altrimenti
     */
    @Override
    public boolean insertUser(String username,String password,String email,String telefono,String nome,String cognome,String codfisc,String indirizzo,String citta,String prov,int cap,Gruppo gruppo){
        if(!isThisUsername(username)){
            manager.getTransaction().begin();
            //Instanzio l'oggetto utente che voglio inserire nel database
            User u = new UserMysqlImpl(null, username, password, nome, cognome, codfisc, indirizzo, citta, prov, cap);
            u.setEmail(email);
            u.setTelefono(telefono);
            //Imposto il gruppo di appartenenza dell'utente, definito dal biblotecario
            u.setGruppo(gruppo);
            //Memorizzo fisicamente l'utente sul database
            manager.persist(u);
            manager.getTransaction().commit();
            return true;
        }
        else return false;
    }
    
    /*
     * Questo metodo verifica se una particolare username è già presente nel DB.
     * @param username String rappresentante l'username
     * @retur true se l'username inserito è già stato utilizzato da un altro utente
     */
    @Override
    public boolean isThisUsername(String username){
        manager.getTransaction().begin();
        User u = null;
        try{
            //Verifico se un utente con quella username è già presente nel database
            u = (User) manager.createNamedQuery("UserMysqlImpl.findByUsername").setParameter("username", username).getSingleResult();
        }catch (NoResultException e){
            //Non esiste alcun utente con quell'username
        }
        manager.getTransaction().commit();
        if(u==null) return false;
        else return true;
    }
    
    /*
     * Questo metodo fornisce la funzionalità di login al sistema.
     * @param username e password necessari per l'autenticazione al sistema
     * @return User; se il logging va a buon fine viene restituito l'oggetto user
     * che si appena loggato, altrimenti restituisce null se c'è stato qualche 
     * problema nella procedura.
     */
    @Override
    public User login(String username, String password){
        manager.getTransaction().begin();
        User u = null;
        try{
            User temp = (User) manager.createNamedQuery("UserMysqlImpl.findByUsername").setParameter("username", username).getSingleResult();
            //Controllo se la password inserita è corretta, altrimento l'utente rimane nullo
            if(temp.getPassword().equals(password)){
                u = temp;
            }
        }catch (NoResultException e){
            //Non esiste alcun utente con quell'username
        }
        manager.getTransaction().commit();
        return u;
    }
}
