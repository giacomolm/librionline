/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.model;

import it.univaq.idw.librionline.model.impl.UserMysqlImpl;
import it.univaq.idw.librionline.model.impl.VolumeMysqlImpl;
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

    UserMysqlImpl getUser();

    VolumeMysqlImpl getVolume();

    int hashCode();

    void setDataPrestito(Date dataPrestito);

    void setDataRestituzione(Date dataRestituzione);

    void setId(Integer id);

    void setRestituito(boolean restituito);

    void setUser(UserMysqlImpl user);

    void setVolume(VolumeMysqlImpl volume);

    String toString();
    
}
