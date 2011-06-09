/*
 * Questa classe, derivata da HttpServletRequestWrapper, estende la normale HttpServletRequest
 * per trattare in maniera trasparente le richieste di tipo multipart utilizzando
 * Apache FileUpload.
 * I parametri normali vengono presentati tramite la normale interfaccia getParameter,
 * mentre il metodo specifico getStream viene utilizzato per leggere l'oggetto
 * InputStream associato ai campi di tipo file. Inoltre, vengono aggiunti
 * i due parametri X_name, X_type e X_size, dove X è il nome del campo di tipo file,
 * contenenti rispettivamente il nome, il tipo mime e la dimensione del file inviato.
 *
 */
package it.univaq.idw.librionline.framework.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class MultipartHttpServletRequest extends HttpServletRequestWrapper {

    private Map<String, String[]> postparams;
    private Map<String, InputStream[]> poststreams;

    public MultipartHttpServletRequest(HttpServletRequest request) {
        super(request);
        postparams = new HashMap();
        poststreams = new HashMap();
        parsePOSTParameters(request);
    }

    private void parsePOSTParameters(HttpServletRequest request) {
        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List<FileItem> items;
                items = upload.parseRequest(request);
                for (FileItem item : items) {
                    if (item.isFormField()) {
                        addToParameterMap(item.getFieldName(), item.getString());
                    } else {
                        addToParameterMap(item.getFieldName(),"$STREAM$");
                        addToParameterMap(item.getFieldName() + "_type", item.getContentType());
                        addToParameterMap(item.getFieldName() + "_size", String.valueOf(item.getSize()));
                        addToParameterMap(item.getFieldName() + "_name", item.getName());
                        addToStreamMap(item.getFieldName(), item.getInputStream());
                    }
                }
            } catch (FileUploadException ex) {
                Logger.getLogger(MultipartHttpServletRequest.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MultipartHttpServletRequest.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            postparams = request.getParameterMap();
        }
    }

    private void addToParameterMap(String key, String value) {
        String[] arrvalue = {value};
        if (!postparams.containsKey(key)) {
            postparams.put(key, arrvalue);
        } else {
            String[] oldarrvalue = postparams.get(key);
            String[] newarrvalue = new String[oldarrvalue.length + 1];
            System.arraycopy(oldarrvalue, 0, newarrvalue, 0, oldarrvalue.length);
            System.arraycopy(arrvalue, 0, newarrvalue, oldarrvalue.length, arrvalue.length);
            postparams.put(key, newarrvalue);
        }
    }

    private void addToStreamMap(String key, InputStream value) {
        InputStream[] arrvalue = {value};
        if (!poststreams.containsKey(key)) {
            poststreams.put(key, arrvalue);
        } else {
            InputStream[] oldarrvalue = poststreams.get(key);
            InputStream[] newarrvalue = new InputStream[oldarrvalue.length + 1];
            System.arraycopy(oldarrvalue, 0, newarrvalue, 0, oldarrvalue.length);
            System.arraycopy(arrvalue, 0, newarrvalue, oldarrvalue.length, arrvalue.length);
            poststreams.put(key, newarrvalue);
        }
    }

    @Override
    public String getParameter(String name) {
        if (postparams.containsKey(name)) {
            return postparams.get(name)[0];
        } else {
            return null;
        }
    }

    @Override
    public Map getParameterMap() {
        return postparams;
    }

    @Override
    public Enumeration getParameterNames() {
        return Collections.enumeration(postparams.keySet());
    }

    @Override
    public String[] getParameterValues(String name) {
        return postparams.get(name);
    }

    public InputStream[] getStreams(String name) {
        return poststreams.get(name);

    }

    public InputStream getStream(String name) {
        if (poststreams.containsKey(name)) {
            return poststreams.get(name)[0];
        } else {
            return null;
        }
    }
}
