/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.controller;

import it.univaq.idw.librionline.framework.util.SecurityLayer;
import it.univaq.idw.librionline.framework.util.TemplateResult;
import it.univaq.idw.librionline.model.Copiaelettronica;
import it.univaq.idw.librionline.model.LibriOnLineDataLayer;
import it.univaq.idw.librionline.model.Libro;
import it.univaq.idw.librionline.model.impl.LibriOnLineDataLayerMysqlImpl;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataSource;
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
public class SchedaDettaglioLibro extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NamingException {
        
        TemplateResult template = new TemplateResult(getServletContext());
        HttpSession session = SecurityLayer.checkSession(request);
	//controllo validita dell'ID del libro
	String isbn = request.getParameter("isbn");
	if (isbn == null){
	    request.setAttribute("title", "Errore");
            request.setAttribute("error", "Attenzione: Codice ISBN non immesso!");
            template.activate("error.ftl.html", request, response);
	}
	else{
	    LibriOnLineDataLayer model = new LibriOnLineDataLayerMysqlImpl();
	    Libro l = model.searchByIsbn(isbn);

	    if (l == null){
                request.setAttribute("title", "Errore");
		request.setAttribute("error", "Attenzione: Codice ISBN non presente nel DB!");
                template.activate("error.ftl.html", request, response);
	    }
	    else{
		// carico template
                request.setAttribute("title", l.getTitolo());
		request.setAttribute("libro", l);
                request.setAttribute("lingua", l.getLingua());
                request.setAttribute("autori", l.getAutoreCollection());
                request.setAttribute("tags", l.getTagCollection());
                
                int numerocopie = model.getNumeroCopie(isbn);
                request.setAttribute("copietotali", numerocopie);
                    
                int numerocopiedisponibili = model.getNumeroCopieDisponibili(isbn);
                request.setAttribute("copiedisponibili", numerocopiedisponibili);

                if(numerocopiedisponibili == 0){
                request.setAttribute("datapresuntarestituzione", model.getProssimoData(isbn));
                }
                
                if(session != null){
                    Collection<Copiaelettronica> ce = l.getCopiaelettronicaCollection();
                    if(ce.isEmpty()){
                        request.setAttribute("copieelettroniche",null);
                    }
                    else{
                        request.setAttribute("copieelettroniche",ce);
                    }

                    InitialContext ctx = new InitialContext();
                    String path = getServletContext().getInitParameter("copieelettroniche");
                    request.setAttribute("path",path);
                    if(model.isAdmin((String)session.getAttribute("username"))){
                        request.setAttribute("bibliotecario",true);
                        request.setAttribute("tipologia_utente","Bibliotecario");
                    }
                    else{
                        request.setAttribute("bibliotecario",false);
                        request.setAttribute("tipologia_utente","Utente");
                    }
                    
                    request.setAttribute("stato_log", "Logout");
                }
                request.setAttribute("navigazione","<a href='Home'>Homepage</a> -> <a href='Libro'>Libro</a>");
                template.activate("schedalibro.ftl.html", request, response); 
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
        try {
            processRequest(request, response);
        } catch (NamingException ex) {
            Logger.getLogger(SchedaDettaglioLibro.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(SchedaDettaglioLibro.class.getName()).log(Level.SEVERE, null, ex);
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
