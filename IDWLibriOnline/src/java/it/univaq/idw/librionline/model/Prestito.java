/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.model;

import java.util.Date;

/**
 *
 * @author giacomolm
 */
public interface Prestito {

    boolean equals(Object object);

    Date getDataPrestito();

    Date getDataRestituzione();

    Integer getId();

    boolean getRestituito();

    User getUser();

    Volume getVolume();

    int hashCode();

    void setDataPrestito(Date dataPrestito);

    void setDataRestituzione(Date dataRestituzione);

    void setId(Integer id);

    void setRestituito(boolean restituito);

    void setUser(User user);

    void setVolume(Volume volume);

    String toString();
    
}
