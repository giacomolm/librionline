/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.controller;

import it.univaq.idw.librionline.framework.util.SecurityLayer;
import it.univaq.idw.librionline.framework.util.TemplateResult;
import it.univaq.idw.librionline.model.LibriOnLineDataLayer;
import it.univaq.idw.librionline.model.Stato;
import it.univaq.idw.librionline.model.impl.LibriOnLineDataLayerMysqlImpl;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Zilfio
 */
public class UpdateStato extends HttpServlet {
    
    private boolean analizza_form_stato(HttpServletRequest request, HttpServletResponse response) {
        
        String stato = request.getParameter("updatestato_stato");
        
        if(stato == null || stato.isEmpty()){
            return false;
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
        LibriOnLineDataLayer dl = new LibriOnLineDataLayerMysqlImpl();
        
        if(session != null){
            request.setAttribute("stato_log", "Logout");

            if(dl.isAdmin((String)session.getAttribute("username"))){
                request.setAttribute("bibliotecario",true);
                request.setAttribute("tipologia_utente","Bibliotecario");
                
                String update = request.getParameter("Modifica Stato");
                
                if(update == null){
                    String id = request.getParameter("id");
                    Stato stato = dl.getStato(Integer.parseInt(id));
                    
                    request.setAttribute("title", "Modifica Stato");
                    request.setAttribute("stato", stato);
                    res.activate("backoffice_updatestato.ftl.html", request, response);
                }
                
                else if(update.equals("Modifica Stato")){
                    String id = request.getParameter("updatestato_id");
                    int id_stato = Integer.parseInt(id);
                    String stato = request.getParameter("updatestato_stato");
                    boolean result = analizza_form_stato(request, response);
                    if(result){
                        if(dl.modificaStato(id_stato, stato)){
                            request.setAttribute("messaggio", "Stato modificato correttamente!");
                        }
                        else{
                            request.setAttribute("messaggio", "Stato non modificato!");
                        }
                        Stato object_stato = dl.getStato(id_stato);
                        request.setAttribute("stato", object_stato);
                        request.setAttribute("title", "Modifica Stato");
                        res.activate("backoffice_updatestato.ftl.html", request, response);
                    }
                    else{
                        Stato object_stato = dl.getStato(id_stato);
                        request.setAttribute("stato", object_stato);
                        request.setAttribute("title", "Modifica Stato");
                        request.setAttribute("messaggio", "Impossibile modificare lo Stato!");
                        res.activate("backoffice_updatestato.ftl.html", request, response);
                    }
                }
                else{
                    String id = request.getParameter("updatestato_id");
                    int id_stato = Integer.parseInt(id);
                    
                    if(dl.eliminaStato(id_stato)){
                        response.sendRedirect("VisualizzaStati");
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
