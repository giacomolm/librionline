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
import it.univaq.idw.librionline.model.Prestito;
import it.univaq.idw.librionline.model.Tag;
import it.univaq.idw.librionline.model.User;
import it.univaq.idw.librionline.model.Volume;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
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
    
    /**
     * Inserisce un libro nella libreria, controllondo anticipatamente
     * se questo è presente già nella libreria, tramite il metodo bookIsThis(Libro).
     * È importante notare come gli autori vengono passati come una collezione 
     * dell'oggetto autore: gli utilizzatori della funzione devono prima creare
     * una collezione di autori relativi il libro e succesivamente passarlo alla
     * funzione che effettua l'inserimento del libro.
     * @param l Libro da inserire
     * @return true se il l'inserimento è stato effettuato in maniera corretta
     */
    public boolean insertBook(String isbn, String titolo, String editore, Date annopubbl, String recens, Lingua lingua,Collection<Autore> autori){
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
    /**
     * Il controllo di presenza viene effettuato in relazione all'isbn. 
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
    
    /**
     * Questo metodo viene utilizzato nella fase di ricerca base: i visitatori
     * possono eseguire una ricerca rapida per trovare il libro desiderato. Abbiamo
     * assunto che questo tipo di ricerca si basi sul titolo del libro, sull'editore,
     * sul suo isbn, e sul nome degli autori
     * @param String titolo indicannte il titolo del libro che si vuole cercare
     * @return Collection<Libro> restituisce una collezione di libri, in quanto il titolo potrebbe
     * coincidere tra i diversi libri
     */
    @Override
    public List<Libro> simpleBookSearch(String titolo){
        if (!titolo.equals("")){
            //manager.getTransaction().begin();

            List<Libro> bl= (List) new ArrayList<LibroMysqlImpl>();
            
                //bl = manager.createNamedQuery("LibroMysqlImpl.findByIsbn").setParameter("isbn",titolo).getResultList();                
                //bl.add(searchByIsbn(titolo)); 
                //System.out.println("ci sono");
                bl.addAll(searchByTitle(titolo));
                
                try{
                    bl.addAll(manager.createNamedQuery("LibroMysqlImpl.findByEditore").setParameter("editore",titolo).getResultList());
                }catch (NoResultException e){
            
                }
                bl.addAll(searchByAutori(titolo));
                
            //manager.getTransaction().commit();
            return bl;
        }else return null;
    }
    
    /**
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
            //Imposto il gruppo di appartenenza dell'utente, definito dal biblotecario se è egli stesso a registrarlo, altrimenti viene assegnato quello di default
            u.setGruppo(gruppo);
            //Memorizzo fisicamente l'utente sul database
            manager.persist(u);
            manager.getTransaction().commit();
            return true;
        }
        else return false;
    }
    
    /**
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
    
    /**
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
    
    /**
     * Questa funzione restituisce il gruppo relativo un particolare tipo. Ad esempio
     * se presente nella entità gruppo un tipo Amministrazione, passando questa stringa
     * come parametro viene restituito l'oggetto gruppo riferito.
     * @param tipo del gruppo che si vuole ricevere
     * @return gruppo di appartenza del tipo
     */
    
    @Override
    public Gruppo getGruppo(String tipo){
        manager.getTransaction().begin();
        Gruppo g = null;
        try{
            g = (Gruppo) manager.createNamedQuery("GruppoMysqlImpl.findByGruppo").setParameter("gruppo",tipo).getSingleResult();
        }
        catch(NoResultException e){
        
        }
        manager.getTransaction().commit();
        return g;
    }
    
    /**
     * Ricerca per tag: la ricerca consiste nella individuazione dei libri che
     * appartengono a quell'insieme di tag. Si procede con la ricerca dei libri 
     * associati al primo tag della lista; poi si verifica se questi appartengono
     * anche a quello successivo, raffinando progressivamente la lista.
     * @param String indicante la lista di tag separati da uno spazio
     * @return List di libri appartenente a quell'insieme di tag
    */
    @Override
    public List<Libro> searchByTags(String tc){
        manager.getTransaction().begin();
        String[] lista = tc.split(" ");
        
        List<Libro> cl = (List) new ArrayList<LibroMysqlImpl>();
        List<Tag> tl = (List) new ArrayList<TagMysqlImpl>();
        int k=0;
        for(int i=0; i<lista.length; i++){
            try{
                tl.add((Tag) manager.createNamedQuery("TagMysqlImpl.findByTag").setParameter("tag", lista[i]).getSingleResult());
            }
            catch(NoResultException e){

            }
        }
        cl = (List) tl.get(0).getLibroCollection();
        
        for(int i=1; i<tl.size(); i++){
            try{
                List<Libro> res = (List) new ArrayList<LibroMysqlImpl>();
                Collection<Libro> temp = (List) tl.get(i).getLibroCollection();
                for ( Iterator it = temp.iterator(); it.hasNext(); ) {
                    LibroMysqlImpl element = (LibroMysqlImpl) it.next();
                    if(cl.contains(element)) res.add(element);
                }
                cl =  res;
            }
            catch(NoResultException e){

            }
        }
        manager.getTransaction().commit();
        return cl;
    }

    /**
     * Il metodo ricerca un libro in base al suo isbn. Restituisce il libro, se viene
     * trovato, altrimenti torna null.
     * @param isbn Stringa indicante l'isbn
     * @return Libro, se esiste, relativo all'isbn indicato; altrimenti restituisce null
     */
    @Override
    public Libro searchByIsbn(String isbn) {
        manager.getTransaction().begin();
        
        Libro l = null;
        try{
            l = (Libro) manager.createNamedQuery("LibroMysqlImpl.findByIsbn").setParameter("isbn",isbn).getSingleResult();
        }
        catch(NoResultException e){
            //nessun libro trovato
        }

        manager.getTransaction().commit();
        return l;
    }
    
    /**
     * Questo tipo di ricerca fa riferimento esclusivamente al titolo del libro:
     * vengono considerati tutti i titoli che matchano con il titolo completo o 
     * parte di esso.
     * @param String titolo rappresentante il titolo completo o parte di esso
     * @return List di libri rappresentante il risultato della ricerca
     */
    public List<Libro> searchByTitle(String titolo){  
     if (!titolo.equals("")){
            manager.getTransaction().begin();
            //Piccola funzione di editing per le stringhe in modo da poterle utilizzare con il LIKE
            String[] lista = titolo.split(" ");
            String ric = "%"+lista[0]+"%";
            //System.out.println(lista[0]);
            for (int i=1; i<lista.length; i++){
                //System.out.println(lista[i]);
                ric += lista[i]+"%";
            }
            //System.out.println(ric);
            List<Libro> bl=null;
            try{
                //Sfruttiamo la funzione messa a disposizione della libreria JPA che effettua la ricerca per titolo
                bl =  manager.createQuery("SELECT l FROM LibroMysqlImpl l WHERE l.titolo LIKE :keyword").setParameter("keyword", ric).getResultList();                                
            }catch(NoResultException e){
                //bl=null;
                //System.out.println("Ci sono");
            }
            manager.getTransaction().commit();
            return bl;
        }else return null;
    }
    /**
     * 
     */
    @Override
    public List<Libro> searchByAutori(String autori){  
     if (!autori.equals("")){
            manager.getTransaction().begin();
            //Piccola funzione di editing per le stringhe in modo da poterle utilizzare con il LIKE
            String[] lista = autori.split(" ");
            String ric = "%"+lista[0]+"%";
            //System.out.println(lista[0]);
            for (int i=1; i<lista.length; i++){
                //System.out.println(lista[i]);
                ric += lista[i]+"%";
            }
            List<Autore> bl=null;
            try{
                //Sfruttiamo la funzione messa a disposizione della libreria JPA che effettua la ricerca per titolo
                bl =  manager.createQuery("SELECT a FROM AutoreMysqlImpl a WHERE a.cognome LIKE :keyword").setParameter("keyword", ric).getResultList();                                
            }catch(NoResultException e){
                //bl=null;
                
            }
            
            List<Libro> cl = (List) new ArrayList<LibroMysqlImpl>();
            for(int i=0; i<bl.size(); i++){
            try{
                //Ottengo l'elemtento, cioè l'autore i-esimo della lista
  
                Collection<Libro> temp = (List) bl.get(i).getLibroCollection();
                
                //Estraggo tutti quanti libri che ha scritto
                for ( Iterator it = temp.iterator(); it.hasNext(); ) {
                    LibroMysqlImpl element = (LibroMysqlImpl) it.next();
                    if(!cl.contains(element)) cl.add(element);
                }
            }
            catch(NoResultException e){

            }
        }
            manager.getTransaction().commit();
            return cl;
        }else return null;
    }
    
    /**
     * Questa funziona si dimostra essere necessaria in quanto potrebbero esistere
     * due o più autori con lo stesso nome: in questo modo possiamo accedere alla
     * lista dei libri scritti dall'autore desiderato tramite il suo id in modo tale da evitare
     * delle inesattezze
     * @param id dell'autore
     * @return List<Libro> scritti dall'autore rappresento da quell'id
     */
    @Override
    public List<Libro> searchLibriAutoriById(int id){
        manager.getTransaction().begin();
        List<Libro> l = null;
        try{
            Autore a = (Autore) manager.createNamedQuery("AutoreMysqlImpl.findById").setParameter("id",id).getSingleResult();
            l = (List) a.getLibroCollection();
        }
        catch(NoResultException e){

        }
        manager.getTransaction().commit();
        return l;
    }
    
    /**
     * 
     * @param isbn
     * @return 
     */
    public int getNumeroCopie(String isbn){
        Libro l = searchByIsbn(isbn);
        return l.getVolumeCollection().size();
    }
    
    public int getNumeroCopieDisponibili(String isbn){
        Libro l = searchByIsbn(isbn);
        List<Volume> lv =(List) l.getVolumeCollection();
        for ( Iterator it = lv.iterator(); it.hasNext(); ) {
            VolumeMysqlImpl vol = (VolumeMysqlImpl) it.next();
            Collection<Prestito> cp =  vol.getPrestitoCollection();
            for ( Iterator ite = cp.iterator(); ite.hasNext(); ) {
                PrestitoMysqlImpl prestito = (PrestitoMysqlImpl) ite.next();
                //prestito.
            }
        }
        return 0;
    }
}
