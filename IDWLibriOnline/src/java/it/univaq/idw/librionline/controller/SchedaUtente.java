/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.controller;

import it.univaq.idw.librionline.framework.util.SecurityLayer;
import it.univaq.idw.librionline.framework.util.TemplateResult;
import it.univaq.idw.librionline.model.LibriOnLineDataLayer;
import it.univaq.idw.librionline.model.User;
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
public class SchedaUtente extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        TemplateResult template = new TemplateResult(getServletContext());
        HttpSession session = SecurityLayer.checkSession(request);
        LibriOnLineDataLayer model = new LibriOnLineDataLayerMysqlImpl();
        
        if(session != null){    
            
            request.setAttribute("stato_log", "Logout");
            
            if(model.isAdmin((String)session.getAttribute("username"))){
                request.setAttribute("bibliotecario",true);
                request.setAttribute("tipologia_utente","Bibliotecario");
                
                //controllo validita dell'ID dell'user
                String iduser = request.getParameter("id");
                int id_user = Integer.parseInt(iduser);

                if (id_user <= 0){
                    request.setAttribute("title", "Errore");
                    request.setAttribute("error_title", "Errore");
                    request.setAttribute("error", "Attenzione: ID Utente non immesso!");
                    request.setAttribute("navigazione","<a href='Home'>Homepage</a> -> <a href='RicercaUtente'>RicercaUtente</a>");
                    template.activate("error.ftl.html", request, response);
                }
                else{
                    User u = model.getUser(id_user);
                    List<it.univaq.idw.librionline.model.Prestito> p = model.getPrestitiAttivi(model.getUser(id_user).getUsername());
                    if (u == null){
                        request.setAttribute("title", "Errore");
                        request.setAttribute("error_title", "Errore");
                        request.setAttribute("error", "Attenzione: ID Utente non presente nel DB!");
                        request.setAttribute("navigazione","<a href='Home'>Homepage</a> -> <a href='RicercaUtente'>RicercaUtente</a>");
                        template.activate("error.ftl.html", request, response);
                    }
                    else{
                        request.setAttribute("title", "Scheda Utente: " + u.getUsername());
                        request.setAttribute("schedautente", u);
                        if(p.isEmpty()){
                            request.setAttribute("prestitiattivi", null);
                        }
                        else{
                            request.setAttribute("prestitiattivi", p);
                        }
                        request.setAttribute("navigazione","<a href='Home'>Homepage</a> -> <a href='RicercaUtente'>RicercaUtente</a>");
                        template.activate("schedautente.ftl.html", request, response); 
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
