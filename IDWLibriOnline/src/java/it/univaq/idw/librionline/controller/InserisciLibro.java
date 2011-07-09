/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.controller;
 
import it.univaq.idw.librionline.framework.util.MultipartHttpServletRequest;
import it.univaq.idw.librionline.framework.util.SecurityLayer;
import it.univaq.idw.librionline.framework.util.TemplateResult;
import it.univaq.idw.librionline.model.Autore;
import it.univaq.idw.librionline.model.LibriOnLineDataLayer;
import it.univaq.idw.librionline.model.Libro;
import it.univaq.idw.librionline.model.Lingua;
import it.univaq.idw.librionline.model.Stato;
import it.univaq.idw.librionline.model.Tag;
import it.univaq.idw.librionline.model.impl.LibriOnLineDataLayerMysqlImpl;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
            String nome_file = request.getParameter("insertbook_file_name");
            String type_file = request.getParameter("insertbook_file_type");
            int size_file = Integer.parseInt(request.getParameter("insertbook_file_size"));
            if(size_file > 0){
                InputStream is = ((MultipartHttpServletRequest)request).getStream("insertbook_file");
                if("application/force-download".equals(type_file)){
                    String type = "pdf";
                    File file = new File(getServletContext().getRealPath("")+"/copie_elettroniche/"+isbn+"."+type);
                    OutputStream out=new FileOutputStream(file);
                    byte buf[]=new byte[1024];
                    int len;
                    while((len=is.read(buf))>0)
                        out.write(buf,0,len);
                    out.close();
                    is.close();
                    Libro libro = dl.searchByIsbn(isbn);
                    if(libro != null){
                        dl.insertCopiaElettronica(dl.searchByIsbn(isbn), type);
                    }
                }
                else if("text/plain".equals(type_file)){
                    String type = "txt";
                    File file = new File(getServletContext().getRealPath("")+"/copie_elettroniche/"+isbn+"."+type);
                    OutputStream out=new FileOutputStream(file);
                    byte buf[]=new byte[1024];
                    int len;
                    while((len=is.read(buf))>0)
                        out.write(buf,0,len);
                    out.close();
                    is.close();
                    Libro libro = dl.searchByIsbn(isbn);
                    if(libro != null){
                        dl.insertCopiaElettronica(dl.searchByIsbn(isbn), type);
                    }
                }
                else if("application/msword".equals(type_file)){
                    String type = "doc";
                    File file = new File(getServletContext().getRealPath("")+"/copie_elettroniche/"+isbn+"."+type);
                    OutputStream out=new FileOutputStream(file);
                    byte buf[]=new byte[1024];
                    int len;
                    while((len=is.read(buf))>0)
                        out.write(buf,0,len);
                    out.close();
                    is.close();
                    Libro libro = dl.searchByIsbn(isbn);
                    if(libro != null){
                        dl.insertCopiaElettronica(dl.searchByIsbn(isbn), type);
                    }
                }
            }
            
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
                
                String insert_book = request.getParameter("Inserisci Libro");
                
                if(insert_book == null){
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
