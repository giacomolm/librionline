/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.test;

import it.univaq.idw.librionline.model.LibriOnLineDataLayer;
import it.univaq.idw.librionline.model.User;
import it.univaq.idw.librionline.model.impl.LibriOnLineDataLayerMysqlImpl;

/**
 *
 * @author giacomolm
 */
public class EsempioLogin {
    public static void main(String[] args){
        
        //Questo è l'oggetto che devi dichiarare per qualsiasi interazione con il DB
        LibriOnLineDataLayer dl = new LibriOnLineDataLayerMysqlImpl();
        //Inserisci un utente a mano sul DB e inserisci qui sotto i suoi dati
        String username = "user";
        String password = "pass";
        //Questa è la funzione da richiamare per il login
        //Attenzione! restituisco un'oggetto user! Se non esiste alcun utente o se c'è stato qualche problema di autenticazione viene restituito un oggetto null
        User u = dl.login(username, password);
        if(u!=null)
            System.out.println(u.getNome()+" "+u.getCognome());
        System.out.println(dl.isThisUsername(username));
        System.out.println(dl.isThisUsername("Nduccio"));
    }
}
