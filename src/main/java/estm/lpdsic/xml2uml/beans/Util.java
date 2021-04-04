package estm.lpdsic.xml2uml.beans;

import estm.lpdsic.xml2uml.dao.XMLDiagramHandler;
import estm.lpdsic.xml2uml.model.Diagram;
import org.xml.sax.SAXException;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.servlet.http.Part;
import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.SchemaFactory;
import java.io.InputStream;
import java.io.Serializable;

@Named(value = "xmlUtil")
@SessionScoped
public class Util implements Serializable {
    private final SAXParser saxParser;
    private Part uploadedFile;
    private String errorsHTML;
    private Diagram selectedDiagram;

    public Util() throws ParserConfigurationException, SAXException {
        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        parserFactory.setSchema(SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                .newSchema(getClass().getResource("/schema.xsd")));
        saxParser = parserFactory.newSAXParser();
    }

    public void upload() {
        try {
            if (uploadedFile != null) {
                InputStream input = uploadedFile.getInputStream();
                XMLDiagramHandler diagramHandler = new XMLDiagramHandler();

                saxParser.parse(input, diagramHandler);

                errorsHTML = diagramHandler.getErrors();

                selectedDiagram = diagramHandler.getDiagram();
            } else {
                errorsHTML = null;
                selectedDiagram = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getErrorsHTML() {
        return errorsHTML;
    }

    public Part getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(Part uploadedFile) {
        this.uploadedFile = uploadedFile;
        upload();
    }

    public String getSelectedFileName() {
        return this.uploadedFile.getSubmittedFileName();
    }

    public Diagram getSelectedDiagram() {
        return selectedDiagram;
    }
}
