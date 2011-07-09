/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.controller;

import it.univaq.idw.librionline.framework.util.MultipartHttpServletRequest;
import it.univaq.idw.librionline.framework.util.SecurityLayer;
import it.univaq.idw.librionline.framework.util.TemplateResult;
import it.univaq.idw.librionline.model.LibriOnLineDataLayer;
import it.univaq.idw.librionline.model.Libro;
import it.univaq.idw.librionline.model.impl.LibriOnLineDataLayerMysqlImpl;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Zilfio
 */
public class InserisciCopiaElettronica2 extends HttpServlet {
    private String isbn;

    private boolean analizza_form_copiaelettronica(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String insfile = request.getParameter("insertcopiaelettronica_file");
        String ISBN = request.getParameter("isbn");
        
        if((insfile == null || insfile.isEmpty())){
            return false;
        }
        else{
            String nome_file = request.getParameter("insertcopiaelettronica_file_name");
            String type_file = request.getParameter("insertcopiaelettronica_file_type");
            int size_file = Integer.parseInt(request.getParameter("insertcopiaelettronica_file_size"));
            LibriOnLineDataLayer dl = new LibriOnLineDataLayerMysqlImpl();
            if(size_file > 0){
                InputStream is = ((MultipartHttpServletRequest)request).getStream("insertcopiaelettronica_file");
                if("application/force-download".equals(type_file)){
                    String type = "pdf";
                    File file = new File(getServletContext().getRealPath("")+"/copie_elettroniche/"+ISBN+"."+type);
                    OutputStream out=new FileOutputStream(file);
                    byte buf[]=new byte[1024];
                    int len;
                    while((len=is.read(buf))>0)
                        out.write(buf,0,len);
                    out.close();
                    is.close();
                    Libro libro = dl.searchByIsbn(ISBN);
                    if(libro != null){
                        if(dl.insertCopiaElettronica(dl.searchByIsbn(ISBN), type)){
                            return true;
                        }
                        else{
                           return false; 
                        } 
                    }
                    else return false;
                }
                else if("text/plain".equals(type_file)){
                    String type = "txt";
                    File file = new File(getServletContext().getRealPath("")+"/copie_elettroniche/"+ISBN+"."+type);
                    OutputStream out=new FileOutputStream(file);
                    byte buf[]=new byte[1024];
                    int len;
                    while((len=is.read(buf))>0)
                        out.write(buf,0,len);
                    out.close();
                    is.close();
                    Libro libro = dl.searchByIsbn(ISBN);
                    if(libro != null){
                        if(dl.insertCopiaElettronica(dl.searchByIsbn(ISBN), type)){
                            return true;
                        }
                        else{
                           return false; 
                        } 
                    }
                    else return false;
                }
                else if("application/msword".equals(type_file)){
                    String type = "doc";
                    File file = new File(getServletContext().getRealPath("")+"/copie_elettroniche/"+ISBN+"."+type);
                    OutputStream out=new FileOutputStream(file);
                    byte buf[]=new byte[1024];
                    int len;
                    while((len=is.read(buf))>0)
                        out.write(buf,0,len);
                    out.close();
                    is.close();
                    Libro libro = dl.searchByIsbn(ISBN);
                    if(libro != null){
                        if(dl.insertCopiaElettronica(dl.searchByIsbn(ISBN), type)){
                            return true;
                        }
                        else{
                           return false; 
                        } 
                    }
                    else return false;
                }
                else{
                    return false;
                }
            }
            else{
                return false;
            }
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
        
        if(session != null){
            request.setAttribute("stato_log", "Logout");

            LibriOnLineDataLayer dl = new LibriOnLineDataLayerMysqlImpl();

            if(dl.isAdmin((String)session.getAttribute("username"))){
                request.setAttribute("bibliotecario",true);
                request.setAttribute("tipologia_utente","Bibliotecario");
                
                String insert_copia = request.getParameter("Inserisci Copia Elettronica");
                
                if(insert_copia == null){
                    request.setAttribute("title","Inserisci Copia Elettronica");
                    res.activate("backoffice_inseriscicopiaelettronica.ftl.html", request, response);
                }
                else{
                    boolean result = analizza_form_copiaelettronica(request,response);
                    if(result){
                        request.setAttribute("title","Inserisci Copia Elettronica");
                        request.setAttribute("messaggio","La Copia Elettronica è stata inserita correttamente!");
                        res.activate("backoffice_inseriscicopiaelettronica.ftl.html", request, response);
                    }
                    else{
                        request.setAttribute("title","Inserisci Lingua");
                        request.setAttribute("messaggio","Inserimento Copia Elettronica fallito: Si prega di compilare bene i campi sottostanti!");
                        res.activate("backoffice_inseriscicopiaelettronica.ftl.html", request, response);
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
