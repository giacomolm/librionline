/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.model.impl;

import it.univaq.idw.librionline.model.LibriOnLineDataLayer;
import it.univaq.idw.librionline.model.Autore;
import it.univaq.idw.librionline.model.Copiaelettronica;
import it.univaq.idw.librionline.model.Gruppo;
import it.univaq.idw.librionline.model.Libro;
import it.univaq.idw.librionline.model.Lingua;
import it.univaq.idw.librionline.model.Prestito;
import it.univaq.idw.librionline.model.Stato;
import it.univaq.idw.librionline.model.Tag;
import it.univaq.idw.librionline.model.User;
import it.univaq.idw.librionline.model.Volume;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    @Override
    public boolean insertBook(String isbn, String titolo, String editore, String annopubbl, String recens, int id_lingua,String[] id_autori, String[] id_tag, int n_copie, int durata_max,int id_stato){
        if(!bookIsThis(isbn)){
            List<Autore> autori = new ArrayList<Autore>();
            List<Tag> tags = new ArrayList<Tag>();
            boolean res = true;
            manager.getTransaction().begin();
            //Inseriamo i campi opportuni nel nuovo oggetto libro
            Libro l = new LibroMysqlImpl(isbn, titolo);
            if(editore != null) l.setEditore(editore);
            l.setAnnoPubblicazione(annopubbl);
            if(recens != null) l.setRecensione(recens);
            l.setDataIns(new Date());
            //Si è deciso di creare un entità separata per le lingue. Per questo motivo
            //dobbiamo recuperare la lingua dall'entità per impostarla nel libro
            try{
                l.setLingua((LinguaMysqlImpl)(manager.createNamedQuery("LinguaMysqlImpl.findById").setParameter("id", id_lingua).getSingleResult()));      
            }catch(NoResultException e){
                //System.out.printlm("Problemi con le lingue");
            }
            
            //Procediamo con l'inserimento degli autori. Dobbiamo prima recuperare ciascun autore
            //dalla propria entità e poi aggiungerlo al libro che si vuole inserire
            try{
                for(int i=0; i<id_autori.length; i++){
                    autori.add((AutoreMysqlImpl)manager.createNamedQuery("AutoreMysqlImpl.findById").setParameter("id", Integer.parseInt(id_autori[i])).getSingleResult());
                }
                l.setAutoreCollection(autori);
                
                //Facciamo la stessa cosa con i tag
                for(int i=0; i<id_tag.length; i++){
                    tags.add((TagMysqlImpl)manager.createNamedQuery("TagMysqlImpl.findById").setParameter("id", Integer.parseInt(id_tag[i])).getSingleResult());
                }
                l.setTagCollection(tags);
            }catch(NoResultException e){
                //Ci sono stati dei problemi nell'aggiunta degli autori o nell'aggiunta dei tag
                res = false;
            }
           
            //Memorizzo effettivamente sul db le modifiche
            manager.persist(l);
            manager.getTransaction().commit();
            
            //Devo ricordare di impostare anche i volumi
            Libro li = searchByIsbn(isbn);
            for(int i=0; i<n_copie; i++) insertVolume(li, durata_max, id_stato);

            return res;
        }
        else return false;
    }
    
    /**
     * La funzione permette di modificare il libro che indichiamo con il parametro
     * isbn.
     * @param isbn
     * @param titolo
     * @param editore
     * @param annopubbl
     * @param recens
     * @param id_lingua
     * @param id_autori
     * @param id_tag
     * @param n_copie
     * @param durata_max
     * @param id_stato
     * @return true se la modifica è andata a buon fine.
     */
    @Override
    public boolean modificaLibro(String isbn, String titolo, String editore, String annopubbl, String recens, int id_lingua,String[] id_autori, String[] id_tag){
        if(bookIsThis(isbn)){
            Libro l = searchByIsbn(isbn);
            
            boolean res = true;
            manager.getTransaction().begin();
            //Inseriamo i campi opportuni nel nuovo oggetto libro
            l.setTitolo(titolo);
            if(editore != null) l.setEditore(editore);
            l.setAnnoPubblicazione(annopubbl);
            
            if(recens != null) l.setRecensione(recens);            
            
            //Facciamo la stessa cosa con i tag
            try{
                Collection<Tag> tags = new ArrayList<Tag>();
                for(int i=0; i<id_tag.length; i++){
                    Tag temp = (Tag) manager.createNamedQuery("TagMysqlImpl.findById").setParameter("id", Integer.parseInt(id_tag[i])).getSingleResult();
                    tags.add(temp);
                }
                l.setTagCollection(tags);
            }
            catch(NoResultException e){
                res = false;
            }
            
            //Si è deciso di creare un entità separata per le lingue. Per questo motivo
            //dobbiamo recuperare la lingua dall'entità per impostarla nel libro
            /*try{
                l.setLingua((LinguaMysqlImpl)(manager.createNamedQuery("LinguaMysqlImpl.findById").setParameter("id", id_lingua).getSingleResult()));      
            }catch(NoResultException e){
                //out.printlm("Problemi con le lingue");
            }*/                  

            
            //Procediamo con l'inserimento degli autori. Dobbiamo prima recuperare ciascun autore
            //dalla propria entità e poi aggiungerlo al libro che si vuole inserire

            
            //Memorizzo effettivamente sul db le modifiche
            if(res) manager.persist(l);
            manager.getTransaction().commit();
            
            manager.getTransaction().begin();
            try{
                Collection<Autore> ac = new ArrayList<Autore>();
                for(int i=0; i<id_autori.length; i++){
                    Autore temp = (Autore)manager.createNamedQuery("AutoreMysqlImpl.findById").setParameter("id", Integer.parseInt(id_autori[i])).getSingleResult();
                    ac.add(temp);
                }
                //Gestione Errore di mutuo accesso
                l.setAutoreCollection(ac);

            }catch(NoResultException e){
                //Ci sono stati dei problemi nell'aggiunta degli autori
                res = false;
            }
            if(res) manager.persist(l);
            manager.getTransaction().commit();
            
            return res;
        }
        else return false;
    }
    
    /**
     * La funzione permette di modificare il libro che indichiamo con il parametro
     * isbn.
     * @param isbn del libro che vogliamo modificare
     */
    @Override
     public boolean eliminaLibro(String isbn){
          if(bookIsThis(isbn)){
              //Cerchiamo il libro che vogliamo occupare
              Libro l = searchByIsbn(isbn);
              manager.getTransaction().begin();
              //effetuiamo la rimozione vera e propria
              manager.remove(l);
              manager.getTransaction().commit();
              return true;
          }
          else return false;
     }
 
    /**
     * Il metodo restituisce un volume, se presente, a partire dal suo id
     * @param id_volume che vogliamo trovare
     * @return Volume se presente , altrimenti null
     */
    @Override
    public Volume getVolume(int id_volume){
        Volume v = null;
        manager.getTransaction().begin();
        try{
            v = (Volume) manager.createNamedQuery("VolumeMysqlImpl.findById").setParameter("id", id_volume).getSingleResult();
        }
        catch(NoResultException e){
         
        }
        manager.getTransaction().commit();
        return v;
    }
    
    /**
     * Il metodo permette l'inserimento di un volume a partire da un libro 
     * @param l Libro al quale vogliamo inserire i volumi
     * @param durata_max indica la durata di default del prestito
     * @param id_stato stato dei volumi che vogliamo inserire
     * @return true se l'aggiunta dei volumi viene fatta in maniera corretta
     */
    @Override
    public boolean insertVolume(Libro l, int durata_max,int id_stato){
        boolean res = true;
        Volume vol = new VolumeMysqlImpl(null);
        //Imposto il libro al nuovo volume creato
        vol.setLibro(l);
        ((VolumeMysqlImpl)vol).setDurataMax(durata_max);
        manager.getTransaction().begin();
        try{
            //cerco l'oggetto stato a partire dal suo id
            Stato st = (Stato) manager.createNamedQuery("StatoMysqlImpl.findById").setParameter("id", id_stato).getSingleResult();
            //imposto lo stato sul volume
            vol.setStato(st);
            //memorizzo il volume fisicamente sul db
            manager.persist(vol);
        }
        catch(NoResultException e){
            //se qualcosa è andata storta, avviso il chiamante
            //e.printStackTrace();
            res = false;
        }
        
        manager.getTransaction().commit();
        return res;
    }
    
    /**
     * Il metodo permette la modifica di un volume relativo un libro 
     * @param id_volume
     * @param durata_max indica la durata di default del prestito
     * @param id_stato stato dei volumi che vogliamo inserire
     * @return true se la modifica dei volumi viene fatta in maniera corretta
     */
    @Override
    public boolean modificaVolume(int id_volume, int durata_max,int id_stato){
        boolean res = true;
        Volume v = getVolume(id_volume);
        if(v!=null){
            manager.getTransaction().begin();
            ((VolumeMysqlImpl)v).setDurataMax(durata_max);
       
            try{
                //cerco l'oggetto stato a partire dal suo id
                Stato st = (Stato) manager.createNamedQuery("StatoMysqlImpl.findById").setParameter("id", id_stato).getSingleResult();
                //imposto lo stato sul volume
                v.setStato(st);
                //memorizzo il volume fisicamente sul db
                manager.persist(v);
            }
            catch(NoResultException e){
                //se qualcosa è andata storta, il flag prende false
                res = false;
            }
        }
        manager.getTransaction().commit();
        return res;
    }   
   
    /**
     * Il metodo permette la rimozione di un volume relativo un libro 
     * @param id_volume
     * @return true se la rimozione del volume viene fatta in maniera corretta
     */
    @Override
    public boolean eliminaVolume(int id_volume){
   
        Volume v = getVolume(id_volume);
        if (v!=null){
              manager.getTransaction().begin();
              //effetuiamo la rimozione vera e propria
              manager.remove(v);
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
                    List<Libro> le = manager.createNamedQuery("LibroMysqlImpl.findByEditore").setParameter("editore",titolo).getResultList();
                    List<Libro> temp = bookContained(bl, le);
                    le.removeAll(temp);
                    bl.addAll(le);
                }catch (NoResultException e){
            
                }
                //Allo stesso modo di come abbiamo fatto in precedenza, chiamiamo
                //la funzione searchByAutori per ottenere tutti i libri che contengono
                //quell'autore indicato; successivamente si chiama la funzione 
                //bookContained in modo da evitare di aggiungere quelli presenti
                //Infine si aggiungono i restanti
                List<Libro> lt = searchByAutori(titolo);
                List<Libro> diff = bookContained(bl,lt);
                lt.removeAll(diff);
                bl.addAll(lt);
                
            //manager.getTransaction().commit();
            return bl;
        }else return null;
    }
    
    /**
     * Funzione di ricerca avanzata
     * @param titolo
     * @param tag
     * @param autori
     * @param isbn
     * @return 
     */
    @Override
    public List<Libro> advancedSearch(String titolo, String tag, String autori, String isbn){
    
           List<Libro> res = new ArrayList<Libro>();
        
           //Effettuo una ricerca per isbn, se questo campo non è vuoto
           //notiamo che se viene effettuata una ricerca per isbn non continuiamo
           //con la ricerca, per migliorare l'efficenza della ricerca
           if(!isbn.equals("")){
               Libro l = searchByIsbn(isbn);
               if(l!=null){
                   res.add(l);
               }
               return res;
           }
           //effettuo una ricerca per titolo .Se ci sono dei libri che corrispondono
           //li aggiungo alla lista dei libri creata precedentemente
           if(!titolo.equals("")){
               String[] lista = titolo.split(" ");
               res.addAll(searchByTitle(lista[0]));
                   for(int i=1; i<lista.length; i++){
                        List<Libro> temp = searchByTitle(lista[i]);
                        res = bookContained(res, temp);
                   }
               if(!autori.equals("")){
                   lista = autori.split(" ");
                   for(int i=0; i<lista.length; i++){
                        List<Libro> temp = searchByAutori(lista[i]);
                        res = bookContained(res, temp);
                   }
               }
               if(!res.isEmpty())
                  if(!tag.equals("")){
                    lista = tag.split(" ");
                    for(int i=0; i<lista.length; i++){
                        List<Libro> temp =searchByTags(tag);
                        res = bookContained(res, temp);
                    }
                  }
           }
           //Cerco la lista di libri scritta da tutti gli autori indicati
           else if(!autori.equals("")){
                String[] lista = autori.split(" ");
                res.addAll(searchByAutori(lista[0]));
                for(int i=1; i<lista.length; i++){
                    List<Libro> temp = searchByAutori(lista[i]);
                    res = bookContained(res, temp);
                } 
                if(!res.isEmpty())
                   if(!tag.equals("")){
                    lista = tag.split(" ");
                    for(int i=0; i<lista.length; i++){
                        List<Libro> temp =searchByTags(tag);
                        res = bookContained(res, temp);
                    }
                   }
           }
           else if(!tag.equals("")){
                String[] lista = tag.split(" ");
                res.addAll(searchByTags(lista[0]));
                for(int i=1; i<lista.length; i++){
                    List<Libro> temp =searchByTags(tag);
                    res = bookContained(res, temp);
                }
           }
           return res;
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
     * Il metofo permette di modificare i dati relativi ad un utente, con riferimento
     * agli stessi parametri parametri per la creazione dell'utente stesso
     * @param id_user
     * @return true se la modifica va a buon fine
     */
    public boolean modificaUser(int id_user, String username,String password,String email,String telefono,String nome,String cognome,String codfisc,String indirizzo,String citta,String prov,int cap,Gruppo gruppo){
        User u=getUser(id_user);
        if(u != null){
            manager.getTransaction().begin();
            //Instanzio l'oggetto utente che voglio inserire nel database
            u.setEmail(email);
            u.setTelefono(telefono);
            //Imposto il gruppo di appartenenza dell'utente, definito dal biblotecario se è egli stesso a registrarlo, altrimenti viene assegnato quello di default
            u.setGruppo(gruppo);
            //Memorizzo fisicamente l'utente sul database
            manager.persist(u);
            manager.getTransaction().commit();
            return true;
        }
        return false;
    }
    
    public boolean removeUser(int id_user){
        User u=getUser(id_user);
        if(u != null){
            manager.getTransaction().begin();
            //Memorizzo fisicamente l'utente sul database
            manager.remove(u);
            manager.getTransaction().commit();
            return true;
        }
        return false;
    }
    
    /**
     * Il metodo restituisce l'oggetto user in modo tale da poter fornire delle 
     * informazioni utili su di esso.
     * @param id_user
     * @return oggetto User relativo quell'id
     */
    @Override
    public User getUser(int id_user){
        manager.getTransaction().begin();
        User u = null;
        try{
            //Verifico se un utente con quella username è già presente nel database
            u = (User) manager.createNamedQuery("UserMysqlImpl.findById").setParameter("id", id_user).getSingleResult();
        }catch (NoResultException e){
            //Non esiste alcun utente con quell'username
        }
        manager.getTransaction().commit();
        return u;
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
    public Gruppo getGruppo(int gruppo){
        manager.getTransaction().begin();
        Gruppo g = null;
        try{
            g = (Gruppo) manager.createNamedQuery("GruppoMysqlImpl.findById").setParameter("id",gruppo).getSingleResult();
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
        if(!tl.isEmpty()){
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
    @Override
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
            
            List<Autore> bl= new ArrayList<Autore>();
            for(int i=0; i<lista.length; i++){
                try{
                    //Sfruttiamo la funzione messa a disposizione della libreria JPA che effettua la ricerca per autore
                    List<Autore> temp =  manager.createQuery("SELECT a FROM AutoreMysqlImpl a WHERE a.cognome LIKE :keyword OR a.nome LIKE :keyword").setParameter("keyword", lista[i]).getResultList();                                
                    for(Iterator it = temp.iterator(); it.hasNext();){
                        Autore a = (Autore )it.next();
                        if(!bl.contains(a)) bl.add(a);
                    }
                }catch(NoResultException e){
                    //bl=null;

                }
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
     * Restituisce il numero di copie, quindi il numero dei volumi, appartententi alla
     * libreria relativi all'isbn passato come parametro
     * @param isbn
     * @return int indicante il numero delle copie che appartengono alla libreria
     */
    @Override
    public int getNumeroCopie(String isbn){
        if(bookIsThis(isbn)){
            Libro l = searchByIsbn(isbn);
            return l.getVolumeCollection().size();
        }
        else return -1;
    }
    
    /**
     * Restituisco il numero delle copie di un libro disponibili al prestito, cioè
     * il numero delle copiè che per quel determinato momento possono essere prestate
     * @param isbn indicante quello del libro
     * @return int indicante il numero delle copie disponibili
     */
    @Override
    public int getNumeroCopieDisponibili(String isbn){
        if(bookIsThis(isbn)){
            Libro l = searchByIsbn(isbn);
            
            int numdisp = getNumeroCopie(isbn);
            List<Volume> lv =(List) l.getVolumeCollection();
            for ( Iterator it = lv.iterator(); it.hasNext(); ) {
                VolumeMysqlImpl vol = (VolumeMysqlImpl) it.next();
                Collection<Prestito> cp =  vol.getPrestitoCollection();
                for ( Iterator ite = cp.iterator(); ite.hasNext(); ) {
                    PrestitoMysqlImpl prestito = (PrestitoMysqlImpl) ite.next();
                    if(!prestito.getRestituito()) numdisp--;
                }
            }
            
            return numdisp;
        }
        else return -1;
    }
    
    /**
     * Restituisco la data di restituzione del volume più vicina, in modo tale 
     * da informare chi di interesse quando potrà essere reperibile un determinato
     * volume
     * @param  del libro interessato
     * @return data indicante la restituzione più prossima della prima copia
     */
    @Override
    public Date getProssimoData(String isbn){
        
        long dataInit;
        VolumeMysqlImpl vol;
        long minTemp=0;
        
        Date min= null;
        
        if(getNumeroCopieDisponibili(isbn)==0){
            //Avvio la ricerca del libro per isbn
            Libro l = searchByIsbn(isbn);
            //Ricevo la lista dei volumi, cioè delle copie concrete associate a quel libro
            List<Volume> lv =(List) l.getVolumeCollection();
            //inizializzo a quella attuale per esigenze di inizializzazione
            min = new Date();
            manager.getTransaction().begin();
            Iterator it = lv.iterator();
            if(it.hasNext()){
                  
                
                  vol = (VolumeMysqlImpl) it.next();
                  
                  //itero sui prestiti esercitati su quel libro per inizializzare la data della restituzione più vicina
                  Collection<Prestito> cp =  vol.getPrestitoCollection();
                  for ( Iterator ite = cp.iterator(); ite.hasNext(); ) {
                    PrestitoMysqlImpl prestito = (PrestitoMysqlImpl) ite.next();
                    //nel caso individuo il record relativo al prestito in corso, memorizzo la data
                    if(!prestito.getRestituito()){
                        min = prestito.getDataPrestito();
                        minTemp = min.getTime()+(((long) vol.getDurataMax())*86400000);
                    }
                  }
                  
                  //itero sugli altri volumi cercando quello con data di restituzione più prossima
                  for ( it = lv.iterator(); it.hasNext(); ) {
                    vol = (VolumeMysqlImpl) it.next();
                    //inizializzo la durata del prestito a quella del volume attuale
                    long durata = (long) vol.getDurataMax();
                    cp =  vol.getPrestitoCollection();
                    for ( Iterator ite = cp.iterator(); ite.hasNext(); ) {
                        PrestitoMysqlImpl prestito = (PrestitoMysqlImpl) ite.next();
                        //Sono sul record relativo al prestito in corso
                        if(!prestito.getRestituito()){
                            //calcolo la durata del prestito relativo al volume attuale
                            dataInit = prestito.getDataPrestito().getTime()+(durata*86400000);
                            //confronto se la data di restituzione attuale è minore di quella minima finora calcolata
                            if(dataInit<(minTemp)){
                                //aggiorno i campi
                                min = prestito.getDataPrestito();
                                minTemp = dataInit;
                            }
                        }        
                    }

                  }
            }
            manager.getTransaction().commit();
        }
        
        return new Date(minTemp);
    }
    
    /**
     * Il metodo ha il compito di cercare un insieme di un certo numero di libri,
     * in questo caso ne assumiamo 10, che sono stati aggiunti più di recente nella
     * biblioteca
     * @return Lista di libri aggiunta di recente
     */
    @Override
    public List<Libro> getLastAdded(){
    
        List<Libro> ll = null;
        manager.getTransaction().begin();
        try{
            ll = manager.createQuery("SELECT l FROM LibroMysqlImpl l ORDER BY l.dataIns DESC").getResultList();
        }
        catch(NoResultException e){
            //Nessun libro trovato
        }
        
        manager.getTransaction().commit();
        if(ll.size()>10)
            return ll.subList(0, 9);
        else return ll.subList(0, ll.size());
    }
    
    /**
     * Il metodo ha come compito quello di selezionare l'insieme di libri che sono
     * stati maggiormente prestati. L'insieme è stato impostato ad una cardinalità
     * si 10 elementi. 
     * @return lista dei libri maggiormente prestati
     */
    @Override
    public List<Libro> getMostProvided(){
    
        manager.getTransaction().begin();
        List<Libro> ll = null;
        Map<String,Integer> lc = null;
        //Prelevo tutti i libri presenti nella libreria
        
        try{
            ll = manager.createQuery("SELECT l FROM LibroMysqlImpl l").getResultList();
        }
        catch(NoResultException e){
            //Nessun libro trovato
        }
        //scorro ciascun libro con l'intenzione di contare quanti prestiti sono
        //stati eseguiti per ciascuno di essi
        Iterator it = ll.iterator();
        lc = (Map) new HashMap(10);
            int i=9;
        while(it.hasNext()){
            
            //Se i libri presenti nella libreria sono maggiori di 10, devo selezionare quali tra questi devo restituire

             //Mi riferrico con l al libro corrente nella lista di libri che sto esaminando
             Libro l = ((Libro) it.next());

                 if(lc.size()<5){
                    Collection<Volume> volcol = l.getVolumeCollection();
                    Iterator<Volume> iv;
                    //Calcolo la somma dei prestiti per quel libro
                    int sum=0;
                    for(iv = volcol.iterator(); iv.hasNext();){
                        sum+=((Volume) iv.next()).getPrestitoCollection().size();
                    }
                    if(sum>0)lc.put(l.getIsbn(), new Integer(sum));
                 }
                 else{
                     Integer temp = ((Integer)lc.get(getMinPrestiti(lc)));
                     //System.out.println(getMinPrestiti(lc)+" val "+temp);
                     //se il numero di prestiti riferiti a l sono maggiori di quelli di temp
                     //allora devo procedere con il replace di questo
                     Collection<Volume> volcol = l.getVolumeCollection();
                     Iterator<Volume> iv;
                     int sum=0;
                     for(iv = volcol.iterator(); iv.hasNext();){
                            sum+=((Volume) iv.next()).getPrestitoCollection().size();
                        }

                     Integer sumTemp = new Integer(sum);
                     //System.out.println(sumTemp+" --"+temp+" "+sumTemp.compareTo(temp));
                     if(sumTemp.compareTo(temp)== 1){      
                         lc.remove(getMinPrestiti(lc));
                         lc.put(l.getIsbn(), sumTemp);
                     }
             }

        }
        manager.getTransaction().commit();
        List<Libro> l = (List) new ArrayList<LibroMysqlImpl>();
        //devo convertire le chiavi presenti della mappa in libri concreti
        Set keys = lc.keySet();
        for(Iterator iter=keys.iterator(); iter.hasNext();){
            String k = (String) iter.next();
            l.add(searchByIsbn(k));
        }
        return l;
    }
    

    /**
     * Questo metodo è ausiliare al metodo getMostProvided() ed il suo compito è
     * di ritornare l'indice del volume nella lista di libri (passata come parametro)
     * che ha subito meno prestiti
     * @param list
     * @return int indicante l'indice del libro con meno prestiti nella lista
     */
    public String getMinPrestiti(Map m){
    
        int i;
        String min;
        Set set = m.keySet();
        
        //Lista degli 
        Object[] list = new Object[set.size()];
        list = set.toArray();
        if(list.length>1){ 
            min =(String) list[0];
            String key;
            for(i=1; i<list.length; i++){
                key = (String) list[i];
                System.out.print((Integer)m.get(key)+" "+((Integer)m.get(min))+" "+ ((Integer)m.get(key)).compareTo((Integer)m.get(min)));
                if(((Integer)m.get(key)).compareTo((Integer)m.get(min))==-1) min = key;
                System.out.println(" res "+m.get(min));
            }
        }
        else min = (String) list[0];
        return min;
    }
    
    /**
     * il metodo si occupa di ricerca qual'è l'id del gruppo che appartiene ad
     * un determina utente
     * @param un String username
     * @return intero indicante l'id del gruppo
     */
    @Override
    public int getGruppoByUsername(String un){
        int id = -1;
        User u;
        //Verifico se l'username passato è presente
        if(isThisUsername(un)){
             manager.getTransaction().begin();
             //Cerco lo user con quell'usernamenull
             u = (User) manager.createNamedQuery("UserMysqlImpl.findByUsername").setParameter("username", un).getSingleResult();
             id = u.getGruppo().getId();
             manager.getTransaction().commit();
        }
        return id;
    }
    
    /**
     * Verifico se l'username passato come parametro appartiene al gruppo 
     * amministratore
     * @param un stringa username
     * @return true se lo user appartiene all'amministrazione
     */
    @Override
    public boolean isAdmin(String un){
        boolean res = false;
        User u;
        //Verifico se l'username passato è presente
        if(isThisUsername(un)){
             manager.getTransaction().begin();
             //Cerco lo user con quell'username
             u = (User) manager.createNamedQuery("UserMysqlImpl.findByUsername").setParameter("username", un).getSingleResult();
             //Verifico se l'user è amministratore tramite l'id del suo gruppo (se amministratore -> id_user_gruppo == 1)
             if (u.getGruppo().getId()==1) res = true;
             manager.getTransaction().commit();
        }
        return res;
    }
    
    /**
     * Il metodo si occupa di restituire la lista di libri presi in prestito da parte di un determinato
     * utente. Nel caso l'utente non abbia alcun prestito attivo, il metodo restituisce
     * alcuna lista referenziata (NULL).
     * @param username dell'utente di cui si vogliono conoscere il libri da lui detenuti
     * @return lista di libri non ancora restituiti 
     */
    @Override
    public List<Prestito> getPrestitiAttivi(String username){
        String un = username;
        User u;
        List<Prestito> lp = null;
        if(isThisUsername(un)){
             manager.getTransaction().begin();
             //Cerco lo user con quell'username
             u = (User) manager.createNamedQuery("UserMysqlImpl.findByUsername").setParameter("username", un).getSingleResult();
             //Verifico se ha preso qualche libro in prestito non ancora restituito
             try{
                 lp = manager.createQuery("SELECT p FROM PrestitoMysqlImpl p WHERE p.user = :user AND p.restituito = false").setParameter("user", (UserMysqlImpl) u).getResultList();
             }
             catch(NoResultException e){
                 //Nessun risultato
             }
             manager.getTransaction().commit();
        }
        //ritorna la lista dei prestiti attivi 
        return lp;
    }
    
     /**
     * Il metodo restituisce la lista dei libri presi in prestito in passato da un
     * utente, e quindi già restituiti. Nel caso l'utente non abbia preso in prestito 
     * alcun libro, il metodo restituisce alcuna lista referenziata (NULL).
     * @param username dell'utente di cui si vogliono conoscere il libri presi in prestito
     * @return lista di libri presi in prestito e già restituiti
     */
    @Override
    public List<Prestito> getPrestitiPassati(String username){
        String un = username;
        User u;
        List<Prestito> lp = null;
        if(isThisUsername(un)){
             manager.getTransaction().begin();
             //Cerco lo user con quell'username
             u = (User) manager.createNamedQuery("UserMysqlImpl.findByUsername").setParameter("username", un).getSingleResult();
             //Cerco tutti i volumi che sono stati presi in prestito dall'utente e restituiti
             try{
                 lp = manager.createQuery("SELECT p FROM PrestitoMysqlImpl p WHERE p.user = :user AND p.restituito = true").setParameter("user", (UserMysqlImpl) u).getResultList();
             }
             catch(NoResultException e){
                 //Nessun risultato
             }
             manager.getTransaction().commit();
        }
        //ritorna la lista dei prestiti attivi 
        return lp;
    }
    
    /**
     * Restituisce la lista di tutti i libri presenti nel DB, inclusi quelli
     * prestati.
     * @return Lista di libri appartenenti alla libreria
     */
    @Override
    public List<Libro> libriTotale(){
        manager.getTransaction().begin();
        List<Libro> ll = null;
        //Prelevo tutti i libri presenti nella libreria
        
        try{
            ll = manager.createQuery("SELECT l FROM LibroMysqlImpl l ORDER BY l.titolo").getResultList();
        }
        catch(NoResultException e){
            //Nessun libro trovato
        }
        manager.getTransaction().commit();
        return ll;
    }
    
    /**
     * Il metodo si occupa di restituire l'insieme dei prestiti attivi legati 
     * a ciascun volume di un libro. 
     * @param isbn del libro
     * @return  Lista di prestiti legati a quel libro
     */
    @Override
    public List<Prestito> prestitiAttiviLibro(String isbn){
        Libro l = null;
        List<Prestito> lp = new ArrayList<Prestito>();
        manager.getTransaction().begin();
        try{
            l = (Libro) manager.createNamedQuery("LibroMysqlImpl.findByIsbn").setParameter("isbn",isbn).getSingleResult();
            //Per ciascun libro esamino ciascun volume 
            Collection<Volume> cv = l.getVolumeCollection();
            for(Iterator iter=cv.iterator(); iter.hasNext();){
                //restituisce la lista dei prestiti legati a quel volume
                Collection<Prestito> cp =  ((Volume) iter.next()).getPrestitoCollection();
                for(Iterator it=cp.iterator(); it.hasNext();){
                    Prestito p =  ((Prestito) it.next());
                    //Se il libro non ancora è stato restituito allora viene aggiunto
                    if(!p.getRestituito()) lp.add(p);
                }
            }
            //lp = manager.createQuery("SELECT p FROM PrestitoMysqlImpl p WHERE p.libro = :libro AND p.restituito = true").setParameter("libro", l.getTitolo()).getResultList();
        }
        catch(NoResultException e){
            //nessun libro trovato
        }
        manager.getTransaction().commit();
        return lp;
    }
        /**
     * Il metodo si occupa di restituire l'insieme dei prestiti passati legati 
     * a ciascun volume di un libro. 
     * @param isbn del libro
     * @return  Lista di prestiti legati a quel libro
     */
    @Override
    public List<Prestito> prestitiPassiviLibro(String isbn){
        Libro l = null;
        List<Prestito> lp = new ArrayList<Prestito>();
        manager.getTransaction().begin();
        try{
            l = (Libro) manager.createNamedQuery("LibroMysqlImpl.findByIsbn").setParameter("isbn",isbn).getSingleResult();
            //Per ciascun libro esamino ciascun volume 
            Collection<Volume> cv = l.getVolumeCollection();
            for(Iterator iter=cv.iterator(); iter.hasNext();){
                //restituisce la lista dei prestiti legati a quel volume
                Collection<Prestito> cp =  ((Volume) iter.next()).getPrestitoCollection();
                for(Iterator it=cp.iterator(); it.hasNext();){
                    Prestito p =  ((Prestito) it.next());
                    //Se il libro è stato restituito allora viene aggiunto
                    if(p.getRestituito()) lp.add(p);
                }
            }
            //lp = manager.createQuery("SELECT p FROM PrestitoMysqlImpl p WHERE p.libro = :libro AND p.restituito = true").setParameter("libro", l.getTitolo()).getResultList();
        }
        catch(NoResultException e){
            //nessun libro trovato
        }
        manager.getTransaction().commit();
        return lp;
    }
    
    /**
     * Il metodo ha il compito di esaminare quali sono i prestiti che sono
     * scaduti, in modo tale da poter essere avvisati da un bibliotecario
     * @return 
     */
    @Override
    public List<Prestito> getPrestitiScaduti(){
        
        List<Prestito> list = new ArrayList<Prestito>();
        //Prelevo tutti quanti i libri che sono nel db
        List<Libro> ll = libriTotale();
        for(Iterator it = ll.iterator(); it.hasNext();){
            Libro l = (Libro) it.next();
            //Per ciascun libro, esamino quali volumi sono in prestito
            List<Prestito> lp = prestitiAttiviLibro(l.getIsbn());
            for(Iterator ite=lp.iterator(); ite.hasNext();){
                //per ciascun prestito, verifico se è scaduto
                Prestito p =  (Prestito) ite.next();
                if(p.isExpired()) list.add(p);
            }
        }
        return list;
    }
    
    /**
     * Il metodo ha come compito quello di cercare tutti i volumi
     * che sono stati prestati agli utenti della libreria e non 
     * ancora restituiti
     * @return lista di tutti i prestiti in corso
     */
    @Override
    public List<Prestito> getAllActivePrestiti(){
        List<Prestito> list = new ArrayList<Prestito>();
        List<Libro> ll = libriTotale();
        for(Iterator it = ll.iterator(); it.hasNext();){
            Libro l = (Libro) it.next();
            //Per ciascun libro, esamino quali volumi sono in prestito
            List<Prestito> lp = prestitiAttiviLibro(l.getIsbn());
            for(Iterator ite=lp.iterator(); ite.hasNext();){
                //ciascun prestito attivo lo aggiungo alla lista risultato
                list.add((Prestito) ite.next());
            }
        }
        return list;
    }
    
    /**
     * Il metodo restituisce l'insieme di tutti gli utenti iscritti alla libreria
     * @return Lista di tutti gli utenti registrati
     */
    @Override
    public List<User> allUser(){
        List<User> ul= new ArrayList<User>();
        manager.getTransaction().begin();
        try{
            //Restituisco tutti gli utenti con gruppo di appartenenza id 1
            ul = manager.createQuery("SELECT u FROM UserMysqlImpl u WHERE u.gruppo.id = 2").getResultList();
        }
        catch(NoResultException e){
            //Nessun utente registrato
        }
        manager.getTransaction().commit();
        return ul;
    }

    /**
     * Il metodo analizza quali sono i volumi disponibili per il libro indicato
     * nell'isbn e procede con la restituzione dell'insieme di questi
     * @param isbn indicante il libro di cui si vogliono analizzare i volumi
     * @return Lista di volumi disponibili
     */
    @Override
    public List<Volume> getVolumiDisponibili(String isbn){
        List<Volume> res = new ArrayList<Volume>();
        //cerco l'oggetto libro associato a quell'isbn
        Libro l = searchByIsbn(isbn);
        if(l!=null){
            //se il libro esiste, prelevo l'intero insieme dei volumi
            List<Volume> vl = (List) l.getVolumeCollection();
            for(Iterator it = vl.iterator(); it.hasNext();){
                Volume v = (Volume) it.next();
                //di ciascun volume analizza la propria storia relativi ai prestiti
                List<Prestito> lp = (List) v.getPrestitoCollection();
                boolean trovato = false;
                //verifico se il volume è presente nella bibilioteca oppure se è stato prestato
                for(Iterator ite=lp.iterator(); ite.hasNext()&&!trovato;){
                    //per ciascun prestito, verifico se è scaduto
                    Prestito p =  (Prestito) ite.next();
                    if(!p.getRestituito()) trovato = true;
                }
                if(!trovato) res.add(v);
            }
            //restituisco la lista finale
            return res;
        }
        else return null;
    }
    
    /**
     * Il metodo registra nella base di dati il prestito eseguito da un
     * bibliotecario, in relazione ai parametri passati
     * @param isbn del libro che vogliamo prestare
     * @param id_vol della capia fisica che rilasciamo all'utente
     * @param id_user che vuole prendere in prestito il libro indicato
     * @return true se il prestito va a buon fine
     */
    @Override
    public boolean registraPrestito(String isbn, int id_vol, int id_user){
        //Cerchiamo il libro che vogliamo dare in prestito
        Libro l = searchByIsbn(isbn);
        Volume vol = null;
        User u=null;
        if(l!=null){
           
            //Dobbiamo recuperare l'oggetto che fa riferimento al volume fisico   
            List<Volume> lv = (List) l.getVolumeCollection();
            boolean trovato=false,already=false;
            //Scorriamo la lista di volumi per poter registrare il prestito
            for(Iterator it = lv.iterator(); it.hasNext()&&!trovato&&!already;){
                Volume v = (Volume) it.next();
                //Attenzione, dobbiamo anche controllare se l'utente ha
                //già in prestito una copia diversa dello stesso libro
                if(isAlreadyOwner(v, id_user)) already = true;
                if(v.getId()==id_vol){
                    //Abbiamo trovato il volume di nostro interesse!
                    vol = v;
                    //((VolumeMysqlImpl)vol).setDurataMax(durata_max);
                    trovato = true;
                }
            }
            //Devo controllore se la variabile booleana already è true, cioè
            //se un'altra copia fisica dello stesso libro è stata già presa in prestito
            if(already) return false;
            manager.getTransaction().begin();
            try{
                //Verifico se un utente con quella username è presente nel database
                u = (User) manager.createNamedQuery("UserMysqlImpl.findById").setParameter("id", id_user).getSingleResult();

            }catch (NoResultException e){
                //Non esiste alcun utente con quell'username
            } 
            
            if(u!=null&&vol!=null){
                //Registro il nuovo prestito
                Prestito p = new PrestitoMysqlImpl(null, new Date(), null, false);
                //Registro l'utente interessato
                p.setUser(u);
                //Imposto il volume selezionato dal bibliotecario
                p.setVolume(vol);
                manager.persist(p);
                manager.getTransaction().commit();
                return true;
            }
            manager.getTransaction().commit();
            
        }
        return false;
    }
    
    /**
     * Il metodo permette di chiudere un prestito effettuato in precedenza da un
     * utente.
     * @param id_prestito che si vuole chiudere
     * @return true se la chiusura è stata eseguita correttamente
     */
    @Override
    public boolean chiudiPrestito(int id_prestito){
        Prestito p = null;
        manager.getTransaction().begin();
        try{
            p = (Prestito) manager.createNamedQuery("PrestitoMysqlImpl.findById").setParameter("id", id_prestito).getSingleResult();
        }
        catch(NoResultException e){
            //nessun prestito per quell'id
        }
        if(p!=null && !p.getRestituito()){
            //Imposto la data di restituzione a quella odierna
            p.setDataRestituzione(new Date());
            //immposto il flag di restituzione a true
            p.setRestituito(true);
            manager.persist(p);
            manager.getTransaction().commit();
            return true;
        }
        manager.getTransaction().commit();
        return false;
    }
    
    /**
     * Questo metodo verifica se un tag è gia presente nella lista dei tag della
     * libreria: risulta indispensabile quando si vuole inserire un nuovo tag.
     * @param tag stringa indicante il tag che si vuole inserire
     * @return true se il tag è già presente
     */
    @Override
    public boolean tagIsThis(String tag){
        boolean res=false;
        manager.getTransaction().begin();
        try{
            Tag t = (Tag) manager.createNamedQuery("TagMysqlImpl.findByTag").setParameter("tag", tag).getSingleResult();
            res = true;
        }
        catch(NoResultException e){
            //nessun tag trovato
        }
        manager.getTransaction().commit();
        return res;
    }
    
    /**
     * Il metodo permette l'aggiunta di un nuovo tag nel database, nel caso
     * la libreria non abbia ancora incluso una particolare categoria di libri
     * @param tag stringa indicante il tag
     * @return true se l'inserimento è andato a buon fine
     */
    @Override
    public boolean insertTag(String tag){
        
        if(!tagIsThis(tag)){
            manager.getTransaction().begin();
            Tag t = new TagMysqlImpl(null, tag);
            manager.persist(t);
            manager.getTransaction().commit();
            return true;
        }
        else return false;
    }

    /**
     * Il metodo permette la modifica di un tag nel database.
     * @param id_tag indicante l'id del tag che vogliamo modificare
     * @param tag stringa indicante il tag
     * @return true se la modifica è andata a buon fine
     */
    @Override
    public boolean modificaTag(int id_tag,String tag){
        Tag t = getTag(id_tag);
        if(t!=null){
            boolean res = true;
            manager.getTransaction().begin();
            try{
                //Significa che esiste qualche tag con quel nome
                Tag temp = (Tag)manager.createNamedQuery("TagMysqlImpl.findByTag").setParameter("tag", tag).getSingleResult();
                res = false;
                
            }
            catch(NoResultException e){
                
                        
                t.setTag(tag);
                manager.persist(t);
            }
            manager.getTransaction().commit();
            return res;
        }
        else return false;
    }
    /**
     * Il metodo permette la rimozione di un tag nel database.
     * @param id_tag indicante l'id del tag che vogliamo eliminare
     * @return true se la rimozione è andata a buon fine
     */
    @Override
    public boolean eliminaTag(int id_tag){
        Tag t = getTag(id_tag);
        if(t!=null){
            manager.getTransaction().begin();
            manager.remove(t);
            manager.getTransaction().commit();
            return true;
        }
        else return false;
    }   
    /**
     * Controllo se l'autore è già presente nel database, attraverso il suo nome
     * e cognome
     * @param cognome dell'autore che vogliamo inserire
     * @param nome dell'autore che vogliamo inserire
     * @return true se un autore con quei dati è già presente
     */
    @Override
    public boolean autoreIsThis(String cognome, String nome){
        boolean res=false;
        manager.getTransaction().begin();
        try{
            manager.createQuery("SELECT u FROM AutoreMysqlImpl u WHERE u.nome=:nome AND u.cognome=:cognome").setParameter("nome", nome).setParameter("cognome", cognome).getSingleResult();
            res = true;
        }
        catch(NoResultException e){
            //nessun tag trovato
        }
        manager.getTransaction().commit();
        return res;
    }
    
    /**
     * Il metodo permette l'aggiunta di un nuovo autore nel database.
     * @param cognome dell'autore da inserire
     * @param nome nome dell'autore da inserire
     * @return true se l'inserimento è andato a buon fine
     */
    @Override
    public boolean insertAutore(String cognome,String nome){
        
        if(!autoreIsThis(cognome, nome)){
            manager.getTransaction().begin();
            Autore a = new AutoreMysqlImpl(null, nome);
            a.setCognome(cognome);
            manager.persist(a);
            manager.getTransaction().commit();
            return true;
        }
        else return false;
    }
    
    /**
     * Il metodo permette la modifica di un autore nel database.
     * @param id_autore 
     * @param cognome dell'autore da inserire
     * @param nome nome dell'autore da inserire
     * @return true se la modifica è andata a buon fine
     */
    @Override
    public boolean modificaAutore(int id_autore,String cognome,String nome){
        Autore a = getAutore(id_autore);
        if(a!=null){
            a.setNome(nome);
            a.setCognome(cognome);
            manager.getTransaction().begin();
            manager.persist(a);
            manager.getTransaction().commit();
            return true;
        }
        else return false;
    }    

    /**
     * Il metodo permette di eliminare un autore presente nel database.
     * @param id_autore 
     * @return true se la cancellazione è andata a buon fine
     */
    @Override
    public boolean eliminaAutore(int id_autore){
        Autore a = getAutore(id_autore);
        if(a!=null){
            manager.getTransaction().begin();
            manager.remove(a);
            manager.getTransaction().commit();
            return true;
        }
        else return false;
    }  
    
    /**
     * Il metodo permette di prelevare tutti quanti i tag presenti nel database
     * in modo tale da poter essere visualizzati agli utenti che hanno bisogno
     * di manipolarli
     * @return Lista di tag presenti nel database
     */
    @Override
    public List<Tag> getAllTag(){
        List<Tag> tl = (List) new ArrayList<TagMysqlImpl>();
        manager.getTransaction().begin();
        try{
            tl = manager.createNamedQuery("TagMysqlImpl.findAll").getResultList();
        }
        catch(NoResultException e){
            //Nessun tag presente nel database
        }
        manager.getTransaction().commit();
        return tl;
    }
    
    /**
     * Il metodo permette di prelevare tutti quanti gli autori presenti nel database
     * in modo tale da poter essere visualizzati agli utenti che hanno bisogno
     * di manipolarli
     * @return Lista di autori presenti nel database
     */
    @Override
    public List<Autore> getAllAutori(){
        List<Autore> al = (List) new ArrayList<AutoreMysqlImpl>();
        manager.getTransaction().begin();
        try{
            al = manager.createNamedQuery("AutoreMysqlImpl.findAll").getResultList();
        }
        catch(NoResultException e){
            //Nessun tag presente nel database
        }
        manager.getTransaction().commit();
        return al;
    }
    /**
     * Il metodo permette di prelevare tutte quante le lingue presenti nel database
     * in modo tale da poter essere visualizzati agli utenti che hanno bisogno
     * di manipolarli
     * @return Lista di Lingua presenti nel database
     */
    @Override
    public List<Lingua> getAllLingua(){
        List<Lingua> ll = (List) new ArrayList<LinguaMysqlImpl>();
        manager.getTransaction().begin();
        try{
            ll = manager.createNamedQuery("LinguaMysqlImpl.findAll").getResultList();
        }
        catch(NoResultException e){
            //Nessun tag presente nel database
        }
        manager.getTransaction().commit();
        return ll;
    }
    
    /**
     * Verifica se la lingua passata come parametro è presente nella tabella lingua
     * @param lingua di cui vogliamo controllare la presenza
     * @return true se la lingua è gia presente
     */
    @Override
    public boolean linguaIsThis(String lingua){
        boolean res=false;
        manager.getTransaction().begin();
        try{
            Lingua t = (Lingua) manager.createNamedQuery("LinguaMysqlImpl.findByLingua").setParameter("lingua", lingua).getSingleResult();
            res = true;
        }
        catch(NoResultException e){
            //nessun tag trovato
        }
        manager.getTransaction().commit();
        return res;
    }
    
    /**
     * Inserisce una nuova lingua nella base di dati, se questa non esiste
     * @param lingua che vogliamo inserire
     * @return true se l'inserimento è andato a buon fine
     */
    @Override
    public boolean insertLingua(String lingua){
        if(!linguaIsThis(lingua)){
            manager.getTransaction().begin();
            Lingua l = new LinguaMysqlImpl(null, lingua);
            manager.persist(l);
            manager.getTransaction().commit();
            return true;
        }
        else return false;
    }
    
    /**
     * Il metodo permette di modificare una lingua, indicando il suo id
     * @param id_lingua
     * @param lingua
     * @return true se la modifica è stata effettuata corretramente
     */
    @Override
    public boolean modificaLingua(int id_lingua, String lingua){
        if(!linguaIsThis(lingua)){
            boolean res = true;
            manager.getTransaction().begin();
            try{
                 Lingua l = (Lingua) manager.createNamedQuery("LinguaMysqlImpl.findById").setParameter("id", id_lingua).getSingleResult();
                 l.setLingua(lingua);
                 manager.persist(l);
            }
            catch(NoResultException e){
                res = false;
            }      
  
            manager.getTransaction().commit();
            return res;
        }
        else return false;
    }
    /**
     * Il metodo permette di eliminare una lingua, indicando il suo id
     * @param id_lingua
     * @return true se la sua eliminazione è stata effettuata correttamente
     */
    @Override
    public boolean eliminaLingua(int id_lingua){
        boolean res = true;
        manager.getTransaction().begin();
        try{
             Lingua l = (Lingua) manager.createNamedQuery("LinguaMysqlImpl.findById").setParameter("id", id_lingua).getSingleResult();
             manager.remove(l);
        }
        catch(NoResultException e){
             res = false;
        }
        manager.getTransaction().commit();
        return res;
    }
    
    /**
     * Il metodo verifica se un'altra copia fisica dello stesso libro è già 
     * in possesso dallo stesso utente
     * @param v
     * @param id_user
     * @return 
     */
    public boolean isAlreadyOwner(Volume v, int id_user){
        
        User u = getUser(id_user);
        //Prelevo tutti quanti i prestiti relativi al volume considerato
        if(u != null){
            Collection<Prestito> cp = v.getPrestitoCollection();
            for(Iterator ite = cp.iterator(); ite.hasNext(); ){
                Prestito p = (Prestito) ite.next();
                //Per ciascun prestito, verifico se è in corso e se l'utente
                //indicato dal parametro passato in input
                if((!p.getRestituito()) && (p.getUser().equals(u))) return true;
            }
        }
        return false;
    }
    
    /**
     * Il metodo restituisce un oggetto Autore a partire dal suo ID
     * @param id_autore che vogliamo cercare
     * @return Autore relativo a quell'id, se esiste, altrimenti null
     */
    @Override
    public Autore getAutore(int id_autore){ 
        Autore a=null;
        manager.getTransaction().begin();
        try{
            a = (Autore) manager.createNamedQuery("AutoreMysqlImpl.findById").setParameter("id", id_autore).getSingleResult();
        }
        catch(NoResultException e){
            //nessun tag trovato
        }
        manager.getTransaction().commit();
        return a;
    }
    
    /**
     * Il metodo restituisce un oggetto Tag a partire dal suo ID
     * @param id_tag
     * @return Tag relativo quell'id se esiste, altrimenti null
     */
    @Override
    public Tag getTag(int id_tag){ 
        Tag t = null;
        manager.getTransaction().begin();
        try{
            t = (Tag) manager.createNamedQuery("TagMysqlImpl.findById").setParameter("id", id_tag).getSingleResult();
        }
        catch(NoResultException e){
            //nessun tag trovato
        }
        manager.getTransaction().commit();
        return t;
    }
    /**
     * Il metodo restituisce un oggetto Lingua a partire dal suo ID
     * @param id_lingua
     * @return Lingua relativo quell'id se esiste, altrimenti null
     */
    @Override
    public Lingua getLingua(int id_lingua){ 
        Lingua l = null;
        manager.getTransaction().begin();
        try{
            l = (Lingua) manager.createNamedQuery("LinguaMysqlImpl.findById").setParameter("id", id_lingua).getSingleResult();
        }
        catch(NoResultException e){
            //nessun tag trovato
        }
        manager.getTransaction().commit();
        return l;
    }
    /**
     * Il metodo restituisce l'oggetto user in modo tale da poter fornire delle 
     * informazioni utili su di esso.
     * @param username dell'utente, o parte di esso
     * @return list di oggetti User relativi quell'username
     */
    @Override
    public List<User> getUsers(String username){
        String un = ""+username+"%";
        manager.getTransaction().begin();
        List<User> ul = null;
        try{
            //Verifico se un utente con quella username è già presente nel database
            ul =  manager.createQuery("SELECT u FROM UserMysqlImpl u WHERE u.username LIKE :username AND u.gruppo.id = 2").setParameter("username", un).getResultList();
        }catch (NoResultException e){
            //Non esiste alcun utente con quell'username
        }
        manager.getTransaction().commit();
        return ul;
    }
    
    /**
     * Il metodo fornisce tutti quanti gli stati presenti nel sistema. Ricordiamo
     * che per stato indichiamo le condizioni fisiche di un volume
     * @return Lista di stato
     */
    @Override
    public List<Stato> getAllStato(){
        List<Stato> sl = new ArrayList<Stato>();
        try{
            //Prelevo tutti quanti gli oggetti stato
            sl =  manager.createNamedQuery("StatoMysqlImpl.findAll").getResultList();
        }catch (NoResultException e){
            //Non esiste alcun utente con quell'username
        }
        return sl;
    }
    
    /**
     * Il metodo provvede a restituire uno stato, inteso come oggetto a partire 
     * dal suo id.
     * @param id dello stato che si vuole cercare
     * @return Stato se ce n'è uno con quell'id, altrimenti null
     */
    @Override
    public Stato getStato(int id){
        Stato s  = null;
        try{
            //Prelevo tutti quanti gli oggetti stato
            s =  (Stato) manager.createNamedQuery("StatoMysqlImpl.findById").setParameter("id", id).getSingleResult();
        }catch (NoResultException e){
            //Non esiste alcun utente con quell'username
        }
        return s;
    }
    
    /**
     * Il metodo provvede all'inserimento di un nuovo stato, cioè di una nuova
     * condizione fisica del libro
     * @param stato
     * @return 
     */
    @Override
    public boolean insertStato(String stato){
        boolean res = false;
        manager.getTransaction().begin();
        try{
           //Prelevo gli oggetti stato, se esistono
           manager.createNamedQuery("StatoMysqlImpl.findByStato").setParameter("stato", stato).getResultList();
           Stato s = new StatoMysqlImpl(null, stato);
           //memorizzo sul database
           manager.persist(s);
           res = true;
        }catch (NoResultException e){
            //Non esiste alcuno stato con quel nome
  
        }
        manager.getTransaction().commit();
        return res;
    }
    
    /**
     * Il metodo provvede alla modifica di uno stato, cioè alla modifica di una
     * condizione fisica del libro
     * @param id_stato 
     * @param stato, cioè la stringa indicante il nuovo stato
     * @return true se la modifica è avvenuta correttamente
     */
    @Override
    public boolean modificaStato(int id_stato,String stato){
        Stato s = getStato(id_stato);
        if(s!=null){
        boolean res = true;
        manager.getTransaction().begin(); 
            try{
               //Prelevo gli oggetti stato, se esistono
               Stato temp = (Stato) manager.createNamedQuery("StatoMysqlImpl.findByStato").setParameter("stato", stato).getSingleResult();
               //Significa che esiste già uno stato con quel nome
               res = false;
            }catch (NoResultException e){
                //Altrimenti procedo con il regolare inserimento
                s.setStato(stato);
                manager.persist(s);
            }
            manager.getTransaction().commit();
            return res;
        }
        else return false;
    }

    /**
     * Il metodo provvede alla rimozione di uno stato.
     * @param id_stato 
     * @return true se la cancellazione è avvenuta correttamente
     */
    @Override
    public boolean eliminaStato(int id_stato){
        Stato s = getStato(id_stato);
        if(s!=null){
                manager.getTransaction().begin();
                manager.remove(s);
                manager.getTransaction().commit();
                return true;
        }
        else return false;
    } 
   
    /**
     * Il metodo si occupa di eliminare i libri nella seconda lista già
     * presente nella prima: si aggiunge alla lista che sarà infine restituta
     * l'insieme dei libri che sono comuni ad entrambe le liste
     * @param ll1 lista 1
     * @param ll2 lista 1
     * @return Lista contentente i libri comuni ad entrambe le liste
     */
    public List<Libro> bookContained(List<Libro> ll1,List<Libro> ll2){
        List<Libro> resList = new ArrayList<Libro>();
        for(Iterator it = ll1.iterator(); it.hasNext(); ){
            //Prelevo un libro della prima lista
            Libro l1 = (Libro) it.next();
            
            //Per ogni libro della seconda lista lista, vedo se è presente anche nella prima
            for(Iterator ite = ll2.iterator(); ite.hasNext(); ){
                Libro l2 = (Libro) ite.next();
                if(l1.getIsbn().equals( l2.getIsbn())){
                    resList.add(l2);
                }
            }
        }
        return resList;
    }

    /**
     * Il metodo provvede l'inserimento della copia elettronica relativo un libro
     * @param l libro al quale vogliamo aggiungere una copia elettronica
     * @param mime tipo del file che stiamo inserendo
     * @return true se l'inserimento va a buon fine
     */
    @Override
    public boolean insertCopiaElettronica(Libro l, String mime){
 
            manager.getTransaction().begin();
            Copiaelettronica ce =new CopiaelettronicaMysqlImpl(null, mime, l.getIsbn()+"."+mime);
            ce.setLibro(l); 
            manager.persist(ce);
            manager.getTransaction().commit();
            return true;
    }
    
    /**
     * Il metodo provvede la modifica della copia elettronica relativo un libro
     * @param id_copia 
     * @param mime tipo del file che stiamo modificando
     * @return true se l'inserimento va a buon fine
     */
    @Override
    public boolean modificaCopiaElettronica(int id_copia, String mime){
        Copiaelettronica ce = getCopiaElettronica(id_copia);
        if(ce!=null){
            ce.setMimetype(mime);
            ce.setUrl(ce.getLibro().getIsbn()+"."+mime);
            manager.getTransaction().begin();
            manager.persist(ce);
            manager.getTransaction().commit();
            return true;
        }
        else return false;
    }
    
    /**
     * Il metodo provvede alla rimozione della copia elettronica relativa un libro
     * @param id_copia 
     * @return true se l'eliminazione va a buon fine
     */
    @Override
    public boolean eliminaCopiaElettronica(int id_copia){
        Copiaelettronica ce = getCopiaElettronica(id_copia);
        if(ce!=null){
            manager.getTransaction().begin();
            manager.remove(ce);
            manager.getTransaction().commit();
            return true;
        }
        else return false;
    }
    
    /**
     * Ritorna a copia elettronica indicata da quell'id
     * @param id_copia della copia che vogliamo recuperare
     * @return CopiaElettronica desiderata altrimenti null se non esiste
     */
    public Copiaelettronica getCopiaElettronica(int id_copia){
        Copiaelettronica e = null;
        manager.getTransaction().begin();
        try{
            e = (Copiaelettronica) manager.createNamedQuery("CopiaElettronicaMysqlImpl.findById").setParameter("id", id_copia).getSingleResult();
        }
        catch(NoResultException ex){
            //Non c'è nessuna copia elettronica con quell'id
        }
        manager.getTransaction().commit();
        return e;
    }
    
    /**
     * Il metodo consiste nel verificare se un autore ha scritto il libro indicato
     * dall'id e restituire la lista degli autori che non hanno scritto quel libro
     * @param id_libro
     * @return restituisce la lista degli autori che non hanno scritto quel libro
     */
    @Override
    public List<Autore> notAuthor(String isbn_libro){
        //Prelevo il Libro
        Libro l = searchByIsbn(isbn_libro);
        if(l!=null){
            //Se esiste, prelevo la lista degli autori che l'hanno scritto
            List<Autore> ca = getAllAutori();
            //Per ciascun autore, esamino il suo id
            ca.removeAll(l.getAutoreCollection());
            return ca;
        }
        return null;
    }
    
    /**
     * Il metodo consiste nel verificare se un tag appartiene al libro indicato
     * dall'isbn, e restituire la lista dei tag che non appartengono al libro
     * @param isbn_libro
     * @return la lista dei tag che non appartengono al libro
     */
    @Override
    public List<Tag> notAtag(String isbn_libro){
        //Prelevo il Libro
        Libro l = searchByIsbn(isbn_libro);
        if(l!=null){
            //Se esiste, prelevo la lista degli autori che l'hanno scritto
            List<Tag> tc = getAllTag();
            tc.removeAll(l.getTagCollection());
            return tc;
        }
        else return null;
    }
    
    /**
     * Il metodo permette di visualizzare tutti quanti gli stati di un volume,
     * ad eccezione di quello del volume di riferimento.
     * @param id_volume per il quale 
     * @return Lista di stato escluso quello del volume riferito
     */
    @Override
    public List<Stato> notStati(int id_volume){
        Volume v = getVolume(id_volume);
        if(v!=null){
            List<Stato> sl = getAllStato();
            sl.remove(v.getStato());
            return sl;
        }
        else return null;
    }
}
