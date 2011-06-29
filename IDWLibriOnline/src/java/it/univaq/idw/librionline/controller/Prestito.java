/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.controller;

import it.univaq.idw.librionline.framework.util.SecurityLayer;
import it.univaq.idw.librionline.framework.util.TemplateResult;
import it.univaq.idw.librionline.model.LibriOnLineDataLayer;
import it.univaq.idw.librionline.model.User;
import it.univaq.idw.librionline.model.Volume;
import it.univaq.idw.librionline.model.impl.LibriOnLineDataLayerMysqlImpl;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Zilfio
 */
public class Prestito extends HttpServlet {
    
    private boolean analizza_form_prestito(HttpServletRequest request, HttpServletResponse response) {
        
        LibriOnLineDataLayer dl = new LibriOnLineDataLayerMysqlImpl();
        
        String isbn = request.getParameter("prestito_isbn");
        String prestito_volumi = request.getParameter("prestito_volumi");
        String prestito_utenti = request.getParameter("prestito_utenti");
        
        int id_vol = Integer.parseInt(prestito_volumi);
        int id_user = Integer.parseInt(prestito_utenti);
        
        if((isbn == null) || (id_vol <= 0) || (id_user <= 0)){
            return false;
        }
        
        if(dl.registraPrestito(isbn, id_vol, id_user)){
            return true;
        }
        else{
            return false;
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
            throws ServletException, IOException {
        TemplateResult res = new TemplateResult(getServletContext());
        HttpSession session = SecurityLayer.checkSession(request);
        LibriOnLineDataLayer dl = new LibriOnLineDataLayerMysqlImpl();
        
        String isbn = request.getParameter("isbn");
        
        if(session != null){
            request.setAttribute("stato_log", "Logout");

            if(dl.isAdmin((String)session.getAttribute("username"))){
                request.setAttribute("bibliotecario",true);
                request.setAttribute("tipologia_utente","Bibliotecario");
                
                String prestito = request.getParameter("Conferma Prestito");
                
                if(prestito == null){
                
                    request.setAttribute("title","Prestito");
                    request.setAttribute("isbn",isbn);

                    List<Volume> vd = dl.getVolumiDisponibili(isbn);
                    List<User> allUser = dl.allUser();

                    request.setAttribute("prestitivolumi",vd);
                    request.setAttribute("prestitiusers",allUser);
                    res.activate("form_prestito.ftl.html", request, response);
                }
                else{
                    boolean result = analizza_form_prestito(request,response);
                    
                    if(result){
                        request.setAttribute("messaggio","Prestito eseguito correttamente");
                    }
                    else{
                        request.setAttribute("messaggio","Prestito fallito!");
                    }
                    request.setAttribute("title","Prestito");
                    request.setAttribute("isbn",request.getParameter("prestito_isbn"));
                    List<Volume> vd = dl.getVolumiDisponibili(isbn);
                    List<User> allUser = dl.allUser();

                    request.setAttribute("prestitivolumi",vd);
                    request.setAttribute("prestitiusers",allUser);
                    res.activate("form_prestito.ftl.html", request, response);
                }
            }
            else{
                request.setAttribute("bibliotecario",false);
                request.setAttribute("tipologia_utente","Utente");
            }
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
        processRequest(request, response);
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
        processRequest(request, response);
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
