/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.controller;

import it.univaq.idw.librionline.framework.util.SecurityLayer;
import it.univaq.idw.librionline.framework.util.TemplateResult;
import it.univaq.idw.librionline.model.Autore;
import it.univaq.idw.librionline.model.LibriOnLineDataLayer;
import it.univaq.idw.librionline.model.Lingua;
import it.univaq.idw.librionline.model.Tag;
import it.univaq.idw.librionline.model.impl.LibriOnLineDataLayerMysqlImpl;
import java.io.IOException;
import java.io.PrintWriter;
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
public class InserisciLibro extends HttpServlet {
    
    private boolean analizza_form_libro(HttpServletRequest request, HttpServletResponse response) throws IOException {
      
        String isbn = request.getParameter("insertbook_isbn");
        String titolo = request.getParameter("insertbook_titolo");
        String editore = request.getParameter("insertbook_editore");
        String annoPubblicazione = request.getParameter("insertbook_annopubblicazione");
        String recensione = request.getParameter("insertbook_recensione");
        String lingua = request.getParameter("insertbook_lingua");
        String[] autore = request.getParameterValues("insertbook_autore");
        String[] tag = request.getParameterValues("insertbook_tag");
        
        PrintWriter  w = response.getWriter();
        w.println(tag);
        
        if(isbn == null || isbn.isEmpty()){
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
        
        if(session != null){
            request.setAttribute("stato_log", "Logout");

            LibriOnLineDataLayer dl = new LibriOnLineDataLayerMysqlImpl();

            if(dl.isAdmin((String)session.getAttribute("username"))){
                request.setAttribute("bibliotecario",true);
                request.setAttribute("tipologia_utente","Bibliotecario");
                
                List<Lingua> lingue = dl.getAllLingua();
                List<Autore> autori = dl.getAllAutori();
                List<Tag> tags = dl.getAllTag();

                request.setAttribute("title","Libri");
                request.setAttribute("lingue",lingue);
                request.setAttribute("autori",autori);
                request.setAttribute("tags",tags);
                
                String insert_tag = request.getParameter("Inserisci Libro");
                
                if(insert_tag == null){
                    request.setAttribute("title","Inserisci Libro");
                    res.activate("backoffice_inseriscilibro.ftl.html", request, response);
                }
                else{
                    boolean result = analizza_form_libro(request,response);
                    if(result){
                        request.setAttribute("title","Inserisci Autore");
                        request.setAttribute("messaggio","Il Libro è stato inserito correttamente!");
                        res.activate("backoffice_inseriscilibro.ftl.html", request, response);
                    }
                    else{
                        request.setAttribute("title","Inserisci Libro");
                        request.setAttribute("messaggio","Inserimento Libro fallito: Si prega di compilare bene i campi sottostanti!");
                        res.activate("backoffice_inseriscilibro.ftl.html", request, response);
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
