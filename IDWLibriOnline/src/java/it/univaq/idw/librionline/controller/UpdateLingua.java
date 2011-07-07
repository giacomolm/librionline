/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.controller;

import it.univaq.idw.librionline.framework.util.SecurityLayer;
import it.univaq.idw.librionline.framework.util.TemplateResult;
import it.univaq.idw.librionline.model.LibriOnLineDataLayer;
import it.univaq.idw.librionline.model.Lingua;
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
public class UpdateLingua extends HttpServlet {

    private boolean analizza_form_lingua(HttpServletRequest request, HttpServletResponse response) {
        
        String lingua = request.getParameter("updatelingua_lingua");
        
        if(lingua == null || lingua.isEmpty()){
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
                
                String update = request.getParameter("Modifica Lingua");
                
                if(update == null){
                    String id = request.getParameter("id");
                    Lingua lingua = dl.getLingua(Integer.parseInt(id));
                    
                    request.setAttribute("title", "Modifica Lingua");
                    request.setAttribute("lingua", lingua);
                    res.activate("backoffice_updatelingua.ftl.html", request, response);
                }
                
                else if(update.equals("Modifica Lingua")){
                    String id = request.getParameter("updatelingua_id");
                    int id_lingua = Integer.parseInt(id);
                    String lingua = request.getParameter("updatelingua_lingua");
                    boolean result = analizza_form_lingua(request, response);
                    if(result){
                        if(dl.modificaLingua(id_lingua, lingua)){
                            request.setAttribute("messaggio", "Lingua modificata correttamente!");
                        }
                        else{
                            request.setAttribute("messaggio", "Lingua non modificata!");
                        }
                        Lingua object_lingua = dl.getLingua(id_lingua);
                        request.setAttribute("lingua", object_lingua);
                        request.setAttribute("title", "Modifica Lingua");
                        
                        res.activate("backoffice_updatelingua.ftl.html", request, response);
                    }
                    else{
                        Lingua object_lingua = dl.getLingua(id_lingua);
                        request.setAttribute("stato", object_lingua);
                        request.setAttribute("title", "Modifica Lingua");
                        request.setAttribute("messaggio", "Impossibile modificare la Lingua!");
                        res.activate("backoffice_updatelingua.ftl.html", request, response);
                    }
                }
                else{
                    String id = request.getParameter("updatelingua_id");
                    int id_lingua = Integer.parseInt(id);
                    
                    if(dl.eliminaLingua(id_lingua)){
                        response.sendRedirect("VisualizzaLingue");
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
