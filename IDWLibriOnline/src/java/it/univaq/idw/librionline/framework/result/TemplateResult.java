package it.univaq.idw.librionline.framework.result;

import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Giuseppe Della Penna
 */
public class TemplateResult {

    protected ServletContext context;
    protected Configuration cfg;

    public TemplateResult(ServletContext context) {
        this.context = context;
        init();
    }

    private void init() {
        cfg = new Configuration();
        //impostiamo l'encoding di default per l'output, se specificato
        if (context.getInitParameter("view.encoding") != null) {
            cfg.setDefaultEncoding(context.getInitParameter("view.encoding"));
        }
        //impostiamo il gestore degli oggetti - trasformer� in data model i Java beans
        cfg.setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
        //impostiamo la directory (relativa al contesto) da cui caricare i templates
        cfg.setServletContextForTemplateLoading(context, context.getInitParameter("view.template_directory"));
        //impostiamo un handler per gli errori nei template - utile per il debug
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
        //formato di default per data/ora
        if (context.getInitParameter("view.date_format") != null) {
            cfg.setDateTimeFormat(context.getInitParameter("view.date_format"));
        }
    }

    //questo metodo restituisce un data model (hash) di base,
    //(qui inizializzato anche con informazioni di base utili alla gestione dell'outline)
    protected Map getDefaultDataModel() {
        //inizializziamo il contenitore per i dati di deafult        
        Map default_data_model = new HashMap();
        default_data_model.put("compiled_on", Calendar.getInstance().getTime()); //data di compilazione del template
        default_data_model.put("outline_tpl", context.getInitParameter("view.outline_template")); //eventuale template di outline
        //aggiungiamo al template i dati di inizializzazione
        Map init_tpl_data = new HashMap();
        default_data_model.put("defaults", init_tpl_data);
        Enumeration parms = context.getInitParameterNames();
        while (parms.hasMoreElements()) {
            String name = (String) parms.nextElement();
            if (name.startsWith("view.data.")) {
                init_tpl_data.put(name.substring(10), context.getInitParameter(name));
            }
        }
        return default_data_model;
    }

    //questo metodo restituisce un data model estratto dagli attributi della request
    protected Map getRequestDataModel(HttpServletRequest request) {
        Map datamodel = getDefaultDataModel();
        Enumeration attrs = request.getAttributeNames();
        while (attrs.hasMoreElements()) {
            String attrname = (String) attrs.nextElement();
            datamodel.put(attrname, request.getAttribute(attrname));
        }
        return datamodel;
    }

    //questo metodo principale si occupa di chiamare Freemarker e compilare il template
    //se � stato specificato un template di outline, quello richiesto viene inserito
    //all'interno dell'outline
    protected void process(String tplname, Map datamodel, Writer out) throws ServletException {
        Template t;
        //assicuriamoci di avere sempre un data model da passare al template, che contenga anche tutti i default
        Map<String, Object> localdatamodel = getDefaultDataModel();
        //nota: in questo modo il data model utente pu� eventualmente sovrascrivere i dati precaricati da getDefaultDataModel
        //ad esempio per disattivare l'outline template basta porre a null la rispettiva chiave

        if (datamodel != null) {
            localdatamodel.putAll(datamodel);
        }
        String outline_name = (String) localdatamodel.get("outline_tpl");
        try {
            if (outline_name == null || outline_name.isEmpty()) {
                //se non c'� un outline, carichiamo semplicemente il template specificato
                t = cfg.getTemplate(tplname);
            } else {
                //un template di outline � stato specificato: il template da caricare � quindi sempre l'outline...
                t = cfg.getTemplate(outline_name);
                //...e il template specifico per questa pagina viene indicato all'outline tramite una variabile content_tpl
                localdatamodel.put("content_tpl", tplname);
                //si suppone che l'outline includa questo secondo template
            }
            //Freemarker: associamo i dati al template e lo mandiamo in output
            t.process(localdatamodel, out);
        } catch (IOException e) {
            throw new ServletException("Errore nell'elaborazione del template: " + e.getMessage(), e);
        } catch (TemplateException e) {
            throw new ServletException("Errore nell'elaborazione del template: " + e.getMessage(), e);
        }
    }

    //questa versione di activate accetta un modello dati esplicito
    public void activate(String tplname, Map datamodel, HttpServletResponse response) throws ServletException, IOException {
        //impostiamo il content type, se specificato dall'utente, o usiamo il default
        String contentType = (String) datamodel.get("contentType");
        if (contentType == null) {
            contentType = "text/html";
        }
        response.setContentType(contentType);

        //impostiamo l'encoding, se specificato dall'utente, o usiamo il default
        String encoding = (String) datamodel.get("encoding");
        if (encoding == null) {
            encoding = cfg.getDefaultEncoding();
        }
        response.setCharacterEncoding(encoding);

        process(tplname, datamodel, response.getWriter());
    }

    //questa versione di activate estrae un modello dati dagli attributi della request
    public void activate(String tplname, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map datamodel = getRequestDataModel(request);
        activate(tplname, datamodel, response);
    }

    //questa versione di activate pu� essere usata per generare output non diretto verso il browser, ad esempio
    //su un file
    public void activate(String tplname, Map datamodel, OutputStream out) throws ServletException, UnsupportedEncodingException {
        //impostiamo l'encoding, se specificato dall'utente, o usiamo il default
        String encoding = (String) datamodel.get("encoding");
        if (encoding == null) {
            encoding = cfg.getDefaultEncoding();
        }
        //notare la gestione dell'encoding, che viene invece eseguita implicitamente tramite il setContentType nel contesto servlet
        process(tplname, datamodel, new OutputStreamWriter(out, encoding));
    }
}
