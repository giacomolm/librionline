/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.controller;

import it.univaq.idw.librionline.framework.util.Md5;
import it.univaq.idw.librionline.framework.util.SecurityLayer;
import it.univaq.idw.librionline.framework.util.TemplateResult;
import it.univaq.idw.librionline.model.LibriOnLineDataLayer;
import it.univaq.idw.librionline.model.User;
import it.univaq.idw.librionline.model.impl.LibriOnLineDataLayerMysqlImpl;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.eclipse.persistence.internal.eis.cobol.helper.Helper;

/**
 *
 * @author Zilfio
 */
public class Login extends HttpServlet {
    
    private boolean analizza_form_login(HttpServletRequest request,HttpServletResponse response) throws IOException, NoSuchAlgorithmException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        if((username.equals("")) || (password.equals(""))){
            return false;
        }
        
        // converto la password inserita in md5
        password = Md5.md5(password);
        
        LibriOnLineDataLayer dl = new LibriOnLineDataLayerMysqlImpl();
        
        username = SecurityLayer.addSlashes(username);
        password = SecurityLayer.addSlashes(password); 
        
        //Questa è la funzione da richiamare per il login
        //Attenzione! restituisco un'oggetto user! Se non esiste alcun utente o se c'è stato qualche problema di autenticazione viene restituito un oggetto null
        User u = dl.login(username, password);
        
        if(u != null){
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
        HttpSession session = SecurityLayer.checkSession(request);
        String login = request.getParameter("Login");

        if(session != null){
            response.sendRedirect("StatoConnessione");
            
            LibriOnLineDataLayer dl = new LibriOnLineDataLayerMysqlImpl();
                
                if(dl.isAdmin((String)session.getAttribute("username"))){
                    request.setAttribute("bibliotecario",true);
                    request.setAttribute("tipologia_utente","Bibliotecario");
                }
                else{
                    request.setAttribute("bibliotecario",false);
                    request.setAttribute("tipologia_utente","Utente");
                }
        }
        else{
            if("Login".equals(login)){
                if(analizza_form_login(request,response)){
                    LibriOnLineDataLayer dl = new LibriOnLineDataLayerMysqlImpl();
                    SecurityLayer.createSession(request, request.getParameter(SecurityLayer.stripSlashes("username")) , dl.getGruppoByUsername(request.getParameter(SecurityLayer.stripSlashes("username"))));
                    response.sendRedirect("StatoConnessione");
                }
                else{
                    request.setAttribute("title","Login");
                    request.setAttribute("messaggio","Login Fallito: inserire correttamente 'username' e 'password'");
                    request.setAttribute("navigazione","<a href='Home'>Homepage</a>");
                    res.activate("form_login.ftl.html", request, response);
                }
            }
            else{
                request.setAttribute("title","Login");
                request.setAttribute("navigazione","<a href='Home'>Homepage</a>");
                res.activate("form_login.ftl.html", request, response);
            }
        }
        
    }  // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servelt Login";
    }// </editor-fold>
}
