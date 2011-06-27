/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.test;

import it.univaq.idw.librionline.model.Autore;
import it.univaq.idw.librionline.model.LibriOnLineDataLayer;
import it.univaq.idw.librionline.model.Libro;
import it.univaq.idw.librionline.model.Prestito;
import it.univaq.idw.librionline.model.Tag;
import it.univaq.idw.librionline.model.impl.LibriOnLineDataLayerMysqlImpl;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author giacomolm
 */
public class EsempioRicerca {
    public static void main(String[] args){
        
        //Questo è l'oggetto che devi dichiarare per qualsiasi interazione con il DB
        LibriOnLineDataLayer dl = new LibriOnLineDataLayerMysqlImpl();
        //Inserisci un libro a mano sul DB e inserisci qui sotto il suo titolo
        String l = "t";
        //Questa è la funzione da richiamare per la ricerca base
        //Attenzion! restituisco una collezione di libri! Perchè più libri potrebbero avere lo stesso titolo
        Collection<Libro> bc = dl.simpleBookSearch(l);
        System.out.println(bc.isEmpty());
        
        //Se le collezioni potrebbero essere un problema fammi sapere!
        for ( Iterator i = bc.iterator(); i.hasNext(); ) {
            Libro element = (Libro) i.next();
            System.out.println( "Codice ISBN "+element.getTitolo());
        }

        //ricerca per isbn
        Libro li = dl.searchByIsbn("9788850329649");
        System.out.println( "Titolo "+li.getTitolo());
        Collection<Autore> ac = li.getAutoreCollection();
        for ( Iterator i = ac.iterator(); i.hasNext(); ) {
            Autore element = (Autore) i.next();
            System.out.println("Cognome "+element.getCognome());
        } 
        
        Collection<Tag> tc = li.getTagCollection();
        for ( Iterator i = tc.iterator(); i.hasNext(); ) {
            Tag element = (Tag) i.next();
            System.out.print(element.getTag()+" ");
        }
        System.out.println("");
        Collection<Libro> list = dl.searchByTags("programmazione ");
        for ( Iterator i = list.iterator(); i.hasNext(); ) {
            Libro element = (Libro) i.next();
            System.out.println( "Titolo Tag "+element.getTitolo());
        }

        list = dl.searchByAutori("Della Penna");
        for ( Iterator i = list.iterator(); i.hasNext(); ) {
            Libro element = (Libro) i.next();
            System.out.println( "Autore "+element.getTitolo());                           
        }
        
        list = dl.searchLibriAutoriById(1);
        for ( Iterator i = list.iterator(); i.hasNext(); ) {
            Libro element = (Libro) i.next();
            System.out.println( "Libro Autore "+element.getTitolo());                           
        }
        
        list = dl.getLastAdded();
        for ( Iterator i = list.iterator(); i.hasNext(); ) {
            Libro element = (Libro) i.next();
            System.out.println( "Anno : "+element.getDataIns());                           
        }
        System.out.println( "Numero copie : "+dl.getNumeroCopie("9788861142879")); 
        
        dl.getMostProvided();
        
        System.out.println("Zilgio "+dl.getGruppoByUsername("zilfio"));
        System.out.println("Zilfio "+dl.isAdmin("zilfio"));
        dl.getNumeroCopieDisponibili(l);
        dl.getProssimoData(li.getIsbn());
        
        List<Prestito> listp = dl.getPrestitiPassati("prova");
        for ( Iterator i = listp.iterator(); i.hasNext(); ) {
            Prestito element = (Prestito) i.next();
            System.out.println( "giacomolm : "+element.getVolume());                           
        }
        listp = dl.prestitiAttiviLibro("9788871923031");
        for ( Iterator i = listp.iterator(); i.hasNext(); ) {
            Prestito element = (Prestito) i.next();
            System.out.println( "Attivo : "+element.getVolume());                           
        }
        listp = dl.prestitiPassiviLibro("9788861142879");
        for ( Iterator i = listp.iterator(); i.hasNext(); ) {
            Prestito element = (Prestito) i.next();
            System.out.println( "Attivo : "+element.getVolume());                           
        }
    }
}
