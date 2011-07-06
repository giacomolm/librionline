package it.univaq.idw.librionline.framework.util;

import java.io.IOException;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SecurityLayer {

    //--------- SICUREZZA DI SESSIONE ------------
    //questa funzione esegue una serie di controlli di sicurezza
    //sulla sessione corrente. Se la sessione non è valida, la cancella
    //e ritorna null, altrimenti la aggiorna e la restituisce
    public static HttpSession checkSession(HttpServletRequest r) {
        boolean check = true;

        HttpSession s = r.getSession(false);
        //per prima cosa vediamo se la sessione è attiva
        if (s == null) {
            return null;
        }

        //check sulla validità della sessione
        if (s.getAttribute("userid") == null) {
            check = false;
            //check sull'ip del client
        } else if (!((String) s.getAttribute("ip")).equals(r.getRemoteHost())) {
            check = false;
            //check sulle date
        } else {
            //inizio sessione
            Calendar begin = (Calendar) s.getAttribute("inizio-sessione");
            //ultima azione
            Calendar last = (Calendar) s.getAttribute("ultima-azione");
            //data/ora correnti
            Calendar now = Calendar.getInstance();
            if (begin == null) {
                check = false;
            } else {
                //millisecondi trascorsi dall'inizio della sessione
                long secondsfrombegin = (now.getTimeInMillis() - begin.getTimeInMillis()) / 1000;
                //dopo tre ore la sessione scade
                if (secondsfrombegin / 60 > 3 * 60) {
                    check = false;
                } else if (last != null) {
                    //millisecondi trascorsi dall'ultima azione
                    long secondsfromlast = (now.getTimeInMillis() - last.getTimeInMillis()) / 1000;
                    //dopo 30 minuti dall'ultima operazione la sessione è invalidata
                    if (secondsfromlast / 60 > 1 * 60) {
                        check = false;
                    }
                }
            }
        }
        if (!check) {
            s.invalidate();
            return null;
        } else {
            //reimpostiamo la data/ora dell'ultima azione
            s.setAttribute("ultima-azione", Calendar.getInstance());
            return s;
        }
    }

    public static HttpSession createSession(HttpServletRequest request, String username, int userid) {
        HttpSession s = request.getSession(true);
        s.setAttribute("username", username);
        s.setAttribute("ip", request.getRemoteHost());
        s.setAttribute("inizio-sessione", Calendar.getInstance());
        s.setAttribute("userid", userid);
        return s;
    }

    public static void disposeSession(HttpServletRequest request) {
        HttpSession s = request.getSession(true);
        s.invalidate();
    }

    //--------- SICUREZZA DATI ------------
    //questa funzione aggiunge un backslash davanti a
    //tutti i caratteri "pericolosi", usati per eseguire
    //SQL injection attraverso i parametri delle form
    public static String addSlashes(String s) {
        return s.replaceAll("(['\"\\\\])", "\\\\$1");
    }

    //questa funzione rimuove gli slash aggiunti da addSlashes
    public static String stripSlashes(String s) {
        return s.replaceAll("\\\\(['\"\\\\])", "$1");
    }

    public static int checkNumeric(String s) throws NumberFormatException {
        //convertiamo la stringa in numero, ma assicuriamoci prima che sia valida
        if (s != null) {
            //se la conversione fallisce, viene generata un'eccezione
            return Integer.parseInt(s);
        } else {
            throw new NumberFormatException("String argument is null");
        }
    }

    //--------- SICUREZZA DI CONNESSIONE ------------
    //questa funzione verifica se il protocollo HTTPS � attivo
    public static boolean checkHttps(HttpServletRequest r) {
        return r.isSecure();
        //metodo "fatto a mano" che funziona solo se il server trasmette gli header corretti
        //String httpsheader = r.getHeader("HTTPS");
        //return (httpsheader != null && httpsheader.toLowerCase().equals("on"));
    }

    //questa funzione ridirige il browser sullo stesso indirizzo
    //attuale, ma con protocollo https
    public static void redirectToHttps(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        //estraiamo le parti della request url
        String server = request.getServerName();
        //int port = request.getServerPort();
        String context = request.getContextPath();
        String path = request.getServletPath();
        String info = request.getPathInfo();
        String query = request.getQueryString();

        //riconstruiamo la url cambiando il protocollo e la porta COME SPECIFICATO NELLA CONFIGURAZIONE DI TOMCAT
        String newUrl = "https://" + server + ":8443" +  context + path + (info != null ? info : "") + (query != null ? "?" + query : "");
        try {
            //ridirigiamo il client
            response.sendRedirect(newUrl);
        } catch (IOException ex) {
            try {
                //in caso di problemi tentiamo prima di inviare un errore HTTP standard
                response.sendError(response.SC_INTERNAL_SERVER_ERROR, "Cannot redirect to HTTPS, blocking request");
            } catch (IOException ex1) {
                //altrimenti generiamo un'eccezione
                throw new ServletException("Cannot redirect to https!");
            }
        }
    }
}
