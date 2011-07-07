/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.controller;

import it.univaq.idw.librionline.framework.util.SecurityLayer;
import it.univaq.idw.librionline.framework.util.TemplateResult;
import it.univaq.idw.librionline.model.LibriOnLineDataLayer;
import it.univaq.idw.librionline.model.Volume;
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
public class UpdateVolumi extends HttpServlet {

    private boolean analizza_form_volume(HttpServletRequest request, HttpServletResponse response) {
        String stato = request.getParameter("updatevol_stato");
        String duratamax = request.getParameter("updatevol_duratamax");
        
        if((stato == null || stato.isEmpty()) || (duratamax == null || duratamax.isEmpty())){
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
                
                String update = request.getParameter("Modifica Volume");
                
                if(update == null){
                    String id = request.getParameter("id");
                    Volume volume = dl.getVolume(Integer.parseInt(id));
                    
                    request.setAttribute("title", "Modifica Volume");
                    request.setAttribute("volume", volume);
                    res.activate("backoffice_updatevolumi.ftl.html", request, response);
                }
                
                else if(update.equals("Modifica Volume")){
                    String id = request.getParameter("updatevol_id");
                    int id_volume = Integer.parseInt(id);
                    String stato = request.getParameter("updatevol_stato");
                    int stato_id = Integer.parseInt(stato);
                    String duratamax = request.getParameter("updatevol_duratamax");
                    int durata = Integer.parseInt(duratamax);
                    
                    boolean result = analizza_form_volume(request, response);
                    if(result){
                        if(dl.modificaVolume(id_volume, durata, stato_id)){
                            request.setAttribute("messaggio", "Volume modificato correttamente!");                            
                        }
                        else{
                            request.setAttribute("messaggio", "Volume non modificato!");                            
                        }
                        Volume object_volume = dl.getVolume(id_volume);
                        request.setAttribute("volume", object_volume);
                        request.setAttribute("title", "Modifica Volume");
                        res.activate("backoffice_updatevolumi.ftl.html", request, response);
                    }
                    else{
                        Volume object_volume = dl.getVolume(id_volume);
                        request.setAttribute("volume", object_volume);
                        request.setAttribute("title", "Modifica Volume");
                        request.setAttribute("messaggio", "Impossibile modificare il volume!");
                        res.activate("backoffice_updatevolumi.ftl.html", request, response);
                    }
                }
                else{
                    String id = request.getParameter("updatevol_id");
                    int id_volume = Integer.parseInt(id);
                    String isbn = request.getParameter("updatevol_isbn");
                    if(dl.eliminaVolume(id_volume)){
                        response.sendRedirect("VisualizzaVolume2?isbn="+isbn);
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
