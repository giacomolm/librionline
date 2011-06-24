/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.test;

import it.univaq.idw.librionline.model.LibriOnLineDataLayer;
import it.univaq.idw.librionline.model.Prestito;
import it.univaq.idw.librionline.model.impl.LibriOnLineDataLayerMysqlImpl;
import it.univaq.idw.librionline.model.impl.VolumeMysqlImpl;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author giacomolm
 */
public class gestioneRitardi {
    public static void main(String[] args){
        LibriOnLineDataLayer dl = new LibriOnLineDataLayerMysqlImpl();
        List<Prestito> lp = dl.getPrestitiAttivi("zilfio");
        for(Iterator it = lp.iterator(); it.hasNext();){
            Prestito element = (Prestito )it.next();
            //converto in millisecondi la data di prestito
            long dataprest = element.getDataPrestito().getTime();
             //Calcolo in millisecondi la data della restituzione
            int durata =((VolumeMysqlImpl) element.getVolume()).getDurataMax()*86400000;
            //Costruito un nuovo oggetto Date relativa la restituzione presunta
            Date temp = new Date(dataprest+(long) durata);
            //Controllo se il prestito è scaduto
            if (temp.before(new Date())) System.out.println("Il prestito è scaduto");
                  
        }
        
    }
}
