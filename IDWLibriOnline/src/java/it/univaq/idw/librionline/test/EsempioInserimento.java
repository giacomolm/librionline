/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.test;
import it.univaq.idw.librionline.model.Libro;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import it.univaq.idw.librionline.model.impl.LibroMysqlImpl;

/**
 *
 * @author giacomolm
 */
public class EsempioInserimento {
    
    public static void main(String[] args) {
        // TODO code application logic here
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("IDWLibriOnlinePU");
        EntityManager manager = factory.createEntityManager();
        Libro l = new LibroMysqlImpl("90", "Giuliano Pisapia");
        l.setEditore("Mondadori");
        l.setAnnoPubblicazione(new Integer(1928));
        l.setRecensione("Il libro racconta la storia dal 1928 ad oggi");
        //l'inserimento della lingua è delicata, per via di un'altra 
        manager.getTransaction().begin();
        manager.persist(l);
        manager.getTransaction().commit();
        //Collection<Libro> lc = get
    }
}
