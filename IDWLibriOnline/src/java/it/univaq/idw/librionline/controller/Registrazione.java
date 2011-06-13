/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.controller;

import it.univaq.idw.librionline.framework.util.Md5;
import it.univaq.idw.librionline.framework.util.TemplateResult;
import it.univaq.idw.librionline.model.Gruppo;
import it.univaq.idw.librionline.model.LibriOnLineDataLayer;
import it.univaq.idw.librionline.model.impl.LibriOnLineDataLayerMysqlImpl;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Zilfio
 */

public class Registrazione extends HttpServlet {
    
    private boolean analizza_form_registrazione(HttpServletRequest request, HttpServletResponse response) throws NoSuchAlgorithmException, IOException {
        
        PrintWriter w = response.getWriter();
        
        //prendo in post tutti i campi del form_registrazione
        String user = request.getParameter("user_reg");
        String pass = Md5.md5(request.getParameter("pass_reg"));
        String email = request.getParameter("email");
        String tel = request.getParameter("tel");
        
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String codicefiscale = request.getParameter("codicefiscale");
        String indirizzo = request.getParameter("indirizzo");
        String citta = request.getParameter("citta");
        String provincia = request.getParameter("provincia");
        String cap = request.getParameter("cap");
        
        int cap2 = Integer.parseInt(cap); 
        
        //Questo è l'oggetto che devi dichiarare per qualsiasi interazione con il DB
        LibriOnLineDataLayer dl = new LibriOnLineDataLayerMysqlImpl();
        
        //Ottengo il gruppo che voglio inserire
        Gruppo g = dl.getGruppo("registrato");
        
        //Eseguiamo la registrazione
        if(dl.insertUser(user, pass, email, tel, nome, cognome, codicefiscale, indirizzo, citta, provincia, cap2, g)){
            return true;
        }
        return false;
    }
    
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NoSuchAlgorithmException {
        
        TemplateResult res = new TemplateResult(getServletContext());
        
        String registrazione = request.getParameter("Registrazione");
        
        if(registrazione == null){
            res.activate("form_registrazione.ftl.html", request, response);
        }
        else{
            if(analizza_form_registrazione(request,response)){
                request.setAttribute("message","Registrazione eseguita correttamente!");
                res.activate("message.ftl.html", request, response);
            }
            else{
                request.setAttribute("message","Registrazione non eseguita!");
                res.activate("message.ftl.html", request, response);
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
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Registrazione.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Registrazione.class.getName()).log(Level.SEVERE, null, ex);
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
