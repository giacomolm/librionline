/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.controller;

import it.univaq.idw.librionline.framework.util.SecurityLayer;
import it.univaq.idw.librionline.framework.util.TemplateResult;
import it.univaq.idw.librionline.model.LibriOnLineDataLayer;
import it.univaq.idw.librionline.model.Libro;
import it.univaq.idw.librionline.model.Stato;
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
public class InserisciVolume2 extends HttpServlet {

    private boolean analizza_form_volume(HttpServletRequest request, HttpServletResponse response) {
      
        String numero_volumi = request.getParameter("insertvol_numerocopie");
        String stato = request.getParameter("insertvol_stato");
        String durata = request.getParameter("insertvol_duratamax");
        
        if((numero_volumi == null || numero_volumi.isEmpty()) || (stato == null || stato.isEmpty()) || (durata == null || durata.isEmpty())){
            return false;
        }
        
        LibriOnLineDataLayer dl = new LibriOnLineDataLayerMysqlImpl();
        
        int volumi = Integer.parseInt(numero_volumi);
        int id_stato = Integer.parseInt(stato);
        int durata_max = Integer.parseInt(durata);
        
        String isbn = request.getParameter("isbn");
        Libro l = dl.searchByIsbn(isbn);

        for(int i=0;i<volumi;i++){
            if(!dl.insertVolume(l, durata_max, id_stato)){
                return false;
            }
        }
        return true;
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
        
        if(session != null){
            request.setAttribute("stato_log", "Logout");

            LibriOnLineDataLayer dl = new LibriOnLineDataLayerMysqlImpl();

            if(dl.isAdmin((String)session.getAttribute("username"))){
                request.setAttribute("bibliotecario",true);
                request.setAttribute("tipologia_utente","Bibliotecario");
                
                List<Stato> stati = dl.getAllStato();
                
                request.setAttribute("stati",stati);
                
                String insert_vol = request.getParameter("Inserisci Volume");
                
                if(insert_vol == null){
                    request.setAttribute("title","Inserisci Volume");
                    res.activate("backoffice_inseriscivolume.ftl.html", request, response);
                }
                else{
                    boolean result = analizza_form_volume(request,response);
                    if(result){
                        request.setAttribute("title","Inserisci Volume");
                        request.setAttribute("messaggio","Il Volume è stato inserito correttamente!");
                        res.activate("backoffice_inseriscivolume.ftl.html", request, response);
                    }
                    else{
                        request.setAttribute("title","Inserisci Volume");
                        request.setAttribute("messaggio","Inserimento Volume fallito: Si prega di compilare bene i campi sottostanti!");
                        res.activate("backoffice_inseriscivolume.ftl.html", request, response);
                    }         
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
