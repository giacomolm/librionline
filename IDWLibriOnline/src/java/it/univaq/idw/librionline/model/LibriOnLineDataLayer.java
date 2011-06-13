/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.model;

import it.univaq.idw.librionline.model.Libro;
import java.util.List;

/**
 *
 * @author giacomolm
 */
public interface LibriOnLineDataLayer {

    boolean isThisUsername(String username);
    User login(String username, String password);
    List<Libro> simpleBookSearch(String titolo);
    boolean insertUser(String username,String password,String email,String telefono,String nome,String cognome,String codfisc,String indirizzo,String citta,String prov,int cap,Gruppo gruppo);
    Gruppo getGruppo(String tipo);
    
}
