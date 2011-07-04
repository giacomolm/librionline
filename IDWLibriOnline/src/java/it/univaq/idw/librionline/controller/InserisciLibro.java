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
import it.univaq.idw.librionline.model.Stato;
import it.univaq.idw.librionline.model.Tag;
import it.univaq.idw.librionline.model.impl.LibriOnLineDataLayerMysqlImpl;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


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
        String copie = request.getParameter("insertbook_numerocopie");
        String stato = request.getParameter("insertbook_stato");
        String durata_max = request.getParameter("insertbook_duratamax");
        
        if((isbn == null || isbn.isEmpty()) || (titolo == null || titolo.isEmpty()) || (editore == null || editore.isEmpty()) || (annoPubblicazione == null || annoPubblicazione.isEmpty())|| (autore == null) || (tag == null) || (copie == null || copie.isEmpty()) || (stato == null || stato.isEmpty()) || (durata_max == null || durata_max.isEmpty())){
            return false;
        }

        LibriOnLineDataLayer dl = new LibriOnLineDataLayerMysqlImpl();
        
        int id_lingua = Integer.parseInt(lingua);
        int n_copie = Integer.parseInt(copie);
        int id_stato = Integer.parseInt(stato);
        int durata = Integer.parseInt(durata_max);
        
        if(dl.insertBook(isbn, titolo, editore, annoPubblicazione, recensione, id_lingua, autore, tag, n_copie,durata, id_stato)){         
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
        PrintWriter out = response.getWriter();

        if(session != null){
            request.setAttribute("stato_log", "Logout");

            LibriOnLineDataLayer dl = new LibriOnLineDataLayerMysqlImpl();

            if(dl.isAdmin((String)session.getAttribute("username"))){
                request.setAttribute("bibliotecario",true);
                request.setAttribute("tipologia_utente","Bibliotecario");
                
                List<Lingua> lingue = dl.getAllLingua();
                List<Autore> autori = dl.getAllAutori();
                List<Tag> tags = dl.getAllTag();
                List<Stato> stati = dl.getAllStato();

                request.setAttribute("title","Libri");
                request.setAttribute("lingue",lingue);
                request.setAttribute("autori",autori);
                request.setAttribute("tags",tags);
                request.setAttribute("stati",stati);
                
                String insert_tag = request.getParameter("Inserisci Libro");
                
                if(insert_tag == null){
                    request.setAttribute("title","Inserisci Libro");
                    res.activate("backoffice_inseriscilibro.ftl.html", request, response);
                }
                else{
                    boolean result = analizza_form_libro(request,response);
                    if(result){  
                        if(ServletFileUpload.isMultipartContent(request)){
                            // prepariamo per l'upload della copia elettronica del libro
                            FileItemFactory factory = new DiskFileItemFactory();
                            ServletFileUpload upload = new ServletFileUpload(factory);
                            List<FileItem> items;
                            try {
                                //analizzo la richiesta
                                items = upload.parseRequest(request);
                                Iterator<FileItem> it = items.iterator();
                                //itero sugli elementi della richiesta
                                while (it.hasNext()) {
                                    //singolo elemento della richiesta
                                    FileItem item = it.next();
                                    String nome = item.getFieldName();
                                    if (item.isFormField()) {
                                        //l'elemento è un campo semplice
                                        String valore = item.getString();
                                        if (!valore.isEmpty()) {
                                            out.println("<p><b>" + nome + "</b>=" + valore + "</p>");
                                        } else {
                                            out.println("<p><b>" + nome + "</b>=<i>VUOTO</i></p>");
                                        }
                                    } else {
                                        //l'elemento è un file
                                        String fileName = item.getName();
                                        String contentType = item.getContentType();
                                        long size = item.getSize();
                                        //copia del file nel repository
                                        File f = new File(getServletContext().getRealPath("") + "/" + fileName);
                                        item.write(f);
                                        out.println("<p><b>" + nome + "</b>= FILE '" + fileName + "' (" + contentType + "," + size + " bytes)</p>");
                                    }
                                }
                                } catch (FileUploadException ex) {
                                    //qui va gestito ogni errore di upload
                                    ex.printStackTrace();
                                } catch (Exception ex) {
                                    //qui vanno gestiti gli errori derivanti dalla chiamata a write()
                                    ex.printStackTrace();
                                }
                        }
                        
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
            throws ServletException, IOException{
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
