/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.controller;

import it.univaq.idw.librionline.framework.util.TemplateResult;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Zilfio
 */
public class RicercaAvanzata extends HttpServlet {
    
    private Map analizza_form(HttpServletRequest request) throws IOException, ServletException {
        //per elaborare tutti i campi della request, ne richiedo la mappa
        //se volessi accedere a un campo particolare, potrei usare direttamente request.getParameter
        Map m = request.getParameterMap();
        Set<Entry<String, String[]>> fieldset = m.entrySet();
        for (Entry<String, String[]> field : fieldset) {
            //itero sui vari campi
            String nome = field.getKey();
            //il valore, anche se semplice, è sempre un array
            String[] valori = field.getValue();
        }
        return m;
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
                
                PrintWriter w = response.getWriter();
                TemplateResult res = new TemplateResult(getServletContext());
                
                String s = request.getParameter("Invia");
                
                if(s == null){
                    res.activate("form_ricerca_avanzata.ftl.html", request, response);
                }
                else{
                    request.setAttribute("headers", analizza_form(request));
                    res.activate("risultati_ricerca_avanzata.ftl.html", request, response);
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
        return "Servlet RicercaAvanzata";
    }// </editor-fold>

}
