/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.controller;

import it.univaq.idw.librionline.framework.util.SecurityLayer;
import it.univaq.idw.librionline.framework.util.TemplateResult;
import it.univaq.idw.librionline.model.LibriOnLineDataLayer;
import it.univaq.idw.librionline.model.Tag;
import it.univaq.idw.librionline.model.impl.LibriOnLineDataLayerMysqlImpl;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Zilfio
 */
public class UpdateTag extends HttpServlet {

    private boolean analizza_form_tag(HttpServletRequest request, HttpServletResponse response) {
        
        String tag = request.getParameter("updatetag_tag");
        
        if(tag == null || tag.isEmpty()){
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
            throws ServletException, IOException {
        TemplateResult res = new TemplateResult(getServletContext());
        HttpSession session = SecurityLayer.checkSession(request);
        LibriOnLineDataLayer dl = new LibriOnLineDataLayerMysqlImpl();
        PrintWriter w = response.getWriter();
        
        if(session != null){
            request.setAttribute("stato_log", "Logout");

            if(dl.isAdmin((String)session.getAttribute("username"))){
                request.setAttribute("bibliotecario",true);
                request.setAttribute("tipologia_utente","Bibliotecario");
                
                String update = request.getParameter("Modifica Tag");
                
                if(update == null){
                    String id = request.getParameter("id");
                    Tag tag = dl.getTag(Integer.parseInt(id));
                    
                    request.setAttribute("title", "Modifica Tag");
                    request.setAttribute("tag", tag);
                    request.setAttribute("navigazione","<a href='Home'>Homepage</a> -> <a href='Visualizza'>Modifica</a> -> <a href='VisualizzaTag'>VisualizzaTag</a>");
                    res.activate("backoffice_updatetag.ftl.html", request, response);
                }
                
                else if(update.equals("Modifica Tag")){
                    String id = request.getParameter("updatetag_id");
                    int id_tag = Integer.parseInt(id);
                    String tag = request.getParameter("updatetag_tag");
                    boolean result = analizza_form_tag(request, response);
                    if(result){
                        if(dl.modificaTag(id_tag, tag)){
                            request.setAttribute("messaggio", "Tag modificato correttamente!");
                        }
                        else{
                            request.setAttribute("messaggio", "Tag non modificato!");
                        }
                        Tag object_tag = dl.getTag(id_tag);
                        request.setAttribute("tag", object_tag);
                        request.setAttribute("title", "Modifica Tag");
                        request.setAttribute("navigazione","<a href='Home'>Homepage</a> -> <a href='Visualizza'>Modifica</a> -> <a href='VisualizzaTag'>VisualizzaTag</a>");
                        res.activate("backoffice_updatetag.ftl.html", request, response);
                    }
                    else{
                        Tag object_tag = dl.getTag(id_tag);
                        request.setAttribute("tag", object_tag);
                        request.setAttribute("title", "Modifica Tag");
                        request.setAttribute("messaggio", "Impossibile modificare il Tag!");
                        request.setAttribute("navigazione","<a href='Home'>Homepage</a> -> <a href='Visualizza'>Modifica</a> -> <a href='VisualizzaTag'>VisualizzaTag</a>");
                        res.activate("backoffice_updatetag.ftl.html", request, response);
                    }
                }
                else{
                    String id = request.getParameter("updatetag_id");
                    int id_tag = Integer.parseInt(id);
                    
                    if(dl.eliminaTag(id_tag)){                    
                        response.sendRedirect("VisualizzaTag");
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
