/*
 * Questa classe implementa in Servlet Filter, cioè una classe che intercetta
 * e gestisce il traffico di dati in entrata e in uscita dalle servlet.
 * In questo caso, il filtro sostituisce la request che arriverà alla servlet
 * con una MultipartHttpServletRequest, nel caso in cui la request stessa
 * contenga dati codificati in multipart.
 *
 */
package it.univaq.idw.librionline.framework.util;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Giuseppe Della Penna
 */
public class MultipartFilter implements Filter {
    private FilterConfig config = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.config = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httprequest = (HttpServletRequest) request;
        if (request.getContentType() != null && request.getContentType().toLowerCase().indexOf("multipart/form-data") > -1) {
            MultipartHttpServletRequest mrequest = new MultipartHttpServletRequest(httprequest);
            chain.doFilter(mrequest, response);
        } else {
            chain.doFilter(httprequest, response);
        }
    }

    @Override
    public void destroy() {
        config = null;
    }
}
