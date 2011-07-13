/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.controller;

import it.univaq.idw.librionline.framework.util.Md5;
import it.univaq.idw.librionline.framework.util.SecurityLayer;
import it.univaq.idw.librionline.framework.util.TemplateResult;
import it.univaq.idw.librionline.model.Gruppo;
import it.univaq.idw.librionline.model.LibriOnLineDataLayer;
import it.univaq.idw.librionline.model.User;
import it.univaq.idw.librionline.model.impl.LibriOnLineDataLayerMysqlImpl;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Zilfio
 */
public class UpdateUtente extends HttpServlet {

    private boolean analizza_form_updateuser(HttpServletRequest request, HttpServletResponse response) throws NoSuchAlgorithmException, IOException {
        
        //prendo in post tutti i campi del form_registrazione
        String pass = Md5.md5(request.getParameter("backoffice_updateutente_password"));
        String email = request.getParameter("backoffice_updateutente_email");
        String tel = request.getParameter("backoffice_updateutente_tel");
        
        String nome = request.getParameter("backoffice_updateutente_nome");
        String cognome = request.getParameter("backoffice_updateutente_cognome");
        String codicefiscale = request.getParameter("backoffice_updateutente_codicefiscale");
        String indirizzo = request.getParameter("backoffice_updateutente_indirizzo");
        String citta = request.getParameter("backoffice_updateutente_citta");
        String provincia = request.getParameter("backoffice_updateutente_provincia");
        String cap = request.getParameter("backoffice_updateutente_cap");
        String gruppo = request.getParameter("backoffice_updateutente_gruppo");
        
        if(pass.equals("") || email.equals("") || tel.equals("") || nome.equals("") || cognome.equals("") || codicefiscale.equals("") || indirizzo.equals("") || citta.equals("") || provincia.equals("") || cap.equals("") || gruppo.equals("")){
            return false;
        }
        
        else{
            return true;
        }
    }
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NoSuchAlgorithmException {
        TemplateResult res = new TemplateResult(getServletContext());
        HttpSession session = SecurityLayer.checkSession(request);
                
        if(session != null){
            request.setAttribute("stato_log", "Logout");

            LibriOnLineDataLayer dl = new LibriOnLineDataLayerMysqlImpl();

            if(dl.isAdmin((String)session.getAttribute("username"))){
                request.setAttribute("bibliotecario",true);
                request.setAttribute("tipologia_utente","Bibliotecario");
                
                String update_user = request.getParameter("Modifica Utente");
        
                if(update_user == null){
                    String id = request.getParameter("id");
                    User user = dl.getUser(Integer.parseInt(id));
                    
                    request.setAttribute("title","Modifica Utente");
                    request.setAttribute("updateutente", user);
                    request.setAttribute("groupsnotgroups", dl.getNotGruppo(Integer.parseInt(id)));
                    request.setAttribute("navigazione","<a href='Home'>Homepage</a> -> <a href='VisualizzaUtente'>VisualizzaUtente</a>");
                    res.activate("backoffice_updateutente.ftl.html", request, response);
                }
                else if(update_user.equals("Modifica Utente")){
                    String id_user = request.getParameter("backoffice_updateutente_id");
                    String pass = request.getParameter("backoffice_updateutente_password");
                    String email = request.getParameter("backoffice_updateutente_email");
                    String tel = request.getParameter("backoffice_updateutente_tel");

                    String nome = request.getParameter("backoffice_updateutente_nome");
                    String cognome = request.getParameter("backoffice_updateutente_cognome");
                    String codicefiscale = request.getParameter("backoffice_updateutente_codicefiscale");
                    String indirizzo = request.getParameter("backoffice_updateutente_indirizzo");
                    String citta = request.getParameter("backoffice_updateutente_citta");
                    String provincia = request.getParameter("backoffice_updateutente_provincia");
                    String cap = request.getParameter("backoffice_updateutente_cap");
                    String gruppo = request.getParameter("backoffice_updateutente_gruppo");
                    
                    Gruppo g = dl.getGruppo(Integer.parseInt(gruppo));
                    
                    boolean result = analizza_form_updateuser(request, response);
                    if(result){
                        //Vecchia password in md5
                        String password = dl.getUser(Integer.parseInt(id_user)).getPassword();
                        if(!pass.equals(password)){
                            password = Md5.md5(pass);
                        }
                        if(dl.modificaUser(Integer.parseInt(id_user), password, email, tel, nome, cognome, codicefiscale, indirizzo, citta, provincia, Integer.parseInt(cap), g)){
                            request.setAttribute("messaggio", "Utente modificato correttamente!");                            
                        }
                        else{
                            request.setAttribute("messaggio", "Utente non modificato!");                            
                        }
                        User object_user = dl.getUser(Integer.parseInt(id_user));
                        request.setAttribute("updateutente", object_user);
                        request.setAttribute("groupsnotgroups", dl.getNotGruppo(Integer.parseInt(id_user)));
                        request.setAttribute("title", "Modifica Utente");
                        request.setAttribute("navigazione","<a href='Home'>Homepage</a> -> <a href='VisualizzaUtente'>VisualizzaUtente</a>");
                        res.activate("backoffice_updateutente.ftl.html", request, response);
                    }
                    else{
                        User object_user = dl.getUser(Integer.parseInt(id_user));
                        request.setAttribute("volume", object_user);
                        request.setAttribute("groupsnotgroups", dl.getNotGruppo(Integer.parseInt(id_user)));
                        request.setAttribute("title", "Modifica Utente");
                        request.setAttribute("messaggio", "Impossibile modificare l'utente!");
                        request.setAttribute("navigazione","<a href='Home'>Homepage</a> -> <a href='VisualizzaUtente'>VisualizzaUtente</a>");
                        res.activate("backoffice_updateutente.ftl.html", request, response);
                    }
                }
                else{
                    String id = request.getParameter("backoffice_updateutente_id");
                    if(dl.removeUser(Integer.parseInt(id))){
                        response.sendRedirect("VisualizzaUtente");
                    }
                }
            }
                    
            else{
                request.setAttribute("bibliotecario",false);
                request.setAttribute("tipologia_utente","Utente");
                response.sendRedirect("Home");
            }
        }
        else{
            response.sendRedirect("Home");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UpdateUtente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(UpdateUtente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
