/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.controller;

import it.univaq.idw.librionline.framework.util.SecurityLayer;
import it.univaq.idw.librionline.framework.util.TemplateResult;
import it.univaq.idw.librionline.model.Autore;
import it.univaq.idw.librionline.model.Copiaelettronica;
import it.univaq.idw.librionline.model.LibriOnLineDataLayer;
import it.univaq.idw.librionline.model.Libro;
import it.univaq.idw.librionline.model.Tag;
import it.univaq.idw.librionline.model.impl.LibriOnLineDataLayerMysqlImpl;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Zilfio
 */
public class UpdateLibro extends HttpServlet {

    private boolean analizza_form_libro(HttpServletRequest request, HttpServletResponse response) throws IOException {
        
        String isbn = request.getParameter("updatebook_isbn");
        String titolo = request.getParameter("updatebook_titolo");
        String editore = request.getParameter("updatebook_editore");
        String annoPubblicazione = request.getParameter("updatebook_annopubblicazione");
        String recensione = request.getParameter("updatebook_recensione");
        String lingua = request.getParameter("updatebook_lingua");
        String[] autore = request.getParameterValues("updatebook_autore");
        String[] tag = request.getParameterValues("updatebook_tag");
        
        if((isbn == null || isbn.isEmpty()) || (titolo == null || titolo.isEmpty()) || (editore == null || editore.isEmpty()) || (annoPubblicazione == null || annoPubblicazione.isEmpty())|| (autore == null) || (tag == null)){
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
            throws ServletException, IOException, NamingException {
        TemplateResult res = new TemplateResult(getServletContext());
        HttpSession session = SecurityLayer.checkSession(request);
        LibriOnLineDataLayer dl = new LibriOnLineDataLayerMysqlImpl();
        
        if(session != null){
            request.setAttribute("stato_log", "Logout");

            if(dl.isAdmin((String)session.getAttribute("username"))){
                request.setAttribute("bibliotecario",true);
                request.setAttribute("tipologia_utente","Bibliotecario");
                
                String update = request.getParameter("Modifica Libro");
                
                if(update == null){
                    String isbn = request.getParameter("id");
                    Libro libro = dl.searchByIsbn(isbn);
                    Collection<Autore> autori = libro.getAutoreCollection();
                    Collection<Autore> autorinotautori = dl.notAuthor(isbn);
                    Collection<Tag> tags = libro.getTagCollection();
                    Collection<Tag> tagsnottags = dl.notAtag(isbn);
                    
                    request.setAttribute("title", "Modifica libro");
                    request.setAttribute("libro", libro);
                    request.setAttribute("autori", autori);
                    request.setAttribute("autorinotautori", autorinotautori);
                    request.setAttribute("tags", tags);
                    request.setAttribute("tagsnottags", tagsnottags);
                    request.setAttribute("navigazione","<a href='Home'>Homepage</a> -> <a href='Visualizza'>Modifica</a> -> <a href='VisualizzaLibri'>VisualizzaLibri</a>");
                    res.activate("backoffice_updatelibro.ftl.html", request, response);
                }
                
                else if(update.equals("Modifica Libro")){
                    String isbn = request.getParameter("updatebook_isbn");
                    String titolo = request.getParameter("updatebook_titolo");
                    String editore = request.getParameter("updatebook_editore");
                    String annoPubblicazione = request.getParameter("updatebook_annopubblicazione");
                    String recensione = request.getParameter("updatebook_recensione");
                    String lingua = request.getParameter("updatebook_lingua");
                    String[] autore = request.getParameterValues("updatebook_autore");
                    String[] tag = request.getParameterValues("updatebook_tag");
                    int id_lingua = Integer.parseInt(lingua);
                    boolean result = analizza_form_libro(request, response);
                    
                    if(result){
                        if(dl.modificaLibro(isbn, titolo, editore, annoPubblicazione, recensione, id_lingua, autore, tag)){
                            request.setAttribute("messaggio", "Libro modificato correttamente!");
                        }
                        else{
                            request.setAttribute("messaggio", "Libro non modificato!");
                        }
                        Libro object_libro = dl.searchByIsbn(isbn);
                        Collection<Autore> autori = object_libro.getAutoreCollection();
                        Collection<Autore> autorinotautori = dl.notAuthor(isbn);
                        Collection<Tag> tags = object_libro.getTagCollection();
                        Collection<Tag> tagsnottags = dl.notAtag(isbn);
                    
                        request.setAttribute("title", "Modifica libro");
                        request.setAttribute("libro", object_libro);
                        request.setAttribute("autori", autori);
                        request.setAttribute("autorinotautori", autorinotautori);
                        request.setAttribute("tags", tags);
                        request.setAttribute("tagsnottags", tagsnottags);
                        res.activate("backoffice_updatelibro.ftl.html", request, response);
                    }
                    else{
                        Libro object_libro = dl.searchByIsbn(isbn);
                        Collection<Autore> autori = object_libro.getAutoreCollection();
                        Collection<Autore> autorinotautori = dl.notAuthor(isbn);
                        Collection<Tag> tags = object_libro.getTagCollection();
                        Collection<Tag> tagsnottags = dl.notAtag(isbn);
                    
                        request.setAttribute("title", "Modifica libro");
                        request.setAttribute("libro", object_libro);
                        request.setAttribute("autori", autori);
                        request.setAttribute("autorinotautori", autorinotautori);
                        request.setAttribute("tags", tags);
                        request.setAttribute("tagsnottags", tagsnottags);
                        request.setAttribute("messaggio", "Update libro Fallito");
                        res.activate("backoffice_updatelibro.ftl.html", request, response);
                    }
                }
                else{
                    String isbn = request.getParameter("updatebook_isbn");
                    
                        Libro libro = dl.searchByIsbn(isbn);
                        Collection<Copiaelettronica> ce = libro.getCopiaelettronicaCollection();

                        if(!ce.isEmpty()){
                            for(Copiaelettronica c : ce){
                                InitialContext ctx = new InitialContext();
                                
                                String path = getServletContext().getInitParameter("copieelettroniche");
                                String filename = (getServletContext().getRealPath(path+"/"+c.getUrl()));
                                File f = new File(filename);
                                if (!f.exists())
                                throw new IllegalArgumentException(
                                      "Delete: no such file or directory: " + filename);

                                if (!f.canWrite())
                                  throw new IllegalArgumentException("Delete: write protected: "
                                      + filename);

                                // If it is a directory, make sure it is empty
                                if (f.isDirectory()) {
                                  String[] files = f.list();
                                  if (files.length > 0)
                                    throw new IllegalArgumentException(
                                        "Delete: directory not empty: " + filename);
                                }

                                // Attempt to delete it
                                boolean success = f.delete();
                                if(success){
                                    dl.eliminaLibro(isbn);
                                    response.sendRedirect("VisualizzaLibri");
                                }

                                if (!success)
                                  throw new IllegalArgumentException("Delete: deletion failed");
                              }
                            }
                        else{
                            dl.eliminaLibro(isbn);
                            response.sendRedirect("VisualizzaLibri");
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
        } catch (NamingException ex) {
            Logger.getLogger(UpdateLibro.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (NamingException ex) {
            Logger.getLogger(UpdateLibro.class.getName()).log(Level.SEVERE, null, ex);
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
