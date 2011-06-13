/*
 * Questo esempio cerca di spiegare come procedere con l'inserimento di un utente
 */
package it.univaq.idw.librionline.test;

import it.univaq.idw.librionline.model.Gruppo;
import it.univaq.idw.librionline.model.LibriOnLineDataLayer;
import it.univaq.idw.librionline.model.User;
import it.univaq.idw.librionline.model.impl.LibriOnLineDataLayerMysqlImpl;

/**
 *
 * @author giacomolm
 */
public class EsempioUser {
    public static void main(String[] args){
        //Questo è l'oggetto che devi dichiarare per qualsiasi interazione con il DB
        LibriOnLineDataLayer dl = new LibriOnLineDataLayerMysqlImpl();
        //Ottengo il gruppo che voglio inserire;
        Gruppo g = dl.getGruppo("registrato"); 
        //Eseguiamo l'inserimento e lo stampiamo
        System.out.println(dl.insertUser("paperino","paperina", "paperino@pluto.it", "12341234", "Nome", "Cognome", "per123te5g51", "Via Roma", "Paperopoli", "Papera", 12412, g));
        //Attenzione! deve esserci una gestione del gruppo, cosa che abbiamo tralasciato
    }
}
