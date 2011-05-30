/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.test;
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
        LibroMysqlImpl l = new LibroMysqlImpl("28", "TwentyEight");
        l.setEditore("Mondadori");
        l.setAnnoPubblicazione(new Integer(1928));
        l.setRecensione("Il libro racconta la storia dal 1928 ad oggi");
        //l'inserimento della lingua è delicata, per via di un'altra 
        manager.getTransaction().begin();
        manager.persist(l);
        manager.getTransaction().commit();
    }
}
